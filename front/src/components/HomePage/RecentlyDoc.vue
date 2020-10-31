<template>
  <div>

    <br/>
    <van-row>
      <van-col span="21"></van-col>
      <van-col span="2">

        <div style="border-right: 3px solid;border-color: darkgrey">
    <span>
      <el-tooltip class="item" effect="dark" content="文件排序方式" placement="top-start">
        <el-popover
          placement="bottom"
          width="120"
          trigger="click"
          content="">
          <van-cell-group>
            <van-cell title="更改时间" clickable @click="rank(1)"/>
            <van-cell title="创建时间" clickable @click="rank(2)"/>
          </van-cell-group>

          <van-icon name="ellipsis" slot="reference" clickable/>
        </el-popover>
      </el-tooltip>
    </span>
      </div>

      </van-col>
    </van-row>
    <br/>


<!--列表显示-->
  <div v-for="(item,index) in fileList" :key="item">
    <van-row>
      <van-col span="2"></van-col>
      <van-col span="20">
        <div style="border-top: 3px solid;border-color: darkgrey;" >
          <h3 style="color: #656565;">{{fileList[index].date}}</h3>
        </div>
      </van-col>
      <van-col span="2">
      </van-col>
    </van-row>

    <van-row>
      <van-col span="2">
      </van-col>

      <van-col span="10">



<!--列表显示左侧-->
<div  v-for="(item,index1) in fileList[index].doclist" :key="item" @mouseenter="hoverIncell(index,index1)" @mouseleave="hoverOutcell(index,index1)">
  <el-dropdown placement="bottom-start" @command="chooseFile" trigger="hover">

      <span class="el-dropdown-link">

        <van-cell :title="fileList[index].doclist[index1].docname" clickable  style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);width: 300px" v-if="index1%2==0" @click="clickFile(index,index1)">

          <template #icon>
            <van-image
              width="45"
              height="45"
              :src="require('../../assets/file.jpg')">
            </van-image>
          </template>

          <template #default>
              <div style="color:">创建者:</div><div style="font-size: 20px; color:">{{fileList[index].doclist[index1].owner}}</div>
          </template>
          <template #label>
            <div v-bind:style="{width:'80%'}"><div class="van-ellipsis">{{fileList[index].doclist[index1].doc}}</div></div>
          </template>
        </van-cell>

      </span>

<!--下拉框内容-->
    <el-dropdown-menu slot="dropdown"  style="width: 220px">
      <van-row><van-col span="2"></van-col>
        <van-col span="20">
          <el-dropdown-item>
            <van-image
              width="20"
              height="20"
              :src="require('../../assets/file.jpg')"/>&nbsp;
            {{fileList[index].doclist[index1].docname}}</el-dropdown-item>
          <el-dropdown-item divided icon="el-icon-edit-outline" command="0">预览文件</el-dropdown-item>
          <el-dropdown-item icon="el-icon-edit-outline" command="1" :disabled="item.power<2">编辑文件</el-dropdown-item>
          <el-dropdown-item icon="el-icon-star-off" divided command="2">收藏</el-dropdown-item>
          <el-dropdown-item icon="el-icon-share" command="3">分享</el-dropdown-item>
          <!--<el-dropdown-item icon="el-icon-top-right" command="4" divided>导出</el-dropdown-item>-->
          <el-dropdown-item icon="el-icon-edit" command="5" :disabled="item.power<3">重命名</el-dropdown-item>
          <el-dropdown-item icon="el-icon-document" command="6">创建副本</el-dropdown-item>
          <el-dropdown-item icon="el-icon-delete" divided style="color: red" command="7">删除</el-dropdown-item>
        </van-col>
        <van-col span="2"></van-col>
      </van-row>
    </el-dropdown-menu>
  </el-dropdown>

</div>

      </van-col>

      <van-col span="1">
      </van-col>

      <van-col span="10">

