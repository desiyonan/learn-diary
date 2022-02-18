# session源码解析

基于 `org.mybatis:mybatis:3.4.6` 源码包

## SqlSession

作为使用 Mybatis 的主要方式，SqlSession 的功能非常强大，它包含了所有可执行语句、事务提交和回滚以及获取映射器的方法。
深入了解 SqlSession 源码，需要关注以下几点：

1. SqlSession 实例的创建获取
2. SqlSession 提供的 API
3. SqlSession 的主要实现类
4. SqlSession 执行 sql 的基本过程

### 获取 session 实例

在 Mybatis 中 SqlSession 实例对象的获取主要是通过对应的工厂接口 SqlSessionFactory 创建, 而该接口内提供了一组获取 SqlSession 实例对象的方法

```java
SqlSession openSession();

SqlSession openSession(boolean autoCommit);
SqlSession openSession(Connection connection);
SqlSession openSession(TransactionIsolationLevel level);

SqlSession openSession(ExecutorType execType);
SqlSession openSession(ExecutorType execType, boolean autoCommit);
SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);
SqlSession openSession(ExecutorType execType, Connection connection);
```

官方简介上有对使用以上方法获取 `SqlSession` 实例的说明，通常需要考虑到以下几方面：

> - 事务处理：我需要在 session 使用事务或者使用自动提交功能（auto-commit）吗？（通常意味着很多数据库和/或 JDBC 驱动没有事务）
> - 连接：我需要依赖 MyBatis 获得来自数据源的配置吗？还是使用自己提供的配置？
> - 执行语句：我需要 MyBatis 复用预处理语句和/或批量更新语句（包括插入和删除）吗？

mybaits 在枚举 `TransactionIsolationLevel` 下定义了支持的事务级别，同时也可以在接口 `Connection` 找到对应常量值：

```java
public enum TransactionIsolationLevel {
  NONE(Connection.TRANSACTION_NONE),                         // 0 无事务
  READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),     // 1 不可重复读，阻止脏读，但允许不可重复读和幻读
  READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED), // 2 读未提交，允许脏读、不可重复读、幻读
  REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),   // 4 可重复读，阻止幻读
  SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);         // 8 串行，阻止脏读、不可重复读和幻读
}
```

### session API

```java
<T> T selectOne(String statement, Object parameter)
<E> List<E> selectList(String statement, Object parameter)
<K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey)
int insert(String statement, Object parameter)
int update(String statement, Object parameter)
int delete(String statement, Object parameter)
```

## 参数解析

## sql 执行

## 结果处理

## 参考

[Mybatis 3简介](http://www.mybatis.org/mybatis-3/zh/java-api.html)