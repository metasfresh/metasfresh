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


-- Set the validation rule to those without it and has no ad_val_rule_id nor ad_reference_value_id
UPDATE AD_Column SET ad_val_rule_id=540579,
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
                             where upper(c.columnname) = upper('c_project_id')
                               and c.ad_reference_value_id is null        -- No ad_reference defined
                               and c.ad_val_rule_id is NULL               -- No validation rule defined
                             order by 1) x
                       )
;


-- Update manually the existed ad_val_rule_id for columns that has no ad_reference
-- 2022-06-27T19:47:57.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Project.AD_Org_ID IN (@AD_Org_ID/-1@, 0) AND C_Project.IsSummary=''N'' AND (C_Project.C_BPartner_ID IS NULL OR C_Project.C_BPartner_ID=@C_BPartner_ID@ OR C_Project.C_BPartnerSR_ID=@C_BPartner_ID@)',Updated=TO_TIMESTAMP('2022-06-27 20:47:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=227
;

-- 2022-06-27T19:50:19.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Project.AD_Org_ID IN (@AD_Org_ID/-1@, 0) AND C_Project.IsSummary=''N'' AND C_Project.IsActive=''Y'' AND C_Project.Processed=''N''',Updated=TO_TIMESTAMP('2022-06-27 20:50:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=232
;


-- Update manually the existed ad_reference that reference the project table

-- 2022-06-27T19:58:05.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Project.AD_Org_ID IN (@AD_Org_ID/-1@, 0) AND C_Project.IsSummary=''N''',Updated=TO_TIMESTAMP('2022-06-27 20:58:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=141
;

-- 2022-06-27T19:59:00.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Project.AD_Org_ID IN (@AD_Org_ID/-1@, 0) AND C_Project.IsSummary=''Y''',Updated=TO_TIMESTAMP('2022-06-27 20:59:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=169
;

-- 2022-06-27T20:00:58.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Project.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2022-06-27 21:00:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541242
;

-- 2022-06-27T20:02:13.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Project.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2022-06-27 21:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541136
;

