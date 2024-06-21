select backup_table('ad_column'); -- backup.ad_column_bkp_20240620_114638_818
select backup_table('ad_field'); -- backup.ad_field_bkp_20240620_114658_431

UPDATE AD_Field f
SET AD_Reference_ID = 30
WHERE f.AD_Reference_ID IN (18, 19)
  AND f.AD_Column_ID NOT IN (SELECT AD_Column_ID
                             FROM AD_Column
                             WHERE AD_Reference_ID = 30)
;

UPDATE AD_Column
SET AD_Reference_ID = 30
WHERE AD_Reference_ID IN (18, 19)
;

UPDATE AD_UI_Element
SET UIStyle=''
WHERE AD_Field_ID IN (SELECT AD_Field_ID
                      FROM AD_Field
                      WHERE AD_Reference_ID = 30
                         OR AD_Column_ID IN (SELECT AD_Column_ID
                                             FROM AD_Column
                                             WHERE AD_Reference_ID = 30))
;

