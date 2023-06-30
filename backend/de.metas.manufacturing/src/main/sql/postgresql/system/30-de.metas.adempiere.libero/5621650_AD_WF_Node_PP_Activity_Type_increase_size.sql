-- 2022-01-16T01:56:27.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=40,Updated=TO_TIMESTAMP('2022-01-16 03:56:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578131
;

-- 2022-01-16T01:56:29.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_wf_node','PP_Activity_Type','VARCHAR(40)',null,null)
;

