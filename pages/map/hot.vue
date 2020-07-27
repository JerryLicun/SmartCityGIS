<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body">
      <el-menu class="el-menu-demo"
               default-active="公交"
               @select="handleSelect"
               style="margin-bottom:10px;height:601px;float: left">
        <el-menu-item index="公交"><i class="fa fa-train" aria-hidden="true"></i>&nbsp;交通设施</el-menu-item>
        <el-menu-item index="医院"><i class="fa fa-hospital-o" aria-hidden="true"></i>&nbsp;医疗机构</el-menu-item>
        <el-menu-item index="超市"><i class="fa fa-shopping-cart" aria-hidden="true"></i>&nbsp;商场超市</el-menu-item>
        <el-menu-item index="学校"><i class="fa fa-graduation-cap" aria-hidden="true"></i>&nbsp;教育机构</el-menu-item>
        <el-menu-item index="餐馆"><i class="fa fa-cutlery" aria-hidden="true"></i>&nbsp;餐饮休闲</el-menu-item>
        <el-menu-item index="公园"><i class="fa fa-area-chart" aria-hidden="true"></i>&nbsp;公园景点</el-menu-item>
      </el-menu>
      <baidu-map class="bm-view" id="maphot" :center="location" :zoom="15" :scroll-wheel-zoom="true" @rightclick="getPosition">
        <bm-scale anchor="BMAP_ANCHOR_BOTTOM_RIGHT"></bm-scale>
        <bm-map-type :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']" anchor="RIGHT"></bm-map-type>
        <bm-geolocation anchor="BMAP_ANCHOR_TOP_LEFT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
        <bm-local-search :nearby="nearby"  :keyword="keywordsearch" :location="location"></bm-local-search>
        <bm-circle :center="nearby.center"
                   :radius="nearby.radius"
                   @lineupdate="updateCirclePath"
                   strokeStyle="dashed"
                   :editing="true"></bm-circle>
      </baidu-map>
    </div>
  </div>
</template>
<script type="text/javascript">
  import {panelTitle} from 'components'
  import { BaiduMap } from "vue-baidu-map";
  export default{
    data(){
      return {
        value:100,
        location:'武汉',
        keywordsearch:"公交",
        nearby: {
          center: {
            lng: 114.361675,
            lat: 30.480878
          },
          radius: 1000
        }
      }
    },
    components: {
      panelTitle
    },
    mounted() {
    },
    watch:{

    },
    methods:{
      handleSelect(index){
        this.keywordsearch=index
      },
      getPosition (e) {
        this.nearby.center={lng:e.point.lng,lat:e.point.lat}
      },
      updateCirclePath (e) {
        this.nearby.center = e.target.getCenter()
        this.nearby.radius = e.target.getRadius()
      }
      // handler ({BMap, map}) {
      //   var geolocation =new BMap.Geolocation
      //   geolocation.getCurrentPosition((r)=>{
      //     console.log(r)
      //     this.center={lng:r.longitude,lat:r.latitude}
      //     this.autoloctionPoint ={lng:r.longitude,lat:r.latitude}
      //     this.initLocation=true
      //     console.log(this.center)
      //   },{enableHighAccuracy:true})
      // }
    }
  }
</script>
<style>
  #maphot{
    widht:100%;
    height:600px;
    overflow: hidden;
  }
  .BMap_vectex_node{
    border-radius: 100%;
  }
</style>
