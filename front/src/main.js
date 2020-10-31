// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import { Col, Row } from 'vant'
import { Form } from 'vant'
import { Field } from 'vant'
import { Button } from 'vant'
import { Sticky } from 'vant'
import { Icon } from 'vant'
import { Image as VanImage } from 'vant'
import { DropdownMenu, DropdownItem } from 'vant'
import { Toast } from 'vant'
import { Sidebar, SidebarItem } from 'vant'
import { Tab, Tabs } from 'vant'
import { Dialog } from 'vant'
import { Cell, CellGroup } from 'vant'
import { Popup } from 'vant'
import { Grid, GridItem } from 'vant'
import { Pagination } from 'vant'
import { List } from 'vant'
import { Search } from 'vant'
import { Checkbox, CheckboxGroup } from 'vant'
import VueContextMenu from 'vue-contextmenu'
import { RadioGroup, Radio } from 'vant'
import { Uploader } from 'vant'

Vue.use(Uploader)
Vue.use(Radio)
Vue.use(RadioGroup)
Vue.use(VueContextMenu)
Vue.use(Checkbox)
Vue.use(CheckboxGroup)
Vue.use(Search)
Vue.use(List)
Vue.use(Pagination)
Vue.use(Grid)
Vue.use(GridItem)
Vue.use(Popup)
Vue.use(Cell)
Vue.use(CellGroup)
Vue.use(Dialog)
Vue.use(Tab)
Vue.use(Tabs)
Vue.use(Sidebar)
Vue.use(SidebarItem)
Vue.use(Toast)
Vue.use(DropdownMenu)
Vue.use(DropdownItem)
Vue.use(VanImage)
Vue.use(Icon)
Vue.use(Sticky)
Vue.use(Button)
Vue.use(Field)
Vue.use(Form)
Vue.use(ElementUI)
Vue.use(Col)
Vue.use(Row)
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
