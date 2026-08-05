-- SysConfig: max number of seconds the invoice-candidate recompute wait polls for a selection's invoice
-- candidates to be recomputed before aborting. Default 3600 (one hour); lower it to make a wedged
-- recompute fail fast instead of blocking for the full hour.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Description,Updated,UpdatedBy,Value)
SELECT 0,0,541846 /*From ID Server*/,'S',
       TO_TIMESTAMP('2026-08-05 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
       'de.metas.invoicecandidate.api.impl.InvoiceCandBL.WaitForInvoiceCandidatesUpdatedTimeoutSeconds',
       'Maximale Wartezeit (Sekunden) beim Warten auf die Neuberechnung der Rechnungskandidaten, bevor abgebrochen wird (Standard 3600).',
       TO_TIMESTAMP('2026-08-05 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'3600'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig
    WHERE Name='de.metas.invoicecandidate.api.impl.InvoiceCandBL.WaitForInvoiceCandidatesUpdatedTimeoutSeconds'
);
