<template>
  <div>

    <van-row>
      <van-col span="4">
      </van-col>

      <van-col span="16">
        <h2 style="color: darkgrey">回收站</h2>
      </van-col>

      <van-col span="4" style="">
          <el-tooltip class="item" effect="light" content="点击清空回收站" placement="bottom">

          <el-popconfirm
          title="确定清空回收站吗？"
          @onConfirm="clearRecycelBin">

            <h2 slot="reference">
              <i class="el-icon-delete" @click="clearclick"></i>
            </h2>

            </el-popconfirm>
          </el-tooltip>
      </van-col>
    </van-row>

    <br/>
    <div v-if="fileList.length==0">
        <h2 style="color: darkgrey">空</h2>
    </div>

    <div v-for="(item,index) in fileList" :key="item" >
      <van-row>
        <van-col span="2"></van-col>
        <van-col span="20">
          <div style="border-top: 3px solid;border-color: darkgrey;" >
            <h3 style="color: darkgrey">{{fileList[index].date}}</h3>
            <p style="color: orange">还有&nbsp;{{remainDays(fileList[index].date)}}&nbsp;天将被永久删除</p>
          </div>
        </van-col>
        <van-col span="2"></van-col>
      </van-row>

      <van-row>
        <van-col span="2">
        </van-col>

        <van-col span="7">
          <div v-for="(item,index1) in fileList[index].doclist" :key="item" @mouseenter="hoverIncell(index,index1)" @mouseleave="hoverOutcell(index,index1)">
            <el-dropdown placement="bottom-start" @command="chooseFile" trigger="hover">
              <span class="el-dropdown-link">

          <van-cell :title="fileList[index].doclist[index1].docname" clickable  style="margin-top:12px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);width: 300px" v-if="index1%2==0"  @click="clickFile(index,index1)">
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
              <el-dropdown-menu slot="dropdown"  style="width: 220px">
                <van-row><van-col span="2"></van-col>
                  <van-col span="20">
                    <el-dropdown-item>
                      <van-image
                        width="20"
                        height="20"
                        :src="require('../../assets/file.jpg')"/>&nbsp;
                      {{fileList[index].doclist[index1].docname}}</el-dropdown-item>
                    <el-dropdown-item icon="el-icon-edit-outline" divided command="1" >恢复</el-dropdown-item>
                    <el-dropdown-item icon="el-icon-delete" style="color: red" command="2">删除</el-dropdown-item>
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


            <div v-for="(item,index1) in fileList[index].doclist" :key="item" @mouseenter="hoverIncell(index,index1)" @mouseleave="hoverOutcell(index,index1)">

              <el-dropdown placement="bottom-start" @command="chooseFile" trigger="hover">

              <span class="el-dropdown-link">

              <van-cell :title="fileList[index].doclist[index1].docname" clickable  style="margin-top:12px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);width: 300px" v-if="index1%2==1">

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
          <el-dropdown-menu slot="dropdown"  style="width: 220px">
            <van-row><van-col span="2"></van-col>
              <van-col span="20">
                <el-dropdown-item>
                  <van-image
                    width="20"
                    height="20"
                    :src="require('../../assets/file.jpg')"/>&nbsp;
                  {{fileList[index].doclist[index1].docname}}</el-dropdown-item>
                <el-dropdown-item icon="el-icon-edit-outline" divided command="1" >恢复</el-dropdown-item>
                <el-dropdown-item icon="el-icon-delete" style="color: red" command="2">删除</el-dropdown-item>
              </van-col>
              <van-col span="2"></van-col>
            </van-row>
          </el-dropdown-menu>
          </el-dropdown>
          </div>
        </van-col>

        <van-col span="2">
        </van-col>
      </van-row>

      <br/>
    </div>

  </div>
</template>

