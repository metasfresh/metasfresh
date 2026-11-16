-- MD_Candidate.MD_Candidate_BusinessCase is varchar(15) (AD_Column_ID 556485). Script 5823960 (already
-- integrated on this branch) inserted Value='ATP_RECONCILIATION' - 18 characters, silently truncated to
-- 'ATP_RECONCILIAT' on write, so CandidateBusinessCase.ofCode() throws on every read-back. Per Migration
-- Script Immutability, 5823960 is left untouched; this follow-up corrects the value to fit the column.

UPDATE AD_Ref_List SET Value='ATP_RECONCILE', Updated=TO_TIMESTAMP('2026-09-10 22:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544366
;
