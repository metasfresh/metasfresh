-- DELETE ALL THE PERIOD CONTROLS THAT HAVE A DocBaseType which is not anymore in the C_DocType DocBaseType AD_Ref_List
SELECT backup_table('c_periodcontrol')
;

DELETE FROM c_periodcontrol
WHERE docbasetype NOT IN (select value from ad_ref_list where ad_reference_id = 183);
