-- IDs allocated from idserver.metas.de on 2026-07-27:
--   AD_MigrationScript 5816400 (this script's filename prefix)
--   AD_Reference    542123 (Product Life Cycle Status list)
--   AD_Ref_List     544324 (O), 544325 (A), 544326 (G), 544327 (N)
--   AD_Element      585137 (M_Product.ProductLifeCycleStatus)
--   AD_Column       593038 (M_Product.ProductLifeCycleStatus)
--   AD_Field        781848 (Produkt window, main tab)
--   AD_UI_Element   652772 (Produkt window, main tab, "No" group)
--
-- New core product life-cycle status column: M_Product.ProductLifeCycleStatus VARCHAR(1)
-- DEFAULT 'O'. Values: O=OK, A=Auslauf/Phase-out, G=Gesperrt/Blocked, N=Lieferstopp/Delivery stop.
-- Orthogonal to IsSold/IsPurchased/Discontinued -- never derived from them.
--
-- German ref-list term for the 'N' value: sourced from the existing "Lieferstopp"/"Delivery stop"
-- AD_Element (1001070) and the "Liefersperre" ContractStatus ChangeStatus value ("St"), which are
-- the established terms for "block only the delivery step" -- used instead of a literal
-- "Nicht ausliefern"/"Do not deliver" translation for consistency with existing glossary terms.
-- 'Gesperrt'/'Blocked' matches the existing ArticleStatus ref-list value '2'.

-- Reference: Product Life Cycle Status
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType)
VALUES (0,0,542123 /*From ID Server*/,TO_TIMESTAMP('2026-07-27 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Produktlebenszyklus-Status',TO_TIMESTAMP('2026-07-27 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=542123 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 14:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=542123
;

UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 14:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=542123
;

UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Product Life Cycle Status',Updated=TO_TIMESTAMP('2026-07-27 14:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=542123
;

-- Reference Item: Value O (OK)
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,542123,544324 /*From ID Server*/,TO_TIMESTAMP('2026-07-27 14:00:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','OK',TO_TIMESTAMP('2026-07-27 14:00:04','YYYY-MM-DD HH24:MI:SS'),100,'O')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544324 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 14:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544324
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 14:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544324
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='OK',Updated=TO_TIMESTAMP('2026-07-27 14:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544324
;

-- Reference Item: Value A (Auslauf / Phase-out)
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,542123,544325 /*From ID Server*/,TO_TIMESTAMP('2026-07-27 14:00:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Auslauf',TO_TIMESTAMP('2026-07-27 14:00:08','YYYY-MM-DD HH24:MI:SS'),100,'A')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544325 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 14:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544325
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 14:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544325
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Phase-out',Updated=TO_TIMESTAMP('2026-07-27 14:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544325
;

-- Reference Item: Value G (Gesperrt / Blocked)
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,542123,544326 /*From ID Server*/,TO_TIMESTAMP('2026-07-27 14:00:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Gesperrt',TO_TIMESTAMP('2026-07-27 14:00:12','YYYY-MM-DD HH24:MI:SS'),100,'G')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544326 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 14:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544326
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 14:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544326
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Blocked',Updated=TO_TIMESTAMP('2026-07-27 14:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544326
;

-- Reference Item: Value N (Lieferstopp / Delivery stop)
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,542123,544327 /*From ID Server*/,TO_TIMESTAMP('2026-07-27 14:00:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lieferstopp',TO_TIMESTAMP('2026-07-27 14:00:16','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544327 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 14:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544327
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 14:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544327
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Delivery stop',Updated=TO_TIMESTAMP('2026-07-27 14:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544327
;

-- Element: ProductLifeCycleStatus
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585137 /*From ID Server*/,0,'ProductLifeCycleStatus',TO_TIMESTAMP('2026-07-27 14:00:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produktlebenszyklus-Status','Produktlebenszyklus-Status',TO_TIMESTAMP('2026-07-27 14:00:20','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585137 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 14:00:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585137 AND AD_Language='de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585137,'de_DE')
;
/* DDL */ select update_ad_element_on_ad_element_trl_update(585137,'de_DE')
;

UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 14:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585137 AND AD_Language='de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585137,'de_CH')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Product Life Cycle Status', PrintName='Product Life Cycle Status',Updated=TO_TIMESTAMP('2026-07-27 14:00:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585137 AND AD_Language='en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585137,'en_US')
;

-- Column: M_Product.ProductLifeCycleStatus
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593038 /*From ID Server*/,585137,0,17,542123,208,'ProductLifeCycleStatus',TO_TIMESTAMP('2026-07-27 14:00:24','YYYY-MM-DD HH24:MI:SS'),100,'N','O','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produktlebenszyklus-Status','NP',0,0,TO_TIMESTAMP('2026-07-27 14:00:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593038 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(585137)
;

/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN ProductLifeCycleStatus VARCHAR(1) DEFAULT ''O''')
;

-- Field: Produkt(140,D) -> Produkt(180,D) -> Produktlebenszyklus-Status
-- Column: M_Product.ProductLifeCycleStatus
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy)
VALUES (0,593038,781848 /*From ID Server*/,0,180,0,TO_TIMESTAMP('2026-07-27 14:00:25','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Produktlebenszyklus-Status',0,65,0,1,1,TO_TIMESTAMP('2026-07-27 14:00:25','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=781848 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585137)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=781848
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(781848)
;

-- UI Element: Produkt(140,D) -> Produkt(180,D) -> No -> 75 -> Produktlebenszyklus-Status
-- Column: M_Product.ProductLifeCycleStatus
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781848,0,180,1000015,652772 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-27 14:00:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','Y','N','N',0,'Produktlebenszyklus-Status',75,65,0,TO_TIMESTAMP('2026-07-27 14:00:26','YYYY-MM-DD HH24:MI:SS'),100)
;
