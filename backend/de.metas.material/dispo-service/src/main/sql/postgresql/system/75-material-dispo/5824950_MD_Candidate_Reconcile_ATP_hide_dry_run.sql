-- Hide the "Testlauf" (IsDryRun) parameter from MD_Candidate_Reconcile_ATP: its preview
-- functionality is covered by the sibling MD_Candidate_ATP_Divergence_Report process, so
-- offering it as a separate operator choice on the writing process is redundant.
--
-- IsMandatory must be cleared to 'N' together with IsActive: an inactive AD_Process_Para
-- never gets an AD_PInstance_Para row (ADProcessDAO.retrieveProcessParameters() filters to
-- active rows only), so a still-mandatory hidden parameter makes every real invocation throw
-- FillMandatoryException("IsDryRun") at JavaProcess.loadParametersFromContext - the mandatory
-- check is presence-based, not IsActive-based, and never falls back to DefaultValue on the
-- executing path. The companion Java change (MD_Candidate_Reconcile_ATP.java, @Param mandatory
-- = false) relies on the same absent-row behaviour: with mandatory=false, a missing
-- AD_PInstance_Para row resolves the primitive boolean field to its Java default (false),
-- which matches DefaultValue='N' exactly, so the real-run-by-default behaviour is unchanged.
UPDATE AD_Process_Para SET IsActive='N', IsMandatory='N', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543314
;
