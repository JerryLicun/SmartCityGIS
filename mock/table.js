

//获取url参数
const parseQueryString = (url) => {
  var reg_url = /^[^\?]+\?([\w\W]+)$/,
    reg_para = /([^&=]+)=([\w\W]*?)(&|$|#)/g,
    arr_url = reg_url.exec(url),
    ret = {}
  if (arr_url && arr_url[1]) {
    var str_para = arr_url[1], result;
    while ((result = reg_para.exec(str_para)) != null) {
      ret[result[1]] = result[2]
    }
  }
  return ret

}

import Mock from 'mockjs'
import {port_code, port_table} from 'common/port_uri'

// 姓名	性别	年龄	生日	地址

const data_list = [{
  'person_id|+1': 1,
  'realname': '@cname',
  'gender': '@pick(男,女)',//1男，2女
  'username': '@FIRST',
  'job_id': '@pick(武汉市政府办公厅,洪山区税务局,洪山区工商局,洪山区劳动局)@pick(人事部,办公室,财政部)@pick(经理,主任,部长,职员)',
  'telephone':  /^1[385][1-9]\d{8}/,
  'email':'@email',
  'isadmin': '@pick([0, 1])'
}]

Mock.mock(new RegExp(port_table.list), ({url}) => {
  const params = parseQueryString(url)
  return Mock.mock({
    code: port_code.success,
    msg: '获取成功',
    data: {
      [`result|${params.length}`]: data_list,
      page: Number.parseInt(params.page),
      'total': 200
    }
  })
})

Mock.mock(new RegExp(port_table.get), {
  code: port_code.success,
  msg: '获取成功',
  data: data_list[0]
})

Mock.mock(new RegExp(port_table.del), {
  code: port_code.success,
  msg: '删除成功'
})

Mock.mock(new RegExp(port_table.save), {
  code: port_code.success,
  msg: '操作成功'
})

Mock.mock(new RegExp(port_table.batch_del), {
  code: port_code.success,
  msg: '批量删除成功'
})
