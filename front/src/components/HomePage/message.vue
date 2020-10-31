<template>
  <el-container>

    <el-main>
      <div>
        <h2 style="color: darkgrey">我的消息</h2>
        <br />

        <el-tabs v-model="activeName" @tab-click="handleClick">

<!--新消息-->
          <el-tab-pane label="新消息" name="first">
            <el-table
              :data="newmessage"
              style="width: 90%"
              :default-sort="{ prop: 'date', order: 'descending' }"
            >
            <template slot="empty">
                <span style="">
                    暂未收到新消息
                </span>
            </template>
              <el-table-column align="center" width="50">
                <el-badge is-dot class="item">
                  <i class="el-icon-message-solid"></i>
                </el-badge>
              </el-table-column>
              <el-table-column prop="message" label="信息" align="center"></el-table-column>
              <el-table-column prop="date" label="日期" align="center" width="150px"></el-table-column>
              <el-table-column prop="people" label="发件人" align="center"></el-table-column>
              <el-table-column label="操作" align="center" width="150px">
                <template slot-scope="scope">
                  <!--type=='1'-->

          <!--分享消息-->
                  <el-tooltip v-if="scope.row.type=='1'" effect="dark" content="查看" placement="top">
                    <el-button
                      size="small"
                      type="primary"
                      icon="el-icon-right"
                      circle
                      @click="changemessage(scope.row)"
                    ></el-button>
                  </el-tooltip>
                  <!--type=='2'-->

          <!--邀请消息-->
                  <el-tooltip v-if="scope.row.type=='2'" effect="dark" content="同意" placement="top">
                    <el-button
                      size="small"
                      type="success"
                      icon="el-icon-check"
                      circle
                      @click="acceptInvite(scope.row)"
                    ></el-button>
                  </el-tooltip>
                  <el-tooltip v-if="scope.row.type=='2'" effect="dark" content="拒绝" placement="top">
                    <el-button
                      size="small"
                      type="danger"
                      icon="el-icon-close"
                      circle
                      @click="rejectInvite(scope.row)"
                    ></el-button>
                  </el-tooltip>
                  <!--type=='3'-->

          <!--查收-->
                  <el-tooltip
                    v-if="scope.row.type=='3'"
                    effect="dark"
                    content="查收"
                    placement="top"
                  >
                    <el-button
                      size="small"
                      type="primary"
                      icon="el-icon-check"
                      circle
                      @click="changemessage(scope.row)"
                    ></el-button>
                  </el-tooltip>
                  <!--type=='4'-->

          <!--查收-->
                  <el-tooltip v-if="scope.row.type=='4'" effect="dark" content="查收" placement="top">
                    <el-button
                      size="small"
                      type="danger"
                      icon="el-icon-check"
                      circle
                      @click="changemessage(scope.row)"
                    ></el-button>
                  </el-tooltip>

          <!--type=='5'-->
                  <el-tooltip v-if="scope.row.type=='5'" effect="dark" content="查收" placement="top">
                    <el-button
                      size="small"
                      type="warning"
                      icon="el-icon-check"
                      circle
                      @click="changemessage(scope.row)"
                    ></el-button>
                  </el-tooltip>

            <!--type=='6'-->
                  <el-tooltip v-if="scope.row.type=='6'" effect="dark" content="查收" placement="top">
                    <el-button
                      size="small"
                      type="warning"
                      icon="el-icon-check"
                      circle
                      @click="changemessage(scope.row)"
                    ></el-button>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>


<!--已读消息-->
          <el-tab-pane label="已读消息" name="second">
            <el-table
              :data="oldmessage"
              style="width: 100%"
              :default-sort="{ prop: 'date', order: 'descending' }"
            >
            <template slot="empty">
                <span style="">
                    暂无已读消息
                </span>
            </template>
              <el-table-column align="center" width="50">
                <i class="el-icon-message-solid"></i>
              </el-table-column>
              <el-table-column prop="message" label="信息" align="center"></el-table-column>
              <el-table-column prop="date" label="日期" align="center" width="120px"></el-table-column>
              <el-table-column prop="people" label="发件人" align="center"></el-table-column>
              <el-table-column label="操作" align="center" width="150px">
                <template slot-scope="scope">
                  <!--type=='1'-->
                  <el-tooltip v-if="scope.row.type=='1'" effect="dark" content="查看" placement="top">
                    <el-button size="small" type="primary" icon="el-icon-right" circle @click="changemessage1(scope.row)"></el-button>
                  </el-tooltip>

                  <el-tooltip effect="dark" content="删除" placement="top">
                    <el-button size="small" type="danger" icon="el-icon-delete" circle @click="deletemessage(scope.row.id)"></el-button>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </div>
	  <div></div>
    </el-main>
  </el-container>
