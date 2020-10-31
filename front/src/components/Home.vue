<template>
  <div v-if="refreshed" >
    <!--<div class="background">
            <img :src="imgSrc" width="100%" height="100%" alt="" />
        </div>-->

<!--上标-->
    <div style="z-index:99999;position: absolute;width:100%;margin-top:-18px" >
    <van-sticky>
      <div class="up" style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)">
        <div style="padding:15px 0;">
          <van-row>
            <van-col span="4">
              <span style="font-size:21px;">金刚石文档</span>
            </van-col>
            <van-col span="6"></van-col>
            <van-col span="8">
              <el-dropdown placement="bottom">
                <span class="el-dropdown-link">
                  <el-input
                    v-model="searchData"
                    placeholder="搜索文件"
                    size="small"
                    prefix-icon="el-icon-search"
                  >
                  </el-input>
                </span>
                <el-dropdown-menu v-if="searchData.length>0" slot="dropdown">
                  <div
                    v-for="(item, index) in Newitems"
                    :key="index">
                    <el-dropdown-item>{{ item.docname }}</el-dropdown-item>
                  </div>
                </el-dropdown-menu>
              </el-dropdown>
            </van-col>
            <van-col span="2"></van-col>

<!--消息提醒-->
            <van-col span="1">
              <van-icon @click="messageshow" name="chat-o" size="30" :badge="messagenumber"/>
            </van-col>

            <van-col span="2">
              <el-dropdown @command="onCommand">
                <span class="el-dropdown-link">
                  <van-image
                    round
                    width="30px"
                    height="30px"
                    fit="cover"
                    :src="userhead"
                  /><i class="el-icon-arrow-down el-icon--right"></i>
                </span>

                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item icon="el-icon-user" @click.native="toMyinfo"
                    >个人信息</el-dropdown-item
                  >
                  <el-dropdown-item
                    icon="el-icon-warning-outline"
                    @click.native="toLogin"
                    ><span style="color:red;">退出账号</span></el-dropdown-item
                  >
                  <el-dropdown-item icon="el-icon-circle-plus-outline"
                    >帮助</el-dropdown-item
                  >
                </el-dropdown-menu>
              </el-dropdown>
            </van-col>
          </van-row>
        </div>
      </div>
    </van-sticky>
    </div>

    <div>

    <van-row style="margin-top:15px;">

<!--侧边栏-->
      <van-col span="4">
      <div style="">
        <van-sticky :offset-top="80">

        <van-row>

    <!--左边栏-->
        <van-col span="4" style="text-align:left;">
          <el-menu
            :default-active="nowActive"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
            @select="onChange"
          >
            <el-menu-item index="1-0">
              <i class="el-icon-s-promotion"></i>
              <span slot="title">工作台</span>
            </el-menu-item>
            <el-menu-item index="1-1">
              <i class="el-icon-receiving"></i>
              <span slot="title">收件箱<span v-if="messagenumber>=1">({{this.messagenumber}})</span></span>
            </el-menu-item>
            <el-menu-item index="1-2">
              <i class="el-icon-s-platform"></i>
              <span slot="title">我的桌面</span>
            </el-menu-item>

<!--团队部分-->
            <el-submenu index="1-3">
              <template slot="title">
                <van-icon name="friends" size="21px"/>
                <span>&nbsp&nbsp&nbsp团队空间</span>
              </template>
                  <el-menu-item v-for="(item,index) in allTeams" :key="index" @click="openTeampage(item)" :index="index">{{item.teamName}}</el-menu-item>
            </el-submenu>


            <el-menu-item index="1-4">
              <i class="el-icon-delete-solid"></i>
              <span slot="title">&nbsp回收站</span>
            </el-menu-item>
            <el-menu-item index="1-5">
              <i class="el-icon-s-help"></i>
              <span slot="title">&nbsp帮助中心</span>
            </el-menu-item>
          </el-menu>
          </van-col>
          <van-col span="16"></van-col>

    <!--右边栏-->
          <van-col span="3">
          <van-button
            type=""
            block
            style="margin-top:66px;background-color: #071543;color: white" @click="newDoc"
            >新建</van-button
          >
          <van-button
            type=""
            block
            style="margin-top:16px;background-color: #f4f4f4;color: black" @click="showModel"
            >模板库</van-button
          >
          <van-button
            type=""
            block
            style="margin-top:16px;background-color: #f4f4f4;color: black" @click="creatTeam"
            >创建团队</van-button
          >
          <van-uploader :after-read="importDoc" accept=".doc">
            <van-icon name="add-o" size="45px" style="margin-top:30px;"/>
            </van-uploader>
          </van-col>
          <van-col span="1"></van-col>

          </van-row>

          </van-sticky>
        </div>
        </van-col>


