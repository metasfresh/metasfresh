delete from ad_element where ad_element_id=583049;

-- 2024-03-21T17:50:16.053447Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583049,0,'Sprint',TO_TIMESTAMP('2024-03-21 19:50:15.046','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.serviceprovider','Y','Sprint','Sprint',TO_TIMESTAMP('2024-03-21 19:50:15.046','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-21T17:50:16.100264200Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583049 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: S_Issue.Sprint
-- 2024-03-21T17:50:42.409951800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,
                       IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,588044,583049,0,10,541468,'Sprint',TO_TIMESTAMP('2024-03-21 19:50:41.878','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.serviceprovider',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N',
        'N','N','N','N','N','N','Y','N',0,'Sprint',0,0,TO_TIMESTAMP('2024-03-21 19:50:41.878','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-21T17:50:42.446023700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588044 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-21T17:50:44.306469300Z
/* DDL */  select update_Column_Translation_From_AD_Element(583049) 
;

-- 2024-03-21T17:50:48.818419800Z
/* DDL */ SELECT public.db_alter_table('S_Issue','ALTER TABLE public.S_Issue ADD COLUMN Sprint VARCHAR(255)')
;

-- Field: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> Sprint
-- Column: S_Issue.Sprint
-- 2024-03-21T18:00:18.455222700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588044,726634,0,542341,TO_TIMESTAMP('2024-03-21 20:00:17.833','YYYY-MM-DD HH24:MI:SS.US'),100,255,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Sprint',TO_TIMESTAMP('2024-03-21 20:00:17.833','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-21T18:00:18.493321400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726634 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-21T18:00:18.559245700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583049) 
;

-- 2024-03-21T18:00:18.606912700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726634
;

-- 2024-03-21T18:00:18.647343800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726634)
;

-- UI Element: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> Issue -> 10 -> invoice.Sprint
-- Column: S_Issue.Sprint
-- 2024-03-21T18:00:39.141669900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726634,0,542341,543829,623804,'F',TO_TIMESTAMP('2024-03-21 20:00:37.926','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Sprint',70,0,0,TO_TIMESTAMP('2024-03-21 20:00:37.926','YYYY-MM-DD HH24:MI:SS.US'),100)
;

