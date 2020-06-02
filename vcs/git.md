# GIT NOTE

## 配置相关

### 控制台中文显示

`git status` utf-8文件名显示被自动转码，修改为中文显示：

```sh
git config --global core.quotepath false
```

### 差异比较工具

配置 `beyond compare4`:

1. 安装 bc4, 并验证终端中是否可运行

    ```powershell
    PS > scoop install beyondcompare
    ...
    PS > bcomp
    # 安装成功将弹出窗口
    ```

2. 命令行输入 `git config --global -e` 或者直接编辑 `C:\Users\[用户名]\.gitconfig` 追加下列内容

    ```sh
    #追加
    [diff]
          tool = bc4
    [difftool]
          prompt = false
    [difftool "bc4"]
          cmd = bcomp $LOCAL $REMOTE
    ```

3. 运行 `git difftool -d HEAD^` 测试

## 日常命令

### branch

- 分支重命名
  - 本地：

    ```sh
    git branch oldName newName
    ```

  - 远程：

    ```sh
    #重命名远程分支对应的本地分支
    git branch -m oldName newName
    #删除远程分支
    git push --delete origin oldName
    #上传新命名的本地分支
    git push origin newName
    #把修改后的本地分支与远程分支关联
    git branch --set-upstream-to origin/newName
    ```

## 参考资料

[Git添加beyond compare4作为比较工具](https://segmentfault.com/a/1190000019606428?utm_source=tag-newest)
[git 设置 mergetool，difftool 为 BeyondCompare](https://blog.csdn.net/gdutxiaoxu/article/details/80455810)
[git_中文编码问题](https://blog.csdn.net/qq_36209121/article/details/92798446)
[git修改分支名称](https://www.jianshu.com/p/cc740394faf5)