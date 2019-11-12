# Rest Client

REST HTTP 接口请求模拟工具

## 文件上传

```http
###

POST http://192.168.40.253:8688/cfs/oapi/0/cloud/disk/OpenFileUploadApi·/upload
Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryV7Zmh8EA1UWKV3bS
Accept: application/json

------WebKitFormBoundaryV7Zmh8EA1UWKV3bS
Content-Disposition: form-data; name="file"; filename="jj_p.jasper"
Content-Type: application/octet-stream

< tml/jj_p.jasper
------WebKitFormBoundaryV7Zmh8EA1UWKV3bS--

<> 2019-11-12T121837.200.json

###
```

NOTE：

1. 请求头不能有 `Content-Length` , 否则会抛出`ClientProtocolException`
