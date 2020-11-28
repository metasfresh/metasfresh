-- 2018-09-13T13:14:53.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Mahnbeleg mit angehängten Rechnungsbelegen', Value='C_DunningDoc_DunningWithInvoicesPDFs',Updated=TO_TIMESTAMP('2018-09-13 13:14:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540999
;

-- 2018-09-13T13:15:02.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-13 13:15:02','YYYY-MM-DD HH24:MI:SS'),Name='Mahnbeleg mit angehängten Rechnungsbelegen' WHERE AD_Process_ID=540999 AND AD_Language='de_CH'
;

-- 2018-09-13T13:15:42.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-13 13:15:42','YYYY-MM-DD HH24:MI:SS'),Name='Dunning document with attached invoice PDFs' WHERE AD_Process_ID=540999 AND AD_Language='en_US'
;

-- 2018-09-13T13:16:44.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.dunning.process.C_DunningDoc_DunningWithInvoicesPDFs', IsReport='Y',Updated=TO_TIMESTAMP('2018-09-13 13:16:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540999
;

-- 2018-09-13T13:16:53.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET ShowHelp='S',Updated=TO_TIMESTAMP('2018-09-13 13:16:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540999
;

-- 2018-09-13T13:21:25.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.dunning',Updated=TO_TIMESTAMP('2018-09-13 13:21:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=1000000
;

-- 2018-09-13T13:28:16.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Mahnbrief mit angehängten Rechnungsbelegen',Updated=TO_TIMESTAMP('2018-09-13 13:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540999
;

-- 2018-09-14T07:45:36.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.dunning.process.C_DunningDoc_JasperWithInvoicePDFs', Value='C_DunningDoc_JasperWithInvoicePDFs',Updated=TO_TIMESTAMP('2018-09-14 07:45:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540999
;

-- 2018-09-14T07:50:39.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541232,'O',TO_TIMESTAMP('2018-09-14 07:50:39','YYYY-MM-DD HH24:MI:SS'),100,'AD_Process ID of the dunning doc (jasper) process. Used by the process C_DunningDoc_JasperWithInvoicePDFs.','de.metas.dunning','Y','de.metas.dunning.C_DunningDoc.Report.AD_Process_ID',TO_TIMESTAMP('2018-09-14 07:50:39','YYYY-MM-DD HH24:MI:SS'),100,'1000000')
;

-- 2018-09-14T07:58:06.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET TechnicalNote='Invokes the "normal" dunning jasper process and then contatenates archived PDFs.
The "normal" processe''s AD_Process_ID is found with AD_SysConfig de.metas.dunning',Updated=TO_TIMESTAMP('2018-09-14 07:58:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540999
;

-- 2018-09-14T07:58:34.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET TechnicalNote='Invokes the "normal" dunning report process and then contatenates archived PDFs.
The "normal" dunning processe''s AD_Process_ID is found with AD_SysConfig de.metas.dunning.C_DunningDoc.Report.AD_Process_ID.',Updated=TO_TIMESTAMP('2018-09-14 07:58:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540999
;

