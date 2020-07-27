<template>
  <div class="left-side">
    <div class="left-side-inner">
      <router-link to="/" class="logo block">
        <img src="./images/logo.png" alt="AdminX">
      </router-link>
      <el-menu
        class="menu-box"
        theme="dark"
        router
        :default-active="$route.path">
        <div
          v-for="(item, index) in nav_menu_data"
          :key="index">
          <el-menu-item
            class="menu-list"
            v-if="typeof item.child === 'undefined'"
            :index="item.path">
            <i class="icon fa" :class="item.icon"></i>
            <span v-text="item.title" class="text"></span>
          </el-menu-item>
          <el-submenu
            :index="item.path"
            v-else>
            <template slot="title">
              <i class="icon fa" :class="item.icon"></i>
              <span v-text="item.title" class="text"></span>
            </template>
            <el-menu-item
              class="menu-list"
              v-for="(sub_item, sub_index) in item.child"
              :index="sub_item.path"
              :key="sub_index">
              <!--<i class="icon fa" :class="sub_item.icon"></i>-->
              <span v-text="sub_item.title" class="text"></span>
            </el-menu-item>
          </el-submenu>
        </div>
      </el-menu>
    </div>
  </div>
</template>
<script type="text/javascript">
  export default{
    name: 'slide',
    data(){
      return {
        nav_menu_data: [{
          title: "首页",
          path: "/home",
          icon: "fa-home"
        }, {
          title: "系统管理",
          path: "/table",
          icon: "fa-gears",
          child: [{
            title: "组织机构",
            path: "/table/organization",
            icon: "fa-table",
          }, {
            title: "部门管理",
            path: "/table/dept"
          },{
            title: "岗位管理",
            path: "/table/job"
          },{
            title: "人员管理",
            path: "/table/base"
          },{
            title: "日志管理",
            path: "/table/log"
          }]
        }, {
          title: "功能管理",
          path: "/monitor",
          icon: "fa-align-justify",
          child: [{
            title: "新闻管理",
            path: "/monitor/news"
          },{
            title: "公告管理",
            path: "/monitor/tips"
          }]
        },{
          title: "地图服务",
          path: "/map",
          icon: "fa-map",
          child: [{
            title: "热点搜索",
            path: "/map/hot"
          },{
            title: "行政区划",
            path: "/map/area"
          },{
            title: "地图查看",
            path: "/map/search"
          },{
            title: "路线导航",
            path: "/map/road"
          }]
        }],
      }
    }
  }
</script>
