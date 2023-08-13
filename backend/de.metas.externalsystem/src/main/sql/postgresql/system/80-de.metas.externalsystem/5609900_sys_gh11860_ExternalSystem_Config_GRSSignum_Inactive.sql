-- 2021-10-18T09:40:00.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ExternalSystem_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Config_ID,IsActive,Name,Type,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-10-18 12:40:00','YYYY-MM-DD HH24:MI:SS'),100,540006,'Y','GRSSignum','GRS',TO_TIMESTAMP('2021-10-18 12:40:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-18T09:40:18.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ExternalSystem_Config_GRSSignum (AD_Client_ID,AD_Org_ID,BaseURL,CamelHttpResourceAuthKey,Created,CreatedBy,ExternalSystem_Config_GRSSignum_ID,ExternalSystem_Config_ID,ExternalSystemValue,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,'Base-URL',NULL,TO_TIMESTAMP('2021-10-18 12:40:18','YYYY-MM-DD HH24:MI:SS'),100,540000,540006,'GRSSignum','Y',TO_TIMESTAMP('2021-10-18 12:40:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-18T09:40:19.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ExternalSystem_Config SET IsActive='N',Updated=TO_TIMESTAMP('2021-10-18 12:40:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_ID=540006
;

