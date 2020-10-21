-- 2018-12-12T11:05:53.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=56561
;

-- 2018-12-12T11:05:53.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=56561
;

-- 2018-12-12T11:05:53.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=610406
;

-- 2018-12-12T11:06:52.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53987
;

-- 2018-12-12T11:06:52.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53987
;

-- 2018-12-12T11:06:52.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=608195
;

delete from dlm_partition_config_reference where DLM_Referencing_Column_ID=53832;

-- 2018-12-12T11:10:41.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53832
;

-- 2018-12-12T11:10:41.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53832
;

select * from db_alter_table('PP_Cost_Collector','alter table PP_Cost_Collector drop PP_Order_Workflow_ID');

