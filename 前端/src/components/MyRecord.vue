<template>
  <div class="container">
    <Table border :columns="columns7" :data="data6"></Table>
    <Page :total="total" :page-size="10" @on-change="changePage"></Page>
  </div>
</template>
<script type="es6">
export default {
  name: 'MyRecord',
  data () {
    return {
      total: 1,
      condi: '',
      modal1: false,
      modal2: false,
      currIndex: 0,//最近被点击添加编号副本的图书编号
      formInline: {
        raccount: ''
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
          title: 'ISBN',
          key: 'isbn'
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
          title: '是否逾期',
          key: 'condi'
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
            let test = true
            if(params.row.overduedays==0){
              test = false
            }
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small',
                  disabled: test,
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.reborrow(params.index)
                  }
                }
              }, '续借'),
            ]);
          }
        }
      ],
      data6: [
        {
          title: '123',
          overduedays: 0,
        },
        {
          title: '23',
          overduedays: 1,
        }
      ],
      data7: [] //存放从后台请求过来的借阅记录
    }
  },
  mounted(){
    this.request(1);
  },
  methods: {
    //借阅记录接口
    request (currentPage){
      let testdata = window.localStorage.getItem('account')
      let that=this
      this.$http.post(that.GLOBAL.serverPath + '/excise/getMyRecord',
        {
          raccount: window.localStorage.getItem('account'),
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
          obj.isbn=e.isbn
          obj.overduedays=e.overduedays
          obj.borrowtime = e.borrowtime
          obj.backtime = e.backtime
          obj.paytime=e.paytime
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
    //续借接口
    reborrow(index){
      let testdata = window.localStorage.getItem('account')
      this.$Modal.confirm({
        title: '温馨提示',
        content: '<p>确定续借这本图书吗?</p>',
        onOk: () => {
          // this.$Message.info('Clicked ok');
          let that=this
          this.$http.post(that.GLOBAL.serverPath + '/excise/renewMyBook',
            {
              raccount: window.localStorage.getItem('account'),
              callnumber: that.data6[index].callnumber
            },
            {
              emulateJSON: true
            }
          ).then(function (res) {

            if(res.data.result == 'yes'){
              this.$Message.success('操作成功')
              this.request(1)
            }else{
              this.$Message.error('操作失败')
            }
          })
        },
      });
    },
  }
}
</script>

