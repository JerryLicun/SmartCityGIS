
import fetch from 'common/fetch'
import {port_user} from 'common/port_uri'

//登录
export function login(data) {
  return fetch({
    url: port_user.login,
    method: 'post',
    data
  })
}
//登出
export function logout() {
  return fetch({
    url: port_user.logout,
    method: 'post'
  })
}
