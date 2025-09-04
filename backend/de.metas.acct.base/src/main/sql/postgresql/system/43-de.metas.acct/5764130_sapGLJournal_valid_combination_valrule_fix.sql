-- Run mode: SWING_CLIENT

-- Name: C_Valid_Combination_OnlyNonSpecificSet
-- old value: ... AND harvesting_year_id IS NULL AND userelementnumber1 IS NULL AND userelementnumber2 IS NULL)
-- 2025-08-27T10:51:40.556Z
UPDATE AD_Val_Rule SET Code='C_ValidCombination.C_ValidCombination_ID IN (SELECT C_ValidCombination_ID FROM C_ValidCombination WHERE AD_Client_ID = @AD_Client_ID@ AND C_AcctSchema_ID = @C_AcctSchema_ID@ AND AD_Org_ID IN (@AD_Org_ID@, 0) AND m_product_id IS NULL AND c_bpartner_id IS NULL AND ad_orgtrx_id IS NULL AND c_locfrom_id IS NULL AND c_locto_id IS NULL AND c_salesregion_id IS NULL AND c_project_id IS NULL AND c_campaign_id IS NULL AND c_activity_id IS NULL AND user1_id IS NULL AND user2_id IS NULL AND userelement1_id IS NULL AND userelement2_id IS NULL AND userelementstring1 IS NULL AND userelementstring2 IS NULL AND userelementstring3 IS NULL AND userelementstring4 IS NULL AND userelementstring5 IS NULL AND userelementstring6 IS NULL AND userelementstring7 IS NULL AND c_orderso_id IS NULL)',Updated=TO_TIMESTAMP('2025-08-27 10:51:40.554000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540695
;

