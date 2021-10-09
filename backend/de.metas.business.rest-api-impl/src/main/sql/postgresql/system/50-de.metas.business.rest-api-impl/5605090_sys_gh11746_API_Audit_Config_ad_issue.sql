-- 2021-09-16T09:48:27.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO API_Audit_Config (AD_Client_ID,AD_Org_ID,API_Audit_Config_ID,Created,CreatedBy,IsActive,IsInvokerWaitsForResult,KeepRequestBodyDays,KeepRequestDays,KeepResponseBodyDays,KeepResponseDays,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,540006,TO_TIMESTAMP('2021-09-16 12:48:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',0,0,0,0,10010,TO_TIMESTAMP('2021-09-16 12:48:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-16T09:48:32.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET SeqNo=10,Updated=TO_TIMESTAMP('2021-09-16 12:48:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE API_Audit_Config_ID=540006
;

-- 2021-09-16T09:48:33.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET Method='POST',Updated=TO_TIMESTAMP('2021-09-16 12:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE API_Audit_Config_ID=540006
;

-- 2021-09-16T09:49:14.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET PathPrefix='externalsystem/',Updated=TO_TIMESTAMP('2021-09-16 12:49:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE API_Audit_Config_ID=540006
;

-- 2021-09-16T09:49:21.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET PathPrefix='externalsystem/**/',Updated=TO_TIMESTAMP('2021-09-16 12:49:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE API_Audit_Config_ID=540006
;

-- 2021-09-16T09:50:06.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET PathPrefix='externalsystem/**/externalstatus/error',Updated=TO_TIMESTAMP('2021-09-16 12:50:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE API_Audit_Config_ID=540006
;

-- 2021-09-16T09:50:33.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET PathPrefix='**/externalsystem/**/externalstatus/error',Updated=TO_TIMESTAMP('2021-09-16 12:50:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE API_Audit_Config_ID=540006
;

-- 2021-09-16T09:51:59.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET NotifyUserInCharge='ALWAYS ',Updated=TO_TIMESTAMP('2021-09-16 12:51:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE API_Audit_Config_ID=540006
;

-- 2021-09-16T09:52:03.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET AD_UserGroup_InCharge_ID=540002,Updated=TO_TIMESTAMP('2021-09-16 12:52:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE API_Audit_Config_ID=540006
;

-- 2021-09-16T09:52:35.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepRequestDays=7,Updated=TO_TIMESTAMP('2021-09-16 12:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE API_Audit_Config_ID=540006
;

-- 2021-09-16T09:52:36.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepRequestBodyDays=7,Updated=TO_TIMESTAMP('2021-09-16 12:52:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE API_Audit_Config_ID=540006
;

-- 2021-09-16T09:52:37.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepResponseDays=7,Updated=TO_TIMESTAMP('2021-09-16 12:52:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE API_Audit_Config_ID=540006
;

-- 2021-09-16T09:52:47.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepResponseBodyDays=7,Updated=TO_TIMESTAMP('2021-09-16 12:52:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE API_Audit_Config_ID=540006
;

