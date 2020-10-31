<template>
<div class="helloworld">
    
    <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
    </div>
</br></br>
    <van-row style="">
      <van-col span="4"></van-col>
      <van-col span="16">
        <div class="div1" style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.9)" >
          <van-row>
            <van-col span="2"></van-col>
            <van-col span="20">
            <br/><br/>
            <h2 style="color: darkgrey">用户登录</h2><br/><br/>
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="活动名称">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="活动区域">
            <el-select v-model="form.region" placeholder="请选择活动区域">
              <el-option label="区域一" value="shanghai"></el-option>
              <el-option label="区域二" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="活动时间">
            <el-col :span="11">
              <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" style="width: 100%;"></el-date-picker>
            </el-col>
            <el-col class="line" :span="2">-</el-col>
            <el-col :span="11">
              <el-time-picker placeholder="选择时间" v-model="form.date2" style="width: 100%;"></el-time-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="即时配送">
            <el-switch v-model="form.delivery"></el-switch>
          </el-form-item>
          <el-form-item label="活动性质">
            <el-checkbox-group v-model="form.type">
              <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
              <el-checkbox label="地推活动" name="type"></el-checkbox>
              <el-checkbox label="线下主题活动" name="type"></el-checkbox>
              <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="特殊资源">
            <el-radio-group v-model="form.resource">
              <el-radio label="线上品牌商赞助"></el-radio>
              <el-radio label="线下场地免费"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="活动形式">
            <el-input type="textarea" v-model="form.desc"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">立即创建</el-button>
            <el-button @click="topic">取消</el-button>
          </el-form-item>
        </el-form>
            </van-col>

            <van-col span="2"></van-col>

          </van-row>
        </div>
      </van-col>

      <van-col span="4"></van-col>

    </van-row>

  <br/><br/><br/><br/>

</div>
</template>

<script>
    export default {
      name: "Login",
      data() {
        return {
          imgSrc:require('../assets/s1.jpg'),
          form: {
            name: '',
            region: '',
            date1: '',
            date2: '',
            delivery: false,
            type: [],
            resource: '',
            desc: '',
            
          }
        }
      },
      methods: {
        onSubmit() {
          console.log('submit!');
          let _this=this;
          axios.get(baseUrl+'/login',{//给后端传值
            params:{
              info:_this.form,
            }
          })
          .then(function(response){//从后端取值
            console.log(response);
            _this.form=info;
            this.$router.push({
              path:'/',
            });
          })
        },
        topic(){
            this.$router.push({
                path:'/pic',
            })
        },
      }

    }
</script>

<style scoped>
.background{
    width:100%;  
    height:100%;  /**宽高100%是为了图片铺满屏幕 */
    z-index:-1;
    position: fixed;
}
.front{
    z-index:1;
    position: absolute;
}
.div1{
  background:white;
}

</style>
