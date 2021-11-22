import { createRouter, createWebHistory } from 'vue-router'

import store from '@/store'
const axios = require('axios')
const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  { path: '/home',
    name: 'Home',
    component: () => import('@/components/Home'),
    redirect: '/index',
    children:[
      {
        path: '/index',
        name: 'AppIndex',
        component: () => import('@/components/home/Appindex'),
      },
      {
        path: '/library',
        name: 'Library',
        component: () => import('@/components/library/LibraryIndex'),
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: ()=> import('@/components/Login')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/components/Register')
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('../components/admin/AdminIndex'),
    meta: {
      requireAuth: true
    },
    children:[
      {
        path: '/admin/dashboard',
        name: 'Dashboard',
        component: () => import('../components/admin/dashboard/admin/index'),
        meta: {
          requireAuth: true
        }
      }
    ]
  }
]



const router = createRouter({
  history: createWebHistory(),
  routes
})


router.beforeEach((to, from, next) => {

  //訪問admin前 初始化加載menu
  if (store.state.username && to.path.startsWith('/admin')) {
    initAdminMenu(router, store)
  }

  // 已登入狀態訪問login 跳轉到admin/dashboard
  if (store.state.username && to.path.startsWith('/login')) {
    next({
      path: 'admin/dashboard'
    })
  }

  if (to.meta.requireAuth) {
        if (store.state.username) {
          axios.get('/authentication').then(resp => {
            if (resp.data) {
              next()
            }else {
              next({
                path: 'login',
                query: {redirect: to.fullPath}
              })
            }
          })
        } else {
          next({
            path: 'login',
            query: {redirect: to.fullPath}
          })
        }
      } else {
        next()
      }
    }
)

const formatRoutes = (routes) => {
  let fmtRoutes = []

  routes.forEach(route => {
    if (route.children) {
      route.children = formatRoutes(route.children)
    }
    let fmtRoute = {
      path: route.path,

      // component: (resolve) => require(['@/components/admin/' + route.component + '.vue'], resolve),
      component:() => require.ensure([], (require) => require('@/components/admin/' + route.component + '.vue')),
      name: route.name,
      nameZh: route.nameZh,
      iconCls: route.iconCls,
      meta: {
        requireAuth: true
      },
      children: route.children
    }

    fmtRoutes.push(fmtRoute)
  })
  return fmtRoutes
}




const initAdminMenu = (router, store) => {
  // 防止重复触发加载菜单操作
  if (store.state.adminMenus.length > 0) {
    return
  }
  axios.get('/menu').then(resp => {
    if (resp && resp.status === 200) {
      let fmtRoutes = formatRoutes(resp.data)
      fmtRoutes.forEach(fmtRoute =>{
        router.addRoute(fmtRoute)
      })
      store.commit('initAdminMenu', fmtRoutes)
    }
  })
}


export default router
