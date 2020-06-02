# JasperReport

## 动态SQL

```text



```

## 页面大小 - 像素转换

模板默认 `A4` 大小是在 `72 dpi` 下，浏览器打印是设定的是 `96 dpi`, 可以通过计算得到应调整的像素宽高

>A4纸的尺寸是210mm*297mm，也就是21.0cm*29.7cm，而1英寸=2.54cm，如果屏幕DPI分辨率为72像素/英寸，换算一下：相当于1cm可呈现 (72px/2.54cm) = 28.34px
分辨率是72像素/英寸时，A4纸的尺寸的图像的像素是595×842；(jasper A4 默认)
分辨率是96像素/英寸时，A4纸的尺寸的图像的像素是794×1123；(打印机、浏览器默认)
分辨率是120像素/英寸时，A4纸的尺寸的图像的像素是1487×2105；
分辨率是150像素/英寸时，A4纸的尺寸的图像的像素是1240×1754；
分辨率是300像素/英寸时，A4纸的尺寸的图像的像素是2480×3508；

## 强制分页

```java

final SimpleHtmlExporterConfiguration exporterConfig = new SimpleHtmlExporterConfiguration();
exporterConfig.setBetweenPagesHtml("<div style=\"page-break-after:always\"> </div>"); // 通过 css 设置打印换页符
HtmlExporter exporter = new HtmlExporter();
exporter.setConfiguration(exporterConfig);

```

## 参考资料

[打印常识：A4纸张在显示器上应该要多少像素?](https://www.cnblogs.com/yjmyzz/archive/2012/01/09/2316892.html)
[CSS page-break-after 属性](https://www.w3school.com.cn/cssref/pr_print_page-break-after.asp)
[java web jasperreport+ireport 实现多记录自动分页打印](https://blog.csdn.net/towardsfuture/article/details/18399927)
