-- 2021-01-27T21:34:49.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=628645
;

-- 2021-01-27T21:34:49.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=628645
;

-- 2021-01-27T21:34:49.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=628645
;

-- 2021-01-27T21:34:49.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Project_Repair_CostCollector','ALTER TABLE C_Project_Repair_CostCollector DROP COLUMN IF EXISTS IsOwnedByCustomer')
;

-- 2021-01-27T21:34:50.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572536
;

-- 2021-01-27T21:34:50.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=572536
;

