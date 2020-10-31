<template>
    <div>

<!--评论区-->
        <div style="height:100px;widthd:100%;">

            <van-cell autosize 
            :title="CommentList[currentPage*3-3].username"
            title-style="text-align:left; margin-left:10px;min-width: 65%;"
            :icon="CommentList[currentPage*3-3].userhead" 
            style="white-space: pre-wrap;">
                <template #icon>
                    <van-image
                         width="40"
                         round
                          height="40"
                          v-bind:src="CommentList[currentPage*3-3].userhead">
                    </van-image>
                </template>
                    <van-icon name="delete" v-if="onemail==CommentList[currentPage*3-3].useremail||onemail==author" @click="deletecomment(currentPage*3-3)"/>
                <template #label>
                    <div>{{CommentList[currentPage*3-3].comments}}</div>
                </template>
            </van-cell>

            <van-cell autosize 
            :title="CommentList[currentPage*3-2].username" 
            title-style="text-align: left; margin-left:10px;min-width: 65%;" 
            :icon="CommentList[currentPage*3-2].userhead" 
            style="white-space: pre-wrap;" 
            v-if="currentPage*3-2<CommentList.length">
                <template #icon>
                    <van-image
                         width="40"
                         round
                          height="40"
                          v-bind:src="CommentList[currentPage*3-2].userhead">
                    </van-image>
                </template>
                    <van-icon name="delete" v-if="onemail==CommentList[currentPage*3-2].useremail||onemail==author" @click="deletecomment(currentPage*3-2)"/>
                <template #label>
                    <div v-bind:style="{width:'100%'}">{{CommentList[currentPage*3-2].comments}}
                    </div>
                </template>
            </van-cell>

            <van-cell autosize 
            :title="CommentList[currentPage*3-1].username" 
            title-style="text-align: left; margin-left:10px;min-width: 65%;" 
            :icon="CommentList[currentPage*3-1].userhead"
            style="white-space: pre-wrap;" 
            v-if="currentPage*3-1<CommentList.length">
                <template #icon>
                    <van-image
                         width="40"
                         round
                          height="40"
                          v-bind:src="CommentList[currentPage*3-1].userhead">
                    </van-image>
                </template>
                    <van-icon name="delete" v-if="onemail==CommentList[currentPage*3-1].useremail||onemail==author" @click="deletecomment(currentPage*3-1)"/>
                <template #label>
                    <div>{{CommentList[currentPage*3-1].comments}}
                    </div>
                </template>
            </van-cell>

            <van-sticky :offset-top="500">
              <van-row>
              <van-col span="20">
              </van-col>
              <van-col span="4">
                <van-pagination
                v-model="currentPage"
                :total-items=CommentList.length
                :show-page-size="5"
                :items-per-page="3"
                force-ellipses>
                </van-pagination>
              </van-col>
              </van-row>
            </van-sticky>
        </div>


<!--发表评论-->
        <div style="margin-top:380px">
            <el-input
            type="textarea"
            placeholder="请输入你的评论"
            :autosize="{ minRows: 4, maxRows: 7}"
            v-model="message"
            maxlength="100"
            show-word-limit>
            </el-input>

            <el-button type="warning" @click="clear">清空输入</el-button>
            <el-button type="info" @click="submit">发表评论</el-button>


            
        </div>
    </div>
</template>

<script>
    import {Toast} from "vant";
	import axios from 'axios';
    import baseUrl from './baseUrl'
    export default{
        name:'Comment',
        data(){
            return{
                currentPage: 1,
                message:'',
                onemail:localStorage.getItem("myemail"),
                author:'567', 
				docId:'',
                CommentList:[
                    {userhead:' ',username:' ',comments:'暂无评论',useremail:' ',commentid:' '}
                ],
            }
        },
        props:{
            docId:{
                type:String,
                docId: null,
                required: true
            }
        },
        methods:{
            clear(){
                this.message='';
            },
            submit(){//提交评论
			  console.log('submit!');
			  let _this=this;
			  let data = new FormData();
			  data.append('userId',this.onemail);
			  data.append('docId',this.docId);
			  data.append('content',this.message);
			  axios.post(baseUrl+'/submitComment', data)
			  .then(function(response){//从后端取值
				if(response.data.success === true) {
				  Toast(response.data.message);
				  _this.loadcomment();
				  _this.clear();
				}
				else { // 登录失败 ，，，
				  Toast(response.data.message);
				}
			  })
			  this.loadcomment();
            },
            deletecomment(index){//删除评论
				console.log('delete!');
				let _this=this;
				let data = new FormData();
				data.append('commentId',this.CommentList[index].commentid);
				axios.post(baseUrl+'/deleteComment', data)
				.then(function(response){//从后端取值
				if(response.data.success === true) {
				  Toast(response.data.message);
				  _this.loadcomment();
				}
				else {
				  Toast(response.data.message);
				}
			  })
            },
			loadcomment(){
			console.log('submit!');
			 let _this=this;
			  let data = new FormData();
			  data.append('docId',this.docId);
			  axios.post(baseUrl+'/initialComment', data)
			   .then(function (response) {
			   if(response.data.length>0){
                _this.CommentList = [];}
                for(let i=0; i<response.data.length; i++){
                  _this.CommentList.push(response.data[i]);
                }
            })
            .catch(function (err) {
            })
			},
			loadauthor(){
			console.log('loadauthor!');
				let _this=this;
			  let data = new FormData();
			  data.append('docId',this.docId);
			  axios.post(baseUrl+'/loadauthor', data)
			   .then(function (response) {
			   _this.author=response.data.result;
			    console.log(_this.author);
            })
			}
        },
		mounted(){
			 this.loadcomment();
			 this.loadauthor();
			},
    }
</script>

<style>
</style>