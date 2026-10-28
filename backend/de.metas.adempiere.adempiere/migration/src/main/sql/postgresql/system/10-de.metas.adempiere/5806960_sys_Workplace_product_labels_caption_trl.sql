-- Workplace window: make every restriction Labels field translatable.
-- They had no AD_Name_ID, so a Labels caption (resolved via AD_Name_ID -> AD_Element, since
-- there is no AD_UI_Element_Trl table) was not translatable (the product/external-system ones
-- even showed their English base Name in every language). Point each at its existing translated
-- column element, and set the German base Name as a fallback.

-- Product -> M_Product_ID (454: Produkt / Product)
UPDATE AD_UI_Element SET AD_Name_ID=454, Name='Produkt', Updated=TO_TIMESTAMP('2026-06-09 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=638746
;

-- Product Category -> M_Product_Category_ID (453: Produkt Kategorie / Product Category)
UPDATE AD_UI_Element SET AD_Name_ID=453, Name='Produkt Kategorie', Updated=TO_TIMESTAMP('2026-06-09 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=638739
;

-- Carrier Product -> Carrier_Product_ID (584116: Lieferweg-Produkt / Carrier Product)
UPDATE AD_UI_Element SET AD_Name_ID=584116, Name='Lieferweg-Produkt', Updated=TO_TIMESTAMP('2026-06-09 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=638740
;

-- External Systems -> ExternalSystem_ID (583968: Externes System / External System)
UPDATE AD_UI_Element SET AD_Name_ID=583968, Name='Externes System', Updated=TO_TIMESTAMP('2026-06-09 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=638738
;

-- BPartner Group -> C_BP_Group_ID (1383: Geschäftspartnergruppe / Business Partner Group)
UPDATE AD_UI_Element SET AD_Name_ID=1383, Name='Geschäftspartnergruppe', Updated=TO_TIMESTAMP('2026-06-09 10:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=652032
;

-- Document Type -> C_DocType_ID (196: Belegart / Document Type)
UPDATE AD_UI_Element SET AD_Name_ID=196, Name='Belegart', Updated=TO_TIMESTAMP('2026-06-09 10:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=652033
;

-- Carrier_Product_ID (584116) had en_US IsTranslated='N', so its English caption fell back to the German base name. Mark it translated (the en_US text 'Carrier Product' is already correct).
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-09 10:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584116 AND AD_Language='en_US'
;
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-09 10:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=756177 AND AD_Language='en_US'
;
