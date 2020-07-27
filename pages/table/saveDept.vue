<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body"
         v-loading="load_data"
         element-loading-text="拼命加载中">
      <el-row>
        <el-col :span="8">
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="部门ID:" prop="dept_id" v-if="false">
              <el-input :disabled="flag" v-model.number="form.dept_id" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="部门名称:" prop="dept_name">
              <el-input v-model="form.dept_name" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="所属组织：" prop="organ_id">
              <el-select v-model.number="form.organ_id" placeholder="请选择">
                <el-option
                  v-for="item in organlist"
                  :key="item.organ_id"
                  :label="item.organ_name"organ_id
                  :value="item.organ_id">
                </el-option>
              </el-select>
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
  import axios from 'axios'
  const url="/api/deptserver"
  const baseurl ="/api/personserver"


  export default{
    data(){
      return {
        flag:true,
        form: {
          dept_id:null,
          dept_name: null,
          organ_id:null,
        },
        route_id: this.$route.params.dept_id,
        load_data: false,
        on_submit_loading: false,
        rules: {
          dept_name: [{required: true, message: '部门名称不能为空', trigger: 'blur'}],
          // dept_id: [{type:"number",required: true, message: '部门ID不能为空', trigger: 'blur'}],
          organ_id: [{type:"number",required: true, message: '所属组织不能为空', trigger: 'blur'}],
        },
        organlist:[]
      }
    },
    created(){
      this.getOrganlist()
      this.route_id && this.get_form_data()
      if(typeof (this.route_id)==="undefined"){
        this.flag=false
      }
      this.type=this.flag?"number":""
    },
    methods: {
      //获取数据
      get_form_data(){
        this.load_data = true
        axios.get(url,{
          params:{
            method:"getDept",
            dept_id:this.route_id
          }
        }).then(({data})=>{
          this.form=data
          this.load_data = false
        })
      },
      getOrganlist(){
        axios.get(baseurl,{
          params:{
            method:"queryOrganlist",
          }
        })
          .then(({data}) => {
            this.organlist=data
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
          var method =this.flag?"changeDept":"addDept"
          axios.get(url, {
            params: {
              method:method,
              dept_id: this.form.dept_id,
              organ_id: this.form.organ_id,
              dept_name: this.form.dept_name
            }
          }).then((res) => {
            console.log(res)
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
