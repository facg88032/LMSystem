import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/components/Login'
import AppIndex from '@/components/home/Appindex'
import Home from '@/components/Home'
import Library from '@/components/library/LibraryIndex'
import Register from '@/components/Register'
import store from '@/store'
const axios = require('axios')
const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  { path: '/home',
    name: 'Home',
    component: Home,
    redirect: '/index',
    children:[
      {
        path: '/index',
        name: 'AppIndex',
        component: AppIndex,
        // meta: {
        //   requireAuth: true
        // }
      },
      {
        path: '/library',
        name: 'Library',
        component: Library,
        // meta: {
        //   requireAuth: true
        // }
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('../components/admin/AdminIndex'),
    meta: {
      requireAuth: true
    },
    // children:[
    //   {
    //     path: '/admin/dashboard',
    //     name: 'Dashboard',
    //     component: () => import('../components/admin/dashboard/admin/index'),
    //     meta: {
    //       requireAuth: true
    //     }
    //   }
    // ]
  }
]



const router = createRouter({
  history: createWebHistory(),
  routes
})


router.beforeEach((to, from, next) => {

  if (store.state.username && to.path.startsWith('/admin')) {
    initAdminMenu(router, store)
  }

  // 已登录状态下访问 login 页面直接跳转到后台首页
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
