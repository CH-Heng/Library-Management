<template>
  <div class="container">
    <Form ref="formInline" :model="formInline" :rules="ruleInline" inline>
      <FormItem prop="account">
        <Input type="text" v-model="formInline.account" placeholder="学号">
          <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit()">查找</Button>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="modal1 = true">新添用户</Button>
      </FormItem>
    </Form>
    <Table border :columns="columns7" :data="data6"></Table>
    <Page :total="total" :page-size="10" @on-change="changePage"></Page>

    <Modal
      v-model="modal1"
      title="新添用户"
      @on-ok="ok('formItem2')"
    >
      <Form ref="formItem2" :model="formItem2" :rules="ruleItem2" :label-width="80">
        <FormItem label="账号" prop="account">
          <Input v-model="formItem2.account" placeholder=""></Input>
        </FormItem>
        <FormItem label="姓名" prop="name">
          <Input v-model="formItem2.name" placeholder=""></Input>
        </FormItem>
        <FormItem label="性别" prop="sex">
          <RadioGroup v-model="formItem2.sex">
            <Radio label="男">男</Radio>
            <Radio label="女">女</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="电话" prop="phone">
          <Input v-model="formItem2.phone" placeholder=""></Input>
        </FormItem>
        <FormItem label="邮箱" prop="email">
          <Input v-model="formItem2.email" placeholder=""></Input>
        </FormItem>
        <FormItem label="身份" prop="condi">
          <Select v-model="formItem2.condi">
            <Option value="0">学生</Option>
            <Option value="1">图书管理员</Option>
          </Select>
        </FormItem>
      </Form>
    </Modal>

    <Modal
      v-model="modal2"
      title="编辑用户信息"
      @on-ok="comfirmManager()"
    >
      <Form ref="manageUser" :model="manageUser" :rules="ruleItem3" :label-width="80">
        <FormItem label="姓名" prop="name">
          <Input v-model="manageUser.name" v-bind:disabled="ButStatu" placeholder=""></Input>
        </FormItem>
        <FormItem label="电话" prop="phone">
          <Input v-model="manageUser.phone" v-bind:disabled="ButStatu" placeholder=""></Input>
        </FormItem>
        <FormItem label="邮箱" prop="email">
          <Input v-model="manageUser.email" v-bind:disabled="ButStatu" placeholder=""></Input>
        </FormItem>
        <FormItem label="身份" prop="condi">
          <Select v-model="manageUser.condi" v-bind:disabled="ButStatu">
            <Option value="0">学生</Option>
            <Option value="1">图书管理员</Option>
          </Select>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="default"  @click="modal2 = false">取消</Button>
        <Button type="primary" v-show="ButStatu" @click="ButStatu=false">编辑</Button>
        <Button type="primary" @click="comfirmManager()">修改</Button>
      </div>
    </Modal>
  </div>
