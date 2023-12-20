-- 2023-12-04T20:15:50.462Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582834,0,'NotFound_Message',TO_TIMESTAMP('2023-12-04 22:15:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Not Found Message','Not Found Message',TO_TIMESTAMP('2023-12-04 22:15:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-04T20:15:50.468Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582834 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-12-04T20:16:44.673Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582835,0,'NotFound_MessageDetail',TO_TIMESTAMP('2023-12-04 22:16:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Not Found Message Detail','Not Found Message Detail',TO_TIMESTAMP('2023-12-04 22:16:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-04T20:16:44.674Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582835 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Tab.NotFound_Message
-- 2023-12-04T20:17:41.970Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587683,582834,0,14,106,'NotFound_Message',TO_TIMESTAMP('2023-12-04 22:17:41','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Not Found Message',0,0,TO_TIMESTAMP('2023-12-04 22:17:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-04T20:17:41.972Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587683 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-04T20:17:42.004Z
/* DDL */  select update_Column_Translation_From_AD_Element(582834) 
;

-- 2023-12-04T20:18:05.310Z
/* DDL */ SELECT public.db_alter_table('AD_Tab','ALTER TABLE public.AD_Tab ADD COLUMN NotFound_Message VARCHAR(2000)')
;

-- Column: AD_Tab.NotFound_MessageDetail
-- 2023-12-04T20:18:37.665Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587684,582835,0,14,106,'NotFound_MessageDetail',TO_TIMESTAMP('2023-12-04 22:18:37','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Not Found Message Detail',0,0,TO_TIMESTAMP('2023-12-04 22:18:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-04T20:18:37.667Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587684 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-04T20:18:37.670Z
/* DDL */  select update_Column_Translation_From_AD_Element(582835) 
;

-- 2023-12-04T20:18:38.323Z
/* DDL */ SELECT public.db_alter_table('AD_Tab','ALTER TABLE public.AD_Tab ADD COLUMN NotFound_MessageDetail VARCHAR(2000)')
;

-- Column: AD_Tab_Trl.NotFound_Message
-- 2023-12-04T20:20:06.579Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587685,582834,0,14,123,'NotFound_Message',TO_TIMESTAMP('2023-12-04 22:20:06','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Not Found Message',0,0,TO_TIMESTAMP('2023-12-04 22:20:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-04T20:20:06.581Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587685 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-04T20:20:06.584Z
/* DDL */  select update_Column_Translation_From_AD_Element(582834) 
;

-- 2023-12-04T20:20:08.949Z
/* DDL */ SELECT public.db_alter_table('AD_Tab_Trl','ALTER TABLE public.AD_Tab_Trl ADD COLUMN NotFound_Message VARCHAR(2000)')
;

-- Column: AD_Tab_Trl.NotFound_MessageDetail
-- 2023-12-04T20:20:24.415Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587686,582835,0,14,123,'NotFound_MessageDetail',TO_TIMESTAMP('2023-12-04 22:20:24','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Not Found Message Detail',0,0,TO_TIMESTAMP('2023-12-04 22:20:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-04T20:20:24.418Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587686 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-04T20:20:24.421Z
/* DDL */  select update_Column_Translation_From_AD_Element(582835) 
;

-- 2023-12-04T20:20:25.099Z
/* DDL */ SELECT public.db_alter_table('AD_Tab_Trl','ALTER TABLE public.AD_Tab_Trl ADD COLUMN NotFound_MessageDetail VARCHAR(2000)')
;

-- Column: AD_Tab.NotFound_Message
-- 2023-12-04T20:20:43.376Z
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-04 22:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587683
;

-- Column: AD_Tab.NotFound_MessageDetail
-- 2023-12-04T20:20:45.718Z
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-04 22:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587684
;


























-- Field: Fenster Verwaltung -> Register -> Not Found Message
-- Column: AD_Tab.NotFound_Message
-- 2023-12-04T20:46:25.407Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587683,721963,0,106,TO_TIMESTAMP('2023-12-04 22:46:25','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Not Found Message',TO_TIMESTAMP('2023-12-04 22:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-04T20:46:25.410Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721963 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-04T20:46:25.417Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582834) 
;

-- 2023-12-04T20:46:25.433Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721963
;

-- 2023-12-04T20:46:25.436Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721963)
;

-- Field: Fenster Verwaltung -> Register -> Not Found Message Detail
-- Column: AD_Tab.NotFound_MessageDetail
-- 2023-12-04T20:46:25.558Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587684,721964,0,106,TO_TIMESTAMP('2023-12-04 22:46:25','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Not Found Message Detail',TO_TIMESTAMP('2023-12-04 22:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-04T20:46:25.560Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721964 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-04T20:46:25.561Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582835) 
;

-- 2023-12-04T20:46:25.564Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721964
;

-- 2023-12-04T20:46:25.564Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721964)
;

-- Field: Fenster Verwaltung -> Register -> Not Found Message
-- Column: AD_Tab.NotFound_Message
-- 2023-12-04T20:47:21.573Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=485,Updated=TO_TIMESTAMP('2023-12-04 22:47:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721963
;

-- Field: Fenster Verwaltung -> Register -> Not Found Message Detail
-- Column: AD_Tab.NotFound_MessageDetail
-- 2023-12-04T20:47:37.712Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=486,Updated=TO_TIMESTAMP('2023-12-04 22:47:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721964
;

-- Field: Fenster Verwaltung -> Übersetzung: Register -> Not Found Message
-- Column: AD_Tab_Trl.NotFound_Message
-- 2023-12-04T20:48:11.717Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587685,721965,0,116,TO_TIMESTAMP('2023-12-04 22:48:11','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Not Found Message',TO_TIMESTAMP('2023-12-04 22:48:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-04T20:48:11.719Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721965 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-04T20:48:11.721Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582834) 
;

-- 2023-12-04T20:48:11.727Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721965
;

-- 2023-12-04T20:48:11.729Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721965)
;

-- Field: Fenster Verwaltung -> Übersetzung: Register -> Not Found Message Detail
-- Column: AD_Tab_Trl.NotFound_MessageDetail
-- 2023-12-04T20:48:21.186Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587686,721966,0,116,TO_TIMESTAMP('2023-12-04 22:48:21','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Not Found Message Detail',TO_TIMESTAMP('2023-12-04 22:48:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-04T20:48:21.189Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721966 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-04T20:48:21.191Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582835) 
;

-- 2023-12-04T20:48:21.195Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721966
;

-- 2023-12-04T20:48:21.196Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721966)
;

-- Field: Fenster Verwaltung -> Übersetzung: Register -> Not Found Message
-- Column: AD_Tab_Trl.NotFound_Message
-- 2023-12-04T20:48:44.910Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2023-12-04 22:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721965
;

-- Field: Fenster Verwaltung -> Übersetzung: Register -> Not Found Message Detail
-- Column: AD_Tab_Trl.NotFound_MessageDetail
-- 2023-12-04T20:48:51.544Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2023-12-04 22:48:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721966
;

