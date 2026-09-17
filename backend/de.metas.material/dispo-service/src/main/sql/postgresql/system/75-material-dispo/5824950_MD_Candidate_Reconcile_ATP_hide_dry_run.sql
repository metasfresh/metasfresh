-- Hide the "Testlauf" (IsDryRun) parameter from MD_Candidate_Reconcile_ATP: its preview
-- functionality is covered by the sibling MD_Candidate_ATP_Divergence_Report process, so
-- offering it as a separate operator choice on the writing process is redundant. Leaving
-- IsActive='N' (rather than deleting the row) keeps the Java @Param binding intact -
-- DefaultValue='N' is unchanged, so the process continues to run as a real, data-writing
-- reconciliation exactly as it already does when the (now-hidden) checkbox is left unchecked.
UPDATE AD_Process_Para SET IsActive='N', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543314
;
