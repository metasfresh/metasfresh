-- 2024-02-01T16:21:17.575Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541697,'O',TO_TIMESTAMP('2024-02-01 17:21:17','YYYY-MM-DD HH24:MI:SS'),100,'If Y and C_Invoice_Candidates are enqueued, then metasfresh asserts that these ICs'' LineNetAmt and C_Tax_ID are not changed on-the-fly by masterdata-changes that did not reflect in the ICs before.
(see de.metas.invoicecandidate.api.impl.InvoiceCandidatesChangesChecker.InvoiceCandidateInfo#checkEquals)
This is useful in order to guard against users inadvertendly changing pricing master data (for a current PLV!) while existing ICs were already created and validated by other users.
However, the check can cause problems in case of mass-invoicings, when the ICs to invoice were just created, but their very first update did not yet take place at the time the enqueing started. ','de.metas.invoicecandidate','Y','de.metas.invoicecandidate.api.impl.InvoiceCandidateEnqueuer.assertNoChanges',TO_TIMESTAMP('2024-02-01 17:21:17','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2024-02-01T16:24:26.637Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='If Y and C_Invoice_Candidates are enqueued, then metasfresh asserts that in these ICs'', LineNetAmt is not changed on-the-fly by masterdata-changes that did not reflect in the ICs before.
(see de.metas.invoicecandidate.api.impl.InvoiceCandidatesChangesChecker.InvoiceCandidateInfo#checkEquals)
This is useful in order to guard against users inadvertendly changing pricing master data (for a current PLV!) while existing ICs were already created and validated by other users.
However, the check can cause problems in case of mass-invoicings, when the ICs to invoice were just created, but their very first update did not yet take place at the time the enqueing started. ', Name='de.metas.invoicecandidate.api.impl.InvoiceCandidateEnqueuer.FailOnChanges',Updated=TO_TIMESTAMP('2024-02-01 17:24:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541697
;

