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

### 时间分片查询

```sql
SELECT time_bucket('1 mothod',wrap_date)+'5 minute' as five_min, first(create_date,wrap_date),last(create_date,wrap_date) 
FROM es_data WHERE ss_id=206 GROUP BY five_min ORDER BY five_min;
```
