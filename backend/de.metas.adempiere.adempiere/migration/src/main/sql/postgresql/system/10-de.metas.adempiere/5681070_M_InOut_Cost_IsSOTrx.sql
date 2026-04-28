-- Column: M_InOut_Cost.IsSOTrx
-- 2023-03-08T15:06:57.247Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586288,1106,0,20,542299,'IsSOTrx',TO_TIMESTAMP('2023-03-08 17:06:57','YYYY-MM-DD HH24:MI:SS'),100,'N','N','This is a Sales Transaction','D',0,1,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Sales Transaction',0,0,TO_TIMESTAMP('2023-03-08 17:06:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-08T15:06:57.249Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586288 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-08T15:06:57.252Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106) 
;

-- 2023-03-08T15:06:58.112Z
/* DDL */ SELECT public.db_alter_table('M_InOut_Cost','ALTER TABLE public.M_InOut_Cost ADD COLUMN IsSOTrx CHAR(1) DEFAULT ''N'' CHECK (IsSOTrx IN (''Y'',''N'')) NOT NULL')
;

-- Column: M_InOut_Cost.IsSOTrx
-- 2023-03-08T15:07:03.784Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-03-08 17:07:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586288
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Sales Transaction
-- Column: M_InOut_Cost.IsSOTrx
-- 2023-03-08T15:07:22.518Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586288,712823,0,546813,TO_TIMESTAMP('2023-03-08 17:07:22','YYYY-MM-DD HH24:MI:SS'),100,'This is a Sales Transaction',1,'D','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','N','N','N','N','N','Sales Transaction',TO_TIMESTAMP('2023-03-08 17:07:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T15:07:22.519Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T15:07:22.522Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106) 
;

-- 2023-03-08T15:07:22.527Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712823
;

-- 2023-03-08T15:07:22.528Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712823)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Sales Transaction
-- Column: M_InOut_Cost.IsSOTrx
-- 2023-03-08T15:07:48.296Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712823,0,546813,550365,615998,'F',TO_TIMESTAMP('2023-03-08 17:07:48','YYYY-MM-DD HH24:MI:SS'),100,'This is a Sales Transaction','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','Sales Transaction',110,0,0,TO_TIMESTAMP('2023-03-08 17:07:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Sales Transaction
-- Column: M_InOut_Cost.IsSOTrx
-- 2023-03-08T15:08:02.001Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2023-03-08 17:08:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615998
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Sales Transaction
-- Column: M_InOut_Cost.IsSOTrx
-- 2023-03-08T15:08:39.083Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615998
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Sales order
-- Column: M_InOut_Cost.C_Order_ID
-- 2023-03-08T15:08:39.091Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615639
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Orderline
-- Column: M_InOut_Cost.C_OrderLine_ID
-- 2023-03-08T15:08:39.098Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615640
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Shipment/ Receipt
-- Column: M_InOut_Cost.M_InOut_ID
-- 2023-03-08T15:08:39.105Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615641
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Receipt Line
-- Column: M_InOut_Cost.M_InOutLine_ID
-- 2023-03-08T15:08:39.112Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615642
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Order Cost
-- Column: M_InOut_Cost.C_Order_Cost_ID
-- 2023-03-08T15:08:39.118Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615643
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Order Cost Detail
-- Column: M_InOut_Cost.C_Order_Cost_Detail_ID
-- 2023-03-08T15:08:39.125Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615644
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Business Partner
-- Column: M_InOut_Cost.C_BPartner_ID
-- 2023-03-08T15:08:39.131Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615659
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Cost Type
-- Column: M_InOut_Cost.C_Cost_Type_ID
-- 2023-03-08T15:08:39.138Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615660
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Cost Element
-- Column: M_InOut_Cost.M_CostElement_ID
-- 2023-03-08T15:08:39.143Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615848
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.UOM
-- Column: M_InOut_Cost.C_UOM_ID
-- 2023-03-08T15:08:39.149Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615645
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Quantity
-- Column: M_InOut_Cost.Qty
-- 2023-03-08T15:08:39.165Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615646
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Currency
-- Column: M_InOut_Cost.C_Currency_ID
-- 2023-03-08T15:08:39.170Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615647
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Cost Amount
-- Column: M_InOut_Cost.CostAmount
-- 2023-03-08T15:08:39.175Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615648
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> invoicing.Cost Amount Invoiced
-- Column: M_InOut_Cost.CostAmountInvoiced
-- 2023-03-08T15:08:39.180Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615839
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> invoicing.Invoiced
-- Column: M_InOut_Cost.IsInvoiced
-- 2023-03-08T15:08:39.185Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615840
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Reversal ID
-- Column: M_InOut_Cost.Reversal_ID
-- 2023-03-08T15:08:39.190Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615653
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> org & client.Organisation
-- Column: M_InOut_Cost.AD_Org_ID
-- 2023-03-08T15:08:39.195Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2023-03-08 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615649
;

