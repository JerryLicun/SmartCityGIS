<template>
  <div class="panel">
    <div class="panel-body">
      <baidu-map class="mapdriving" center="武汉" :zoom="15" :scroll-wheel-zoom="true">
        <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
        <bm-scale anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-scale>
        <bm-traffic :predictDate="{weekday: 7, hour: 12}">
        </bm-traffic>
        <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
        <bm-driving
          :start=startsearch
          :end=endsearch
          :auto-viewport="true"
          location="武汉"
          policy="BMAP_DRIVING_POLICY_LEAST_DISTANCE"
          :waypoints="[]"
        ></bm-driving >
      </baidu-map>
      <div class="input-wrapper">
        <el-input
          class="input ip1"
          placeholder="请输入起点"
          v-model="startaim"
          clearable>
        </el-input>
        <el-input
          class="input ip2"
          placeholder="请输入目的地"
          v-model="endaim"
          clearable>
          <el-button slot="append" @click="handleSearch">
            <i class="fa fa-search" aria-hidden="true"></i>
          </el-button>
        </el-input>
        <!--<el-input-->
          <!--class="input ip3"-->
          <!--placeholder="请输入起始城市"-->
          <!--v-model="startlocal"-->
          <!--clearable>-->
        <!--</el-input>-->
        <!--<el-input-->
          <!--class="input ip4"-->
          <!--placeholder="请输入目的城市"-->
          <!--v-model="endlocal"-->
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
        startLocalsearch:"武汉",
        endlocalsearch:"武汉",
        startsearch:"",
        endsearch:"",
        startlocal:"",
        endlocal:"",
        startaim:"",
        endaim:""
      }
    },
    components: {
      panelTitle
    },
    mounted() {
    },
    methods:{
      handleSearch(){
        this.startsearch =this.startaim
        this.endsearch = this.endaim
      },
      handleLocation(){
        this.startLocalsearch=this.startlocal
        this.endlocalsearch=this.endlocal
      }
    }
  }
</script>
<style scoped>
  .mapdriving {
    width: 100%;
    height: 600px;
  }
  .input-wrapper{
    height: 2500px;
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
    max-width:130px;
    float: left;
    transform: translateY(-40px);
  }
  .ip4{
    max-width:170px;
    float: left;
    transform: translateY(-40px);
  }
  .suggest-plan-des{
    transform: translateY(40px);
  }
</style>
