-- 2021-11-24T07:41:58.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM C_LicenseFeeSettingsLine WHERE C_LicenseFeeSettings_ID=@C_LicenseFeeSettings_ID@',Updated=TO_TIMESTAMP('2021-11-24 09:41:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578579
;

-- 2021-11-24T07:42:01.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_licensefeesettingsline','SeqNo','NUMERIC(10)',null,null)
;

