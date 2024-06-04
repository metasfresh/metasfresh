--
-- tweak IC windows and C_Flatrate_Term windows
-- 

-- Window: Rechnungsdisposition, InternalName=C_Invoice_Candidate_Sales
-- 2024-06-03T13:17:31.025Z
UPDATE AD_Window SET InternalName='C_Invoice_Candidate_Sales',Updated=TO_TIMESTAMP('2024-06-03 15:17:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540092
;

-- Window: Rechnungsdisposition Einkauf, InternalName=C_Invoice_Candidate_Purchase
-- 2024-06-03T13:17:53.634Z
UPDATE AD_Window SET InternalName='C_Invoice_Candidate_Purchase',Updated=TO_TIMESTAMP('2024-06-03 15:17:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540983
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.M_AttributeSetInstance_ID
-- Column: C_Invoice_Detail.M_AttributeSetInstance_ID
-- 2024-06-03T13:19:55.919Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622499,0,543056,544374,624788,'F',TO_TIMESTAMP('2024-06-03 15:19:55','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','N','N',0,'M_AttributeSetInstance_ID',55,0,0,TO_TIMESTAMP('2024-06-03 15:19:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.M_AttributeSetInstance_ID
-- Column: C_Invoice_Detail.M_AttributeSetInstance_ID
-- 2024-06-03T13:20:05.664Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624788
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Produktionsauftrag
-- Column: C_Invoice_Detail.PP_Order_ID
-- 2024-06-03T13:20:05.676Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573517
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Notiz
-- Column: C_Invoice_Detail.Note
-- 2024-06-03T13:20:05.689Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573518
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Beschreibung
-- Column: C_Invoice_Detail.Description
-- 2024-06-03T13:20:05.702Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573519
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Date
-- Column: C_Invoice_Detail.Date
-- 2024-06-03T13:20:05.715Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573520
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Percentage
-- Column: C_Invoice_Detail.Percentage
-- 2024-06-03T13:20:05.728Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573521
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Bestellte Menge in Preiseinheit
-- Column: C_Invoice_Detail.QtyEnteredInPriceUOM
-- 2024-06-03T13:20:05.740Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573522
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Preis
-- Column: C_Invoice_Detail.PriceEntered
-- 2024-06-03T13:20:05.751Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573523
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Einzelpreis
-- Column: C_Invoice_Detail.PriceActual
-- 2024-06-03T13:20:05.765Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573524
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Rabatt %
-- Column: C_Invoice_Detail.Discount
-- 2024-06-03T13:20:05.776Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573525
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Preiseinheit
-- Column: C_Invoice_Detail.Price_UOM_ID
-- 2024-06-03T13:20:05.787Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573526
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Menge
-- Column: C_Invoice_Detail.Qty
-- 2024-06-03T13:20:05.798Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573527
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Maßeinheit
-- Column: C_Invoice_Detail.C_UOM_ID
-- 2024-06-03T13:20:05.807Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573528
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Rechnung
-- Column: C_Invoice_Detail.C_Invoice_ID
-- 2024-06-03T13:20:05.819Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573529
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Rechnungsposition
-- Column: C_Invoice_Detail.C_InvoiceLine_ID
-- 2024-06-03T13:20:05.829Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573530
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Packvorschrift (TU)
-- Column: C_Invoice_Detail.M_TU_HU_PI_ID
-- 2024-06-03T13:20:05.839Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573531
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Menge TU
-- Column: C_Invoice_Detail.QtyTU
-- 2024-06-03T13:20:05.850Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573532
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungszeilendetails.Sektion
-- Column: C_Invoice_Detail.AD_Org_ID
-- 2024-06-03T13:20:05.860Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2024-06-03 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573533
;

-- UI Element: Rechnungsdisposition -> Rechnungskandidaten.Async Batch
-- Column: C_Invoice_Candidate.C_Async_Batch_ID
-- 2024-06-03T13:20:52.098Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-06-03 15:20:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=587064
;

--
-- tweak C_Flatrate_DataEntry completeIt and reactivateIt processes
-- 


--
--
--