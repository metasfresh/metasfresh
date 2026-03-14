-- Run mode: SWING_CLIENT

-- 2026-02-18T11:07:19.874Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584539,0,'ToleranceChanged',TO_TIMESTAMP('2026-02-18 11:07:19.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Toleranz geändert','Toleranz geändert',TO_TIMESTAMP('2026-02-18 11:07:19.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T11:07:19.884Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584539 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ToleranceChanged
-- 2026-02-18T11:07:39.078Z
UPDATE AD_Element_Trl SET Name='Tolerance Changed', PrintName='Tolerance Changed',Updated=TO_TIMESTAMP('2026-02-18 11:07:39.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584539 AND AD_Language='en_US'
;

-- 2026-02-18T11:07:39.079Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-18T11:07:39.413Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584539,'en_US')
;

-- 2026-02-18T11:08:55.032Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584540,0,'ToleranceChangedBy',TO_TIMESTAMP('2026-02-18 11:08:54.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Toleranz geändert von','Toleranz geändert von',TO_TIMESTAMP('2026-02-18 11:08:54.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T11:08:55.034Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584540 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ToleranceChangedBy
-- 2026-02-18T11:09:12.938Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Tolerance Changed By', PrintName='Tolerance Changed By',Updated=TO_TIMESTAMP('2026-02-18 11:09:12.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584540 AND AD_Language='en_US'
;

-- 2026-02-18T11:09:12.939Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-18T11:09:13.206Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584540,'en_US')
;

-- Column: PP_Order_BOMLine.ToleranceChanged
-- 2026-02-18T11:17:08.704Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592025,584539,0,16,53025,'ToleranceChanged',TO_TIMESTAMP('2026-02-18 11:17:08.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','EE01',7,'','Y','Y','N','N','N','N','N','N','N','N','Y','Toleranz geändert',0,TO_TIMESTAMP('2026-02-18 11:17:08.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-18T11:17:08.707Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592025 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-18T11:17:08.711Z
/* DDL */  select update_Column_Translation_From_AD_Element(584539)
;

-- 2026-02-18T11:17:15.919Z
/* DDL */ SELECT public.db_alter_table('PP_Order_BOMLine','ALTER TABLE public.PP_Order_BOMLine ADD COLUMN ToleranceChanged TIMESTAMP WITH TIME ZONE')
;

-- 2026-02-18T11:18:56.985Z
UPDATE AD_Element SET ColumnName='ToleranceChangedBy_ID',Updated=TO_TIMESTAMP('2026-02-18 11:18:56.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584540
;

-- 2026-02-18T11:18:56.989Z
UPDATE AD_Column SET ColumnName='ToleranceChangedBy_ID' WHERE AD_Element_ID=584540
;

-- 2026-02-18T11:18:56.990Z
UPDATE AD_Process_Para SET ColumnName='ToleranceChangedBy_ID' WHERE AD_Element_ID=584540
;

-- 2026-02-18T11:18:56.994Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584540,'de_DE')
;

-- Column: PP_Order_BOMLine.ToleranceChangedBy_ID
-- 2026-02-18T11:19:24.251Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592026,584540,0,30,110,53025,'ToleranceChangedBy_ID',TO_TIMESTAMP('2026-02-18 11:19:24.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','EE01',10,'','Y','Y','N','N','N','N','N','N','N','N','Y','Toleranz geändert von',0,TO_TIMESTAMP('2026-02-18 11:19:24.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-18T11:19:24.253Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592026 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-18T11:19:24.256Z
/* DDL */  select update_Column_Translation_From_AD_Element(584540)
;

-- 2026-02-18T11:19:48.892Z
/* DDL */ SELECT public.db_alter_table('PP_Order_BOMLine','ALTER TABLE public.PP_Order_BOMLine ADD COLUMN ToleranceChangedBy_ID NUMERIC(10)')
;

-- 2026-02-18T11:19:49.234Z
ALTER TABLE PP_Order_BOMLine ADD CONSTRAINT ToleranceChangedBy_PPOrderBOMLine FOREIGN KEY (ToleranceChangedBy_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Value: PP_Order_BOMLine_Update_Tolerance
-- Classname: de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance
-- 2026-02-18T11:37:29.902Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585571,'Y','de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance','N',TO_TIMESTAMP('2026-02-18 11:37:29.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Toleranz nachträglich ändern','json','N','N','xls','Java',TO_TIMESTAMP('2026-02-18 11:37:29.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PP_Order_BOMLine_Update_Tolerance')
;

-- 2026-02-18T11:37:29.906Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585571 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: PP_Order_BOMLine_Update_Tolerance(de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance)
-- 2026-02-18T11:37:38.232Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Change tolerance retroactively',Updated=TO_TIMESTAMP('2026-02-18 11:37:38.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585571
;

-- 2026-02-18T11:37:38.234Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: PP_Order_BOMLine_Update_Tolerance(de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance)
-- ParameterName: IsEnforceIssuingTolerance
-- 2026-02-18T11:38:34.782Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581751,0,585571,543117,20,'IsEnforceIssuingTolerance',TO_TIMESTAMP('2026-02-18 11:38:34.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',0,'Y','N','Y','N','N','N','Toleranz erzw.',10,TO_TIMESTAMP('2026-02-18 11:38:34.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T11:38:34.785Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543117 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_BOMLine_Update_Tolerance(de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance)
-- ParameterName: IssuingTolerance_ValueType
-- 2026-02-18T11:40:48.693Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581752,0,585571,543118,17,541693,'IssuingTolerance_ValueType',TO_TIMESTAMP('2026-02-18 11:40:48.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@IsEnforceIssuingTolerance/N@=Y','D',0,'Y','N','Y','N','N','N','Toleranzwert Art',20,TO_TIMESTAMP('2026-02-18 11:40:48.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T11:40:48.695Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543118 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_BOMLine_Update_Tolerance(de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance)
-- ParameterName: IssuingTolerance_Perc
-- 2026-02-18T11:42:50.376Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581754,0,585571,543119,22,'IssuingTolerance_Perc',TO_TIMESTAMP('2026-02-18 11:42:50.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=P','U',0,'Y','N','Y','N','N','N','Toleranz %',30,TO_TIMESTAMP('2026-02-18 11:42:50.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T11:42:50.378Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543119 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_BOMLine_Update_Tolerance(de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance)
-- ParameterName: IssuingTolerance_Perc
-- 2026-02-18T11:42:56.266Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2026-02-18 11:42:56.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543119
;

-- Process: PP_Order_BOMLine_Update_Tolerance(de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance)
-- ParameterName: IssuingTolerance_UOM_ID
-- 2026-02-18T11:44:34.251Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581757,0,585571,543120,30,541960,'IssuingTolerance_UOM_ID',TO_TIMESTAMP('2026-02-18 11:44:34.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q','U',0,'Y','N','Y','N','N','N','Toleranz Einheit','',40,TO_TIMESTAMP('2026-02-18 11:44:34.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T11:44:34.253Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543120 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_BOMLine_Update_Tolerance(de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance)
-- ParameterName: IssuingTolerance_Perc
-- 2026-02-18T11:45:39.592Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581754,0,585571,543121,22,'IssuingTolerance_Perc',TO_TIMESTAMP('2026-02-18 11:45:39.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=P','U',0,'Y','N','Y','N','N','N','Toleranz %',50,TO_TIMESTAMP('2026-02-18 11:45:39.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T11:45:39.594Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543121 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_BOMLine_Update_Tolerance(de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance)
-- ParameterName: IssuingTolerance_Qty
-- 2026-02-18T11:46:19.519Z
UPDATE AD_Process_Para SET AD_Element_ID=581756, ColumnName='IssuingTolerance_Qty', DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q', Name='Toleranz',Updated=TO_TIMESTAMP('2026-02-18 11:46:19.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543119
;

-- 2026-02-18T11:46:19.522Z
UPDATE AD_Process_Para_Trl trl SET Name='Toleranz' WHERE AD_Process_Para_ID=543119 AND AD_Language='de_DE'
;

-- Process: PP_Order_BOMLine_Update_Tolerance(de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance)
-- Table: PP_Product_BOMLine
-- EntityType: D
-- 2026-02-18T11:46:50.824Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585571,53019,541611,TO_TIMESTAMP('2026-02-18 11:46:50.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2026-02-18 11:46:50.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Process: PP_Order_BOMLine_Update_Tolerance(de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance)
-- Table: PP_Product_BOMLine
-- Tab: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01)
-- Window: Produktionsauftrag(53009,EE01)
-- EntityType: D
-- 2026-02-18T12:36:39.529Z
UPDATE AD_Table_Process SET AD_Tab_ID=53039, AD_Window_ID=53009,Updated=TO_TIMESTAMP('2026-02-18 12:36:39.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541611
;

-- Process: PP_Order_BOMLine_Update_Tolerance(de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance)
-- Table: PP_Product_BOMLine
-- Tab: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01)
-- Window: Produktionsauftrag(53009,EE01)
-- EntityType: D
-- 2026-02-18T13:29:43.767Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541611
;

-- Process: PP_Order_BOMLine_Update_Tolerance(de.metas.ui.web.pporder.process.PP_Order_BOMLine_Update_Tolerance)
-- Table: PP_Order_BOMLine
-- EntityType: D
-- 2026-02-18T13:29:58.299Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585571,53025,541612,TO_TIMESTAMP('2026-02-18 13:29:58.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2026-02-18 13:29:58.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Field: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01) -> Toleranz geändert von
-- Column: PP_Order_BOMLine.ToleranceChangedBy_ID
-- 2026-02-18T14:32:48.869Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592026,774166,0,53039,0,TO_TIMESTAMP('2026-02-18 14:32:48.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'@ToleranceChangedBy_ID/-1@>0','D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Toleranz geändert von',0,0,430,0,1,1,TO_TIMESTAMP('2026-02-18 14:32:48.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T14:32:48.875Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774166 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-18T14:32:48.900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584540)
;

-- 2026-02-18T14:32:48.912Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774166
;

-- 2026-02-18T14:32:48.916Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774166)
;

-- Field: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01) -> Toleranz geändert
-- Column: PP_Order_BOMLine.ToleranceChanged
-- 2026-02-18T14:33:01.974Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592025,774167,0,53039,0,TO_TIMESTAMP('2026-02-18 14:33:01.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'@ToleranceChangedBy_ID/-1@>0','D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Toleranz geändert',0,0,440,0,1,1,TO_TIMESTAMP('2026-02-18 14:33:01.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T14:33:01.977Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774167 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-18T14:33:01.979Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584539)
;

-- 2026-02-18T14:33:01.984Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774167
;

-- 2026-02-18T14:33:01.985Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774167)
;

-- Field: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01) -> Toleranz geändert
-- Column: PP_Order_BOMLine.ToleranceChanged
-- 2026-02-18T14:33:15.862Z
UPDATE AD_Field SET DisplayLogic='@ToleranceChanged/-1@>0',Updated=TO_TIMESTAMP('2026-02-18 14:33:15.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=774167
;

-- UI Element: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01) -> main -> 20 -> Issuing Tolerance.Toleranz geändert
-- Column: PP_Order_BOMLine.ToleranceChanged
-- 2026-02-18T14:33:39.297Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774167,0,53039,550061,648131,'F',TO_TIMESTAMP('2026-02-18 14:33:39.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Toleranz geändert',60,0,0,TO_TIMESTAMP('2026-02-18 14:33:39.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01) -> main -> 20 -> Issuing Tolerance.Toleranz geändert von
-- Column: PP_Order_BOMLine.ToleranceChangedBy_ID
-- 2026-02-18T14:33:45.642Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774166,0,53039,550061,648132,'F',TO_TIMESTAMP('2026-02-18 14:33:45.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Toleranz geändert von',70,0,0,TO_TIMESTAMP('2026-02-18 14:33:45.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

