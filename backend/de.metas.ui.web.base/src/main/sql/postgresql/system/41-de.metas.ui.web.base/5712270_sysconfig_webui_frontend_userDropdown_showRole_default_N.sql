-- 2023-11-30T09:36:46.941Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541662,'S',TO_TIMESTAMP('2023-11-30 11:36:46','YYYY-MM-DD HH24:MI:SS'),100,'If true the role name is displayed in webui on top-right user dropdown.','de.metas.ui.web','Y','webui.frontend.userDropdown.showRole',TO_TIMESTAMP('2023-11-30 11:36:46','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2023-11-30T09:36:52.520Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2023-11-30 11:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541661
;

