<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <i class="fa fa-bell-o fa-lg" aria-hidden="true" ></i>
          <span>公告</span>
          <el-button style="float: right;" @click.stop="on_refreshtips" size="small">
            <i class="fa fa-refresh"></i>
          </el-button>
        </div>
        <div class="post" v-for="item in tipList">
          <el-button class="btn upvote" type="primary" plain >
            <i class="fa fa-arrow-up" aria-hidden="true"></i>
          </el-button>
          <div class="postcontent">
            <h3>
              <router-link :to="{name: 'tipsDetail', params: {inform_id:item.inform_id}}" tag="a">
                {{item.infotitle}}
              </router-link>
            </h3>
            <hr>
            <p>
              <span class="author">
                {{item.infoname}}
              </span>
              <span style="float: right">
                {{item.infotime}}
              </span>
            </p>
          </div>
        </div>
        <div style="clear: both"></div>
        <bottom-tool-bar>
          <div slot="page" class="pagination">
            <el-pagination
              @current-change="handletipChange"
              :current-page="tipcurrentPage"
              :page-size="tiplength"
              layout="total, prev, pager, next"
              :total="tiptotal">
            </el-pagination>
          </div>
        </bottom-tool-bar>
      </el-card>

      <el-card class="box-card" style="margin:40px 3% 0 0;width: 35%;float: left">
        <div slot="header" class="clearfix">
          <i class="fa fa-newspaper-o fa-lg" aria-hidden="true" ></i>
          <span>新闻</span>
          <el-button style="float: right;" @click.stop="on_refreshnews" size="small">
            <i class="fa fa-refresh"></i>
          </el-button>
        </div>
        <div v-for="item in newsList" class="text item" style="font-size: 16px;cursor: pointer">
          <router-link :to="{name: 'newsDetail', params: {news_id:item.news_id}}" tag="span">
            {{item.newstitle }}
          </router-link>
          <i class="fa fa-fire" aria-hidden="true" v-if="item.hot" style="color:darkred;float: right;"></i>
          <hr>
        </div>
        <bottom-tool-bar>
          <div slot="page" class="pagination">
            <el-pagination
              @current-change="handlenewsChange"
              :current-page="newscurrentPage"
              :page-size="newslength"
              layout="total, prev, pager, next"
              :total="newstotal">
            </el-pagination>
          </div>
        </bottom-tool-bar>
      </el-card>
      <el-card class="box-card" style="margin: 40px 3% 0 0;width: 30%;float: left">
        <div slot="header" class="clearfix">
          <i class="fa fa-list fa-lg" aria-hidden="true"></i>
          <span>TodoList</span>
        </div>
        <todo-list></todo-list>
      </el-card>
      <el-card class="box-card" style="margin-top:40px;width: 27.8%;float: left" :body-style="{ padding: '0px' }">
        <div slot="header" class="clearfix">
          <i class="fa fa-map-marker fa-lg" aria-hidden="true"></i>
          <span>地图定位</span>
        </div>
        <baidu-map id="map" :center="{lng: 114.361675, lat: 30.480878}" :zoom="15" :scroll-wheel-zoom="true">
          <bm-geolocation anchor="BMAP_ANCHOR_TOP_LEFT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
        </baidu-map>
      </el-card>
    </div>
  </div>
</template>
<script type="text/javascript">
  import {panelTitle, bottomToolBar} from 'components'
  import axios from 'axios'
  import TodoList from './TodoList'
  export default{
    data(){
      return {
        activeName:"1",
        tipcurrentPage:1,
        newscurrentPage:1,
        tiptotal:15,
        newstotal:15,
        tiplength:4,
        newslength:7,
        newsList:[],
        tipList:[]
      }
    },
    components: {
      panelTitle,
      bottomToolBar,
      TodoList
    },
    created(){
      this.get_tips()
      this.get_news()
    },
    methods:{
      on_refreshtips(){
        this.get_tips()
      },
      on_refreshnews(){
        this.get_news()
      },
      get_tips(){
        this.load_data = true
        axios.get("/api/infoserver",{
          params:{
            method:"infoList",
            page: this.tipcurrentPage,
            length: this.tiplength
          }
        }).then((res)=>{
          // console.log(res)
          this.tipList=res.data.result
          this.tipcurrentPage=res.data.page
          this.tiptotal = res.data.total
          setTimeout(1000)
          this.load_data = false
        })
      },
      get_news(){
        this.load_data = true
        axios.get("/api/newserver",{
          params:{
            method:"newsList",
            page: this.newscurrentPage,
            length: this.newslength
          }
        }).then((res)=>{
          this.newsList=res.data.result
          this.newscurrentPage=res.data.page
          this.newstotal = res.data.total
          setTimeout(1000)
          this.load_data = false
        })
      },
      handletipChange(val){
        this.tipcurrentPage=val
        this.get_tips()
      },
      handlenewsChange(val){
        this.newscurrentPage=val
        this.get_news()
      }
    }
  }
</script>

<style>
  #map{
    width: 100%;
    height: 300px;
  }
  .el-pager li.active{
    border-color: dimgrey;
    background-color: dimgrey;
  }
  .text {
    font-size: 14px;
  }
  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 99%;
  }
  .pagination{

    margin: 0 auto;
  }
  .post{
    float: left;
    width: 40%;
    margin: 0  50px 10px 10px;
    position: relative;
  }
  .post .upvote{
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    color: darkgrey;
    pointer-events: none;
  }
  .postcontent{
    padding-left: 60px;
  }
  .postcontent h3{
    margin:0;
    line-height: 1.4;
    font-size: 18px;
    white-space:nowrap;
    text-overflow:ellipsis;
    overflow: hidden;
  }
  .postcontent h3 a{
    margin-left: 5px;
    text-decoration: none;
    color:dimgrey;
  }
  hr{
    margin:5px 0 5px 0;
    border-top: 2px dashed rgba(0,0,0,.15);
  }
  .postcontent p{
    margin: 5px 0 0;
    color: dimgrey;
  }
  .post .author{
    font-weight: 500;
    color:dimgrey;
  }
</style>
