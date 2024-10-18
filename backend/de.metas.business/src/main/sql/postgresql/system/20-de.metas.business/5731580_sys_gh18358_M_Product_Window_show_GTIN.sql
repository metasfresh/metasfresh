
-- take GTIN from advanced edit to center-stage and put UPC to advanced edit.
-- UPC was never really used by us so far, but GTIN is    

-- UI Element: Produkt -> Produkt.UPC/ EAN
-- Column: M_Product.UPC
-- UI Element: Produkt(140,D) -> Produkt(180,D) -> main -> 10 -> No.UPC/ EAN
-- Column: M_Product.UPC
-- 2024-08-21T09:39:59.346Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-08-21 11:39:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000093
;

-- UI Element: Produkt -> Produkt.GTIN
-- Column: M_Product.GTIN
-- UI Element: Produkt(140,D) -> Produkt(180,D) -> main -> 10 -> No.GTIN
-- Column: M_Product.GTIN
-- 2024-08-21T09:40:07.018Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2024-08-21 11:40:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563218
;

