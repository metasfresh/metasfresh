
-- SysConfig Name: de.metas.ui.web.material.cockpit.DetailsRowAggregation
-- SysConfig Value: PLANT
-- 2024-01-08T15:00:33.829Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541686,'S',TO_TIMESTAMP('2024-01-08 17:00:33.662','YYYY-MM-DD HH24:MI:SS.US'),100,'Possible values: "PLANT" = detail-rows are aggregated on the plant level; "WAREHOUSE" = details-rows are aggregated on the warehouse level ; "NONE" = no detail rows for either the plant or the warehouse level.','D','Y','de.metas.ui.web.material.cockpit.DetailsRowAggregation',TO_TIMESTAMP('2024-01-08 17:00:33.662','YYYY-MM-DD HH24:MI:SS.US'),100,'PLANT')
;

-- SysConfig Name: de.metas.ui.web.material.cockpit.DetailsRowAggregation
-- SysConfig Value: PLANT
-- SysConfig Value (old): WAREHOUSE
-- 2024-01-10T15:06:26.685Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='PLANT',Updated=TO_TIMESTAMP('2024-01-10 17:06:26.684','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541686
;

-- SysConfig Name: de.metas.ui.web.material.cockpit.DetailsRowAggregation
-- SysConfig Value: PLANT
-- 2024-01-10T15:11:47.340Z
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2024-01-10 17:11:47.339','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541686
;

