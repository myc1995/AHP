import Vue from 'vue'
import Router from 'vue-router'
import AHP from '@/components/AHP'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/ahp',
      name: 'AHP',
      component: AHP
    }
  ]
})
