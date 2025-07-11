-- Run mode: SWING_CLIENT

-- 2025-07-10T13:04:35.154Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583798,0,'OAuth2_Logo_URI',TO_TIMESTAMP('2025-07-10 16:04:34.905','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Logo URI','Logo URI',TO_TIMESTAMP('2025-07-10 16:04:34.905','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T13:04:35.165Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583798 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: OAuth2_Client.OAuth2_Logo_URI
-- 2025-07-10T13:04:55.411Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590523,583798,0,10,542509,'OAuth2_Logo_URI',TO_TIMESTAMP('2025-07-10 16:04:55.242','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Logo URI',0,0,TO_TIMESTAMP('2025-07-10 16:04:55.242','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T13:04:55.414Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590523 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T13:04:55.418Z
/* DDL */  select update_Column_Translation_From_AD_Element(583798)
;

-- 2025-07-10T13:04:57.282Z
/* DDL */ SELECT public.db_alter_table('OAuth2_Client','ALTER TABLE public.OAuth2_Client ADD COLUMN OAuth2_Logo_URI VARCHAR(255)')
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> Logo URI
-- Column: OAuth2_Client.OAuth2_Logo_URI
-- 2025-07-10T13:05:54.461Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590523,749968,0,548281,TO_TIMESTAMP('2025-07-10 16:05:54.294','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Logo URI',TO_TIMESTAMP('2025-07-10 16:05:54.294','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T13:05:54.465Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749968 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T13:05:54.470Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583798)
;

-- 2025-07-10T13:05:54.476Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749968
;

-- 2025-07-10T13:05:54.480Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749968)
;

-- UI Column: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10
-- UI Element Group: branding
-- 2025-07-10T13:06:15.076Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548288,553248,TO_TIMESTAMP('2025-07-10 16:06:14.904','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','branding',30,TO_TIMESTAMP('2025-07-10 16:06:14.904','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> branding.Logo URI
-- Column: OAuth2_Client.OAuth2_Logo_URI
-- 2025-07-10T13:06:27.327Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749968,0,548281,553248,635166,'F',TO_TIMESTAMP('2025-07-10 16:06:27.056','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Logo URI',10,0,0,TO_TIMESTAMP('2025-07-10 16:06:27.056','YYYY-MM-DD HH24:MI:SS.US'),100)
;

