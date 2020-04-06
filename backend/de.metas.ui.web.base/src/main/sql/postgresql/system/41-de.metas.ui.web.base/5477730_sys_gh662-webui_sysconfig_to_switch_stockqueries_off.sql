-- 2017-11-21T09:07:49.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541177,'O',TO_TIMESTAMP('2017-11-21 09:07:49','YYYY-MM-DD HH24:MI:SS'),100,'If set to Y, then product lookups will look to the (projected) on-hand quantity as well and display the results','de.metas.material.dispo','Y','de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.QueryAvailableStorage',TO_TIMESTAMP('2017-11-21 09:07:49','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2017-11-21T09:08:28.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Only used if activated via ''..ProductLookupDescriptor.QueryAvailableStorage''
A comma-separated list that may include M_AttributeValue_IDs and/or either of the keywords <OTHER_STORAGE_ATTRIBUTES_KEYS> and <ALL_STORAGE_ATTRIBUTES_KEYS>.
Can be used to specify that when the product lookup descriptor retrieves the available quantity per product, it shall actually retrieve n results per product.
Those n results shall be grouped by the n items specified in this comma-separated list.',Updated=TO_TIMESTAMP('2017-11-21 09:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541176
;

-- 2017-11-21T09:11:47.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.QueryAvailableStock',Updated=TO_TIMESTAMP('2017-11-21 09:11:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541177
;

-- 2017-11-21T09:23:36.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.QueryStockAttributesKeys',Updated=TO_TIMESTAMP('2017-11-21 09:23:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541176
;

UPDATE AD_SysConfig 
SET Name='de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.QueryStockAttributesKeys' 
WHERE Name='de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.StorageAttributesKeys';