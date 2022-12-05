CREATE TABLE ExternalSystem_Config_Shopware6_Temp
(
    externalsystem_config_id NUMERIC(10),
    percentage               NUMERIC,
    isSync                   char
)
;

INSERT INTO ExternalSystem_Config_Shopware6_Temp (externalsystem_config_id, percentage, isSync)
SELECT externalsystem_config_shopware6_id, PercentageOfAvailableStockToSync, IsSyncStockToShopware6
FROM ExternalSystem_Config_Shopware6
;