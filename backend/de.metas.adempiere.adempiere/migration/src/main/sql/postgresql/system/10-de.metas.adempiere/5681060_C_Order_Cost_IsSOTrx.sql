-- Column: C_Order_Cost.IsSOTrx
-- 2023-03-08T15:04:23.573Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586287,1106,0,20,542296,'IsSOTrx',TO_TIMESTAMP('2023-03-08 17:04:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N','This is a Sales Transaction','D',0,1,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Sales Transaction',0,0,TO_TIMESTAMP('2023-03-08 17:04:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-08T15:04:23.576Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586287 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-08T15:04:23.608Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106) 
;

-- 2023-03-08T15:04:24.985Z
/* DDL */ SELECT public.db_alter_table('C_Order_Cost','ALTER TABLE public.C_Order_Cost ADD COLUMN IsSOTrx CHAR(1) DEFAULT ''N'' CHECK (IsSOTrx IN (''Y'',''N'')) NOT NULL')
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Sales Transaction
-- Column: C_Order_Cost.IsSOTrx
-- 2023-03-08T15:04:52.375Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586287,712822,0,546808,TO_TIMESTAMP('2023-03-08 17:04:52','YYYY-MM-DD HH24:MI:SS'),100,'This is a Sales Transaction',1,'D','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','N','N','N','N','N','Sales Transaction',TO_TIMESTAMP('2023-03-08 17:04:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T15:04:52.377Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712822 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T15:04:52.379Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106) 
;

-- 2023-03-08T15:04:52.394Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712822
;

-- 2023-03-08T15:04:52.396Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712822)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Sales Transaction
-- Column: C_Order_Cost.IsSOTrx
-- 2023-03-08T15:05:41.155Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712822,0,546808,550345,615997,'F',TO_TIMESTAMP('2023-03-08 17:05:40','YYYY-MM-DD HH24:MI:SS'),100,'This is a Sales Transaction','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','Sales Transaction',50,0,0,TO_TIMESTAMP('2023-03-08 17:05:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Sales Transaction
-- Column: C_Order_Cost.IsSOTrx
-- 2023-03-08T15:05:50.083Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2023-03-08 17:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615997
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Sales Transaction
-- Column: C_Order_Cost.IsSOTrx
-- 2023-03-08T15:06:05.426Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-08 17:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615997
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Cost Type
-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-03-08T15:06:05.435Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-08 17:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615593
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> amounts.Currency
-- Column: C_Order_Cost.C_Currency_ID
-- 2023-03-08T15:06:05.441Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-08 17:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615597
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> amounts.Cost Amount
-- Column: C_Order_Cost.CostAmount
-- 2023-03-08T15:06:05.448Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-08 17:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615596
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Business Partner
-- Column: C_Order_Cost.C_BPartner_ID
-- 2023-03-08T15:06:05.455Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-08 17:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615654
;

-- Column: C_Order_Cost.IsSOTrx
-- 2023-03-08T15:06:16.097Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-03-08 17:06:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586287
;

