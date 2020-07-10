/*
-- DOCBASETYPE_
UPDATE AD_Ref_List SET ValueName='GLJournal' WHERE AD_Reference_ID=183 AND Value='GLJ';
UPDATE AD_Ref_List SET ValueName='GLDocument' WHERE AD_Reference_ID=183 AND Value='GLD';
UPDATE AD_Ref_List SET ValueName='APInvoice' WHERE AD_Reference_ID=183 AND Value='API';
UPDATE AD_Ref_List SET ValueName='APPayment' WHERE AD_Reference_ID=183 AND Value='APP';
UPDATE AD_Ref_List SET ValueName='ARInvoice' WHERE AD_Reference_ID=183 AND Value='ARI';
UPDATE AD_Ref_List SET ValueName='ARReceipt' WHERE AD_Reference_ID=183 AND Value='ARR';
UPDATE AD_Ref_List SET ValueName='SalesOrder' WHERE AD_Reference_ID=183 AND Value='SOO';
UPDATE AD_Ref_List SET ValueName='ARProFormaInvoice' WHERE AD_Reference_ID=183 AND Value='ARF';
UPDATE AD_Ref_List SET ValueName='MaterialDelivery' WHERE AD_Reference_ID=183 AND Value='MMS';
UPDATE AD_Ref_List SET ValueName='MaterialReceipt' WHERE AD_Reference_ID=183 AND Value='MMR';
UPDATE AD_Ref_List SET ValueName='MaterialMovement' WHERE AD_Reference_ID=183 AND Value='MMM';
UPDATE AD_Ref_List SET ValueName='PurchaseOrder' WHERE AD_Reference_ID=183 AND Value='POO';
UPDATE AD_Ref_List SET ValueName='PurchaseRequisition' WHERE AD_Reference_ID=183 AND Value='POR';
UPDATE AD_Ref_List SET ValueName='MaterialPhysicalInventory' WHERE AD_Reference_ID=183 AND Value='MMI';
UPDATE AD_Ref_List SET ValueName='APCreditMemo' WHERE AD_Reference_ID=183 AND Value='APC';
UPDATE AD_Ref_List SET ValueName='ARCreditMemo' WHERE AD_Reference_ID=183 AND Value='ARC';
UPDATE AD_Ref_List SET ValueName='BankStatement' WHERE AD_Reference_ID=183 AND Value='CMB';
UPDATE AD_Ref_List SET ValueName='CashJournal' WHERE AD_Reference_ID=183 AND Value='CMC';
UPDATE AD_Ref_List SET ValueName='PaymentAllocation' WHERE AD_Reference_ID=183 AND Value='CMA';
UPDATE AD_Ref_List SET ValueName='MaterialProduction' WHERE AD_Reference_ID=183 AND Value='MMP';
UPDATE AD_Ref_List SET ValueName='MatchInvoice' WHERE AD_Reference_ID=183 AND Value='MXI';
UPDATE AD_Ref_List SET ValueName='MatchPO' WHERE AD_Reference_ID=183 AND Value='MXP';
UPDATE AD_Ref_List SET ValueName='ProjectIssue' WHERE AD_Reference_ID=183 AND Value='PJI';
UPDATE AD_Ref_List SET ValueName='MaintenanceOrder' WHERE AD_Reference_ID=183 AND Value='MOF';
UPDATE AD_Ref_List SET ValueName='ManufacturingOrder' WHERE AD_Reference_ID=183 AND Value='MOP';
UPDATE AD_Ref_List SET ValueName='QualityOrder' WHERE AD_Reference_ID=183 AND Value='MQO';
UPDATE AD_Ref_List SET ValueName='Payroll' WHERE AD_Reference_ID=183 AND Value='HRP';
UPDATE AD_Ref_List SET ValueName='DistributionOrder' WHERE AD_Reference_ID=183 AND Value='DOO';
UPDATE AD_Ref_List SET ValueName='ManufacturingCostCollector' WHERE AD_Reference_ID=183 AND Value='MCC';
*/


-- PERIODACTION_
UPDATE AD_Ref_List SET ValueName='OpenPeriod' WHERE AD_Reference_ID=176 AND Value='O';
UPDATE AD_Ref_List SET ValueName='ClosePeriod' WHERE AD_Reference_ID=176 AND Value='C';
UPDATE AD_Ref_List SET ValueName='PermanentlyClosePeriod' WHERE AD_Reference_ID=176 AND Value='P';
UPDATE AD_Ref_List SET ValueName='NoAction' WHERE AD_Reference_ID=176 AND Value='N';


-- PERIODSTATUS_
UPDATE AD_Ref_List SET ValueName='Open' WHERE AD_Reference_ID=177 AND Value='O';
UPDATE AD_Ref_List SET ValueName='Closed' WHERE AD_Reference_ID=177 AND Value='C';
UPDATE AD_Ref_List SET ValueName='PermanentlyClosed' WHERE AD_Reference_ID=177 AND Value='P';
UPDATE AD_Ref_List SET ValueName='NeverOpened' WHERE AD_Reference_ID=177 AND Value='N';
