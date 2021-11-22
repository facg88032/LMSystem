<template src="./template.html">

</template>

<script>
import RoleCreate from '../RoleCreate'
export default {
  name: "Role",
  components:{RoleCreate},
  data(){
    return {
      dialogFormVisible: false,
      roles:[],
      perms:[],
      menus:[],
      selectedRole:[],
      selectedPermsIds:[],
      selectedMenusIds:[],
      props: {
        id: 'id',
        label: 'nameZh',
        children: 'children'
      }
    }
  },
  mounted () {
    this.listRoles()
    this.listPerms()
    this.listMenus()
  },
  computed: {
    tableHeight () {
      return window.innerHeight - 320
    }
  },
  methods:{
    listRoles(){
      let _this = this
      this.$axios.get('/admin/role').then(resp =>{
        if(resp && resp.status === 200){
          _this.roles =resp.data.result
        }
      })
    },
    listPerms(){
      let _this = this
      this.$axios.get('/admin/role/perm').then(resp =>{
        if(resp && resp.status === 200){
          _this.perms =resp.data.result
        }
      })
    },
    listMenus(){
      let _this = this
      this.$axios.get('/admin/role/menu').then(resp =>{
        if(resp && resp.status === 200){
          _this.menus =resp.data.result
        }
      })
    },
    commitStatusChange (value, role) {
      if(role.id !==1){
        this.$confirm('是否更改角色狀態','提示',{
          confirmButtonText: '確定',
          cancelButtonText: '取消',
          type:"warning"
        }).then(()=>{
          this.$axios.put('/admin/role/status',{
            enabled: value,
            id: role.id
          }).then(resp =>{
            if(resp && resp.data.code === 200){
              if (value) {
                this.$message('角色 [' + role.nameZh + '] 已啟用')
              } else {
                this.$message('角色 [' + role.nameZh + '] 已禁用')
              }
            }
          }).catch(() => {
            role.enabled = !role.enabled
            this.$message({
              type: 'info',
              message: '已取消'
            })
          })
        })
      }else{
        role.enabled = true
        this.$alert('無法禁用系統管理員')
      }
    },
    editRole(role){
      this.dialogFormVisible = true,
          this.selectedRole = role

      let permIds = []
      for (let i = 0; i < role.perms.length; i++) {
        permIds.push(role.perms[i].id)
      }
      this.selectedPermsIds = permIds
      let menuIds = []
      for (let i = 0; i < role.menus.length; i++) {
        menuIds.push(role.menus[i].id)
        for (let j = 0; j < role.menus[i].children.length; j++) {
          menuIds.push(role.menus[i].children[j].id)
        }
      }
      this.selectedMenusIds = menuIds

      if (this.$refs.tree) {
        this.$refs.tree.setCheckedKeys(menuIds)
      }
    },
    onSubmit(role){
      let _this = this
      let perms = []
      for (let i = 0; i < _this.selectedPermsIds.length; i++) {
        for (let j = 0; j < _this.perms.length; j++) {
          if(_this.selectedPermsIds[i] === _this.perms[j].id){
            perms.push(_this.perms[j])
          }
        }
      }
      this.$axios.put('/admin/role',{
        id: role.id,
        name: role.name,
        nameZh : role.nameZh,
        enabled: role.enabled,
        perms: perms
      }).then(resp =>{
        if(resp && resp.data.code === 200){
          this.$alert(resp.data.result)
          this.dialogFormVisible = false
          this.listRoles()
        }
      })
      console.log(this.$refs.tree.getCheckedKeys())
      this.$axios.put('/admin/role/menu?rid=' + role.id, {
        menusIds: this.$refs.tree.getCheckedKeys()
      }).then(resp => {
        if (resp && resp.data.code === 200) {
          console.log(resp.data.result)
        }
      })
    },
    deleteRole(id){
      this.$confirm('將永久刪除此角色,是否繼續','提示',{
        confirmButtonText: '確定',
        cancelButtonText: '取消',
        type: "warning"
      }).then(()=>{
        this.$axios.post('/admin/role/delete',{id:id}).then(resp =>{
          if(resp && resp.data.code === 200){
            this.listRoles();
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