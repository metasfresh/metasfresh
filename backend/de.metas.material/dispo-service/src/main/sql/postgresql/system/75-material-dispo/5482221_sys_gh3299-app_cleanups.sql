



--
-- cleanup: drop PP_Product_Planning.IsRequiredDRP
--
delete from ad_ui_element where ad_field_id IN (select ad_field_id from ad_field where ad_column_id=55334);
delete from ad_field where ad_column_id=55334;
SELECT db_alter_table('PP_Product_Planning', 'ALTER TABLE PP_Product_Planning DROP COLUMN IF EXISTS IsRequiredDRP;');

-- 2018-01-15T13:45:48.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=55334
;

-- 2018-01-15T13:45:48.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=55334
;


--
-- cleanup: remove process PP_Product_Planning
--
delete from AD_Menu where AD_Process_ID=53010;

-- 2018-01-15T13:51:06.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53010
;

-- 2018-01-15T13:51:06.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=53010
;
--
-- cleanup: drop PP_Product_Planning.IsRequiredMRP
--
delete from ad_ui_element where ad_field_id IN (select ad_field_id from ad_field where ad_column_id=53387);
delete from ad_field where ad_column_id=53387;
SELECT db_alter_table('PP_Product_Planning', 'ALTER TABLE PP_Product_Planning DROP COLUMN IF EXISTS IsRequiredMRP;');
-- 2018-01-15T13:54:53.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53387
;

-- 2018-01-15T13:54:53.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53387
;

