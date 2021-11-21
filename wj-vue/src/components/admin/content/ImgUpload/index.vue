<template src="./template.html">

</template>

<script>
export default {
  name: 'ImgUpload',
  data() {
    return {
      fileList: [],
      url: ''
    }
  },
  methods: {
    beforeUpload(file) {
      const Filetypes = ['image/jpeg', 'image/jpg', 'image/gif', 'image/png']
      const isJPG = Filetypes.includes(file.type)
      const isLtSize = file.size / 1024 / 1024 < 5;
      if (!isJPG) {
        this.$message.error(
            '只能是 JPG、JPEG、GIF或PNG 格式!'
        );
      }
      if (!isLtSize) {
        this.$message.error('圖檔不能超過5M');
      }
      return isJPG && isLtSize;
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    },
    handleExceed(files, fileList) {
      this.$message.warning(`當前限制選擇 1 張圖檔，本次選擇 ${files.length} 張圖檔，共選擇${files.length + fileList.length} 張圖檔`)
    },
    beforeRemove(file, fileList) {
      console.log(fileList)
      return this.$confirm(`確定移除 ${file.name}?`)
    },
    handleSuccess(response) {
      this.url = response.result
      this.$emit('onUpload')
      this.$message.warning('上傳成功')
    },
    clear() {
      this.$refs.upload.clearFiles()
    }

  }

}
</script>

<style scoped>

</style>
