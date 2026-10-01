-- 5823290 (MD_Candidate_Reconcile_ATP) and 5823400 (MD_Candidate_ATP_Divergence_Report), both already
-- integrated, gave the M_Product_ID process parameter AD_Reference_ID=19 (TableDir), the same as
-- M_Warehouse_ID / M_Product_Category_ID on those same processes. That renders as a plain dropdown that
-- loads the whole product list, which is unusable to find a specific product on an instance with a large
-- catalog. The established convention elsewhere for an M_Product_ID process parameter is AD_Reference_ID=30
-- (Search) with IsAutocomplete='Y' (e.g. AD_Process_Para_ID 540999, 540778, 541008) -- M_Warehouse_ID and
-- M_Product_Category_ID keep TableDir elsewhere too (e.g. 541861, 540779), so those two are left unchanged.

UPDATE AD_Process_Para SET AD_Reference_ID=30, IsAutocomplete='Y', Updated=TO_TIMESTAMP('2026-09-16 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543312, 543318) AND ColumnName='M_Product_ID'
;
