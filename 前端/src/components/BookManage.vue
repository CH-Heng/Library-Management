<template>
  <div class="container">

    <Form ref="Bookname" :model="Bookname" :rules="ruleBookname" inline style="display: inline">
      <FormItem prop="title">
        <Input type="text" v-model="Bookname.title" placeholder="书名">
          <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
    </Form>
    <Button type="primary" @click="handleSubmit()">查找</Button>
    <Button type="primary" @click="addBookModal = true">新添书籍</Button>
    <Table border :columns="columns7" :data="data6"></Table>
    <Page :total="total" :page-size="10" @on-change="changePage"></Page>

    <Modal
      v-model="addBookModal"
      title="新添书籍"
      width="800"
    >
      <Form ref="addBook" :model="addBook" :rules="ruleaddBook" :label-width="80">
        <FormItem label="索书号" prop="callnumber">
          <Input v-model="addBook.callnumber" placeholder=""></Input>
        </FormItem>
        <FormItem label="书名" prop="title">
          <Input v-model="addBook.title" placeholder=""></Input>
        </FormItem>
        <FormItem label="作者" prop="author">
          <Input v-model="addBook.author" placeholder=""></Input>
        </FormItem>
        <FormItem label="出版社" prop="publisher">
          <Input v-model="addBook.publisher" placeholder=""></Input>
        </FormItem>
        <FormItem label="isbn" prop="isbn">
          <Input v-model="addBook.isbn" placeholder="" style="width: 200px"></Input>
        </FormItem>
        <FormItem label="描述" prop="descri">
          <Input v-model="addBook.descri" type="textarea" :autosize="{minRows: 4,maxRows: 20}" placeholder="书籍描述..."></Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="default" @click="addBookModal = false">取消</Button>
        <Button type="primary" @click="comfirmAddbook()">添加</Button>
      </div>
    </Modal>

    <Modal
      v-model="borrowModal"
      title="提示"
    >
      <Form ref="accountForm" :model="accountForm" :rules="ruleaccountForm" :label-width="120">
        <FormItem label='请输入学生账号' prop="account">
          <input v-model="accountForm.account"></input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="default" @click="borrowModal = false">取消</Button>
        <Button type="primary" @click="comfirmBorrow(accountForm.account)">确定</Button>
      </div>
    </Modal>

    <Modal
      v-model="managerBookModal"
      title="编辑书籍"
      width="800"
    >
      <Form ref="managerBook" :model="managerBook" :rules="ruleaddBook" :label-width="80">
        <FormItem label="索书号" prop="callnumber">
          <Input v-model="managerBook.callnumber"  v-bind:disabled="ButStatu" placeholder=""></Input>
        </FormItem>
        <FormItem label="书名" prop="title">
          <Input v-model="managerBook.title" v-bind:disabled="ButStatu" placeholder=""></Input>
        </FormItem>
        <FormItem label="作者" prop="author">
          <Input v-model="managerBook.author" v-bind:disabled="ButStatu" placeholder=""></Input>
        </FormItem>
        <FormItem label="出版社" prop="publisher">
          <Input v-model="managerBook.publisher" v-bind:disabled="ButStatu" placeholder=""></Input>
        </FormItem>
        <FormItem label="isbn" prop="isbn">
          <Input v-model="managerBook.isbn" v-bind:disabled="ButStatu" placeholder="" style="width: 200px"></Input>
        </FormItem>
        <FormItem label="描述" prop="descri">
          <Input v-model="managerBook.descri" v-bind:disabled="ButStatu" type="textarea" :autosize="{minRows: 4,maxRows: 20}" placeholder="书籍描述..."></Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="default"  @click="managerBookModal = false">取消</Button>
        <Button type="primary" v-show="ButStatu" @click="ButStatu=false">编辑</Button>
        <Button type="primary" @click="comfirmManager()">修改</Button>
      </div>
    </Modal>

  </div>
</template>

<style>
  .container{
    height: 100%;
  }
</style>

