-- 2018-10-17T14:58:44.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (Updated,Created,CreatedBy,IsActive,UpdatedBy,Classname,C_Queue_PackageProcessor_ID,AD_Client_ID,InternalName,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2018-10-17 14:58:44','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-10-17 14:58:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',100,'de.metas.invoice.export.async.C_Invoice_CreateExportData',540062,0,'C_Invoice_CreateExportData',0,'de.metas.document.archive')
;

-- 2018-10-17T14:59:22.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Description='Creates export data and attaches it to the respective invoice - if configores accordingly and if the invoice is applicable',Updated=TO_TIMESTAMP('2018-10-17 14:59:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540062
;

-- 2018-10-17T14:59:35.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Description='Creates export data and attaches it to the respective invoice - if configores accordingly and if the invoice is applicable; does nothing otherwise.',Updated=TO_TIMESTAMP('2018-10-17 14:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540062
;

-- 2018-10-17T15:00:09.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (Updated,Created,CreatedBy,IsActive,UpdatedBy,KeepAliveTimeMillis,PoolSize,C_Queue_Processor_ID,AD_Client_ID,AD_Org_ID,Name) VALUES (TO_TIMESTAMP('2018-10-17 15:00:09','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-10-17 15:00:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',100,1000,1,540051,0,0,'C_Invoice_CreateExportData')
;

-- 2018-10-17T15:01:38.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET EntityType='de.metas.invoice',Updated=TO_TIMESTAMP('2018-10-17 15:01:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540062
;

-- 2018-10-17T15:01:46.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (C_Queue_Processor_ID,C_Queue_PackageProcessor_ID,Updated,Created,CreatedBy,IsActive,UpdatedBy,C_Queue_Processor_Assign_ID,AD_Client_ID,AD_Org_ID) VALUES (540051,540062,TO_TIMESTAMP('2018-10-17 15:01:46','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-10-17 15:01:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',100,540083,0,0)
;

