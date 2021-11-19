<template src="./template.html">

</template>

<script>
import {
  HomeFilled,User,Tickets,Tools
} from '@element-plus/icons';

export default {
  name: "AdminMenu",
  data() {
    return {
      isCollapse: false
    }
  },
  computed: {
    adminMenus() {
      return this.$store.state.adminMenus
    },
    currentPath() {
      return this.$route.path
    }
  },
  components: {
    HomeFilled,
    User,
    Tickets,
    Tools
  }

}
</script>

<style scoped>
.el-menu-admin {
  border-radius: 5px;
  height: 100%;
}
</style>
