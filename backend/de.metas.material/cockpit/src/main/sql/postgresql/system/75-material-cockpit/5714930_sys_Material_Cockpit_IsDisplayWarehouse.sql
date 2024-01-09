
--------------------- MAYBE NEEDED
-- SysConfig Name: de.metas.ui.web.material.cockpit.field.M_Warehouse_ID.IsDisplayed
-- SysConfig Value: N
-- 2024-01-09T16:36:08.581Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541687,'O',TO_TIMESTAMP('2024-01-09 18:36:08.399','YYYY-MM-DD HH24:MI:SS.US'),100,'Can be overridden on client or org-level','U','Y','de.metas.ui.web.material.cockpit.field.M_Warehouse_ID.IsDisplayed',TO_TIMESTAMP('2024-01-09 18:36:08.399','YYYY-MM-DD HH24:MI:SS.US'),100,'N')
;

-- SysConfig Name: de.metas.ui.web.material.cockpit.field.M_Warehouse_ID.IsDisplayed
-- SysConfig Value: N
-- 2024-01-09T16:36:14.890Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2024-01-09 18:36:14.889','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541687
;

------------------------


-- SysConfig Name: de.metas.ui.web.material.cockpit.field.M_Warehouse_ID.IsDisplayed
-- SysConfig Value: Y
-- SysConfig Value (old): N
-- 2024-01-09T17:18:40.166Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Value='Y',Updated=TO_TIMESTAMP('2024-01-09 19:18:40.164','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541687
;

