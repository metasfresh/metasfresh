

-------
-- Set the C_Queue_PackageProcessors' internal names
-------
-- 12.10.2015 15:31
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET InternalName='InvoiceCandWorkpackageProcessor',Updated=TO_TIMESTAMP('2015-10-12 15:31:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540000
;

-- 12.10.2015 15:31
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET InternalName='DocOutboundWorkpackageProcessor',Updated=TO_TIMESTAMP('2015-10-12 15:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540001
;

-- 12.10.2015 15:31
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET InternalName='PasswordResetWorkpackageProcessor',Updated=TO_TIMESTAMP('2015-10-12 15:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540004
;

-- 12.10.2015 15:31
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET InternalName='GenerateReceiptScheduleWorkpackageProcessor',Updated=TO_TIMESTAMP('2015-10-12 15:31:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540006
;

-- 12.10.2015 15:31
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET InternalName='UpdateReceiptScheduleWorkpackageProcessor',Updated=TO_TIMESTAMP('2015-10-12 15:31:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540008
;

-- 12.10.2015 15:31
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET Description='See http://dewiki908/mediawiki/index.php/04758_Bestellungen_an_Fax_Server_%C3%BCbermitteln_%282013081610000038%29', InternalName='DocOutboundCCWorkpackageProcessor',Updated=TO_TIMESTAMP('2015-10-12 15:31:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540009
;

-- 12.10.2015 15:31
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET InternalName='GenerateInOutFromHU',Updated=TO_TIMESTAMP('2015-10-12 15:31:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540010
;

-- 12.10.2015 15:31
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET Description='See http://dewiki908/mediawiki/index.php/07042_Simple_InOut-Creation_from_shipment-schedule_%28109342691288%29', InternalName='GenerateInOutFromShipmentSchedules',Updated=TO_TIMESTAMP('2015-10-12 15:31:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540011
;

-- 12.10.2015 15:32
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET Description='http://dewiki908/mediawiki/index.php/08577_ESR_Import_merge_from_%28107890741466%29', InternalName='LoadESRImportFileWorkpackageProcessor',Updated=TO_TIMESTAMP('2015-10-12 15:32:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540013
;

-- 12.10.2015 15:32
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET Description='See http://dewiki908/mediawiki/index.php/07373_Email_Versand_an_Urproduzent_mit_Button_Fertigstellen_und_Email_senden_in_Listen%C3%BCbersicht_%28104179228076%29', InternalName='MailWorkpackageProcessor',Updated=TO_TIMESTAMP('2015-10-12 15:32:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540014
;

-- 12.10.2015 15:32
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET Description='See http://dewiki908/mediawiki/index.php/08038_EDI_DESADV%2C_INVOIC_halbautomatisch_%28107606012432%29#Development_infrastructure', InternalName='EDIWorkpackageProcessor',Updated=TO_TIMESTAMP('2015-10-12 15:32:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540015
;

-- 12.10.2015 15:32
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET Description='Generate planning HUs for submited receipt schedule(s). See http://dewiki908/mediawiki/index.php/08168_Verantwortlichkeit_Server_Betrieb_und_Auslegung_%28103370306103%29', InternalName='M_ReceiptSchedule_GeneratePlanningHUs_WorkpackageProcessor',Updated=TO_TIMESTAMP('2015-10-12 15:32:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540016
;

-- 12.10.2015 15:32
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET InternalName='CheckProcessedAsynBatchWorkpackageProcessor',Updated=TO_TIMESTAMP('2015-10-12 15:32:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540020
;

-- 12.10.2015 15:32
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET InternalName='M_Storage_Add',Updated=TO_TIMESTAMP('2015-10-12 15:32:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540022
;

-- 12.10.2015 15:33
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET InternalName='C_BPartner_UpdateStatsFromInvoice',Updated=TO_TIMESTAMP('2015-10-12 15:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540023
;

-- 12.10.2015 15:33
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET InternalName='C_BPartner_UpdateStatsFromBPartner',Updated=TO_TIMESTAMP('2015-10-12 15:33:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540024
;

---------
-- create the AD_SysConfig records
---------
-- 12.10.2015 15:36
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540893,'S',TO_TIMESTAMP('2015-10-12 15:36:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','Y','de.metas.async.InvoiceCandWorkpackageProcessor.SizeBasedPrio.01',TO_TIMESTAMP('2015-10-12 15:36:12','YYYY-MM-DD HH24:MI:SS'),100,'urgent')
;

-- 12.10.2015 15:36
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540894,'S',TO_TIMESTAMP('2015-10-12 15:36:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','de.metas.async.InvoiceCandWorkpackageProcessor.SizeBasedPrio.05',TO_TIMESTAMP('2015-10-12 15:36:48','YYYY-MM-DD HH24:MI:SS'),100,'high')
;

-- 12.10.2015 15:36
-- URL zum Konzept
UPDATE AD_SysConfig SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2015-10-12 15:36:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540893
;

-- 12.10.2015 15:37
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540895,'S',TO_TIMESTAMP('2015-10-12 15:37:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','de.metas.async.InvoiceCandWorkpackageProcessor.SizeBasedPrio.15',TO_TIMESTAMP('2015-10-12 15:37:49','YYYY-MM-DD HH24:MI:SS'),100,'medium')
;

-- 12.10.2015 15:38
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540896,'S',TO_TIMESTAMP('2015-10-12 15:38:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','de.metas.async.InvoiceCandWorkpackageProcessor.SizeBasedPrio.30',TO_TIMESTAMP('2015-10-12 15:38:17','YYYY-MM-DD HH24:MI:SS'),100,'low')
;

-- 12.10.2015 15:38
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540897,'S',TO_TIMESTAMP('2015-10-12 15:38:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','de.metas.async.InvoiceCandWorkpackageProcessor.SizeBasedPrio.50',TO_TIMESTAMP('2015-10-12 15:38:50','YYYY-MM-DD HH24:MI:SS'),100,'minor')
;

-- 12.10.2015 15:40
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540898,'S',TO_TIMESTAMP('2015-10-12 15:40:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','de.metas.async.GenerateInOutFromShipmentSchedules.SizeBasedPrio.01',TO_TIMESTAMP('2015-10-12 15:40:02','YYYY-MM-DD HH24:MI:SS'),100,'urgent')
;

-- 12.10.2015 15:41
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540899,'S',TO_TIMESTAMP('2015-10-12 15:41:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','de.metas.async.GenerateInOutFromShipmentSchedules.SizeBasedPrio.05',TO_TIMESTAMP('2015-10-12 15:41:06','YYYY-MM-DD HH24:MI:SS'),100,'high')
;

-- 12.10.2015 15:41
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540900,'S',TO_TIMESTAMP('2015-10-12 15:41:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','de.metas.async.GenerateInOutFromShipmentSchedules.SizeBasedPrio.15',TO_TIMESTAMP('2015-10-12 15:41:36','YYYY-MM-DD HH24:MI:SS'),100,'medium')
;

-- 12.10.2015 15:42
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540901,'S',TO_TIMESTAMP('2015-10-12 15:42:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','de.metas.async.GenerateInOutFromShipmentSchedules.SizeBasedPrio.30',TO_TIMESTAMP('2015-10-12 15:42:09','YYYY-MM-DD HH24:MI:SS'),100,'low')
;

-- 12.10.2015 15:42
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540902,'S',TO_TIMESTAMP('2015-10-12 15:42:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','de.metas.async.GenerateInOutFromShipmentSchedules.SizeBasedPrio.50',TO_TIMESTAMP('2015-10-12 15:42:40','YYYY-MM-DD HH24:MI:SS'),100,'minor')
;

-- 12.10.2015 16:27
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='priority for the 1st enqueued workpackage',Updated=TO_TIMESTAMP('2015-10-12 16:27:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540898
;

-- 12.10.2015 16:27
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority for the 1st enqueued workpackage',Updated=TO_TIMESTAMP('2015-10-12 16:27:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540898
;

-- 12.10.2015 16:27
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority for the 1st enqueued invoicing workpackage',Updated=TO_TIMESTAMP('2015-10-12 16:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540898
;

-- 12.10.2015 16:27
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority for the 2nd to 5th enqueued invoicing workpackage',Updated=TO_TIMESTAMP('2015-10-12 16:27:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540899
;

-- 12.10.2015 16:28
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority for the 1st enqueued shipping workpackage',Updated=TO_TIMESTAMP('2015-10-12 16:28:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540893
;

-- 12.10.2015 16:28
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority for the 2nd to 5th enqueued shipping workpackage',Updated=TO_TIMESTAMP('2015-10-12 16:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540894
;

---------------
-- sysconfig fixes after code-fixes
---------------
-- 13.10.2015 09:03
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 2nd enqueued invoicing workpackage onwards', Name='de.metas.async.GenerateInOutFromShipmentSchedules.SizeBasedPrio.02',Updated=TO_TIMESTAMP('2015-10-13 09:03:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540899
;

-- 13.10.2015 09:03
-- URL zum Konzept
UPDATE AD_SysConfig SET Name='de.metas.async.GenerateInOutFromShipmentSchedules.SizeBasedPrio.06',Updated=TO_TIMESTAMP('2015-10-13 09:03:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540900
;

-- 13.10.2015 09:04
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 6th enqueued invoicing workpackage onwards',Updated=TO_TIMESTAMP('2015-10-13 09:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540900
;

-- 13.10.2015 09:04
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 16th enqueued invoicing workpackage onwards', Name='de.metas.async.GenerateInOutFromShipmentSchedules.SizeBasedPrio.16',Updated=TO_TIMESTAMP('2015-10-13 09:04:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540901
;

-- 13.10.2015 09:04
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 31st enqueued invoicing workpackage onwards', Name='de.metas.async.GenerateInOutFromShipmentSchedules.SizeBasedPrio.31',Updated=TO_TIMESTAMP('2015-10-13 09:04:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540902
;

-- 13.10.2015 09:04
-- URL zum Konzept
UPDATE AD_SysConfig SET Name='de.metas.async.InvoiceCandWorkpackageProcessor.SizeBasedPrio.02',Updated=TO_TIMESTAMP('2015-10-13 09:04:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540894
;

-- 13.10.2015 09:04
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 2nd enqueued shipping workpackage onwards',Updated=TO_TIMESTAMP('2015-10-13 09:04:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540894
;

-- 13.10.2015 09:05
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 6th enqueued shipping workpackage onwards', Name='de.metas.async.InvoiceCandWorkpackageProcessor.SizeBasedPrio.06',Updated=TO_TIMESTAMP('2015-10-13 09:05:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540895
;

-- 13.10.2015 09:05
-- URL zum Konzept
UPDATE AD_SysConfig SET Name='de.metas.async.InvoiceCandWorkpackageProcessor.SizeBasedPrio.16',Updated=TO_TIMESTAMP('2015-10-13 09:05:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540896
;

-- 13.10.2015 09:05
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 16th enqueued shipping workpackage onwards',Updated=TO_TIMESTAMP('2015-10-13 09:05:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540896
;

-- 13.10.2015 09:05
-- URL zum Konzept
UPDATE AD_SysConfig SET Name='de.metas.async.InvoiceCandWorkpackageProcessor.SizeBasedPrio.31',Updated=TO_TIMESTAMP('2015-10-13 09:05:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540897
;

-- 13.10.2015 09:05
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 31st enqueued shipping workpackage onwards',Updated=TO_TIMESTAMP('2015-10-13 09:05:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540897
;


-- 13.10.2015 09:10
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 31st enqueued workpackage onwards',Updated=TO_TIMESTAMP('2015-10-13 09:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540897
;

-- 13.10.2015 09:10
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 16th enqueued workpackage onwards',Updated=TO_TIMESTAMP('2015-10-13 09:10:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540896
;

-- 13.10.2015 09:10
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 6th enqueued workpackage onwards',Updated=TO_TIMESTAMP('2015-10-13 09:10:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540895
;

-- 13.10.2015 09:10
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 2nd enqueued workpackage onwards',Updated=TO_TIMESTAMP('2015-10-13 09:10:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540894
;

-- 13.10.2015 09:10
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority for the 1st enqueued workpackage',Updated=TO_TIMESTAMP('2015-10-13 09:10:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540893
;

-- 13.10.2015 09:10
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 31st enqueued workpackage onwards',Updated=TO_TIMESTAMP('2015-10-13 09:10:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540902
;

-- 13.10.2015 09:10
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 16th enqueued workpackage onwards',Updated=TO_TIMESTAMP('2015-10-13 09:10:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540901
;

-- 13.10.2015 09:10
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 6th enqueued workpackage onwards',Updated=TO_TIMESTAMP('2015-10-13 09:10:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540900
;

-- 13.10.2015 09:10
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority from the 2nd enqueued workpackage onwards',Updated=TO_TIMESTAMP('2015-10-13 09:10:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540899
;

-- 13.10.2015 09:10
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Priority for the 1st enqueued workpackage',Updated=TO_TIMESTAMP('2015-10-13 09:10:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540898
;

