<template src="./template.html">

</template>

<script>
import SearchBar from '../SearchBar'

export default {
  name: 'Books',
  components: {SearchBar},
  data() {
    return {
      books: [],
      currentPage: 1,
      pageSize: 15
    }
  },
  mounted() {
    this.loadBooks()
  },
  methods: {
    loadBooks() {
      const _this = this
      this.$axios.get('/books').then(resp => {
        if (resp && resp.status === 200) {
          _this.books = resp.data.result
        }
      })
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
    },
    searchResult() {
      const _this = this
      this.$axios.get('search?keywords=' + this.$refs.searchBar.keywords).then(resp => {
        if (resp && resp.status === 200) {
          _this.books = resp.data.result
        }
      })
    },
    // deleteBook(id) {
    //   this.$confirm('此操作将永久删除该书籍, 是否继续?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //         this.$axios
    //             .post('/delete', {id: id}).then(resp => {
    //           if (resp && resp.status === 200) {
    //             this.loadBooks()
    //           }
    //         })
    //       }
    //   ).catch(() => {
    //     this.$message({
    //       type: 'info',
    //       message: '已取消删除'
    //     })
    //   })
  }
}

</script>

<style scoped>
.cover {
  width: 140px;
  height: 160px;
  margin-bottom: 7px;
  overflow: hidden;
  cursor: pointer;
}

img {
  width: 140px;
  height: 150px;
  /*margin: 0 auto;*/
}

.title {
  font-size: 14px;
  text-align: left;
}

.author {
  color: #333;
  width: 102px;
  font-size: 13px;
  margin-bottom: 6px;
  text-align: left;
}

.abstract {
  display: block;
  line-height: 17px;
}

a {
  text-decoration: none;
}

a:link, a:visited, a:focus {
  color: #3377aa;
}

</style>
