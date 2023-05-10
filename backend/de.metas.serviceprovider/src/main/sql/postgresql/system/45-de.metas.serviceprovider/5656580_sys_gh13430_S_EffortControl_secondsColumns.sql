-- Column: S_EffortControl.IsIssueClosed
-- Column SQL (old): (CASE             WHEN (SELECT COUNT(*)                   from S_Issue s                   where s.iseffortissue = 'Y'                     and s.c_activity_id = S_EffortControl.c_activity_id                     and s.c_project_id = S_EffortControl.c_project_id                     and s.ad_org_id = S_EffortControl.ad_org_id                     and s.status in ('Invoiced')) = 0 THEN 'N'             ELSE (CASE                       WHEN (SELECT COUNT(*)                             from S_Issue iss                             where iss.iseffortissue = 'Y'                               and iss.c_activity_id = S_EffortControl.c_activity_id                               and iss.c_project_id = S_EffortControl.c_project_id                               and iss.ad_org_id = S_EffortControl.ad_org_id                               and iss.status not in ('Invoiced')) > 0 THEN 'N'                       ELSE 'Y' END) END)
-- 2022-09-15T16:48:28.310Z
UPDATE AD_Column SET ColumnSQL='(coalesce((select case when max(status) = min(status) and max(status) = ''Invoiced'' then ''Y'' else ''N'' end                   from s_issue iss                   where iss.iseffortissue = ''Y''                     and iss.c_activity_id = S_EffortControl.c_activity_id                     and iss.c_project_id = S_EffortControl.c_project_id                     and iss.ad_org_id = S_EffortControl.ad_org_id                   group by iss.c_activity_id, iss.c_project_id, iss.ad_org_id), ''N''))',Updated=TO_TIMESTAMP('2022-09-15 19:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584346
;

-- 2022-09-15T16:48:42.108Z
INSERT INTO t_alter_column values('s_effortcontrol','IsIssueClosed','CHAR(1)',null,'N')
;

-- 2022-09-15T16:51:14.310Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581462,0,'PendingEffortSumSeconds',TO_TIMESTAMP('2022-09-15 19:51:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','PendingEffortSumSeconds','PendingEffortSumSeconds',TO_TIMESTAMP('2022-09-15 19:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T16:51:14.317Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581462 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-09-15T16:51:34.892Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581463,0,'EffortSumSeconds',TO_TIMESTAMP('2022-09-15 19:51:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','EffortSumSeconds','EffortSumSeconds',TO_TIMESTAMP('2022-09-15 19:51:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T16:51:34.897Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581463 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: S_EffortControl.EffortSumSeconds
-- 2022-09-15T16:51:55.312Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584354,581463,0,22,542215,'EffortSumSeconds',TO_TIMESTAMP('2022-09-15 19:51:55','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.serviceprovider',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'EffortSumSeconds',0,0,TO_TIMESTAMP('2022-09-15 19:51:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-15T16:51:55.316Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584354 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-15T16:51:55.323Z
/* DDL */  select update_Column_Translation_From_AD_Element(581463) 
;

-- 2022-09-15T16:51:57.559Z
/* DDL */ SELECT public.db_alter_table('S_EffortControl','ALTER TABLE public.S_EffortControl ADD COLUMN EffortSumSeconds NUMERIC')
;

-- Column: S_EffortControl.PendingEffortSumSeconds
-- 2022-09-15T16:52:12.138Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584355,581462,0,22,542215,'PendingEffortSumSeconds',TO_TIMESTAMP('2022-09-15 19:52:11','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.serviceprovider',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'PendingEffortSumSeconds',0,0,TO_TIMESTAMP('2022-09-15 19:52:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-15T16:52:12.141Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584355 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-15T16:52:12.149Z
/* DDL */  select update_Column_Translation_From_AD_Element(581462) 
;

-- 2022-09-15T16:52:13.358Z
/* DDL */ SELECT public.db_alter_table('S_EffortControl','ALTER TABLE public.S_EffortControl ADD COLUMN PendingEffortSumSeconds NUMERIC')
;

-- Field: Effort control(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> Invoiceable effort
-- Column: S_Issue.InvoiceableEffort
-- 2022-09-16T05:44:57.115Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570628,707308,0,546632,TO_TIMESTAMP('2022-09-16 08:44:54','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Invoiceable effort',TO_TIMESTAMP('2022-09-16 08:44:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-16T05:44:57.121Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707308 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-16T05:44:57.130Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577681)
;

-- 2022-09-16T05:44:57.151Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707308
;

-- 2022-09-16T05:44:57.161Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707308)
;

-- UI Element: Effort control(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> main -> 10 -> default.Abrechenb. Kind-Issues
-- Column: S_Issue.InvoiceableChildEffort
-- 2022-09-16T05:45:15.861Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612999
;

-- UI Element: Effort control(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> main -> 10 -> default.Invoiceable effort
-- Column: S_Issue.InvoiceableEffort
-- 2022-09-16T05:45:27.671Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707308,0,546632,613010,549931,'F',TO_TIMESTAMP('2022-09-16 08:45:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Invoiceable effort',50,0,0,TO_TIMESTAMP('2022-09-16 08:45:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Effort control(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> main -> 10 -> default.Abrechenb. Kind-Issues
-- Column: S_Issue.InvoiceableChildEffort
-- 2022-09-16T05:45:50.884Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613005
;

-- UI Element: Effort control(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> main -> 10 -> default.Budgetiert
-- Column: S_Issue.BudgetedEffort
-- 2022-09-16T05:47:53.964Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2022-09-16 08:47:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613003
;

-- UI Element: Effort control(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> main -> 10 -> default.Issue effort (H:mm)
-- Column: S_Issue.IssueEffort
-- 2022-09-16T05:47:59.199Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2022-09-16 08:47:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613004
;

