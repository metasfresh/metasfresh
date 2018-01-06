-- 2017-11-27T15:20:04.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541846
;

-- 2017-11-27T15:20:12.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=555187
;

-- 2017-11-27T15:20:12.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=555187
;

-- 2017-11-27T15:20:43.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- ALTER TABLE M_ReceiptSchedule DROP COLUMN IF EXISTS IsHUPrepared;
select public.db_alter_table('M_ReceiptSchedule','ALTER TABLE M_ReceiptSchedule DROP COLUMN IF EXISTS IsHUPrepared;');

-- 2017-11-27T15:20:45.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=551518
;

-- 2017-11-27T15:20:45.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=551518
;

