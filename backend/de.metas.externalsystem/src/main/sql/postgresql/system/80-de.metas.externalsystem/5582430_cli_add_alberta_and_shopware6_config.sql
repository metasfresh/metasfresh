------
--Shopware6 (inactive)

-- 2021-03-16T18:47:24.170Z
-- URL zum Konzept
INSERT INTO ExternalSystem_Config (AD_Client_ID,AD_Org_ID,CamelURL,Created,CreatedBy,ExternalSystem_Config_ID,IsActive,Name,Type,Updated,UpdatedBy) VALUES (1000000,1000000,'http://172.17.0.1:54804/camel/do',TO_TIMESTAMP('2021-03-16 19:47:24','YYYY-MM-DD HH24:MI:SS'),100,540001,'N','Shopware6','S6',TO_TIMESTAMP('2021-03-16 19:47:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T19:30:17.180Z
-- URL zum Konzept
INSERT INTO ExternalSystem_Config_Shopware6 (AD_Client_ID,AD_Org_ID,BaseURL,Client_Id,Client_Secret,Created,CreatedBy,ExternalSystem_Config_ID,ExternalSystem_Config_Shopware6_ID,ExternalSystemValue,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,'Base-URL','Zugrangs-ID','Sicherheitsschlüssel',TO_TIMESTAMP('2021-03-16 20:30:17','YYYY-MM-DD HH24:MI:SS'),100,540001,540000,'Shopware6-Konfig','Y',TO_TIMESTAMP('2021-03-16 20:30:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T19:30:19.988Z
-- URL zum Konzept
UPDATE ExternalSystem_Config_Shopware6 SET IsActive='N',Updated=TO_TIMESTAMP('2021-03-16 20:30:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_Shopware6_ID=540000
;

-- API-User (inactive, without auth-token)
-- 2021-03-16T19:48:53.950Z
-- URL zum Konzept
INSERT INTO AD_User (AD_Client_ID,AD_Language,AD_Org_ID,AD_User_ID,Created,CreatedBy,Firstname,Fresh_Gift,IsAccountLocked,IsActive,IsAuthorizedSignatory,IsBillToContact_Default,IsDecider,IsDefaultContact,IsFullBPAccess,IsInPayroll,IsLoginAsHostKey,IsManagement,IsMFProcurementUser,IsMultiplier,IsNewsletter,IsPurchaseContact,IsPurchaseContact_Default,IsSalesContact,IsSalesContact_Default,IsShipToContact_Default,IsSubjectMatterContact,IsSystemUser,Name,NotificationType,Processing,Title,Updated,UpdatedBy,Value) VALUES (1000000,'de_DE',1000000,540091,TO_TIMESTAMP('2021-03-16 20:48:53','YYYY-MM-DD HH24:MI:SS'),100,'Shopware6','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Shopware6','N','N','',TO_TIMESTAMP('2021-03-16 20:48:53','YYYY-MM-DD HH24:MI:SS'),100,'1000079')
;

-- 2021-03-16T19:48:58.489Z
-- URL zum Konzept
UPDATE AD_User SET Lastname='API-Uuser', Name='API-Uuser, Shopware6',Updated=TO_TIMESTAMP('2021-03-16 20:48:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540091
;

-- 2021-03-16T19:49:04.878Z
-- URL zum Konzept
UPDATE AD_User SET IsSystemUser='Y', Login='Shopware6',Updated=TO_TIMESTAMP('2021-03-16 20:49:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540091
;

-- 2021-03-16T19:49:10.117Z
-- URL zum Konzept
UPDATE AD_User SET Value='shopware',Updated=TO_TIMESTAMP('2021-03-16 20:49:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540091
;

-- 2021-03-16T19:49:12.172Z
-- URL zum Konzept
UPDATE AD_User SET Lastname='API-User', Name='API-User, Shopware6',Updated=TO_TIMESTAMP('2021-03-16 20:49:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540091
;

-- 2021-03-16T19:49:14.608Z
-- URL zum Konzept
UPDATE AD_User SET Value='shopware',Updated=TO_TIMESTAMP('2021-03-16 20:49:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540091
;

-- 2021-03-16T19:49:41.364Z
-- URL zum Konzept
UPDATE AD_User SET IsActive='N',Updated=TO_TIMESTAMP('2021-03-16 20:49:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540091
;

-- 2021-03-16T19:49:42.302Z
-- URL zum Konzept
UPDATE AD_User SET IsActive='Y',Updated=TO_TIMESTAMP('2021-03-16 20:49:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540091
;

-- 2021-03-16T19:49:44.821Z
-- URL zum Konzept
INSERT INTO AD_User_Roles (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_User_ID,AD_User_Roles_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,0,540024,540091,540113,TO_TIMESTAMP('2021-03-16 20:49:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-03-16 20:49:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T19:49:49.676Z
-- URL zum Konzept
UPDATE AD_User SET IsActive='N',Updated=TO_TIMESTAMP('2021-03-16 20:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540091
;

--
-- scheduler to get shopware-orders (inactive)
--
-- 2021-03-16T19:51:21.710Z
-- URL zum Konzept
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,1000000,584804,540024,550069,TO_TIMESTAMP('2021-03-16 20:51:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',10,'M','Y','N',7,'Call_External_System_Shopware6 - getOrders','N','P','F','NEW',540091,TO_TIMESTAMP('2021-03-16 20:51:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T19:51:34.571Z
-- URL zum Konzept
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,541939,550069,540025,TO_TIMESTAMP('2021-03-16 20:51:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-03-16 20:51:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T19:51:46.867Z
-- URL zum Konzept
UPDATE AD_Scheduler_Para SET ParameterDefault='getOrders',Updated=TO_TIMESTAMP('2021-03-16 20:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_Para_ID=540025
;

-- 2021-03-16T19:51:50.523Z
-- URL zum Konzept
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,541940,550069,540026,TO_TIMESTAMP('2021-03-16 20:51:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-03-16 20:51:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T19:51:57.356Z
-- URL zum Konzept
UPDATE AD_Scheduler_Para SET ParameterDefault='540000',Updated=TO_TIMESTAMP('2021-03-16 20:51:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_Para_ID=540026
;
-- 2021-03-16T19:55:04.173Z
-- URL zum Konzept
UPDATE AD_Scheduler SET CronPattern=NULL, IsActive='N',Updated=TO_TIMESTAMP('2021-03-16 20:55:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550069
;


--------
--------
--Alberta (inactive)

-- 2021-03-16T18:42:20.850Z
-- URL zum Konzept
INSERT INTO ExternalSystem_Config (AD_Client_ID,AD_Org_ID,CamelURL,Created,CreatedBy,ExternalSystem_Config_ID,IsActive,Name,Type,Updated,UpdatedBy) VALUES (1000000,1000000,'http',TO_TIMESTAMP('2021-03-16 19:42:20','YYYY-MM-DD HH24:MI:SS'),100,540000,'N','Alberta','A',TO_TIMESTAMP('2021-03-16 19:42:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T18:42:46.909Z
-- URL zum Konzept
UPDATE ExternalSystem_Config SET CamelURL='http://172.17.0.1:54804/camel/do',Updated=TO_TIMESTAMP('2021-03-16 19:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_ID=540000
;

-- 2021-03-16T18:46:01.809Z
-- URL zum Konzept
INSERT INTO ExternalSystem_Config_Alberta (AD_Client_ID,AD_Org_ID,ApiKey,BaseURL,Created,CreatedBy,ExternalSystem_Config_Alberta_ID,ExternalSystem_Config_ID,ExternalSystemValue,IsActive,Pharmacy_PriceList_ID,Tenant,Updated,UpdatedBy) VALUES (1000000,1000000,'ApiKey','Base-URL',TO_TIMESTAMP('2021-03-16 19:46:01','YYYY-MM-DD HH24:MI:SS'),100,540000,540000,'Alberta','N',null,'Tenant',TO_TIMESTAMP('2021-03-16 19:46:01','YYYY-MM-DD HH24:MI:SS'),100)
;


--
-- API-User (inactive, without token)
-- 

-- 2021-03-16T19:58:07.554Z
-- URL zum Konzept
INSERT INTO AD_User (AD_Client_ID,AD_Language,AD_Org_ID,AD_User_ID,Created,CreatedBy,Firstname,Fresh_Gift,IsAccountLocked,IsActive,IsAuthorizedSignatory,IsBillToContact_Default,IsDecider,IsDefaultContact,IsFullBPAccess,IsInPayroll,IsLoginAsHostKey,IsManagement,IsMFProcurementUser,IsMultiplier,IsNewsletter,IsPurchaseContact,IsPurchaseContact_Default,IsSalesContact,IsSalesContact_Default,IsShipToContact_Default,IsSubjectMatterContact,IsSystemUser,Name,NotificationType,Processing,Title,Updated,UpdatedBy,Value) VALUES (1000000,'de_DE',1000000,540092,TO_TIMESTAMP('2021-03-16 20:58:07','YYYY-MM-DD HH24:MI:SS'),100,'API-User','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','API-User','N','N','',TO_TIMESTAMP('2021-03-16 20:58:07','YYYY-MM-DD HH24:MI:SS'),100,'1000079')
;

-- 2021-03-16T19:58:14.065Z
-- URL zum Konzept
UPDATE AD_User SET Lastname='API-User', Name='API-User, API-User',Updated=TO_TIMESTAMP('2021-03-16 20:58:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540092
;

-- 2021-03-16T19:58:17.827Z
-- URL zum Konzept
UPDATE AD_User SET Firstname='Alberta', Name='API-User, Alberta',Updated=TO_TIMESTAMP('2021-03-16 20:58:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540092
;

-- 2021-03-16T19:58:22.782Z
-- URL zum Konzept
UPDATE AD_User SET IsSystemUser='Y', Login='alberta',Updated=TO_TIMESTAMP('2021-03-16 20:58:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540092
;

-- 2021-03-16T19:58:35.301Z
-- URL zum Konzept
UPDATE AD_User SET Value='alberta',Updated=TO_TIMESTAMP('2021-03-16 20:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540092
;

-- 2021-03-16T19:58:35.481Z
-- URL zum Konzept
INSERT INTO AD_User_Roles (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_User_ID,AD_User_Roles_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,0,540024,540092,540114,TO_TIMESTAMP('2021-03-16 20:58:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-03-16 20:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2021-03-16T20:02:25.614Z
-- URL zum Konzept
UPDATE AD_User SET IsActive='N',Updated=TO_TIMESTAMP('2021-03-16 21:02:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540092
;


--
-- Scheduler to get patients (inactive)
--
-- 2021-03-16T19:01:48.439Z
-- URL zum Konzept
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,1000000,584802,540024,550067,TO_TIMESTAMP('2021-03-16 20:01:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',1,'M','Y','N',7,'Call_External_System_Alberta','N','P','F','NEW',540092,TO_TIMESTAMP('2021-03-16 20:01:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T19:02:11.406Z
-- URL zum Konzept
UPDATE AD_Scheduler SET CronPattern=NULL, Frequency=10,Updated=TO_TIMESTAMP('2021-03-16 20:02:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550067
;

-- 2021-03-16T19:02:19.886Z
-- URL zum Konzept
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,541931,550067,540020,TO_TIMESTAMP('2021-03-16 20:02:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-03-16 20:02:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T19:02:36.297Z
-- URL zum Konzept
UPDATE AD_Scheduler_Para SET ParameterDefault='getPatients',Updated=TO_TIMESTAMP('2021-03-16 20:02:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_Para_ID=540020
;

-- 2021-03-16T19:02:42.578Z
-- URL zum Konzept
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,541932,550067,540021,TO_TIMESTAMP('2021-03-16 20:02:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-03-16 20:02:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T19:02:54.803Z
-- URL zum Konzept
UPDATE AD_Scheduler_Para SET ParameterDefault='540000',Updated=TO_TIMESTAMP('2021-03-16 20:02:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_Para_ID=540021
;

-- 2021-03-16T19:05:43.089Z
-- URL zum Konzept
UPDATE AD_Scheduler SET CronPattern=NULL, Name='Call_External_System_Alberta - getPatients',Updated=TO_TIMESTAMP('2021-03-16 20:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550067
;

-- 2021-03-16T19:05:43.124Z
-- URL zum Konzept
UPDATE AD_Scheduler SET CronPattern=NULL, IsActive='N',Updated=TO_TIMESTAMP('2021-03-16 20:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550067
;

--
-- Scheduler to post or put products (inactive)
--
-- 2021-03-16T19:33:06.163Z
-- URL zum Konzept
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,1000000,540024,550068,TO_TIMESTAMP('2021-03-16 20:33:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',10,'M','Y','N',7,'Call_External_System_Alberta - pushProducts','N','P','F','NEW',540092,TO_TIMESTAMP('2021-03-16 20:33:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T19:33:25.980Z
-- URL zum Konzept
UPDATE AD_Scheduler SET AD_Process_ID=584802, CronPattern=NULL,Updated=TO_TIMESTAMP('2021-03-16 20:33:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550068
;

-- 2021-03-16T19:33:32.038Z
-- URL zum Konzept
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,541931,550068,540023,TO_TIMESTAMP('2021-03-16 20:33:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-03-16 20:33:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T19:33:42.830Z
-- URL zum Konzept
UPDATE AD_Scheduler_Para SET ParameterDefault='pushProducts',Updated=TO_TIMESTAMP('2021-03-16 20:33:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_Para_ID=540023
;

-- 2021-03-16T19:33:52.779Z
-- URL zum Konzept
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,541932,550068,540024,TO_TIMESTAMP('2021-03-16 20:33:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-03-16 20:33:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T19:34:07.198Z
-- URL zum Konzept
UPDATE AD_Scheduler_Para SET ParameterDefault='540000',Updated=TO_TIMESTAMP('2021-03-16 20:34:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_Para_ID=540024
;

-- 2021-03-16T19:34:10.607Z
-- URL zum Konzept
UPDATE AD_Scheduler SET CronPattern=NULL, IsActive='N',Updated=TO_TIMESTAMP('2021-03-16 20:34:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550068
;
