-- Run mode: SWING_CLIENT

-- 2025-05-22T15:13:25.010Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583656,0,'MaxLineChars',TO_TIMESTAMP('2025-05-22 18:13:24.765','YYYY-MM-DD HH24:MI:SS.US'),100,'Geben Sie die maximalen Zeilenzeichen für jede Zeile der Adresse an','D','Y','Maximale Zeilenzeichen','Maximale Zeilenzeichen',TO_TIMESTAMP('2025-05-22 18:13:24.765','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-22T15:13:25.038Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583656 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: MaxLineChars
-- 2025-05-22T15:13:41.301Z
UPDATE AD_Element_Trl SET Description='Specify the maximum line characters for each line of the address', Name='Maximum Line Characters', PrintName='Maximum Line Characters',Updated=TO_TIMESTAMP('2025-05-22 18:13:41.301','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583656 AND AD_Language='en_US'
;

-- 2025-05-22T15:13:41.328Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583656,'en_US')
;

-- Column: C_Country.MaxLineChars
-- 2025-05-22T15:15:03.139Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590021,583656,0,11,170,'MaxLineChars',TO_TIMESTAMP('2025-05-22 18:15:03.003','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','Geben Sie die maximalen Zeilenzeichen für jede Zeile der Adresse an','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maximale Zeilenzeichen',0,0,TO_TIMESTAMP('2025-05-22 18:15:03.003','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-22T15:15:03.144Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590021 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-22T15:15:03.147Z
/* DDL */  select update_Column_Translation_From_AD_Element(583656)
;

-- 2025-05-22T15:15:05.081Z
/* DDL */ SELECT public.db_alter_table('C_Country','ALTER TABLE public.C_Country ADD COLUMN MaxLineChars NUMERIC(10)')
;

-- Field: Land, Region, Stadt(122,D) -> Land(135,D) -> Maximale Zeilenzeichen
-- Column: C_Country.MaxLineChars
-- 2025-05-22T15:16:38.458Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590021,746218,0,135,TO_TIMESTAMP('2025-05-22 18:16:38.266','YYYY-MM-DD HH24:MI:SS.US'),100,'Geben Sie die maximalen Zeilenzeichen für jede Zeile der Adresse an',10,'D','Y','N','N','N','N','N','N','N','Maximale Zeilenzeichen',TO_TIMESTAMP('2025-05-22 18:16:38.266','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-22T15:16:38.463Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=746218 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-22T15:16:38.468Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583656)
;

-- 2025-05-22T15:16:38.492Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=746218
;

-- 2025-05-22T15:16:38.505Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(746218)
;

-- UI Element: Land, Region, Stadt(122,D) -> Land(135,D) -> main -> 10 -> description.Maximale Zeilenzeichen
-- Column: C_Country.MaxLineChars
-- 2025-05-22T15:17:09.588Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,746218,0,135,633174,540558,'F',TO_TIMESTAMP('2025-05-22 18:17:09.409','YYYY-MM-DD HH24:MI:SS.US'),100,'Geben Sie die maximalen Zeilenzeichen für jede Zeile der Adresse an','Y','N','N','Y','N','N','N',0,'Maximale Zeilenzeichen',5,0,0,TO_TIMESTAMP('2025-05-22 18:17:09.409','YYYY-MM-DD HH24:MI:SS.US'),100)
;
