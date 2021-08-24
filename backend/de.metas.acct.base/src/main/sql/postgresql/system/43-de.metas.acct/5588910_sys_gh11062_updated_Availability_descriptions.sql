-- 2021-05-18T13:48:33.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig
SET Description='This config is only relevant if ''..ProductLookupDescriptor.AvailabilityInfo.QueryType'' is set to AFS or ATP. A comma-separated list that may include M_AttributeValue_IDs and/or either of the keywords <OTHER_STORAGE_ATTRIBUTES_KEYS> and <ALL_STORAGE_ATTRIBUTES_KEYS>. Can be used to specify that when the product lookup descriptor retrieves the available quantity per product, it shall actually retrieve n results per product. Those n results shall be grouped by the n items specified in this comma-separated list.',
    Updated=TO_TIMESTAMP('2021-05-18 16:48:33', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_SysConfig_ID = 541176
;

-- 2021-05-18T13:48:41.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig
SET Description='This config is only relevant if ''..ProductLookupDescriptor.AvailabilityInfo.QueryType'' is set to AFS or ATP. It decides whether only positive stock values shall be displayed, or also zero or negative values.', Updated=TO_TIMESTAMP('2021-05-18 16:48:41', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_SysConfig_ID = 541201
;

-- 2021-05-18T13:48:44.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig
SET Description='If set to ATP, then product lookups will look to the available-to-promise quantity display the results within their respective labels. If set to AFS, then product lookups will look to the available-for-sale quantity display the results within their respective labels.', Updated=TO_TIMESTAMP('2021-05-18 16:48:44', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_SysConfig_ID = 541177
;

-- 2021-05-18T13:49:09.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig
SET Description='If set to ATP, then product lookups will look to the available-to-promise quantity display the results within their respective labels. If set to AFS, then product lookups will look to the available-for-sale quantity display the results within their respective labels. If set to NONE, no availability info is displayed.',
    Updated=TO_TIMESTAMP('2021-05-18 16:49:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_SysConfig_ID = 541177
;

