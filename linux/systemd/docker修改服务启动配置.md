# 修改 docker daemon.json 导致服务无法重启

## 前言

起因本是想通过修改 `daemon.json` 的 `hosts` 来监听本地端口实现远程链接，然而之后无法成功重启，经过一番百度和排除最后才得知 `dockerd` 和 `daemon.json` 的参数不能重复，否则会导致冲突

## 日志排除

```sh
$ sudo journalctl -amu docker
......
dockerd[31014]: unable to configure the Docker daemon with file /etc/docker/daemon.json: the following directives are specified both as a flag and in the configuration file: hosts: (from flag: [fd://], from file: [tcp://0.0.0.0:2375 unix:///var/run/docker.sock])
......
```

错误明确的提示两个地方配置了 `hosts`, 一个 `daemon.json` 和 一个命令行参数
其中 `daemon.json` 包含：

```sh
$ cat /etc/docker/daemon.json
{
  ...
  "hosts": [
        "tcp://0.0.0.0:2375",
        "unix:///var/run/docker.sock"
    ]
}
```

而对应命令行同样包含参数 `-H`

```sh
$ sudo systemctl cat docker.service
# /lib/systemd/system/docker.service
......
[Service]
Type=notify
# the default is not to use systemd for cgroups because the delegate issues still
# exists and systemd currently does not support the cgroup feature set required
# for containers run by docker
ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
......
```

可以看到 `/lib/systemd/system/docker.service` 下的启动命令与 `daemon.json` 产生了冲突

## 解决

解决方法比较多，这里简单提供一种，如果其他需求可以参考 [docker 远程连接设置](https://www.cnblogs.com/weiyiming007/p/10168733.html) 其中解决方法总结得比较全面

解决方法：

```sh
$ sudo systemctl edit docker.service
[Service]
# 清除之前的值（必须）
ExecStart=
# 重设
ExecStart=/usr/bin/dockerd --containerd=/run/containerd/containerd.sock
```

输入内容并命名保存，该命令的内容会生产一个 `/etc/systemd/system/docker.service.d/override.conf` 文件，其中那个内容会覆盖 `/lib/systemd/system/docker.service` 下的同单元，详细参考文档 [systemd#Editing provided units](https://wiki.archlinux.org/index.php/systemd#Editing_provided_units)

重新加载并查看

```sh
$ sudo systemctl daemon-reload
$ sudo systemctl cat docker.service
# /lib/systemd/system/docker.service
......
[Service]
Type=notify
# the default is not to use systemd for cgroups because the delegate issues still
# exists and systemd currently does not support the cgroup feature set required
# for containers run by docker
ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
......

# /etc/systemd/system/docker.service.d/execstart.conf
[Service]
ExecStart=
ExecStart=/usr/bin/dockerd --containerd=/run/containerd/containerd.sock
$ sudo systemctl restart docker.service
$ sudo systemctl status docker.service
● docker.service - Docker Application Container Engine
   Loaded: loaded (/lib/systemd/system/docker.service; enabled; vendor preset: enabled)
  Drop-In: /etc/systemd/system/docker.service.d
           └─execstart.conf
   Active: active (running) since Sat 2019-04-20 22:26:30 CST; 6s ago
```

可以看到生成的 `/etc/systemd/system/docker.service.d/execstart.conf` 配置被加载到最后实现覆盖并成功启动
关于 `dockerd 命令参数和 daemon.json 冲突`造成的问题暂时先记录到这里，若是那里有误或是建议可以联系我，谢谢