-- 2022-02-17T08:48:35.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_QRCode','ALTER TABLE public.M_HU_QRCode ADD COLUMN DisplayableQRCode VARCHAR(255) NOT NULL')
;

-- 2022-02-17T08:48:44.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_QRCode','ALTER TABLE public.M_HU_QRCode ADD COLUMN RenderedQRCode TEXT NOT NULL')
;

