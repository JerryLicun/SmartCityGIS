<template>
  <div class="drag">
    <div class="drag_bg"></div>
    <div class="drag_text">{{confirmWords}}</div>
    <div @mousedown="mousedownFn($event)" class="handler handler_bg"></div>
  </div>
</template>

<script>
  export default {
    name: '',
    components: {},
    props: {},
    data () {
      return {
        beginClientX:0,      /*距离屏幕左端距离*/
        mouseMoveStata:false,    /*触发拖动状态  判断*/
        maxwidth:258,       /*拖动最大宽度，依据滑块宽度算出来的*/
        confirmWords:'拖动滑块验证',      /*滑块文字*/
        confirmSuccess:false              /*验证成功判断*/
      }
    },
    created() {},
    methods: {
      mousedownFn:function (e) {
        this.mouseMoveStata = true;
        this.beginClientX = e.clientX;
      },       //按下滑块函数
      successFunction(){
        $(".handler").removeClass('handler_bg').addClass('handler_ok_bg');
        this.confirmWords = '验证通过'
        $(".drag").css({'color': '#fff'});
        $(".handler").css({'left':this.maxwidth});
        $(".drag_bg").css({'width': this.maxwidth});
        $('body').unbind('mousemove');
        $('body').unbind('mouseup');
        this.confirmSuccess = true
        this.$emit('change')
      }                //验证成功函数
    },
    mounted(){
      $('body').on('mousemove',(e) => {   //拖动，这里需要用箭头函数，不然this的指向不会是vue对象
        if(this.mouseMoveStata){
          var width = e.clientX - this.beginClientX;
          if(width>0 && width<=this.maxwidth){
            $(".handler").css({'left':width});
            $(".drag_bg").css({'width': width});
          }else if(width>this.maxwidth){
            this.successFunction();
          }
        }
      });
      $('body').on('mouseup',(e) => {     //鼠标放开
        this.mouseMoveStata = false;
        var width = e.clientX - this.beginClientX;
        if(width<this.maxwidth){
          $(".handler").css({'left':0});
          $(".drag_bg").css({'width': 0});
        }
      })
    }
  }
</script>

<style scoped>
  .drag{
    position: relative;
    background-color: #e8e8e8;
    color: darkgrey;
    width: 300px;
    height: 34px;
    margin-bottom: 10px;
    line-height: 34px;
    text-align: center;
    border-radius: 5px;
  }
  .handler{
    position: absolute;
    top: 0px;
    left: 0px;
    width: 40px;
    height: 32px;
    border: 1px solid #ccc;
    border-radius: 5px;
    cursor: move;
  }
  .handler_bg{
    background: #fff  no-repeat center;
  }
  .handler_ok_bg{
    background: #fff  no-repeat center;
  }
  .drag_bg{
    background-color: #6bc5a4;
    height: 34px;
    width: 0px;
  }
  .drag_text{
    position: absolute;
    top: 0px;
    font-size: 16px;
    width: 300px;
    -moz-user-select: none;
    -webkit-user-select: none;
    user-select: none;
    -o-user-select:none;
    -ms-user-select:none;
  }
</style>
