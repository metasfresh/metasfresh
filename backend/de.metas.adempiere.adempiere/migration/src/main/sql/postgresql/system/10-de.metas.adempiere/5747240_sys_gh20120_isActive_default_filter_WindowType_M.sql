select backup_table('ad_column')
;

create table fix.gh62_isactive_columns as
SELECT

    w.name as window_name,
    c.ad_column_id,
    c.columnname,
    c.isselectioncolumn,
    c.filterdefaultvalue

FROM ad_window w
         JOIN ad_tab t ON t.ad_window_id = w.ad_window_id
         JOIN ad_field f ON f.ad_tab_id = t.ad_tab_id
         JOIN ad_table tab on tab.ad_table_id = t.ad_table_id
         JOIN ad_column c on f.ad_column_id = c.ad_column_id
WHERE true
  --and w.name = 'Geschäftspartner B2C'
  and w.windowtype='M'
  and c.columnname ilike'isactive'

ORDER BY w.name
;

update ad_column
set isselectioncolumn='Y', filterdefaultvalue='Y'
from (select *
      from fix.gh62_isactive_columns) data
where data.ad_column_id=ad_column.ad_column_id
;