<!--中间部分-->
    <!--工作台-->


        <van-col span="16">
            <div style="z-index:999;position: relative;width:100%;margin-top:65px;">

                      <div v-if="index1">
                      <van-tabs v-model="activeDoc" style="width:80%;" @change="onChangegzt">
                      <van-tab title="最近使用"><RecentlyDoc :key="timer" /></van-tab>
                      <van-tab title="我创建的">
                      <CreateDoc :key="timer" /></van-tab>
                      <van-tab title="我的收藏"><FavouriteDoc :key="timer" /></van-tab>
                      <!--<van-tab title="工作动态">这里是工作动态项目</van-tab>-->
                      </van-tabs>
                      </div>

    <!--收件箱-->
          <div v-if="index2" style="margin-left:20px;">
            <message v-on:getChildinfo="getMessageNum"/>
          </div>

    <!--桌面-->
          <div v-if="index3">
            <MyDesktop />
          </div>

    <!--团队空间-->
          <div v-if="index4">
            <MyTeam :teamId="teamID" v-on:childMyTeam="MyTeamInfo"></MyTeam>
          </div>

    <!--回收站-->
          <div v-if="index5">
            <RecycleBin />
          </div>

    <!--帮助中心-->
          <div v-if="index6">
            帮助中心
          </div>

      </div>
      </van-col>


      </van-row>
    </div>

<!--新建文件对话框-->
    <van-dialog v-model="showNew" title="新建文件" show-cancel-button @confirm="confirmNewFile" style="width:400px;">
        <van-field v-model="DocName" label="输入文件名" />
        已选择模板:<span style="" v-if="modelRadio==-1">空白页</span><span style="" v-if="modelRadio!=-1">{{modelList[modelRadio].modelName}}</span>
        <div style="margin-left:20px;">
          <van-radio-group v-model="modelRadio">
            <van-radio name="-1">空白页</van-radio>
            <van-radio :name="index" v-for="(item,index) in modelList" :key="item" style="margin-top:10px;">
                {{modelList[index].modelName}}
            </van-radio>
          </van-radio-group>
        </div>
    </van-dialog>

<!--创建团队对话框-->
    <van-dialog v-model="showCreatTeam" title="创建新的团队" show-cancel-button @confirm="confirmNewTeam" style="width:400px;">
        <van-field style="margin:20px;" placeholder="请输入新的团队名称" v-model="TeamName" label="输入团队名" />
    </van-dialog>


