update ad_column c
set iscalculated = 'Y'
from ad_table t
where true
  and t.ad_table_id = c.ad_table_id
  and t.isview='N'
  and c.columnname in ('DocStatus', 'DocAction', 'Processed', 'Processing')
  and c.columnsql is null
  and c.iscalculated='N';