<!--列表显示右侧-->
        <div  v-for="(item,index1) in fileList[index].doclist" :key="item"  @mouseenter="hoverIncell(index,index1)" @mouseleave="hoverOutcell(index,index1)">
          <el-dropdown placement="bottom-start" @command="chooseFile" trigger="hover">
              <span class="el-dropdown-link">

              <van-cell :title="fileList[index].doclist[index1].docname" clickable  style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);width: 300px" v-if="index1%2==1"  @click="clickFile(index,index1)">
               <template #icon>
               <van-image
                width="45"
                height="45"
                :src="require('../../assets/file.jpg')"/>
               </template>
               <template #default>
                  <div style="color:">创建者:</div><div style="font-size: 20px; color:">{{fileList[index].doclist[index1].owner}}</div>
               </template>
             <template #label>
               <div v-bind:style="{width:'80%'}"><div class="van-ellipsis">{{fileList[index].doclist[index1].doc}}</div></div>
            </template>
           </van-cell>

        </span>

<!--下拉框内容-->
            <el-dropdown-menu slot="dropdown" style="width: 220px">
              <van-row><van-col span="2"></van-col>
                <van-col span="20">
                  <el-dropdown-item>
                    <van-image
                      width="20"
                      height="20"
                      :src="require('../../assets/file.jpg')"/>&nbsp;
                    {{fileList[index].doclist[index1].docname}}
                  </el-dropdown-item>
                  <el-dropdown-item divided icon="el-icon-edit-outline" command="0">预览文件</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-edit-outline" command="1" :disabled="fileList[index].doclist[index1].power<2">编辑文件</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-star-off" divided command="2">收藏</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-share" command="3">分享</el-dropdown-item>
                  <!--<el-dropdown-item icon="el-icon-top-right" command="4" divided>导出</el-dropdown-item>-->
                  <el-dropdown-item icon="el-icon-edit" command="5" :disabled="fileList[index].doclist[index1].power<3">重命名</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-document" command="6">创建副本</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-delete" divided style="color: red" command="7">删除</el-dropdown-item>
                </van-col>
                <van-col span="2"></van-col>
              </van-row>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </van-col>

      <van-col span="1">
      </van-col>
    </van-row>
    <br/>
  </div>

    <!--重命名对话框-->
    <van-dialog v-model="showRenameDialog" title="重命名" show-cancel-button @confirm="confirmRenameFile">
      <van-field v-model="newDocName" label="输入新文件名" />
    </van-dialog>


<!--分享对话框-->
    <van-dialog v-model="showShare" title="分享" show-cancel-button style="height:400px;width:350px;" @confirm="confirmShare" :show-confirm-button="isshowConfirm" confirm-button-text="确认分享" @cancel="cancelShare">
        <van-search v-model="keywords" placeholder="请输入分享对象用户名" v-if="radio=='1'" @input="onSearch"/>
        <van-search v-model="keywords" placeholder="请输入分享对象邮箱" v-if="radio=='2'" @input="onSearch"/>

        <template>
            <el-radio v-model="radio" label="1" @change="onSearch">用户名搜索</el-radio>
            <el-radio v-model="radio" label="2" @change="onSearch">用户邮箱搜素</el-radio>
        </template>

        <van-cell v-for="(item,index) in possible" :key="item" :title="possible[index].userName" title-style="text-align:left;margin-left:20px;" :icon="possible[index].userImage" clickable @click="chooseOne(item)" v-if="radio=='1'&&havechoose==false">
            <template #icon>
                <van-image
                width="25"
                round
                fit="cover"
                height="25"
                :src="possible[index].userImage"/>
            </template>
        </van-cell>
        <van-cell v-for="(item,index) in possible" :key="item" :title="possible[index].userId" title-style="text-align:left;margin-left:20px;" :icon="possible[index].userImage" clickable @click="chooseOne(item)" v-if="radio=='2'&&havechoose==false">
            <template #icon>
                <van-image
                width="25"
                round
                fit="cover"
                height="25"
                :src="possible[index].userImage"/>
            </template>
        </van-cell>

        <div v-if="havechoose==true" style="margin-top:50px;">
            <p style="margin-left:20px;color:grey;">分享意味着对方可以查看文档并将文档分享给他人查看</p>
            <van-checkbox v-model="checked" style="margin-left:20px;margin:30px;" v-if="fileList[nowindex.index].doclist[nowindex.index1].ownerid==myemail">是否允许ta对文档进行修改</van-checkbox>
        </div>
    </van-dialog>

  </div>
