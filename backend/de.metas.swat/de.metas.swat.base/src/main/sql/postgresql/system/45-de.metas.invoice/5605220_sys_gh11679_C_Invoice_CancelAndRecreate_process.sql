
-- 2021-09-16T15:13:16.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584906,'Y','de.metas.invoicecandidate.process.C_Invoice_CancelAndRecreate','N',TO_TIMESTAMP('2021-09-16 18:13:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','N','N','Y','N','N','N','Y','Y',0,'Auswahl stornieren und neu erstellen','json','N','N','xls','Java',TO_TIMESTAMP('2021-09-16 18:13:16','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_CancelAndRecreate')
;

-- 2021-09-16T15:13:16.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584906 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-09-16T15:13:29.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Reverse and reinvoice selection',Updated=TO_TIMESTAMP('2021-09-16 18:13:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584906
;

-- 2021-09-16T15:16:40.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584906,318,540997,TO_TIMESTAMP('2021-09-16 18:16:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y',TO_TIMESTAMP('2021-09-16 18:16:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-09-22T04:59:02.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET ShowHelp='Y',Updated=TO_TIMESTAMP('2021-09-22 07:59:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584906
;

-- 2021-09-22T05:17:19.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Storniert die ausgewählten fertig gestellten Rechnungen und erzeugt neue Rechnungen unter Benutzung der zur Zeit aktuellen Geschäftspartner-Stammdaten.
Rechnungen, die manuell ohne Rechnungskandidaten erstellt wurden oder die durch Zuordnung ohne Zahlungen beglichen wurden, werden übersprungen.', Help='Storniert die ausgewählten fertig gestellten Rechnungen und erzeugt neue Rechnungen unter Benutzung der zur Zeit aktuellen Geschäftspartner-Stammdaten.
Rechnungen, die manuell ohne Rechnungskandidaten erstellt wurden oder die durch Zuordnung ohne Zahlungen beglichen wurden, werden übersprungen.',Updated=TO_TIMESTAMP('2021-09-22 08:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584906
;

-- 2021-09-22T05:17:45.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Storniert die ausgewählten fertig gestellten Rechnungen und erzeugt neue Rechnungen unter Benutzung der zur Zeit aktuellen Geschäftspartner-Stammdaten.
Rechnungen, die manuell ohne Rechnungskandidaten erstellt wurden oder die durch Zuordnung ohne Zahlungen beglichen wurden, werden übersprungen.', Help='Storniert die ausgewählten fertig gestellten Rechnungen und erzeugt neue Rechnungen unter Benutzung der zur Zeit aktuellen Geschäftspartner-Stammdaten.
Rechnungen, die manuell ohne Rechnungskandidaten erstellt wurden oder die durch Zuordnung ohne Zahlungen beglichen wurden, werden übersprungen.',Updated=TO_TIMESTAMP('2021-09-22 08:17:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584906
;

-- 2021-09-22T05:17:56.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Storniert die ausgewählten fertig gestellten Rechnungen und erzeugt neue Rechnungen unter Benutzung der zur Zeit aktuellen Geschäftspartner-Stammdaten.
Rechnungen, die manuell ohne Rechnungskandidaten erstellt wurden oder die durch Zuordnung ohne Zahlungen beglichen wurden, werden übersprungen.', Help='Storniert die ausgewählten fertig gestellten Rechnungen und erzeugt neue Rechnungen unter Benutzung der zur Zeit aktuellen Geschäftspartner-Stammdaten.
Rechnungen, die manuell ohne Rechnungskandidaten erstellt wurden oder die durch Zuordnung ohne Zahlungen beglichen wurden, werden übersprungen.',Updated=TO_TIMESTAMP('2021-09-22 08:17:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584906
;

-- 2021-09-22T05:18:07.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Storniert die ausgewählten fertig gestellten Rechnungen und erzeugt neue Rechnungen unter Benutzung der zur Zeit aktuellen Geschäftspartner-Stammdaten.
Rechnungen, die manuell ohne Rechnungskandidaten erstellt wurden oder die durch Zuordnung ohne Zahlungen beglichen wurden, werden übersprungen.', Help='Storniert die ausgewählten fertig gestellten Rechnungen und erzeugt neue Rechnungen unter Benutzung der zur Zeit aktuellen Geschäftspartner-Stammdaten.
Rechnungen, die manuell ohne Rechnungskandidaten erstellt wurden oder die durch Zuordnung ohne Zahlungen beglichen wurden, werden übersprungen.',Updated=TO_TIMESTAMP('2021-09-22 08:18:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584906
;

-- 2021-09-22T05:18:27.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Voidss and recreates the selected invoices.', Help='Voidss and recreates the selected invoices.',Updated=TO_TIMESTAMP('2021-09-22 08:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584906
;


