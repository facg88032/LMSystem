<template src="./template.html">

</template>

<script>
export default {
  name: 'ImageUpload',
  data () {
    return {
      fileList: [],
      url: ''
    }
  },
  methods: {
    handleRemove (file, fileList) {
      console.log(file,fileList)
    },
    handlePreview (file) {
      console.log(file)
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove (file, fileList) {
      console.log(fileList)
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    handleSuccess (response,file) {
      console.log(file.size)
      this.url = response
      this.$emit('onUpload')
      this.$message.warning('上传成功')
    },
    clear () {
      this.$refs.upload.clearFiles()
    }

  }

}
</script>

<style scoped>

</style>
