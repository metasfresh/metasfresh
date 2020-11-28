
DELETE FROM ad_ref_table WHERE AD_Table_ID=541460
;

-- 2020-03-31T11:49:44.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=541460
;

-- 2020-03-31T11:49:44.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=541460
;

DROP TABLE IF EXISTS S_BUDGET_ISSUE
;

ALTER TABLE s_externalissuedetail drop column if exists s_effort_issue_id
;

ALTER TABLE s_externalissuedetail drop column if exists s_budget_issue_id
;
