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
            <van-col span="4">
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/Home' }">主页</el-breadcrumb-item>
                <el-breadcrumb-item><a href="#/Myinfo">个人信息</a></el-breadcrumb-item>
              </el-breadcrumb>
            </van-col>
            <van-col span="14"></van-col>
          </van-row>
        </div>

        <br/>


        <div class="div1" style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2)" >
            <van-row>
              <van-col span="6" style="margin-top:120px;">

                  <el-tooltip class="item" effect="dark" content="点击修改头像" placement="right">
                    <van-image
                    round
                    width="110px"
                    height="110px"
                    fit="cover"
                    clickable
                    @click="showChangeHead"
                    :src="userhead"/>
                  </el-tooltip>

                  <br/><br/>
                    <div>个人信息</div>
              </van-col>
              <van-col span="2">
                <el-divider direction="vertical"></el-divider>
              </van-col>
              <van-col span="13" style="margin-top:220px;">

                <van-row>
                  <van-col span="20">
                    <el-input v-model="this.myemail" :disabled="true" size="large"
                          prefix-icon="el-icon-message">
                      <template slot="prepend">邮箱</template>
                    </el-input>
                  </van-col>
                  <van-col span="2"></van-col>
                  <van-col span="2">
                    <el-button @click="change(1)">修改</el-button>
                  </van-col>
                </van-row>

                <br/><br/>

                <van-row>
                  <van-col span="20">
                    <el-input v-model="this.username" :disabled="true" size="large"
                              prefix-icon="el-icon-user">
                      <template slot="prepend">用户名</template>
                    </el-input>
                  </van-col>
                  <van-col span="2"></van-col>
                  <van-col span="2">
                    <el-button @click="change(2)">修改</el-button>
                  </van-col>
                </van-row>

                <br/><br/>

                <van-row>
                  <van-col span="20">
                    <el-input v-model="this.userpassword" :disabled="true" size="large"
                              prefix-icon="el-icon-lock">
                      <template slot="prepend">密码</template>
                    </el-input>
                  </van-col>
                  <van-col span="2"></van-col>
                  <van-col span="2">
                    <el-button @click="change(3)">修改</el-button>
                  </van-col>
                </van-row>

              </van-col>
              <van-col span="3"></van-col>
            </van-row>



        </div>

        <van-dialog v-model="changepassword" title="修改密码" show-cancel-button @confirm="onConfirm(3)">
            <van-field
            v-model="password1"
            type="password"
            name="原密码"
            label="原密码"
            placeholder="请输入原密码"
            :rules="[{ required: true, message: '请填写密码' }]">
                <template #button>
                    <van-button size="small" type="info" @click="tofind">忘记密码?</van-button>
                </template>
            </van-field>

            <van-field
            v-model="password2"
            type="password"
            name="新密码"
            label="新密码"
            placeholder="请输入新密码"
            :rules="[{ required: true, message: '请填写密码' }]"/>

            <van-field
            v-model="password3"
            type="password"
            name="确认新密码"
            label="确认新密码"
            placeholder="再次输入新密码"
            :rules="[{ required: true, message: '请填写密码' }]"/>
        </van-dialog>

        <van-dialog v-model="changename" title="修改用户名" show-cancel-button @confirm="onConfirm(2)">
            <van-field
            v-model="newname"
            type="text"
            name="用户名"
            label="用户名"
            :rules="[{ required: true, message: '用户名不能为空' }]"/>
        </van-dialog>

      <!--修改邮箱的对话框-->
        <van-dialog v-model="changeemail" title="修改邮箱" show-cancel-button @confirm="onConfirm(1)">
            <van-field
            v-model="useremail"
            type="email"
            name="新邮箱"
            label="新邮箱"
            placeholder="请输入新邮箱"
            :rules="[{ required: true, message: '请填写邮箱' }]">
            </van-field>

            <van-field
            v-model="idcode"
            name="验证码"
            label="验证码"
            placeholder="请输入验证码"
            :rules="[{ required: true, message: '请填写验证码' }]">
              <template #button>
                <van-button size="small" type="primary" @click="submitIdCode()" :disabled="disabled">{{timeContent}}</van-button>
              </template>
            </van-field>

        </van-dialog>

      <br/><br/><br/><br/>