</template>
<script type="es6">
export default {
  name: 'UserManage',
  data () {
    return {
      ButStatu: false,
      testindex: 0,
      total: 0,
      condi: '',
      modal1: false,
      modal2: false,
      formInline: {
        account: ''
      },
      manageUser:{
        account: '',
        name: '',
        phone: '',
        email: '',
        condi: 0
      },
      formItem2: {
        account: '',
        name: '',
        sex: '男',
        phone: '',
        email: '',
        condi: '0'
      },
      formItem3: {
        name: '',
        phone: '',
        email: '',
        condi: '0'
      },
      ruleInline: {
        account: [{
          required: true,
          message: '请填写学号',
          trigger: 'blur'
        }]
      },
      ruleItem2: {
        account: [{
          required: true,
          message: '请填写账号！',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请填写学生姓名',
          trigger: 'blur'
        }],
        phone: [{
          required: true,
          message: '请填写电话号码',
          trigger: 'blur'
        }],
        email: [{
          required: true,
          message: '请填写电子邮箱',
          trigger: 'blur'
        }]
      },
      ruleItem3: {
        name: [{
          required: true,
          message: '请填写学生姓名',
        }],
        phone: [{
          required: true,
          message: '请填写电话号码',
        }],
        email: [{
          required: true,
          message: '请填写电子邮箱',
        }]
      },
      columns7: [
        {
          title: '账号',
          key: 'account',
          render: (h, params) => {
            return h('div', [
              h('Icon', {
                props: {
                  type: 'person'
                }
              }),
              h('strong', params.row.account)
            ]);
          }
        },
        {
          title: '姓名',
          key: 'name'
        },
        {
          title: '性别',
          key: 'sex'
        },
        {
          title: '录入时间',
          key: 'time'
        },
        {
          title: '身份',
          key: 'condi'
        },
        {
          title: '操作',
          key: 'action',
          width: 220,
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
              }, '查看'),
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
                    this.modal2=true
                    this.ButStatu=true
                    this.manageUser.account=params.row.account
                    this.manageUser.name=params.row.name
                    this.manageUser.phone=params.row.phone
                    this.manageUser.email=params.row.email
                    this.manageUser.condi=params.row.condi
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
        account: 123456,
        phone: 1,
        name: 123,
        email: 123,
        condi: 123,
      },{
        phone: 1,
        name: 123,
        email: 123,
        condi: 123,
      },{
        phone: 1,
        name: 123,
        email: 123,
        condi: 123,
      },{
        phone: 1,
        name: 123,
        email: 123,
        condi: 123,
      },{
        phone: 1,
        name: 123,
        email: 123,
        condi: 123,
      },{
        phone: 1,
        name: 123,
        email: 123,
        condi: 123,
      },{
        phone: 1,
        name: 123,
        email: 123,
        condi: 123,
      },{
        phone: 1,
        name: 123,
        email: 123,
        condi: 123,
      },{
        phone: 1,
        name: 123,
        email: 123,
        condi: 123,
      },{
        phone: 1,
        name: 123,
        email: 123,
        condi: 123,
      },{
        phone: 1,
        name: 123,
        email: 123,
        condi: 123,
      },{
        phone: 1,
        name: 123,
        email: 123,
        condi: 123,
      }]
    }
  },
  mounted(){
    this.request(1);
  },
  methods: {
    handleSubmit(account) {
      this.$refs.formInline.validate((vaild)=>{
        if(vaild){
          this.request(1)
        }
      })

    },
    show (index) {
      if(this.data6[index].condi==0){
        this.condi='学生'
      }else{
        this.condi='图书管理员'
      }
      this.$Modal.info({
        title: '用户信息',
        content: `姓名：${this.data6[index].name}<br>性别：${this.data6[index].sex}<br>账号：${this.data6[index].account}<br>身份：${this.condi}`
      })
    },
    request (currentPage){
      var that=this
      this.$http.post(that.GLOBAL.serverPath + '/excise/getAllReaders',
        {
          account: that.formInline.account,
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        console.log(res.data.pageInfo)
        that.total=res.data.pageInfo.total
        that.data6=res.data.readers
      }).catch((e) => {
        this.$Message.fail('网络有误！')
      })
    },
    changePage: function(page){
      this.request(page)
    },
    ok (name) {
      var that=this
      this.$refs[name].validate((valid) => {
        if (valid) {
          that.$http.post(that.GLOBAL.serverPath + '/excise/addReader',
            {
              account: that.formItem2.account,
              name: that.formItem2.name,
              sex: that.formItem2.sex,
              phone: that.formItem2.phone,
              email: that.formItem2.email,
              condi: that.formItem2.condi,
            },
            {
              emulateJSON: true
            }
          ).then(function (res) {
            console.log(res.data.status)
            if(res.data.status=='ok'){
              that.$Message.success('新增成功')
              that.formInline.account=''
              that.request(1)
            }else{
              that.$Message.error('已存在该账户的用户')
            }

          }).catch((e) => {
            that.$Message.fail('网络有误！')
          })
        }
      })
    },
    comfirmManager(){
      let that =this
      this.$refs.manageUser.validate(vaild=>{
        if(vaild){
          this.$Modal.confirm({
            title: '温馨提示',
            content: '<p>确定编辑用户信息?</p>',
            onOk: () => {
              this.$Message.info('Clicked ok');
              console.log('所传数据'+that.manageUser)
              that.$http.post(that.GLOBAL.serverPath + '/excise/modifyReader',
                {
                  account: that.manageUser.account,
                  name: that.manageUser.name,
                  phone: that.manageUser.phone,
                  email: that.manageUser.email,
                  condi: that.manageUser.condi,
                },
                {
                  emulateJSON: true
                }
              ).then(function (res){
                  if(res.data.result =='yes'){
                    that.$Message.success('编辑成功!')
                    that.modal2 = false
                    this.request(1)
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
    comfirmRemove(index){
      let that = this
      this.$Modal.confirm({
        title: '温馨提示',
        content: '<p>确定删除这个用户吗?</p>',
        onOk: () => {
          // this.$Message.info('Clicked ok');
          console.log('所传数据'+that.data6[index].account)
          this.$http.post(that.GLOBAL.serverPath + '/excise/deleteReader',
            {
              account: that.data6[index].account
            },
            {
              emulateJSON: true
            }
          ).then(function (res) {
            if(res.data.result=='yes'){
              that.data6.splice(index, 1)
              that.$Message.success('删除成功！')
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

