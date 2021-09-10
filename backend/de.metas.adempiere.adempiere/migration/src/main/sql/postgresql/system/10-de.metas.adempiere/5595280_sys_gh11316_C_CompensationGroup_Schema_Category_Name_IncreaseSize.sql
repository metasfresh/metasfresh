-- 2021-06-28T08:39:04.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=150,Updated=TO_TIMESTAMP('2021-06-28 11:39:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574465
;

-- 2021-06-28T08:39:06.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_compensationgroup_schema_category','Name','VARCHAR(150)',null,null)
;