<!--修改头像页面-->
    <van-popup v-model="popChangeHead" closeable close-icon="close" style="height:350px;width:400px;">
      <div style="margin-top:60px;">
        <van-uploader v-model="headList" multiple :max-count="1" preview-size="150px" upload-text="点击即可上传" image-fit="cover"/>
      </div>
      <div style="margin-top:20px;">
          <van-button type="" @click="submitNewHead">提交修改</van-button>
      </div>

    </van-popup>


    </div>
</template>

<script>
    import {Toast} from "vant";
    import axios from 'axios';
    import baseUrl from "./baseUrl";

    export default{
        name: "Home",
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
                timeContent: '发送验证码',

                password1:'',  // 原密码
                password2:'',  // 新密码
                password3:'',  // 新密码

                idcode: '', // 用户输入的验证码
                subidcode: '',

                //修改头像
                popChangeHead:false,
                headList: [],
            }
        },
        methods:{

        //修改头像
            showChangeHead(){
                this.popChangeHead=true;
            },

            submitNewHead(){//上传headList[0]
              let _this = this;
              let data = new FormData();
              data.append('userId',this.myemail);
              data.append('image',this.headList[0].content);
              axios.post(baseUrl+'/uploadUserHeadImage',data)
              .then(function (response) {
                if(response.data.success){
                  _this.popChangeHead = false;
                  _this.loadData();
                }
              })
            },


            onCommand(command){
            },
            toMyinfo(){
                this.$router.push({
                    path:'/Myinfo',
                });
            },
            toLogin(){
                localStorage.setItem('myemail','');
                this.$router.push({
                    path:'/',
                });
            },
            tofind(){
                this.$router.push({
                    path:'/FindPassword',
                });
            },
            change(type){ // type 1:邮箱 2:用户名 3:密码
              if(type === 1)
                this.changeemail = true;
              else if(type === 2)
                this.changename=true;
              else
                this.changepassword=true;
            },
            onConfirm(index){//需上传数据1 邮箱 2 用户名 3 密码
              console.log('confirm');
              let _this = this;
              let data = new FormData();
              data.append('type',index);
              data.append('userId',this.myemail);
              if(index==1){
                if(this.idcode !== this.subidcode){
                  Toast('验证码不正确！');
                  return;
                }
                data.append('newValue',this.useremail);
              }
              else if(index==2){
                data.append('newValue',this.newname);
              }
              else{
                if(this.password2!==this.password3){
                  Toast('两次密码不一样！');
                  return;
                }
                data.append('password',this.password1);
                data.append('newValue',this.password2);
              }
              console.log('submit');
              axios.post(baseUrl+'/changeUserInfo',data)
                .then(function (response) {
                  if(response.data.success === true){
                    Toast('修改成功');
                    if(index == 1) {
                      localStorage.removeItem('myemail');
                      localStorage.setItem('myemail', _this.useremail);
                    }
                    else if(index==2){
                      _this.username = _this.newname;
                    }
                    _this.refreshPage();
                  }
                  else{
                    Toast('修改失败' + response.data.message);
                  }
                })
                .catch(function (err) {
                })
            },
            submitIdCode() {
                var _this = this;
                if(this.timeContent=='发送验证码'){
                  let time=59;
                  let timer=setInterval(()=>{
                    if(time>0){
                        this.timeContent=time+'s';
                        this.disabled=true;
                        time--;
                    }
                    else{
                        window.clearInterval(timer);
                        this.disabled=false;
                        this.timeContent='发送验证码';
                    }
                  },1000);
                }
                let data = new FormData();
                data.append('userId',this.useremail);
                axios.post(baseUrl+'/sendEmail',data)
                .then(function (response){
                  Toast(response.data.message);
                  console.log(response);
                  _this.subidcode = response.data.result;
                })
                .catch(function (error) { // 请求失败处理
                  console.log(error);
                });
            },
          refreshPage(){ // 不闪屏的刷新
            this.showPage = false;
            let _this = this;
            this.$nextTick(function () {
              _this.showPage = true;
            })
          },
          loadData(){
            let _this=this;
            let data = new FormData();
            data.append('userId',this.myemail);
            axios.post(baseUrl+'/showUserInfoPage',data)
              .then(function (response) {
                if(response.data.success === true){
                  _this.username = response.data.userName;
                  _this.userhead = response.data.userImage;
                }
              })
              .catch(function (err) {
              })
          }
        },
      mounted() {
        this.loadData();
      },
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
