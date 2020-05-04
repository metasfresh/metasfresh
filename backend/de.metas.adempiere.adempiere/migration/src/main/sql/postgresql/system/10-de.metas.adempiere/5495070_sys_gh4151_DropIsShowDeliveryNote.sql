-- 2018-06-04T16:11:50.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=544730
;

-- 2018-06-04T16:11:50.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=544730
;

-- 2018-06-04T16:12:18.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=541211
;

-- 2018-06-04T16:13:04.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2018-06-04 16:13:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564454
;

-- 2018-06-04T16:13:13.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=564201
;

-- 2018-06-04T16:13:13.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=564201
;

/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner DROP COLUMN IsShowDeliveryNote')
;

-- 2018-06-04T16:16:08.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=560151
;

-- 2018-06-04T16:16:08.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=560151
;

