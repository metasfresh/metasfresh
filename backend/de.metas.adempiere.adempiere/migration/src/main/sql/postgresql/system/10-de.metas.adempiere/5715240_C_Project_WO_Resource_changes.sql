-- 2024-01-12T10:18:59.548110Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582897,0,'Resource_AssignDateFrom',TO_TIMESTAMP('2024-01-12 12:18:59.289','YYYY-MM-DD HH24:MI:SS.US'),100,'Ressource zuordnen ab','D','Beginn Zuordnung','Y','Zuordnung von (Ressource)','Zuordnung von (Ressource)',TO_TIMESTAMP('2024-01-12 12:18:59.289','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T10:18:59.559583800Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582897 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-01-12T10:19:28.042579900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582898,0,'HR_AssignDateFrom',TO_TIMESTAMP('2024-01-12 12:19:27.908','YYYY-MM-DD HH24:MI:SS.US'),100,'','D','','Y','Zuordnung von (HR)','Zuordnung von (HR)',TO_TIMESTAMP('2024-01-12 12:19:27.908','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T10:19:28.045197600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582898 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Resource_AssignDateFrom
-- 2024-01-12T10:20:15.267578600Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-01-12 12:20:15.267','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582897 AND AD_Language='de_CH'
;

-- 2024-01-12T10:20:15.304906900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582897,'de_CH') 
;

-- Element: Resource_AssignDateFrom
-- 2024-01-12T10:20:16.625928400Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-01-12 12:20:16.625','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582897 AND AD_Language='de_DE'
;

-- 2024-01-12T10:20:16.629603900Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582897,'de_DE') 
;

-- 2024-01-12T10:20:16.631679100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582897,'de_DE') 
;

-- Element: Resource_AssignDateFrom
-- 2024-01-12T10:21:06.239563100Z
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Assign From (Resource)', PrintName='Assign From (Resource)',Updated=TO_TIMESTAMP('2024-01-12 12:21:06.239','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582897 AND AD_Language='en_US'
;

-- 2024-01-12T10:21:06.242679900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582897,'en_US') 
;

-- Element: HR_AssignDateFrom
-- 2024-01-12T10:21:15.416041600Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-01-12 12:21:15.415','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582898 AND AD_Language='de_CH'
;

-- 2024-01-12T10:21:15.419178900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582898,'de_CH') 
;

-- Element: HR_AssignDateFrom
-- 2024-01-12T10:21:16.402443400Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-01-12 12:21:16.401','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582898 AND AD_Language='de_DE'
;

-- 2024-01-12T10:21:16.406077300Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582898,'de_DE') 
;

-- 2024-01-12T10:21:16.407113Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582898,'de_DE') 
;

-- Element: HR_AssignDateFrom
-- 2024-01-12T10:21:37.674409200Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Assign From (HR)', PrintName='Assign From (HR)',Updated=TO_TIMESTAMP('2024-01-12 12:21:37.673','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582898 AND AD_Language='en_US'
;

-- 2024-01-12T10:21:37.678070900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582898,'en_US') 
;

-- 2024-01-12T10:22:03.856222300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582899,0,'Resource_AssignDateTo',TO_TIMESTAMP('2024-01-12 12:22:03.704','YYYY-MM-DD HH24:MI:SS.US'),100,'Ressource zuordnen bis','D','Zuordnung endet','Y','Zuordnung bis (Ressource)','Zuordnung bis (Ressource)',TO_TIMESTAMP('2024-01-12 12:22:03.704','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T10:22:03.859318400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582899 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Resource_AssignDateTo
-- 2024-01-12T10:22:07.888599700Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-01-12 12:22:07.888','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582899 AND AD_Language='de_CH'
;

-- 2024-01-12T10:22:07.891749500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582899,'de_CH') 
;

-- Element: Resource_AssignDateTo
-- 2024-01-12T10:22:09.103089600Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-01-12 12:22:09.103','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582899 AND AD_Language='de_DE'
;

-- 2024-01-12T10:22:09.107812300Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582899,'de_DE') 
;

-- 2024-01-12T10:22:09.110426Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582899,'de_DE') 
;

