<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="on_refresh" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
      <router-link :to="{name: 'addOrgan'}" tag="span">
        <el-button type="primary" icon="plus" size="small">添加数据</el-button>
      </router-link>
    </panel-title>
    <div class="panel-body">
      <div style="width: 30%;margin-bottom: 20px">
        <el-input placeholder="请输入内容" v-model="searchkey" class="input-with-select">
          <el-select v-model="searchid" slot="prepend" placeholder="请选择方式" style="width: 130px;">
            <el-option label="按组织名查询" value="organname"></el-option>
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
          :selectable="selectable"
          width="80">
        </el-table-column>
        <el-table-column
          label="序号"
          width="200">
          <template scope="scope"><span>{{scope.$index+(currentPage - 1) * length + 1}} </span></template>
        </el-table-column>
        <el-table-column
          prop="organ_id"
          label="组织ID"
          width="200"
          v-if="false"
        >
        </el-table-column>
        <el-table-column
          prop="organ_name"
          label="组织名称"
          width="790"
        >
        </el-table-column>
        <el-table-column
          prop="deptlist"
          label="组织部门"
          width="400"
        >
          <template slot-scope="scope">
            <div v-for='v in scope.row.deptlist'>
              <div style="margin-top:4px;display: flex;justify-content:space-between;border-bottom: 1px solid #dfe6ec;
              padding: 1px 3px;">
                {{v.dept_name}}
                <el-button v-if="v.dept_id!=get_user_info.dept_id" plain  size="mini" icon="delete" @click="delete_dept(v.dept_id)"></el-button>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="165">
          <template scope="props">
            <router-link :to="{name: 'saveOrgan', params: {organ_id: props.row.organ_id}}" tag="span">
              <el-button type="info" size="small" icon="edit">修改</el-button>
            </router-link>
            <el-button v-if="props.row.organ_id != get_user_info.organ_id" type="danger" size="small" icon="delete" @click="delete_organ(props.row.organ_id)">删除</el-button>
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
            <el-input ref="iplength" v-model="length" max="99" min="3" size="mini" @blur="lengthchange" style="width:40px;"></el-input>
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
  axios.defaults.timeout = 5000;
  const url="/api/organserver"

  export default{
    data(){
      return {
        idlist:"",
        searchkey:"",
        searchid:"",
        //当前页码
        currentPage: 1,
        //数据总条目
        total: 0,
        //每页显示多少条数据
        length: 4,
        //请求时的loading效果
        load_data: false,
        //批量选择数组
        batch_select: [],
        // table_data: null,
        table_data:[]
      }
    },
    components: {
      panelTitle,
      bottomToolBar,
    },
    computed:{
      ...mapGetters({
        get_user_info: GET_USER_INFO
      })
    },
    created(){
      this.get_table_data()
      if(!this.get_user_info.user.isadmin){
        this.$router.replace({path:'/403'})
      }
      console.log(this.get_user_info.organ_id)
    },
    methods: {
      selectable(row){
        if(row.organ_id!=this.get_user_info.organ_id){
          return true
        }else{
          return false
        }
      },
      lengthchange(){
        var  val =this.$refs.iplength.value
        if(parseInt(this.$refs.iplength.value)){
          if(val>10){
            this.length=10
          }else if(val<3){
            this.length=3
          }else{
            this.length=val
          }
        }else{
          this.length=3
        }
        this.get_table_data()
      },
      submit_search() {
        if (this.searchkey === ""){
          this.get_table_data()
        }else if(this.searchid === "organname"){
          this.load_data = true
          axios.get(url,{
            params:{
              method:"searchorgan",
              page: this.currentPage,
              length: this.length,
              organ_name:this.searchkey
            }
          }).then((res)=>{
            setTimeout(2000)
            this.table_data=res.data.result
            this.currentPage=res.data.page
            this.total = res.data.total
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
            method:"organlist",
            page: this.currentPage,
            length: this.length
          }
        }).then((res)=>{
            this.table_data=res.data.result
            this.currentPage=res.data.page
            this.total = res.data.total
            setTimeout(this.load_data = false,2000)
          })
      },
      delete_dept(deptid){
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true
            axios.get("/api/deptserver",{
              params:{
                method:"delDept",
                dept_id:deptid ,
              }
            }).then((res) => {
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
      delete_organ(organid){
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data=true
            axios.get(url, {
              params: {
                method: "delOrgan",
                organ_id: organid
              }
            }).then((res) => {
              this.$message.success(res.data)
              this.on_refresh()
              setTimeout(1000)
              this.load_data = false
            })
          })
      },
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
              this.idlist+=item.organ_id+","
            })
            axios.get(url, {
              params: {
                method: "delOrganlist",
                list: this.idlist,
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
                var message = ""
                if (err.response.status === 404) {
                  message = "删除失败！"
                }
                this.$notify.info({
                  title: '温馨提示',
                  message: message,
                })
              })

          })
          .catch(() => {
          })
      }
    }
  }
</script>
