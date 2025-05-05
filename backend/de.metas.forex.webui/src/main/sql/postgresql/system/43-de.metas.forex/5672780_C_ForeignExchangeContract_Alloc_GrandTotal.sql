-- Column: C_ForeignExchangeContract_Alloc.GrandTotal
-- 2023-01-20T11:30:54.106Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585598,316,0,12,542283,'GrandTotal',TO_TIMESTAMP('2023-01-20 13:30:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Total amount of document','D',0,10,'The Grand Total displays the total amount including Tax and Freight in document currency','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Grand Total',0,0,TO_TIMESTAMP('2023-01-20 13:30:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-20T11:30:54.114Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585598 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-20T11:30:54.154Z
/* DDL */  select update_Column_Translation_From_AD_Element(316) 
;

-- 2023-01-20T11:30:59.363Z
/* DDL */ SELECT public.db_alter_table('C_ForeignExchangeContract_Alloc','ALTER TABLE public.C_ForeignExchangeContract_Alloc ADD COLUMN GrandTotal NUMERIC NOT NULL')
;

-- Table: C_ForeignExchangeContract_Alloc
-- 2023-01-20T11:37:27.694Z
UPDATE AD_Table SET AD_Window_ID=541664,Updated=TO_TIMESTAMP('2023-01-20 13:37:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542283
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> Grand Total
-- Column: C_ForeignExchangeContract_Alloc.GrandTotal
-- 2023-01-20T11:37:38.660Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585598,710326,0,546746,TO_TIMESTAMP('2023-01-20 13:37:38','YYYY-MM-DD HH24:MI:SS'),100,'Total amount of document',10,'D','The Grand Total displays the total amount including Tax and Freight in document currency','Y','N','N','N','N','N','N','N','Grand Total',TO_TIMESTAMP('2023-01-20 13:37:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-20T11:37:38.662Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710326 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-20T11:37:38.664Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(316) 
;

-- 2023-01-20T11:37:38.681Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710326
;

-- 2023-01-20T11:37:38.682Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710326)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main -> 10 -> main.Grand Total
-- Column: C_ForeignExchangeContract_Alloc.GrandTotal
-- 2023-01-20T11:38:00.741Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710326,0,546746,550240,614768,'F',TO_TIMESTAMP('2023-01-20 13:38:00','YYYY-MM-DD HH24:MI:SS'),100,'Total amount of document','The Grand Total displays the total amount including Tax and Freight in document currency','Y','N','Y','N','N','Grand Total',40,0,0,TO_TIMESTAMP('2023-01-20 13:38:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main -> 10 -> main.Amount
-- Column: C_ForeignExchangeContract_Alloc.Amount
-- 2023-01-20T11:38:14.647Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2023-01-20 13:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614766
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main -> 10 -> main.Grand Total
-- Column: C_ForeignExchangeContract_Alloc.GrandTotal
-- 2023-01-20T11:38:20.391Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-01-20 13:38:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614768
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main -> 10 -> main.Grand Total
-- Column: C_ForeignExchangeContract_Alloc.GrandTotal
-- 2023-01-20T11:38:33.725Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-01-20 13:38:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614768
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main -> 10 -> main.Amount
-- Column: C_ForeignExchangeContract_Alloc.Amount
-- 2023-01-20T11:38:33.733Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-01-20 13:38:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614766
;

