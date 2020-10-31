<template>
<div id="app" @contextmenu="showMenu"
    style="width: 100px;height: 100px;margin-top: 20px;background: red;">
    <vue-context-menu :contextMenuData="contextMenuData"
        @home="home"
        @deletedata="deletedata"></vue-context-menu>



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
<div  v-for="(item,index1) in fileList[index].doclist" :key="item">
  <el-dropdown placement="bottom-start" @command="chooseFile" trigger="click" ref="dropdownleft">

      <span class="el-dropdown-link">

        <van-cell :title="fileList[index].doclist[index1].docname" clickable  style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);width: 300px" v-if="index1%2==0" @click="clickFile(index,index1)">

          <template #icon>
            <van-image
              width="45"
              height="45"
              :src="require('../assets/file.jpg')">
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
              :src="require('../assets/file.jpg')"/>&nbsp;
            {{fileList[index].doclist[index1].docname}}</el-dropdown-item>
          <el-dropdown-item divided icon="el-icon-edit-outline" command="0">预览文件</el-dropdown-item>
          <el-dropdown-item icon="el-icon-edit-outline" command="1" :disabled="item.power<2">编辑文件</el-dropdown-item>
          <el-dropdown-item icon="el-icon-star-off" divided command="2">收藏</el-dropdown-item>
          <el-dropdown-item icon="el-icon-share" command="3">分享</el-dropdown-item>
          <el-dropdown-item icon="el-icon-top-right" command="4" divided>导出</el-dropdown-item>
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
        
        <van-col span="10">

<!--列表显示右侧-->
        <div  v-for="(item,index1) in fileList[index].doclist" :key="item" >
          <el-dropdown placement="bottom-start" @command="chooseFile" trigger="click">
              <span class="el-dropdown-link">

              <van-cell :title="fileList[index].doclist[index1].docname" clickable  style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);width: 300px" v-if="index1%2==1"  @click="clickFile(index,index1)">
               <template #icon>
               <van-image
                width="45"
                height="45"
                :src="require('../assets/file.jpg')"/>
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
                      :src="require('../assets/file.jpg')"/>&nbsp;
                    {{fileList[index].doclist[index1].docname}}
                  </el-dropdown-item>
                  <el-dropdown-item divided icon="el-icon-edit-outline" command="0">预览文件</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-edit-outline" command="1" :disabled="fileList[index].doclist[index1].power<2">编辑文件</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-star-off" divided command="2">收藏</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-share" command="3">分享</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-top-right" command="4" divided>导出</el-dropdown-item>
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
</van-row>
</div>
</div>
</template>
<script>
export default {
  name: 'mouseClcik',
  data () {
    return {
    // 菜单数据
        contextMenuData: {
            menuName: 'demo',
            //菜单显示的位置
            axis: {
                x: null,
                y: null
            },
            //菜单选项
            menulists: [{
                fnHandler: 'home', //绑定事件
                icoName: 'fa fa-home fa-fw', //icon图标
                btnName: '回到主页' //菜单名称
            },
            {
                fnHandler: 'deletedata',
                icoName: 'fa fa-minus-square-o  fa-fw',
                btnName: '删除布局'
            }]
          },
          fileList:[ // 最近浏览 一个月
                {date:'8/7',doclist:[{docname:'you',docId:'fsd',owner:'dfsafd',doc:'fsdsdfsdf'},{docname:'you',docId:'fsd',owner:'dfsafd',doc:'fsdsdfsdf'}]}
             ],
    }
  },
  methods:{
    showMenu () {
        event.preventDefault()
        var x = event.clientX
        var y = event.clientY
        // Get the current location
      this.contextMenuData.axis = {
        x, y
      }
    },
    homed () {
      alert("主页")
    },
    deletedata () {
      console.log('delete!')
    },
  },
}
</script>