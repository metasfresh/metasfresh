-- 2021-06-10T11:52:12.897Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584841,'N','de.metas.banking.process.paymentdocumentform.WEBUI_Import_Payment_Request_For_Purchase_Invoice','N',TO_TIMESTAMP('2021-06-10 13:52:12','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','N','N','N','N','Y','Y',0,'Import Payment Request for Purchase Invoice','json','N','Y','Java',TO_TIMESTAMP('2021-06-10 13:52:12','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Import_Payment_Request_For_Purchase_Invoice QR')
;

-- 2021-06-10T11:52:12.902Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584841 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-06-10T11:52:39.473Z
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.banking.process.paymentdocumentform.WEBUI_Import_Payment_Request_For_Purchase_Invoice_QRCode',Updated=TO_TIMESTAMP('2021-06-10 13:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584841
;

-- 2021-06-10T11:53:15.667Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,BarcodeScannerType,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543079,0,584841,542026,10,'qrCode','FullPaymentString',TO_TIMESTAMP('2021-06-10 13:53:15','YYYY-MM-DD HH24:MI:SS'),100,'Im Fall von ESR oder anderen von Zahlschein gelesenen Zahlungsaufforderungen ist dies der komplette vom Schein eingelesene String','de.metas.payment',0,'Y','N','Y','N','N','N','Eingelesene Zeichenkette',10,TO_TIMESTAMP('2021-06-10 13:53:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-10T11:53:15.674Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542026 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-06-10T11:54:16.289Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,584841,542027,30,'C_BPartner_ID',TO_TIMESTAMP('2021-06-10 13:54:16','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','U',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','Y','N','N','N','Geschäftspartner',20,TO_TIMESTAMP('2021-06-10 13:54:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-10T11:54:16.291Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542027 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-06-10T11:55:02.507Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,837,0,584841,542028,18,269,'C_BP_BankAccount_ID',TO_TIMESTAMP('2021-06-10 13:55:02','YYYY-MM-DD HH24:MI:SS'),100,'Bankverbindung des Geschäftspartners','U',0,'Y','N','Y','N','N','N','Bankverbindung',30,TO_TIMESTAMP('2021-06-10 13:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-10T11:55:02.508Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542028 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-06-10T11:55:48.183Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ReadOnlyLogic='@Will_Create_a_new_Bank_Account@=N',Updated=TO_TIMESTAMP('2021-06-10 13:55:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542028
;

-- 2021-06-10T11:56:17.660Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2091,0,584841,542029,10,'BankAccountNo',TO_TIMESTAMP('2021-06-10 13:56:17','YYYY-MM-DD HH24:MI:SS'),100,'Bank Account Number','U',0,'Y','N','Y','N','N','N','Bank Account No',40,TO_TIMESTAMP('2021-06-10 13:56:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-10T11:56:17.663Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542029 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-06-10T11:56:22.061Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Will_Create_a_new_Bank_Account@=N', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-06-10 13:56:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542028
;

-- 2021-06-10T11:56:26.872Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Will_Create_a_new_Bank_Account@=Y',Updated=TO_TIMESTAMP('2021-06-10 13:56:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542029
;

-- 2021-06-10T11:57:13.113Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577105,0,584841,542030,20,'Will_Create_a_new_Bank_Account',TO_TIMESTAMP('2021-06-10 13:57:13','YYYY-MM-DD HH24:MI:SS'),100,'Will create a new bank account','U',0,'Y','N','Y','N','N','N','Neues Bankkonto wird erstellt',50,TO_TIMESTAMP('2021-06-10 13:57:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-10T11:57:13.122Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542030 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-06-10T11:57:17.202Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Will_Create_a_new_Bank_Account@=Y',Updated=TO_TIMESTAMP('2021-06-10 13:57:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542030
;

-- 2021-06-10T11:57:52.921Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1367,0,584841,542031,22,'Amount',TO_TIMESTAMP('2021-06-10 13:57:52','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung','U',0,'"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','Y','N','N','N','Betrag',60,TO_TIMESTAMP('2021-06-10 13:57:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-10T11:57:52.926Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542031 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-06-10T14:36:45.185Z
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584841,318,540942,null,TO_TIMESTAMP('2021-06-10 16:36:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y',TO_TIMESTAMP('2021-06-10 16:36:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

