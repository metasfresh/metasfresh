-- Add M_Product.MedicalDeviceType list column (MD / Z / N)
-- IDs allocated from idserver.metas.de on 2026-06-26:
--   AD_Reference  542113  (MedicalDeviceType list reference)
--   AD_Ref_List   544289  (value MD)
--   AD_Ref_List   544290  (value Z)
--   AD_Ref_List   544291  (value N)
--   AD_Element    585056  (MedicalDeviceType column label)
--   AD_Column     592896  (M_Product.MedicalDeviceType)
--   AD_Field      781253  (Product window header tab field)
--   AD_UI_Element 652373  (UI element in group 1000015, SeqNo=67)

-- Physical column DDL
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN IF NOT EXISTS MedicalDeviceType VARCHAR(20)')
;

-- Create AD_Reference (ValidationType='L')
-- 2026-06-26 15:00:00
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType)
VALUES (0,0,542113 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 15:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Medizinprodukt-Typ',TO_TIMESTAMP('2026-06-26 15:00:00','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2026-06-26 15:00:01
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=542113
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2026-06-26 15:00:02
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-26 15:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Reference_ID=542113
;

-- 2026-06-26 15:00:03
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Medical device type',Updated=TO_TIMESTAMP('2026-06-26 15:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Reference_ID=542113
;

-- AD_Ref_List: value MD (code is language-neutral; Name=Value=ValueName by design)
-- 2026-06-26 15:00:10
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542113 /*From ID Server*/,544289 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 15:00:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','MD',TO_TIMESTAMP('2026-06-26 15:00:10','YYYY-MM-DD HH24:MI:SS'),100,'MD','MD')
;

-- 2026-06-26 15:00:11
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544289
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-06-26 15:00:12
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-26 15:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544289
;

-- 2026-06-26 15:00:13
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='MD',Updated=TO_TIMESTAMP('2026-06-26 15:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544289
;

-- AD_Ref_List: value Z
-- 2026-06-26 15:00:20
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542113 /*From ID Server*/,544290 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 15:00:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Z',TO_TIMESTAMP('2026-06-26 15:00:20','YYYY-MM-DD HH24:MI:SS'),100,'Z','Z')
;

-- 2026-06-26 15:00:21
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544290
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-06-26 15:00:22
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-26 15:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544290
;

-- 2026-06-26 15:00:23
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Z',Updated=TO_TIMESTAMP('2026-06-26 15:00:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544290
;

-- AD_Ref_List: value N
-- 2026-06-26 15:00:30
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542113 /*From ID Server*/,544291 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 15:00:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2026-06-26 15:00:30','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2026-06-26 15:00:31
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544291
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-06-26 15:00:32
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-26 15:00:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544291
;

-- 2026-06-26 15:00:33
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='N',Updated=TO_TIMESTAMP('2026-06-26 15:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544291
;

-- AD_Element for MedicalDeviceType (German in base column, English via Trl)
-- 2026-06-26 15:01:00
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585056 /*From ID Server*/,0,'MedicalDeviceType',TO_TIMESTAMP('2026-06-26 15:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Medizinprodukt-Typ','D','Y','Medizinprodukt-Typ','Medizinprodukt-Typ',TO_TIMESTAMP('2026-06-26 15:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26 15:01:01
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585056
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-06-26 15:01:12
UPDATE AD_Element_Trl SET Description='Medical device type', IsTranslated='Y', Name='Medical device type', PrintName='Medical device type',Updated=TO_TIMESTAMP('2026-06-26 15:01:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585056 AND AD_Language='en_US'
;

-- 2026-06-26 15:01:13
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585056,'en_US')
;

-- 2026-06-26 15:01:14
/* DDL */ select update_ad_element_on_ad_element_trl_update(585056,'en_US')
;

-- 2026-06-26 15:01:18
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-26 15:01:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585056 AND AD_Language='de_DE'
;

-- 2026-06-26 15:01:19
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585056,'de_DE')
;

-- 2026-06-26 15:01:20
/* DDL */ select update_ad_element_on_ad_element_trl_update(585056,'de_DE')
;

-- AD_Column on M_Product (AD_Table_ID=208)
-- 2026-06-26 15:02:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592896 /*From ID Server*/,585056,0,17,542113,208,'MedicalDeviceType',TO_TIMESTAMP('2026-06-26 15:02:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Medizinprodukt-Typ','D',0,20,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Medizinprodukt-Typ','NP',0,0,TO_TIMESTAMP('2026-06-26 15:02:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26 15:02:01
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592896
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26 15:02:02
/* DDL */ select update_Column_Translation_From_AD_Element(585056)
;

-- AD_Field on Product window header tab (AD_Tab_ID=180)
-- 2026-06-26 15:03:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy)
VALUES (0,592896,781253 /*From ID Server*/,585056,0,180,0,TO_TIMESTAMP('2026-06-26 15:03:00','YYYY-MM-DD HH24:MI:SS'),100,'Medizinprodukt-Typ',0,'D',0,'Y','Y','N','N','N','N','N','N','Medizinprodukt-Typ',0,0,0,1,1,TO_TIMESTAMP('2026-06-26 15:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26 15:03:01
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=781253
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-26 15:03:02
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585056)
;

-- 2026-06-26 15:03:03
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781253
;

-- 2026-06-26 15:03:04
/* DDL */ select AD_Element_Link_Create_Missing_Field(781253)
;

-- AD_UI_Element in group 1000015 (the "No" group on the Product header tab), SeqNo=67
-- Placed after PreferentialOrigin which is at SeqNo=65
-- 2026-06-26 15:04:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781253,0,180,1000015,652373 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-26 15:04:00','YYYY-MM-DD HH24:MI:SS'),100,'Medizinprodukt-Typ','Y','N','N','Y','N','N','N',0,'Medizinprodukt-Typ',67,0,0,TO_TIMESTAMP('2026-06-26 15:04:00','YYYY-MM-DD HH24:MI:SS'),100)
;
