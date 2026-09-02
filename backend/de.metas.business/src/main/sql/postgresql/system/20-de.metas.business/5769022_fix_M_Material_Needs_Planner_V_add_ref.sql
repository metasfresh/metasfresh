-- Run mode: SWING_CLIENT

-- Reference: PP_Order_Candidate target for M_Material_Needs_Planner_V
-- Table: PP_Order_Candidate
-- Key: PP_Order_Candidate.PP_Order_Candidate_ID
-- 2025-11-17T13:47:09.972Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1               from pp_order_candidate o               where PP_Order_Candidate.pp_order_candidate_id = o.pp_order_candidate_id                 AND o.m_product_id = @M_Product_ID/-1@                 AND o.m_warehouse_id = @M_Warehouse_ID/-1@                 AND o.m_hu_pi_item_product_id = @M_HU_PI_Item_Product_ID/-1@)',Updated=TO_TIMESTAMP('2025-11-17 13:47:09.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541983
;

