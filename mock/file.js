
import Mock from 'mockjs'
import {port_code, port_file} from 'common/port_uri'

Mock.mock(new RegExp(port_file.image_upload), {
  code: port_code.success,
  msg: "图片上传成功",
  data: {
    'id|10-100': 1,
    "name": "@ctitle",
    "image": "@image"
  }
})
