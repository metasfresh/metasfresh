-- Column: Fact_Acct_Transactions_View.TaxCode
-- 2023-06-05T18:01:57.120630696Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586739,581888,0,10,541485,'TaxCode',TO_TIMESTAMP('2023-06-05 19:01:56.705','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,25,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tax Code',0,0,TO_TIMESTAMP('2023-06-05 19:01:56.705','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-05T18:01:57.126189990Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586739 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-05T18:01:57.781785432Z
/* DDL */  select update_Column_Translation_From_AD_Element(581888) 
;

-- Column: Fact_Acct_Transactions_View.TaxCode
-- 2023-06-05T18:02:21.183216237Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-06-05 19:02:21.182','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586739
;

-- Field: Buchführungs-Details(162,D) -> Buchführung(242,D) -> Tax Code
-- Column: Fact_Acct_Transactions_View.TaxCode
-- 2023-06-05T18:03:46.123302701Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586739,716173,0,242,0,TO_TIMESTAMP('2023-06-05 19:03:45.821','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Tax Code',0,360,0,1,1,TO_TIMESTAMP('2023-06-05 19:03:45.821','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-05T18:03:46.124646016Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716173 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-05T18:03:46.125893055Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581888) 
;

-- 2023-06-05T18:03:46.132769135Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716173
;

-- 2023-06-05T18:03:46.133764354Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716173)
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> document.Tax Code
-- Column: Fact_Acct_Transactions_View.TaxCode
-- 2023-06-05T18:05:45.196182285Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716173,0,242,540307,617939,'F',TO_TIMESTAMP('2023-06-05 19:05:44.927','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Tax Code',70,0,0,TO_TIMESTAMP('2023-06-05 19:05:44.927','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> document.Tax Code
-- Column: Fact_Acct_Transactions_View.TaxCode
-- 2023-06-05T18:06:01.440336837Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-06-05 19:06:01.44','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617939
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Geschäfts Partner
-- Column: Fact_Acct_Transactions_View.C_BPartner_ID
-- 2023-06-05T18:06:01.445244639Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2023-06-05 19:06:01.445','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=543382
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Produkt
-- Column: Fact_Acct_Transactions_View.M_Product_ID
-- 2023-06-05T18:06:01.450399487Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2023-06-05 19:06:01.45','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=543383
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Kostenstelle
-- Column: Fact_Acct_Transactions_View.C_Activity_ID
-- 2023-06-05T18:06:01.455589116Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2023-06-05 19:06:01.455','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=543387
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> dimensions.Section Code
-- Column: Fact_Acct_Transactions_View.M_SectionCode_ID
-- 2023-06-05T18:06:01.460226433Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2023-06-05 19:06:01.46','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613104
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> dimensions.Sales order
-- Column: Fact_Acct_Transactions_View.C_OrderSO_ID
-- 2023-06-05T18:06:01.464434107Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2023-06-05 19:06:01.464','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614551
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 10 -> document.Belegstatus
-- Column: Fact_Acct_Transactions_View.DocStatus
-- 2023-06-05T18:06:01.468626876Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2023-06-05 19:06:01.468','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=543370
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> quantity.Menge
-- Column: Fact_Acct_Transactions_View.Qty
-- 2023-06-05T18:06:01.473228215Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2023-06-05 19:06:01.473','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=570661
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> quantity.Maßeinheit
-- Column: Fact_Acct_Transactions_View.C_UOM_ID
-- 2023-06-05T18:06:01.477418418Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2023-06-05 19:06:01.477','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=570662
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Cost Element
-- Column: Fact_Acct_Transactions_View.M_CostElement_ID
-- 2023-06-05T18:06:01.480551890Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2023-06-05 19:06:01.48','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=615852
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 10 -> document.Belegstatus
-- Column: Fact_Acct_Transactions_View.DocStatus
-- 2023-06-05T18:06:56.389082726Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-06-05 19:06:56.388','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=543370
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> document.Steuer
-- Column: Fact_Acct_Transactions_View.C_Tax_ID
-- 2023-06-05T18:06:56.392564214Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-06-05 19:06:56.392','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=543393
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> document.Tax Code
-- Column: Fact_Acct_Transactions_View.TaxCode
-- 2023-06-05T18:06:56.395336009Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2023-06-05 19:06:56.395','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617939
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Geschäfts Partner
-- Column: Fact_Acct_Transactions_View.C_BPartner_ID
-- 2023-06-05T18:06:56.397672279Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2023-06-05 19:06:56.397','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=543382
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Produkt
-- Column: Fact_Acct_Transactions_View.M_Product_ID
-- 2023-06-05T18:06:56.400527061Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2023-06-05 19:06:56.4','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=543383
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Kostenstelle
-- Column: Fact_Acct_Transactions_View.C_Activity_ID
-- 2023-06-05T18:06:56.403182885Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2023-06-05 19:06:56.403','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=543387
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> dimensions.Section Code
-- Column: Fact_Acct_Transactions_View.M_SectionCode_ID
-- 2023-06-05T18:06:56.405709992Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2023-06-05 19:06:56.405','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613104
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> dimensions.Sales order
-- Column: Fact_Acct_Transactions_View.C_OrderSO_ID
-- 2023-06-05T18:06:56.408100618Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2023-06-05 19:06:56.407','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614551
;

