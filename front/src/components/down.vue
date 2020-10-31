<template>
  <div >
    <div>
        <van-button type="" @click="goOff">返回</van-button>
    </div>
    <div id="text"></div>

  </div>
</template>

<script>
    export default {
        name: "down",
      data(){
        return{
          content: '',
          docName:'',
        }
      },
      methods:{
          goOff(){
              if (window.history.length <= 1) {
                  this.$router.push({path:'/'})
                  return false
              } else {
                  this.$router.go(-1)
              }
          },
      },
      mounted() {
        this.content = this.$route.query.value;
        this.docName=this.$route.query.docName;
        document.getElementById("text").innerHTML = this.content;
        $("#text").wordExport(this.docName.substring(0,this.docName.length-4));
      }
    }
</script>

<style scoped>

</style>
