-- 2022-01-04T14:47:43.409555600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET InternalName='PrintingQueuePDFConcatenateWorkpackageProcessor', Classname = 'de.metas.printing.async.spi.impl.PrintingQueuePDFConcatenateWorkpackageProcessor',Updated=TO_TIMESTAMP('2022-01-04 16:47:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540092
;

-- 2022-01-04T14:48:27.010524100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'de.metas.printing.async.spi.impl.InvoiceEnqueueingWorkpackageProcessor',540093,TO_TIMESTAMP('2022-01-04 16:48:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','InvoiceEnqueueingWorkpackageProcessor','Y',TO_TIMESTAMP('2022-01-04 16:48:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-04T14:48:56.798573100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540063,TO_TIMESTAMP('2022-01-04 16:48:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'InvoiceEnqueueingWorkpackageProcessor',1,TO_TIMESTAMP('2022-01-04 16:48:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-04T14:49:22.792362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540093,540099,540063,TO_TIMESTAMP('2022-01-04 16:49:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-01-04 16:49:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-04T14:50:05.888001200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.printing.process.C_Invoice_Candidate_EnqueueSelectionForInvoicingAndPDFPrinting',Updated=TO_TIMESTAMP('2022-01-04 16:50:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584953
;

-- 2022-01-04T14:50:10.222741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Enqueue Selection For Invoicing And PDF Printing',Updated=TO_TIMESTAMP('2022-01-04 16:50:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584953
;

-- 2022-01-04T14:50:10.225853900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Enqueue Selection For Invoicing And PDF Printing',Updated=TO_TIMESTAMP('2022-01-04 16:50:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541886
;

-- 2022-01-04T14:51:07.551991300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.printing.process.C_Invoice_Candidate_EnqueueSelectionForInvoicingAndPDFConcatenating', Name='Enqueue Selection For Invoicing And PDF Concatenating', Value='C_Invoice_Candidate_EnqueueSelectionForInvoicingAndPDFConcatenating',Updated=TO_TIMESTAMP('2022-01-04 16:51:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584953
;

-- 2022-01-04T14:51:07.558058300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Enqueue Selection For Invoicing And PDF Concatenating',Updated=TO_TIMESTAMP('2022-01-04 16:51:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541886
;

-- 2022-01-04T14:51:53.494558700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Enqueue Selection For Invoicing And PDF Concatenating',Updated=TO_TIMESTAMP('2022-01-04 16:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584953
;

-- 2022-01-04T14:51:53.497156200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Enqueue Selection For Invoicing And PDF Concatenating', IsActive='Y', Name='Enqueue Selection For Invoicing And PDF Concatenating',Updated=TO_TIMESTAMP('2022-01-04 16:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541886
;

-- 2022-01-04T14:52:41.983722200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Auswahl in Warteschlange für Rechnungsstellung und PDF-Verkettung stellen', IsTranslated='Y', Name='Rechnungsauswahl',Updated=TO_TIMESTAMP('2022-01-04 16:52:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584953
;

-- 2022-01-04T14:52:50.890381800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Rechnungsauswahl',Updated=TO_TIMESTAMP('2022-01-04 16:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584953
;

-- 2022-01-04T14:52:50.893501500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Rechnungsauswahl',Updated=TO_TIMESTAMP('2022-01-04 16:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541886
;

-- 2022-01-04T14:52:50.885730400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Rechnungsauswahl',Updated=TO_TIMESTAMP('2022-01-04 16:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584953
;

-- 2022-01-04T14:52:56.594807100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Auswahl in Warteschlange für Rechnungsstellung und PDF-Verkettung stellen', Help=NULL, Name='Rechnungsauswahl',Updated=TO_TIMESTAMP('2022-01-04 16:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584953
;

-- 2022-01-04T14:52:56.597401700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Auswahl in Warteschlange für Rechnungsstellung und PDF-Verkettung stellen', IsActive='Y', Name='Rechnungsauswahl',Updated=TO_TIMESTAMP('2022-01-04 16:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541886
;

-- 2022-01-04T14:52:56.590129500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Auswahl in Warteschlange für Rechnungsstellung und PDF-Verkettung stellen',Updated=TO_TIMESTAMP('2022-01-04 16:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584953
;

-- 2022-01-04T14:53:28.358215600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Enqueue selection for Invoicing And PDF concatenating', IsTranslated='Y', Name='Invoice selection',Updated=TO_TIMESTAMP('2022-01-04 16:53:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584953
;


