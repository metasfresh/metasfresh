-- Hide the "Testlauf" (IsDryRun) parameter from MD_Candidate_Reconcile_ATP: its preview
-- functionality is covered by the sibling MD_Candidate_ATP_Divergence_Report process, so
-- offering it as a separate operator choice on the writing process is redundant.
--
-- The load-bearing fix for FillMandatoryException("IsDryRun") is the companion Java change
-- (MD_Candidate_Reconcile_ATP.java, @Param mandatory = false) - the actual mandatory check
-- (ProcessClassInfo.createProcessClassParamInfo) reads only the Java @Param annotation, never
-- this table's IsMandatory column. IsMandatory is cleared to 'N' here only for dictionary
-- consistency (a hidden/inactive parameter that still claims to be mandatory reads as
-- self-contradictory metadata to a future reader), not because this column gates the runtime
-- check.
UPDATE AD_Process_Para SET IsActive='N', IsMandatory='N', Updated=TO_TIMESTAMP('2026-09-17 20:14:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_Para_ID=543314
;
