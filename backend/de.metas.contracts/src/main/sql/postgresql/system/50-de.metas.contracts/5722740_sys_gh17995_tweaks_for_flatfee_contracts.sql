-- Column: C_Flatrate_Term.M_Product_ID
-- 2024-04-24T12:51:42.607Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2024-04-24 14:51:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547283
;

-- Column: C_Flatrate_Conditions.M_Product_Flatrate_ID
-- 2024-04-24T12:53:32.884Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2024-04-24 14:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545654
;

-- allow FlatFee contracts to be created via C_Flatrate_Term_Create_For_BPartners
update AD_Val_Rule set code='C_Flatrate_Conditions.Type_Conditions NOT IN (''HoldingFee'', ''Subscr'', ''Procuremnt'')' where  AD_Val_Rule_ID=540237;
