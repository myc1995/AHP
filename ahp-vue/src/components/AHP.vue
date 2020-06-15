<template>
  <div>
    <div>
      <el-tree :data="data" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
      <button v-on:click="handleClick">反转消息</button>
    </div>
    <div>
      <div id="chart"></div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import echarts from 'echarts'

  export default {
    data () {
      return {
        data: [{
          label: '一级 1',
          children: [{
            label: '二级 1-1',
            children: [{
              label: '三级 1-1-1'
            }]
          }]
        }, {
          label: '一级 2',
          children: [{
            label: '二级 2-1',
            children: [{
              label: '三级 2-1-1'
            }]
          }, {
            label: '二级 2-2',
            children: [{
              label: '三级 2-2-1'
            }]
          }]
        }, {
          label: '一级 3',
          children: [{
            label: '二级 3-1',
            children: [{
              label: '三级 3-1-1'
            }]
          }, {
            label: '二级 3-2',
            children: [{
              label: '三级 3-2-1'
            }]
          }]
        }],
        defaultProps: {
          children: 'children',
          label: 'label'
        }
      }
    },
    methods: {
      handleClick () {
        axios.post('/getAhp/hello')
          .then(function (response) {
            console.log(response.data)
          })
          .catch(function (err) {
            console.log(err)
          })
      },
      drawTree () {
        let chart = echarts.init(document.getElementById('chart'))
      }
    },

  }
</script>

<style scoped>

</style>
