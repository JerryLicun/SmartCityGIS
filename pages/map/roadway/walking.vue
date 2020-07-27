<template>
  <div class="panel">
    <div class="panel-body">
      <baidu-map class="mapwalk" center="武汉" :zoom="15" :scroll-wheel-zoom="true">
        <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
        <bm-scale anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-scale>
        <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
        <bm-walking :start=startsearch :end=endsearch :auto-viewport="true" :location=Localsearch></bm-walking>
      </baidu-map>
      <div class="input-wrapper">
        <el-input
          class="input ip1"
          placeholder="请输入起点"
          v-model="startlocal"
          clearable>
        </el-input>
        <el-input
          class="input ip2"
          placeholder="请输入目的地"
          v-model="endlocal"
          clearable>
          <el-button slot="append" @click="handleSearch">
            <i class="fa fa-search" aria-hidden="true"></i>
          </el-button>
        </el-input>
        <!--<el-input-->
          <!--class="input ip3"-->
          <!--placeholder="请输入城市"-->
          <!--v-model="Local"-->
          <!--clearable>-->
          <!--<el-button slot="append" @click="handleLocation">-->
            <!--<i class="fa fa-search" aria-hidden="true"></i>-->
          <!--</el-button>-->
        <!--</el-input>-->
      </div>
    </div>
  </div>
</template>
<script type="text/javascript">
  import {panelTitle} from 'components'
  export default{
    data(){
      return {
        Localsearch:"武汉",
        startsearch:"",
        endsearch:"",
        startlocal:"",
        endlocal:"",
        Local:""
      }
    },
    components: {
      panelTitle
    },
    mounted() {
    },
    methods:{
      handleSearch(){
        this.startsearch =this.startlocal
        this.endsearch = this.endlocal
      },
      handleLocation(){
        this.Localsearch=this.Local
      }
    }
  }
</script>
<style scoped>
  .mapwalk {
    width: 100%;
    height: 600px;
  }
  .input-wrapper{
    height: 600px;
  }
  .ip1{
    float: left;
    width: 20%;
    max-width: 400px;
    transform: translateY(-40px);
  }
  .ip2{
    width: 22%;
    max-width:430px;
    margin-right: 20px;
    float: left;
    transform: translateY(-40px);
  }
  .ip3{
    max-width:150px;
    float: left;
    transform: translateY(-40px);
  }
</style>
