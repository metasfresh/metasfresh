update ad_column c
set iscalculated = 'Y',
    updated      = '2020-03-10 17:01:56.767453+01',
    updatedBy    = 99
from ad_table t
where true
  and t.ad_table_id = c.ad_table_id
  and t.isview = 'N'
  and c.columnname in ('DocStatus', 'DocAction', 'Processed', 'Processing')
  and c.columnsql is null
  and c.iscalculated = 'N';
