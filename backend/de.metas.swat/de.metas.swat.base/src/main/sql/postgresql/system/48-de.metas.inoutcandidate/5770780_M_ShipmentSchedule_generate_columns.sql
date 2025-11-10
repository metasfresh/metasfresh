UPDATE ad_column
SET isforceincludeingeneratedmodel='Y'
WHERE ad_column_id IN (549794, 550834, 551063)
;

/*
select c.ad_column_id, t.tablename, c.columnname, c.isforceincludeingeneratedmodel, c.entitytype
from ad_column c
         inner join ad_table t on t.ad_table_id=c.ad_table_id
where t.tablename='M_ShipmentSchedule'
  -- and c.columnname in ('M_HU_PI_Item_Product_ID','M_HU_PI_Item_Product_Override_ID','QtyOrdered_TU')
  and c.entitytype!=t.entitytype
order by c.columnname;
 */