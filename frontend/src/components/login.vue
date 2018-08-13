<template>
  <div>
    <Card class="card">
      <div style="margin-bottom: 20px">
        <span>登录</span>
      </div>
      <i-form :model="formInline" :rules="ruleInline" :label-width="80">
        <Form-item prop="user" label="账号">
          <i-input type="text" :value.sync="formInline.user" placeholder="Username">
          </i-input>
        </Form-item>
        <Form-item prop="password" label="密码">
          <i-input type="password" :value.sync="formInline.password" placeholder="Password" >
          </i-input>
        </Form-item>
        <Form-item>
          <i-button type="primary" @click="handleSubmit('formInline')">登录</i-button>
        </Form-item>
      </i-form>
    </Card>
  </div>
</template>
<script>
  import http from './http'
  export default {
    data () {
      return {
        formInline: {
          user: 'admin',
          password: 'admin'
        },
        ruleInline: {
          user: [
            { required: true, message: '请填写用户名', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请填写密码', trigger: 'blur' },
            { type: 'string', min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      handleSubmit(name) {

          // this.$refs[name].validate((valid) => {
          //   if (valid) {

              http.post('login',{username: this.formInline.user,password: this.formInline.password})
                .then((res)=>{
                  console.log(res)
                })

              this.$Message.success('登录成功!');
              this.$router.push('/index');

            // } else {
            //   this.$Message.error('表单验证失败!');
            // }
          // })
        }
      }
  }
</script>

<style scoped>
  .card {
    text-align: center;
    width: 500px;
    margin: auto;
    padding-top: 20px;
    padding-right: 80px;
  }
</style>
