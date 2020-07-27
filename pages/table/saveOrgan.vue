<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body"
         v-loading="load_data"
         element-loading-text="拼命加载中">
      <el-row>
        <el-col :span="8">
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="组织ID:" prop="organ_id" v-if="false">
              <el-input :disabled="flag" type="number" v-model.number="form.organ_id" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="组织名称:" prop="organ_name">
              <el-input v-model="form.organ_name" placeholder="请输入内容" style="width: 250px;"></el-input>
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
  const url ="/api/organserver"
  import axios from 'axios'


  export default{
    data(){
      return {
        flag:true,
        type:"",
        form: {
          organ_id:null,
          organ_name: null,
        },
        route_id: this.$route.params.organ_id,
        load_data: false,
        on_submit_loading: false,
        rules: {
          organ_name: [{required: true, message: '请输入组织名称',trigger: 'blur'}],
          // organ_id: [{type:"number",required: true, message: '请输入组织ID',trigger: 'blur'}]
        }
      }
    },
    mounted(){
      // console.log(this.route_id)
      this.route_id && this.get_form_data()
      if(typeof (this.route_id)==="undefined"){
        this.flag=false
      }
      this.type=this.flag?"number":""
      console.log(typeof (this.type))
    },
    methods: {
      //获取数据
      get_form_data(){
        this.load_data = true
        // this.$fetch.api_table.get({
        //   organid: this.route_id
        // })
        axios.get(url,{
          params:{
            method:"getorgan",
            organ_id:this.route_id
          }
        })
          .then(({data}) => {
            console.log(data)
            this.form.organ_id = data.organ_id
            this.form.organ_name=data.organ_name
            this.load_data = false
          })
          .catch(() => {
            this.load_data = false
          })
      },
      //提交
      on_submit_form(){
        this.$refs.form.validate((valid) => {
          if (!valid) return false
          this.on_submit_loading = true
          var method =this.flag?"changeorgan":"addorgan"
          console.log(this.form.organ_name)
          var organ_id=this.form.organ_id*1
          var organ_name=this.form.organ_name
          axios.get(url,{
            params:{
              method:method,
              organ_id:organ_id,
              organ_name:organ_name
            }
          })
            .then((res) => {
              console.log(res)
              this.$message.success(res.data)
              setTimeout(this.$router.back(), 500)
            })
            .catch((err) => {
              this.load_data = false
              this.on_submit_loading = false
              var message =err.response.data
              if(err.response.status === 404){
                message="ID重复，添加失败！"
              }
              this.$notify.info({
                title: '温馨提示',
                message:message,
              })
            })
          // this.$fetch.api_table.save(this.form)
          //   .then(({msg}) => {
          //     this.$message.success(msg)
          //     setTimeout(this.$router.back(), 500)
          //   })
          //   .catch(() => {
          //     this.on_submit_loading = false
          //   })
        })
      }
    },
    components: {
      panelTitle
    }
  }
</script>
