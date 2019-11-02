# fish 使用笔记

## 快捷键

`ctrl r` 代替方案： 安装 `fzf` 并绑定快捷键盘

```sh
echo \
"
function fish_user_key_bindings
    fzf_key_bindings
end" > ~/.config/fish/functions/fish_user_key_bindings.fish
```

## 配置

### prompt

fish_prompt
fish_right_prompt

## 参考资料

[安装 Fish](https://www.jianshu.com/p/1e2cd9e3e8ff)
