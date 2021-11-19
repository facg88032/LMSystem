<template src="./template.html">

</template>

<script>
export default {
  name: "index",
  data() {
    return {
      sideList: [
        {id: '0', class: '全部'},
        {id: '1', class: '文學'},
        {id: '2', class: '流行'},
        {id: '3', class: '文化'},
        {id: '4', class: '生活'},
        {id: '5', class: '經營'},
        {id: '6', class: '科技'}
      ],
      cid: '0'
    }
  },
  methods:{
    handleSelect (key) {
      this.cid = key
      this.$emit('indexSelect')
    }
  }
}
</script>

<style scoped>

</style>
