
-- 2021-12-06T12:16:46.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-12-06 14:16:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578580
;

-- 2021-12-06T12:16:51.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_licensefeesettingsline','C_BP_Group_Match_ID','NUMERIC(10)',null,null)
;

-- 2021-12-06T12:16:51.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_licensefeesettingsline','C_BP_Group_Match_ID',null,'NULL',null)
;

