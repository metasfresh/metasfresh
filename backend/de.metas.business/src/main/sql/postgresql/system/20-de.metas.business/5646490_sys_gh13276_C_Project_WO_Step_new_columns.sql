-- 2022-07-12T10:25:34.198Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581108,0,'WOPartialReportDate',TO_TIMESTAMP('2022-07-12 12:25:34','YYYY-MM-DD HH24:MI:SS'),100,'Datum Erstellung des Teilberichtes','D','Y','Datum Teilbericht','Datum Teilbericht',TO_TIMESTAMP('2022-07-12 12:25:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:25:34.200Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581108 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:25:37.152Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581108 AND AD_Language='de_CH'
;

-- 2022-07-12T10:25:37.154Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581108,'de_CH') 
;

-- 2022-07-12T10:25:39.505Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:25:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581108 AND AD_Language='de_DE'
;

-- 2022-07-12T10:25:39.507Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581108,'de_DE') 
;

-- 2022-07-12T10:25:39.517Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581108,'de_DE') 
;

-- 2022-07-12T10:26:02.316Z
UPDATE AD_Element_Trl SET Description='Date of the partial report', IsTranslated='Y', Name='Partial report date', PrintName='Partial report date',Updated=TO_TIMESTAMP('2022-07-12 12:26:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581108 AND AD_Language='en_US'
;

-- 2022-07-12T10:26:02.318Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581108,'en_US') 
;

-- Column: C_Project_WO_Step.WOPartialReportDate
-- 2022-07-12T10:26:18.563Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583634,581108,0,15,542159,'WOPartialReportDate',TO_TIMESTAMP('2022-07-12 12:26:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum Erstellung des Teilberichtes','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Datum Teilbericht',0,0,TO_TIMESTAMP('2022-07-12 12:26:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:26:18.565Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583634 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:26:18.569Z
/* DDL */  select update_Column_Translation_From_AD_Element(581108) 
;

-- 2022-07-12T10:26:19.289Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN WOPartialReportDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2022-07-12T10:29:00.273Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581109,0,'WOPlannedResourceDurationHours',TO_TIMESTAMP('2022-07-12 12:29:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SOLL-Anlagenstunden','SOLL-Anlagenstunden',TO_TIMESTAMP('2022-07-12 12:29:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:29:00.276Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581109 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:29:04.608Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:29:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581109 AND AD_Language='de_CH'
;

-- 2022-07-12T10:29:04.610Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581109,'de_CH') 
;

-- 2022-07-12T10:29:06.416Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:29:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581109 AND AD_Language='de_DE'
;

-- 2022-07-12T10:29:06.418Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581109,'de_DE') 
;

-- 2022-07-12T10:29:06.425Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581109,'de_DE') 
;

-- 2022-07-12T10:29:17.107Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='TARGET facility hours', PrintName='TARGET facility hours',Updated=TO_TIMESTAMP('2022-07-12 12:29:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581109 AND AD_Language='en_US'
;

-- 2022-07-12T10:29:17.109Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581109,'en_US') 
;

-- 2022-07-12T10:29:31.339Z
INSERT INTO t_alter_column values('c_project_wo_step','WOPartialReportDate','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- Column: C_Project_WO_Step.WOPlannedResourceDurationHours
-- 2022-07-12T10:29:56.759Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583635,581109,0,11,542159,'WOPlannedResourceDurationHours',TO_TIMESTAMP('2022-07-12 12:29:56','YYYY-MM-DD HH24:MI:SS'),100,'N','1','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'SOLL-Anlagenstunden',0,0,TO_TIMESTAMP('2022-07-12 12:29:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:29:56.760Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583635 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:29:56.766Z
/* DDL */  select update_Column_Translation_From_AD_Element(581109) 
;

-- 2022-07-12T10:29:58.267Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN WOPlannedResourceDurationHours NUMERIC(10) DEFAULT 1 NOT NULL')
;

-- 2022-07-12T10:31:10.831Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581110,0,'WODeliveryDate',TO_TIMESTAMP('2022-07-12 12:31:10','YYYY-MM-DD HH24:MI:SS'),100,'Anlieferdatum an Prüfanlage','D','Y','Anlieferdatum','Anlieferdatum',TO_TIMESTAMP('2022-07-12 12:31:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:31:10.833Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581110 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:31:13.591Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581110 AND AD_Language='de_CH'
;

-- 2022-07-12T10:31:13.593Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581110,'de_CH') 
;

-- 2022-07-12T10:31:15.463Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:31:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581110 AND AD_Language='de_DE'
;

-- 2022-07-12T10:31:15.465Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581110,'de_DE') 
;

-- 2022-07-12T10:31:15.472Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581110,'de_DE') 
;

