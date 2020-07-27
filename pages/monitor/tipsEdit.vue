<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body"
         v-loading="load_data"
         element-loading-text="拼命加载中">
      <el-row>
        <el-col :span="8">
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="公告标题:" prop="infotitle">
              <el-input v-model="form.infotitle" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item v-if="flag" label="作者" prop="infoname">
              <el-input v-model="form.infoname" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="公告内容:" prop="infocontext">
              <quill-editor
                id="QE"
                v-model="form.infocontext"
                :options="editorOption">
              </quill-editor>
            </el-form-item>
            <el-form-item label="关键词" prop="keyword">
              <el-input
                placeholder="请输入内容"
                v-model="form.keyword"
                style="width: 250px;">
              </el-input>
            </el-form-item>
            <el-form-item label="是否HOT:" prop="hot">
              <el-radio-group v-model="form.hot">
                <el-radio :label="true">是</el-radio>
                <el-radio :label="false">否</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item  v-if="flag" label="最近编辑时间:" prop="infotime">
              <el-input
                :disabled="flag"
                v-model="form.infotime"
                style="width: 200px;">
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="on_submit_form" :loading="on_submit_loading">立即提交</el-button>
              <el-button @click="$router.back()">取消</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script type="text/javascript">
  import {panelTitle} from 'components'
  import { quillEditor } from 'vue-quill-editor'
  const url ="/api/infoserver"
  import {mapGetters} from 'vuex'
  import {GET_USER_INFO} from 'store/getters/type'
  import axios from 'axios'
  Date.prototype.Format = function (fmt) {
    var o = {
      "M+": this.getMonth() + 1, // 月份
      "d+": this.getDate(), // 日
      "h+": this.getHours(), // 小时
      "m+": this.getMinutes(), // 分
      "s+": this.getSeconds(), // 秒
      "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
      "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt))
      fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
      if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
  }
  export default{
    components:{
      quillEditor
    },
    computed: {
      ...mapGetters({
        get_user_info: GET_USER_INFO
      })
    },
    data(){
      return {
        editorOption:{},
        flag:true,
        form: {
          infocontext:null,
          infotime:null,
          hot:false,
          keyword: null,
          inform_id: null,
          infotitle: null,
          infoname:null
        },
        route_id: this.$route.params.inform_id,
        load_data: false,
        on_submit_loading: false,
        rules: {
          infotitle: [{required: true, message: '请输入标题', trigger: 'blur'}],
          infocontext: [{required: true, message: '请输入内容', trigger: 'blur'}],
          infoname: [{required: true, message: '请输入作者', trigger: 'blur'}],
          // keyword: [{required: true, message: '请输入关键词', trigger: 'blur'}]
        }
      }
    },
    created(){
      this.route_id && this.get_form_data()
      if(typeof (this.route_id)==="undefined"){
        this.flag=false
      }
      if(!this.flag){
        this.form.infoname=this.get_user_info.user.realname
        // console.log(this.form.infoname)
      }
    },
    methods: {
      //获取数据
      get_form_data(){
        this.load_data = true
        axios.get(url,{
          params:{
            method:"getInfo",
            inform_id:this.route_id
          }
        }).then(({data})=>{
          console.log(data)
          this.form=data
          this.load_data = false
        })
      },
      //提交
      on_submit_form(){
        this.$refs.form.validate((valid) => {
          if (!valid) return false
          this.on_submit_loading = true
          this.form.infotime=new Date().Format("yyyy-MM-dd   hh:mm:ss");

          var method =this.flag?"changeInfo":"addInfo"
          console.log(this.form)
          axios.get(url,{
            params:{
              method:method,
              info:this.form
            }
          })
            .then((res) => {
              // console.log(res)
              this.$message.success(res.data)
              setTimeout(this.$router.back(), 500)
            })
            .catch((err) => {
              this.load_data = false
              this.on_submit_loading = false
              var message =""
              if(err.response.status === 404){
                message="信息有误，添加失败！"
              }
              this.$notify.info({
                title: '温馨提示',
                message:message,
              })
            })
        })
      }
    },
    components: {
      panelTitle
    }
  }
</script>
<style>
  #QE{
    width:1300px;
    height: 400px;
    margin-bottom: 60px ;
  }
</style>
