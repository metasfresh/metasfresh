-- Name: C_Flatrate_Conditions_Modular_Settings
-- 2023-10-20T15:27:07.543Z
UPDATE AD_Val_Rule SET Code='(C_Flatrate_Conditions_ID IN (SELECT c.C_Flatrate_Conditions_ID FROM C_Flatrate_Conditions c INNER JOIN ModCntr_Settings mc ON (c.ModCntr_Settings_ID = mc.ModCntr_Settings_ID AND mc.M_Product_ID = @M_Product_ID@ AND mc.c_year_id = @Harvesting_Year_ID@) WHERE c.ModCntr_Settings_ID IS NOT NULL AND c.DocStatus = ''CO''  AND mc.issotrx = ''@IsSOTrx@''))',Updated=TO_TIMESTAMP('2023-10-20 16:27:07.541','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540640
;

-- Name: Harvesting Year (Selected Calendar)
-- 2023-10-23T09:12:56.588Z
UPDATE AD_Val_Rule SET Name='Harvesting Year (Selected Calendar)',Updated=TO_TIMESTAMP('2023-10-23 10:12:56.584','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540647
;