<script>
    import axios from "axios";
    import baseUrl from "../baseUrl";
    import {Dialog, Toast} from "vant";

    export default {
        name: "RecycleBin",
      data() {
          return{
            myemail:localStorage.getItem('myemail'),//登着的邮箱
            imgSrc:require('../../assets/loginback.jpg'),

            // 图标部分
            nowindex: {index: -1, index1: -1},
            newDocName: '',
            showRenameDialog: false,

            fileList:[ // 最近浏览 一个月
              {date:'2020/8/12',doclist:[{docname:'hello',docid:'1',doc:'你好1',owner:'youabcd'},{docname:'hello',docid:'2',doc:'你好2',owner:'youabcd'},{docname:'hello',docid:'3',doc:'你好3',owner:'youabcd'}]},
              {date:'2020/8/11',doclist:[{docname:'hello',docid:'4',doc:'你好4',owner:'youabcd'},{docname:'hello',docid:'5',doc:'你好5',owner:'youabcd'},{docname:'hello',docid:'6',doc:'你好6',owner:'youabcd'}]},
              {date:'2020/8/10',doclist:[{docname:'hello',docid:'7',doc:'你好7',owner:'youabcd'},{docname:'hello',docid:'8',doc:'你好8',owner:'youabcd'},{docname:'hello',docid:'12',doc:'你好12',owner:'youabcd'}]},
              {date:'2020/8/9',doclist:[{docname:'hello',docid:'9',doc:'你好9',owner:'youabcd'},{docname:'hello',docid:'10',doc:'你好10',owner:'youabcd'},{docname:'hello',docid:'11',doc:'你好11',owner:'youabcd'}]},
            ],
          }
      },
      methods:{

      //清空回收站
        clearRecycelBin(){
              //Toast(this.myemail);
              let _this = this;
              let data = new FormData();
              data.append('userId',this.myemail);
              axios.post(baseUrl+'/userClearRecycleBin',data)
                .then(function (response) {
                  Toast(response.data.message);
                  if(response.data.success){
                    _this.loadData();
                  }
                })
                .catch(function (err) {
                })

        },
        clearclick(){},


        remainDays(d){
          let now = new Date();
          let date = now.getTime() - new Date(d).getTime();
          return 30-Math.floor(date/(24*3600*1000));
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
          if(command==1)
            this.reverseFile(index);
          else if(command==2)
            this.deleteFile(index);
        },
        // 恢复文件
        reverseFile(index){
          let msg = '确定恢复文件？';
          Dialog.confirm({
            title: '恢复文件',
            message: msg,
          })
            .then(() => {
              let _this = this;
              let data = new FormData();
              data.append('userId',this.myemail);
              data.append('docId',this.fileList[index.index].doclist[index.index1].docid);
              axios.post(baseUrl+'/userReverseFile',data)
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
        // 彻底删除文件
        deleteFile(index){
          let msg = '确定删除文件？回收站中删除的文件将不可恢复。';
          Dialog.confirm({
            title: '删除文件',
            message: msg,
          })
            .then(() => {
              let _this = this;
              let data = new FormData();
              data.append('userId',this.myemail);
              data.append('docId',this.fileList[index.index].doclist[index.index1].docid);
              axios.post(baseUrl+'/userDeleteFileThorough',data)
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


        loadData(){ // 加载数据
          let _this = this;
          let data = new FormData();
          data.append('userId',this.myemail);
          axios.post(baseUrl+'/getUserBinDoc',data)
            .then(function (response) {
              console.log(response.data);
              _this.fileList = [];
              for(let i=0; i<response.data.length; i++){
                let dlist = [];
                for(let j=0; j<response.data[i].docList.length;j++){
                  dlist.push(response.data[i].docList[j]);
                }
                _this.fileList.push({date:response.data[i].date, doclist: dlist});
              }
              console.log(_this.fileList);
            })
            .catch(function (err) {
            })
        }
      },
      mounted() {
        this.loadData();
      }
    }
</script>

<style scoped>

</style>
