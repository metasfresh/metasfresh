-- 2020-06-05T10:59:01.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=613913
;

-- 2020-06-05T10:59:01.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=613913
;

-- 2020-06-05T10:59:01.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=613913
;

-- 2020-06-05T10:59:01.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_AllocationLine','ALTER TABLE C_AllocationLine DROP COLUMN IF EXISTS BankFeeAmt')
;

-- 2020-06-05T10:59:02.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=570831
;

-- 2020-06-05T10:59:02.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=570831
;

