-- C_DocType.EntityType
update AD_Column set EntityType='D' where AD_Column_ID=547289 and EntityType<>'D';

-- 2017-10-02T16:31:16.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('c_rfq','ALTER TABLE public.C_RfQ ADD COLUMN DocAction CHAR(2) DEFAULT ''CO'' NOT NULL')
;

