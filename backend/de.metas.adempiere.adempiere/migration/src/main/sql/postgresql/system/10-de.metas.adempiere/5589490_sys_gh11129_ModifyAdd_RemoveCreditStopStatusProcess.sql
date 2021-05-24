-- 2021-05-21T11:55:10.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.bpartner.process.C_BPartner_AddRemoveCreditStopStatus', Value='C_BPartner_AddRemoveCreditStopStatus',Updated=TO_TIMESTAMP('2021-05-21 14:55:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540938
;

-- 2021-05-21T11:55:38.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=540621
;

-- 2021-05-21T12:00:52.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,540938,291,540934,TO_TIMESTAMP('2021-05-21 15:00:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2021-05-21 15:00:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-05-24T09:11:09.407Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Kredit-Stop Status ändern',Updated=TO_TIMESTAMP('2021-05-24 12:11:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540938
;

-- 2021-05-24T09:11:14.783Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Help='Kredit-Stop Status ändern',Updated=TO_TIMESTAMP('2021-05-24 12:11:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540938
;

-- 2021-05-24T09:11:29.197Z
-- URL zum Konzept
UPDATE AD_Process SET Description='Kredit-Stop Status ändern', Help='If parameter IsSetCreditStop is on Y, the process will set CreditStatus to CreditStop.
If parameter IsSetCreditStop is on N, the process will remove CreditStop status, but only if the credit limit allows it.', Name='Kredit-Stop Status ändern',Updated=TO_TIMESTAMP('2021-05-24 12:11:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540938
;

-- 2021-05-24T09:11:28.855Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Description='Kredit-Stop Status ändern', IsTranslated='Y', Name='Kredit-Stop Status ändern',Updated=TO_TIMESTAMP('2021-05-24 12:11:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540938
;

-- 2021-05-24T09:11:38.307Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Description='Kredit-Stop Status ändern', Help='Kredit-Stop Status ändern', Name='Kredit-Stop Status ändern',Updated=TO_TIMESTAMP('2021-05-24 12:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=540938
;

-- 2021-05-24T09:17:29.052Z
-- URL zum Konzept
UPDATE AD_Process SET Description='', Help='If parameter IsSetCreditStop is on Y, the process will set CreditStatus to CreditStop.
If parameter IsSetCreditStop is on N, the process will remove CreditStop status, but only if the credit limit allows it.', Name='Kredit-Stop Status ändern',Updated=TO_TIMESTAMP('2021-05-24 12:17:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540938
;

-- 2021-05-24T09:17:28.778Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Description='',Updated=TO_TIMESTAMP('2021-05-24 12:17:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540938
;

-- 2021-05-24T09:17:55.221Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Help='',Updated=TO_TIMESTAMP('2021-05-24 12:17:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540938
;

-- 2021-05-24T09:18:07.273Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2021-05-24 12:18:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=540938
;


