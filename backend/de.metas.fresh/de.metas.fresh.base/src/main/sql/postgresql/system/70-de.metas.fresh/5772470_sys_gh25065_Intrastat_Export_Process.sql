-- Run mode: SWING_CLIENT

-- 2025-10-06T08:54:53.674Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584084,0,'IntrastatDeclarationType',TO_TIMESTAMP('2025-10-06 08:54:52.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Deklarationstyp','Deklarationstyp',TO_TIMESTAMP('2025-10-06 08:54:52.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T08:54:53.746Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584084 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IntrastatDeclarationType
-- 2025-10-06T08:55:29.032Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Declaration Type', PrintName='Declaration Type',Updated=TO_TIMESTAMP('2025-10-06 08:55:29.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584084 AND AD_Language='en_US'
;

-- 2025-10-06T08:55:29.098Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T08:55:37.060Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584084,'en_US')
;

-- 2025-10-06T08:56:04.813Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584085,0,'IntrastaNatureOfTransaction',TO_TIMESTAMP('2025-10-06 08:56:04.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Art der Transaktion','Art der Transaktion',TO_TIMESTAMP('2025-10-06 08:56:04.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T08:56:04.880Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584085 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IntrastaNatureOfTransaction
-- 2025-10-06T08:56:21.626Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Nature of Transaction', PrintName='Nature of Transaction',Updated=TO_TIMESTAMP('2025-10-06 08:56:21.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584085 AND AD_Language='en_US'
;

-- 2025-10-06T08:56:21.694Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T08:56:31.787Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584085,'en_US')
;

-- Value: Intrastat_Export
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-10-06T08:57:35.429Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585508,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2025-10-06 08:57:34.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Erzeugt eine Datei, die in das Reporting Tool Intra Collect hochgeladen werden kann, um Importe und Exporte zu deklarieren.
Die Zolltarifnummer des jeweiligen Produktes wird als CN8-Code ausgegben','D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'INTRASTAT RTIC Datei (AT)','json','N','N','xls','SELECT * FROM report.Intrastat_Export(@C_Year_ID/-1@, @C_Period_ID/-1@, @IntrastatDeclarationType@, @IntrastaNatureOfTransaction@)','Excel',TO_TIMESTAMP('2025-10-06 08:57:34.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Intrastat_Export')
;

-- 2025-10-06T08:57:35.496Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585508 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Intrastat_Export(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-10-06T08:58:29.830Z
UPDATE AD_Process_Trl SET Description='Creates a file that can be uploaded into the Reporting Tool Intra Collect to declare imports and exports
The customs tariff number of the respective product is issued as CN8-code', IsTranslated='Y', Name='INTRASTAT RTIC File (AT)',Updated=TO_TIMESTAMP('2025-10-06 08:58:29.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585508
;

-- 2025-10-06T08:58:29.895Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Name: C_Year_2025
-- 2025-10-06T09:03:46.238Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540748,'C_Year.FiscalYear::Numeric >= 2025',TO_TIMESTAMP('2025-10-06 09:03:45.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','C_Year_2025','S',TO_TIMESTAMP('2025-10-06 09:03:45.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Process: Intrastat_Export(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_Year_ID
-- 2025-10-06T09:04:08.054Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,223,0,585508,543004,30,540133,540748,'C_Year_ID',TO_TIMESTAMP('2025-10-06 09:04:07.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kalenderjahr','D',0,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','Y','N','N','N','Jahr',10,TO_TIMESTAMP('2025-10-06 09:04:07.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:04:08.124Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543004 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Intrastat_Export(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_Period_ID
-- 2025-10-06T09:05:33.521Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,206,0,585508,543005,30,540658,'C_Period_ID',TO_TIMESTAMP('2025-10-06 09:05:32.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Periode des Kalenders','D',0,'"Periode" bezeichnet einen eklusiven Datumsbereich eines Kalenders.','Y','N','Y','N','N','N','Periode',20,TO_TIMESTAMP('2025-10-06 09:05:32.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:05:33.588Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543005 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Name: IntrastatDeclarationType
-- 2025-10-06T09:06:37.369Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541992,TO_TIMESTAMP('2025-10-06 09:06:36.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','IntrastatDeclarationType',TO_TIMESTAMP('2025-10-06 09:06:36.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-10-06T09:06:37.435Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541992 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: IntrastatDeclarationType
-- Value: Import
-- ValueName: Import
-- 2025-10-06T09:07:02.970Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541992,543988,TO_TIMESTAMP('2025-10-06 09:07:02.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Import',TO_TIMESTAMP('2025-10-06 09:07:02.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Import','Import')
;

-- 2025-10-06T09:07:03.040Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543988 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: IntrastatDeclarationType
-- Value: Export
-- ValueName: Export
-- 2025-10-06T09:07:32.206Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541992,543989,TO_TIMESTAMP('2025-10-06 09:07:31.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Export',TO_TIMESTAMP('2025-10-06 09:07:31.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Export','Export')
;

-- 2025-10-06T09:07:32.273Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543989 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Process: Intrastat_Export(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: IntrastatDeclarationType
-- 2025-10-06T09:07:45.430Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584084,0,585508,543006,17,541992,'IntrastatDeclarationType',TO_TIMESTAMP('2025-10-06 09:07:44.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U',0,'Y','N','Y','N','N','N','Deklarationstyp',30,TO_TIMESTAMP('2025-10-06 09:07:44.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:07:45.495Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543006 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Element: IntrastatDeclarationType
-- 2025-10-06T09:08:17.959Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Art', PrintName='Art',Updated=TO_TIMESTAMP('2025-10-06 09:08:17.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584084 AND AD_Language='de_DE'
;

-- 2025-10-06T09:08:18.026Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T09:08:29.477Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584084,'de_DE')
;

-- 2025-10-06T09:08:29.546Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584084,'de_DE')
;

-- Element: IntrastatDeclarationType
-- 2025-10-06T09:08:46.737Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Art', PrintName='Art',Updated=TO_TIMESTAMP('2025-10-06 09:08:46.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584084 AND AD_Language='de_CH'
;

-- 2025-10-06T09:08:46.802Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T09:08:58.117Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584084,'de_CH')
;

-- Element: IntrastatDeclarationType
-- 2025-10-06T09:09:16.472Z
UPDATE AD_Element_Trl SET Name='Type', PrintName='Type',Updated=TO_TIMESTAMP('2025-10-06 09:09:16.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584084 AND AD_Language='en_US'
;

-- 2025-10-06T09:09:16.539Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T09:09:28.118Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584084,'en_US')
;

-- Element: IntrastatDeclarationType
-- 2025-10-06T09:09:37.474Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Type', PrintName='Type',Updated=TO_TIMESTAMP('2025-10-06 09:09:37.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584084 AND AD_Language='fr_CH'
;

-- 2025-10-06T09:09:37.541Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T09:09:46.567Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584084,'fr_CH')
;

-- Process: Intrastat_Export(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: IntrastatDeclarationType
-- 2025-10-06T09:10:06.746Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-10-06 09:10:06.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543006
;

-- Process: Intrastat_Export(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: IntrastaNatureOfTransaction
-- 2025-10-06T09:11:58.580Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,584085,0,585508,543007,10,'IntrastaNatureOfTransaction',TO_TIMESTAMP('2025-10-06 09:11:57.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'11','1=2','D',0,'Y','N','Y','N','N','N','Art der Transaktion','',40,TO_TIMESTAMP('2025-10-06 09:11:57.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:11:58.646Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543007 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: Intrastat_Export
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-10-06T09:17:48.056Z
UPDATE AD_Process SET CSVFieldDelimiter='\t',Updated=TO_TIMESTAMP('2025-10-06 09:17:47.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585508
;

-- Run mode: SWING_CLIENT

-- 2025-10-06T09:24:01.789Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584087,0,TO_TIMESTAMP('2025-10-06 09:24:00.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','INTRASTAT RTIC Datei (AT)','INTRASTAT RTIC Datei (AT)',TO_TIMESTAMP('2025-10-06 09:24:00.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:24:01.859Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584087 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-10-06T09:24:35.297Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='INTRASTAT RTIC File (AT)', PrintName='INTRASTAT RTIC File (AT)',Updated=TO_TIMESTAMP('2025-10-06 09:24:35.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584087 AND AD_Language='en_US'
;

-- 2025-10-06T09:24:35.364Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T09:24:44.758Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584087,'en_US')
;

-- Name: INTRASTAT RTIC Datei (AT)
-- Action Type: P
-- Process: Intrastat_Export(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-10-06T09:26:15.778Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,584087,542261,0,585508,TO_TIMESTAMP('2025-10-06 09:26:14.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Intrastat_Export','Y','N','N','N','N','INTRASTAT RTIC Datei (AT)',TO_TIMESTAMP('2025-10-06 09:26:14.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:26:15.843Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542261 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-10-06T09:26:15.912Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542261, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542261)
;

-- 2025-10-06T09:26:15.985Z
/* DDL */  select update_menu_translation_from_ad_element(584087)
;

-- Reordering children of `Finance`
-- Node name: `Remittance Advice (REMADV)`
-- 2025-10-06T09:26:20.551Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Kontenauszug`
-- 2025-10-06T09:26:20.620Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Node name: `GL Journal`
-- 2025-10-06T09:26:20.687Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account`
-- 2025-10-06T09:26:20.755Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP)`
-- 2025-10-06T09:26:20.825Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement`
-- 2025-10-06T09:26:20.896Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement`
-- 2025-10-06T09:26:20.972Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `GL Distribution`
-- 2025-10-06T09:26:21.042Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal`
-- 2025-10-06T09:26:21.112Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2025-10-06T09:26:21.178Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment`
-- 2025-10-06T09:26:21.244Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations`
-- 2025-10-06T09:26:21.310Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection`
-- 2025-10-06T09:26:21.376Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation`
-- 2025-10-06T09:26:21.441Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture`
-- 2025-10-06T09:26:21.507Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning`
-- 2025-10-06T09:26:21.573Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates`
-- 2025-10-06T09:26:21.637Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions`
-- 2025-10-06T09:26:21.704Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import`
-- 2025-10-06T09:26:21.770Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination`
-- 2025-10-06T09:26:21.834Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts`
-- 2025-10-06T09:26:21.900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values`
-- 2025-10-06T09:26:21.966Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts`
-- 2025-10-06T09:26:22.031Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Cost Element`
-- 2025-10-06T09:26:22.096Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs`
-- 2025-10-06T09:26:22.163Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost`
-- 2025-10-06T09:26:22.229Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity`
-- 2025-10-06T09:26:22.297Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail`
-- 2025-10-06T09:26:22.364Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents`
-- 2025-10-06T09:26:22.430Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Type`
-- 2025-10-06T09:26:22.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation`
-- 2025-10-06T09:26:22.561Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Cost Center`
-- 2025-10-06T09:26:22.629Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No`
-- 2025-10-06T09:26:22.694Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type`
-- 2025-10-06T09:26:22.759Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export`
-- 2025-10-06T09:26:22.825Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices`
-- 2025-10-06T09:26:22.892Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents`
-- 2025-10-06T09:26:22.959Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment`
-- 2025-10-06T09:26:23.026Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents`
-- 2025-10-06T09:26:23.090Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-06T09:26:23.157Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2025-10-06T09:26:23.223Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2025-10-06T09:26:23.291Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-06T09:26:23.357Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-06T09:26:23.425Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File`
-- 2025-10-06T09:26:23.492Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides`
-- 2025-10-06T09:26:23.558Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Document Accounting Log`
-- 2025-10-06T09:26:23.624Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542238 AND AD_Tree_ID=10
;

-- Node name: `INTRASTAT RTIC Datei (AT)`
-- 2025-10-06T09:26:23.690Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542261 AND AD_Tree_ID=10
;

-- Element: null
-- 2025-10-06T09:27:00.748Z
UPDATE AD_Element_Trl SET Description='Creates a file that can be uploaded into the Reporting Tool Intra Collect to declare imports and exports The customs tariff number of the respective product is issued as CN8-code',Updated=TO_TIMESTAMP('2025-10-06 09:27:00.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584087 AND AD_Language='en_US'
;

-- 2025-10-06T09:27:00.815Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T09:27:12.237Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584087,'en_US')
;

-- Element: null
-- 2025-10-06T09:27:28.527Z
UPDATE AD_Element_Trl SET Description='Erzeugt eine Datei, die in das Reporting Tool Intra Collect hochgeladen werden kann, um Importe und Exporte zu deklarieren. Die Zolltarifnummer des jeweiligen Produktes wird als CN8-Code ausgegben', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-06 09:27:28.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584087 AND AD_Language='de_DE'
;

-- 2025-10-06T09:27:28.595Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T09:27:37.789Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584087,'de_DE')
;

-- 2025-10-06T09:27:37.855Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584087,'de_DE')
;

-- Element: null
-- 2025-10-06T09:27:45.116Z
UPDATE AD_Element_Trl SET Description='Erzeugt eine Datei, die in das Reporting Tool Intra Collect hochgeladen werden kann, um Importe und Exporte zu deklarieren. Die Zolltarifnummer des jeweiligen Produktes wird als CN8-Code ausgegben', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-06 09:27:45.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584087 AND AD_Language='de_CH'
;

-- 2025-10-06T09:27:45.181Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T09:27:55.472Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584087,'de_CH')
;

-- 2025-10-06T09:31:24.266Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584088,0,'CNCode',TO_TIMESTAMP('2025-10-06 09:31:23.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','CN8 Code','CN8 Code',TO_TIMESTAMP('2025-10-06 09:31:23.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:31:24.340Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584088 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-10-06T09:32:04.763Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584089,0,'GoodsDescription',TO_TIMESTAMP('2025-10-06 09:32:03.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Goods description','Goods description',TO_TIMESTAMP('2025-10-06 09:32:03.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:32:04.828Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584089 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-10-06T09:32:42.586Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584090,0,'CountryDestinationConsignment',TO_TIMESTAMP('2025-10-06 09:32:41.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Country of Destination/Consignment','Country of Destination/Consignment',TO_TIMESTAMP('2025-10-06 09:32:41.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:32:42.653Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584090 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-10-06T09:33:14.551Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584091,0,'CountryOfOrigin',TO_TIMESTAMP('2025-10-06 09:33:13.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Country of origin','Country of origin',TO_TIMESTAMP('2025-10-06 09:33:13.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:33:14.617Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584091 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-10-06T09:35:24.187Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584092,0,'NetMass',TO_TIMESTAMP('2025-10-06 09:35:23.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Net mass','Net mass',TO_TIMESTAMP('2025-10-06 09:35:23.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:35:24.253Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584092 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-10-06T09:35:56.868Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584093,0,'SupplementaryUnits',TO_TIMESTAMP('2025-10-06 09:35:56.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Supplementary units','Supplementary units',TO_TIMESTAMP('2025-10-06 09:35:56.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:35:56.937Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584093 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-10-06T09:36:20.427Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584094,0,'InvoiceValue',TO_TIMESTAMP('2025-10-06 09:36:19.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Invoice value','Invoice value',TO_TIMESTAMP('2025-10-06 09:36:19.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:36:20.495Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584094 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-10-06T09:36:44.997Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584095,0,'StatisticalValue',TO_TIMESTAMP('2025-10-06 09:36:44.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Statistical value','Statistical value',TO_TIMESTAMP('2025-10-06 09:36:44.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:36:45.063Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584095 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-10-06T09:39:04.199Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584096,0,'RecipientVATNo',TO_TIMESTAMP('2025-10-06 09:39:03.388000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Recipient-VAT-No','Recipient-VAT-No',TO_TIMESTAMP('2025-10-06 09:39:03.388000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:39:04.265Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584096 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Value: Intrastat_Export
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-10-06T09:41:44.895Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.Intrastat_Export(@C_Year_ID/-1@, @C_Period_ID/-1@, ''@IntrastatDeclarationType@'', ''@IntrastaNatureOfTransaction@'')',Updated=TO_TIMESTAMP('2025-10-06 09:41:44.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585508
;

-- Value: Intrastat_Export
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-10-06T09:43:24.618Z
UPDATE AD_Process SET CSVFieldDelimiter='	',Updated=TO_TIMESTAMP('2025-10-06 09:43:24.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585508
;

