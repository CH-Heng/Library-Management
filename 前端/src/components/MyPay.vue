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
  name: 'MyPay',
  data () {
    return {
      total: 0,
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
          title: '应归还时间',
          key: 'backtime'
        },
        {
          title: '罚款时间',
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
      data6: [],
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
        content: `索书号：${this.data6[index].callnumber}<br>书名：${this.data6[index].title}<br>ISBN：${this.data6[index].isbn}<br>应归还时间：${this.data6[index].backtime}<br>罚款时间：${this.data6[index].paytime}<br>罚款金额：${this.data6[index].fine}`
      })
    },
    //缴款记录接口
    request (currentPage){
      let testdata = window.localStorage.getItem('account')

      let that=this

      that.$http.post(that.GLOBAL.serverPath + '/excise/getmyfine',
        {
          raccount: window.localStorage.getItem('account'),
          DateStart: that.formInline.DateRange[0].toString(),
          DateEnd: that.formInline.DateRange[1].toString(),
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {

        that.formInline.DateRange=''
        that.total=res.data.pageInfo.total
        that.data6=[]
        that.data7=res.data.finerecords
        that.data7.forEach((e) =>{
          let obj = {}
          obj.callnumber = e.callnumber
          obj.title = e.title
          obj.backtime=e.backtime
          obj.paytime = e.paytime
          obj.fine = e.fine
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
