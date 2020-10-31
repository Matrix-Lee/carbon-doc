<template>
  <div>
    <h2 style="color: darkgrey">我的桌面</h2>

    <br/>
    <van-row>
      <van-col span="16"></van-col>

      <van-col span="1">

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
      </van-col>
      <van-col span="1">
          <div style="border-right: 3px solid;border-color: darkgrey;">&nbsp;
          </div>&nbsp;&nbsp;
      </van-col>
      <van-col span="4">
          <el-tooltip class="item" effect="dark" content="平铺" placement="top">
            <van-icon :name="icon1" @click="changetype(1)" :size="size1"/>
          </el-tooltip>

          <el-tooltip class="item" effect="dark" content="列表" placement="top">
            <van-icon :name="icon2" clickable @click="changetype(2)" :size="size2"/>
          </el-tooltip>
      </van-col>
      <van-col span="2">
      </van-col>
    </van-row>

    <div v-if="showtype==1" style="margin-top:25px;">
      <van-row>
        <van-col span="2"></van-col>
        <van-col span="20">

          <div v-if="tableList.length==0">
              我的桌面空空如也~~~&nbsp;&nbsp;<el-link type="primary" @click="newDoc">点击新建</el-link>
          </div>
          
          <van-grid :border="false" column-num="4">
            <van-grid-item v-for="(item,index) in tableList" :key="index">
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
                                    :src="require('../../assets/file.jpg')"/>&nbsp;
                                  {{item.docname}}</el-dropdown-item>
                          <el-dropdown-item icon="el-icon-edit-outline" divided command="0">预览文件</el-dropdown-item>
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


                <br/>
                <van-image :src="require('../../assets/file.jpg')"
                         width="4rem"
                         height="4rem"
                         fit="contain"></van-image>
                <p>{{item.docname}}</p>
              </div>
            </van-grid-item>
          </van-grid>
        </van-col>

      </van-row>

      <br/>
    </div>


<!--列表-->
    <div v-if="showtype==2" style="margin-top:35px;">
      <van-row>
        <van-col span="2">
        </van-col>

        <van-col span="20">
          <el-table
            :row-class-name="rowClassName"
            :data="tableList"
            style="width: 100%"
            max-height="500"
            @cell-mouse-enter="hoverInTable"
            @cell-mouse-leave="hoverOutTable">

            <template slot="empty">
                  我的桌面空空如也~~~&nbsp;&nbsp;<el-link type="primary" @click="newDoc">点击新建</el-link>
            </template>

            <el-table-column
              prop="docname"
              label="文件名"
              width="250">
            </el-table-column>

            <el-table-column
              prop="owner"
              label="创建者"
              width="100">
            </el-table-column>

            <el-table-column
              prop="changedate"
              label="更新时间"
              width="100">
            </el-table-column>

            <el-table-column
              prop="createdate"
              label="创建时间"
              width="150">
            </el-table-column>

            <el-table-column>
              <template slot-scope="scope">

                <el-dropdown placement="bottom-start" @command="chooseFile">
                  <span class="el-dropdown-link">
                    <i v-if="scope.$index==nowindex" class="el-icon-s-tools" @click="clickTool(scope.$index)"></i>
                  </span>

                  <el-dropdown-menu slot="dropdown"  style="width: 220px">
                    <van-row><van-col span="2"></van-col>
                      <van-col span="20">
                        <el-dropdown-item>
                          <van-image
                            width="20"
                            height="20"
                            :src="require('../../assets/file.jpg')"/>&nbsp;
                          {{tableList[scope.$index].docname}}</el-dropdown-item>
                        <el-dropdown-item icon="el-icon-edit-outline" command="0">预览文件</el-dropdown-item>
                        <el-dropdown-item icon="el-icon-edit-outline" command="1" :disabled="tableList[scope.$index].power<2">编辑文件</el-dropdown-item>
                        <el-dropdown-item icon="el-icon-star-off" divided command="2">收藏</el-dropdown-item>
                        <el-dropdown-item icon="el-icon-share" command="3">分享</el-dropdown-item>
                        <!--<el-dropdown-item icon="el-icon-top-right" command="4" divided>导出</el-dropdown-item>-->
                        <el-dropdown-item icon="el-icon-edit" command="5" :disabled="tableList[scope.$index].power<3">重命名</el-dropdown-item>
                        <el-dropdown-item icon="el-icon-document" command="6">创建副本</el-dropdown-item>
                        <el-dropdown-item icon="el-icon-delete" divided style="color: red" command="7">删除</el-dropdown-item>
                      </van-col>
                      <van-col span="2"></van-col>
                    </van-row>
                  </el-dropdown-menu>
                </el-dropdown>


              </template>
            </el-table-column>

          </el-table>
        </van-col>

        <van-col span="2">
        </van-col>
      </van-row>
    </div>

    <!--重命名对话框-->
    <van-dialog v-model="showRenameDialog" title="标题" show-cancel-button @confirm="confirmRenameFile">
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
            <van-checkbox v-model="checked" style="margin-left:20px;margin:30px;" v-if="tableList[lastindex].ownerid==myemail">是否允许ta对文档进行修改</van-checkbox>
        </div>
    </van-dialog>

    <!--新建文件对话框-->
    <van-dialog v-model="showNew" title="新建文件" show-cancel-button @confirm="confirmNewFile">
        <van-field v-model="DocName" label="输入文件名" />
    </van-dialog>
  </div>
