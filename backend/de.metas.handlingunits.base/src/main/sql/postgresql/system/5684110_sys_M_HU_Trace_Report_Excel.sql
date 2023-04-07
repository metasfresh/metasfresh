-- Value: M_HU_Trace_Report
-- 2023-04-04T16:02:03.264Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585253,'Y','N',TO_TIMESTAMP('2023-04-04 19:02:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Rückverfolgungsbericht ','json','N','N','xls','Java',TO_TIMESTAMP('2023-04-04 19:02:03','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_Trace_Report')
;

-- 2023-04-04T16:02:03.268Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585253 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: M_HU_Trace_Report (Excel)
-- 2023-04-05T07:43:44.228Z
UPDATE AD_Process SET Value='M_HU_Trace_Report (Excel)',Updated=TO_TIMESTAMP('2023-04-05 10:43:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585253
;

-- Value: M_HU_Trace_Report_Excel
-- 2023-04-05T07:44:43.797Z
UPDATE AD_Process SET Value='M_HU_Trace_Report_Excel',Updated=TO_TIMESTAMP('2023-04-05 10:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585253
;

-- Value: M_HU_Trace_Report_Excel
-- Classname: de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel
-- 2023-04-05T07:46:00.734Z
UPDATE AD_Process SET Classname='de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel',Updated=TO_TIMESTAMP('2023-04-05 10:46:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585253
;

-- Process: M_HU_Trace_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)
-- ParameterName: M_Product_ID
-- 2023-04-05T07:46:38.860Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,585253,542613,30,'M_Product_ID',TO_TIMESTAMP('2023-04-05 10:46:38','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','de.metas.handlingunits',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','Produkt',10,TO_TIMESTAMP('2023-04-05 10:46:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-05T07:46:38.863Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542613 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_HU_Trace_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)
-- ParameterName: Lot
-- 2023-04-05T07:46:56.371Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,446,0,585253,542614,30,'Lot',TO_TIMESTAMP('2023-04-05 10:46:56','YYYY-MM-DD HH24:MI:SS'),100,'Los-Nummer (alphanumerisch)','de.metas.handlingunits',0,'"Los-Nr." bezeichnet die spezifische Los- oder Chargen-Nummer zu der ein Produkt gehört.','Y','N','Y','N','N','N','Los-Nr.',20,TO_TIMESTAMP('2023-04-05 10:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-05T07:46:56.373Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542614 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_HU_Trace_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)
-- ParameterName: M_HU_ID
-- 2023-04-05T07:47:18.732Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542140,0,585253,542615,30,'M_HU_ID',TO_TIMESTAMP('2023-04-05 10:47:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','N','N','Handling Unit',30,TO_TIMESTAMP('2023-04-05 10:47:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-05T07:47:18.734Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542615 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_HU_Trace_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)
-- ParameterName: Lot
-- 2023-04-05T07:47:25.633Z
UPDATE AD_Process_Para SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2023-04-05 10:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542614
;

-- Process: M_HU_Trace_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)
-- ParameterName: LotNumber
-- 2023-04-05T07:50:51.627Z
UPDATE AD_Process_Para SET AD_Element_ID=576652, ColumnName='LotNumber', Description=NULL, EntityType='de.metas.vertical.pharma.securpharm', Help=NULL, Name='Chargennummer',Updated=TO_TIMESTAMP('2023-04-05 10:50:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542614
;




-- Process: M_HU_Trace_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)
-- ParameterName: VHU_ID
-- 2023-04-06T08:40:13.335Z
UPDATE AD_Process_Para SET AD_Element_ID=542583, ColumnName='VHU_ID', Description='Customer Unit', Name='CU',Updated=TO_TIMESTAMP('2023-04-06 11:40:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542615
;

-- Process: M_HU_Trace_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)
-- ParameterName: VHU_ID
-- 2023-04-06T08:41:42.583Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540499,Updated=TO_TIMESTAMP('2023-04-06 11:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542615
;

