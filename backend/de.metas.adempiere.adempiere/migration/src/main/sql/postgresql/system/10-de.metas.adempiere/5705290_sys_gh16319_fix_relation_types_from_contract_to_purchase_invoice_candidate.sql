-- Run mode: SWING_CLIENT

-- Name: C_FlatRate_Term->C_Invoice_Candidate
-- Source Reference: C_Flatrate_Term_Source
-- Target Reference: RelType C_FlatRate_Term->C_Invoice_Candidate
-- 2023-10-05T09:47:36.312Z
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-05 12:47:36.312','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540190
;

-- Column: C_Invoice_Candidate.Record_ID
-- 2023-10-05T09:48:08.218Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2023-10-05 12:48:08.218','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=546178
;

-- Name: Purchase Invoice Candidate target
-- Source Reference: -
-- Target Reference: C_Invoice_Candidate Purchase
-- 2023-10-05T10:24:45.338Z
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-05 13:24:45.338','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540275
;

