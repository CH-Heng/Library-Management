<template>
  <div class="container">
    <Table border :columns="columns3" :data="data6"></Table>
    <Page :total="total" :page-size="10" @on-change="changePage"></Page>
  </div>
</template>
<script type="es6">
  export default {
    name: 'readerinfo',
    data () {
      return {
        total: '',
        condi: '',
        modal1: false,
        modal2: false,
        currIndex: 0,//最近被点击添加编号副本的图书编号
        formInline: {
          raccount: ''
        },
        columns3: [
          {
            title: '电话',
            key: 'bid',
            render: (h, params) => {
              return h('div', [
                h('Icon', {
                  props: {
                    type: 'document-text'
                  }
                }),
                h('strong', params.row.bid)
              ]);
            }
          },
          {
            title: '邮箱',
            key: 'title'
          },
          {
            title: '密码',
            key: 'number'
          },
          {
            title: '借阅者学号',
            key: 'raccount'
          },
          {
            title: '借阅者姓名',
            key: 'rname'
          },
          {
            title: '借阅时间',
            key: 'time'
          },
          {
            title: '应归还时间',
            key: 'backtime'
          },
          {
            title: '是否逾期',
            key: 'condi'
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
      request (currentPage){
        var that=this
        this.$http.post(that.GLOBAL.serverPath + '/excise/getAllBorrowRecords',
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
            obj.bid = e.bid
            obj.title = e.album.title
            obj.callnumber = e.subalbum.callnumber
            obj.raccount = e.raccount
            obj.rname = e.reader.name
            obj.time = e.time
            obj.backtime = e.backtime
            var time = new Date().getTime();

            if(time < e.inttime){
              obj.condi = '未逾期'
            }else{
              var delayDay = Math.ceil((time - e.inttime)/86400000)
              obj.condi = '逾期'+delayDay+'天'
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

