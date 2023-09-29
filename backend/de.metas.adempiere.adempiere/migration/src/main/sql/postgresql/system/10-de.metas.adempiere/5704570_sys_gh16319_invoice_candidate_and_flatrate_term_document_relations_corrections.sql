-- Run mode: SWING_CLIENT

-- 2023-09-29T13:45:19.519Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=541787, Name='C_FlatRate_Term->C_Invoice_Candidate',Updated=TO_TIMESTAMP('2023-09-29 16:45:19.519','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540190
;

-- Reference: RelType C_FlatRate_Term->C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-09-29T13:46:11.018Z
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN (select C_Invoice_Candidate_ID from C_Invoice_Candidate ic  where @C_Flatrate_Term_ID@ = ic.Record_ID AND ic.AD_table_ID = 540320 AND ic.C_Invoice_Candidate_ID != @C_Invoice_Candidate_ID/-1@)',Updated=TO_TIMESTAMP('2023-09-29 16:46:11.018','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=540753
;

-- Reference: RelType C_FlatRate_Term->C_Invoice_Candidate
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-09-29T13:46:35.314Z
UPDATE AD_Ref_Table SET AD_Window_ID=541517,Updated=TO_TIMESTAMP('2023-09-29 16:46:35.314','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=540753
;

-- 2023-09-29T13:47:22.610Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=540752,Updated=TO_TIMESTAMP('2023-09-29 16:47:22.595','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540194
;

-- Reference: RelType C_Invoice_Candidate->C_FlatRate_Term
-- Table: C_Flatrate_Term
-- Key: C_Flatrate_Term.C_Flatrate_Term_ID
-- 2023-09-29T13:48:50.982Z
UPDATE AD_Ref_Table SET WhereClause='C_Flatrate_Term_ID IN (SELECT ic.record_id from c_invoice_candidate AS ic where @C_Invoice_Candidate_ID@ = ic.C_Invoice_Candidate_ID AND ic.AD_table_ID = 540320; )',Updated=TO_TIMESTAMP('2023-09-29 16:48:50.982','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=540752
;

-- Reference: RelType C_Invoice_Candidate->C_FlatRate_Term
-- Table: C_Flatrate_Term
-- Key: C_Flatrate_Term.C_Flatrate_Term_ID
-- 2023-09-29T13:52:38.554Z
UPDATE AD_Ref_Table SET WhereClause='C_Flatrate_Term_ID IN (SELECT ic.record_id from c_invoice_candidate AS ic where @C_Invoice_Candidate_ID@ = ic.C_Invoice_Candidate_ID AND ic.AD_table_ID = 540320)',Updated=TO_TIMESTAMP('2023-09-29 16:52:38.554','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=540752
;

