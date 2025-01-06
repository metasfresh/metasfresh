-- Run mode: SWING_CLIENT

-- Column: C_Flatrate_Term.M_Product_ID
-- 2024-04-30T11:55:14.184Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-04-30 14:55:14.184','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=547283
;

-- Run mode: SWING_CLIENT

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Maßeinheit
-- Column: C_InvoiceLine.C_UOM_ID
-- 2024-04-30T14:20:08.219Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,3365,0,291,624715,540219,'F',TO_TIMESTAMP('2024-04-30 17:20:08.059','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',31,0,0,TO_TIMESTAMP('2024-04-30 17:20:08.059','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Maßeinheit
-- Column: C_InvoiceLine.C_UOM_ID
-- 2024-04-30T14:20:17.213Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-04-30 17:20:17.212','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624715
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Preis
-- Column: C_InvoiceLine.PriceEntered
-- 2024-04-30T14:20:17.216Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-04-30 17:20:17.216','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=542665
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Kostenstelle
-- Column: C_InvoiceLine.C_Activity_ID
-- 2024-04-30T14:20:17.219Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-04-30 17:20:17.219','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=542666
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Beschreibung
-- Column: C_InvoiceLine.Description
-- 2024-04-30T14:20:17.224Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-04-30 17:20:17.224','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=542667
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Steuer
-- Column: C_InvoiceLine.C_Tax_ID
-- 2024-04-30T14:20:17.226Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-04-30 17:20:17.226','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=542668
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Zeilennetto
-- Column: C_InvoiceLine.LineNetAmt
-- 2024-04-30T14:20:17.229Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-04-30 17:20:17.229','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=542669
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Packaging Material
-- Column: C_InvoiceLine.IsPackagingMaterial
-- 2024-04-30T14:20:17.231Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-04-30 17:20:17.231','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=542670
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Project
-- Column: C_InvoiceLine.C_Project_ID
-- 2024-04-30T14:20:17.234Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-04-30 17:20:17.234','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=573745
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Section Code
-- Column: C_InvoiceLine.M_SectionCode_ID
-- 2024-04-30T14:20:17.237Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-04-30 17:20:17.237','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=611509
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Sektion
-- Column: C_InvoiceLine.AD_Org_ID
-- 2024-04-30T14:20:17.240Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-04-30 17:20:17.24','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=549111
;

