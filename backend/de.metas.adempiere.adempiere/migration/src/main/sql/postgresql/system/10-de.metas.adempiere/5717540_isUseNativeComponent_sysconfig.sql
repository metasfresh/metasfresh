-- 2024-02-16T14:33:55.654Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='500',Updated=TO_TIMESTAMP('2024-02-16 16:33:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541708
;

-- 2024-02-16T14:34:38.841Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='100',Updated=TO_TIMESTAMP('2024-02-16 16:34:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541708
;

-- 2024-02-26T10:16:14.696Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541710,'S',TO_TIMESTAMP('2024-02-26 12:16:14','YYYY-MM-DD HH24:MI:SS'),100,'N - use as text input for date field
Y - use browser native input type=date','D','Y','mobileui.frontend.dateInput.isUseNativeComponent',TO_TIMESTAMP('2024-02-26 12:16:14','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

