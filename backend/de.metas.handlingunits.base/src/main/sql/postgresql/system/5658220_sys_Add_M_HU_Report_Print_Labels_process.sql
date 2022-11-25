-- 2022-09-30T12:56:30.157Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585114,'N','de.metas.handlingunits.process.M_HU_Report_Print_Labels','N',TO_TIMESTAMP('2022-09-30 14:56:30','YYYY-MM-DD HH24:MI:SS'),100,'','U','Y','N','N','N','N','N','N','N','Y','Y',0,'Paletten Etikett','json','N','S','xls','Hooked in M_HU_Report. When called, it expects the M_HU_IDs in T_Selection. Will generate the QR codes for them and will call the actual Jasper Report which prints the LU label that contains QR labels and addtional product infos.','Java',TO_TIMESTAMP('2022-09-30 14:56:30','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_Report_Print_Labels')
;

-- 2022-09-30T12:56:30.159Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585114 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-09-30T12:59:00.325Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540604,'AD_Process.JasperReport IS NOT NULL AND AD_Process.Value ilike ''%HU_Labels%''',TO_TIMESTAMP('2022-09-30 14:59:00','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','AD_Process Jasper Reports for HUs','S',TO_TIMESTAMP('2022-09-30 14:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-30T12:59:10.487Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,117,0,585114,542309,19,540604,'AD_Process_ID',TO_TIMESTAMP('2022-09-30 14:59:10','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','U',0,'das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','Y','N','N','N','Prozess',10,TO_TIMESTAMP('2022-09-30 14:59:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-30T12:59:10.488Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542309 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-09-30T12:59:15.225Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-09-30 14:59:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542309
;

-- 2022-09-30T12:59:58.716Z
-- URL zum Konzept
UPDATE AD_Process_Para SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2022-09-30 14:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542309
;

-- 2022-09-30T13:00:18.172Z
-- URL zum Konzept
UPDATE AD_Process SET Name='Etiketten',Updated=TO_TIMESTAMP('2022-09-30 15:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585114
;

-- 2022-09-30T13:01:51.199Z
-- URL zum Konzept
INSERT INTO M_HU_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,IsActive,IsApplyToCUs,IsApplyToLUs,IsApplyToTopLevelHUsOnly,IsApplyToTUs,IsProvideAsUserAction,M_HU_Process_ID,Updated,UpdatedBy) VALUES (0,0,585114,TO_TIMESTAMP('2022-09-30 15:01:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Y',540024,TO_TIMESTAMP('2022-09-30 15:01:51','YYYY-MM-DD HH24:MI:SS'),100)
;


update ad_process_trl  set name = 'Etiketten' where ad_process_id=585114 and ad_language in ('de_DE','de_CH');

update ad_process_trl  set name = 'HU Labels' where ad_process_id=585114 and ad_language ='en_US';