<script type="es6">
export default {
  name: 'UserManage',
  data () {
    return {
      ButStatu: true,
      total: 0,
      condi: '',
      addBookModal: false,
      borrowModal: false,
      managerBookModal: false,
      managered: 0,
      content:'',
      Bookname: {
        title: ''
      },
      ruleBookname: {
        title: [{
          required: true,
          message: '请填写索书号',
          trigger: 'blur'
        }],
      },
      addBook: {
        callnumber: '',
        title: '',
        author: '',
        publisher: '',
        isbn: '',
        descri: ''
      },
      managerBook: {
        callnumber: '',
        title: '',
        author: '',
        publisher: '',
        isbn: '',
        descri: ''
      },
      ruleaddBook: {
        callnumber: [{
          required: true,
          message: '请填写索书号',
        }],
        title: [{
          required: true,
          message: '请填写书名！',
        }],
        author: [{
          required: true,
          message: '请填书籍作者',
        }],
        publisher: [{
          required: true,
          message: '请填出版社',
        }],
        isbn: [{
          required: true,
          message: '请填写出版时间'
        }],
        descri: [{
          required: true,
          message: '请填书籍描述',
        }]
      },
      accountForm: {
        account: ''
      },
      ruleaccountForm: {
        account: [{
          required: true,
          message: '请填写账号',
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
          title: '索书号',
          key: 'callnumber'
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
          title: 'isbn',
          key: 'isbn'
        },
        {
          title: '操作',
          key: 'action',
          width: 300,
          align: 'center',
          render: (h, params) => {
            let BTStatu = true
            if(params.row.loan){
              BTStatu = false
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
                  disabled: BTStatu
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.borrowModal=true
                    this.managerBook.callnumber=params.row.callnumber
                  }
                }
              }, '借阅'),
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
                    this.ButStatu=true
                    this.managerBookModal=true
                    this.managerBook.author=params.row.author
                    this.managerBook.isbn=params.row.isbn
                    this.managerBook.callnumber=params.row.callnumber
                    this.managerBook.descri=params.row.descri
                    this.managerBook.publisher=params.row.publisher
                    this.managerBook.title=params.row.title
                  }
                }
              }, '编辑'),
              h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                on: {
                  click: () => {
                    this.comfirmRemove(params.index)
                  }
                }
              }, '删除')
            ]);
          }
        }
      ],
      data6: [{
        callnumber: 1151,
        title: 123,
        author: 123,
        publisher: 123,
        isbn: 123,
        descri: 123,
        loan: 1
      },
        {
          callnumber: 114514,
          title: 1234,
          author: 1235,
          publisher: 1263,
          isbn: 123,
          descri: 123,
          loan: 0
        }],
      data7: []
    }
  },
  mounted(){
    this.request(1);
  },
  methods: {
    handleSubmit() {
      this.$refs.Bookname.validate((valid)=>{
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
        //content: `书名：${this.data6[index].title}<br>作者：${this.data6[index].author}<br>出版社：${this.data6[index].publisher}<br>出版时间：${this.data6[index].ISBN}<br>副本数量：${this.data6[index].num}<br>介绍：${this.data6[index].descri}`
        content: `索书号：${this.data6[index].callnumber}<br>书名：${this.data6[index].title}<br>作者：${this.data6[index].author}<br>出版社：${this.data6[index].publisher}<br>ISBN：${this.data6[index].isbn}<br>介绍：${this.data6[index].descri}`
      })
    },
    request (currentPage){
      var that=this

      this.$http.post(that.GLOBAL.serverPath + '/excise/managergetAllAlbums',
        {
          title: that.Bookname.title,
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {


        that.total=res.data.pageInfo.total
        that.data6=[]
        that.data7=res.data.albums
        that.data7.forEach((e) => {
          let obj={}
          obj.title = e.title
          obj.author = e.author
          obj.publisher = e.publisher
          obj.isbn = e.isbn
          obj.callnumber=e.callnumber
          obj.publishtime=e.publishtime
          obj.time=e.time
          obj.descri = e.descri
          obj.loan = e.loan
          that.data6.push(obj)
        })
      })
    },
    changePage: function(page){
      this.request(page)
    },
    //新增书籍接口
    comfirmAddbook(){
      let that =this
      this.$refs.addBook.validate(vaild=>{
        if(vaild){
          this.$Modal.confirm({
            title: '温馨提示',
            content: '<p>确定添加这本图书吗?</p>',
            onOk: () => {
              that.addBookModal= false
              this.$Message.info('Clicked ok');

              that.$http.post(that.GLOBAL.serverPath + '/excise/addAlbum',
                {
                  callnumber: that.addBook.callnumber,
                  title: that.addBook.title,
                  author: that.addBook.author,
                  publisher: that.addBook.publisher,
                  publishtime: that.addBook.publishtime,
                  isbn: that.addBook.isbn,
                  descri: that.addBook.descri,
                },
                {
                  emulateJSON: true
                }
              ).then(function (res) {


                if(res.data.result=='yes'){
                  //that.$Message.success('新增成功')
                  that.$Notice.config({
                    top: 50,
                    duration: 3,
                    title: '通知',
                    desc: '新添书籍成功!'
                  })
                  that.addBook.account=''
                  that.addBook.callnumber=''
                  that.addBook.title=''
                  that.addBook.author=''
                  that.addBook.publisher=''
                  that.addBook.publishtime=''
                  that.addBook.ISBN=''
                  that.addBook.descri=''
                  that.request(1)
                }
                else
                {
                  that.$Message.error('新增失败！')
                }
              }).catch((e) => {
                that.$Message.fail('网络有误！')
              })
            },
          });
        }
      })
    },
    //借阅接口
    comfirmBorrow(account){
      let that =this
      this.$refs.accountForm.validate(vaild=>{
        if(vaild){
          this.$Modal.confirm({
            title: '温馨提示',
            content: '<p>确定借阅这本图书吗?</p>',
            onOk: () => {
              this.$Message.info(account);
              that.borrowModal=false

              that.$http.post(that.GLOBAL.serverPath + '/excise/borrowBook',
                {
                  raccount: account,
                  callnumber: that.managerBook.callnumber
                },
                {
                  emulateJSON: true
                }
              ).then(function (res){
                  if(res.data.result =='yes'){
                    that.$Message.success('借阅成功!')
                    that.managerBookModal = false
                    that.request(1)
                  } else{
                    that.$Message.error('借阅失败!')
                  }
                }
              )
            },
          });
        }
      })
    },
    //编辑接口
    comfirmManager(){
      let that =this
      this.$refs.managerBook.validate(vaild=>{
        if(vaild){
          this.$Modal.confirm({
            title: '温馨提示',
            content: '<p>确定编辑这本图书吗?</p>',
            onOk: () => {
              this.$Message.info('Clicked ok');
              that.$http.post(that.GLOBAL.serverPath + '/excise/modeifyAlbum',
                {
                  callnumber: that.managerBook.callnumber,
                  title: that.managerBook.title,
                  author: that.managerBook.author,
                  publisher: that.managerBook.publisher,
                  isbn: that.managerBook.isbn,
                  descri: that.managerBook.descri,
                },
                {
                  emulateJSON: true
                }
              ).then(function (res){
                that.request(1)
                  if(res.data.result =='yes'){
                    that.$Message.success('编辑成功!')
                    that.managerBookModal = false
                  } else{
                    that.$Message.error('编辑失败!')
                  }
                }
              )
            },
          });
        }
      })
    },
    //删除接口
    comfirmRemove(index){
      let that = this
      this.$Modal.confirm({
        title: '温馨提示',
        content: '<p>确定删除这本图书吗?</p>',
        onOk: () => {

          this.$http.post(that.GLOBAL.serverPath + '/excise/deleteAlbum',
            {
              callnumber: that.data6[index].callnumber
            },
            {
              emulateJSON: true
            }
          ).then(function (res) {
            if(res.data.result=='yes'){
              that.request(1)
              that.data6.splice(index, 1)
            } else {
              that.$Message.error('删除失败')
            }
          })
        },
      });
    },
  }
}
</script>

