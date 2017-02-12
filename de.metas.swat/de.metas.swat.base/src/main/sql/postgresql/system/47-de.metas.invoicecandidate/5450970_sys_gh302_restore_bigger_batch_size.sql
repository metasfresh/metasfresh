--
-- ..also pimp the descriptions a bit.
--
UPDATE AD_SysConfig
SET Description='Maximum number of invoice candidates to be updated in one workpackage. If there are more ICs to update, then another workpackage will be created for them.', 
	Updated=now(), UpdatedBy=99
WHERE Name='de.metas.invoicecandidate.async.spi.impl.UpdateInvalidInvoiceCandidatesWorkpackageProcessor.MaxInvoiceCandidatesToUpdate';

UPDATE AD_SysConfig
SET Description='How many invoice candidates shall be updated in one transaction; Note that the number of ICs to process is generally limited by the AD_Sysconfig-"de.metas.invoicecandidate.async.spi.impl.UpdateInvalidInvoiceCandidatesWorkpackageProcessor.MaxInvoiceCandidatesToUpdate" value',
	Updated=now(), UpdatedBy=99
WHERE Name='de.metas.invoicecandidate.api.impl.InvoiceCandInvalidUpdater.ItemsPerBatch';

--
-- set the value back from 1 to 100
--
UPDATE AD_SysConfig
SET Value='100', 
	Updated=now(), Updatedby=99
WHERE Name='de.metas.invoicecandidate.api.impl.InvoiceCandInvalidUpdater.ItemsPerBatch'
AND Value='1';