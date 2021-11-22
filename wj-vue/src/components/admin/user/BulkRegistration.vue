<template>
  <div style="text-align: left">
    <el-button class="add-button" type="success" @click="dialogFormVisible = true">新增用戶</el-button>
    <el-dialog
        title="新增用戶"
        v-model="dialogFormVisible"
        @close="clear"
        width="25%">
      <el-form :model="userForm" :rules="rules" style="text-align: left">
        <el-form-item label="用戶名" :label-width="formLabelWidth" prop="username">
          <el-input v-model="userForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密碼" :label-width="formLabelWidth" prop="password">
          <el-input v-model="userForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="真實姓名" :label-width="formLabelWidth" prop="name">
          <el-input v-model="userForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手機" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="userForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="E-mail" :label-width="formLabelWidth" prop="email">
          <el-input v-model="userForm.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click="onSubmit">確定</el-button>
          </span>
      </template>
    </el-dialog>
  </div>
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