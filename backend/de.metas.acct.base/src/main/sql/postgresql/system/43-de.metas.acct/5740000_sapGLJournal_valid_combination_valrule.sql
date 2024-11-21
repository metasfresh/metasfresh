-- Run mode: SWING_CLIENT

-- Name: C_Valid_Combination_OnlyNonSpecificSet
-- 2024-11-20T16:19:12.928Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540695,'C_ValidCombination.C_ValidCombination_ID IN (SELECT C_ValidCombination_ID FROM C_ValidCombination WHERE AD_Client_ID = @AD_Client_ID@ AND C_AcctSchema_ID = @C_AcctSchema_ID@ AND AD_Org_ID IN (@AD_Org_ID@, 0) AND m_product_id IS NULL AND c_bpartner_id IS NULL AND ad_orgtrx_id IS NULL AND c_locfrom_id IS NULL AND c_locto_id IS NULL AND c_salesregion_id IS NULL AND c_project_id IS NULL AND c_campaign_id IS NULL AND c_activity_id IS NULL AND user1_id IS NULL AND user2_id IS NULL AND userelement1_id IS NULL AND userelement2_id IS NULL AND userelementstring1 IS NULL AND userelementstring2 IS NULL AND userelementstring3 IS NULL AND userelementstring4 IS NULL AND userelementstring5 IS NULL AND userelementstring6 IS NULL AND userelementstring7 IS NULL AND c_orderso_id IS NULL AND harvesting_year_id IS NULL AND userelementnumber1 IS NULL AND userelementnumber2 IS NULL)',TO_TIMESTAMP('2024-11-20 17:19:12.729','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.acct','Y','C_Valid_Combination_OnlyNonSpecificSet','S',TO_TIMESTAMP('2024-11-20 17:19:12.729','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: SAP_GLJournalLine.C_ValidCombination_ID
-- 2024-11-20T16:33:29.207Z
UPDATE AD_Column SET AD_Val_Rule_ID=540695,Updated=TO_TIMESTAMP('2024-11-20 17:33:29.207','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=585392
;

-- Field: SAP Buchführungs Journal(541656,de.metas.acct) -> SAP Buchführungs Journal Line(546731,de.metas.acct) -> Parent
-- Column: SAP_GLJournalLine.Parent_ID
-- 2024-11-20T16:23:51.820Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-11-20 17:23:51.82','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=710048
;
