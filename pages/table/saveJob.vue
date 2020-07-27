<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body"
         v-loading="load_data"
         element-loading-text="拼命加载中">
      <el-row>
        <el-col :span="8">
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="岗位id:" prop="jobid" v-if="false">
              <el-input :disabled="flag" v-model.number="form.jobid" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="岗位名称:" prop="jobname">
              <el-input v-model="form.jobname" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="组织：">
              <el-select v-model.number="organ_id" placeholder="请选择">
                <el-option
                  v-for="item in organlist"
                  :key="item.organ_id"
                  :label="item.organ_name"
                  :value="item.organ_id"
                  @click.native="getDeptlist">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="部门：" prop="deptid">
              <el-select v-model.number="form.deptid"  placeholder="请选择">
                <el-option
                  v-for="item in deptlist"
                  :key="item.dept_id"
                  :label="item.dept_name"
                  :value="item.dept_id">
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
  const url="/api/jobserver"
  const baseurl ="/api/personserver"
  export default{
    data(){
      return {
        flag:true,
        organ_id:null,
        form: {
          jobname:null,
          jobid: null,
          deptid:null
        },
        route_id: this.$route.params.job_id,
        load_data: false,
        on_submit_loading: false,
        rules: {
          jobname: [{required: true, message: '岗位名不能为空', trigger: 'blur'}],
          // jobid: [{type:"number",required: true, message: '岗位ID不能为空', trigger: 'blur'}],
          deptid: [{type:"number",required: true, message: '请选择所属部门', trigger: 'blur'}],
        },
        deptlist:[],
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
            method:"queryById",
            jobid:this.route_id
          }
        })
          .then(({data})=>{
            this.form=data
            this.load_data = false
            axios.get(baseurl,{
              params:{
                method:"queryOrganid",
                dept_id:this.form.deptid
              }
            }).then(({data}) => {
              this.organ_id = data
              axios.get(baseurl, {
                params: {
                  method: "queryOrganlist",
                }
              }).then(({data}) => {
                this.organlist = data
                axios.get(baseurl, {
                  params: {
                    method: "queryDeptlist",
                    organ_id: this.organ_id
                  }
                }).then(({data}) => {
                  this.deptlist = data
                  axios.get(url, {
                    params: {
                      method: "queryJoblist",
                      dept_id: this.form.deptid
                    }
                  })
                    .then(({data}) => {
                      this.joblist = data
                    })
                })
              })
            })
          })
          .catch(()=>{
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
      getDeptlist(){
        this.form.deptid=null
        this.deptlist=[]
        axios.get(baseurl,{
          params:{
            method:"queryDeptlist",
            organ_id:this.organ_id
          }
        })
          .then(({data}) => {
            this.deptlist=data
          })
          .catch(() => {
            this.load_data = false
          })
      },
      //提交
      on_submit_form(){
        this.$refs.form.validate((valid) => {
          if (!valid) return false
          var method =this.flag?"changeJob":"addJob"
          this.on_submit_loading = true
          axios.get(url, {
            params: {
              method:method,
              jobid: this.form.jobid,
              deptid: this.form.deptid,
              jobname: this.form.jobname
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
//        this.$refs.form.validate((valid) => {
//          if (!valid) return false
//          this.on_submit_loading = true
//          this.$fetch.api_table.save(this.form)
//            .then(({msg}) => {
//              this.$message.success(msg)
//              setTimeout(this.$router.back(), 500)
//            })
//            .catch(() => {
//              this.on_submit_loading = false
//            })
//        })
      },
    },
    components: {
      panelTitle
    }
  }
</script>
