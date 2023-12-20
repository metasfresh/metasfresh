-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Empfangene Menge
-- Column: C_Order_Cost_Detail.QtyReceived
-- 2023-02-08T13:45:04.769Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585934,712272,0,546809,TO_TIMESTAMP('2023-02-08 15:45:04','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Empfangene Menge',TO_TIMESTAMP('2023-02-08 15:45:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T13:45:04.772Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712272 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T13:45:04.803Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542547) 
;

-- 2023-02-08T13:45:04.816Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712272
;

-- 2023-02-08T13:45:04.818Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712272)
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Cost Amount Received
-- Column: C_Order_Cost_Detail.CostAmountReceived
-- 2023-02-08T13:45:04.940Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585935,712273,0,546809,TO_TIMESTAMP('2023-02-08 15:45:04','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Amount Received',TO_TIMESTAMP('2023-02-08 15:45:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T13:45:04.941Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712273 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T13:45:04.942Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582042) 
;

-- 2023-02-08T13:45:04.945Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712273
;

-- 2023-02-08T13:45:04.946Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712273)
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Empfangene Menge
-- Column: C_Order_Cost_Detail.QtyReceived
-- 2023-02-08T13:45:44.047Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712272,0,546809,550348,615651,'F',TO_TIMESTAMP('2023-02-08 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Empfangene Menge',80,0,0,TO_TIMESTAMP('2023-02-08 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Cost Amount Received
-- Column: C_Order_Cost_Detail.CostAmountReceived
-- 2023-02-08T13:45:49.873Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712273,0,546809,550348,615652,'F',TO_TIMESTAMP('2023-02-08 15:45:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Amount Received',90,0,0,TO_TIMESTAMP('2023-02-08 15:45:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Empfangene Menge
-- Column: C_Order_Cost_Detail.QtyReceived
-- 2023-02-08T13:46:10.632Z
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2023-02-08 15:46:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615651
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Empfangene Menge
-- Column: C_Order_Cost_Detail.QtyReceived
-- 2023-02-08T13:46:34.976Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-08 15:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615651
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Currency
-- Column: C_Order_Cost_Detail.C_Currency_ID
-- 2023-02-08T13:46:34.984Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-08 15:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615604
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Line Net Amount
-- Column: C_Order_Cost_Detail.LineNetAmt
-- 2023-02-08T13:46:34.990Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-08 15:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615605
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Cost Amount
-- Column: C_Order_Cost_Detail.CostAmount
-- 2023-02-08T13:46:34.997Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-02-08 15:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615617
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Cost Amount Received
-- Column: C_Order_Cost_Detail.CostAmountReceived
-- 2023-02-08T13:46:35.003Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-02-08 15:46:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615652
;

-- Table: M_InOut_Cost
-- 2023-02-08T13:47:05.014Z
UPDATE AD_Table SET AD_Window_ID=541677,Updated=TO_TIMESTAMP('2023-02-08 15:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542299
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Reversal ID
-- Column: M_InOut_Cost.Reversal_ID
-- 2023-02-08T13:50:42.521Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585955,712274,0,546813,TO_TIMESTAMP('2023-02-08 15:50:42','YYYY-MM-DD HH24:MI:SS'),100,'ID of document reversal',10,'D','Y','N','N','N','N','N','N','N','Reversal ID',TO_TIMESTAMP('2023-02-08 15:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T13:50:42.522Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712274 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T13:50:42.523Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53457) 
;

-- 2023-02-08T13:50:42.529Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712274
;

-- 2023-02-08T13:50:42.529Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712274)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Reversal ID
-- Column: M_InOut_Cost.Reversal_ID
-- 2023-02-08T13:51:08.855Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712274,0,546813,550365,615653,'F',TO_TIMESTAMP('2023-02-08 15:51:08','YYYY-MM-DD HH24:MI:SS'),100,'ID of document reversal','Y','N','Y','N','N','Reversal ID',70,0,0,TO_TIMESTAMP('2023-02-08 15:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Reversal ID
-- Column: M_InOut_Cost.Reversal_ID
-- 2023-02-08T13:51:30.369Z
UPDATE AD_Field SET DisplayLogic='@Reversal_ID/0@>0',Updated=TO_TIMESTAMP('2023-02-08 15:51:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712274
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Sales order
-- Column: M_InOut_Cost.C_Order_ID
-- 2023-02-08T13:52:45.460Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-08 15:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615639
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Orderline
-- Column: M_InOut_Cost.C_OrderLine_ID
-- 2023-02-08T13:52:45.467Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-08 15:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615640
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Shipment/ Receipt
-- Column: M_InOut_Cost.M_InOut_ID
-- 2023-02-08T13:52:45.475Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-08 15:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615641
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Receipt Line
-- Column: M_InOut_Cost.M_InOutLine_ID
-- 2023-02-08T13:52:45.482Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-08 15:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615642
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Order Cost
-- Column: M_InOut_Cost.C_Order_Cost_ID
-- 2023-02-08T13:52:45.489Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-08 15:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615643
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Order Cost Detail
-- Column: M_InOut_Cost.C_Order_Cost_Detail_ID
-- 2023-02-08T13:52:45.496Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-08 15:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615644
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.UOM
-- Column: M_InOut_Cost.C_UOM_ID
-- 2023-02-08T13:52:45.504Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-08 15:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615645
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Quantity
-- Column: M_InOut_Cost.Qty
-- 2023-02-08T13:52:45.511Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-02-08 15:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615646
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Currency
-- Column: M_InOut_Cost.C_Currency_ID
-- 2023-02-08T13:52:45.516Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-02-08 15:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615647
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Cost Amount
-- Column: M_InOut_Cost.CostAmount
-- 2023-02-08T13:52:45.522Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-02-08 15:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615648
;

