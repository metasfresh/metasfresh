CREATE TABLE backup.BKP_ad_column_gh13138_27062022
AS
SELECT * FROM ad_column
;

-- test the backup
SELECT * FROM backup.BKP_ad_column_gh13138_27062022
;

CREATE TABLE backup.BKP_ad_val_rule_gh13138_27062022
AS
SELECT * FROM ad_val_rule
;

-- test the backup
SELECT * FROM backup.BKP_ad_val_rule_gh13138_27062022
;

CREATE TABLE backup.BKP_ad_ref_table_gh13138_27062022
AS
SELECT * FROM ad_ref_table
;

-- test the backup
SELECT * FROM backup.BKP_ad_ref_table_gh13138_27062022
;


-- Set the validation rule to those without it and has no ad_ref_table
UPDATE AD_Column SET AD_Val_Rule_ID=540579,
                     Updated=now(),
                     UpdatedBy=99
WHERE AD_Column_ID in (select distinct x.ad_column_id
                       from (select distinct t.tablename, c.ad_reference_id, ref.name, c.ad_reference_value_id, c.ad_val_rule_id,  cvr.code as columnVR, f.name as field , fvr.code as fieldVR , t.name as tab, c.ad_column_id, f.ad_field_id
                             from ad_column c
                                      inner join ad_field f  on c.ad_column_id = f.ad_column_id
                                      inner join ad_table t on t.ad_table_id = c.ad_table_id
                                      inner join ad_reference ref on ref.ad_reference_id = c.ad_reference_id
                                      left outer join ad_tab tab on t.ad_table_id = tab.ad_table_id
                                      left outer join ad_val_rule cvr on cvr.ad_val_rule_id = c.ad_val_rule_id
                                      left outer join ad_val_rule fvr on fvr.ad_val_rule_id = f.ad_val_rule_id
                             where upper(c.columnname) = upper('c_project_id') and c.ad_reference_value_id is null
                             order by 1) x
                       where x.ad_val_rule_id is null);