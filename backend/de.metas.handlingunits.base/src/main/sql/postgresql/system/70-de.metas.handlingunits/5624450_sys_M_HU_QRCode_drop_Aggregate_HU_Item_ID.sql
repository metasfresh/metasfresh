-- 2022-02-04T13:56:53.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_QRCode','ALTER TABLE M_HU_QRCode DROP COLUMN IF EXISTS Aggregate_HU_Item_ID')
;

-- 2022-02-04T13:56:54.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=579141
;

-- 2022-02-04T13:56:54.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=579141
;

