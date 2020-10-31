import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Register from '@/components/Register'
import FindPassword from '@/components/FindPassword'
import Home from '@/components/Home'
import Myinfo from '@/components/Myinfo'
import test from '@/components/test'
import editor from '@/components/editor'
import RecentlyDoc from '@/components/HomePage/RecentlyDoc'
import Model00 from '@/components/Model/Model00'
import Model01 from '@/components/Model/Model01'
import Model02 from '@/components/Model/Model02'
import Model03 from '@/components/Model/Model03'
import Model04 from '@/components/Model/Model04'
import Model05 from '@/components/Model/Model05'
import OthersInfo from '@/components/OthersInfo'
import down from '@/components/down'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/Register',
      name: 'Register',
      component: Register
    },
    {
      path: '/FindPassword',
      name: 'FindPassword',
      component: FindPassword
    },
    {
      path: '/Home',
      name: 'Home',
      component: Home
    },
    {
      path: '/Myinfo',
      name: 'Myinfo',
      component: Myinfo
    },
    {
      path: '/test',
      name: 'test',
      component: test
    },
    {
      path: '/editor',
      name: 'editor',
      component: editor
    },
    {
      path: '/RecentlyDoc',
      name: 'RecentlyDoc',
      component: RecentlyDoc
    },
    {
      path: '/Model00',
      name: 'Model00',
      component: Model00
    },
    {
      path: '/Model01',
      name: 'Model01',
      component: Model01
    },
    {
      path: '/Model02',
      name: 'Model02',
      component: Model02
    },
    {
      path: '/Model03',
      name: 'Model03',
      component: Model03
    },
    {
      path: '/Model04',
      name: 'Model04',
      component: Model04
    },
    {
      path: '/Model05',
      name: 'Model05',
      component: Model05
    },
    {
      path: '/OthersInfo',
      name: 'OthersInfo',
      component: OthersInfo
    },
    {
      path: '/down',
      name: 'down',
      component: down
    },
  ]
})
