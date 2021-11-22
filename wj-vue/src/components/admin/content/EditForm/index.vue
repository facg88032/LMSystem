<template src="./template.html">

</template>

<script>
import ImgUpload from '../ImgUpload'
export default {
  name: "EditForm",
  components:{ImgUpload},
  data(){
    return {
      dialogFormVisible: false,
      form : {
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
  methods:{
    clear() {
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
          .post('/admin/content/books', {
            id: this.form.id,
            cover: this.form.cover,
            title: this.form.title,
            author: this.form.author,
            date: this.form.date,
            press: this.form.press,
            abs: this.form.abs,
            category: this.form.category
          }).then(resp => {
        if (resp && resp.data.code === 200) {
          this.dialogFormVisible = false
          this.clear()
          this.$emit('onSubmit')
        }
      }).catch(failResponse => {
        console.log(failResponse)
      })
    },
    uploadImg(){
      this.form.cover=this.$refs.imgUpload.url
    }
  }
}
</script>

<style scoped>
.add-button {
  margin: 18px 0 0 10px;
}
</style>
