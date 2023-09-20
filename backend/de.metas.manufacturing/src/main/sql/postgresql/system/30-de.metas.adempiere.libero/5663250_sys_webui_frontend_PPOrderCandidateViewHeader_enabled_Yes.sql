-- 2022-11-04T12:50:24.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541574,'S',TO_TIMESTAMP('2022-11-04 14:50:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','webui.frontend.PPOrderCandidateViewHeader.enabled',TO_TIMESTAMP('2022-11-04 14:50:24','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2022-11-04T13:13:51.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='N',Updated=TO_TIMESTAMP('2022-11-04 15:13:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541574
;

-- 2022-11-04T13:14:12.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='Y',Updated=TO_TIMESTAMP('2022-11-04 15:14:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541574
;