</template>

<script>
  import {Dialog, Toast} from "vant";
  import baseUrl from "../baseUrl";
  import axios from 'axios'
    export default {
        name: "RecentlyDoc",
      data() {
          return{
            myemail:localStorage.getItem('myemail'),//登着的邮箱
            imgSrc:require('../../assets/loginback.jpg'),

            type: 1,
            icon1:'apps-o',
            icon2:'bars',
            size1:25,
            size2:15,


            // 图标部分
            nowindex: {index: -1, index1: -1},
            newDocName: '',
            showRenameDialog: false,

            //分享部分数据
            showShare:false,
            radio:'1',
            checked:false,
            isshowConfirm:false,
            keywords:'',//搜索用关键字
            havechoose:false,

            possible:[//可能搜索对象推荐
                {userImg:'',userId:'123',userName:'you'},
                {userImg:'',userId:'123',userName:'you'},
            ],


            fileList:[ // 最近浏览 一个月
                {date:'8/7',doclist:[{docname:'you',docId:'fsd',owner:'dfsafd',doc:'fsdsdfsdf'},{docname:'you',docId:'fsd',owner:'dfsafd',doc:'fsdsdfsdf'}]}
             ],


          }
      },
      methods:{
        rank(i){
          if(this.type == i){
            return;
          }
          this.type = i;
          this.loadData();
        },



//分享框部分
        //搜索推荐用户
        onSearch(){
            this.havechoose=false;
            //this.keywords传给后端，返回至多5个可能目标用户
            let _this = this;
            let data = new FormData();
            data.append('value',this.keywords);
            let model = 1;
            if(this.radio == 1)
              model = 2;
            data.append('model',model);
            data.append('userId',this.myemail);
            axios.post(baseUrl+'/userSearchDocUser',data)
            .then(function (response) {
              _this.possible = [];
                for(let i=0; i<response.data.length;i++){
                  _this.possible.push(response.data[i]);
                }
            })
            .catch(function (err) {
            })
        },

        //点击推荐用户
        chooseOne(item){
            this.keywords=item.userId;
            this.havechoose=true;
            this.isshowConfirm=true;
        },

        confirmShare(){//提交分享 参数：分享对象邮箱 keywords 是否给编辑权限 check(false 不给/true 给)
            let index = this.nowindex;
            let _this = this;
            let data = new FormData();
            data.append('userId',this.myemail);
            data.append('objId',this.keywords);
            data.append('docId',this.fileList[this.nowindex.index].doclist[this.nowindex.index1].docid);
            data.append('power',this.checked);
            axios.post(baseUrl+'/userShareFile',data)
            .then(function (response) {
                Toast(response.data.message);
            })
            .catch(function (err) {
            });

            this.keywords='';
            this.havechoose=false;
            this.isshowConfirm=false;

        },

        cancelShare(){
            this.keywords='';
            this.havechoose=false;
            this.isshowConfirm=false;
        },



        // 图标下拉框部分

        clickFile(index,index1){
          this.nowindex.index = index;
          this.nowindex.index1 = index1;
        },

        hoverIncell(index,index1){
          this.nowindex.index = index;
          this.nowindex.index1 = index1;
        },

        chooseFile(command){
          let index = this.nowindex;
          if(command==0)
            this.showFile(index);
          else if(command==1)
            this.openFile(index);
          else if(command==2)
            this.setFavourite(index);
          else if(command==3)
            this.shareFile(index);
          else if(command==4)
            this.exportFile(index);
          else if(command==5)
            this.renameFile(index);
          else if(command==6)
            this.saveFileAs(index);
          else if(command==7)
            this.deleteFile(index);
        },
        // 预览文件
        showFile(index){
          localStorage.setItem('readnow',true);
          this.$router.push({
              path:'/test',
              query:{
              editing:this.fileList[index.index].doclist[index.index1].docid,
              },
          });
        },
        // 打开文件
        openFile(index){
          localStorage.setItem('readnow',false);
          this.$router.push({
            path:'/test',
            query:{editing:this.fileList[index.index].doclist[index.index1].docid,
            },
          });
        },
        // 收藏文件
        setFavourite(index) {
          let _this = this;
          let data = new FormData();
          data.append('userId',this.myemail);
          data.append('docId',this.fileList[index.index].doclist[index.index1].docid);
          axios.post(baseUrl+'/userSetFavourite',data)
            .then(function (response) {
              Toast(response.data.message);
            })
            .catch(function (err) {
            })
        },
        // 分享文件
        shareFile(index){
          this.showShare=true;
        },
        // 导出文件
        exportFile(index){
          // TODO 下载文件 文件id为this.fileList[index].docid

        },
        // 文件重命名
        renameFile(index){
          this.showRenameDialog = true;
          this.newDocName = this.fileList[index.index].doclist[index.index1].docname;
          this.newDocName = this.newDocName.substring(0,this.newDocName.length-4);
        },
        confirmRenameFile(){
          // TODO 文件重命名
          let index = this.nowindex;
          let _this = this;
          let data = new FormData();
          data.append('userId',this.myemail);
          data.append('docId',this.fileList[index.index].doclist[index.index1].docid);
          data.append('newName',this.newDocName+".doc");
          axios.post(baseUrl+'/userRenameFile',data)
            .then(function (response) {
              Toast(response.data.message);
              if(response.data.success) {
                _this.fileList[index.index].doclist[index.index1].docname = _this.newDocName+".doc";
              }
            })
            .catch(function (err) {
            })
        },
        // 保存文件副本
        saveFileAs(index){

          let name =this.fileList[index.index].doclist[index.index1].docname;
          name = name.substring(0,name.length-4);
          Toast(name);
          let _this = this;
          let data = new FormData();
          data.append('userId',this.myemail);
          data.append('docName',name+"-副本.doc");
          axios.post(baseUrl+'/userCreateNewFile',data)
          .then(function (response) {
              Toast(response.data.message);
              if(response.data.success)
                _this.loadData();
          })
          .catch(function (err) {
          })
        },
        // 删除文件
        deleteFile(index){
          let msg = '';
          if(this.fileList[index.index].doclist[index.index1].ownerid === this.myemail){
            msg = "文件将被放入回收站，是否确认？";
          }
          else{
            msg = "您不是文件的创建者，此操作只会删除文件在您这里的显示，文件本身不会删除，是否确认？"
          }
          Dialog.confirm({
            title: '删除文件',
            message: msg,
          })
            .then(() => {
              let _this = this;
              let data = new FormData();
              data.append('userId',this.myemail);
              data.append('docId',this.fileList[index.index].doclist[index.index1].docid);
              axios.post(baseUrl+'/userDeleteFile',data)
                .then(function (response) {
                  Toast(response.data.message);
                  if(response.data.success){
                    _this.loadData();
                  }
                })
                .catch(function (err) {
                })
            })
            .catch(() => {
            });
        },


        loadData(){
          let _this = this;
          let data = new FormData();
          data.append('userId',this.myemail);
          data.append('type',this.type); // 1 更改时间 2 创建时间
          axios.post(baseUrl+'/getUserRecentlyDoc',data)
            .then(function (response) {
              _this.fileList = [];
              for(let i=0; i<response.data.length; i++){
                let dlist = [];
                for(let j=0; j<response.data[i].docList.length;j++){
                  dlist.push(response.data[i].docList[j]);
                }
                _this.fileList.push({date:response.data[i].date, doclist: dlist});
              }
              console.log(_this.fileList)
            })
            .catch(function (err) {
            })
        }

      },
      mounted() {
          this.loadData();
      },
      created(){},

    }
</script>

<style scoped>

</style>
