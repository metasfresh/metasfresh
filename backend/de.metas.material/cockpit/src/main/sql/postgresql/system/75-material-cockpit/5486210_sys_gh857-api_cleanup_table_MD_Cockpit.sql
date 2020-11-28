-- 2018-02-21T13:21:46.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=558319
;

-- 2018-02-21T13:21:46.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=558319
;

SELECT db_alter_table('MD_Cockpit','ALTER TABLE MD_Cockpit DROP COLUMN IF EXISTS M_Product_Category_ID;');
