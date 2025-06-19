-- Run mode: SWING_CLIENT

-- Name: Purchase_Modular_Flatrate_Term_ID
-- 2025-06-19T13:46:31.791Z
UPDATE AD_Val_Rule SET Code='C_Flatrate_Term.C_Flatrate_Term_ID IN (SELECT ft.C_Flatrate_Term_ID FROM C_Flatrate_Term ft INNER JOIN C_Flatrate_Conditions c ON ft.c_flatrate_conditions_id = c.c_flatrate_conditions_id INNER JOIN ModCntr_Settings mc ON @Harvesting_Year_ID/0@>0 AND mc.ModCntr_Settings_ID = c.ModCntr_Settings_ID AND (mc.M_Raw_Product_ID = @M_Product_ID@ OR mc.M_Processed_Product_ID = @M_Product_ID@) AND mc.C_Year_ID = @Harvesting_Year_ID/0@ AND mc.isSOTrx = ''N'' INNER JOIN C_Order o ON ft.c_order_term_id = o.c_order_id AND o.m_warehouse_id = @M_Warehouse_ID@ WHERE ft.type_conditions = ''ModularContract'' AND ft.isSOTrx = ''N'' AND ft.DocStatus = ''CO'' )',Updated=TO_TIMESTAMP('2025-06-19 15:46:31.79','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540694
;

