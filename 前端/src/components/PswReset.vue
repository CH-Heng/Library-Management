<template>
  <Form ref="formValidate" :model="formPsw" :rules="ruleValidate" :label-width="80">
    <FormItem label="旧密码" prop="oldpsw">
      <Input v-model="formPsw.oldpsw"  placeholder="请输入旧密码"></Input>
    </FormItem>
    <FormItem label="新密码" prop="newpsw">
      <Input v-model="formPsw.newpsw"  placeholder="请输入新密码"></Input>
    </FormItem>
    <FormItem label="确认" prop="confirmpsw">
      <Input v-model="formPsw.confirmpsw" placeholder="请再次确认新密码"></Input>
    </FormItem>
    <FormItem>
      <Button type="primary" @click="handleSubmit('formValidate')">保存修改</Button>
    </FormItem>
  </Form>
</template>

<script>
  export default {
    name: 'PswReset',
    data () {
      return {
        ButStatu: true,
        formPsw: {
          oldpsw: '',
          newpsw: '',
          confirmpsw: ''
        },
        ruleValidate: {
          oldpsw: [
            { required: true, message: '旧密码不能为空', trigger: 'blur' }
          ],
          newpsw: [
            { required: true, message: '新密码不能为空', trigger: 'blur' }
          ],
          confirmpsw: [
            { required: true, message: '确认密码不能为空', trigger: 'blur' }
          ]

        }
      }
    },
    methods: {
      handleSubmit (name) {
        var that = this
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.$Message.success('表单验证成功!')
            let _account = window.localStorage.getItem('account')

            // 这里改接口
            that.$http.post(that.GLOBAL.serverPath + '/excise/modifypassword',
              {
                account: _account,
                oldpassword: that.formPsw.oldpsw,
                newpassword: that.formPsw.newpsw,
                confirmpassword: that.formPsw.confirmpsw
              },
              {
                emulateJSON: true
              }
            ).then(function (res) {

              if (res.data.result === 'yes') {
                this.$Message.success('修改成功!')
              } else {
                this.$Message.error('服务器繁忙请稍后再试!')
              }
            })
          } else {
            this.$Message.error('表单验证失败!')
          }
        })
      },
      editOn () {
        this.ButStatu = !this.ButStatu
      }
    }
  }
</script>

<style scoped>

</style>
