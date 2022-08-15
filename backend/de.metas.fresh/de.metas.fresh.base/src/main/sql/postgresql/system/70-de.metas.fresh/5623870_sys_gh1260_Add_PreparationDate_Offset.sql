
UPDATE AD_SysConfig
SET Name='de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate_Offset_Hours',
    Description='This configuration allows to define the offset (IN HOURS) for the calculation of the initial preparation date, only when not deliverydays/ tour are found, so the preparation date will be equal to the promised date plus the offset (IN HOURS). Default value in metasfresh: 0 Hours'
WHERE Name = 'de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate_Offset'
;

