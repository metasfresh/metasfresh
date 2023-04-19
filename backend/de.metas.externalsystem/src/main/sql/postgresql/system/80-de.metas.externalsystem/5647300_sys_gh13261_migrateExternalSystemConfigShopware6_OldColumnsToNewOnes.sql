UPDATE ExternalSystem_Config_Shopware6
SET percentageofavailableforsalestosync = (SELECT percentage
                                           FROM ExternalSystem_Config_Shopware6_Temp ext
                                           WHERE ext.externalsystem_config_id =
                                                 ExternalSystem_Config_Shopware6.externalsystem_config_shopware6_id)
;

UPDATE ExternalSystem_Config_Shopware6
SET issyncavailableforsalestoshopware6 = (SELECT isSync
                                          FROM ExternalSystem_Config_Shopware6_Temp ext
                                          WHERE ext.externalsystem_config_id =
                                                ExternalSystem_Config_Shopware6.externalsystem_config_shopware6_id)
;

DROP TABLE ExternalSystem_Config_Shopware6_Temp
;