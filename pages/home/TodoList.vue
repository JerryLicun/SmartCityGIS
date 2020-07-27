<template>
    <div class="container">
      <el-input placeholder="回车添加待办事项" class="todoinput" @keyup.enter.native="add" v-model="newtodo.content"></el-input>
      <p style="font-size: 16px">进行中：{{ todolist.length }} 已完成：{{ donenum }}</p>
      <el-row v-for="(item, index) in todolist" class="list-row">
        <el-col :xs="2" :sm="1" :md="1" :lg="1" :xl="1" class="check" :class="{ red: !todolist[index].done, 'green': todolist[index].done }">
          <el-checkbox fill="dimgrey" size="mini" v-model="item.done"></el-checkbox>
        </el-col>
        <el-col :xs="20" :sm="22" :md="22" :lg="22" :xl="22">
          <input type="text" v-model="item.content" class="ipcont" :class="{done: todolist[index].done}">
        </el-col>
        <el-col :xs="2" :sm="1" :md="1" :lg="1" :xl="1" class="close">
          <i class="el-icon-close" @click="del(index)"></i>
        </el-col>
      </el-row>
    </div>
</template>

<script>
    var STORAGE_KEY = 'todolist'

    function fetch() {
      return JSON.parse(window.localStorage.getItem(STORAGE_KEY) || '[]')
    }
    function save(items) {
      window.localStorage.setItem(STORAGE_KEY, JSON.stringify(items))
    }
    export default {
      name: "TodoList",
      data(){
        return {
          newtodo: {
            content: '',
            done: false
          },
          todolist:fetch()
        }
      },
      methods: {
        add: function () {
          if (this.newtodo.content) {
            this.todolist.push(this.newtodo)
            this.newtodo = { content: '', done: false }
          }
        },
        del: function (index) {
          this.todolist.splice(index, 1)
        }
      },
      computed: {
        donenum: function () {
          return this.todolist.filter(function (val) { return val.done }).length
        }
      },
      watch: {
        todolist: {
          handler(items) {
            save(items)
          },
          deep: true
        }
      }
    }
</script>

<style scoped>
  .container{
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    color: #756C83;
    text-align: center;
  }

  .todoinput {
    margin-bottom: 10px;
  }

  .list-row {
    height: 40px;
    background-color: #fbfbfb;
    margin-bottom: 5px;
  }

  .check {
    text-align: center;
    line-height: 40px;
  }

  .ipcont {
    width: 90%;
    margin-top: 8px;
    border: 0;
    line-height: 24px;
    background-color: transparent;
    font-size: 16px;
    color: #756C83;
  }

  .close {
    text-align: center;
    line-height: 40px;
  }

  .el-icon-close {
    cursor: pointer;
    font-size: 14px;
  }

  .el-icon-close:hover {
    color: #ef5f65;
  }

  .done {
    text-decoration: line-through;
  }
</style>
