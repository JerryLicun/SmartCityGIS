<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body">
      <baidu-map class="map" :center="Localsearch" :zoom="15" :scroll-wheel-zoom="true">
        <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
        <bm-scale anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-scale>
        <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
        <bm-local-search :keyword="keywordsearch" :auto-viewport="true" :location="Localsearch"></bm-local-search>
        <bm-context-menu>
          <bm-context-menu-item :callback="getPosition" text="收藏点"></bm-context-menu-item>
          <bm-context-menu-item :callback="showCollect" text="收藏列表"></bm-context-menu-item>
        </bm-context-menu>
        <bm-marker v-for="item in pointcollect" :position="item.point" animation="BMAP_ANIMATION_BOUNCE">
          <bm-label :content="item.name" :labelStyle="{color: 'red', fontSize : '14px'}" :offset="{width: -16, height: 30}"/>
        </bm-marker>
      </baidu-map>
      <div class="input-wrapper">
        <el-input
          class="input ip1"
          placeholder="请输入地点( 右键可添加收藏坐标)"
          v-model="keyword"
          clearable>
          <el-button slot="append" @click="handlekeyword">
            <i class="fa fa-search" aria-hidden="true"></i>
          </el-button>
        </el-input>
        <el-dialog title="收藏点" :visible.sync="dialogFormVisible">
          <el-form :model="pform">
            <el-form-item label="点名称" label-width="80px">
              <el-input v-model="pform.pname" auto-complete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="addpoint">确 定</el-button>
          </div>
        </el-dialog>
        <el-dialog title="收藏点" :visible.sync="dialogTableVisible">
          <div v-for="item in pointcollect" style="line-height: 50px;font-size: 20px;border-bottom: 1px dashed dimgrey">
            <span style="margin-left: 2%;cursor: pointer" @click="handlelocal(item.point)">
              <i class="fa fa-dot-circle-o" aria-hidden="true"></i>
              {{item.name}}
            </span>
            <el-button @click="delPoint(item.id)" size="medium" type="danger" style="float: right;margin-top: 10px;margin-right: 2%"><i class="fa fa-trash-o fa-lg" aria-hidden="true"></i></el-button>
          </div>
        </el-dialog>
      </div>
    </div>
  </div>
</template>
<script type="text/javascript">
  import {panelTitle} from 'components'
  export default{
    data(){
      return {
        dialogTableVisible:false,
        dialogFormVisible: false,
        pid:1,
        pform:{
          pname:''
        },
        pointcollect:[],
        psize:"BMAP_POINT_SIZE_HUGE",
        point:{
          lng:null,
          lat:null
        },
        Localsearch:"武汉",
        keywordsearch:"",
        keyword:"",
        Local:"",
        refresh:true
      }
    },
    components: {
      panelTitle
    },
    mounted() {
      this.pointcollect= JSON.parse(localStorage.getItem("pointcollect"));
      if(this.pointcollect=== null){
        this.pointcollect=[]
      }
    },
    methods:{
      handlelocal(point){
        this.Localsearch=point
        this.dialogTableVisible=false
      },
      handlekeyword(){
        this.keywordsearch=this.keyword
      },
      getPosition(e){
        this.dialogFormVisible = true
        this.point={
          lng:e.point.lng,
          lat:e.point.lat
        }
        // this.points.push({lng:e.point.lng,lat:e.point.lat})
      },
      addpoint(){
        this.dialogFormVisible = false
        let p = {
          id:this.pid,
          name:this.pform.pname,
          point:this.point
        }
        this.pointcollect.push(p)
        let str =JSON.stringify(this.pointcollect)
        localStorage.setItem("pointcollect",str);
        this.pid++
        this.pform.pname=''
      },
      delPoint(id){
        this.$confirm('此操作将删除该点, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.removeindex(id)
          let str =JSON.stringify(this.pointcollect)
          localStorage.setItem("pointcollect",str);
        })
      },
      findindex(val){
        for (var i = 0; i < this.pointcollect.length; i++) {
          if (this.pointcollect[i].id == val) return i;
        }
        return -1;
      },
      removeindex(val){
        var index = this.findindex(val);
          if (index > -1) {
            this.pointcollect.splice(index, 1);
          }
      },
      showCollect(){
        this.dialogTableVisible=true
      }
    }
  }
</script>
<style scoped>
  .map {
    width: 100%;
    height: 600px;
  }
  .input-wrapper{
    height: 550px;
  }
  .ip1{
    float: left;
    width: 46%;
    max-width: 500px;
    margin-right: 20px;
    transform: translateY(-40px);
  }
  .ip2{
    width: 46%;
    max-width:500px;
    float: left;
    transform: translateY(-40px);
  }
</style>
