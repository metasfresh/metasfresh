-- Column: C_Order.C_BPartner_SalesRep_ID
-- Column: C_Order.C_BPartner_SalesRep_ID
-- 2025-02-26T08:52:02.605Z
UPDATE AD_Column SET EntityType='de.metas.contracts.commission', FilterOperator='E',Updated=TO_TIMESTAMP('2025-02-26 09:52:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568785
;

-- Column: C_Order.C_BPartner_SalesRep_ID
-- Column: C_Order.C_BPartner_SalesRep_ID
-- 2025-02-26T08:56:16.918Z
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-02-26 09:56:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568785
;

-- UI Element: Auftrag_OLD -> Auftrag.C_BPartner_SalesRep_ID
-- Column: C_Order.C_BPartner_SalesRep_ID
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 20 -> Commission.C_BPartner_SalesRep_ID
-- Column: C_Order.C_BPartner_SalesRep_ID
-- 2025-02-26T08:57:30.341Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-02-26 09:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562100
;

-- UI Element: Auftrag_OLD -> Auftrag.Abw. Lieferanschrift
-- Column: C_Order.IsDropShip
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Abw. Lieferanschrift
-- Column: C_Order.IsDropShip
-- 2025-02-26T08:57:30.788Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-02-26 09:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544809
;

-- UI Element: Auftrag_OLD -> Auftrag.Abw. Kunde
-- Column: C_Order.DropShip_BPartner_ID
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Abw. Kunde
-- Column: C_Order.DropShip_BPartner_ID
-- 2025-02-26T08:57:31.235Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-02-26 09:57:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544810
;

-- UI Element: Auftrag_OLD -> Auftrag.Abladeort
-- Column: C_Order.HandOver_Location_ID
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Abladeort
-- Column: C_Order.HandOver_Location_ID
-- 2025-02-26T08:57:31.672Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-02-26 09:57:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548025
;

-- UI Element: Auftrag_OLD -> Auftrag.Zugesagter Liefertermin
-- Column: C_Order.DatePromised
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 20 -> Dates.Zugesagter Liefertermin
-- Column: C_Order.DatePromised
-- 2025-02-26T08:57:32.115Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-02-26 09:57:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000009
;

-- UI Element: Auftrag_OLD -> Auftrag.Preissystem
-- Column: C_Order.M_PricingSystem_ID
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> pricing.Preissystem
-- Column: C_Order.M_PricingSystem_ID
-- 2025-02-26T08:57:32.555Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-02-26 09:57:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000210
;

-- UI Element: Auftrag_OLD -> Auftrag.Währung
-- Column: C_Order.C_Currency_ID
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> pricing.Währung
-- Column: C_Order.C_Currency_ID
-- 2025-02-26T08:57:32.979Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-02-26 09:57:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541299
;

-- UI Element: Auftrag_OLD -> Auftrag.Belegstatus
-- Column: C_Order.DocStatus
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 20 -> Rest.Belegstatus
-- Column: C_Order.DocStatus
-- 2025-02-26T08:57:33.410Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-02-26 09:57:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000024
;

-- UI Element: Auftrag_OLD -> Auftrag.Verbucht
-- Column: C_Order.Posted
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 20 -> posted.Verbucht
-- Column: C_Order.Posted
-- 2025-02-26T08:57:33.864Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-02-26 09:57:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544788
;

-- UI Element: Auftrag_OLD -> Auftrag.Incoterm Standort
-- Column: C_Order.IncotermLocation
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> pricing.Incoterm Standort
-- Column: C_Order.IncotermLocation
-- 2025-02-26T08:57:34.290Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-02-26 09:57:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544771
;

-- UI Element: Auftrag_OLD -> Auftrag.Organisation
-- Column: C_Order.AD_Org_ID
-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 20 -> Org und Lager.Organisation
-- Column: C_Order.AD_Org_ID
-- 2025-02-26T08:57:34.725Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-02-26 09:57:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000069
;

