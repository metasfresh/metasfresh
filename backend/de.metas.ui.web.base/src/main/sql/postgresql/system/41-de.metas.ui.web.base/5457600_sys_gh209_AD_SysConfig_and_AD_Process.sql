--
-- AS_SysConfig for the receiptlabel process
--

-- 04.03.2017 21:35
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541078,'O',TO_TIMESTAMP('2017-03-04 21:35:45','YYYY-MM-DD HH24:MI:SS'),100,'This specifies the process which the system shall use when printing material receipts labels for HUs
Note: AD_Process_ID=540370 is the process with value=Wareneingangsetikett LU (Jasper)','de.metas.handlingunits','Y','de.metas.handlingunits.MaterialReceiptLabel.AD_Process_ID',TO_TIMESTAMP('2017-03-04 21:35:45','YYYY-MM-DD HH24:MI:SS'),100,'540370')
;

-- 04.03.2017 21:35
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='This specifies the process which the system shall use when printing material receipts labels for HUs
Note: AD_Process_ID=540370 is the process with value "Wareneingangsetikett LU (Jasper)"',Updated=TO_TIMESTAMP('2017-03-04 21:35:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541078
;

-- 04.03.2017 21:36
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='This specifies the process which the system shall use when printing material receipts labels for HUs.
Note: AD_Process_ID=540370 is the process with the value "Wareneingangsetikett LU (Jasper)".',Updated=TO_TIMESTAMP('2017-03-04 21:36:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541078
;

-- 04.03.2017 21:37
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541079,'O',TO_TIMESTAMP('2017-03-04 21:37:44','YYYY-MM-DD HH24:MI:SS'),100,'Y means that the system will automatically print a material receipt label then a HU is received.','de.metas.handlingunits','Y','de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Enabled',TO_TIMESTAMP('2017-03-04 21:37:44','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 04.03.2017 21:38
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Y means that the system will automatically print a material receipt label then a HU is received.
This only works if de.metas.handlingunits.MaterialReceiptLabel.AD_Process_ID is also set apropriately.',Updated=TO_TIMESTAMP('2017-03-04 21:38:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541079
;

-- 04.03.2017 21:39
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541080,'O',TO_TIMESTAMP('2017-03-04 21:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Number of copies (1 means one printout) to print if ','de.metas.handlingunits','Y','de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Copies',TO_TIMESTAMP('2017-03-04 21:39:02','YYYY-MM-DD HH24:MI:SS'),100,'2')
;

-- 04.03.2017 21:39
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Number of copies (1 means one printout) to print if de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Enabled is set to Y.',Updated=TO_TIMESTAMP('2017-03-04 21:39:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541080
;

--
-- the new WEBUI_M_HU_PRintReceiptLabel process
--
-- 04.03.2017 21:43
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540766,'N','de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateMaterialReceipt','N',TO_TIMESTAMP('2017-03-04 21:43:05','YYYY-MM-DD HH24:MI:SS'),100,'This process can invoke the process specitfied via SysConfig de.metas.handlingunits.MaterialReceiptLabel.AD_Process_ID','de.metas.ui.web','Y','N','N','N','N','N','N','Y',0,'Print material receipt label','N','Y','Java',TO_TIMESTAMP('2017-03-04 21:43:05','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_M_HU_PrintReceiptLabel')
;

-- 04.03.2017 21:43
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540766 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 04.03.2017 21:43
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.ui.web.handlingunits.process.WEBUI_M_HU_PrintReceiptLabel',Updated=TO_TIMESTAMP('2017-03-04 21:43:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540766
;

-- 04.03.2017 21:44
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMin) VALUES (0,505211,0,540766,541164,11,'Copies',TO_TIMESTAMP('2017-03-04 21:44:14','YYYY-MM-DD HH24:MI:SS'),100,'1','Anzahl der zu erstellenden/zu druckenden Exemplare','de.metas.ui.web',0,'Y','N','Y','N','Y','N','Kopien',10,TO_TIMESTAMP('2017-03-04 21:44:14','YYYY-MM-DD HH24:MI:SS'),100,'1')
;

-- 04.03.2017 21:44
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541164 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 04.03.2017 21:46
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540766,540516,TO_TIMESTAMP('2017-03-04 21:46:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y',TO_TIMESTAMP('2017-03-04 21:46:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 04.03.2017 21:46
-- URL zum Konzept
UPDATE AD_Table_Process SET WEBUI_QuickAction='Y',Updated=TO_TIMESTAMP('2017-03-04 21:46:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540766 AND AD_Table_ID=540516
;

