-- Run mode: SWING_CLIENT

-- SysConfig Name: de.metas.ui.web.material.cockpit.DetailsRowAggregation
-- SysConfig Value: PLANT
-- 2024-01-15T09:31:57.497Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Possible values: "PLANT" = detail-rows are aggregated on the plant level; "WAREHOUSE" = details-rows are aggregated on the warehouse level ; "NONE" = no detail rows for either the plant or the warehouse level.
The default value is considered "PLANT" therefore, if a different value is set, detail-rows are aggregated on the plant level.',Updated=TO_TIMESTAMP('2024-01-15 11:31:57.485','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541686
;

