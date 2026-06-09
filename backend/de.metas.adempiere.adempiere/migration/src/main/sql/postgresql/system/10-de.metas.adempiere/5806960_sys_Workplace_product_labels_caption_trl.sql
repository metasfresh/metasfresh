-- Workplace window: make the product and external-system Labels fields translatable.
-- They had an English base Name and no AD_Name_ID, so a Labels caption (resolved via
-- AD_Name_ID -> AD_Element, since there is no AD_UI_Element_Trl table) showed English in
-- every language. Point each at its existing translated column element, and set the German
-- base Name as a fallback (consistent with the other Labels fields on this window).

-- Product -> M_Product_ID (454: Produkt / Product)
UPDATE AD_UI_Element SET AD_Name_ID=454, Name='Produkt', Updated=now(), UpdatedBy=100 WHERE AD_UI_Element_ID=638746
;

-- Product Category -> M_Product_Category_ID (453: Produkt Kategorie / Product Category)
UPDATE AD_UI_Element SET AD_Name_ID=453, Name='Produkt Kategorie', Updated=now(), UpdatedBy=100 WHERE AD_UI_Element_ID=638739
;

-- Carrier Product -> Carrier_Product_ID (584116: Lieferweg-Produkt / Carrier Product)
UPDATE AD_UI_Element SET AD_Name_ID=584116, Name='Lieferweg-Produkt', Updated=now(), UpdatedBy=100 WHERE AD_UI_Element_ID=638740
;

-- External Systems -> ExternalSystem_ID (583968: Externes System / External System)
UPDATE AD_UI_Element SET AD_Name_ID=583968, Name='Externes System', Updated=now(), UpdatedBy=100 WHERE AD_UI_Element_ID=638738
;
