-- Name: ModCntr_Type for Processed Product and isSOTrx
-- 2024-09-24T07:51:38.295Z
UPDATE AD_Val_Rule SET Code='((@M_Product_ID/-1@ = @M_Processed_Product_ID/-1@ OR modcntr_type.modularcontracthandlertype != ''SalesOnProcessedProduct'') AND ModCntr_Type.IsSOTrx = ''@IsSOTrx@'')',Updated=TO_TIMESTAMP('2024-09-24 09:51:38.292','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540674
;

-- Column: ModCntr_Settings.IsSOTrx
-- 2024-09-25T17:16:04.390Z
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2024-09-25 19:16:04.39','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587164
;
