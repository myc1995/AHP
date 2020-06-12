<template>
  <div>
    <el-row>
      <el-col :span="9">
        <el-button type="primary" @click="this.newProject">新建工程</el-button>
        <el-tree
          :data="data4"
          :props="defaultProps"
          node-key="id"
          default-expand-all
          :expand-on-click-node="false"
          :render-content="renderContent">
        </el-tree>
        <el-button type="primary" @click="this.build">生成树图</el-button>
        <el-button type="primary" @click="">编辑权重</el-button>
        <el-button type="primary" @click="">工程分析</el-button>
      </el-col>
      <el-col :span="15">
        <div>
          <div id="chart"></div>
        </div>
      </el-col>
    </el-row>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-input v-model="input" placeholder="请输入节点名称"></el-input>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="this.doAdd">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible1"
      width="30%"
      :before-close="handleClose">
      <el-input v-model="input1" placeholder="请输入节点名称"></el-input>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible1 = false">取 消</el-button>
    <el-button type="primary" @click="this.addNewProject">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
  import echarts from 'echarts'

  let id = 1000
  export default {
    data () {
      return {
        addData: {},
        input1: '',
        dialogVisible1: false,
        input: '',
        dialogVisible: false,
        data4: [
          {
            id: 1,
            label: '一级 1',
            children: [{
              id: 4,
              label: '二级 1-1',
              children: [
                {
                  id: 9,
                  label: '三级 1-1-1',
                  children: []
                }, {
                  id: 10,
                  label: '三级 1-1-2',
                  children: []
                }]
            }]
          }
        ],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        treeData: {
          'name': '烟草质量',
          'children': [
            {
              'name': '施肥',
              'children': [
                {
                  'name': '施肥',
                  'children': [
                    {'name': '氮', 'value': 4116, 'children': []},
                    {'name': '磷', 'value': 2105, 'children': []},
                    {'name': '钾', 'value': 1316, 'children': []},
                    {'name': '硼', 'value': 3151, 'children': []},
                  ]
                }
              ]
            },
            {
              'name': '光照',
              'children': [
                {'name': '充足', 'value': 3770, 'children': []},
                {'name': '正常', 'value': 2435, 'children': []},
                {'name': '缺乏', 'value': 4839, 'children': []},
                {'name': '暴晒', 'value': 1756, 'children': []},
                {'name': '无光', 'value': 4268, 'children': []},

              ]
            },
            {
              'name': '水分',
              'children': [
                {'name': '干旱', 'value': 8833, 'children': []},
                {'name': '充足', 'value': 1821, 'children': []},
                {'name': '湿涝', 'value': 5833, 'children': []}
              ]
            }
          ]
        },
        newTreeData: [],
        childInfo: [],
        eachChildren:[]
      }
    },
    methods: {
      build () {
        this.buildTree(this.data4)
      },
      buildTree (data) {
        //首先判断eachChildren是否为空，如果不为空，则要构建对象，然后压入到空的数组内，再让eachChildren空，再执行递归
        for (let i = 0; i < data.length; i++) {
          if (data[i].children.length > 0) {
            this.buildTree(data[i].children)
          } else if (data[i].children.length === 0) {
            let obj={'name':data[i].label,'value':'1','children':[]}
            this.eachChildren.push(obj)
          }
        }
      },
      addNewProject () {
        const newChild = {id: 1, label: this.input1, children: []}
        this.data4.push(newChild)
        this.dialogVisible1 = false
      },
      newProject () {
        if (this.data4.length !== 0) {
          alert('已存在工程，无法新建！')
        } else if (this.data4.length === 0) {
          this.dialogVisible1 = true
        }
      },
      handleClose (done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done()
          })
          .catch(_ => {
          })
      },
      doAdd () {
        if (this.data4.length === 0) {
          this.dialogVisible = false
          return null
        }
        const newChild = {id: id++, label: this.input, children: []}
        if (!this.addData.children) {
          this.$set(this.addData, 'children', [])
        }
        this.addData.children.push(newChild)
        this.input = ''
        this.addData = {}
        this.dialogVisible = false
      },
      handleAdd (data) {
        this.addData = data
        this.dialogVisible = true
      },
      handleModify (node, data) {
      },
      remove (node, data) {
        const parent = node.parent
        const children = parent.data.children || parent.data
        const index = children.findIndex(d => d.id === data.id)
        children.splice(index, 1)
      },
      renderContent (h, {node, data, store}) {
        return (
          <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
          <span>
          <span>{node.label}</span>
          </span>
          <span>
          <el-button style="font-size: 12px;" type="text" on-click={ () => this.handleAdd(data) }>添加节点</el-button>
        <el-button style="font-size: 12px;" type="text" on-click={ () => this.handleModify(node, data) }>修改节点</el-button>
        <el-button style="font-size: 12px;" type="text" on-click={ () => this.remove(node, data) }>删除节点</el-button>
        </span>
        </span>
        )
      },
      handleNodeClick (data) {
        console.log(data.value)
      },
      printChart () {
        let chart = echarts.init(document.getElementById('chart'))
        chart.showLoading()
        chart.setOption({
          tooltip: {
            trigger: 'item',
            triggerOn: 'mousemove'
          },
          legend: {
            top: '2%',
            left: '3%',
            orient: 'vertical',
            data: [
              {
                // name: 'tree',
                // icon: 'rectangle'
              }],
            borderColor: '#c23531'
          },
          series: [
            {
              type: 'tree',
              name: 'tree',
              data: [this.treeData],
              top: '10%',
              left: '10%',
              bottom: '10%',
              right: '10%',
              symbolSize: 10,
              label: {
                position: 'left',
                verticalAlign: 'middle',
                align: 'right'
              },
              leaves: {
                label: {
                  position: 'right',
                  verticalAlign: 'middle',
                  align: 'left'
                }
              },
              expandAndCollapse: true,
              animationDuration: 550,
              animationDurationUpdate: 500
            }
          ]
        })
        chart.hideLoading()
      }
    },
    mounted () {
      this.printChart()
    }
  }
</script>
<style>
  #chart {
    width: 100%;
    height: 500px;
    border: 1px solid red;
    margin: 0 auto;
  }

  #form {
    width: 100%;
    height: 500px;
    border: 1px solid red;
    margin: 0 auto;
  }
</style>
