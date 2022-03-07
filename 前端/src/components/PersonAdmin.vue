<template>
  <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
    <FormItem label="姓名" prop="name">
      <Input v-model="formValidate.name" v-show="ButStatu" placeholder="请输入姓名"></Input>
      <span v-show="!ButStatu">{{formLocal.name}}</span>
    </FormItem>
    <FormItem label="电话号码" prop="phone">
      <Input v-model="formValidate.phone" v-show="ButStatu" placeholder="请输入电话号码"></Input>
      <span v-show="!ButStatu">{{formLocal.phone}}</span>
    </FormItem>
    <FormItem label="E-mail" prop="email">
      <Input v-model="formValidate.email" v-show="ButStatu" placeholder="Enter your e-mail"></Input>
      <span v-show="!ButStatu">{{formLocal.email}}</span>
    </FormItem>

    <FormItem>
      <Button style="background-color: #ffffff;color: #2475f5" type="primary" v-show="ButStatu" @click="editOn()">取消</Button>
      <Button type="primary" v-show="!ButStatu" @click="editOn()">编辑</Button>
      <Button type="primary" @click="handleSubmit()">保存</Button>
    </FormItem>
  </Form>
</template>

<script>
export default {
  name: 'PersonAdmin',
  data () {
    return {
      ButStatu: false,
      formLocal: {
        name: '',
        phone: '',
        email: ''
      },
      formValidate: {
        name: '',
        phone: '',
        email: ''
      },
      ruleValidate: {
        name: [
          { required: true, message: 'The name cannot be empty', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: 'The name cannot be empty', trigger: 'blur' }
        ],
        email: [
          { required: true, message: 'Mailbox cannot be empty', trigger: 'blur' },
          { type: 'email', message: 'Incorrect email format', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    // window.localStorage.setItem('name', '')
    // window.localStorage.setItem('phone', '')
    // window.localStorage.setItem('email', '')
    this.getInfo()
  },
  methods: {
    handleSubmit () {
      let that = this
      this.$refs.formValidate.validate((valid) => {
        if (valid) {
          // this.$Message.success('Success!')
          let account = window.localStorage.getItem('account')
          // 这里改接口
          that.$http.post(that.GLOBAL.serverPath + '/excise/modeifymessage',
            {
              account: account,
              name: that.formValidate.name,
              phone: that.formValidate.phone,
              email: that.formValidate.email
            },
            {
              emulateJSON: true
            }
          ).then(function (res) {
            if (res.data.result === 'yes') {
              this.$Message.success('保存成功!')
              location.reload()
              window.localStorage.name = that.formValidate.name
              window.localStorage.phone = that.formValidate.phone
              window.localStorage.email = that.formValidate.email
              that.formLocal.name = that.formValidate.name
              that.formLocal.phone = that.formValidate.phone
              that.formLocal.email = that.formValidate.email
              that.ButStatu = !that.ButStatu
            } else {
              this.$Message.error('保存失败！')
            }
          })
        } else {
          this.$Message.error('Fail!')
        }
      })
    },
    editOn () {
      this.formValidate.name = this.formLocal.name
      this.formValidate.phone = this.formLocal.phone
      this.formValidate.email = this.formLocal.email
      this.ButStatu = !this.ButStatu
    },
    getInfo () {
      this.formLocal.name = window.localStorage.getItem('name')
      this.formLocal.phone = window.localStorage.getItem('phone')
      this.formLocal.email = window.localStorage.getItem('email')
      this.formValidate.name = this.formLocal.name
      this.formValidate.phone = this.formLocal.phone
      this.formValidate.email = this.formLocal.email
    }
  }
}
</script>

<style scoped>

</style>
