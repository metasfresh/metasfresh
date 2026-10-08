
-- Value: DATEV_Export_Materdata
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-20T14:11:07.103Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585533,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess',TO_TIMESTAMP('2025-11-20 14:11:07.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y','DATEV Export Masterdata','json','N','N','csv','Excel',TO_TIMESTAMP('2025-11-20 14:11:07.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DATEV_Export_Materdata')
;

-- 2025-11-20T14:11:07.105Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585533 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: DATEV_Export_Materdata
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-20T14:11:47.825Z
UPDATE AD_Process SET SQLStatement='select * from export_datev_csv_masterdata(
        ''/tmp/'', ''creditors'', 1000000, ''Y'', ''2023-01-01'', ''2025-12-31''
              );',Updated=TO_TIMESTAMP('2025-11-20 14:11:47.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585533
;

-- 2025-11-20T14:16:42.752Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584247,0,TO_TIMESTAMP('2025-11-20 14:16:42.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','PartnerType','Partner Type',TO_TIMESTAMP('2025-11-20 14:16:42.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T14:16:42.753Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584247 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-20T14:16:45.304Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 14:16:45.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584247
;

-- 2025-11-20T14:16:45.326Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584247,'de_DE')
;

-- 2025-11-20T14:17:03.289Z
UPDATE AD_Element SET Name='Partner Type',Updated=TO_TIMESTAMP('2025-11-20 14:17:03.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584247
;

-- 2025-11-20T14:17:03.289Z
UPDATE AD_Element_Trl trl SET Name='Partner Type' WHERE AD_Element_ID=584247 AND AD_Language='de_DE'
;

-- 2025-11-20T14:17:03.290Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584247,'de_DE')
;

-- 2025-11-20T14:17:26.227Z
UPDATE AD_Element SET ColumnName='PartnerType',Updated=TO_TIMESTAMP('2025-11-20 14:17:26.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584247
;

-- 2025-11-20T14:17:26.227Z
UPDATE AD_Column SET ColumnName='PartnerType' WHERE AD_Element_ID=584247
;

-- 2025-11-20T14:17:26.228Z
UPDATE AD_Process_Para SET ColumnName='PartnerType' WHERE AD_Element_ID=584247
;

-- 2025-11-20T14:17:26.228Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584247,'de_DE')
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PartnerType
-- 2025-11-20T14:17:47.262Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584247,0,585533,543046,17,'PartnerType',TO_TIMESTAMP('2025-11-20 14:17:47.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U',0,'Y','N','Y','N','N','N','Partner Type',10,TO_TIMESTAMP('2025-11-20 14:17:47.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T14:17:47.262Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543046 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Name: PartnerType
-- 2025-11-20T14:17:56.806Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542036,TO_TIMESTAMP('2025-11-20 14:17:56.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','N','PartnerType',TO_TIMESTAMP('2025-11-20 14:17:56.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-11-20T14:17:56.807Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542036 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Name: PartnerType
-- 2025-11-20T14:17:59.825Z
UPDATE AD_Reference SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 14:17:59.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542036
;

-- Reference: PartnerType
-- Value: Debitors
-- ValueName: Debitors
-- 2025-11-20T14:18:09.519Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542036,544083,TO_TIMESTAMP('2025-11-20 14:18:09.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Debitors',TO_TIMESTAMP('2025-11-20 14:18:09.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Debitors','Debitors')
;

-- 2025-11-20T14:18:09.520Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544083 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PartnerType
-- Value: Debitors
-- ValueName: Debitors
-- 2025-11-20T14:18:11.664Z
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 14:18:11.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544083
;

-- Reference: PartnerType
-- Value: Creditors
-- ValueName: Creditors
-- 2025-11-20T14:18:20.032Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542036,544084,TO_TIMESTAMP('2025-11-20 14:18:20.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Creditors',TO_TIMESTAMP('2025-11-20 14:18:20.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Creditors','Creditors')
;

-- 2025-11-20T14:18:20.032Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544084 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PartnerType
-- Value: Creditors
-- ValueName: Creditors
-- 2025-11-20T14:18:23.834Z
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 14:18:23.833000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544084
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PartnerType
-- 2025-11-20T14:18:57.739Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=542036,Updated=TO_TIMESTAMP('2025-11-20 14:18:57.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543046
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PartnerType
-- 2025-11-20T14:19:00.812Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 14:19:00.812000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543046
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PartnerType
-- 2025-11-20T14:19:38.641Z
UPDATE AD_Process_Para SET DefaultValue='Debitors',Updated=TO_TIMESTAMP('2025-11-20 14:19:38.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543046
;

-- Value: DATEV_Export_Materdata
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-20T14:21:27.907Z
UPDATE AD_Process SET SQLStatement='select * from export_datev_csv_masterdata(''@PartnerType@'',  1000000, ''Y'', ''2023-01-01'', ''2025-12-31''
              );',Updated=TO_TIMESTAMP('2025-11-20 14:21:27.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585533
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PARAM_AD_Org_ID
-- 2025-11-20T14:22:18.246Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,579153,0,585533,543047,19,'PARAM_AD_Org_ID',TO_TIMESTAMP('2025-11-20 14:22:18.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U',0,'Y','N','Y','N','N','N','Sektion',20,TO_TIMESTAMP('2025-11-20 14:22:18.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T14:22:18.246Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543047 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PARAM_AD_Org_ID
-- 2025-11-20T14:22:34.706Z
UPDATE AD_Process_Para SET DefaultValue='1000000',Updated=TO_TIMESTAMP('2025-11-20 14:22:34.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543047
;

-- Value: DATEV_Export_Materdata
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-20T14:22:54.890Z
UPDATE AD_Process SET SQLStatement='select * from export_datev_csv_masterdata(''@PartnerType@'',  @PARAM_AD_Org_ID@, ''Y'', ''2023-01-01'', ''2025-12-31''
              );',Updated=TO_TIMESTAMP('2025-11-20 14:22:54.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585533
;

-- 2025-11-20T14:25:38.729Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584249,0,'PARAM_IsSOTrx',TO_TIMESTAMP('2025-11-20 14:25:38.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Verkaufstransaktion','Verkaufstransaktion',TO_TIMESTAMP('2025-11-20 14:25:38.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T14:25:38.729Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584249 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-20T14:25:41.424Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 14:25:41.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584249
;

-- 2025-11-20T14:25:41.426Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584249,'de_DE')
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PARAM_IsSOTrx
-- 2025-11-20T14:26:10.663Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584249,0,585533,543048,20,'PARAM_IsSOTrx',TO_TIMESTAMP('2025-11-20 14:26:10.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U',0,'Y','N','Y','N','N','N','Verkaufstransaktion',30,TO_TIMESTAMP('2025-11-20 14:26:10.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T14:26:10.664Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543048 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PARAM_IsSOTrx
-- 2025-11-20T14:27:05.679Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 14:27:05.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543048
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PARAM_IsSOTrx
-- 2025-11-20T14:27:14.469Z
UPDATE AD_Process_Para SET DefaultValue='''Y''',Updated=TO_TIMESTAMP('2025-11-20 14:27:14.469000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543048
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PARAM_IsSOTrx
-- 2025-11-20T14:27:24.431Z
UPDATE AD_Process_Para SET DefaultValue='Y',Updated=TO_TIMESTAMP('2025-11-20 14:27:24.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543048
;

-- Value: DATEV_Export_Materdata
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-20T14:28:10.150Z
UPDATE AD_Process SET SQLStatement='select * from export_datev_csv_masterdata(''@PartnerType@'',  @PARAM_AD_Org_ID@, 1@Param_IsSOTrx@1, ''2023-01-01'', ''2025-12-31''
              );',Updated=TO_TIMESTAMP('2025-11-20 14:28:10.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585533
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateFrom
-- 2025-11-20T14:29:08.244Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,585533,543050,15,'DateFrom',TO_TIMESTAMP('2025-11-20 14:29:08.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Startdatum eines Abschnittes','U',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','N','N','Datum von',40,TO_TIMESTAMP('2025-11-20 14:29:08.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T14:29:08.245Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543050 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateFrom
-- 2025-11-20T14:29:15.022Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 14:29:15.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543050
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateTo
-- 2025-11-20T14:29:28.539Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,585533,543051,15,'DateTo',TO_TIMESTAMP('2025-11-20 14:29:28.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Enddatum eines Abschnittes','U',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','N','N','Datum bis',50,TO_TIMESTAMP('2025-11-20 14:29:28.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T14:29:28.540Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543051 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateTo
-- 2025-11-20T14:29:34.110Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 14:29:34.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543051
;

-- Value: DATEV_Export_Materdata
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-20T14:31:58.150Z
UPDATE AD_Process SET SQLStatement='select * from export_datev_csv_masterdata(''@PartnerType@'',  @PARAM_AD_Org_ID@, ''@Param_IsSOTrx@'', ''@DateFrom/1970-01-01@'', ''@DateTo/2999-12-31@''
              );',Updated=TO_TIMESTAMP('2025-11-20 14:31:58.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585533
;



-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- Table: DATEV_Export
-- EntityType: D
-- 2025-11-20T14:35:03.559Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585533,540934,541587,TO_TIMESTAMP('2025-11-20 14:35:03.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-11-20 14:35:03.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;




-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PARAM_AD_Org_ID
-- 2025-11-20T14:45:13.256Z
UPDATE AD_Process_Para SET AD_Element_ID=NULL,Updated=TO_TIMESTAMP('2025-11-20 14:45:13.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543047
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PARAM_AD_Org_ID
-- 2025-11-20T14:45:20.741Z
UPDATE AD_Process_Para SET AD_Element_ID=579153,Updated=TO_TIMESTAMP('2025-11-20 14:45:20.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543047
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PARAM_AD_Org_ID
-- 2025-11-20T14:45:25.953Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2025-11-20 14:45:25.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543047
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PARAM_AD_Org_ID
-- 2025-11-20T14:45:27.119Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=276,Updated=TO_TIMESTAMP('2025-11-20 14:45:27.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543047
;



-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PARAM_AD_Org_ID
-- 2025-11-20T14:46:38.344Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 14:46:38.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543047
;

-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PARAM_AD_Org_ID
-- 2025-11-20T14:46:44.248Z
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2025-11-20 14:46:44.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543047
;

-- Value: DATEV_Export_Materdata
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-20T14:50:00.767Z
UPDATE AD_Process SET SQLStatement='select * from export_datev_csv_masterdata(''@PartnerType@'',  @PARAM_AD_Org_ID@, ''@PARAM_IsSOTrx@'', ''@DateFrom/1970-01-01@'', ''@DateTo/2999-12-31@''
              );',Updated=TO_TIMESTAMP('2025-11-20 14:50:00.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585533
;


-- Reference: PartnerType
-- Value: creditors
-- ValueName: Creditors
-- 2025-11-20T15:27:28.782Z
UPDATE AD_Ref_List SET Value='creditors',Updated=TO_TIMESTAMP('2025-11-20 15:27:28.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544084
;

-- Reference: PartnerType
-- Value: debitors
-- ValueName: Debitors
-- 2025-11-20T15:27:36.182Z
UPDATE AD_Ref_List SET Value='debitors',Updated=TO_TIMESTAMP('2025-11-20 15:27:36.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544083
;



-- Process: DATEV_Export_Materdata(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PartnerType
-- 2025-11-20T15:36:56.106Z
UPDATE AD_Process_Para SET DefaultValue='debitors',Updated=TO_TIMESTAMP('2025-11-20 15:36:56.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543046
;

-- Value: DATEV_Export_Materdata
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-20T15:41:42.579Z
UPDATE AD_Process SET CSVFieldDelimiter=';',Updated=TO_TIMESTAMP('2025-11-20 15:41:42.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585533
;














-- 2025-11-20T15:00:21.178Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES 
(0,584250,0,TO_TIMESTAMP('2025-11-20 15:00:21.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Konto','Konto',TO_TIMESTAMP('2025-11-20 15:00:21.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T15:00:21.187Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584250 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-20T15:00:37.917Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
 VALUES (0,584251,0,TO_TIMESTAMP('2025-11-20 15:00:37.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Matchcode','Matchcode',TO_TIMESTAMP('2025-11-20 15:00:37.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T15:00:37.919Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584251 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-20T15:00:47.937Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) 
VALUES (0,584252,0,TO_TIMESTAMP('2025-11-20 15:00:47.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Name1','Name1',TO_TIMESTAMP('2025-11-20 15:00:47.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T15:00:47.946Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584252 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-20T15:00:56.345Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) 
VALUES (0,584253,0,TO_TIMESTAMP('2025-11-20 15:00:56.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Strasse','Strasse',TO_TIMESTAMP('2025-11-20 15:00:56.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T15:00:56.346Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584253 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-20T15:01:03.626Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
 VALUES (0,584254,0,TO_TIMESTAMP('2025-11-20 15:01:03.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Plz','Plz',TO_TIMESTAMP('2025-11-20 15:01:03.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T15:01:03.627Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584254 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-20T15:01:10.498Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) 
VALUES (0,584255,0,TO_TIMESTAMP('2025-11-20 15:01:10.497000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Ort','Ort',TO_TIMESTAMP('2025-11-20 15:01:10.497000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T15:01:10.498Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584255 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-20T15:01:16.754Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
 VALUES (0,584256,0,TO_TIMESTAMP('2025-11-20 15:01:16.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Land','Land',TO_TIMESTAMP('2025-11-20 15:01:16.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T15:01:16.754Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584256 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-20T15:01:24.860Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) 
VALUES (0,584257,0,TO_TIMESTAMP('2025-11-20 15:01:24.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Telefon','Telefon',TO_TIMESTAMP('2025-11-20 15:01:24.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T15:01:24.861Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584257 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-20T15:01:31.492Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
 VALUES (0,584258,0,TO_TIMESTAMP('2025-11-20 15:01:31.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Email','Email',TO_TIMESTAMP('2025-11-20 15:01:31.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T15:01:31.493Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584258 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-20T15:01:42.332Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) 
VALUES (0,584259,0,TO_TIMESTAMP('2025-11-20 15:01:42.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','UstId','UstId',TO_TIMESTAMP('2025-11-20 15:01:42.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T15:01:42.332Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584259 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-20T15:01:48.588Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
 VALUES (0,584260,0,TO_TIMESTAMP('2025-11-20 15:01:48.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Zahlungsbed','Zahlungsbed',TO_TIMESTAMP('2025-11-20 15:01:48.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T15:01:48.589Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584260 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;






-- 2025-11-20T15:46:24.969Z
UPDATE AD_Element SET ColumnName='Konto',Updated=TO_TIMESTAMP('2025-11-20 15:46:24.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584250
;

-- 2025-11-20T15:46:24.970Z
UPDATE AD_Column SET ColumnName='Konto' WHERE AD_Element_ID=584250
;

-- 2025-11-20T15:46:24.970Z
UPDATE AD_Process_Para SET ColumnName='Konto' WHERE AD_Element_ID=584250
;

-- 2025-11-20T15:46:24.971Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584250,'de_DE')
;

-- 2025-11-20T15:46:27.725Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 15:46:27.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584250
;

-- 2025-11-20T15:46:27.726Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584250,'de_DE')
;

-- 2025-11-20T15:46:42.348Z
UPDATE AD_Element SET ColumnName='Matchcode',Updated=TO_TIMESTAMP('2025-11-20 15:46:42.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584251
;

-- 2025-11-20T15:46:42.357Z
UPDATE AD_Column SET ColumnName='Matchcode' WHERE AD_Element_ID=584251
;

-- 2025-11-20T15:46:42.357Z
UPDATE AD_Process_Para SET ColumnName='Matchcode' WHERE AD_Element_ID=584251
;

-- 2025-11-20T15:46:42.358Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584251,'de_DE')
;

-- 2025-11-20T15:46:44.083Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 15:46:44.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584251
;

-- 2025-11-20T15:46:44.084Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584251,'de_DE')
;

-- 2025-11-20T15:47:36.922Z
UPDATE AD_Element SET ColumnName='Name1',Updated=TO_TIMESTAMP('2025-11-20 15:47:36.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584252
;

-- 2025-11-20T15:47:36.923Z
UPDATE AD_Column SET ColumnName='Name1' WHERE AD_Element_ID=584252
;

-- 2025-11-20T15:47:36.923Z
UPDATE AD_Process_Para SET ColumnName='Name1' WHERE AD_Element_ID=584252
;

-- 2025-11-20T15:47:36.924Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584252,'de_DE')
;

-- 2025-11-20T15:47:39.434Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 15:47:39.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584252
;

-- 2025-11-20T15:47:39.435Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584252,'de_DE')
;

-- 2025-11-20T15:47:54.314Z
UPDATE AD_Element SET ColumnName='Strasse',Updated=TO_TIMESTAMP('2025-11-20 15:47:54.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584253
;

-- 2025-11-20T15:47:54.315Z
UPDATE AD_Column SET ColumnName='Strasse' WHERE AD_Element_ID=584253
;

-- 2025-11-20T15:47:54.315Z
UPDATE AD_Process_Para SET ColumnName='Strasse' WHERE AD_Element_ID=584253
;

-- 2025-11-20T15:47:54.315Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584253,'de_DE')
;

-- 2025-11-20T15:47:58.319Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 15:47:58.319000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584253
;

-- 2025-11-20T15:47:58.320Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584253,'de_DE')
;

-- 2025-11-20T15:48:12.953Z
UPDATE AD_Element SET ColumnName='Plz',Updated=TO_TIMESTAMP('2025-11-20 15:48:12.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584254
;

-- 2025-11-20T15:48:12.962Z
UPDATE AD_Column SET ColumnName='Plz' WHERE AD_Element_ID=584254
;

-- 2025-11-20T15:48:12.962Z
UPDATE AD_Process_Para SET ColumnName='Plz' WHERE AD_Element_ID=584254
;

-- 2025-11-20T15:48:12.963Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584254,'de_DE')
;

-- 2025-11-20T15:48:15.343Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 15:48:15.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584254
;

-- 2025-11-20T15:48:15.344Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584254,'de_DE')
;

-- 2025-11-20T15:49:11.547Z
UPDATE AD_Element SET ColumnName='Ort',Updated=TO_TIMESTAMP('2025-11-20 15:49:11.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584255
;

-- 2025-11-20T15:49:11.547Z
UPDATE AD_Column SET ColumnName='Ort' WHERE AD_Element_ID=584255
;

-- 2025-11-20T15:49:11.547Z
UPDATE AD_Process_Para SET ColumnName='Ort' WHERE AD_Element_ID=584255
;

-- 2025-11-20T15:49:11.548Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584255,'de_DE')
;

-- 2025-11-20T15:49:14.730Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 15:49:14.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584255
;

-- 2025-11-20T15:49:14.731Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584255,'de_DE')
;

-- 2025-11-20T15:49:38.132Z
UPDATE AD_Element SET ColumnName='Land',Updated=TO_TIMESTAMP('2025-11-20 15:49:38.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584256
;

-- 2025-11-20T15:49:38.141Z
UPDATE AD_Column SET ColumnName='Land' WHERE AD_Element_ID=584256
;

-- 2025-11-20T15:49:38.141Z
UPDATE AD_Process_Para SET ColumnName='Land' WHERE AD_Element_ID=584256
;

-- 2025-11-20T15:49:38.142Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584256,'de_DE')
;

-- 2025-11-20T15:49:40.397Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 15:49:40.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584256
;

-- 2025-11-20T15:49:40.398Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584256,'de_DE')
;

-- 2025-11-20T15:49:55.523Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 15:49:55.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584257
;

-- 2025-11-20T15:49:55.524Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584257,'de_DE')
;

-- 2025-11-20T15:49:58.841Z
UPDATE AD_Element SET ColumnName='Telefon',Updated=TO_TIMESTAMP('2025-11-20 15:49:58.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584257
;

-- 2025-11-20T15:49:58.841Z
UPDATE AD_Column SET ColumnName='Telefon' WHERE AD_Element_ID=584257
;

-- 2025-11-20T15:49:58.841Z
UPDATE AD_Process_Para SET ColumnName='Telefon' WHERE AD_Element_ID=584257
;

-- 2025-11-20T15:49:58.842Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584257,'de_DE')
;

-- 2025-11-20T15:51:16.805Z
UPDATE AD_Element SET ColumnName='UstId',Updated=TO_TIMESTAMP('2025-11-20 15:51:16.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584259
;

-- 2025-11-20T15:51:16.805Z
UPDATE AD_Column SET ColumnName='UstId' WHERE AD_Element_ID=584259
;

-- 2025-11-20T15:51:16.805Z
UPDATE AD_Process_Para SET ColumnName='UstId' WHERE AD_Element_ID=584259
;

-- 2025-11-20T15:51:16.806Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584259,'de_DE')
;

-- 2025-11-20T15:51:20.523Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 15:51:20.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584259
;

-- 2025-11-20T15:51:20.524Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584259,'de_DE')
;

-- 2025-11-20T15:51:38.244Z
UPDATE AD_Element SET ColumnName='Zahlungsbed',Updated=TO_TIMESTAMP('2025-11-20 15:51:38.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584260
;

-- 2025-11-20T15:51:38.244Z
UPDATE AD_Column SET ColumnName='Zahlungsbed' WHERE AD_Element_ID=584260
;

-- 2025-11-20T15:51:38.244Z
UPDATE AD_Process_Para SET ColumnName='Zahlungsbed' WHERE AD_Element_ID=584260
;

-- 2025-11-20T15:51:38.245Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584260,'de_DE')
;

-- 2025-11-20T15:51:40.623Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 15:51:40.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584260
;

-- 2025-11-20T15:51:40.623Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584260,'de_DE')
;



-- 2025-11-20T15:54:33.980Z
UPDATE AD_Element SET ColumnName='Email_Datev',Updated=TO_TIMESTAMP('2025-11-20 15:54:33.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584258
;

-- 2025-11-20T15:54:33.980Z
UPDATE AD_Column SET ColumnName='Email_Datev' WHERE AD_Element_ID=584258
;

-- 2025-11-20T15:54:33.980Z
UPDATE AD_Process_Para SET ColumnName='Email_Datev' WHERE AD_Element_ID=584258
;

-- 2025-11-20T15:54:33.981Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584258,'de_DE')
;

-- 2025-11-20T15:54:36.024Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-20 15:54:36.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584258
;

-- 2025-11-20T15:54:36.025Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584258,'de_DE')
;
