<template>
  <div class="container">
    <Form ref="formInline" :model="formInline" :rules="ruleDate" inline>
      <FormItem prop="DateRange">
        <DatePicker v-model="formInline.DateRange" v-bind:clearable=false type="daterange" split-panels format="yyyy年MM月dd日" placement="bottom-end" placeholder="Select date" style="width: 250px"></DatePicker>
      </FormItem>
      <Button type="primary" @click="handleSubmit()">查找</Button>
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
        DateRange: ''
      },
      ruleDate: {
        DateRange: [{
          required: true,
          message: '请选择日期范围'
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
          title: '索书号',
          key: 'callnumber'
        },
        {
          title: '书名',
          key: 'title'
        },
        {
          title: '读者账号',
          key: 'raccount'
        },
        {
          title: '读者姓名',
          key: 'rname'
        },
        {
          title: '应还书时间',
          key: 'backtime'
        },
        {
          title: '付款时间',
          key: 'paytime'
        },
        {
          title: '罚款金额',
          key: 'fine'
        },
        {
          title: '操作',
          key: 'action',
          width: 150,
          align: 'center',
          render: (h, params) => {
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
              }, '查看详情'),
            ]);
          }
        }
      ],
      data6: [{
        title: 123,
        callnumber: '索书号',
        raccount: 123,
        rname: 123,
        backtime: 123,
        paytime: null,
        fine: 0
      }],
      data7: [] //存放从后台请求过来的借阅记录
    }
  },
  mounted(){
    this.request(1);
  },
  methods: {
    handleSubmit() {

      this.$refs.formInline.validate((vaild)=>{
        if(vaild){
          this.$Message.info('验证通过')
          this.request(1)
        }
        else{
          this.$Message.info('验证未通过')
        }
      })
    },
    show (index) {
      this.$Modal.info({
        title: '书籍信息',
        content: `索书号：${this.data6[index].title}<br>书名：${this.data6[index].title}<br>读者账号：${this.data6[index].raccount}<br>借阅者姓名：${this.data6[index].rname}<br>应归还时间：${this.data6[index].backtime}<br>付款时间：${this.data6[index].paytime}<br>罚款金额：${this.data6[index].fine}`
      })
    },
    reback (index) {
      //this.data6.splice(index, 1);
      var that=this
      this.$http.post(that.GLOBAL.serverPath + '/excise/reback',
        {
          bid: that.data6[index].bid,
          sid: that.data6[index].sid
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        if(res.data.status == 'yes'){
          that.data6.splice(index,1)
          this.$Message.success('操作成功')
        }else{
          this.$Message.status('操作失败')
        }
      })
    },
    //刷新接口
    request (currentPage){
      let that=this
      this.$http.post(that.GLOBAL.serverPath + '/excise/manafegetfine',
        {

          DateStart: that.formInline.DateRange[0].toString(),
          DateEnd: that.formInline.DateRange[1].toString(),
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {

        that.total=res.data.pageInfo.total
        that.data6=[]
        that.data7=res.data.finerecords
        that.data7.forEach((e) =>{
          let obj = {}
          obj.title = e.title
          obj.callnumber = e.callnumber
          obj.raccount = e.raccount
          obj.rname = e.rname
          obj.paytime = e.paytime
          obj.backtime = e.backtime
          obj.fine=e.fine
          var time = new Date().getTime();


          if(time < e.inttime){
            obj.condi = '未超期'
          }else{
            var delayDay = Math.ceil((time - e.inttime)/86400000)
            obj.condi = '超期'+delayDay+'天'
          }
          that.data6.push(obj)
        })
      })
    },
    changePage: function(page){
      this.request(page)
    },
    comfirmReback(index){
      this.$Modal.confirm({
        title: '温馨提示',
        content: '<p>确定归还这本图书吗?</p>',
        onOk: () => {
          // this.$Message.info('Clicked ok');
          this.reback(index)
        },
      });
    },
  }
}
</script>
