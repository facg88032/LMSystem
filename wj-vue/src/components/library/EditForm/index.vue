<template src="./template.html"></template>

<script>
import ImageUpload from '../ImageUpload'
export default {
  name: 'EditForm',
  components:{ ImageUpload },
  data () {
    return {
      dialogFormVisible: false,
      form: {
        id: '',
        title: '',
        author: '',
        date: '',
        press: '',
        cover: '',
        abs: '',
        category: {
          id: '',
          name: ''
        }
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    clear () {
      this.form = {
        id: '',
        title: '',
        author: '',
        date: '',
        press: '',
        cover: '',
        abs: '',
        category: {
          id: '',
          name: ''
        }
      }
    },
    onSubmit () {
      this.$axios
          .post('/books', {
            id: this.form.id,
            cover: this.form.cover,
            title: this.form.title,
            author: this.form.author,
            date: this.form.date,
            press: this.form.press,
            abs: this.form.abs,
            category: this.form.category
          }).then(resp => {
        if (resp && resp.status === 200) {

          this.dialogFormVisible = false
          this.$emit('onSubmit')
        }
      })
    },
    uploadImg(){
      this.form.cover = this.$refs.imgUpload.url
    }


  }
}
</script>

<style scoped>
.el-icon-circle-plus {
  width: 100px;
  height: 100px;
  margin: 50px 0 0 20px;
  font-size: 100px;
  float: left;
  cursor: pointer;
}
</style>
