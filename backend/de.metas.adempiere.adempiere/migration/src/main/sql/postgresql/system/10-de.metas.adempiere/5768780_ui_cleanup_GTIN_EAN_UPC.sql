-- Run mode: SWING_CLIENT

-- Field: Produkt(140,D) -> Produkt(180,D) -> UPC
-- Column: M_Product.UPC
-- 2025-09-12T18:45:22.934Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2025-09-12 18:45:22.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=1316
;

-- UI Element: Produkt(140,D) -> Produkt(180,D) -> main -> 10 -> No.UPC/ EAN
-- Column: M_Product.UPC
-- 2025-09-12T18:57:12.030Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2025-09-12 18:57:12.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000093
;

-- UI Element: Produkt(140,D) -> Produkt(180,D) -> main -> 10 -> No.EAN13-Produktcode
-- Column: M_Product.EAN13_ProductCode
-- 2025-09-12T18:57:38.208Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-09-12 18:57:38.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630680
;

-- UI Element: Geschäftspartner(123,D) -> Produkt(541077,D) -> main -> 10 -> default.UPC/EAN
-- Column: C_BPartner_Product.UPC
-- 2025-09-12T19:09:17.558Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2025-09-12 19:09:17.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551529
;

-- UI Element: Produkt(140,D) -> Geschäftspartner(562,D) -> main -> 10 -> default.EAN13-Produktcode
-- Column: C_BPartner_Product.EAN13_ProductCode
-- 2025-09-12T19:10:40.539Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-09-12 19:10:40.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630681
;

-- UI Element: Produkt(140,D) -> Geschäftspartner(562,D) -> main -> 10 -> default.EAN_CU
-- Column: C_BPartner_Product.EAN_CU
-- 2025-09-12T19:11:02.369Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-09-12 19:11:02.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563122
;

-- UI Element: Produkt(140,D) -> Geschäftspartner(562,D) -> main -> 10 -> default.UPC_CU
-- Column: C_BPartner_Product.UPC
-- 2025-09-12T19:11:18.392Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-09-12 19:11:18.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000150
;

-- Field: Produkt(140,D) -> Geschäftspartner(562,D) -> CU-EAN
-- Column: C_BPartner_Product.EAN_CU
-- 2025-09-12T19:11:44.234Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-09-12 19:11:44.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=589271
;

-- Field: Produkt(140,D) -> Geschäftspartner(562,D) -> CU-UPC
-- Column: C_BPartner_Product.UPC
-- 2025-09-12T19:11:55.491Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-09-12 19:11:55.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=552669
;