<!--模板页面-->
    <van-popup v-model="popModel" closeable close-icon="close" style="height:450px;width:600px;">
        <p>模板库</p>
        <van-grid :border="false" column-num="4">
            <van-grid-item v-for="(item,index) in modelList" :key="index" @click="watchModel(index)">
              <div @mouseenter="hoverInGrid(index)" @mouseleave="hoverOutGrid(index)" :style="{width: '120px',background: gridColor[index]}">
                <br/>

                <el-dropdown placement="bottom-start" style="width: 200px" @command="chooseFile">

                  <span class="el-dropdown-link">
                    <i v-if="index==nowindex" class="el-icon-s-tools"></i>
                  </span>
                    <el-dropdown-menu slot="dropdown"  style="width: 220px">
                      <van-row><van-col span="2"></van-col>
                        <van-col span="20">
                                <el-dropdown-item>
                                  <van-image
                                    width="20"
                                    height="20"
                                    :src="item.modelPic"/>&nbsp;
                                  {{item.modelName}}</el-dropdown-item>
                          <el-dropdown-item icon="el-icon-edit-outline" divided command="0">预览模板</el-dropdown-item>
                          <el-dropdown-item icon="el-icon-edit-outline" command="1">以此模板新建</el-dropdown-item>
                        </van-col>
                        <van-col span="2"></van-col>
                      </van-row>

                    </el-dropdown-menu>

                </el-dropdown>


                <br/>
                <van-image :src="item.modelPic"
                         width="100px"
                         height="100px"
                         fit="contain">
                </van-image>
                <p>{{item.modelName}}</p>
              </div>
            </van-grid-item>
          </van-grid>
    </van-popup>

  </div>
</template>

<script>
import { Toast } from "vant";
import baseUrl from "./baseUrl";
import axios from "axios";
import RecentlyDoc from "./HomePage/RecentlyDoc";
import CreateDoc from "./HomePage/CreateDoc";
import FavouriteDoc from "./HomePage/FavouriteDoc";
import RecycleBin from "./HomePage/RecycleBin";
import MyDesktop from "./HomePage/MyDesktop";
import MyTeam from "./HomePage/MyTeam";
import message from "./HomePage/message";

