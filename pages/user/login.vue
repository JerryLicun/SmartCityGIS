<template>
  <div class="login-body">
    <div class="loginWarp"
         v-loading="load_data"
         element-loading-text="正在登陆中..."
         @keyup.enter="submit_form">
      <div class="login-title">
        <img src="./images/login_logo.png" style="width: 118px;height: 113px;"/>
        <!--<div style="color: #6bc5a4;font-weight: bold">智慧武汉后台管理系统</div>-->
      </div>
      <div class="login-form">
        <el-form ref="form" :model="form" :rules="rules" label-width="0">
          <el-form-item prop="username" class="login-item">
            <el-input v-model="form.username" placeholder="请输入账户名：" class="form-input" :autofocus="true"></el-input>
          </el-form-item>
          <el-form-item prop="password" class="login-item">
            <el-input type="password" v-model="form.password" placeholder="请输入账户密码：" class="form-input"></el-input>
          </el-form-item>
          <el-checkbox v-model="checked" class="checkbox" label="记住我"></el-checkbox>
          <check @change="handleLetterChange"></check>
          <el-form-item class="login-item">
            <el-button size="large" icon="check" class="form-submit" @click="submit_form"></el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script type="text/javascript">
  import {mapActions} from 'vuex'
  import {port_user, port_code} from 'common/port_uri'
  import {SET_USER_INFO} from 'store/actions/type'
  import Check from "./check"
//  设置记住我
  import {cookieStorage} from 'common/storage'
  const url="/api/logserver"
  import axios from 'axios'


  export default{
    components:{
      Check
    },
    data(){
      return {
        flag:false,
        checked:false,
        form: {
          username: null,
          password: null
        },
        rules: {
          username: [{required: true, message: '请输入账户名！', trigger: 'blur'}],
          password: [{required: true, message: '请输入账户密码！', trigger: 'blur'}]
        },
        //请求时的loading效果
        load_data: false
      }
    },
    mounted(){
      this.getinit()
    },
    methods: {
      ...mapActions({
        set_user_info: SET_USER_INFO
      }),
      handleLetterChange(){
        this.flag=true
      },
      //提交
      submit_form() {
        this.$refs.form.validate((valid) => {
          if (!valid) return false
          if(!this.flag){
            this.$notify.info({
              title: '温馨提示',
              message:"请完成验证",
            })
            return false
          }
          this.load_data = true
          //登录提交
          cookieStorage.set('checked', this.checked)
          axios.get(url,{
            params:{
              method:"login",
              username: this.form.username,
              password: this.form.password
            }
          }).then((res)=>{
            // console.log(res)
            if(this.checked){
              cookieStorage.set('username', this.form.username)
              cookieStorage.set('password', this.form.password)
            }else{
              cookieStorage.remove("username")
              cookieStorage.remove("password")
            }
            this.set_user_info({
              user: res.data.data,
              login: true,
              dept_id:res.data.dept_id,
              organ_id:res.data.organ_id
            })
            this.$message.success("登录成功")
            setTimeout(this.$router.push({path: '/'}), 500)
          }).catch((err) => {
            var message=""
            if(err.response.status === 404){
              message="用户名错误！"
            }else if(err.response.status===403){
              message="密码不正确！"
            }
            this.load_data = false
            this.$notify.info({
              title: '温馨提示',
              message:message,
              // message: '测试账号为Yang,密码为：admin'
            })

          })
          // this.$fetch.api_user.login(this.form)
          //   .then(({data}) => {
          //     if(this.checked){
          //       cookieStorage.set('username', this.form.username)
          //       cookieStorage.set('password', this.form.password)
          //     }else{
          //       cookieStorage.remove("username")
          //       cookieStorage.remove("password")
          //     }
          //     this.set_user_info({
          //       user: data,
          //       login: true
          //     })
          //     this.$message.success("登录成功")
          //     setTimeout(this.$router.push({path: '/'}), 500)
          //   })
          //   .catch(({code}) => {
          //     this.load_data = false
          //     if (code === port_code.error) {
          //       this.$notify.info({
          //         title: '温馨提示',
          //         message: '测试账号为Yang,密码为：admin'
          //       })
          //     }
          //   })
        })
      },
      getinit(){
        this.checked=cookieStorage.get('checked')
        if(typeof (cookieStorage.get('password'))!="object"&&typeof (cookieStorage.get('password'))!="object"){
          this.form.username=cookieStorage.get('username')
          this.form.password=cookieStorage.get('password')
        }
      }
    }
  }
</script>
<style lang="scss" type="text/scss" rel="stylesheet/scss">
  .login-body {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-image: url(./images/login_bg.jpg);
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
    .checkbox{
      margin-bottom: 10px;
      color:darkgrey;
    }
    .checkbox:hover{
      color:deepskyblue;
    }
    .loginWarp {
      width: 300px;
      padding: 25px 15px;
      margin: 100px auto;
      background-color: #fff;
      border-radius: 5px;
      .login-title {
        margin-bottom: 25px;
        text-align: center;
      }
      .login-item {
        .el-input__inner {
          margin: 0 !important;
        }
      }
      .form-input {
        input {
          margin-bottom: 15px;
          font-size: 12px;
          height: 40px;
          border: 1px solid #eaeaec;
          background: #eaeaec;
          border-radius: 5px;
          color: #555;
        }
      }
      .form-submit {
        width: 100%;
        color: #fff;
        border-color: #6bc5a4;
        background: #6bc5a4;
        &:active, &:hover {
          border-color: #6bc5a4;
          background: #6bc5a4;
        }
      }
    }
  }
</style>
