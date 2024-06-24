-- 2022-09-26T15:02:55.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-09-26 18:02:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584457
;

-- 2022-09-26T15:02:57.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_wf_node_product','AD_Workflow_ID','NUMERIC(10)',null,null)
;

-- 2022-09-26T15:02:57.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_wf_node_product','AD_Workflow_ID',null,'NOT NULL',null)
;

