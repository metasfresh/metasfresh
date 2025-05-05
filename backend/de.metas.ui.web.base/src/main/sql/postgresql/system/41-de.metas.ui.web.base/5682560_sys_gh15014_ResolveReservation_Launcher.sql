-- 2023-03-27T06:14:07.257Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582168,0,'ResolvedHours',TO_TIMESTAMP('2023-03-27 09:14:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Resolved hours','Resolved hours',TO_TIMESTAMP('2023-03-27 09:14:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T06:14:07.272Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582168 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-27T10:20:30.273Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582171,0,'ReservationOrderAvailable',TO_TIMESTAMP('2023-03-27 13:20:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Reservation order available','Reservation order available',TO_TIMESTAMP('2023-03-27 13:20:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T10:20:30.276Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582171 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-27T10:20:51.497Z
UPDATE AD_Element SET ColumnName='IsReservationOrderAvailable',Updated=TO_TIMESTAMP('2023-03-27 13:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582171
;

-- 2023-03-27T10:20:51.583Z
UPDATE AD_Column SET ColumnName='IsReservationOrderAvailable', Name='Reservation order available', Description=NULL, Help=NULL WHERE AD_Element_ID=582171
;

-- 2023-03-27T10:20:51.584Z
UPDATE AD_Process_Para SET ColumnName='IsReservationOrderAvailable', Name='Reservation order available', Description=NULL, Help=NULL, AD_Element_ID=582171 WHERE UPPER(ColumnName)='ISRESERVATIONORDERAVAILABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-27T10:20:51.601Z
UPDATE AD_Process_Para SET ColumnName='IsReservationOrderAvailable', Name='Reservation order available', Description=NULL, Help=NULL WHERE AD_Element_ID=582171 AND IsCentrallyMaintained='Y'
;

-- Column: C_Project.IsReservationOrderAvailable
-- 2023-03-27T10:23:31.343Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586365,582171,0,20,203,'IsReservationOrderAvailable','CASE  WHEN     (SELECT count(*) from c_project p     where C_Project.C_Project_Parent_ID = p.c_project_parent_id     AND p.c_projecttype_id = 540011)     <> 0 THEN ''Y''            ELSE ''N'' END;',TO_TIMESTAMP('2023-03-27 13:23:31','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Reservation order available',0,0,TO_TIMESTAMP('2023-03-27 13:23:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-27T10:23:31.347Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586365 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-27T10:23:31.356Z
/* DDL */  select update_Column_Translation_From_AD_Element(582171) 
;



-- Column: C_Project.IsReservationOrderAvailable
-- 2023-03-27T10:25:55.200Z
UPDATE AD_Column SET ColumnSQL='CASE  WHEN     (SELECT count(*) from c_project p     where C_Project.C_Project_Parent_ID = p.c_project_parent_id     AND p.c_projecttype_id = 540011)     <> 0 THEN ''Y''            ELSE ''N'' END',Updated=TO_TIMESTAMP('2023-03-27 13:25:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586365
;


-- 2023-03-27T10:30:01.654Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582172,0,'ReservationOpen',TO_TIMESTAMP('2023-03-27 13:30:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Reservation open','Reservation open',TO_TIMESTAMP('2023-03-27 13:30:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T10:30:01.657Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582172 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project.ReservationOpen
-- 2023-03-27T10:30:29.016Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586366,582172,0,11,203,'ReservationOpen',TO_TIMESTAMP('2023-03-27 13:30:28','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reservation open',0,0,TO_TIMESTAMP('2023-03-27 13:30:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-27T10:30:29.020Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586366 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Column: C_Project.ReservationOpen
-- 2023-03-27T10:53:42.936Z
UPDATE AD_Column SET ColumnSQL='select sum(c_project_wo_resource.duration)as totalSum from c_project c         join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id         where c.projectcategory = ''W''         and c_projecttype_id = 540011          and C_Project.C_Project_Parent_ID = c.project_parent_id)                                       - (select sum(step.woactualfacilityhours)as totalSum from c_project c         join c_project_wo_step step on c.c_project_id = step.c_project_id         where c.projectcategory = ''W''         and c_projecttype_id = 540011         and C_Project.C_Project_Parent_ID = c.c_project_parent_id', IsLazyLoading='Y', IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-03-27 13:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;


-- Column: C_Project.ReservationOpen
-- 2023-03-27T10:56:25.107Z
UPDATE AD_Column SET ColumnSQL='select sum(c_project_wo_resource.duration)as totalSum from c_project c         join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id         where c.projectcategory = ''W''         and c_projecttype_id = 540011          and C_Project.C_Project_Parent_ID = c.c_project_parent_id)                                       - (select sum(step.woactualfacilityhours)as totalSum from c_project c         join c_project_wo_step step on c.c_project_id = step.c_project_id         where c.projectcategory = ''W''         and c_projecttype_id = 540011         and C_Project.C_Project_Parent_ID = c.c_project_parent_id',Updated=TO_TIMESTAMP('2023-03-27 13:56:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;

-- Column: C_Project.ReservationOpen
-- 2023-03-27T10:59:10.721Z
UPDATE AD_Column SET ColumnSQL='select sum(c_project_wo_resource.duration)as totalSum from c_project c         join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id         where c.projectcategory = ''W''         and c_projecttype_id = 540011          and C_Project.C_Project_Parent_ID = c.c_project_parent_id)                                       - (select sum(step.resolvedhours)as totalSum from c_project c         join c_project_wo_step step on c.c_project_id = step.c_project_id         where c.projectcategory = ''W''         and c_projecttype_id = 540011         and C_Project.C_Project_Parent_ID = c.c_project_parent_id',Updated=TO_TIMESTAMP('2023-03-27 13:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;

-- Column: C_Project.ReservationOpen
-- 2023-03-27T11:47:21.901Z
UPDATE AD_Column SET ColumnSQL=' CASE            WHEN                C_Project.C_ProjectType_ID = 540007 THEN                ((select sum(c_project_wo_resource.duration) as totalSum                 from c_project c                   join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id                     where c.projectcategory = ''W''                      and c.c_projecttype_id = 540011                      and C_Project.C_Project_Parent_ID = c.c_project_parent_id)                   -                 (select sum(step.resolvedhours) as resolvedHours                  from c_project c                      join c_project_wo_step step on c.c_project_id = step.c_project_id                  where c.projectcategory = ''W''                    and c_projecttype_id = 540011                    and C_Project.C_Project_Parent_ID = c.c_project_parent_id))              WHEN C_Project.C_ProjectType_ID = 540011 THEN     ((select sum(c_project_wo_resource.duration) as totalSum       from c_project c       join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id)     - (select sum(step.resolvedhours) as resolvedHours                  from c_project c                      join c_project_wo_step step on c.c_project_id = step.c_project_id)) END',Updated=TO_TIMESTAMP('2023-03-27 14:47:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;


-- Column: C_Project.ReservationOpen
-- 2023-03-27T11:50:49.202Z
UPDATE AD_Column SET ColumnSQL='CASE            WHEN                C_Project.C_ProjectType_ID = 540007 THEN                ((select sum(c_project_wo_resource.duration) as totalSum                 from c_project c                   join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id                     where c.projectcategory = ''W''                      and c.c_projecttype_id = 540011                      and C_Project.C_Project_Parent_ID = c.c_project_parent_id)                   -                 (select sum(step.resolvedhours) as resolvedHours                  from c_project c                      join c_project_wo_step step on c.c_project_id = step.c_project_id                  where c.projectcategory = ''W''                    and c_projecttype_id = 540011                    and C_Project.C_Project_Parent_ID = c.c_project_parent_id))              WHEN C_Project.C_ProjectType_ID = 540011 THEN     ((select sum(c_project_wo_resource.duration) as totalSum       from c_project c       join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id       where C_Project.C_Project_ID = c.c_project_id)     - (select sum(step.resolvedhours) as resolvedHours                  from c_project c                      join c_project_wo_step step on c.c_project_id = step.c_project_id                  where C_Project.C_Project_ID = c.c_project_id)) END',Updated=TO_TIMESTAMP('2023-03-27 14:50:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;



-- 2023-03-28T05:50:01.006Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585245,'Y','de.metas.ui.web.project.step.process.C_Project_WO_Step_ResolveReservationView_Launcher','N',TO_TIMESTAMP('2023-03-28 08:50:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Resolve reservation from reservation order','json','N','N','xls','Java',TO_TIMESTAMP('2023-03-28 08:50:00','YYYY-MM-DD HH24:MI:SS'),100,'Resolve reservation from reservation order')
;

-- 2023-03-28T05:50:01.019Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585245 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2023-03-28T05:50:20.523Z
UPDATE AD_Process_Trl SET Name='Reservierung aus Reservierungsauftrag auflösen',Updated=TO_TIMESTAMP('2023-03-28 08:50:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585245
;

-- 2023-03-28T05:50:23.130Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Reservierung aus Reservierungsauftrag auflösen',Updated=TO_TIMESTAMP('2023-03-28 08:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585245
;

-- 2023-03-28T05:50:23.114Z
UPDATE AD_Process_Trl SET Name='Reservierung aus Reservierungsauftrag auflösen',Updated=TO_TIMESTAMP('2023-03-28 08:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585245
;

-- 2023-03-28T05:52:34.063Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585245,203,541373,TO_TIMESTAMP('2023-03-28 08:52:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-03-28 08:52:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;


-- 2023-03-28T07:31:51.110Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585246,'Y','de.metas.ui.web.project.step.process.C_Project_WO_Step_ResolveHours','N',TO_TIMESTAMP('2023-03-28 10:31:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Resolve hours','json','N','N','xls','Java',TO_TIMESTAMP('2023-03-28 10:31:50','YYYY-MM-DD HH24:MI:SS'),100,'Resolve hours')
;

-- 2023-03-28T07:31:51.127Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585246 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2023-03-28T07:31:54.436Z
UPDATE AD_Process_Trl SET Name='Stunden auflösen',Updated=TO_TIMESTAMP('2023-03-28 10:31:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585246
;

-- 2023-03-28T07:32:13.063Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Stunden auflösen',Updated=TO_TIMESTAMP('2023-03-28 10:32:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585246
;

-- 2023-03-28T07:32:13.045Z
UPDATE AD_Process_Trl SET Name='Stunden auflösen',Updated=TO_TIMESTAMP('2023-03-28 10:32:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585246
;

-- 2023-03-28T07:33:44.750Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582174,0,TO_TIMESTAMP('2023-03-28 10:33:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hours','Hours',TO_TIMESTAMP('2023-03-28 10:33:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-28T07:33:44.757Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582174 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-28T07:34:01.906Z
UPDATE AD_Element SET ColumnName='Hours',Updated=TO_TIMESTAMP('2023-03-28 10:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582174
;

-- 2023-03-28T07:34:01.910Z
UPDATE AD_Column SET ColumnName='Hours', Name='Hours', Description=NULL, Help=NULL WHERE AD_Element_ID=582174
;

-- 2023-03-28T07:34:01.913Z
UPDATE AD_Process_Para SET ColumnName='Hours', Name='Hours', Description=NULL, Help=NULL, AD_Element_ID=582174 WHERE UPPER(ColumnName)='HOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-28T07:34:01.916Z
UPDATE AD_Process_Para SET ColumnName='Hours', Name='Hours', Description=NULL, Help=NULL WHERE AD_Element_ID=582174 AND IsCentrallyMaintained='Y'
;


-- 2023-03-28T07:34:43.426Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582174,0,585246,542591,11,'Hours',TO_TIMESTAMP('2023-03-28 10:34:43','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','N','N','Hours',10,TO_TIMESTAMP('2023-03-28 10:34:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-28T07:34:43.429Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542591 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-03-28T07:35:02.175Z
UPDATE AD_Process_Para SET ValueMin='1',Updated=TO_TIMESTAMP('2023-03-28 10:35:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542591
;

-- 2023-03-28T07:38:32.514Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585247,'Y','de.metas.ui.web.project.step.process.C_Project_WO_Step_UndoResolution','N',TO_TIMESTAMP('2023-03-28 10:38:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Undo reservation resolution','json','N','N','xls','Java',TO_TIMESTAMP('2023-03-28 10:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Undo reservation resolution')
;

-- 2023-03-28T07:38:32.517Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585247 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2023-03-28T09:19:06.404Z
UPDATE AD_Process SET AccessLevel='1',Updated=TO_TIMESTAMP('2023-03-28 12:19:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585247
;

-- 2023-03-28T09:44:00.192Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,586365,0,540145,203,TO_TIMESTAMP('2023-03-28 12:43:59','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',582994,203,TO_TIMESTAMP('2023-03-28 12:43:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-28T09:44:28.398Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,586366,0,540146,203,TO_TIMESTAMP('2023-03-28 12:44:28','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',1349,203,TO_TIMESTAMP('2023-03-28 12:44:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-28T09:44:36.030Z
UPDATE AD_SQLColumn_SourceTableColumn SET Link_Column_ID=1349,Updated=TO_TIMESTAMP('2023-03-28 12:44:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540145
;

-- Column: C_Project_WO_Resource.ResolvedHours
-- 2023-03-29T09:11:54.981Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586374,582168,0,11,542161,'ResolvedHours',TO_TIMESTAMP('2023-03-29 12:11:54','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Resolved hours',0,0,TO_TIMESTAMP('2023-03-29 12:11:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-29T09:11:54.985Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586374 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-29T09:11:54.990Z
/* DDL */  select update_Column_Translation_From_AD_Element(582168)
;

-- 2023-03-29T09:11:55.598Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource','ALTER TABLE public.C_Project_WO_Resource ADD COLUMN ResolvedHours NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- Column: C_Project.ReservationOpen
-- 2023-03-29T11:12:17.502Z
UPDATE AD_Column SET ColumnSQL='CASE             WHEN     C_Project.C_ProjectType_ID = 540007     THEN      (select (select sum(c_project_wo_resource.duration- c_project_wo_resource.resolvedHours)            from c_project c      join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id      where c.projectcategory = ''W''      and c.c_projecttype_id = 540011      and C_Project.C_Project_Parent_ID = c.c_project_parent_id))       WHEN   C_Project.C_ProjectType_ID = 540011  THEN     (select (select sum(c_project_wo_resource.duration- c_project_wo_resource.resolvedHours)            from c_project c      join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id      where c.projectcategory = ''W''      and c.c_projecttype_id = 540011      and C_Project.C_Project_Parent_ID = c.c_project_parent_id      and C_Project.C_Project_ID = c.c_project_id))   END',Updated=TO_TIMESTAMP('2023-03-29 14:12:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;

-- 2023-03-31T06:43:30.568Z
UPDATE AD_SQLColumn_SourceTableColumn SET Link_Column_ID=582994,Updated=TO_TIMESTAMP('2023-03-31 09:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540145
;

-- 2023-03-31T06:48:09.092Z
UPDATE AD_SQLColumn_SourceTableColumn SET FetchTargetRecordsMethod='L', Link_Column_ID=1349, SQL_GetTargetRecordIdBySourceRecordId='',Updated=TO_TIMESTAMP('2023-03-31 09:48:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540145
;

-- 2023-03-31T06:49:00.330Z
UPDATE AD_SQLColumn_SourceTableColumn SET Link_Column_ID=583243, Source_Table_ID=542161,Updated=TO_TIMESTAMP('2023-03-31 09:49:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540146
;

-- 2023-03-31T07:24:39.505Z
UPDATE AD_SQLColumn_SourceTableColumn SET Source_Column_ID=583243,Updated=TO_TIMESTAMP('2023-03-31 10:24:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540146
;

-- 2023-03-31T08:13:19.785Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Source_Table_ID,SQL_GetTargetRecordIdBySourceRecordId,Updated,UpdatedBy) VALUES (0,586366,0,540147,203,TO_TIMESTAMP('2023-03-31 11:13:19','YYYY-MM-DD HH24:MI:SS'),100,'S','Y',542161,'select distinct (c1.c_project_id) from c_project c1 join c_project c2
    on c1.c_project_parent_id = c2.c_project_parent_id
         join c_project_wo_resource r on r.c_project_id = c2.c_project_id
    where c1.c_projecttype_id = 540007
and r.duration <>0 ',TO_TIMESTAMP('2023-03-31 11:13:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-31T08:19:25.642Z
UPDATE AD_SQLColumn_SourceTableColumn SET Link_Column_ID=583243, Source_Table_ID=542161,Updated=TO_TIMESTAMP('2023-03-31 11:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540145
;

-- 2023-03-31T08:23:01.715Z
UPDATE AD_SQLColumn_SourceTableColumn SET FetchTargetRecordsMethod='S', Link_Column_ID=NULL, Source_Table_ID=203, SQL_GetTargetRecordIdBySourceRecordId='select distinct (c1.c_project_id) from c_project c1 join c_project c2
    on c1.c_project_parent_id = c2.c_project_parent_id
    where c1.c_projecttype_id = 540007
      and c2.c_projecttype_id = 540011;',Updated=TO_TIMESTAMP('2023-03-31 11:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540145
;

-- 2023-03-31T08:43:51.920Z
UPDATE AD_SQLColumn_SourceTableColumn SET SQL_GetTargetRecordIdBySourceRecordId='select c_project_id from c_project where c_projecttype_id = 540007',Updated=TO_TIMESTAMP('2023-03-31 11:43:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540145
;

-- 2023-03-31T08:43:51.920Z
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2023-03-31 08:47:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585245
;

-- 2023-03-31T08:43:51.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET IsActive='N',Updated=TO_TIMESTAMP('2023-03-31 08:47:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585245 AND AD_Table_ID=203
;