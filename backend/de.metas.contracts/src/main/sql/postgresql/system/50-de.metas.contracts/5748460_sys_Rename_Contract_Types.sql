-- Run mode: SWING_CLIENT

-- Reference: Type_Conditions
-- Value: InterimInvoice
-- ValueName: InterimInvoice
-- 2025-03-04T14:49:31.488Z
UPDATE AD_Ref_List SET Name='Einkauf Vorfinanzierung',Updated=TO_TIMESTAMP('2025-03-04 16:49:31.488','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543260
;

-- 2025-03-04T14:49:31.493Z
UPDATE AD_Ref_List_Trl trl SET Name='Einkauf Vorfinanzierung' WHERE AD_Ref_List_ID=543260 AND AD_Language='de_DE'
;


-- Reference Item: Type_Conditions -> InterimInvoice_InterimInvoice
-- 2025-03-04T14:58:40.669Z
UPDATE AD_Ref_List_Trl SET Name='Einkauf Vorfinanzierung',Updated=TO_TIMESTAMP('2025-03-04 16:58:40.669','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543260
;

-- Reference: Type_Conditions
-- Value: ModularContract
-- ValueName: ModularContract
-- 2025-03-04T14:59:10.945Z
UPDATE AD_Ref_List SET Name='Einkauf Schlusszahlung und Verkauf',Updated=TO_TIMESTAMP('2025-03-04 16:59:10.945','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543483
;

-- 2025-03-04T14:59:10.949Z
UPDATE AD_Ref_List_Trl trl SET Name='Einkauf Schlusszahlung und Verkauf' WHERE AD_Ref_List_ID=543483 AND AD_Language='de_DE'
;

-- Reference Item: Type_Conditions -> ModularContract_ModularContract
-- 2025-03-04T14:59:34.058Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Einkauf Schlusszahlung und Verkauf',Updated=TO_TIMESTAMP('2025-03-04 16:59:34.058','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543483
;

-- Reference Item: Type_Conditions -> ModularContract_ModularContract
-- 2025-03-04T14:59:38.166Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-03-04 16:59:38.166','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543483
;

