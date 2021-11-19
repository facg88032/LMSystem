<template src="./template.html"></template>

<script>
export default {
  name: 'NavMenu',
  data() {
    return {
      navList: [
        {name: '/index', navItem: '首頁'},
        {name: '/jotter', navItem: '筆記本'},
        {name: '/library', navItem: '圖書館'},
        {name: '/admin', navItem: '個人中心'}
      ]
    }
  },
  // methods:{
  //   logout () {
  //     const _this = this
  //     this.$axios.get('/logout').then(resp => {
  //       if (resp.data.code === 200) {
  //         _this.$store.commit('logout')
  //         _this.$router.replace('/login')
  //       }
  //     })
  //   }
  //
  // }

}
</script>

<style scoped>
a {
  text-decoration: none;
}

span {
  pointer-events: none;
}

</style>
