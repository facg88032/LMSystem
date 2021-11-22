<template src="./template.html">

</template>

<script>
export default {
  name: "RoleCreate",
  data(){
    return {
      dialogFormVisible: false,
      rules: {
        name: [{required: true, message: '角色名不能為空', trigger: 'blur'}],
      },
      roleForm: {
        name: '',
        nameZh: ''
      },
      formLabelWidth:'100px'
    }
  },
  methods: {
    clear() {
      this.roleForm = {
        name: '',
        nameZh: ''
      }
    },
    onSubmit(){
      this.$axios.post("/admin/role",{
        name:this.roleForm.name,
        nameZh:this.roleForm.nameZh
      }).then(resp => {
        if(resp && resp.data.code == 200){
          this.$alert('新增成功','提示',{
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