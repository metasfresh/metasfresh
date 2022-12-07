-- 2021-09-19T13:12:04.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=549642
;

-- 2021-09-19T13:26:14.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM DLM_Partition_Config_Reference WHERE DLM_Partition_Config_Reference_ID=1000151
;

-- 2021-09-19T13:30:55.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=548351
;

-- 2021-09-19T13:30:55.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=548351
;

-- 2021-09-19T13:30:55.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=548351
;

-- 2021-09-19T13:30:55.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=546411
;

-- 2021-09-19T13:30:55.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=546411
;

-- 2021-09-19T13:30:55.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=546411
;

-- 2021-09-19T13:30:55.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE C_OrderLine DROP COLUMN IF EXISTS Link_OrderLine_ID')
;






-- manually added on 22.11.2021

delete from ad_field where ad_column_id=55323;








-- 2021-09-19T13:30:56.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column_Trl WHERE AD_Column_ID=55323
;


--custom ad_fields in legacy DBs - safe to delete because we now have an m:n join table and an AD_Relation_Type for this
delete from  ad_field where AD_Column_ID=55323;


-- 2021-09-19T13:30:56.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=55323
;