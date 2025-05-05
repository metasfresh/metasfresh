-- Run mode: SWING_CLIENT

-- Reference: ModCntr_Log_DocumentType
-- Value: DefinitiveInvoice
-- ValueName: Definitive Final Settlement
-- 2024-05-28T10:38:29.629Z
UPDATE AD_Ref_List SET Value='DefinitiveInvoice',ValueName='DefinitiveInvoice',Updated=TO_TIMESTAMP('2024-05-28 12:38:29.629','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543518
;

-- Reference Item: ModCntr_Log_DocumentType -> DefinitiveInvoice_Definitive Final Settlement
-- 2024-05-28T10:38:49.776Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Definitive Invoice',Updated=TO_TIMESTAMP('2024-05-28 12:38:49.776','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543518
;

-- Reference Item: ModCntr_Log_DocumentType -> DefinitiveInvoice_Definitive Final Settlement
-- 2024-05-28T10:39:18.764Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Definitive Schlusszahlung',Updated=TO_TIMESTAMP('2024-05-28 12:39:18.764','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543518
;

-- 2024-05-28T10:39:18.765Z
UPDATE AD_Ref_List SET Name='Definitive Schlusszahlung' WHERE AD_Ref_List_ID=543518
;

-- Reference Item: ModCntr_Log_DocumentType -> DefinitiveInvoice_Definitive Final Settlement
-- 2024-05-28T10:39:28.696Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Definitive Schlusszahlung',Updated=TO_TIMESTAMP('2024-05-28 12:39:28.696','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543518
;

-- Reference: ModCntr_Log_DocumentType
-- Value: FinalInvoice
-- ValueName: Final Settlement
-- 2024-05-28T10:40:03.605Z
UPDATE AD_Ref_List SET Name='Schlusszahlung', Value='FinalInvoice',ValueName='FinalInvoice',Updated=TO_TIMESTAMP('2024-05-28 12:40:03.605','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543517
;

-- 2024-05-28T10:40:03.607Z
UPDATE AD_Ref_List_Trl trl SET Name='Schlusszahlung' WHERE AD_Ref_List_ID=543517 AND AD_Language='de_DE'
;

-- Reference Item: ModCntr_Log_DocumentType -> FinalInvoice_Final Settlement
-- 2024-05-28T10:40:34.838Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Final Invoice',Updated=TO_TIMESTAMP('2024-05-28 12:40:34.838','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543517
;

-- Reference Item: ModCntr_Log_DocumentType -> FinalInvoice_Final Settlement
-- 2024-05-28T10:40:53.334Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Schlusszahlung',Updated=TO_TIMESTAMP('2024-05-28 12:40:53.334','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543517
;

-- Reference Item: ModCntr_Log_DocumentType -> FinalInvoice_Final Settlement
-- 2024-05-28T10:41:00.914Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-28 12:41:00.914','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543517
;

