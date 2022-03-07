<template>
  <div class="container">
    <Form ref="formInline" :model="formInline" :rules="ruleCheck"  inline>
      <FormItem prop="raccount" style="width:300px">
        <Input type="text" v-model="formInline.raccount" placeholder="借阅者帐号/ISBN/索书号/书名" >
          <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formInline')">查找</Button>
      </FormItem>
    </Form>
    <Table border :columns="columns7" :data="data6"></Table>
    <Page :total="total" :page-size="10" @on-change="changePage"></Page>
  </div>
</template>
<script type="es6">
export default {
  name: 'UserManage',
  data () {
    return {
      total: 0,
      condi: '',
      modal1: false,
      modal2: false,
      currIndex: 0,//最近被点击添加编号副本的图书编号
      formInline: {
        raccount: ''
      },
      ruleCheck: {
        raccount: [{
          required: true,
          message: '请填写借阅者帐号/ISBN/索书号/书名',
        }]
      },
      columns7: [
        {
          title: '编号',
          key: 'bid',
          render: (h, params) => {
            return h('div', [
              h('Icon', {
                props: {
                  type: 'document-text'
                }
              }),
              h('strong', params.index+1)
            ]);
          }
        },
        {
          title: '书名',
          key: 'title'
        },
        {
          title: '索书号',
          key: 'callnumber'
        },
        {
          title: '借阅者账号',
          key: 'raccount'
        },
        {
          title: '借阅者姓名',
          key: 'rname'
        },
        {
          title: '借阅时间',
          key: 'borrowtime'
        },
        {
          title: '应归还时间',
          key: 'backtime'
        },
        {
          title: '逾期情况',
          key: 'condi'
        },
        {
          title: '罚款金额',
          key: 'fine'
        },
        {
          title: '操作',
          key: 'action',
          width: 250,
          align: 'center',
          render: (h, params) => {
            let text1=null
            let text2=null
            if(params.row.overduedays==0){
              text1 = true
              text2 = false
            }
            else{
              text2 = true
              text1 = false
            }
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.show(params.index)
                  }
                }
              }, '查看'),
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small',
                  disabled: text2,
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.comfirmReback(params.index)
                  }
                }
              }, '确认归还'),
              h('Button', {
                props: {
                  type: 'error',
                  size: 'small',
                  disabled: text1,
                },
                on: {
                  click: () => {
                    this.comfirmFine(params.index)
                  }
                }
              }, '罚款')
            ]);
          }
        }
      ],
      data6: [],
      data7: [] //存放从后台请求过来的借阅记录
    }
  },
  mounted(){
    this.request(1);
  },
  methods: {
    handleSubmit(account) {
      this.request(1)
    },
    show (index) {
      this.$Modal.info({
        title: '书籍信息',
        content: `书名：${this.data6[index].title}<br>索书号：${this.data6[index].callnumber}<br>借阅者学号：${this.data6[index].raccount}<br>借阅者姓名：${this.data6[index].rname}<br>借阅时间：${this.data6[index].borrowtime}<br>应归还时间：${this.data6[index].backtime}<br>状态：${this.data6[index].condi}`
      })
    },
    //归还接口
    comfirmReback(index){

      this.$Modal.confirm({
        title: '温馨提示',
        content: '<p>确定归还这本图书吗?</p>',
        onOk: () => {
          // this.$Message.info('Clicked ok');
          let that=this
          this.$http.post(that.GLOBAL.serverPath + '/excise/rabackBook',
            {
              raccount: that.data6[index].raccount,
              callnumber: that.data6[index].callnumber
            },
            {
              emulateJSON: true
            }
          ).then(function (res) {
            if(res.data.result == 'yes'){
              that.request(1)
              that.data6.splice(index,1)
              this.$Message.success('操作成功')
            }else{

              this.$Message.error('操作失败')
            }
          })
        },
      });
    },
    //罚款接口
    comfirmFine(index){

      this.$Modal.confirm({
        title: '温馨提示',
        content: '<p>是否已线下缴款?</p>',
        onOk: () => {
          // this.$Message.info('Clicked ok');
          let that=this

          this.$http.post(that.GLOBAL.serverPath + '/excise/addfine',
            {
              raccount: that.data6[index].raccount,
              callnumber: that.data6[index].callnumber,
              title: that.data6[index].title,
              rname: that.data6[index].rname,
              backtime: that.data6[index].backtime,
              fine: that.data6[index].fine
            },
            {
              emulateJSON: true
            }
          ).then(function (res) {
            if(res.data.result == 'yes'){
              that.request(1)
              this.$Message.success('操作成功')

            }else{
              this.$Message.error('操作失败')
            }
          })
        },
      });
    },
    //模糊查找接口
    request (currentPage){

      let that=this
      this.$http.post(that.GLOBAL.serverPath + '/excise/getAllRecord',
        {
          raccount: that.formInline.raccount,
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {

        that.total=res.data.pageInfo.total
        that.data6=[]
        that.data7=res.data.borrowrecords
        that.data7.forEach((e) =>{
          let obj = {}
          obj.callnumber =e.callnumber
          obj.title = e.title
          obj.raccount = e.raccount
          obj.rname = e.rname
          obj.borrowtime = e.borrowtime
          obj.backtime = e.backtime
          obj.overduedays=e.overduedays
          obj.fine = e.fine
          if( obj.overduedays==0){
            obj.condi = '未逾期'
          }else{
            obj.condi = '逾期'+obj.overduedays+'天'
          }
          that.data6.push(obj)
        })
      })
    },
    changePage: function(page){
      this.request(page)
    },
  }
}
</script>

