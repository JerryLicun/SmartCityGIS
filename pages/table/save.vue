<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body"
         v-loading="load_data"
         element-loading-text="拼命加载中">
      <el-row>
        <el-col :span="8">
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="用户ID:" prop="personid" v-if="false">
              <el-input :disabled="flag" type="number" v-model.number="form.personid" placeholder="请输入ID" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="用户名:" prop="username">
              <el-input  v-model="form.username" placeholder="请输入内容" style="width: 250px;" @blur="checkUsername"></el-input>
            </el-form-item>
            <el-form-item label="姓名:" prop="realname">
              <el-input v-model="form.realname" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="密码:" prop="password">
              <el-input type="password" v-model="form.password" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item v-if="!flag" label="确认密码" prop="checkPass">
              <el-input type="password" v-model="form.checkPass" auto-complete="off" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="性别:">
              <el-radio-group v-model="form.gender" prop="gender">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="联系方式:" prop="telephone">
              <el-input
                placeholder="请输入内容"
                v-model.number="form.telephone"
                style="width: 250px;">
              </el-input>
            </el-form-item>
            <el-form-item label="E-MAIL:" prop="email">
              <el-input
                placeholder="请输入内容"
                v-model="form.email"
                style="width: 250px;">
              </el-input>
            </el-form-item>
            <el-form-item label="是否管理员:" prop="isadmin">
              <el-radio-group v-model="form.isadmin">
                <el-radio :label="true">是</el-radio>
                <el-radio :label="false">否</el-radio>
              </el-radio-group>
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
            <el-form-item label="部门：">
              <el-select v-model.number="dept_id"  placeholder="请选择">
                <el-option
                  v-for="item in deptlist"
                  :key="item.dept_id"
                  :label="item.dept_name"
                  :value="item.dept_id"
                  @click.native="getJoblist">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="岗位：" prop="jobid">
              <el-select v-model.number="form.jobid" placeholder="请选择" prop="jobid">
                <el-option
                  v-for="item in joblist"
                  :key="item.jobid"
                  :label="item.jobname"
                  :value="item.jobid">
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
  const url ="/api/personserver"
  import axios from 'axios'

  export default{
    data(){
      var checkUsername = (rule, value, callback) => {
          if (!value) {
            return callback(new Error('用户名不能为空'));
          }
          setTimeout(() => {
              if (this.checkuser) {
                callback(new Error('用户名已存在！'));
              } else {
                callback();
            }
          }, 1000);
      };
      var checkpass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.form.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      var checkPhone = (rule, value, callback) => {
          if (!value) {
            return callback(new Error('手机号不能为空'));
          } else {
            const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
            console.log(reg.test(value));
            if (reg.test(value)) {
              callback();
            } else {
              return callback(new Error('请输入正确的手机号'));
            }
          }
      };
          return {
        checkuser:false,
        flag:true,
        organ_id:null,
        job_id:null,
        dept_id:null,
        form: {
          personid:null,
          username:null,
          password:null,
          checkPass:null,
          realname: null,
          gender: "男",
          telephone: null,
          jobid: null,
          isadmin:false,
          email:null
        },
        route_id: this.$route.params.personid,
        load_data: false,
        on_submit_loading: false,
        rules: {
          username: [ {required: true, validator: checkUsername, trigger: 'blur' }],
          checkPass:[ {required: true, validator: checkpass, trigger: 'blur' }],
          password: [{required: true, message: '密码不能为空', trigger: 'blur'}],
          realname: [{required: true, message: '姓名不能为空', trigger: 'blur'}],
          gender: [{required: true, message: '性别不能为空', trigger: 'blur'}],
          telephone:[ {required: true, validator: checkPhone, trigger: 'blur' }],
          jobid: [{type:"number",required: true, message: '请选择岗位', trigger: 'blur'}],
          email: [{type:"email",required: true, message: '请输入正确邮箱!', trigger: 'blur'}],
          // telephone: [{required: true, message: '电话号码不能为空', trigger: 'blur'}],
          // personid: [{type:"number",required: true, message: '组织ID不能为空',trigger: 'blur'}]
        },
        joblist:[],
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
      checkUsername(){
        axios.get(url,{
          params:{
            method:"checkUsername",
            username:this.form.username
          }
        }).then((res)=>{
          this.checkuser=res.data
        })
      },
      //获取数据
      get_form_data(){
        this.load_data = true
        // 获取个人信息
        axios.get(url,{
          params:{
            method:"queryById",
            personid:this.route_id
          }
        })
          .then(({data}) => {
            this.form = data
            // console.log(this.form.jobid)
            this.load_data = false
          })
          .then(()=>{
          axios.get(url,{
          params:{
            method:"queryDeptid",
            jobid:this.form.jobid
          }
        })
          .then(({data}) => {
            this.dept_id=data
            // console.log(this.dept_id)
            axios.get(url,{
              params:{
                method:"queryOrganid",
                dept_id:this.dept_id
              }
            })
              .then(({data}) => {
                this.organ_id=data
                axios.get(url,{
                  params:{
                    method:"queryOrganlist",
                  }
                })
                  .then(({data}) => {
                    this.organlist=data
                    axios.get(url,{
                      params:{
                        method:"queryDeptlist",
                        organ_id:this.organ_id
                      }
                    })
                      .then(({data}) => {
                        this.deptlist=data
                        axios.get(url,{
                          params:{
                            method:"queryJoblist",
                            dept_id:this.dept_id
                          }
                        })
                          .then(({data}) => {
                            this.joblist=data
                          })
                      })
                  })
              })
              .catch(() => {
                this.load_data = false
              })
          })
          .catch(() => {
            this.load_data = false
          })
        }

        )
      },
      getOrganlist(){
        axios.get(url,{
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
        this.dept_id=null
        this.deptlist=[]
        this.form.jobid=null
        this.joblist=[]
        axios.get(url,{
          params:{
            method:"queryDeptlist",
            organ_id:this.organ_id
          }
        })
          .then(({data}) => {
            this.deptlist=data
            console.log(this.deptlist)
          })
          .catch(() => {
            this.load_data = false
          })
      },
      getJoblist(){
        this.form.jobid=null
        this.joblist=[]
        axios.get(url,{
          params:{
            method:"queryJoblist",
            dept_id:this.dept_id
          }
        })
          .then(({data}) => {
            this.joblist=data
            console.log(this.joblist)
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
          var method =this.flag?"changePerson":"addPerson"

          var personid=this.form.personid
          axios.get(url,{
            params:{
              method:method,
              personid:personid,
              person:this.form
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
            var message =""
            if(err.response.status === 404){
              message="信息有误，添加失败！"
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
