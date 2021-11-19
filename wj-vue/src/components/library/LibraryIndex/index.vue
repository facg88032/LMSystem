<template src="./template.html">

</template>

<script>
import SideMenu from '../SideMenu'
import Books from '../Books'

export default {
  name: "AppLibrary",
  components: {
    SideMenu,
    Books
  },
  methods:{
    listByCategory(){
      const _this = this
      this.$axios('/categories/'+this.$refs.sideMenu.cid+'/books').then(resp =>{
        if (resp && resp.status === 200) {
            _this.$refs.booksArea.books=resp.data.result
        }
      })
    }
  }
}
</script>

<style scoped>
.books-area {
  width: 990px;
  margin-left: auto;
  margin-right: auto;
}
</style>
