<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body">
      <el-tabs v-model="activeName">
        <el-tab-pane label="公交路线规划" name="first"><transit></transit></el-tab-pane>
        <el-tab-pane label="步行路线规划" name="second"><walking></walking></el-tab-pane>
        <el-tab-pane label="驾车路线规划" name="third"><driving></driving></el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script type="text/javascript">
  import {panelTitle} from 'components'
  import Driving from './roadway/driving'
  import Transit from './roadway/transit'
  import Walking from './roadway/walking'
  export default{
    data(){
      return {
        activeName: 'third'
      }
    },
    components: {
      panelTitle,
      Driving,
      Transit,
      Walking
    }
  }
</script>