-- 2022-07-12T10:31:35.380Z
UPDATE AD_Element_Trl SET Description='Delivery date to test facility', IsTranslated='Y', Name='Delivery date', PrintName='Delivery date',Updated=TO_TIMESTAMP('2022-07-12 12:31:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581110 AND AD_Language='en_US'
;

-- 2022-07-12T10:31:35.382Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581110,'en_US') 
;

-- Column: C_Project_WO_Step.WODeliveryDate
-- 2022-07-12T10:31:47.012Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583636,581110,0,15,542159,'WODeliveryDate',TO_TIMESTAMP('2022-07-12 12:31:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Anlieferdatum an Prüfanlage','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Anlieferdatum',0,0,TO_TIMESTAMP('2022-07-12 12:31:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:31:47.013Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583636 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:31:47.018Z
/* DDL */  select update_Column_Translation_From_AD_Element(581110) 
;

-- 2022-07-12T10:31:47.736Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN WODeliveryDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2022-07-12T10:33:28.819Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581111,0,'WOPlannedPersonDurationHours',TO_TIMESTAMP('2022-07-12 12:33:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SOLL-Personen-Stunden','SOLL-Personen-Stunden',TO_TIMESTAMP('2022-07-12 12:33:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:33:28.820Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581111 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:33:32.184Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:33:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581111 AND AD_Language='de_CH'
;

-- 2022-07-12T10:33:32.186Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581111,'de_CH') 
;

-- 2022-07-12T10:33:35.425Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:33:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581111 AND AD_Language='de_DE'
;

-- 2022-07-12T10:33:35.427Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581111,'de_DE') 
;

-- 2022-07-12T10:33:35.434Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581111,'de_DE') 
;

-- 2022-07-12T10:33:41.244Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='TARGET person hours', PrintName='TARGET person hours',Updated=TO_TIMESTAMP('2022-07-12 12:33:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581111 AND AD_Language='en_US'
;

-- 2022-07-12T10:33:41.247Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581111,'en_US') 
;

-- Column: C_Project_WO_Step.WOPlannedPersonDurationHours
-- 2022-07-12T10:34:07.752Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583637,581111,0,11,542159,'WOPlannedPersonDurationHours',TO_TIMESTAMP('2022-07-12 12:34:07','YYYY-MM-DD HH24:MI:SS'),100,'N','1','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'SOLL-Personen-Stunden',0,0,TO_TIMESTAMP('2022-07-12 12:34:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:34:07.755Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583637 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:34:07.760Z
/* DDL */  select update_Column_Translation_From_AD_Element(581111) 
;

-- 2022-07-12T10:34:08.490Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN WOPlannedPersonDurationHours NUMERIC(10) DEFAULT 1 NOT NULL')
;

-- 2022-07-12T10:35:40.957Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581112,0,'WOTargetEndDate',TO_TIMESTAMP('2022-07-12 12:35:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SOLL Prüfschrittende','SOLL Prüfschrittende',TO_TIMESTAMP('2022-07-12 12:35:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:35:40.959Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581112 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:35:45.008Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581112 AND AD_Language='de_CH'
;

-- 2022-07-12T10:35:45.010Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581112,'de_CH') 
;

-- 2022-07-12T10:35:47.767Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:35:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581112 AND AD_Language='de_DE'
;

-- 2022-07-12T10:35:47.769Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581112,'de_DE') 
;

-- 2022-07-12T10:35:47.776Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581112,'de_DE') 
;

