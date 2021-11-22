<template>
  <div style="text-align: left">
    <el-button class="add-button" type="success" @click="dialogFormVisible = true">新增角色</el-button>
    <el-dialog
        title="新增用戶"
        v-model="dialogFormVisible"
        @close="clear"
        width="25%">
      <el-form :model="roleForm" :rules="rules" style="text-align: left">
        <el-form-item label="用戶名" :label-width="formLabelWidth" prop="name">
          <el-input v-model="roleForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密碼" :label-width="formLabelWidth" prop="nameZh">
          <el-input v-model="roleForm.nameZh" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item >
          <el-tag>初始權限:無</el-tag>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click="onSubmit">確定</el-button>
          </span>
      </template>
    </el-dialog>
  </div>>
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