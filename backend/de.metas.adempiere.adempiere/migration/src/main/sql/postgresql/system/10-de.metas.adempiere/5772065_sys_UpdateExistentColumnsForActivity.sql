 -- AD_Val_Rule_ID=235 contains the validation logic IsSummary='N'

CREATE TABLE migration_data.C_Activity_id_ValidationRule_235 AS
SELECT c.AD_Val_Rule_ID, c.ad_reference_id, r.name AS RefName, c.ad_column_id, c.columnname, t.tablename, c.ad_table_id
FROM ad_column c
         JOIN ad_table t ON c.ad_table_id = t.ad_table_id
         JOIN ad_reference r ON c.ad_reference_id = r.ad_reference_id
WHERE c.AD_Element_ID = 1005
  AND t.isview = 'N'
  AND c.ad_reference_id <> 13 -- ID reference
ORDER BY tablename
;

SELECT backup_table('ad_column')
;

UPDATE ad_column
SET AD_Val_Rule_ID=235
from migration_data.C_Activity_id_ValidationRule_235 m
WHERE m.ad_column_id = ad_column.ad_column_id
  AND ad_column.AD_Val_Rule_ID IS NULL;
  
  
  ---------
  
  

create table migration_data.C_Activity_id_ValidationRule_235_para as
SELECT p.AD_Val_Rule_ID,
       p.ad_reference_id,
       r.name AS RefName,
       p.columnname,
       p.ad_process_id,
       p.name AS ProcessName,
       pr.value AS ProcessValue
FROM ad_process_para p
         JOIN ad_reference r ON p.ad_reference_id = r.ad_reference_id
join ad_process pr on p.ad_process_id = pr.ad_process_id
WHERE p.AD_Element_ID = 1005
   OR columnname ILIKE 'C_Activity_ID';
   
   

SELECT backup_table('ad_process_para');


UPDATE ad_process_para
SET AD_Val_Rule_ID=235
from migration_data.C_Activity_id_ValidationRule_235_para m
WHERE m.ad_process_para_id = ad_process_para.ad_process_para_id
  AND ad_process_para.AD_Val_Rule_ID IS NULL;
  
  
