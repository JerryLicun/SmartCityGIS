

import Mock from 'mockjs'
import {port_code, port_user} from 'common/port_uri'

Mock.mock(new RegExp(port_user.login), ({body}) => {
  const {username, password} = JSON.parse(body)
  if (username === 'Yang' && password === 'admin') {
    return Mock.mock({
      code: port_code.success,
      msg: "登录成功",
      data: {
        'name': username,
      }
    })
  } else {
    return Mock.mock({
      code: port_code.error,
      msg: "账号或密码错误"
    })
  }
})

Mock.mock(new RegExp(port_user.logout), {
  code: port_code.success,
  msg: "退出成功"
})
