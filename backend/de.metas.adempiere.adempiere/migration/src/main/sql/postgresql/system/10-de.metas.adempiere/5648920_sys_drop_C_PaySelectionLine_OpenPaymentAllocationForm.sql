-- 2022-08-02T08:33:08.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556205
;

-- 2022-08-02T08:33:08.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=556205
;

-- 2022-08-02T08:33:08.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=556205
;

-- 2022-08-02T08:33:08.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PaySelectionLine','ALTER TABLE C_PaySelectionLine DROP COLUMN IF EXISTS OpenPaymentAllocationForm')
;

-- 2022-08-02T08:33:08.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=552566
;

-- 2022-08-02T08:33:08.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=552566
;

-- 2022-08-02T08:33:17.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=542859
;

-- 2022-08-02T08:33:17.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=542859
;

