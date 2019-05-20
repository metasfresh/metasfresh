-- 2019-03-29T16:08:16.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsPharmaCustomerNarcoticsPermission CHAR(1) DEFAULT ''N'' CHECK (IsPharmaCustomerNarcoticsPermission IN (''Y'',''N'')) NOT NULL')
;

-- 2019-03-29T16:08:39.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsPharmaVendorNarcoticsPermission CHAR(1) DEFAULT ''N'' CHECK (IsPharmaVendorNarcoticsPermission IN (''Y'',''N'')) NOT NULL')
;

