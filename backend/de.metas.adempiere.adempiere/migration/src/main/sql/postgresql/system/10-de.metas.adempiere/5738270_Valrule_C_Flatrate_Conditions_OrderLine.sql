-- Run mode: SWING_CLIENT

-- Name: C_Flatrate_Conditions for order line
-- 2024-10-31T13:37:48.239Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540693,'(@C_DocTypeTarget_ID/-1@ NOT IN (541034,541062,541113,541112)) AND (C_Flatrate_Conditions.C_Flatrate_Conditions_ID IN ( (SELECT c.C_Flatrate_Conditions_ID FROM C_Flatrate_Conditions c INNER JOIN ModCntr_Settings mc ON @Harvesting_Year_ID/0@>0 AND mc.ModCntr_Settings_ID = c.ModCntr_Settings_ID AND mc.M_Raw_Product_ID = @M_Product_ID@ AND mc.C_Year_ID = @Harvesting_Year_ID/0@ AND mc.isSOTrx = ''@IsSOTrx@'' WHERE c.ModCntr_Settings_ID IS NOT NULL AND c.DocStatus = ''CO'' AND c.type_conditions ''InterimInvoice'' ) UNION ALL (SELECT C_Flatrate_Conditions_ID FROM C_Flatrate_Conditions WHERE @Harvesting_Year_ID/0@=0 AND type_conditions NOT IN (''InterimInvoice'', ''ModularContract'') AND DocStatus = ''CO'' ) ) )',TO_TIMESTAMP('2024-10-31 15:37:47.981','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','C_Flatrate_Conditions for order line','S',TO_TIMESTAMP('2024-10-31 15:37:47.981','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: C_OrderLine.C_Flatrate_Conditions_ID
-- 2024-10-31T13:40:48.630Z
UPDATE AD_Column SET AD_Val_Rule_ID=540693,Updated=TO_TIMESTAMP('2024-10-31 15:40:48.63','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=540003
;


-- SysConfig Name: OrderLineQuickInputDescriptorFactory.FilterFlatrateConditionsADValRule
-- SysConfig Value: 540693
-- SysConfig Value (old): 540640
-- 2024-10-31T14:24:29.437Z
UPDATE AD_SysConfig SET Value='540693',Updated=TO_TIMESTAMP('2024-10-31 16:24:29.435','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541640
;

-- Name: C_Flatrate_Conditions for order line
-- 2024-10-31T14:51:08.406Z
UPDATE AD_Val_Rule SET Code='(NOT EXISTS (SELECT 1 from C_DocType dt where dt.C_DocType_ID = @C_DocTypeTarget_ID/-1@  AND dt.docsubtype in (''PF'',''CAO'' )))  AND (C_Flatrate_Conditions.C_Flatrate_Conditions_ID IN ( (SELECT c.C_Flatrate_Conditions_ID FROM C_Flatrate_Conditions c INNER JOIN ModCntr_Settings mc ON @Harvesting_Year_ID/0@>0 AND mc.ModCntr_Settings_ID = c.ModCntr_Settings_ID AND mc.M_Raw_Product_ID = @M_Product_ID@ AND mc.C_Year_ID = @Harvesting_Year_ID/0@ AND mc.isSOTrx = ''@IsSOTrx@'' WHERE c.ModCntr_Settings_ID IS NOT NULL AND c.DocStatus = ''CO'' AND c.type_conditions !=''InterimInvoice'' ) UNION ALL (SELECT C_Flatrate_Conditions_ID FROM C_Flatrate_Conditions WHERE @Harvesting_Year_ID/0@=0 AND type_conditions NOT IN (''InterimInvoice'', ''ModularContract'') AND DocStatus = ''CO'' ) ) )',Updated=TO_TIMESTAMP('2024-10-31 16:51:08.404','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540693
;

