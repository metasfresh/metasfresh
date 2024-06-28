select backup_table('ad_column'); -- backup.ad_column_bkp_20240620_114638_818
select backup_table('ad_field'); -- backup.ad_field_bkp_20240620_114658_431

UPDATE AD_Field f SET AD_Reference_ID = 30
FROM AD_Column c
WHERE f.AD_Column_ID=c.AD_Column_ID
  AND f.AD_Reference_ID IS NOT NULL
  AND f.AD_Reference_ID=c.AD_Reference_ID
;

UPDATE AD_Column
SET AD_Reference_ID = 30
WHERE AD_Reference_ID IN (18, 19)
;

