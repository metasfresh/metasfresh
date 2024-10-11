-- Run mode: SWING_CLIENT

-- 2024-10-11T13:27:02.609Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583329,0,'IsPOApply5CentCashRounding',TO_TIMESTAMP('2024-10-11 16:27:02.288','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','5-Cent-Bargeldrundung anwenden PO','5-Cent-Bargeldrundung anwenden PO',TO_TIMESTAMP('2024-10-11 16:27:02.288','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-11T13:27:02.627Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583329 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsPOApply5CentCashRounding
-- 2024-10-11T13:27:51.981Z
UPDATE AD_Element_Trl SET Name='Apply 5 Cent Cash Rounding (Purchase)', PrintName='Apply 5 Cent Cash Rounding (Purchase)',Updated=TO_TIMESTAMP('2024-10-11 16:27:51.981','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583329 AND AD_Language='en_US'
;

-- 2024-10-11T13:27:52.016Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583329,'en_US')
;

-- Column: C_Currency.IsPOApply5CentCashRounding
-- 2024-10-11T13:42:37.001Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589304,583329,0,20,141,'IsPOApply5CentCashRounding',TO_TIMESTAMP('2024-10-11 16:42:36.866','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'5-Cent-Bargeldrundung anwenden PO',0,0,TO_TIMESTAMP('2024-10-11 16:42:36.866','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-10-11T13:42:37.005Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589304 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-11T13:42:37.012Z
/* DDL */  select update_Column_Translation_From_AD_Element(583329)
;

-- 2024-10-11T13:42:38.379Z
/* DDL */ SELECT public.db_alter_table('C_Currency','ALTER TABLE public.C_Currency ADD COLUMN IsPOApply5CentCashRounding CHAR(1) DEFAULT ''N'' CHECK (IsPOApply5CentCashRounding IN (''Y'',''N'')) NOT NULL')
;



-- Field: Währung(115,D) -> Währung(151,D) -> 5-Cent-Bargeldrundung anwenden PO
-- Column: C_Currency.IsPOApply5CentCashRounding
-- 2024-10-11T14:00:58.704Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589304,731907,0,151,TO_TIMESTAMP('2024-10-11 17:00:58.528','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','5-Cent-Bargeldrundung anwenden PO',TO_TIMESTAMP('2024-10-11 17:00:58.528','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-11T14:00:58.707Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=731907 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-11T14:00:58.709Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583329)
;

-- 2024-10-11T14:00:58.727Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731907
;

-- 2024-10-11T14:00:58.733Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731907)
;

-- UI Element: Währung(115,D) -> Währung(151,D) -> main -> 20 -> flags.5-Cent-Bargeldrundung anwenden PO
-- Column: C_Currency.IsPOApply5CentCashRounding
-- 2024-10-11T14:01:20.934Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731907,0,151,540326,626191,'F',TO_TIMESTAMP('2024-10-11 17:01:20.754','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','5-Cent-Bargeldrundung anwenden PO',30,0,0,TO_TIMESTAMP('2024-10-11 17:01:20.754','YYYY-MM-DD HH24:MI:SS.US'),100)
;





