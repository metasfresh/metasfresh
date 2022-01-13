-- 2021-05-06T07:20:13.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig
SET Description='This config is only relevant if ''..ProductLookupDescriptor.AvailabilityInfo.QueryEnabled'' is set to Y.
A comma-separated list that may include M_AttributeValue_IDs and/or either of the keywords <OTHER_STORAGE_ATTRIBUTES_KEYS> and <ALL_STORAGE_ATTRIBUTES_KEYS>.
Can be used to specify that when the product lookup descriptor retrieves the available quantity per product, it shall actually retrieve n results per product.
Those n results shall be grouped by the n items specified in this comma-separated list.', Name='de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.AvailabilityInfo.AttributesKeys', Updated=TO_TIMESTAMP('2021-05-06 10:20:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_SysConfig_ID = 541176
;

-- 2021-05-06T07:20:50.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig
SET Description='This config is only relevant if ''..ProductLookupDescriptor.AvailabilityInfo.QueryEnabled'' is set to Y.
It decides whether only positive stock values shall be displayed, or also zero or negative values.', Name='de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.AvailabilityInfo.DisplayOnlyPositive', Updated=TO_TIMESTAMP('2021-05-06 10:20:50', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_SysConfig_ID = 541201
;

-- 2021-05-06T07:23:48.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig
SET Description='If set to ATP, then product lookups will look to the available-to-promise quantity display the results within their respective labels.
If set to AFS, then product lookups will look to the available-for-sale quantity display the results within their respective labels.', Name='de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.AvailabilityInfo.QueryEnabled', Updated=TO_TIMESTAMP('2021-05-06 10:23:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_SysConfig_ID = 541177
;

-- 2021-05-06T07:40:47.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig
SET Name='de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.AvailabilityInfo.QueryType', Updated=TO_TIMESTAMP('2021-05-06 10:40:47', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_SysConfig_ID = 541177
;

-- 2021-05-06T13:02:44.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig
SET Value= CASE Value
               WHEN 'Y' THEN 'ATP'
                        ELSE 'NONE'
           END,
    Updated=TO_TIMESTAMP('2021-05-06 13:02:44', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_SysConfig_ID = 541177
;
