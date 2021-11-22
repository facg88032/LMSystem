<template src="./template.html">

</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      rules: {
        username: [{required: true, message: '用戶名不能為空', trigger: 'blur'}],
        password: [{required: true, message: '密碼不能為空', trigger: 'blur'}]
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
          this.$alert('註冊成功', '提示', {
            confirmButtonText: '確定'
          })
          _this.$router.replace('/login')
        } else {
          this.$alert(resp.data.message, '提示', {
            confirmButtonText: '確定'
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
