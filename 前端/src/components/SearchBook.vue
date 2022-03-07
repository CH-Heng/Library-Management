<template>
  <div class="container">
    <Form ref="formInline" :model="formInline":rules="ruleInline" inline>
      <FormItem prop="title">
        <Input type="text" v-model="formInline.title" placeholder="书名/作者/ISBN/出版社">
          <Icon type="search" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit()">查找</Button>
      </FormItem>
    </Form>
    <Table :row-class-name="rowClassName" border :columns="columns7" :data="data6"></Table>
    <Page :total="total" :page-size="10" @on-change="changePage"></Page>
  </div>
</template>
<script type="es6">
export default {
  name: 'SearchBook',
  data () {
    return {
      total: 0,
      condi: '',
      modal1: false,
      modal2: false,
      currIndex: 0,//最近被点击添加编号副本的图书编号
      formInline: {
        title: ''
      },
      ruleInline: {
        title: [{
          required: true,
          message: '请填写查询信息',
        }]
      },
      formItem2: {
        title: '',
        author: '',
        publisher: '',
        publishtime: '',
        descri: ''
      },
      ruleItem2: {
        title: [{
          required: true,
          message: '请填写书名！',
          trigger: 'blur'
        }],
        author: [{
          required: true,
          message: '请填书籍作者',
          trigger: 'blur'
        }],
        publisher: [{
          required: true,
          message: '请填出版社',
          trigger: 'blur'
        }],
        publishtime: [{
          required: true,
          message: '请填写出版时间'
        }],
        descri: [{
          required: true,
          message: '请填书籍描述',
          trigger: 'blur'
        }]
      },
      columns7: [
        {
          title: '编号',
          key: 'aid',
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
          title: '作者',
          key: 'author'
        },
        {
          title: '出版社',
          key: 'publisher'
        },
        {
          title: 'ISBN',
          key: 'isbn'
        },
        {
          title: '是否可借',
          key: 'canBorrowed'
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
      data7: []
    }
  },
  mounted(){
    this.request(1);
  },
  methods: {
    handleSubmit() {
      this.$refs.formInline.validate((valid)=>{
        if(valid){
          // this.$Message.info('表单验证通过')
          this.request(1)
        }
        else{
          this.$Message.info('表单验证未通过')
        }
      })
    },
    show (index) {
      this.$Modal.info({
        title: '书籍信息',
        width: '1100',
        content: `书名：${this.data6[index].title}<br>作者：${this.data6[index].author}<br>出版社：${this.data6[index].publisher}<br>是否可借: ${this.data6[index].canBorrowed}<br>介绍：${this.data6[index].descri}`
      })
    },
    request (currentPage){
      var that=this
      this.$http.post(that.GLOBAL.serverPath + '/excise/getAllAlbums',
        {
          title: that.formInline.title,
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {

        that.total=res.data.pageInfo.total
        that.data6=[]
        that.data7=res.data.albums
        let aid= 1
        that.data7.forEach((e) => {
          let obj={}
          obj.title = e.title
          obj.author = e.author
          obj.publisher = e.publisher
          obj.isbn = e.isbn
          obj.descri = e.descri
          obj.aid = aid++
          if(e.loan === 0){
            obj.canBorrowed = '不可借'
          }else{
            obj.canBorrowed = '可借'
          }
          that.data6.push(obj)
        })
      })
    },
    changePage: function(page){
      this.request(page)
    },
    rowClassName (row, index) {
      if (this.data6[index].canBorrowed === '不可借') {
        return 'cannotBorrow-row';
      } else{
        return ' ';
      }
      return '';
    }

  }
}
</script>

<style>
  .cannotBorrow-row{
    color: #e2e2e2;
  }
</style>

