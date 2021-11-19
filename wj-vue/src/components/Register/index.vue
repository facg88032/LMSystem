<template src="./template.html">

</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      rules: {
        username: [{required: true, message: '用户名不能为空', trigger: 'blur'}],
        password: [{required: true, message: '密码不能为空', trigger: 'blur'}]
      },
      registerForm:{
        username: '',
        password: '',
      },
      responseResult: [],
      loading: false
    }
  },
  methods:{
    register(){
      const _this = this
      this.$axios
          .post('/register',{
            username: this.registerForm.username,
            password: this.registerForm.password
          }).then(resp =>{
        if (resp.data.code === 200) {
          this.$alert('注册成功', '提示', {
            confirmButtonText: '确定'
          })
          _this.$router.replace('/login')
        } else {
          this.$alert(resp.data.message, '提示', {
            confirmButtonText: '确定'
          })
        }
          }
      ).catch(failResp => {
        console.log(failResp)
      })
    }
  }


}
</script>

<style src="./style.css" scoped>


</style>
