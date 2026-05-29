--
-- tweak IC windows and C_Flatrate_Term windows
-- 

-- 2024-06-03T12:13:06.362Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Billing Record Details',Updated=TO_TIMESTAMP('2024-06-03 14:13:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585389
;

-- 2024-06-03T12:13:58.491Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Detail-Erfassung',Updated=TO_TIMESTAMP('2024-06-03 14:13:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585389
;

-- 2024-06-03T12:13:58.475Z
UPDATE AD_Process_Trl SET Name='Detail-Erfassung',Updated=TO_TIMESTAMP('2024-06-03 14:13:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585389
;

-- 2024-06-03T12:14:00.693Z
UPDATE AD_Process_Trl SET Name='Detail-Erfassung',Updated=TO_TIMESTAMP('2024-06-03 14:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585389
;

-- 2024-06-03T12:14:01.298Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-03 14:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585389
;

-- 2024-06-03T12:14:43.658Z
UPDATE AD_Process SET Description='Erfassung der Details zum gerade ausgewählten Abrechnungssatz', Help=NULL, Name='Detail-Erfassung',Updated=TO_TIMESTAMP('2024-06-03 14:14:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585389
;

-- 2024-06-03T12:14:43.646Z
UPDATE AD_Process_Trl SET Description='Erfassung der Details zum gerade ausgewählten Abrechnungssatz',Updated=TO_TIMESTAMP('2024-06-03 14:14:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585389
;

-- 2024-06-03T12:15:24.122Z
UPDATE AD_Process_Trl SET Description='Erfassung der Details zum gerade ausgewählten Abrechnungssatz',Updated=TO_TIMESTAMP('2024-06-03 14:15:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585389
;

-- 2024-06-03T12:15:44.421Z
UPDATE AD_Process_Trl SET Description='Enter details for the currently selected billing record',Updated=TO_TIMESTAMP('2024-06-03 14:15:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585389
;

-- Field: Rechnungsdisposition -> Rechnungszeilendetails -> Merkmale
-- Column: C_Invoice_Detail.M_AttributeSetInstance_ID
-- 2024-06-03T13:11:21.160Z
UPDATE AD_Field SET IsDisplayed='Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-06-03 15:11:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554849
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.M_AttributeSetInstance_ID
-- Column: C_Invoice_Detail.M_AttributeSetInstance_ID
-- 2024-06-03T13:12:18.588Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554849,0,540622,1000046,624787,'F',TO_TIMESTAMP('2024-06-03 15:12:18','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','N','N',0,'M_AttributeSetInstance_ID',55,0,0,TO_TIMESTAMP('2024-06-03 15:12:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.M_AttributeSetInstance_ID
-- Column: C_Invoice_Detail.M_AttributeSetInstance_ID
-- 2024-06-03T13:12:36.714Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624787
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Produktionsauftrag
-- Column: C_Invoice_Detail.PP_Order_ID
-- 2024-06-03T13:12:36.730Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000497
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Notiz
-- Column: C_Invoice_Detail.Note
-- 2024-06-03T13:12:36.744Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000498
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Beschreibung
-- Column: C_Invoice_Detail.Description
-- 2024-06-03T13:12:36.756Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000499
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Date
-- Column: C_Invoice_Detail.Date
-- 2024-06-03T13:12:36.770Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570137
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Percentage
-- Column: C_Invoice_Detail.Percentage
-- 2024-06-03T13:12:36.782Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000500
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Bestellte Menge in Preiseinheit
-- Column: C_Invoice_Detail.QtyEnteredInPriceUOM
-- 2024-06-03T13:12:36.795Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000501
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Preis
-- Column: C_Invoice_Detail.PriceEntered
-- 2024-06-03T13:12:36.806Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000502
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Einzelpreis
-- Column: C_Invoice_Detail.PriceActual
-- 2024-06-03T13:12:36.817Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000503
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Rabatt %
-- Column: C_Invoice_Detail.Discount
-- 2024-06-03T13:12:36.828Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000504
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Preiseinheit
-- Column: C_Invoice_Detail.Price_UOM_ID
-- 2024-06-03T13:12:36.839Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000505
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Menge
-- Column: C_Invoice_Detail.Qty
-- 2024-06-03T13:12:36.850Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000506
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Maßeinheit
-- Column: C_Invoice_Detail.C_UOM_ID
-- 2024-06-03T13:12:36.860Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000507
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Rechnung
-- Column: C_Invoice_Detail.C_Invoice_ID
-- 2024-06-03T13:12:36.875Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000508
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Rechnungsposition
-- Column: C_Invoice_Detail.C_InvoiceLine_ID
-- 2024-06-03T13:12:36.886Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000509
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Packvorschrift (TU)
-- Column: C_Invoice_Detail.M_TU_HU_PI_ID
-- 2024-06-03T13:12:36.898Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000510
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Menge TU
-- Column: C_Invoice_Detail.QtyTU
-- 2024-06-03T13:12:36.910Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000511
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Sektion
-- Column: C_Invoice_Detail.AD_Org_ID
-- 2024-06-03T13:12:36.921Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2024-06-03 15:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549106
;

-- Field: Rechnungsdisposition Einkauf -> Rechnungszeilendetails -> Merkmale
-- Column: C_Invoice_Detail.M_AttributeSetInstance_ID
-- 2024-06-03T13:13:07.869Z
UPDATE AD_Field SET IsDisplayed='Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-06-03 15:13:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=622499
;

-----
    
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