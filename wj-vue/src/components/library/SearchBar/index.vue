<template src="./template.html"></template>

<script>
export default {
  name: "SearchBar",
  data(){
    return{
      keywords:''
    }
  },
  methods:{
    searchClick () {
      this.$emit('onSearch')
    }
  }
}
</script>

<style scoped>

</style>
