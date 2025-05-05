-- Run mode: SWING_CLIENT

-- Name: ModCntr_Type for Processed Product
-- 2024-05-10T15:57:07.828Z
UPDATE AD_Val_Rule SET Code='@M_Product_ID/-1@ = @M_Processed_Product_ID/-1@ OR modcntr_type.modularcontracthandlertype != ''SalesOnProcessedProduct''',Updated=TO_TIMESTAMP('2024-05-10 18:57:07.827','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540674
;

