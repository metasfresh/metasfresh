update ad_column set ad_reference_value_id=null where ad_column_id=55546;

-- Found the case using:
/*
 select t.tablename, c.columnname,
       (select z.ad_reference_id||'-'||z.name from ad_reference z where z.ad_reference_id=c.ad_reference_id) as displayType,
       c.ad_reference_value_id, r.name, r.validationtype,
       c.updated, c.updatedby,
       c.ad_column_id
from ad_column c
         inner join ad_table t on c.ad_table_id = t.ad_table_id
         left outer join ad_reference r on r.ad_reference_id=c.ad_reference_value_id
where true
  --and (t.tablename='A_Depreciation_Entry' and c.columnname='AD_Client_ID')
  and c.ad_reference_id in (30, 18) and r.validationtype!='T'
;
 */