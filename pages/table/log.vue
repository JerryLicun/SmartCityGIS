<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="on_refresh" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
    </panel-title>
    <div class="panel-body">
      <div style="width: 30%;margin-bottom: 20px">
        <el-input placeholder="请输入内容" v-model="searchkey" class="input-with-select">
          <el-select v-model="searchid" slot="prepend" placeholder="请选择方式" style="width: 130px;">
            <el-option label="按用户名查询" value="username"></el-option>
            <el-option label="按操作详情查询" value="msg"></el-option>
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
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          label="序号"
          width="80">
          <template scope="scope"><span>{{scope.$index+(currentPage - 1) * length + 1}} </span></template>
        </el-table-column>
        <el-table-column
          prop="logid"
          label="日志ID"
          width="120"
          v-if="false"
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
          prop="ip"
          label="IP地址"
          width="180"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="createtime"
          label="时间"
          width="195"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="classfn"
          label="接口"
          width="360"
        >
        </el-table-column>
        <el-table-column
          prop="method"
          label="方法"
          width="195"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="loglevel"
          label="消息等级"
          width="150"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="msg"
          label="操作详情"
          width="200"
        >
        </el-table-column>
        <el-table-column
          label="操作"
          width="100">
          <template scope="props">
            <el-button type="danger" size="small" icon="delete" @click="delete_data(props.row.logid)">删除</el-button>
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
  const url ="/api/loginfoserver"
  export default{
    data(){
      return {
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
      if(!this.get_user_info.user.isadmin){
        this.$router.replace({path:'/403'})
      }
    },
    methods: {
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
              method:"searchByUser",
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
        }else if(this.searchid === "msg"){
          axios.get(url,{
            params:{
              method:"searchByMsg",
              page: this.currentPage,
              length: this.length,
              msg:this.searchkey
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
            method:"logList",
            page: this.currentPage,
            length: this.length
          }
        }).then((res)=>{
          // console.log(res)
          this.table_data=res.data.result
          this.page=res.data.page
          this.total = res.data.total
          setTimeout(1000)
          this.load_data = false
        })
      },
      //单个删除
      delete_data(logid){
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true
            axios.get(url,{
              params:{
                method:"delLog",
                logid:logid,
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
            // this.$fetch.api_table.del(item)
            //   .then(({msg}) => {
            //     this.get_table_data()
            //     this.$message.success(msg)
            //   })
            //   .catch(() => {
            //   })
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
              this.idlist+=item.logid+","
            })
            console.log(this.idlist)
            axios.get(url,{
              params:{
                method:"delLoglist",
                list:this.idlist,
              }
            })
              .then((res) => {
                // console.log(res)
                this.$message.success(res.data)
                this.load_data = false
                this.on_refresh()
                this.idlist=[]
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
