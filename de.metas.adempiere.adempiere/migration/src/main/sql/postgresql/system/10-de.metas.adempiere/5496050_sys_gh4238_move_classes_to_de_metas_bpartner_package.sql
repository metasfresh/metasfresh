-- 2018-06-18T17:57:16.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.bpartner.process.BPartnerCreditLimit_Approve',Updated=TO_TIMESTAMP('2018-06-18 17:57:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540921
;

-- 2018-06-18T17:57:26.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.bpartner.process.BPartnerCreditLimit_RequestApproval',Updated=TO_TIMESTAMP('2018-06-18 17:57:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540922
;

-- 2018-06-18T17:57:35.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.bpartner.process.C_BPartner_Stats_AddRemoveCreditStopStatus',Updated=TO_TIMESTAMP('2018-06-18 17:57:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540938
;

-- 2018-06-18T17:57:43.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.bpartner.process.C_BPartner_Stats_ComputeBPartnerStats',Updated=TO_TIMESTAMP('2018-06-18 17:57:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540947
;

update AD_Tab_Callout set Classname='de.metas.bpartner.callout.C_BPartner_Location_Tab_Callout'
where Classname='org.adempiere.bpartner.callout.C_BPartner_Location_Tab_Callout';

-- 2018-06-18T18:04:56.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Classname='de.metas.bpartner.service.async.spi.impl.C_BPartner_UpdateStatsFromBPartner',Updated=TO_TIMESTAMP('2018-06-18 18:04:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540024
;

