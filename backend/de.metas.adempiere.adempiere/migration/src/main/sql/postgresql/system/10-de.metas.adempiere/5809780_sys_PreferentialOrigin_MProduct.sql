-- Add M_Product.PreferentialOrigin list column (DL / EU)
-- IDs allocated from idserver.metas.de on 2026-06-26:
--   AD_Reference  542112  (PreferentialOrigin list reference)
--   AD_Ref_List   544287  (value DL)
--   AD_Ref_List   544288  (value EU)
--   AD_Element    585055  (PreferentialOrigin column label)
--   AD_Column     592895  (M_Product.PreferentialOrigin)
--   AD_Field      781252  (Product window header tab field)
--   AD_UI_Element 652372  (UI element in group 1000015, SeqNo=65)

-- Physical column DDL
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN IF NOT EXISTS PreferentialOrigin VARCHAR(20)')
;

-- Create AD_Reference (ValidationType='L')
-- 2026-06-26 14:00:00
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType)
VALUES (0,0,542112 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','PreferentialOrigin',TO_TIMESTAMP('2026-06-26 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2026-06-26 14:00:01
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=542112
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2026-06-26 14:00:02
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-26 14:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Reference_ID=542112
;

-- 2026-06-26 14:00:03
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='PreferentialOrigin',Updated=TO_TIMESTAMP('2026-06-26 14:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Reference_ID=542112
;

-- AD_Ref_List: value DL (German name = code, English = code)
-- 2026-06-26 14:00:10
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542112 /*From ID Server*/,544287 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 14:00:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','DL',TO_TIMESTAMP('2026-06-26 14:00:10','YYYY-MM-DD HH24:MI:SS'),100,'DL','DL')
;

-- 2026-06-26 14:00:11
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544287
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-06-26 14:00:12
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-26 14:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544287
;

-- 2026-06-26 14:00:13
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='DL',Updated=TO_TIMESTAMP('2026-06-26 14:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544287
;

-- AD_Ref_List: value EU (German name = code, English = code)
-- 2026-06-26 14:00:20
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542112 /*From ID Server*/,544288 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 14:00:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','EU',TO_TIMESTAMP('2026-06-26 14:00:20','YYYY-MM-DD HH24:MI:SS'),100,'EU','EU')
;

-- 2026-06-26 14:00:21
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544288
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-06-26 14:00:22
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-26 14:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544288
;

-- 2026-06-26 14:00:23
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='EU',Updated=TO_TIMESTAMP('2026-06-26 14:00:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544288
;

-- AD_Element for PreferentialOrigin
-- German in base column, English via Trl
-- 2026-06-26 14:01:00
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585055 /*From ID Server*/,0,'PreferentialOrigin',TO_TIMESTAMP('2026-06-26 14:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Präferentieller Ursprung','D','Y','Präferentieller Ursprung','Präferentieller Ursprung',TO_TIMESTAMP('2026-06-26 14:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26 14:01:01
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585055
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-06-26 14:01:12
UPDATE AD_Element_Trl SET Description='Preferential origin', IsTranslated='Y', Name='Preferential origin', PrintName='Preferential origin',Updated=TO_TIMESTAMP('2026-06-26 14:01:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585055 AND AD_Language='en_US'
;

-- 2026-06-26 14:01:13
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585055,'en_US')
;

-- 2026-06-26 14:01:14
/* DDL */ select update_ad_element_on_ad_element_trl_update(585055,'en_US')
;

-- 2026-06-26 14:01:18
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-26 14:01:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585055 AND AD_Language='de_DE'
;

-- 2026-06-26 14:01:19
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585055,'de_DE')
;

-- 2026-06-26 14:01:20
/* DDL */ select update_ad_element_on_ad_element_trl_update(585055,'de_DE')
;

-- AD_Column on M_Product (AD_Table_ID=208)
-- 2026-06-26 14:02:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592895 /*From ID Server*/,585055,0,17,542112,208,'PreferentialOrigin',TO_TIMESTAMP('2026-06-26 14:02:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Präferentieller Ursprung','D',0,20,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Präferentieller Ursprung','NP',0,0,TO_TIMESTAMP('2026-06-26 14:02:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26 14:02:01
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592895
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26 14:02:02
/* DDL */ select update_Column_Translation_From_AD_Element(585055)
;

-- AD_Field on Product window header tab (AD_Tab_ID=180)
-- 2026-06-26 14:03:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy)
VALUES (0,592895,781252 /*From ID Server*/,585055,0,180,0,TO_TIMESTAMP('2026-06-26 14:03:00','YYYY-MM-DD HH24:MI:SS'),100,'Präferentieller Ursprung',0,'D',0,'Y','Y','N','N','N','N','N','N','Präferentieller Ursprung',0,0,0,1,1,TO_TIMESTAMP('2026-06-26 14:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26 14:03:01
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=781252
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-26 14:03:02
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585055)
;

-- 2026-06-26 14:03:03
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781252
;

-- 2026-06-26 14:03:04
/* DDL */ select AD_Element_Link_Create_Missing_Field(781252)
;

-- AD_UI_Element in group 1000015 (the "No" group on the Product header tab), SeqNo=65
-- Placed after M_CustomsTariff_ID which is at SeqNo=60
-- 2026-06-26 14:04:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781252,0,180,1000015,652372 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-26 14:04:00','YYYY-MM-DD HH24:MI:SS'),100,'Präferentieller Ursprung','Y','N','N','Y','N','N','N',0,'Präferentieller Ursprung',65,0,0,TO_TIMESTAMP('2026-06-26 14:04:00','YYYY-MM-DD HH24:MI:SS'),100)
;
