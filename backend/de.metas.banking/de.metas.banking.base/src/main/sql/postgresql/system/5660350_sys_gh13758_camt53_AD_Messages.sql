-- Value: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile.NoStatementImported
-- 2022-10-14T16:25:41.100Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545163,0,TO_TIMESTAMP('2022-10-14 19:25:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','No statement imported! File does not contain any valid statements!','E',TO_TIMESTAMP('2022-10-14 19:25:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile.NoStatementImported')
;

-- 2022-10-14T16:25:41.104Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545163 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile.NoStatementImported
-- 2022-10-14T16:25:47.701Z
UPDATE AD_Message_Trl SET MsgText='Keine Anweisung importiert! Die Datei enthält keine gültigen Anweisungen!',Updated=TO_TIMESTAMP('2022-10-14 19:25:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545163
;

-- Value: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile.NoStatementImported
-- 2022-10-14T16:25:49.162Z
UPDATE AD_Message_Trl SET MsgText='Keine Anweisung importiert! Die Datei enthält keine gültigen Anweisungen!',Updated=TO_TIMESTAMP('2022-10-14 19:25:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545163
;

-- Value: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile.NoStatementImported
-- 2022-10-14T16:25:51.986Z
UPDATE AD_Message_Trl SET MsgText='Keine Anweisung importiert! Die Datei enthält keine gültigen Anweisungen!',Updated=TO_TIMESTAMP('2022-10-14 19:25:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545163
;

-- Value: de.metas.banking.bankstatement.importer.wrapper.NoBatchBankToCustomerStatementV02Wrapper.BatchTransactionsNotSupported
-- 2022-10-14T16:29:38.411Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545164,0,TO_TIMESTAMP('2022-10-14 19:29:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bank statements with batched transactions are not supported!','E',TO_TIMESTAMP('2022-10-14 19:29:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.bankstatement.importer.wrapper.NoBatchBankToCustomerStatementV02Wrapper.BatchTransactionsNotSupported')
;

-- 2022-10-14T16:29:38.414Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545164 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.bankstatement.importer.wrapper.NoBatchBankToCustomerStatementV02Wrapper.BatchTransactionsNotSupported
-- 2022-10-14T16:30:16.533Z
UPDATE AD_Message_Trl SET MsgText='Kontoauszüge mit gestapelten Transaktionen werden nicht unterstützt!',Updated=TO_TIMESTAMP('2022-10-14 19:30:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545164
;

-- Value: de.metas.banking.bankstatement.importer.wrapper.NoBatchBankToCustomerStatementV02Wrapper.BatchTransactionsNotSupported
-- 2022-10-14T16:30:18.245Z
UPDATE AD_Message_Trl SET MsgText='Kontoauszüge mit gestapelten Transaktionen werden nicht unterstützt!',Updated=TO_TIMESTAMP('2022-10-14 19:30:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545164
;

-- Value: de.metas.banking.bankstatement.importer.wrapper.NoBatchBankToCustomerStatementV02Wrapper.BatchTransactionsNotSupported
-- 2022-10-14T16:30:20.787Z
UPDATE AD_Message_Trl SET MsgText='Kontoauszüge mit gestapelten Transaktionen werden nicht unterstützt!',Updated=TO_TIMESTAMP('2022-10-14 19:30:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545164
;

-- Value: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile.NoStatementImported
-- 2022-10-14T17:18:57.833Z
UPDATE AD_Message SET MsgText='Keine Anweisung importiert! Die Datei enthält keine gültigen Anweisungen!',Updated=TO_TIMESTAMP('2022-10-14 20:18:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545163
;

-- Value: de.metas.banking.bankstatement.importer.wrapper.NoBatchBankToCustomerStatementV02Wrapper.BatchTransactionsNotSupported
-- 2022-10-14T17:20:59.678Z
UPDATE AD_Message SET MsgText='Kontoauszüge mit gestapelten Transaktionen werden nicht unterstützt!',Updated=TO_TIMESTAMP('2022-10-14 20:20:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545164
;