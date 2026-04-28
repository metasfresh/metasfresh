-- 2021-11-12T20:42:23.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step','ALTER TABLE M_Picking_Job_Step DROP COLUMN IF EXISTS QtyPicked')
;

-- 2021-11-12T20:42:24.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=577764
;

-- 2021-11-12T20:42:24.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=577764
;

-- 2021-11-12T20:42:49.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step','ALTER TABLE M_Picking_Job_Step DROP COLUMN IF EXISTS M_Picking_Candidate_ID')
;

-- 2021-11-12T20:42:49.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=577774
;

-- 2021-11-12T20:42:49.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=577774
;

-- 2021-11-12T20:43:10.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step_HUAlternative','ALTER TABLE M_Picking_Job_Step_HUAlternative DROP COLUMN IF EXISTS QtyPicked')
;

-- 2021-11-12T20:43:10.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=578373
;

-- 2021-11-12T20:43:10.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=578373
;

-- 2021-11-12T20:43:23.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step_HUAlternative','ALTER TABLE M_Picking_Job_Step_HUAlternative DROP COLUMN IF EXISTS M_Picking_Candidate_ID')
;

-- 2021-11-12T20:43:23.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=578368
;

-- 2021-11-12T20:43:23.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=578368
;

