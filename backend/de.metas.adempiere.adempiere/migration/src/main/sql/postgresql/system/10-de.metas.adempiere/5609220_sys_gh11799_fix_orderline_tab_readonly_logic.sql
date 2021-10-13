
UPDATE AD_Tab
SET readonlylogic=NULL, updated='2021-10-13 17:58', updatedby=99
WHERE ad_table_id = get_table_id('c_orderline')
  AND readonlylogic = '@Processed@=Y'; 
