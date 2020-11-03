-- 2018-03-22T20:55:20.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If set to Y, then product lookups will look to the available-to-promise quantity display the results within their respective labels.', Name='de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.QueryATP',Updated=TO_TIMESTAMP('2018-03-22 20:55:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541177
;

-- 2018-03-22T20:56:09.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ATP.QueryEnabled',Updated=TO_TIMESTAMP('2018-03-22 20:56:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541177
;

-- 2018-03-22T20:57:27.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Only used if activated via ''..ProductLookupDescriptor.ATP.QueryEnabled''.
A comma-separated list that may include M_AttributeValue_IDs and/or either of the keywords <OTHER_STORAGE_ATTRIBUTES_KEYS> and <ALL_STORAGE_ATTRIBUTES_KEYS>.
Can be used to specify that when the product lookup descriptor retrieves the available quantity per product, it shall actually retrieve n results per product.
Those n results shall be grouped by the n items specified in this comma-separated list.', Name='de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ATP.AttributesKeys',Updated=TO_TIMESTAMP('2018-03-22 20:57:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541176
;

-- 2018-03-22T21:01:38.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541201,'O',TO_TIMESTAMP('2018-03-22 21:01:38','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.material.dispo','Y','de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ATP.DisplayOnlyPositive',TO_TIMESTAMP('2018-03-22 21:01:38','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2018-03-22T21:01:54.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This config is only relevant if activated via ''..ProductLookupDescriptor.ATP.QueryEnabled''.
A comma-separated list that may include M_AttributeValue_IDs and/or either of the keywords <OTHER_STORAGE_ATTRIBUTES_KEYS> and <ALL_STORAGE_ATTRIBUTES_KEYS>.
Can be used to specify that when the product lookup descriptor retrieves the available quantity per product, it shall actually retrieve n results per product.
Those n results shall be grouped by the n items specified in this comma-separated list.',Updated=TO_TIMESTAMP('2018-03-22 21:01:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541176
;

-- 2018-03-22T21:02:08.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This config is only relevant if ''..ProductLookupDescriptor.ATP.QueryEnabled'' is set to Y.
A comma-separated list that may include M_AttributeValue_IDs and/or either of the keywords <OTHER_STORAGE_ATTRIBUTES_KEYS> and <ALL_STORAGE_ATTRIBUTES_KEYS>.
Can be used to specify that when the product lookup descriptor retrieves the available quantity per product, it shall actually retrieve n results per product.
Those n results shall be grouped by the n items specified in this comma-separated list.',Updated=TO_TIMESTAMP('2018-03-22 21:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541176
;

-- 2018-03-22T21:03:01.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This config is only relevant if ''..ProductLookupDescriptor.ATP.QueryEnabled'' is set to Y.
It decides whether only positive ATP values shall be displayed, or also zero or negative values.',Updated=TO_TIMESTAMP('2018-03-22 21:03:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541201
;

