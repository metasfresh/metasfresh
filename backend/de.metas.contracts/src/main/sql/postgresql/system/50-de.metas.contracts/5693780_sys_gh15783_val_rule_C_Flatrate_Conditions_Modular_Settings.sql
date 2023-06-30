-- Name: C_Flatrate_Conditions_Modular_Settings
-- 2023-06-29T11:08:34.992952100Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540640,'(C_Flatrate_Conditions_ID IN (SELECT c.C_Flatrate_Conditions_ID
        FROM C_Flatrate_Conditions c
                 INNER JOIN ModCntr_Settings mc ON c.ModCntr_Settings_ID = mc.ModCntr_Settings_ID
                   AND mc.M_Product_ID = @M_Product_ID@
                   AND c.DocStatus = ''CO''
        WHERE c.ModCntr_Settings_ID IS NOT NULL
          AND mc.C_Year_ID = (SELECT o.C_Year_ID
                              FROM C_Order o
                              WHERE o.C_Order_ID = @C_Order_ID@)))',TO_TIMESTAMP('2023-06-29 14:08:34.791','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','C_Flatrate_Conditions_Modular_Settings','S',TO_TIMESTAMP('2023-06-29 14:08:34.791','YYYY-MM-DD HH24:MI:SS.US'),100)
;

