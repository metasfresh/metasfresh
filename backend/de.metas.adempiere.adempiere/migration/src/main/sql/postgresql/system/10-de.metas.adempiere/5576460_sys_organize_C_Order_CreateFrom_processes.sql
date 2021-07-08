-- 2021-01-14T16:37:24.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.order.process.C_Order_CreateFromProposal',Updated=TO_TIMESTAMP('2021-01-14 18:37:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=531089
;

-- 2021-01-14T16:37:52.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=952, ColumnName='POReference', Description='Referenz-Nummer des Kunden', EntityType='D', Help='The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.', Name='Referenz',Updated=TO_TIMESTAMP('2021-01-14 18:37:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=531091
;

-- 2021-01-14T16:37:58.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2021-01-14 18:37:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=531089
;

-- 2021-01-14T16:38:05.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2021-01-14 18:38:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=531090
;

-- 2021-01-14T16:38:14.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='N', EntityType='D', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-01-14 18:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=531092
;

-- 2021-01-14T16:39:00.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.order.process.C_Order_CreateFromQuotation_Construction',Updated=TO_TIMESTAMP('2021-01-14 18:39:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541063
;

