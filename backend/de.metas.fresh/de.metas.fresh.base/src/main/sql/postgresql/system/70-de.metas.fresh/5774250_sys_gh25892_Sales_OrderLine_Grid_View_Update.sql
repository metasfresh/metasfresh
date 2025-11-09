-- Run mode: SWING_CLIENT

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Grund
-- Column: C_OrderLine.Reason
-- 2025-10-23T09:11:41.619Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-10-23 09:11:41.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635383
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Ohne Berechnung
-- Column: C_OrderLine.IsWithoutCharge
-- 2025-10-23T09:11:41.993Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-10-23 09:11:41.993000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635382
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Preis
-- Column: C_OrderLine.PriceEntered
-- 2025-10-23T09:11:42.356Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-10-23 09:11:42.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000040
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Preiseinheit
-- Column: C_OrderLine.Price_UOM_ID
-- 2025-10-23T09:11:42.724Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-10-23 09:11:42.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000041
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.InvoicableQtyBasedOn
-- Column: C_OrderLine.InvoicableQtyBasedOn
-- 2025-10-23T09:11:43.087Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-10-23 09:11:43.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=560381
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Menge in Preiseinheit
-- Column: C_OrderLine.QtyEnteredInPriceUOM
-- 2025-10-23T09:11:43.455Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-10-23 09:11:43.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000042
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Rabatt %
-- Column: C_OrderLine.Discount
-- 2025-10-23T09:11:43.819Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-10-23 09:11:43.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000043
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Manueller Rabatt
-- Column: C_OrderLine.IsManualDiscount
-- 2025-10-23T09:11:44.193Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-10-23 09:11:44.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=578118
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Netto
-- Column: C_OrderLine.PriceActual
-- 2025-10-23T09:11:44.557Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-10-23 09:11:44.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000044
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Zeilensumme
-- Column: C_OrderLine.LineNetAmt
-- 2025-10-23T09:11:44.917Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2025-10-23 09:11:44.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000045
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Ertrag netto
-- Column: C_OrderLine.ProfitPriceActual
-- 2025-10-23T09:11:45.286Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2025-10-23 09:11:45.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552431
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Marge %
-- Column: C_OrderLine.ProfitPercent
-- 2025-10-23T09:11:45.652Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2025-10-23 09:11:45.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552430
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Auszeichnungspreis
-- Column: C_OrderLine.PriceList
-- 2025-10-23T09:11:46.193Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2025-10-23 09:11:46.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000046
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Steuer
-- Column: C_OrderLine.C_Tax_ID
-- 2025-10-23T09:11:46.886Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2025-10-23 09:11:46.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000047
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Beschreibung
-- Column: C_OrderLine.Description
-- 2025-10-23T09:11:47.243Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2025-10-23 09:11:47.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549097
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Abo-Vertragsbedingungen
-- Column: C_OrderLine.C_Flatrate_Conditions_ID
-- 2025-10-23T09:11:47.607Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2025-10-23 09:11:47.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000039
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Pauschale - Vertragsperiode
-- Column: C_OrderLine.C_Flatrate_Term_ID
-- 2025-10-23T09:11:47.971Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2025-10-23 09:11:47.971000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=601388
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Gruppen Zeile
-- Column: C_OrderLine.IsGroupCompensationLine
-- 2025-10-23T09:11:48.333Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2025-10-23 09:11:48.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549127
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Gruppe
-- Column: C_OrderLine.C_Order_CompensationGroup_ID
-- 2025-10-23T09:11:48.695Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2025-10-23 09:11:48.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549126
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Preisminderung Art
-- Column: C_OrderLine.GroupCompensationType
-- 2025-10-23T09:11:49.058Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2025-10-23 09:11:49.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549129
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Preisminderung Betrag Typ
-- Column: C_OrderLine.GroupCompensationAmtType
-- 2025-10-23T09:11:49.430Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2025-10-23 09:11:49.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549130
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Gruppen Rabatt
-- Column: C_OrderLine.GroupCompensationPercentage
-- 2025-10-23T09:11:49.792Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2025-10-23 09:11:49.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549128
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.No Price Conditions
-- Column: C_OrderLine.NoPriceConditionsColor_ID
-- 2025-10-23T09:11:50.157Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=310,Updated=TO_TIMESTAMP('2025-10-23 09:11:50.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551414
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Zahlungsbedingung
-- Column: C_OrderLine.C_PaymentTerm_Override_ID
-- 2025-10-23T09:11:50.522Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=320,Updated=TO_TIMESTAMP('2025-10-23 09:11:50.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=550143
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Skonto %
-- Column: C_OrderLine.PaymentDiscount
-- 2025-10-23T09:11:50.887Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=330,Updated=TO_TIMESTAMP('2025-10-23 09:11:50.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552486
;

