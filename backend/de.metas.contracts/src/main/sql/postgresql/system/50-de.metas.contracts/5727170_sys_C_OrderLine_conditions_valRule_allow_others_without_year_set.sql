-- Name: C_Flatrate_Conditions_Modular_Settings
-- 2024-06-24T16:15:04.901Z
UPDATE AD_Val_Rule SET Code='(C_Flatrate_Conditions.C_Flatrate_Conditions_ID IN
(
(SELECT c.C_Flatrate_Conditions_ID
FROM C_Flatrate_Conditions c
INNER JOIN ModCntr_Settings mc ON  @Harvesting_Year_ID@>0 AND mc.ModCntr_Settings_ID = c.ModCntr_Settings_ID AND mc.M_Raw_Product_ID = @M_Product_ID@ AND mc.C_Year_ID = @Harvesting_Year_ID@
WHERE c.ModCntr_Settings_ID IS NOT NULL AND c.DocStatus = ''CO'' AND c.type_conditions <> ''InterimInvoice''
)
UNION ALL
(SELECT C_Flatrate_Conditions_ID
FROM C_Flatrate_Conditions
WHERE @Harvesting_Year_ID@<=0 AND type_conditions NOT IN (''InterimInvoice'', ''ModularContract'') AND DocStatus = ''CO''
)
)
)',Updated=TO_TIMESTAMP('2024-06-24 18:15:04.898','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540640
;

