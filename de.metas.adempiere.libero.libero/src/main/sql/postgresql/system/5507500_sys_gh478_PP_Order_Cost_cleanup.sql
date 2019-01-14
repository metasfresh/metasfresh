-- 2018-12-05T11:26:11.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53533
;

-- 2018-12-05T11:26:11.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53533
;

-- 2018-12-05T11:26:57.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53535
;

-- 2018-12-05T11:26:57.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53535
;

alter table PP_Order_Cost drop column if exists AD_Workflow_ID;
alter table PP_Order_Cost drop column if exists CostingMethod;

