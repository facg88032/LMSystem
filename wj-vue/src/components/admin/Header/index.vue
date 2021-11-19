<template src="./template.html">

</template>

<script>
export default {
  name: "Header",
  methods:{
    logout () {
      const _this = this
      this.$axios.get('/logout').then(resp => {
        if (resp && resp.data.code === 200) {
          _this.$store.commit('logout')
          _this.$router.replace('/index')
          // // 清空路由，防止路由重复加载
          // const newRouter = createRouter()
          // _this.$router.matcher = newRouter.matcher
        }
      }).catch(failResponse => {
        console.log(failResponse)
      })
    }
  }
}
</script>

<style scoped>
.admin-header {
  height: 80px;
  opacity: 0.85;
  line-height: 40px;
  min-width: 900px;
}
.el-icon-switch-button {
  cursor: pointer;
  outline:0;
}
</style>
