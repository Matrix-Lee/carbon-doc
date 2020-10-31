<template>
  <div id="editor1">
    <van-row>
    <van-col span="2">
        <div v-if="readnow==='false'" style="margin-top:20px;">
          <van-button block type="primary" :disabled="editingIndex!=-1" @click="editDoc">编辑</van-button>
        </div>
        <div v-if="readnow==='false'" style="margin-top:20px;">
          <van-button block type="success" :disabled="editingIndex==-1 || list[editingIndex].userId!=myemail" @click="submitChange">提交</van-button>
        </div>
        <div v-if="readnow==='false'" style="margin-top:20px;">
          <van-button block type="info" :disabled="editingIndex==-1 || list[editingIndex].userId!=myemail" @click="cancelChange">取消</van-button>
        </div>
        <div style="margin-top:20px;">
          <van-button block type="success" @click="btnWord">导出</van-button>
        </div>
    </van-col>

    <van-col span="1"></van-col>

    <van-col span="21">
    <textarea :id='id' :value='value' class="download"></textarea>

    </van-col>

    </van-row>

  </div>
</template>

<script>
  // Import TinyMCE
  import axios from 'axios';
  import baseUrl from "./baseUrl";
  import {Toast} from "vant";
  import tinymce from 'tinymce/tinymce'
  import 'tinymce/themes/silver/theme'
  import 'tinymce/plugins/advlist' // 这几条引入是因为我的导入不了，不知道为啥
  import 'tinymce/plugins/link'
  import 'tinymce/plugins/image'
  import 'tinymce/plugins/code'
  import 'tinymce/plugins/table'
  import 'tinymce/plugins/textcolor'
  import 'tinymce/plugins/paste'
  import 'tinymce/plugins/textcolor'
  import 'tinymce/plugins/colorpicker'
  import 'tinymce/icons/default/icons.min.js'
  import saveAs from 'file-saver'
  import '../../static/js/jquery.wordexport'
  const INIT = 0
  const CHANGED = 2
  // var EDITOR = null
  export default {
    props: {
      value: {
        type: String,
        editor: null,
        required: true
      },
      docid:{
      },
      setting: {},
      url: { // 接口
        default: '',
        type: String
      },
      accept: { // 文件类型
        default: 'image/jpeg, image/png, image/jpg',
        type: String
      },
      maxSize: { // 大小
        default: 2097152,
        type: Number
      },
      withCredentials: {
        default: false,
        type: Boolean
      }
    },
    /*    watch: {
          value: function(val) {
            if (this.status === INIT || tinymce.activeEditor.getContent() !== val) {
              tinymce.activeEditor.setContent(val)
            }
            this.status = CHANGED;
          }
        },*/

    data() {
      return {
        myemail: localStorage.getItem("myemail"), //登着的邮箱
        status: INIT,
        id: 'editor-' + new Date().getMilliseconds(),
        editingIndex: "-1",
        list: [
          {userImage:'https://img.yzcdn.cn/vant/cat.jpeg',userName:'youabcd1',userId:'1322496098@qq.com'},
          {userImage:'https://img.yzcdn.cn/vant/cat.jpeg',userName:'youabcd2',userId:'13224'},
          {userImage:'https://img.yzcdn.cn/vant/cat.jpeg',userName:'youabcd3',userId:'13224'},
          {userImage:'https://img.yzcdn.cn/vant/cat.jpeg',userName:'youabcd4',userId:'13224'},
        ],
        cantEdit: true,
        userediting: false,
        // 定时函数
        timer: null,
        timer1: null,
        docInfo:{docId:'1',docName:'1',authorName:'x',authorId:'x',cooperatorId:['a','b','c','d','e']},

        readnow: localStorage.getItem('readnow'),

        textValue: '',
      }
    },
    methods: {

      //保存至本地
      btnWord(){
          console.log(this.value);
          let _this = this;
          this.$router.push({
            path:'/down',
            query:{value:_this.textValue,docName:_this.docInfo.docName
            },
          });

/*
          const foo = this.value;
          const blob = new Blob([JSON.stringify(foo)], {type: "text/plain"});
          const fileName = `${new Date().valueOf()}.doc`;
          const link = document.createElement('a');
          link.href = window.URL.createObjectURL(blob);
          link.download = fileName;
          link.click();
          window.URL.revokeObjectURL(link.href);*/
      },

      // 向后端传输数据
      sendData(){
        let _this=this;
        let data = new FormData();
        data.append('value',this.value);
        data.append('userId',this.myemail);
        data.append('docId',this.docid);
        console.log(this.value)
        axios.post(baseUrl+'/docSave', data)
          .then(function(response){//从后端取值
          })
      },
      // 加载文件数据
      loadData(){
        let _this = this;
        let data = new FormData();
        data.append('userId',this.myemail);
        data.append('docId',this.docid);
        axios.post(baseUrl+'/getdocData', data)
          .then(function(response){//从后端取值
            console.log(response.data)
            if(response.data.success) {
              _this.value = response.data.result.value;
              _this.textValue = response.data.result.value;
              // 同步内容
              tinymce.activeEditor.setContent(_this.textValue);
            }
          })
      },
      // 加载围观的用户
      loadEditingUsers(){
        let _this = this;
        let data = new FormData();
        data.append('userId',this.myemail);
        data.append('docId',this.docid);
        axios.post(baseUrl+'/loadEditingUsers', data)
          .then(function(response){//从后端取值
            // 同步内容
            _this.list = [];
            for(let i=0;i<response.data.result.users.length;i++){
              _this.list.push(response.data.result.users[i]);
            }
            _this.editingIndex = response.data.message; // 正在编辑的人的序号
            _this.$emit('update', _this.list, _this.editingIndex);
          })
      },
      // 用户退出文件，编辑结束
      submitDoc(){
        if(this.userediting) {
          this.submitChange();
        }
        let _this = this;
        let data = new FormData();
        data.append('userId',this.myemail);
        data.append('docId',this.docid);
        axios.post(baseUrl+'/userCloseFile', data)
          .then(function(response){
          })
      },
      // 用户提交更改
      submitChange(){
        this.userediting = false;
        this.sendData();
        let _this = this;
        let data = new FormData();
        data.append('userId',this.myemail);
        data.append('docId',this.docid);
        axios.post(baseUrl+'/userSubmitChange', data)
          .then(function(response){//从后端取值
            Toast(response.data.message);
            if(response.data.success){
              tinymce.activeEditor.getBody().setAttribute('contenteditable',false);
              _this.loadData();
              _this.loadEditingUsers();
            }
          })
      },
      // 申请编辑文件
      editDoc(){
        this.loadData();
        let _this = this;
        let data = new FormData();
        data.append('userId',this.myemail);
        data.append('docId',this.docid);
        axios.post(baseUrl+'/wantEditDoc',data)
          .then(function (response) {
            Toast(response.data.message);
            if(response.data.success){
              _this.userediting = true;
              tinymce.activeEditor.getBody().setAttribute('contenteditable',true);
            }
          })
      },
      cancelChange(){
        this.loadData();
        this.submitChange();
      },
      checkLoadData(){
        let _this = this;
        let data = new FormData();
        data.append('userId',this.myemail);
        data.append('docId',this.docid);
        axios.post(baseUrl+'/checkDocData', data)
          .then(function(response){//从后端取值
            if(response.data.success) {
              _this.value = response.data.result.value;
              // 同步内容
              tinymce.activeEditor.setContent(_this.value);
            }
          })
      },
      // 轮询
      checkData(time){
        this.timer=setInterval(()=>{
          //this.loadEditingUsers();
          this.checkLoadData();
        },time);
      },
      // 轮询
      checkUsers(time){
        this.timer1=setInterval(()=>{
          this.loadEditingUsers();
          //this.checkLoadData();
        },time);
      }
    },
    beforeUpdate() {
      //     if(this.value != '<p></p>')
      //       this.sendData();
    },
    destroyed() {
      clearInterval(this.timer);
      this.timer = null;
      clearInterval(this.timer1);
      this.timer1 = null;

      this.submitDoc();
    },

    mounted() {
      let _this = this;
      let data = new FormData();
      data.append('docId',this.docid);
          axios.post(baseUrl+'/getdocInfo',data)
          .then(function (response) {
              _this.docInfo = response.data.result;
          });
      this.loadData();
      this.checkUsers(1000);
      this.checkData(3000);
      //const _this = this;
      const setting =
        {
          selector: '#' + _this.id,
          upload_image_url: 'static/upload/', // 配置的上传图片的路由
          language_url: '/static/lang/zh_CN.js',
          language: 'zh_CN',
          init_instance_callback: function(editor) {
            // EDITOR = editor
            console.log('Editor: ' + editor.id + ' is now initialized.')
            editor.on('input change undo redo', () => {
              var content = editor.getContent()
              _this.$emit('show', content)
            })
          },
          content_style: `
    *                         { padding:0; margin:0; }
    html, body                { height:100%; }
    img                       { max-width:100%; display:block;height:auto; }
    a                         { text-decoration: none; }
    iframe                    { width: 100%; }
    p                         { line-height:1.6; margin: 0px; }
    table                     { word-wrap:break-word; word-break:break-all; max-width:100%; border:none; border-color:#999; }
    .mce-object-iframe        { width:100%; box-sizing:border-box; margin:0; padding:0; }
    ul,ol                     { list-style-position:inside; }
  `,
          insert_button_items: 'image link | inserttable',
          paste_retain_style_properties: 'all',
          paste_word_valid_elements: '*[*]', // word需要它
          paste_data_images: true, // 粘贴的同时能把内容里的图片自动上传，非常强力的功能
          paste_convert_word_fake_lists: false, // 插入word文档需要该属性
          paste_webkit_styles: 'all',
          paste_merge_formats: true,
          nonbreaking_force_tab: false,
          paste_auto_cleanup_on_paste: false,
          'plugins': [
            'advlist link image',
            'code',
            'table textcolor paste textcolor colorpicker'
          ], // 配置
          'toolbar_items_size': 'small',
          'block_formats': 'Paragraph=p;Heading 1=h1;Heading 2=h2;Heading 3=h3;Heading 4=h4;Heading 5=h5;Heading 6=h6;',
          'toolbar1': 'table |insertfile undo redo | formatselect | link unlink | uploadimg image media', // 工具栏1
          'toolbar2': ' fontsizeselect | forecolor backcolor | fontselect bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | outdent indent | removeformat', // 工具栏2
          // 图片上传
          images_upload_handler: function (blobInfo, success, failure) {
            var reader = new FileReader();
            reader.readAsDataURL(blobInfo.blob());
            reader.onload = function () {
              success(this.result);
            }
          }
        }
      Object.assign(setting, _this.setting)
      tinymce.init(setting);

      setTimeout(()=>{
        tinymce.activeEditor.getBody().setAttribute('contenteditable',false);
      },1000);
    },
    beforeDestroy: function() {
      tinymce.get(this.id).destroy()
    }
  }
</script>
