-- 2024-03-19T11:13:10.614Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583041,0,'IsAlwaysSplitHUsEnabled',TO_TIMESTAMP('2024-03-19 13:13:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Always split HU','Always split HU',TO_TIMESTAMP('2024-03-19 13:13:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T11:13:10.636Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583041 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: MobileUI_UserProfile_Picking.IsAlwaysSplitHUsEnabled
-- Column: MobileUI_UserProfile_Picking.IsAlwaysSplitHUsEnabled
-- 2024-03-19T11:13:53.623Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588033,583041,0,20,542373,'XX','IsAlwaysSplitHUsEnabled',TO_TIMESTAMP('2024-03-19 13:13:53','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Always split HU',0,0,TO_TIMESTAMP('2024-03-19 13:13:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-19T11:13:53.630Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588033 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-19T11:13:53.689Z
/* DDL */  select update_Column_Translation_From_AD_Element(583041) 
;

-- 2024-03-19T11:13:54.920Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsAlwaysSplitHUsEnabled CHAR(1) DEFAULT ''N'' CHECK (IsAlwaysSplitHUsEnabled IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil -> Always split HU
-- Column: MobileUI_UserProfile_Picking.IsAlwaysSplitHUsEnabled
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Always split HU
-- Column: MobileUI_UserProfile_Picking.IsAlwaysSplitHUsEnabled
-- 2024-03-19T11:16:19.285Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588033,726611,0,547258,TO_TIMESTAMP('2024-03-19 13:16:19','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Always split HU',TO_TIMESTAMP('2024-03-19 13:16:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T11:16:19.291Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726611 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T11:16:19.299Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583041) 
;

-- 2024-03-19T11:16:19.321Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726611
;

-- 2024-03-19T11:16:19.332Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726611)
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Always split HU
-- Column: MobileUI_UserProfile_Picking.IsAlwaysSplitHUsEnabled
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Always split HU
-- Column: MobileUI_UserProfile_Picking.IsAlwaysSplitHUsEnabled
-- 2024-03-19T11:17:13.556Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726611,0,547258,623784,551252,'F',TO_TIMESTAMP('2024-03-19 13:17:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Always split HU',30,0,0,TO_TIMESTAMP('2024-03-19 13:17:12','YYYY-MM-DD HH24:MI:SS'),100)
;

