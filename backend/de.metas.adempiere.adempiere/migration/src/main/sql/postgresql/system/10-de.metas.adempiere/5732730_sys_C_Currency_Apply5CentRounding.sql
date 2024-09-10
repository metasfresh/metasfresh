-- Run mode: SWING_CLIENT

-- 2024-09-09T14:56:19.413Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583244,0,'IsApply5CentCashRounding',TO_TIMESTAMP('2024-09-09 17:56:19.158','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Apply 5 Cent Cash Rounding','Apply 5 Cent Cash Rounding',TO_TIMESTAMP('2024-09-09 17:56:19.158','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-09T14:56:19.427Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583244 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Currency.IsApply5CentCashRounding
-- 2024-09-09T14:56:48.902Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588945,583244,0,20,141,'IsApply5CentCashRounding',TO_TIMESTAMP('2024-09-09 17:56:48.774','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Apply 5 Cent Cash Rounding',0,0,TO_TIMESTAMP('2024-09-09 17:56:48.774','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-09T14:56:48.904Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588945 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-09T14:56:48.942Z
/* DDL */  select update_Column_Translation_From_AD_Element(583244)
;

-- 2024-09-09T14:56:50.195Z
/* DDL */ SELECT public.db_alter_table('C_Currency','ALTER TABLE public.C_Currency ADD COLUMN IsApply5CentCashRounding CHAR(1) DEFAULT ''N'' CHECK (IsApply5CentCashRounding IN (''Y'',''N'')) NOT NULL')
;




-- Field: Währung(115,D) -> Währung(151,D) -> Apply 5 Cent Cash Rounding
-- Column: C_Currency.IsApply5CentCashRounding
-- 2024-09-09T15:19:18.679Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588945,729854,0,151,TO_TIMESTAMP('2024-09-09 18:19:18.495','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','Y','N','N','N','N','N','Apply 5 Cent Cash Rounding',TO_TIMESTAMP('2024-09-09 18:19:18.495','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-09T15:19:18.681Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729854 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-09T15:19:18.684Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583244)
;

-- 2024-09-09T15:19:18.696Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729854
;

-- 2024-09-09T15:19:18.698Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729854)
;

-- UI Element: Währung(115,D) -> Währung(151,D) -> main -> 20 -> flags.Apply 5 Cent Cash Rounding
-- Column: C_Currency.IsApply5CentCashRounding
-- 2024-09-09T15:20:17.777Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729854,0,151,540326,625310,'F',TO_TIMESTAMP('2024-09-09 18:20:17.616','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Apply 5 Cent Cash Rounding',20,0,0,TO_TIMESTAMP('2024-09-09 18:20:17.616','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Währung(115,D) -> Währung(151,D) -> main -> 20 -> flags.Apply 5 Cent Cash Rounding
-- Column: C_Currency.IsApply5CentCashRounding
-- 2024-09-09T15:20:44.223Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-09 18:20:44.223','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=625310
;

-- UI Element: Währung(115,D) -> Währung(151,D) -> main -> 20 -> org.Sektion
-- Column: C_Currency.AD_Org_ID
-- 2024-09-09T15:20:44.230Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-09-09 18:20:44.23','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=543513
;




