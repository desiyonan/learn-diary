# JDK8 集合类排序比较

## 使用 `Comparator` 遇到的问题

- `Comparator.comparing` 无法推断类型

使用 `Comparator.comparing` 和 `thenComparing` 时编译提示

## List排序

- List.sort()
- CollectionUtils.sort()

以 `Map<String,Object>` 为例

示例中使用到的静态方法

```java
import static java.util.Collections.singletonMap;
import static java.util.Collections.sort;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.Comparator.nanaturalOrder;
import static java.util.Comparator.nullsFrist;
import static java.util.Comparator.nullsLast;

import static org.apache.commons.collections4.MapUtils.getLong;
import static org.apache.commons.collections4.MapUtils.getString;
```

按单一键排序（升序）

```java
@Test
public void testComparator(){
    List<Map<String, Object>> source = new LinkedList<>();
    source.add(singletonMap("id", 1));
    source.add(singletonMap("id", 2));
    source.sort(comparing((Map<String, Object> m) -> getString(m, "id")));
}
// [{id:1},{id:2}]
```

按单一键反序排序（降序）

```java
@Test
public void testComparator(){
    List<Map<String, Object>> source = new LinkedList<>();
    source.add(singletonMap("id", 1));
    source.add(singletonMap("id", 2));
    source.sort(comparing((Map<String, Object> m) -> getString(m, "id"), reverseOrder()));
}
// [{id:2},{id:1}]
```

按多个键排序（降序）

```java
@Test
public void testComparator(){
    List<Map<String, Object>> source = new LinkedList<>();
    Map<String,Object> data;
    for (int i = 0; i < 4; i++) {
        data = new HashMap<>(2);
        data.put("id",i/2);
        data.put("time","t"+(i%2));
        source.add(data);
    };

    source.sort(
        comparing((Map<String, Object> m) -> getString(m, "id"))
            .thenComparing(m -> getLong(m, "time"))
        );
}
// [
//  {id:0,time:t0},
//  {id:0,time:t1},
//  {id:1,time:t0},
//  {id:1,time:t1}
// ]
```

按多个键反序排序（降序）

```java
@Test
public void testComparator(){
    List<Map<String, Object>> source = new LinkedList<>();
    Map<String,Object> data;
    for (int i = 0; i < 4; i++) {
        data = new HashMap<>(2);
        data.put("id",i/2);
        data.put("time","t"+(i%2));
        source.add(data);
    };

    //1. id 降序
    source.sort(
        comparing((Map<String, Object> m) -> getString(m, "id"),reverseOrder())
            .thenComparing(m -> getLong(m, "time"))
        );
    System.out.println(source);

    //2. time 降序
    source.sort(
        comparing((Map<String, Object> m) -> getString(m, "id"))
           .thenComparing(m -> getLong(m, "time"),reverseOrder())
    );
    System.out.println(source);

    //3. id,time 降序
    source.sort(
        comparing((Map<String, Object> m) -> getString(m, "id"), reverseOrder())
            .thenComparing(m -> getLong(m, "time"),reverseOrder())
    );
    System.out.println(source);
}
// 1.
// [
//  {id:1,time:t0},
//  {id:1,time:t1},
//  {id:0,time:t0},
//  {id:0,time:t1}
// ]

// 2.
// [
//  {id:0,time:t1},
//  {id:0,time:t0},
//  {id:1,time:t1},
//  {id:1,time:t0},
//

// 3.
// [
//  {id:1,time:t1},
//  {id:1,time:t0},
//  {id:0,time:t1},
//  {id:0,time:t0}
// ]
```

空处理排序

- `Comparator.nullsFrist` 空放前面
- `Comparator.nullsLast`  空放后面

```java
@Test
public void testComparator(){
    List<Map<String, Object>> source = new LinkedList<>();
    source.add(singletonMap("id", 2));
    source.add(singletonMap("id", 1));
    source.add(singletonMap("time", 9));
    source.add(singletonMap("time", 8));

    source.sort(
            comparing((Map<String, Object> m) -> getString(m, "id"),nullsFrist(reverseOrder()))
                .thenComparing(m -> getLong(m, "time"), nullsLast(naturalOrder()))
    );
    System.out.println(source);
}
//[
// {time=8}, {time=9},
// {id=2}, {id=1}
//]
```