</template>

<script>
    import axios from "axios";
    import baseUrl from "../baseUrl";
    import {Toast} from "vant";
    import {Dialog} from "vant";

    export default {
        name: "MyDesktop",
      data() {
          return{
            myemail:localStorage.getItem('myemail'),//登着的邮箱
            imgSrc:require('../../assets/loginback.jpg'),
            icon1:'apps-o',
            icon2:'bars',
            size1:25,
            size2:15,
            showtype: 1,

            type: 1,

            nowindex: -1,
            lastindex: -1,

            //分享文件部分数据
            showShare:false,
            radio:'1',
            checked:false,
            isshowConfirm:false,
            keywords:'',//搜索用关键字
            havechoose:false,

            possible:[//可能搜索对象推荐
               ],


            newDocName: '',
            showRenameDialog: false,

            gridColor: [],

            tableList: [],

            //新建部分数据
      showNew:false,
      DocName:'',
          }
      },
      methods:{
        rank(index){
          if(index == this.type){
            return;
          }
          this.type = index;
          this.loadData();
        },

        changetype(type){
          if(type==1){
            this.size1=25;
            this.size2=15;
            this.showtype=1;
          }
          else if(type==2){
            this.size1=15;
            this.size2=25;
            this.showtype=2;
          }
          this.loadData();
        },


//新建文件
        newDoc(){
        this.showNew=true;
    },
    confirmNewFile(){
        //需要传入 文件名 this.DocName 创建者 this.myemail  团队id  this.teamID(-1为不在团队内，其余为团队id)
      let _this = this;
      let data = new FormData();
      data.append('docName',this.DocName+='.doc');
      data.append('userId',this.myemail);
      data.append('teamId',this.teamID);
      axios.post(baseUrl+'/userCreateNewFile',data)
      .then(function (response) {
          if(response.data.success){
            _this.$router.push({
              path:'/test',
              query:{editing:response.data.result},
            });
          }
      })
      .catch(function (err) {
      })

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
            this.keywords=item.useremail;
            this.havechoose=true;
            this.isshowConfirm=true;
        },

        confirmShare(){//提交分享 参数：分享对象邮箱 keywords 是否给编辑权限 check(false 不给/true 给)
            let index = this.lastindex;
            let _this = this;

          let data = new FormData();
          data.append('userId',this.myemail);
          data.append('objId',this.keywords);
          data.append('docId',this.tableList[index].docid);
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

        rowClassName({row,rowIndex}){
          row.index = rowIndex;
        },
        hoverInTable(row){
          this.nowindex = row.index;
          this.lastindex = row.index;
        },
        hoverOutTable(row){
          this.nowindex = -1;
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
          // TODO 预览文件
          localStorage.setItem('readnow',true);
          this.$router.push({
            path:'/test',
            query:{
              editing:this.tableList[index].docid,
            },
          });
        },
        // 打开文件
        openFile(index){
          localStorage.setItem('readnow',false);
          this.$router.push({
            path:'/test',
            query:{editing:this.tableList[index].docid,
            },
          });
        },
        // 收藏文件
        setFavourite(index) {
          let _this = this;
          let data = new FormData();
          data.append('userId',this.myemail);
          data.append('docId',this.tableList[index].docid);
          axios.post(baseUrl+'/userSetFavourite',data)
            .then(function (response) {
                Toast(response.data.message);
            })
            .catch(function (err) {
            })
        },
        // 分享文件
        shareFile(index){
          // TODO 分享文件，设置权限
          this.showShare=true;
        },
        // 导出文件
        exportFile(index){
          // TODO 下载文件 文件id为this.tableList[index].docid
        },
        // 文件重命名
        renameFile(index){
          this.showRenameDialog = true;
          this.newDocName = this.tableList[index].docname;
          this.newDocName = this.newDocName.substring(0,this.newDocName.length-4);
        },
        confirmRenameFile(){
          // TODO 文件重命名
          let index = this.lastindex;
          let _this = this;
          let data = new FormData();
          data.append('userId',this.myemail);
          data.append('docId',this.tableList[index].docid);
          data.append('newName',this.newDocName+".doc");
          axios.post(baseUrl+'/userRenameFile',data)
          .then(function (response) {
            Toast(response.data.message);
            if(response.data.success) {
              _this.tableList[index].docname = _this.newDocName+".doc";
            }
          })
          .catch(function (err) {
          })
        },
        // 保存文件副本
        saveFileAs(index){
          // 保存文件副本
          let name =this.tableList[index].docname;
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
          console.log(this.tableList[index].ownerid)
          if(this.tableList[index].ownerid === this.myemail){
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
              data.append('docId',this.tableList[index].docid);
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



        // 加载数据
        loadData(){
          let _this = this;
          let data = new FormData();
          data.append('userId',this.myemail);
          data.append('type',this.type); // 1 更改时间 2 创建时间
          axios.post(baseUrl+'/getUserDesktopDoc',data)
            .then(function (response) {
                _this.tableList = [];
                for(let i=0; i<response.data.length; i++) {
                  _this.tableList.push(response.data[i]);
                  _this.gridColor.push('');
                }
            })
            .catch(function (err) {
            })
        },
      },
      mounted() {
          this.loadData();
      }

    }
</script>

<style scoped>

</style>
