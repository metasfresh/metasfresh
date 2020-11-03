UPDATE ad_column
SET columnsql='-1',
    technicalnote=
        'Lazy-Loading=N; we need it in the REST-API. Also I played with it and think it''s not a big performance-burden (PS: BUT IT IS)

        was: count(1) over (partition by headeraggregationkey)
        then was: count(1) over (partition by c_order_id)'
WHERE columnname = 'FilteredItemsWithSameHeaderAggregationKey'
;
