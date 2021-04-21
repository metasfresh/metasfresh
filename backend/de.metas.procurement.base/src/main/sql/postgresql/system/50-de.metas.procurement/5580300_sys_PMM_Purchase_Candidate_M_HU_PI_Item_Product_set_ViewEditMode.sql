-- 2021-02-23T23:05:58.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET ViewEditMode='D',Updated=TO_TIMESTAMP('2021-02-24 01:05:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541894
;

/*
SELECT t.tablename, c.columnname, f.ad_field_id, aue.ad_ui_element_id
FROM ad_column c
         INNER JOIN ad_table t ON c.ad_table_id = t.ad_table_id
         INNER JOIN ad_field f ON c.ad_column_id = f.ad_column_id
         INNER JOIN ad_ui_element aue ON f.ad_field_id = aue.ad_field_id
WHERE t.tablename = 'PMM_PurchaseCandidate'
  AND c.columnname = 'M_HU_PI_Item_Product_Override_ID'
*/