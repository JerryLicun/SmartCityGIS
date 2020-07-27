<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="on_refresh" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
      <router-link :to="{name: 'tableAdd'}" tag="span">
        <el-button type="primary" icon="plus" size="small">添加数据</el-button>
      </router-link>
    </panel-title>
    <div class="panel-body">
      <div style="width: 30%;margin-bottom: 20px">
        <el-input placeholder="请输入内容" v-model="searchkey" class="input-with-select">
          <el-select v-model="searchid" slot="prepend" placeholder="请选择方式" style="width: 130px;">
            <el-option label="按用户名查询" value="username"></el-option>
            <el-option label="按姓名查询" value="name"></el-option>
            <el-option label="按岗位查询" value="job"></el-option>
          </el-select>
          <el-button slot="append" @click="submit_search"><i class="fa fa-search" aria-hidden="true"></i></el-button>
        </el-input>
      </div>
      <el-table
        :data="table_data"
        v-loading="load_data"
        element-loading-text="拼命加载中"
        border
        @selection-change="on_batch_select">
        <el-table-column
          :selectable="selectable"
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          label="序号"
          width="80">
          <template scope="scope"><span>{{scope.$index+(currentPage - 1) * length + 1}} </span></template>
        </el-table-column>
        <el-table-column
          prop="personid"
          label="id"
          width="80"
          v-if="false"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="username"
          label="用户名"
          width="120"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="relname"
          label="姓名"
          width="130"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="gender"
          label="性别"
          width="120"
        >
        </el-table-column>
        <el-table-column
          prop="telephone"
          label="联系方式"
          width="177px"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="email"
          label="E-MAIL"
          width="250px"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="jobDescr"
          label="岗位"
          width="400px"
        >
        </el-table-column>
        <el-table-column
          prop="isadmin"
          label="是否管理员"
          width="120">

        </el-table-column>
        <el-table-column
          label="操作"
          width="180">
          <template scope="props">
            <router-link :to="{name: 'tableUpdate', params: {personid:props.row.personid}}" tag="span">
              <el-button type="info" size="small" icon="edit">修改</el-button>
            </router-link>
            <el-button v-if="props.row.personid!=get_user_info.user.personid" type="danger" size="small" icon="delete" @click="delete_data(props.row.personid)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <bottom-tool-bar>
        <el-button
          type="danger"
          icon="delete"
          size="small"
          :disabled="batch_select.length === 0"
          @click="on_batch_del"
          slot="handler">
          <span>批量删除</span>
        </el-button>
        <div slot="page">
          <div style="width: 120px;transform: translate(-110px,26px);color:#48576a;font-size: 14px">
            每页显示
            <el-input ref="iplength" v-model.number="length" max="99" min="3" size="mini" @blur="lengthchange" style="width:40px;"></el-input>
            条
          </div>
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="length"
            layout="total, prev, pager, next"
            :total="total">
          </el-pagination>
        </div>
      </bottom-tool-bar>
    </div>
  </div>
