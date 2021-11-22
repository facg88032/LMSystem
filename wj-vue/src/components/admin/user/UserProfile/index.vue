<template src="./template.html">

</template>

<script>
import BulkRegistration from '../BulkRegistration'
export default {
  name: 'UserProfile',
  components: {BulkRegistration},
  data() {
    return {
      users: [],
      roles: [],
      dialogFormVisible: false,
      selectedUser: [],
      selectedRolesIds: []
    }
  },
  mounted() {
    this.listUsers()
    this.listRoles()
  },
  computed: {
    tableHeight() {
      return window.innerHeight - 320
    }
  },
  methods: {
    listUsers() {
      const _this = this
      this.$axios.get('/admin/user').then(resp => {
        if (resp && resp.data.code === 200) {
          _this.users = resp.data.result
        }
      })
    },
    listRoles() {
      const _this = this
      this.$axios.get('/admin/role').then(resp => {
        if (resp && resp.data.code === 200) {
          _this.roles = resp.data.result
        }
      })
    },
    commitStatusChange(value, user) {
      if (user.username !== 'admin') {
        this.$axios.put('/admin/user/status', {
          enabled: value,
          username: user.username
        }).then(resp => {
          if (resp && resp.data.code === 200) {
            if (value) {
              this.$message('用户 [' + user.username + '] 已啟用')
            } else {
              this.$message('用户 [' + user.username + '] 已禁用')
            }
          }
        })
      } else {
        user.enabled = true
        this.$alert('不能禁用管理員帳戶')
      }
    },
    onSubmit(user) {
      let _this = this
      let roles = []
      for (let i = 0; i < _this.selectedRolesIds.length; i++) {
        for (let j = 0; j < _this.roles.length; j++) {
          if (_this.selectedRolesIds[i] === _this.roles[j].id) {
            roles.push(_this.roles[j])
          }
        }
      }

      this.$axios.put('/admin/user', {
        username: user.username,
        name: user.name,
        phone: user.phone,
        email: user.email,
        roles: roles
      }).then(resp => {
        if (resp && resp.status === 200) {
          this.$alert('用戶訊息更改成功')
          this.dialogFormVisible = false

          this.listUsers()
        }
      })

    },
    editUser(user) {
      this.dialogFormVisible = true
      this.selectedUser = user
      let roleIds = []
      for (let i = 0; i < user.roles.length; i++) {
        roleIds.push(user.roles[i].id)
      }
      this.selectedRolesIds = roleIds
    },
    resetPassword(username) {
      this.$axios.put('/admin/user/password', {
        username: username
      }).then(resp => {
        if (resp && resp.data.code === 200) {
          this.$alert('密碼已經重製為 123')
        }
      })
    },
    deleteUser(id){
      this.$confirm('將永久刪除此用戶,是否繼續','提示',{
        confirmButtonText: '確定',
        cancelButtonText: '取消',
        type: "warning"
      }).then(()=>{
        this.$axios.post('/admin/user/delete',{id:id}).then(resp =>{
          if(resp && resp.data.code === 200){
            this.listUsers();
          }
        }).catch(()=>{
          this.$message({
            type: 'info',
            message: '已取消刪除'
          })
        })
      })
    }
  }
}
</script>

<style scoped>

</style>