-- Element: Resource_AssignDateTo
-- 2024-01-12T10:22:42.889980100Z
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Assign To (Ressource)', PrintName='Assign To (Ressource)',Updated=TO_TIMESTAMP('2024-01-12 12:22:42.889','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582899 AND AD_Language='en_US'
;

-- 2024-01-12T10:22:42.893622600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582899,'en_US') 
;

-- Element: Resource_AssignDateTo
-- 2024-01-12T10:22:45.886855800Z
UPDATE AD_Element_Trl SET Name='Assign To (Resource)', PrintName='Assign To (Resource)',Updated=TO_TIMESTAMP('2024-01-12 12:22:45.886','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582899 AND AD_Language='en_US'
;

-- 2024-01-12T10:22:45.889986500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582899,'en_US') 
;

-- 2024-01-12T10:23:05.325635800Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582900,0,'HR_AssignDateTo',TO_TIMESTAMP('2024-01-12 12:23:05.189','YYYY-MM-DD HH24:MI:SS.US'),100,'','D','','Y','Zuordnung bis (HR)','Zuordnung bis (HR)',TO_TIMESTAMP('2024-01-12 12:23:05.189','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T10:23:05.328231500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582900 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: HR_AssignDateTo
-- 2024-01-12T10:23:08.797281300Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-01-12 12:23:08.796','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582900 AND AD_Language='de_DE'
;

-- 2024-01-12T10:23:08.801946500Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582900,'de_DE') 
;

-- 2024-01-12T10:23:08.802982900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582900,'de_DE') 
;

-- Element: HR_AssignDateTo
-- 2024-01-12T10:23:21.805891700Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-01-12 12:23:21.805','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582900 AND AD_Language='de_CH'
;

-- 2024-01-12T10:23:21.810082Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582900,'de_CH') 
;

-- Element: HR_AssignDateTo
-- 2024-01-12T10:23:31.236002900Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Assign To (HR)', PrintName='Assign To (HR)',Updated=TO_TIMESTAMP('2024-01-12 12:23:31.236','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582900 AND AD_Language='en_US'
;

-- 2024-01-12T10:23:31.239661800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582900,'en_US') 
;

