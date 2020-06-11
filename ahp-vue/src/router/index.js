import Vue from 'vue'
import Router from 'vue-router'
import AHP from '@/components/AHP'
import Tree from '@/components/Tree'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/ahp',
      name: 'AHP',
      component: AHP
    }, {
      path: '/tree',
      name: 'Tree',
      component: Tree
    }
  ]
})
