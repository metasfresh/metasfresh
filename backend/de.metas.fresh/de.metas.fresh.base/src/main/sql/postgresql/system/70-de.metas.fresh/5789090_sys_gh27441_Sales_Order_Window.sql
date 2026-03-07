-- Run mode: SWING_CLIENT

-- Field: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> Verfügbar
-- Column: C_OrderLine.InsufficientQtyAvailableForSalesColor_ID
-- 2026-02-18T13:54:35.691Z
UPDATE AD_Field SET AD_Name_ID=1762, Description='Ressource ist verfügbar', Help='Ressource ist verfügbar für Zuordnungen', Name='Verfügbar',Updated=TO_TIMESTAMP('2026-02-18 13:54:35.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=578421
;

-- 2026-02-18T13:54:35.747Z
UPDATE AD_Field_Trl trl SET Description='Ressource ist verfügbar',Help='Ressource ist verfügbar für Zuordnungen',Name='Verfügbar' WHERE AD_Field_ID=578421 AND AD_Language='de_DE'
;

-- 2026-02-18T13:54:35.807Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1762)
;

-- 2026-02-18T13:54:35.866Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=578421
;

-- 2026-02-18T13:54:35.922Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(578421)
;

-- Field: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> Verfügbare Menge
-- Column: C_OrderLine.QtyAvailableForSales
-- 2026-02-18T13:55:17.749Z
UPDATE AD_Field SET AD_Name_ID=584542, Description=NULL, Help=NULL, Name='Verfügbare Menge',Updated=TO_TIMESTAMP('2026-02-18 13:55:17.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=578420
;

-- 2026-02-18T13:55:17.808Z
UPDATE AD_Field_Trl trl SET Description=NULL,Name='Verfügbare Menge' WHERE AD_Field_ID=578420 AND AD_Language='de_DE'
;

-- 2026-02-18T13:55:17.866Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584542)
;

-- 2026-02-18T13:55:17.923Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=578420
;

-- 2026-02-18T13:55:17.980Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(578420)
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Zeile Nr.
-- Column: C_OrderLine.Line
-- 2026-02-18T13:56:04.940Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-02-18 13:56:04.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000033
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.C_OrderLine_QtyAvailableForSales
-- Column: C_OrderLine.QtyAvailableForSales
-- 2026-02-18T13:56:05.330Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-02-18 13:56:05.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=558191
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.InsufficientQtyAvailableForSalesColor_ID
-- Column: C_OrderLine.InsufficientQtyAvailableForSalesColor_ID
-- 2026-02-18T13:56:05.747Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-18 13:56:05.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=558190
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Produkt
-- Column: C_OrderLine.M_Product_ID
-- 2026-02-18T13:56:06.161Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-18 13:56:06.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000034
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Merkmale
-- Column: C_OrderLine.M_AttributeSetInstance_ID
-- 2026-02-18T13:56:06.590Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-02-18 13:56:06.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000035
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Gebindemenge
-- Column: C_OrderLine.QtyEnteredTU
-- 2026-02-18T13:56:06.938Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-02-18 13:56:06.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000036
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Zusagbar
-- Column: C_OrderLine.Qty_AvailableToPromise
-- 2026-02-18T13:56:07.299Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-02-18 13:56:07.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552222
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Gebinde
-- Column: C_OrderLine.M_HU_PI_Item_Product_ID
-- 2026-02-18T13:56:07.658Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-02-18 13:56:07.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000221
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.C_OrderLine_QtyEntered
-- Column: C_OrderLine.QtyEntered
-- 2026-02-18T13:56:07.998Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-02-18 13:56:07.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000037
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Produkt
-- Column: C_OrderLine.M_Product_ID
-- 2026-02-18T14:02:49.229Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-02-18 14:02:49.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000034
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.C_OrderLine_QtyAvailableForSales
-- Column: C_OrderLine.QtyAvailableForSales
-- 2026-02-18T14:02:49.583Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-18 14:02:49.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=558191
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.InsufficientQtyAvailableForSalesColor_ID
-- Column: C_OrderLine.InsufficientQtyAvailableForSalesColor_ID
-- 2026-02-18T14:02:49.933Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-18 14:02:49.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=558190
;
