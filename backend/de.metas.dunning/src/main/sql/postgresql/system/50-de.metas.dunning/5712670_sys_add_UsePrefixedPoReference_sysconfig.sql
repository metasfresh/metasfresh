-- 2023-12-06T08:34:49.309Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541665,'S',TO_TIMESTAMP('2023-12-06 10:34:49','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','de.metas.dunning.UsePrefixedPoReference',TO_TIMESTAMP('2023-12-06 10:34:49','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2023-12-06T08:34:59.526Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2023-12-06 10:34:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541665
;

-- 2023-12-06T08:36:11.811Z
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_NotificationGroup_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Name,Updated,UpdatedBy) VALUES (0,540021,0,TO_TIMESTAMP('2023-12-06 10:36:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dunning','de.metas.MassDunning.OrgBPUserNotifications','Y','Mass Dunning',TO_TIMESTAMP('2023-12-06 10:36:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-06T08:36:32.530Z
UPDATE AD_NotificationGroup SET Description='notification sent to org BPs when mass dunnings are created',Updated=TO_TIMESTAMP('2023-12-06 10:36:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_NotificationGroup_ID=540021
;

