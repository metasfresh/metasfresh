-- 2022-01-27T13:56:37.149Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541446,'S',TO_TIMESTAMP('2022-01-27 14:56:35','YYYY-MM-DD HH24:MI:SS'),100,'This configuration allows to define the offset for the calculation of the initial preparation date, , only when not deliverydays/ tour are found, so the preparation date will be equal to the promised date plus the offset. Default value in metasfresh: 0','D','Y','de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate_Offset',TO_TIMESTAMP('2022-01-27 14:56:35','YYYY-MM-DD HH24:MI:SS'),100,'0')
;