-- Column: C_Project_WO_Resource.Resource_AssignDateFrom
-- 2024-01-12T10:24:00.976909Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587790,582897,0,16,542161,'Resource_AssignDateFrom',TO_TIMESTAMP('2024-01-12 12:24:00.81','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Ressource zuordnen ab','D',0,7,'Beginn Zuordnung','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zuordnung von (Ressource)',0,0,TO_TIMESTAMP('2024-01-12 12:24:00.81','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T10:24:00.979510100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587790 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T10:24:01.489580200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582897) 
;

-- 2024-01-12T10:24:03.175612100Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource','ALTER TABLE public.C_Project_WO_Resource ADD COLUMN Resource_AssignDateFrom TIMESTAMP WITH TIME ZONE')
;

-- Column: C_Project_WO_Resource.Resource_AssignDateTo
-- 2024-01-12T10:24:38.036399400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587791,582899,0,16,542161,'Resource_AssignDateTo',TO_TIMESTAMP('2024-01-12 12:24:37.891','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Ressource zuordnen bis','D',0,7,'Zuordnung endet','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zuordnung bis (Ressource)',0,0,TO_TIMESTAMP('2024-01-12 12:24:37.891','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T10:24:38.038500300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587791 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T10:24:38.573758Z
/* DDL */  select update_Column_Translation_From_AD_Element(582899) 
;

-- Column: C_Project_WO_Resource.HR_AssignDateFrom
-- 2024-01-12T10:24:53.008714500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587792,582898,0,16,542161,'HR_AssignDateFrom',TO_TIMESTAMP('2024-01-12 12:24:52.879','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','D',0,7,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zuordnung von (HR)',0,0,TO_TIMESTAMP('2024-01-12 12:24:52.879','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T10:24:53.011336500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587792 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T10:24:53.520505800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582898) 
;

-- 2024-01-12T10:24:54.913534900Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource','ALTER TABLE public.C_Project_WO_Resource ADD COLUMN HR_AssignDateFrom TIMESTAMP WITH TIME ZONE')
;

-- Column: C_Project_WO_Resource.HR_AssignDateTo
-- 2024-01-12T10:25:08.462869600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587793,582900,0,16,542161,'HR_AssignDateTo',TO_TIMESTAMP('2024-01-12 12:25:08.321','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','D',0,7,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zuordnung bis (HR)',0,0,TO_TIMESTAMP('2024-01-12 12:25:08.321','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T10:25:08.465208600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587793 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T10:25:08.997028600Z
/* DDL */  select update_Column_Translation_From_AD_Element(582900) 
;

-- 2024-01-12T10:25:10.371697500Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource','ALTER TABLE public.C_Project_WO_Resource ADD COLUMN HR_AssignDateTo TIMESTAMP WITH TIME ZONE')
;

-- Field: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> Externe ID
-- Column: C_Project_WO_Resource.ExternalId
-- 2024-01-12T10:25:52.362325600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583692,723813,0,546560,TO_TIMESTAMP('2024-01-12 12:25:52.166','YYYY-MM-DD HH24:MI:SS.US'),100,255,'de.metas.endcustomer.ip180','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2024-01-12 12:25:52.166','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T10:25:52.365197300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723813 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T10:25:52.367306800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2024-01-12T10:25:52.399028900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723813
;

-- 2024-01-12T10:25:52.400604900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723813)
;

-- Field: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> Zuordnung von (Ressource)
-- Column: C_Project_WO_Resource.Resource_AssignDateFrom
-- 2024-01-12T10:25:52.527614100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587790,723814,0,546560,TO_TIMESTAMP('2024-01-12 12:25:52.406','YYYY-MM-DD HH24:MI:SS.US'),100,'Ressource zuordnen ab',7,'de.metas.endcustomer.ip180','Beginn Zuordnung','Y','N','N','N','N','N','N','N','Zuordnung von (Ressource)',TO_TIMESTAMP('2024-01-12 12:25:52.406','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T10:25:52.529990Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723814 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T10:25:52.532561300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582897) 
;

-- 2024-01-12T10:25:52.535703Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723814
;

-- 2024-01-12T10:25:52.536759800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723814)
;

-- Field: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> Zuordnung bis (Ressource)
-- Column: C_Project_WO_Resource.Resource_AssignDateTo
-- 2024-01-12T10:25:52.651642500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587791,723815,0,546560,TO_TIMESTAMP('2024-01-12 12:25:52.54','YYYY-MM-DD HH24:MI:SS.US'),100,'Ressource zuordnen bis',7,'de.metas.endcustomer.ip180','Zuordnung endet','Y','N','N','N','N','N','N','N','Zuordnung bis (Ressource)',TO_TIMESTAMP('2024-01-12 12:25:52.54','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T10:25:52.654549600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723815 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T10:25:52.656658500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582899) 
;

-- 2024-01-12T10:25:52.659808Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723815
;

-- 2024-01-12T10:25:52.660334Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723815)
;

-- Field: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> Zuordnung von (HR)
-- Column: C_Project_WO_Resource.HR_AssignDateFrom
-- 2024-01-12T10:25:52.775407800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587792,723816,0,546560,TO_TIMESTAMP('2024-01-12 12:25:52.663','YYYY-MM-DD HH24:MI:SS.US'),100,'',7,'de.metas.endcustomer.ip180','','Y','N','N','N','N','N','N','N','Zuordnung von (HR)',TO_TIMESTAMP('2024-01-12 12:25:52.663','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T10:25:52.777669Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723816 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T10:25:52.779260600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582898) 
;

-- 2024-01-12T10:25:52.782373400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723816
;

-- 2024-01-12T10:25:52.783414400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723816)
;

-- Field: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> Zuordnung bis (HR)
-- Column: C_Project_WO_Resource.HR_AssignDateTo
-- 2024-01-12T10:25:52.894874600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587793,723817,0,546560,TO_TIMESTAMP('2024-01-12 12:25:52.786','YYYY-MM-DD HH24:MI:SS.US'),100,'',7,'de.metas.endcustomer.ip180','','Y','N','N','N','N','N','N','N','Zuordnung bis (HR)',TO_TIMESTAMP('2024-01-12 12:25:52.786','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T10:25:52.897006900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723817 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T10:25:52.898573100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582900) 
;

-- 2024-01-12T10:25:52.901679100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723817
;

-- 2024-01-12T10:25:52.902204900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723817)
;

