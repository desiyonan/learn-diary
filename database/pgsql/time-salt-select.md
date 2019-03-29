# 时间段平均值查询

```sql
select
    name,
    TO_TIMESTAMP(TEXT( FLOOR ( to_number(create_at::text, '9999999999999999999') / 1000 ) * 1000 ), 'YYYYMMDDHH24MISS' )::TIMESTAMP
    round( avg( val ), 2 ) val
from sys_host_monitor
where
    1=1
    AND create_at between TO_TIMESTAMP(:startDate,'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP and TO_TIMESTAMP(:endDate,'YYYY-MM-DD HH24:MI:SS')::TIMESTAMP
GROUP BY
    TO_TIMESTAMP(TEXT( FLOOR ( to_number(create_at::text, '9999999999999999999') / 1000 ) * 1000 ), 'YYYYMMDDHH24MISS' )::TIMESTAMP,
    name
```