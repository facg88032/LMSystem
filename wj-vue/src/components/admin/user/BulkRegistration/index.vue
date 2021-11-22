<template src="./template.html">

</template>

<script>
export default {
  name: "BulkRegistration",
  data(){
    return{
      dialogFormVisible:false,
      rules: {
        username: [{required: true, message: '用戶名不能為空', trigger: 'blur'}],
        password: [{required: true, message: '密碼不能為空', trigger: 'blur'}]
      },
      userForm:{
        username:'',
        password:'',
        name:'',
        phone:'',
        email:''
      },
      formLabelWidth:'100px'
    }
  },
  methods: {
    clear() {
      this.userForm = {
        username: '',
        password: '',
        name: '',
        phone: '',
        email: ''
      }
    },
    onSubmit(){
      this.$axios.post("/register",{
        username:this.userForm.username,
        password:this.userForm.password,
        name:this.userForm.name,
        phone:this.userForm.phone,
        email:this.userForm.email
      }).then(resp => {
        if(resp && resp.data.code == 200){
          this.$alert('註冊成功','提示',{
            confirmButtonText:'確定'
          })
          this.dialogFormVisible = false
          this.clear()
          this.$emit('onSubmit')
        }else{
          this.$alert(resp.data.message,'提示',{
            confirmButtonText:'確定'
          })
        }
      }).catch(failResponse => {
        console.log(failResponse)
      })
    }
  }

}
</script>

<style scoped>
.add-button {
  margin: 18px 0 0 10px;
}
</style>