/* Query to identify those columns:
select t.TableName, c.ColumnName, c.ReadOnlyLogic
from AD_Column c
inner join AD_Table t on (t.AD_Table_ID=c.AD_Table_ID)
where c.ColumnName='EntityType'
and c.ReadOnlyLogic='@EntityType@=D';
*/

update AD_Column c set ReadOnlyLogic=null where c.ColumnName='EntityType' and c.ReadOnlyLogic='@EntityType@=D';