</template>

<script>
import axios from 'axios';
    import baseUrl from './baseUrl'
    import {Toast} from "vant";
  import message from "./message";
  export default {
    name: "test",
    components: { message },
    data() {
      return {
        activeName: "first",
        number: "10",
        drawer: false,
		messagenumber:"0",
		onemail:localStorage.getItem("myemail"),

		timer:null,

        oldmessage: [],
        newmessage: [
          {type: "1",  id: "1",   message: "1",   date: "1",
            people: "",  teamId: "",docId: "",power:"read"}
        ],
      };
    },
    methods: {
	//轮询
	checkData(time){
	this.timer=setInterval(()=>{
          this.loadmessage();
        },time);
	},
      shownumber() {
        return this.number;
      },
      handleClick(tab, event) {
        console.log(tab, event);
      },
      changemessage(temp) {
		      console.log('submit!');
          let _this=this;
          let data = new FormData();
          data.append('messageId',temp.id);
          axios.post(baseUrl+'/manageMessage', data)
          .then(function(response){//从后端取值
            if(response.data.success === true) {
				_this.loadmessage();
				setTimeout(()=>{
				_this.$emit('getChildinfo',_this.messagenumber);},100)
            }
            else { // 登录失败 ，，，
              Toast(response.data.message);
            }
          }

		       );

          if(temp.type=="1"){
              if(temp.power=="write"){
                  localStorage.setItem('readnow',false);
              }
              else{
                  localStorage.setItem('readnow',true);
              }
              this.$router.push({
                  path:'/test',
                  query:{editing:temp.docId},
              });
          }
      },

      changemessage1(temp) {
          console.log('submit!');

          if(temp.type=="1"){
              if(temp.power=="write"){
                  localStorage.setItem('readnow',false);
              }
              else{
                  localStorage.setItem('readnow',true);
              }
              this.$router.push({
                  path:'/test',
                  query:{editing:temp.docId},
              });
          }
      },


//接受邀请
      acceptInvite(temp){
          console.log('acceptInvite!');
          let _this=this;
          let data = new FormData();
          data.append('messageId',temp.id);
          axios.post(baseUrl+'/acceptInvite', data)
          .then(function(response){//从后端取值
            if(response.data.success === true) {
            }
            else {
              Toast(response.data.message);
            }
          }

           );
		   this.changemessage(temp);
      },
	  rejectInvite(temp){
	     console.log('rejectInvite!');
          let _this=this;
          let data = new FormData();
          data.append('messageId',temp.id);
          axios.post(baseUrl+'/rejectInvite', data)
          .then(function(response){//从后端取值
            if(response.data.success === true) {
            }
            else {
              Toast(response.data.message);
            }
          }

           );
		   this.changemessage(temp);

	  },

	  loadmessage(){
			console.log('submit!');
			 let _this=this;
			  let data = new FormData();
			  data.append('userId',this.onemail);
			  axios.post(baseUrl+'/initialMessage', data)
			   .then(function (response) {
                _this.newmessage = [];
				_this.oldmessage = [];
                for(let i=0; i<response.data.result.length; i++){
                  _this.oldmessage.push(response.data.result[i]);
                }
				for(let i=0; i<response.data.result2.length; i++){
                  _this.newmessage.push(response.data.result2[i]);
                }
				_this.messagenumber=response.data.result2.length;
        _this.$emit('getChildinfo',_this.messagenumber);
				console.log(_this.messagenumber);
            })
            .catch(function (err) {
            })
	},
	deletemessage(temp){
		console.log('submit!');
          let _this=this;
          let data = new FormData();
          data.append('messageId',temp);
          axios.post(baseUrl+'/deleteMessage', data)
          .then(function(response){//从后端取值
            if(response.data.success === true) {
				_this.loadmessage();
            }
            else { // 登录失败 ，，，
              Toast(response.data.message);
            }
          }

		  )
	}
    },
	mounted(){
	this.loadmessage();
	this.checkData(3000);
	},
	destroyed(){
	clearInterval(this.timer);
	this.timer=null;
	}
  };
</script>

<style>
  .item {
    margin-top: 10px;
    margin-right: 40px;
  }
</style>
