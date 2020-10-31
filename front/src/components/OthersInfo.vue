<template>
    <div v-if="showPage">
        <div class="background">
            <img :src="imgSrc" width="100%" height="100%" alt="" />
        </div>
        <van-sticky>
            <div class="up" style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)" >
              <div style="padding:15px 0;">
                <van-row>
                  <van-col span="4">
                      <span style="font-size:21px;">金刚石文档</span>
                  </van-col>
                  <van-col span="12">
                      <span style="font-size:25px;color:green;"></span>
                  </van-col>
                  <van-col span="4">

                  </van-col>
                  <van-col span=2>
                      <i class="el-icon-bell" style="font-size:30px;"></i>
                  </van-col>
                  <van-col span="2">
                      <el-dropdown @command="onCommand">
                        <span class="el-dropdown-link">
                          <van-image
                          round
                          width="30px"
                          height="30px"
                          fit="cover"
                          :src="userhead"/><i class="el-icon-arrow-down el-icon--right"></i>
                        </span>

                        <el-dropdown-menu slot="dropdown">
                          <el-dropdown-item icon="el-icon-user" @click.native="toMyinfo">个人信息</el-dropdown-item>
                          <el-dropdown-item icon="el-icon-warning-outline"  @click.native="toLogin"><span style="color:red;">退出账号</span></el-dropdown-item>
                          <el-dropdown-item icon="el-icon-circle-plus-outline">帮助</el-dropdown-item>

                        </el-dropdown-menu>

                      </el-dropdown>
                  </van-col>
                </van-row>
              </div>
            </div>
        </van-sticky>

        <div style="margin-top:50px;">
          <van-row>
            <van-col span="6"></van-col>
            <van-col span="7">
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/Home' }">返回</el-breadcrumb-item>
                <el-breadcrumb-item><a href="#/OthersInfo">{{othersName}}的信息</a></el-breadcrumb-item>
              </el-breadcrumb>
            </van-col>
            <van-col span="7"></van-col>
          </van-row>
        </div>

        <br/>

        <div class="div1" style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2)" >
            <van-row>
                <van-col span="6" style="margin-top:120px;">
                    <van-image
                    round
                    width="110px"
                    height="110px"
                    fit="cover"
                    :src="othersHead"/>
                    <br/><br/>
                    <p>{{othersName}}的信息</p>
              </van-col>

              <van-col span="2">
                <el-divider direction="vertical"></el-divider>
              </van-col>

              <van-col span="13" style="margin-top:220px;">

                  <van-row>
                    <van-col span="20">
                    <el-input v-model="this.othersId" :disabled="true" size="large"
                          prefix-icon="el-icon-message">
                      <template slot="prepend">邮箱</template>
                    </el-input>
                    </van-col>
                    <van-col span="2"></van-col>
                    <van-col span="2"></van-col>
                  </van-row>

                  <br/><br/>

                  <van-row>
                    <van-col span="20">
                    <el-input v-model="this.othersName" :disabled="true" size="large"
                              prefix-icon="el-icon-user">
                      <template slot="prepend">用户名</template>
                    </el-input>
                    </van-col>
                    <van-col span="2"></van-col>
                    <van-col span="2"></van-col>
                  </van-row>
              </van-col>
            </van-row>
                <br/><br/>

        </div>

      <br/><br/><br/><br/>
    </div>
</template>

<script>
    import {Toast} from "vant";
    import axios from 'axios';
    import baseUrl from "./baseUrl";

    export default{
        name: "OthersInfo",
        data(){
            return{
                myemail:localStorage.getItem('myemail'),
                username:'youabcd',  // 用户名

                imgSrc:require('../assets/loginback.jpg'),
                userhead:"https://img.yzcdn.cn/vant/cat.jpeg",
                useremail:'1322496098@qq.com',  // 新邮箱
                newname: '',


                showPage: true,

                userpassword:'*****************',
                changepassword:false,
                changename:false,
                changeemail: false,

                //别人的部分信息
                othersName:'123',
                othersHead:'https://img.yzcdn.cn/vant/cat.jpeg',
                othersId:'321',

            }
        },
        methods:{
            onCommand(command){
            },
            toMyinfo(){
                this.$router.push({
                    path:'/OthersInfo',
                });
            },
            toLogin(){
                localStorage.setItem('myemail','');
                this.$router.push({
                    path:'/',
                });
            },

          refreshPage(){ // 不闪屏的刷新
            this.showPage = false;
            let _this = this;
            this.$nextTick(function () {
              _this.showPage = true;
            })
          }
        },
      mounted() {
        let _this=this;
        let data = new FormData();
        data.append('userId',this.othersId);
        axios.post(baseUrl+'/showUserInfoPage',data)
        .then(function (response) {
          if(response.data.success === true) {
            _this.othersName = response.data.userName;
            _this.othersHead = response.data.userImage;
          }
        })
        .catch(function (err) {
        })
        data = new FormData();
        data.append('userId',this.myemail);
        axios.post(baseUrl+'/showUserInfoPage',data)
          .then(function (response) {
            if(response.data.success === true) {
              _this.username = response.data.userName;
              _this.userhead = response.data.userImage;
            }
          })
          .catch(function (err) {
          })
      },
      created(){
          this.othersId=this.$route.query.othersId;
      }
    }
</script>

<style>
.up{
  background:white;
  width:100%;
  height:55px;

}
.background{
    width:100%;
    height:100%;  /**宽高100%是为了图片铺满屏幕 */
    z-index:-1;
    position: fixed;
}
.div1{
  background:white;
  width:800px;
  height:550px;
  margin:0 auto;
}
.el-divider{
  height:400px;

}
</style>