</template>
<script type="text/javascript">
  import {panelTitle, bottomToolBar} from 'components'
  import axios from 'axios'
  import {mapGetters} from 'vuex'
  import {GET_USER_INFO} from 'store/getters/type'
  const url ="/api/personserver"
  export default{
    data(){
      return {
        personid:null,
        idlist:"",
        searchkey:"",
        searchid:"",
        table_data: null,
        //当前页码
        currentPage: 1,
        //数据总条目
        total: 0,
        //每页显示多少条数据
        length: 10,
        //请求时的loading效果
        load_data: true,
        //批量选择数组
        batch_select: []
      }
    },
    computed:{
      ...mapGetters({
        get_user_info: GET_USER_INFO
      })
    },
    components: {
      panelTitle,
      bottomToolBar,
    },
    created(){
      this.get_table_data()
      this.personid=this.get_user_info.user.personid
      // console.log(this.get_user_info.user.isadmin)
      if(!this.get_user_info.user.isadmin){
        this.$router.replace({path:'/403'})
      }
    },
    methods: {
      selectable(row){
        if(row.personid!=this.get_user_info.user.personid){
          return true
        }else{
          return false
        }
      },
      lengthchange(){
        var  val =this.$refs.iplength.value
        if(parseInt(this.$refs.iplength.value)){
          if(val>40){
            this.length=30
          }else if(val<3){
            this.length=3
          }else{
            this.length=val
          }
        }else{
          this.length=10
        }
        if(this.searchid !== ""&& this.searchkey!==""){
          this.submit_search()
        }else if (this.searchkey === ""){
          this.get_table_data()
        }
      },
      submit_search() {
        if (this.searchkey === ""){
          this.get_table_data()
        }else if(this.searchid === "username"){
          axios.get(url,{
            params:{
              method:"searchByusername",
              page: this.currentPage,
              length: this.length,
              username:this.searchkey
            }
          }).then((res)=>{
            // console.log(res)
            this.table_data=res.data.result
            this.page=res.data.page
            this.total = res.data.total
            setTimeout(1000)
            this.load_data = false
          })
        }else if(this.searchid === "name"){
          axios.get(url,{
            params:{
              method:"searchByname",
              page: this.currentPage,
              length: this.length,
              realname:this.searchkey
            }
          }).then((res)=>{
            // console.log(res)
            this.table_data=res.data.result
            this.page=res.data.page
            this.total = res.data.total
            setTimeout(1000)
            this.load_data = false
          })

        }else if (this.searchid === "job"){
          axios.get(url,{
            params:{
              method:"searchByjob",
              page: this.currentPage,
              length: this.length,
              jobname:this.searchkey
            }
          }).then((res)=>{
            // console.log(res)
            this.table_data=res.data.result
            this.page=res.data.page
            this.total = res.data.total
            setTimeout(1000)
            this.load_data = false
          })
        }
      },
      on_refresh(){

        this.get_table_data()
      },
      //获取数据
      // $fetch.api_table 等于api/index.js
      get_table_data(){
        this.load_data = true
       axios.get(url,{
         params:{
           method:"personList",
           page: this.currentPage,
           length: this.length
         }
       }).then((res)=>{
           // console.log(res)
           this.table_data=res.data.result
           this.page=res.data.page
           this.total = res.data.total-1
           setTimeout(1000)
           this.load_data = false
         })
      },
      //单个删除
      delete_data(personid){
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true
            axios.get(url,{
              params:{
                method:"delPerson",
                personid:personid,
              }
            })
              .then((res) => {
                // console.log(res)
                this.$message.success(res.data)
                this.load_data = false
                this.on_refresh()
              })
              .catch((err) => {
                this.load_data = false
                var message =""
                if(err.response.status === 404){
                  message="删除失败！"
                }
                this.$notify.info({
                  title: '温馨提示',
                  message:message,
                })
              })
          })
          .catch(() => {
          })
      },
      //页码选择
      handleCurrentChange(val) {
        this.currentPage = val
        if(this.searchid !== ""&& this.searchkey!==""){
          this.submit_search()
        }else if (this.searchkey === ""){
          this.get_table_data()
        }
      },
      //批量选择
      on_batch_select(val){
        this.batch_select = val
      },
      //批量删除
      on_batch_del(){
        this.$confirm('此操作将批量删除选择数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true
            this.batch_select.forEach ((item)=>{
                this.idlist+=item.personid+","
              })
              console.log(this.idlist)
            axios.get(url,{
              params:{
                method:"delPersonArray",
                list:this.idlist,
              }
            })
              .then((res) => {
                // console.log(res)
                this.$message.success(res.data)
                this.load_data = false
                this.on_refresh()
              })
              .catch((err) => {
                this.load_data = false
                var message =""
                if(err.response.status === 404){
                  message="删除失败！"
                }
                this.$notify.info({
                  title: '温馨提示',
                  message:message,
                })
              })

          })
          .catch(() => {
          })
      }
    }
  }
</script>
