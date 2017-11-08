


UPDATE AD_Process SET ClassName = 'de.metas.contracts.flatrate.process.C_Flatrate_Term_Import' WHERE ClassName='de.metas.flatrate.process.C_Flatrate_Term_Import';
UPDATE AD_Process SET ClassName = 'de.metas.contracts.flatrate.process.C_Flatrate_Term_Prepare_Closing' WHERE ClassName='de.metas.flatrate.process.C_Flatrate_Term_Prepare_Closing';
UPDATE AD_Process SET ClassName = 'de.metas.contracts.flatrate.process.C_Flatrate_Term_Create_For_BPartners' WHERE ClassName='de.metas.flatrate.process.C_Flatrate_Term_Create_For_BPartners';
UPDATE AD_Process SET ClassName = 'de.metas.contracts.flatrate.process.C_Flatrate_Term_Extend' WHERE ClassName='de.metas.flatrate.process.C_Flatrate_Term_Extend';
UPDATE AD_Process SET ClassName = 'de.metas.contracts.flatrate.process.C_Flatrate_Term_Create_From_OLCand' WHERE ClassName='de.metas.flatrate.process.C_Flatrate_Term_Create_From_OLCand';
UPDATE AD_Process SET ClassName = 'de.metas.contracts.flatrate.process.C_Flatrate_Term_Extend' WHERE ClassName='de.metas.flatrate.process.C_Flatrate_Term_Extend';
UPDATE AD_Process SET ClassName = 'de.metas.contracts.flatrate.process.C_Flatrate_Term_Change' WHERE ClassName='de.metas.flatrate.process.C_Flatrate_Term_Change';

UPDATE C_PricingRule SET ClassName='de.metas.contracts.pricing.ContractDiscount' WHERE ClassName='de.metas.flatrate.pricing.spi.impl.ContractDiscount';

UPDATE AD_ModelValidator SET ModelValidationclass='de.metas.contracts.interceptor.MainValidator' WHERE ModelValidationclass='de.metas.flatrate.modelvalidator.MainValidator';

UPDATE AD_ColumnCallout SET ClassName = 'de.metas.contracts.flatrate.callout.C_Flatrate_Matching.onM_PricingSystem_ID' WHERE ClassName = 'de.metas.flatrate.callout.C_Flatrate_Matching.onM_PricingSystem_ID';
UPDATE AD_ColumnCallout SET ClassName = 'de.metas.contracts.flatrate.callout.C_Flatrate_Matching.onC_Flatrate_Transition_ID' WHERE ClassName = 'de.metas.flatrate.callout.C_Flatrate_Matching.onC_Flatrate_Transition_ID';

UPDATE AD_ColumnCallout SET ClassName = 'de.metas.contracts.flatrate.callout.OrderLine.qty' WHERE ClassName = 'de.metas.flatrate.callout.OrderLine.qty';
UPDATE AD_ColumnCallout SET ClassName = 'de.metas.contracts.flatrate.callout.OrderLine.amt' WHERE ClassName = 'de.metas.flatrate.callout.OrderLine.amt';

UPDATE C_ILCandHandler SET ClassName='de.metas.contracts.invoicecandidate.FlatrateTermInvoiceCandidateHandler' WHERE ClassName = 'de.metas.flatrate.invoicecandidate.spi.impl.FlatrateTermHandler';
UPDATE C_ILCandHandler SET ClassName='de.metas.contracts.invoicecandidate.FlatrateDataEntryHandler' WHERE ClassName = 'de.metas.flatrate.invoicecandidate.spi.impl.FlatrateDataEntryHandler';

UPDATE AD_EntityType SET ModelPackage='de.metas.contracts.flatrate.model' WHERE ModelPackage='de.metas.flatrate.model';

UPDATE M_IolCandHandler SET ClassName = 'de.metas.contracts.inoutcandidate.SubscriptionShipmentScheduleHandler' WHERE ClassName='de.metas.flatrate.inoutcandidate.spi.impl.SubscriptionInOutCandHandler';


UPDATE C_ILCandHandler SET IsActive='Y' WHERE ClassName='de.metas.contracts.invoicecandidate.FlatrateTermInvoiceCandidateHandler';
