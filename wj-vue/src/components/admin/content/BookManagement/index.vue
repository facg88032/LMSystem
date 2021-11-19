<template src="./template.html">

</template>

<script>
import EditForm from '../EditForm'
export default {
  name: "BookManagement",
  components: {EditForm},
  data() {
    return {
      books: [],
      multipleSelection: []
    }
  },
  computed: {
    tableHeight() {
      return window.innerHeight - 320
    }
  },
  mounted() {
    this.loadBooks()
  },
  methods: {
    loadBooks() {
      let _this = this
      this.$axios.get('/books').then(resp => {
        if (resp && resp.status === 200) {
          _this.books = resp.data.result
        }
      })
    },
    editBook(item){
      this.$refs.edit.dialogFormVisible = true
      this.$refs.edit.form = {
        id: item.id,
        cover: item.cover,
        title: item.title,
        author: item.author,
        date: item.date,
        press: item.press,
        abs: item.abs,
        category: {
          id: item.category.id.toString(),
          name: item.category.name
        }
      }
    },
    deleteBook(id) {
      this.$confirm('將永久刪除此書籍,是否繼續?', '提示', {
        confirmButtonText: '確定',
        cancelButtonText: '取消',
        type: "warning"
      }).then(() => {
        this.$axios.post('/admin/content/books/delete', {id: id}).then(resp => {
          if (resp && resp.status === 200) {
            this.loadBooks();
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消刪除'
          })
        })
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    }

  }

}
</script>

<style scoped>

</style>
