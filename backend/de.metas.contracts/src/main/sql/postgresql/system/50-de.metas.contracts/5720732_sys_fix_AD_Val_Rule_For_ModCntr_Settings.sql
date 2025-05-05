UPDATE AD_Val_Rule
SET Code='(C_Flatrate_Conditions_ID IN (SELECT c.C_Flatrate_Conditions_ID FROM C_Flatrate_Conditions c INNER JOIN ModCntr_Settings mc ON c.ModCntr_Settings_ID = mc.ModCntr_Settings_ID AND mc.M_Raw_Product_ID = @M_Product_ID@ WHERE c.ModCntr_Settings_ID IS NOT NULL AND c.DocStatus = ''CO'' AND c.type_conditions <> ''InterimInvoice''))',
    Updated=TO_TIMESTAMP('2023-08-22 11:44:21.401', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Val_Rule_ID = 540640
;
