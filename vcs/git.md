# GIT NOTE

## 配置相关

### 控制台中文显示

`git status` utf-8文件名显示被自动转码，修改为中文显示：

```sh
git config --global core.quotepath false
```

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

[git_中文编码问题](https://blog.csdn.net/qq_36209121/article/details/92798446)
[git修改分支名称](https://www.jianshu.com/p/cc740394faf5)