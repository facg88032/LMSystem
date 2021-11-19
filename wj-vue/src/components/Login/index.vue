<template src="./template.html">

</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
      },
      // image: require('@/assets/logo.png'),
      responseResult: []
    }
  },
  methods: {
    login() {
      this.$axios
          .post('/login', {
            username: this.loginForm.username,
            password: this.loginForm.password
          }).then(resp => {

            if (resp.data.code == 200) {
              let data = resp.data.result
              this.$store.commit('login', data)
              let path = this.$route.query.redirect
              this.$router.replace({path: path === '/' || path === undefined ? '/index' : path})
            }else {
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

<style src="./style.css">

</style>
