-- 2021-11-04T07:28:36.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Async_Batch_Milestone','ALTER TABLE C_Async_Batch_Milestone DROP COLUMN IF EXISTS AD_Client_ID')
;

-- 2021-11-04T07:28:36.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575221
;

-- 2021-11-04T07:28:36.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=575221
;

-- 2021-11-04T07:28:45.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Async_Batch_Milestone','ALTER TABLE C_Async_Batch_Milestone DROP COLUMN IF EXISTS AD_Org_ID')
;

-- 2021-11-04T07:28:45.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575222
;

-- 2021-11-04T07:28:45.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=575222
;

-- 2021-11-04T07:28:55.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Async_Batch_Milestone','ALTER TABLE C_Async_Batch_Milestone DROP COLUMN IF EXISTS C_Async_Batch_ID')
;

-- 2021-11-04T07:28:55.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575230
;

-- 2021-11-04T07:28:55.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=575230
;

-- 2021-11-04T07:29:01.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Async_Batch_Milestone','ALTER TABLE C_Async_Batch_Milestone DROP COLUMN IF EXISTS C_Async_Batch_Milestone_ID')
;

-- 2021-11-04T07:29:01.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575228
;

-- 2021-11-04T07:29:01.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=575228
;

-- 2021-11-04T07:29:10.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Async_Batch_Milestone','ALTER TABLE C_Async_Batch_Milestone DROP COLUMN IF EXISTS Created')
;

-- 2021-11-04T07:29:10.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575223
;

-- 2021-11-04T07:29:10.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=575223
;

-- 2021-11-04T07:29:20.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Async_Batch_Milestone','ALTER TABLE C_Async_Batch_Milestone DROP COLUMN IF EXISTS CreatedBy')
;

-- 2021-11-04T07:29:20.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575224
;

-- 2021-11-04T07:29:20.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=575224
;

-- 2021-11-04T07:29:31.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Async_Batch_Milestone','ALTER TABLE C_Async_Batch_Milestone DROP COLUMN IF EXISTS IsActive')
;

-- 2021-11-04T07:29:31.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575225
;

-- 2021-11-04T07:29:31.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=575225
;

-- 2021-11-04T07:29:45.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Async_Batch_Milestone','ALTER TABLE C_Async_Batch_Milestone DROP COLUMN IF EXISTS Name')
;

-- 2021-11-04T07:29:45.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575231
;

-- 2021-11-04T07:29:45.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=575231
;

-- 2021-11-04T07:29:55.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Async_Batch_Milestone','ALTER TABLE C_Async_Batch_Milestone DROP COLUMN IF EXISTS Processed')
;

-- 2021-11-04T07:29:55.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575229
;

-- 2021-11-04T07:29:55.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=575229
;

-- 2021-11-04T07:30:05.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Async_Batch_Milestone','ALTER TABLE C_Async_Batch_Milestone DROP COLUMN IF EXISTS Updated')
;

-- 2021-11-04T07:30:05.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575226
;

-- 2021-11-04T07:30:05.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=575226
;

-- 2021-11-04T07:30:14.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Async_Batch_Milestone','ALTER TABLE C_Async_Batch_Milestone DROP COLUMN IF EXISTS UpdatedBy')
;

-- 2021-11-04T07:30:14.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575227
;

-- 2021-11-04T07:30:14.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=575227
;

-- 2021-11-04T07:31:09.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541374
;

-- 2021-11-04T07:31:09.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=541374
;

-- drop table
DROP TABLE IF EXISTS C_Async_Batch_Milestone
;