-- 2022-07-12T10:35:59.387Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='TARGET end date', PrintName='TARGET end date',Updated=TO_TIMESTAMP('2022-07-12 12:35:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581112 AND AD_Language='en_US'
;

-- 2022-07-12T10:35:59.389Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581112,'en_US') 
;

-- Column: C_Project_WO_Step.WOTargetEndDate
-- 2022-07-12T10:36:10.445Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583638,581112,0,15,542159,'WOTargetEndDate',TO_TIMESTAMP('2022-07-12 12:36:10','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SOLL Prüfschrittende',0,0,TO_TIMESTAMP('2022-07-12 12:36:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:36:10.447Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583638 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:36:10.452Z
/* DDL */  select update_Column_Translation_From_AD_Element(581112) 
;

-- 2022-07-12T10:36:11.173Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN WOTargetEndDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2022-07-12T10:37:02.708Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581113,0,'WOTargetStartDate',TO_TIMESTAMP('2022-07-12 12:37:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SOLL Prüfschrittstart','SOLL Prüfschrittstart',TO_TIMESTAMP('2022-07-12 12:37:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:37:02.710Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581113 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:37:05.647Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:37:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581113 AND AD_Language='de_CH'
;

-- 2022-07-12T10:37:05.649Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581113,'de_CH') 
;

-- 2022-07-12T10:37:07.336Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:37:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581113 AND AD_Language='de_DE'
;

-- 2022-07-12T10:37:07.338Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581113,'de_DE') 
;

-- 2022-07-12T10:37:07.346Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581113,'de_DE') 
;

-- 2022-07-12T10:37:17.166Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='TARGET start date', PrintName='TARGET start date',Updated=TO_TIMESTAMP('2022-07-12 12:37:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581113 AND AD_Language='en_US'
;

-- 2022-07-12T10:37:17.168Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581113,'en_US') 
;

-- Column: C_Project_WO_Step.WOTargetStartDate
-- 2022-07-12T10:37:25.857Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583639,581113,0,15,542159,'WOTargetStartDate',TO_TIMESTAMP('2022-07-12 12:37:25','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SOLL Prüfschrittstart',0,0,TO_TIMESTAMP('2022-07-12 12:37:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:37:25.859Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583639 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:37:25.864Z
/* DDL */  select update_Column_Translation_From_AD_Element(581113) 
;

-- 2022-07-12T10:37:26.579Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN WOTargetStartDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2022-07-12T10:38:27.980Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581114,0,'WOStepStatus',TO_TIMESTAMP('2022-07-12 12:38:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Status','Status',TO_TIMESTAMP('2022-07-12 12:38:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:38:27.982Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581114 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:38:30.368Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:38:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581114 AND AD_Language='de_CH'
;

-- 2022-07-12T10:38:30.371Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581114,'de_CH') 
;

-- 2022-07-12T10:38:32.139Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:38:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581114 AND AD_Language='de_DE'
;

-- 2022-07-12T10:38:32.142Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581114,'de_DE') 
;

-- 2022-07-12T10:38:32.154Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581114,'de_DE') 
;

-- 2022-07-12T10:38:39.578Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='State', PrintName='State',Updated=TO_TIMESTAMP('2022-07-12 12:38:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581114 AND AD_Language='en_US'
;

-- 2022-07-12T10:38:39.580Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581114,'en_US') 
;

-- Name: WOStepStatus
-- 2022-07-12T10:39:02.928Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541599,TO_TIMESTAMP('2022-07-12 12:39:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','WOStepStatus',TO_TIMESTAMP('2022-07-12 12:39:02','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-07-12T10:39:02.930Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541599 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: WOStepStatus
-- Value: 0
-- Name: ERSTELLT
-- 2022-07-12T10:39:32.523Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541599,543232,TO_TIMESTAMP('2022-07-12 12:39:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ERSTELLT',TO_TIMESTAMP('2022-07-12 12:39:32','YYYY-MM-DD HH24:MI:SS'),100,'0','CREATED')
;

-- 2022-07-12T10:39:32.526Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543232 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: WOStepStatus
-- Value: 1
-- Name: EINGEGANGEN
-- 2022-07-12T10:40:01.830Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541599,543233,TO_TIMESTAMP('2022-07-12 12:40:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','EINGEGANGEN',TO_TIMESTAMP('2022-07-12 12:40:01','YYYY-MM-DD HH24:MI:SS'),100,'1','RECEIVED')
;

-- 2022-07-12T10:40:01.832Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543233 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: WOStepStatus
-- Value: 2
-- Name: FREIGEGEBEN
-- 2022-07-12T10:40:38.099Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541599,543234,TO_TIMESTAMP('2022-07-12 12:40:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FREIGEGEBEN',TO_TIMESTAMP('2022-07-12 12:40:38','YYYY-MM-DD HH24:MI:SS'),100,'2','RELEASED')
;

-- 2022-07-12T10:40:38.100Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543234 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: WOStepStatus
-- Value: 3
-- Name: VORGEMERKT
-- 2022-07-12T10:41:21.421Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541599,543235,TO_TIMESTAMP('2022-07-12 12:41:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','VORGEMERKT',TO_TIMESTAMP('2022-07-12 12:41:21','YYYY-MM-DD HH24:MI:SS'),100,'3','EARMARKED')
;

-- 2022-07-12T10:41:21.423Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543235 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: WOStepStatus
-- Value: 4
-- Name: PRÜFBEREIT
-- 2022-07-12T10:41:52.794Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541599,543236,TO_TIMESTAMP('2022-07-12 12:41:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','PRÜFBEREIT',TO_TIMESTAMP('2022-07-12 12:41:52','YYYY-MM-DD HH24:MI:SS'),100,'4','READYFORTESTING')
;

-- 2022-07-12T10:41:52.796Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543236 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: WOStepStatus
-- Value: 5
-- Name: IN PRÜFUNG
-- 2022-07-12T10:42:23.046Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541599,543237,TO_TIMESTAMP('2022-07-12 12:42:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IN PRÜFUNG',TO_TIMESTAMP('2022-07-12 12:42:22','YYYY-MM-DD HH24:MI:SS'),100,'5','INTESTING')
;

-- 2022-07-12T10:42:23.047Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543237 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: WOStepStatus
-- Value: 6
-- Name: AUSGEFÜHRT
-- 2022-07-12T10:42:53.302Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541599,543238,TO_TIMESTAMP('2022-07-12 12:42:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AUSGEFÜHRT',TO_TIMESTAMP('2022-07-12 12:42:53','YYYY-MM-DD HH24:MI:SS'),100,'6','EXECUTED')
;

-- 2022-07-12T10:42:53.303Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543238 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: WOStepStatus
-- Value: 7
-- Name: FERTIG
-- 2022-07-12T10:43:06.649Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541599,543239,TO_TIMESTAMP('2022-07-12 12:43:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FERTIG',TO_TIMESTAMP('2022-07-12 12:43:06','YYYY-MM-DD HH24:MI:SS'),100,'7','READY')
;

-- 2022-07-12T10:43:06.651Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543239 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: WOStepStatus
-- Value: 100
-- Name: STORNIERT
-- 2022-07-12T10:43:36.865Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541599,543240,TO_TIMESTAMP('2022-07-12 12:43:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','STORNIERT',TO_TIMESTAMP('2022-07-12 12:43:36','YYYY-MM-DD HH24:MI:SS'),100,'100','CANCELED')
;

-- 2022-07-12T10:43:36.867Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543240 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_Project_WO_Step.WOStepStatus
-- 2022-07-12T10:44:25.271Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583640,581114,0,17,541599,542159,'WOStepStatus',TO_TIMESTAMP('2022-07-12 12:44:25','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,3,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Status',0,0,TO_TIMESTAMP('2022-07-12 12:44:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:44:25.273Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583640 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:44:25.277Z
/* DDL */  select update_Column_Translation_From_AD_Element(581114) 
;

-- 2022-07-12T10:44:26.530Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN WOStepStatus VARCHAR(3) DEFAULT ''0'' NOT NULL')
;

-- 2022-07-12T10:46:15.354Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581115,0,'WOFindingsDeliveredDate',TO_TIMESTAMP('2022-07-12 12:46:15','YYYY-MM-DD HH24:MI:SS'),100,'Datum an dem der Bericht freigegeben wurde','D','Y','Befund Ausgang','Befund Ausgang',TO_TIMESTAMP('2022-07-12 12:46:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:46:15.357Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581115 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:46:19.225Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:46:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581115 AND AD_Language='de_CH'
;

-- 2022-07-12T10:46:19.226Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581115,'de_CH') 
;

-- 2022-07-12T10:46:21.128Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:46:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581115 AND AD_Language='de_DE'
;

-- 2022-07-12T10:46:21.130Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581115,'de_DE') 
;

-- 2022-07-12T10:46:21.137Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581115,'de_DE') 
;

-- 2022-07-12T10:46:55.880Z
UPDATE AD_Element_Trl SET Description='Date on which the report was released.', IsTranslated='Y', Name='Findings released', PrintName='Findings released',Updated=TO_TIMESTAMP('2022-07-12 12:46:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581115 AND AD_Language='en_US'
;

-- 2022-07-12T10:46:55.882Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581115,'en_US') 
;

-- 2022-07-12T10:47:06.005Z
UPDATE AD_Element SET ColumnName='WOFindingsReleasedDate',Updated=TO_TIMESTAMP('2022-07-12 12:47:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581115
;

-- 2022-07-12T10:47:06.013Z
UPDATE AD_Column SET ColumnName='WOFindingsReleasedDate', Name='Befund Ausgang', Description='Datum an dem der Bericht freigegeben wurde', Help=NULL WHERE AD_Element_ID=581115
;

-- 2022-07-12T10:47:06.015Z
UPDATE AD_Process_Para SET ColumnName='WOFindingsReleasedDate', Name='Befund Ausgang', Description='Datum an dem der Bericht freigegeben wurde', Help=NULL, AD_Element_ID=581115 WHERE UPPER(ColumnName)='WOFINDINGSRELEASEDDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-12T10:47:06.017Z
UPDATE AD_Process_Para SET ColumnName='WOFindingsReleasedDate', Name='Befund Ausgang', Description='Datum an dem der Bericht freigegeben wurde', Help=NULL WHERE AD_Element_ID=581115 AND IsCentrallyMaintained='Y'
;

-- Column: C_Project_WO_Step.WOFindingsReleasedDate
-- 2022-07-12T10:47:16.753Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583641,581115,0,15,542159,'WOFindingsReleasedDate',TO_TIMESTAMP('2022-07-12 12:47:16','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum an dem der Bericht freigegeben wurde','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Befund Ausgang',0,0,TO_TIMESTAMP('2022-07-12 12:47:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:47:16.757Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583641 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:47:16.760Z
/* DDL */  select update_Column_Translation_From_AD_Element(581115) 
;

-- 2022-07-12T10:47:17.484Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN WOFindingsReleasedDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2022-07-12T10:48:00.775Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581116,0,'WOFindingsCreatedDate',TO_TIMESTAMP('2022-07-12 12:48:00','YYYY-MM-DD HH24:MI:SS'),100,'Datum an dem der Bericht erstellt wurde','U','Y','Befund Erstellt','Befund Erstellt',TO_TIMESTAMP('2022-07-12 12:48:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:48:00.777Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581116 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:48:03.774Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2022-07-12 12:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581116
;

-- 2022-07-12T10:48:32.680Z
UPDATE AD_Element_Trl SET Description='Date on which the report was created.', IsTranslated='Y', Name='Findings created', PrintName='Findings created',Updated=TO_TIMESTAMP('2022-07-12 12:48:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581116 AND AD_Language='en_US'
;

-- 2022-07-12T10:48:32.683Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581116,'en_US') 
;

-- 2022-07-12T10:48:39.879Z
UPDATE AD_Element_Trl SET Description='Datum an dem der Bericht erstellt wurde.', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:48:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581116 AND AD_Language='de_CH'
;

-- 2022-07-12T10:48:39.882Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581116,'de_CH') 
;

-- 2022-07-12T10:48:42.562Z
UPDATE AD_Element_Trl SET Description='Datum an dem der Bericht erstellt wurde.', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:48:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581116 AND AD_Language='de_DE'
;

-- 2022-07-12T10:48:42.564Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581116,'de_DE') 
;

-- 2022-07-12T10:48:42.571Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581116,'de_DE') 
;

-- 2022-07-12T10:48:42.573Z
UPDATE AD_Column SET ColumnName='WOFindingsCreatedDate', Name='Befund Erstellt', Description='Datum an dem der Bericht erstellt wurde.', Help=NULL WHERE AD_Element_ID=581116
;

-- 2022-07-12T10:48:42.575Z
UPDATE AD_Process_Para SET ColumnName='WOFindingsCreatedDate', Name='Befund Erstellt', Description='Datum an dem der Bericht erstellt wurde.', Help=NULL, AD_Element_ID=581116 WHERE UPPER(ColumnName)='WOFINDINGSCREATEDDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-12T10:48:42.576Z
UPDATE AD_Process_Para SET ColumnName='WOFindingsCreatedDate', Name='Befund Erstellt', Description='Datum an dem der Bericht erstellt wurde.', Help=NULL WHERE AD_Element_ID=581116 AND IsCentrallyMaintained='Y'
;

-- 2022-07-12T10:48:42.577Z
UPDATE AD_Field SET Name='Befund Erstellt', Description='Datum an dem der Bericht erstellt wurde.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581116) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581116)
;

-- 2022-07-12T10:48:42.584Z
UPDATE AD_Tab SET Name='Befund Erstellt', Description='Datum an dem der Bericht erstellt wurde.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581116
;

-- 2022-07-12T10:48:42.587Z
UPDATE AD_WINDOW SET Name='Befund Erstellt', Description='Datum an dem der Bericht erstellt wurde.', Help=NULL WHERE AD_Element_ID = 581116
;

-- 2022-07-12T10:48:42.588Z
UPDATE AD_Menu SET   Name = 'Befund Erstellt', Description = 'Datum an dem der Bericht erstellt wurde.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581116
;

-- Column: C_Project_WO_Step.WOFindingsCreatedDate
-- 2022-07-12T10:49:01.731Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583642,581116,0,15,542159,'WOFindingsCreatedDate',TO_TIMESTAMP('2022-07-12 12:49:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum an dem der Bericht erstellt wurde.','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Befund Erstellt',0,0,TO_TIMESTAMP('2022-07-12 12:49:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:49:01.733Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583642 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:49:01.737Z
/* DDL */  select update_Column_Translation_From_AD_Element(581116) 
;

-- 2022-07-12T10:49:02.465Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN WOFindingsCreatedDate TIMESTAMP WITHOUT TIME ZONE')
;