export default {
  name: "Home",
  components: { MyDesktop, RecycleBin, FavouriteDoc, CreateDoc, RecentlyDoc, MyTeam, message },
  data() {
    return {

    //模板
    popModel:false,
    modelList:[
        {modelId:'1',modelPic:require("../assets/picmodel00.jpg"),modelName:'模板1'},
        {modelId:'2',modelPic:require("../assets/picmodel01.jpg"),modelName:'模板2'},
        {modelId:'3',modelPic:require("../assets/picmodel02.jpg"),modelName:'模板3'},
        {modelId:'4',modelPic:require("../assets/picmodel03.jpg"),modelName:'模板4'},
        {modelId:'5',modelPic:require("../assets/picmodel04.jpg"),modelName:'模板5'},
    ],
    gridColor:[],
    nowindex:-1,
    lastindex:-1,

//message
      messagenumber:false,

      searchData: "",
      myemail: localStorage.getItem("myemail"), //登着的邮箱
      imgSrc: require("../assets/loginback.jpg"),
      userhead: "https://img.yzcdn.cn/vant/cat.jpeg", //用户头像
      icon1: "apps-o",
      icon2: "bars",
      size1: 25,
      size2: 15,

      nowActive:'1-0',//侧边栏位置

      activeDoc: "", // 最近使用 创建 收藏

      refreshed: true,

      showtype: 1, //平铺 1 列表 2
      index1: true,
      index2: false,
      index3: false,
      index4: false,
      index5: false,
      index6: false,
      showpop: false,

//队伍信息
      allTeams:[
          {teamId:"123",teamName:"niu牛0",teamLeader:"youabcd",teamCreateDate:"2020/8/13"},
          {teamId:"1234",teamName:"niu牛1",teamLeader:"youabcd",teamCreateDate:"2020/8/13"},
          {teamId:"1235",teamName:"niu牛2",teamLeader:"youabcd",teamCreateDate:"2020/8/13"},
          {teamId:"1236",teamName:"niu牛3",teamLeader:"youabcd",teamCreateDate:"2020/8/13"},
      ],
      teamID:'-1',


//新建部分数据
      showNew:false,
      DocName:'',
      modelRadio:'-1',

//创建团队数据
      showCreatTeam:false,


      timer:'',


    };
  },

//方法
  methods: {

    onChangegzt(){
        this.timer = new Date().getTime()
    },

    onCommand(command) {},
    onRowclick(row, event, column) {
      Toast(row.id);
    },

//MyTeam的数据
    MyTeamInfo:function(index){
        this.onChange('1-0');
        this.teaminitial();
    },


//模板
    showModel(){
        this.popModel=true;
    },
    hoverInGrid(index){
          this.nowindex = index;
          this.lastindex = index;
          this.gridColor[index] = 'rgba(0,90,99,0.04)';
        },
    hoverOutGrid(index){
          this.nowindex = -1;
          this.gridColor[index] = '';
        },
    chooseFile(command){
          let index = this.lastindex;
          if(command==0)
            this.watchModel(index);
          else if(command==1)
            this.newDoc(index);
            this.modelRadio=index;
    },
    watchModel(index){
        window.open('#/Model0'+index);
    },


//创建团队
    creatTeam(){
        this.showCreatTeam=true;
    },
     confirmNewTeam(){//TeamName myemail
		console.log('submit!');
		var index="0";
          let _this=this;
          let data = new FormData();
          data.append('teamName',this.TeamName);
		  data.append('userId',this.myemail);
          axios.post(baseUrl+'/createTeam', data)
          .then(function(response){//从后端取值
            if(response.data.success === true) {

				_this.teaminitial();

				setTimeout(()=>{
				var newTeamId=response.data.result;
				for(var i=0;i<_this.allTeams.length;i++){
            if(_this.allTeams[i].teamId==newTeamId){
               index=i;
            }
			}
			_this.onChange(index);

				},100)
            }
            else { // 登录失败 ，，，
              Toast(response.data.message);
            }
          }
		  )
		console.log('confirmNewTeam!');

    },


//打开团队空间页面
    openTeampage(item){
        Toast(item.teamName);

        //this.teamID=item.teamId;
    },

// 导入文件
    importDoc(file){
      let _this = this;
      let data = new FormData();
      data.append('userId',this.myemail);
      data.append('fileContent',file.content );
      data.append('fileName',file.file.name);
      axios.post(baseUrl+'/userUploadFile',data)
      .then(function (response) {
        Toast(response.data.message);
        if(response.data.success)
          location.reload()
      })
    },


//新建文件
    newDoc(index){
        this.showNew=true;
        this.popModel=false;
    },
    confirmNewFile(){
        //需要传入 文件名 this.DocName 创建者 this.myemail  团队id  this.teamID(-1为不在团队内，其余为团队id)
      let _this = this;
      let data = new FormData();
      data.append('docName',this.DocName+='.doc');
      data.append('userId',this.myemail);
      data.append('teamId',this.teamID);
      let temp = '0';

      if(this.modelRadio=='-1'){
        temp = '0';
      }
      else {
        temp = this.modelList[this.modelRadio].modelId;
      }

      data.append('temp',temp);
      axios.post(baseUrl+'/userCreateNewFile',data)
      .then(function (response) {
          if(response.data.success){
              localStorage.setItem('readnow',false);
            _this.$router.push({
              path:'/test',
              query:{editing:response.data.result},
            });
          }
      })
      .catch(function (err) {
      })

    },
    setCurrent(row) {
        this.$refs.singleTable.setCurrentRow(row);
    },
    handleCurrentChange(val) {
        this.currentRow = val;
    },



    changetype(type) {
      if (type == 1) {
        this.size1 = 25;
        this.size2 = 15;
        this.showtype = 1;
      } else if (type == 2) {
        this.size1 = 15;
        this.size2 = 25;
        this.showtype = 2;
      }
    },
    rank(index) {
      if (index == 1) {
      } else if (index == 2) {
      } else if (index == 3) {
      } else if (index == 4) {
      } else {
      }
    },
    toMyinfo() {
      this.$router.push({
        path: "/Myinfo"
      });
    },
    toLogin() {
      localStorage.setItem("myemail", "");
      this.$router.push({
        path: "/"
      });
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    openteam() {
      this.showpop = true;
    },
    onChange(index) {
      localStorage.setItem('NowActive',index);
	  console.log(index);
      this.nowActive=index;
      if (index == '1-0') {
        this.index1 = true;
        this.index2 = false;
        this.index3 = false;
        this.index4 = false;
        this.index5 = false;
        this.index6 = false;
        this.teamID='-1';
      } else if (index == '1-1') {
        this.index1 = false;
        this.index2 = true;
        this.index3 = false;
        this.index4 = false;
        this.index5 = false;
        this.index6 = false;
        this.teamID='-1';
      } else if (index == '1-2') {
        this.index1 = false;
        this.index2 = false;
        this.index3 = true;
        this.index4 = false;
        this.index5 = false;
        this.index6 = false;
        this.teamID='-1';
      } else if (index == '1-3') {
        this.index1 = false;
        this.index2 = false;
        this.index3 = false;
        this.index4 = false;
        this.index5 = false;
        this.index6 = false;
        //this.show = true;
        this.teamID='-1';
      } else if (index == '1-4') {
        this.index1 = false;
        this.index2 = false;
        this.index3 = false;
        this.index4 = false;
        this.index5 = true;
        this.index6 = false;
        this.teamID='-1';
      } else if (index == '1-5') {
        this.index1 = false;
        this.index2 = false;
        this.index3 = false;
        this.index4 = false;
        this.index5 = false;
        this.index6 = true;
        this.teamID='-1';
      }
      else{
        this.index1 = false;
        this.index2 = false;
        this.index3 = false;
        this.index4 = true;
        this.index5 = false;
        this.index6 = false;
        this.teamID = this.allTeams[index].teamId;
      }
    },

//message
    open1() {
      const h = this.$createElement;
      this.$notify({
        title: "消息提示",
        offset: 50,
        message: h(
          "i",
          { style: "color: teal" },
          "你目前没有收到任何消息"
        ),
      });
    },
    messageshow()
    {
      if(this.messagenumber==false)
      {

        this.open1();
      }
      else
      {
        this.onChange('1-1');
      }
    },

    getMessageNum:function(childnum){
        this.messagenumber=childnum;
        //Toast(childnum);
        if(this.messagenumber==0){
            this.messagenumber=false;
        }
    },

//加载消息数量
	messageinitial(){
	console.log('submit!');
			 let _this=this;
			  let data = new FormData();
			  data.append('userId',this.myemail);
			  axios.post(baseUrl+'/initialMessage', data)
			   .then(function (response) {
				_this.messagenumber=response.data.result2.length;
        if(_this.messagenumber==0){_this.messagenumber=false;}
				console.log(_this.messagenumber);
            })
            .catch(function (err) {
            })
	},

//加载团队
	teaminitial(){

	let _this = this;
    let data = new FormData();
	data.append('userId',this.myemail);
	axios.post(baseUrl+'/showUserTeams',data)
      .then(function (response) {
	  console.log("进来了");
	  _this.allTeams=[];
        for(let i=0;i<response.data.length;i++){
          _this.allTeams.push(response.data[i]);
        }
		console.log(response.data.length);
      })

	}
  },
  mounted() {
    let _this = this;
    let data = new FormData();
    data.append('userId',this.myemail);
    axios.post(baseUrl+'/showUserImage',data)
    .then(function (response) {
        _this.userhead = response.data;
    })
    .catch(function (err) {
    });
    // 加载队伍信息


    this.nowActive=localStorage.getItem('NowActive');
	console.log(this.NowActive);
    this.onChange(this.nowActive);
	setTimeout(()=>{this.teaminitial();},200)
	setTimeout(()=>{this.messageinitial();},100)

  },
  created() {},
  computed: {
    Newitems() {
      var _this = this;
      var Newitems = [];
      _this.tableData.map(function(item) {
        if (item.docname.search(_this.searchData) != -1) {
          Newitems.push(item);
        }
      });
      return Newitems;
    }
  }
};
</script>

<style>
.el-menu--collapse .el-menu .el-submenu,
.el-menu--popup {
  min-width: 1000px;
}
.up {
  background: white;
  width: 100%;
  height: 60px;
}
.background {
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: fixed;
}
.leftdiv {
  width: 300px;
  height: 800px;
}
  .el-input{
    width: 300;
  }
</style>
