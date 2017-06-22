-- 2017-06-22T18:02:24.657
-- Setting the implicit selection criteria for default filters
update ad_column
set isSelectionColumn = 'Y', updated = '2017-06-22', updatedby = 99
where ad_column_id in
( select ad_column_id 
from ad_column
where true
and lower(columnname) in (
'description',
'documentno',
'name',
'value'
)
and isSelectionColumn = 'N'
)
;
