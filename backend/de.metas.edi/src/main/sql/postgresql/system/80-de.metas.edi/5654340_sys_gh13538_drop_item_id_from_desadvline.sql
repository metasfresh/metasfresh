-- 2022-09-02T06:14:17.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE EDI_DesadvLine DROP COLUMN IF EXISTS EDI_Desadv_Pack_Item_ID')
;

-- 2022-09-02T06:14:18.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583583
;

-- 2022-09-02T06:14:18.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=583583
;

