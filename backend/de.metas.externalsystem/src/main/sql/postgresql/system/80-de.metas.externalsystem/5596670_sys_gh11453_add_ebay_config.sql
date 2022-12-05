-- 2021-07-05T15:52:06.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_User (AD_Client_ID,AD_Language,AD_Org_ID,AD_User_ID,Created,CreatedBy,Firstname,Fresh_Gift,IsAccountLocked,IsActive,IsAuthorizedSignatory,IsBillToContact_Default,IsDecider,IsDefaultContact,IsFullBPAccess,IsInPayroll,IsLoginAsHostKey,IsManagement,IsMembershipContact,IsMFProcurementUser,IsMultiplier,IsNewsletter,IsPurchaseContact,IsPurchaseContact_Default,IsSalesContact,IsSalesContact_Default,IsShipToContact_Default,IsSubjectMatterContact,IsSystemUser,Name,NotificationType,Processing,Title,Updated,UpdatedBy,Value) VALUES (1000000,'en_US',1000000,540101,TO_TIMESTAMP('2021-07-05 18:52:06','YYYY-MM-DD HH24:MI:SS'),100,'API-User','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','API-User','N','N','',TO_TIMESTAMP('2021-07-05 18:52:06','YYYY-MM-DD HH24:MI:SS'),100,'1000079')
;

-- 2021-07-05T15:52:10.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_User SET Lastname='Ebay', Name='Ebay, API-User',Updated=TO_TIMESTAMP('2021-07-05 18:52:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540101
;

-- 2021-07-05T15:52:22.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_User SET Value='ebay',Updated=TO_TIMESTAMP('2021-07-05 18:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540101
;

-- 2021-07-05T15:52:29.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_User SET IsSystemUser='Y', Login='Ebay',Updated=TO_TIMESTAMP('2021-07-05 18:52:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540101
;

-- 2021-07-05T15:52:31.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_User_Roles (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_User_ID,AD_User_Roles_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,0,540024,540101,540120,TO_TIMESTAMP('2021-07-05 18:52:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-07-05 18:52:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-05T15:53:02.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_User SET IsActive='N',Updated=TO_TIMESTAMP('2021-07-05 18:53:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540101
;

-- 2021-07-05T16:20:45.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_User SET IsActive='Y',Updated=TO_TIMESTAMP('2021-07-05 19:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540101
;

-- 2021-07-05T16:20:48.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_User SET AD_Language='de_DE',Updated=TO_TIMESTAMP('2021-07-05 19:20:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540101
;

-- 2021-07-05T16:20:50.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_User SET IsActive='N',Updated=TO_TIMESTAMP('2021-07-05 19:20:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540101
;

-- 2021-07-05T16:34:40.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_User SET IsActive='Y',Updated=TO_TIMESTAMP('2021-07-05 19:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540101
;

-- 2021-07-05T16:36:25.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,1000000,584853,540024,550076,TO_TIMESTAMP('2021-07-05 19:36:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',10,'M','Y','N',7,'Call_External_System_Ebay - getOrders','N','P','F','NEW',540101,TO_TIMESTAMP('2021-07-05 19:36:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-05T16:36:45.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,542035,550076,540034,TO_TIMESTAMP('2021-07-05 19:36:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-07-05 19:36:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-05T16:37:05.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler_Para SET ParameterDefault='getOrders',Updated=TO_TIMESTAMP('2021-07-05 19:37:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_Para_ID=540034
;

-- 2021-07-06T05:21:08.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ExternalSystem_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Config_ID,IsActive,Name,Type,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-07-06 08:21:08','YYYY-MM-DD HH24:MI:SS'),100,540004,'Y','Ebay','Ebay',TO_TIMESTAMP('2021-07-06 08:21:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T05:21:33.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ExternalSystem_Config_Ebay (AD_Client_ID,AD_Org_ID,API_Mode,AppId,CertId,Created,CreatedBy,DevId,ExternalSystem_Config_Ebay_ID,ExternalSystem_Config_ID,ExternalSystemValue,IsActive,RedirectURL,Updated,UpdatedBy) VALUES (1000000,1000000,'SANDBOX','AppID','CertID',TO_TIMESTAMP('2021-07-06 08:21:33','YYYY-MM-DD HH24:MI:SS'),100,'DevID',540000,540004,'Ebay','Y','RedirectURL',TO_TIMESTAMP('2021-07-06 08:21:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T06:02:50.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,542036,550076,540037,TO_TIMESTAMP('2021-07-06 09:02:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-07-06 09:02:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T06:02:58.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler_Para SET ParameterDefault='540000',Updated=TO_TIMESTAMP('2021-07-06 09:02:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_Para_ID=540037
;

-- 2021-07-06T06:03:03.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_User SET IsActive='N',Updated=TO_TIMESTAMP('2021-07-06 09:03:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540101
;

