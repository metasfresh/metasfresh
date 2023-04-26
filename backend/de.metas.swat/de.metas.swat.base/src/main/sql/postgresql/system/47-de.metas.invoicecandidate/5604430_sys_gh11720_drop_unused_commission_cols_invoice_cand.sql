

-- 2021-09-14T08:33:23.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE C_Invoice_Candidate DROP COLUMN IF EXISTS Traded_Commission_Percent')
;

-- 2021-09-14T08:33:24.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=569827
;

-- 2021-09-14T08:33:24.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=569827
;

-- 2021-09-14T08:34:11.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE C_Invoice_Candidate DROP COLUMN IF EXISTS Base_Commission_Points_Per_Price_UOM')
;

-- 2021-09-14T08:34:12.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=569828
;

-- 2021-09-14T08:34:12.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=569828
;

