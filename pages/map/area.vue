<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body">
      <baidu-map class="maparea" center="武汉" :zoom="15" :scroll-wheel-zoom="true">
        <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
        <bm-map-type :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']" anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-map-type>
        <bm-scale anchor="BMAP_ANCHOR_BOTTOM_RIGHT"></bm-scale>
        <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
        <bm-local-search :keyword="keywordsearch" :auto-viewport="true" :location="Localsearch"></bm-local-search>
        <bm-boundary :name="keywordsearch" :strokeWeight="2" strokeColor="blue" :auto-viewport="true"></bm-boundary>
      </baidu-map>
      <div class="input-wrapper">
        <el-input
          class="input ip1"
          placeholder="请输入行政区划"
          v-model="keyword"
          clearable>
          <el-button slot="append" @click="handlesearch">
            <i class="fa fa-search" aria-hidden="true"></i>
          </el-button>
        </el-input>
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
        keywordsearch:"",
        keyword:"",
        Local:""
      }
    },
    components: {
      panelTitle
    },
    methods:{
      handlesearch(){
        this.keywordsearch=this.keyword
      }
    }
  }
</script>
<style scoped>
  .maparea {
    width: 100%;
    height: 750px;
    overflow: hidden;
  }
  .input-wrapper{
    margin-top: 20px;
  }
  .ip1{

    max-width: 500px;
  }

</style>
