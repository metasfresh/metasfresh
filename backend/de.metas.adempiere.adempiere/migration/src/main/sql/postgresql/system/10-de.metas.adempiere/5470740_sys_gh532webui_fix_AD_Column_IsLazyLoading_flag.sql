update AD_Column c set IsLazyLoading='N' where IsLazyLoading='Y' and ColumnSQL is null;

/* checking:
select t.TableName, c.ColumnName, c.IsLazyLoading, c.ColumnSQL
from AD_Column c
inner join AD_Table t on (t.AD_Table_ID=c.AD_Table_ID)
where IsLazyLoading='Y' and ColumnSQL is null
order by t.TableName, c.ColumnName
*/