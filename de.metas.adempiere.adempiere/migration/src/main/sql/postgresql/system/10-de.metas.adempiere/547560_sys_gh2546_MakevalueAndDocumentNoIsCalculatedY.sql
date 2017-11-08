--Mark all Value and DocumentNo columns as IsCalculated=Y 
update AD_Column
set IsCalculated='Y'
FROM 
(
select t.TableName, c.ColumnName, c.IsCalculated, c.AD_Column_ID, c.EntityType
from AD_Column c
inner join AD_Table t on (t.AD_Table_ID=c.AD_Table_ID)
where 
c.ColumnName in ('DocumentNo', 'Value')
and c.IsCalculated='N'
and t.IsView='N'
) as notCalculatedColumns
where AD_Column.AD_Column_ID = notCalculatedColumns.AD_Column_ID;
