-- Process: PP_Order_Candidate_EnqueueSelectionForOrdering(org.eevolution.productioncandidate.process.PP_Order_Candidate_EnqueueSelectionForOrdering)
-- Table: PP_Order_Candidate
-- Window: Produktionsdisposition(541316,EE01)
-- EntityType: EE01
-- 2024-01-31T07:45:10.949Z
UPDATE AD_Table_Process SET AD_Window_ID=541316,Updated=TO_TIMESTAMP('2024-01-31 09:45:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541015
;

-- Reference: RelType PP_Order -> PP_Order_Candidate
-- Table: PP_Order_Candidate
-- Key: PP_Order_Candidate.PP_Order_Candidate_ID
-- 2024-01-31T07:51:47.242Z
UPDATE AD_Ref_Table SET WhereClause='exists(     select 1 from pp_order_candidate cand     inner join pp_ordercandidate_pp_order alloc on cand.pp_order_candidate_id = alloc.pp_order_candidate_id     inner join pp_order o on alloc.pp_order_id = o.pp_order_id     where alloc.pp_order_id = @PP_Order_ID@     and pp_order_candidate.pp_order_candidate_id = cand.pp_order_candidate_id and cand.isMaturing=''N''      )',Updated=TO_TIMESTAMP('2024-01-31 09:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541493
;

-- 2024-01-31T07:52:11.510Z
UPDATE AD_RelationType SET Name='PP_Order -> PP_Order_Candidate (Manufacturing)',Updated=TO_TIMESTAMP('2024-01-31 09:52:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540328
;

-- Name: RelType PP_Order -> PP_Order_Candidate (Manufacturing)
-- 2024-01-31T07:54:01.135Z
UPDATE AD_Reference SET Name='RelType PP_Order -> PP_Order_Candidate (Manufacturing)',Updated=TO_TIMESTAMP('2024-01-31 09:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541493
;

