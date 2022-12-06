-- 2021-10-15T07:06:33.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541419,'S',TO_TIMESTAMP('2021-10-15 10:06:32','YYYY-MM-DD HH24:MI:SS'),100,'If set to Y then all HTTP calls will bypass the API audit logging.
','D','Y','ApiAuditFilter.bypassAll',TO_TIMESTAMP('2021-10-15 10:06:32','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- -- 2021-10-15T07:06:45.696Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2021-10-15 10:06:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541419
-- ;

-- -- 2021-10-15T07:07:01.799Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_SysConfig SET Value='N',Updated=TO_TIMESTAMP('2021-10-15 10:07:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541419
-- ;

