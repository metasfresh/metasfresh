-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithIBAN
-- 2022-10-28T13:48:12.867Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545178,0,TO_TIMESTAMP('2022-10-28 16:48:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','Stmt mit Id={0} wird übersprungen, da kein Konto mit IBAN={1} gefunden wurde','I',TO_TIMESTAMP('2022-10-28 16:48:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithIBAN')
;

-- 2022-10-28T13:48:12.867Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545178 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithIBAN
-- 2022-10-28T13:48:23.332Z
UPDATE AD_Message_Trl SET MsgText='Skipping Stmt with Id={0}, because no account with IBAN={1} was found',Updated=TO_TIMESTAMP('2022-10-28 16:48:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545178
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementSwiftCode
-- 2022-10-28T13:49:37.162Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545179,0,TO_TIMESTAMP('2022-10-28 16:49:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','Skipping Stmt with Id {0}, because it has no swift code','I',TO_TIMESTAMP('2022-10-28 16:49:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementSwiftCode')
;

-- 2022-10-28T13:49:37.162Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545179 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementSwiftCode
-- 2022-10-28T13:49:48.177Z
UPDATE AD_Message SET MsgText='Skipping Stmt with Id={0}, because it has no swift code',Updated=TO_TIMESTAMP('2022-10-28 16:49:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545179
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementSwiftCode
-- 2022-10-28T13:50:27.510Z
UPDATE AD_Message_Trl SET MsgText='Stmt mit Id={0} wird übersprungen, da es keinen Swift-Code hat',Updated=TO_TIMESTAMP('2022-10-28 16:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545179
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementSwiftCode
-- 2022-10-28T13:50:29.127Z
UPDATE AD_Message_Trl SET MsgText='Stmt mit Id={0} wird übersprungen, da es keinen Swift-Code hat',Updated=TO_TIMESTAMP('2022-10-28 16:50:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545179
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementSwiftCode
-- 2022-10-28T13:50:31.972Z
UPDATE AD_Message_Trl SET MsgText='Stmt mit Id={0} wird übersprungen, da es keinen Swift-Code hat',Updated=TO_TIMESTAMP('2022-10-28 16:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545179
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithSwiftCode
-- 2022-10-28T13:51:17.225Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545180,0,TO_TIMESTAMP('2022-10-28 16:51:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','Stmt mit Id={0} wird übersprungen, da keine Bank für SwiftCode={1} gefunden wurde','I',TO_TIMESTAMP('2022-10-28 16:51:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithSwiftCode')
;

-- 2022-10-28T13:51:17.225Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545180 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithSwiftCode
-- 2022-10-28T13:51:26.947Z
UPDATE AD_Message_Trl SET MsgText='Skipping Stmt with Id={0}, because no bank was found for SwiftCode={1}',Updated=TO_TIMESTAMP('2022-10-28 16:51:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545180
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithAccountNo
-- 2022-10-28T13:51:48.723Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545181,0,TO_TIMESTAMP('2022-10-28 16:51:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','Stmt mit Id={0} wird übersprungen, da die Bank mit C_Bank_ID={1} kein Konto mit AccountNo={2} hat','I',TO_TIMESTAMP('2022-10-28 16:51:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithAccountNo')
;

-- 2022-10-28T13:51:48.723Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545181 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithAccountNo
-- 2022-10-28T13:51:56.383Z
UPDATE AD_Message_Trl SET MsgText='Skipping Stmt with Id={0}, because the bank with C_Bank_ID={1} has no account with AccountNo={2}',Updated=TO_TIMESTAMP('2022-10-28 16:51:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545181
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementAccountNo
-- 2022-10-28T13:53:03.314Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545182,0,TO_TIMESTAMP('2022-10-28 16:53:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','Stmt mit Id={0} wird übersprungen, da es keine Kontonummer hat (.../Acct/Id/Othr)','I',TO_TIMESTAMP('2022-10-28 16:53:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementAccountNo')
;

-- 2022-10-28T13:53:03.314Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545182 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementAccountNo
-- 2022-10-28T13:53:10.565Z
UPDATE AD_Message_Trl SET MsgText='Skipping Stmt with Id={0}, because it has no account number (.../Acct/Id/Othr)',Updated=TO_TIMESTAMP('2022-10-28 16:53:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545182
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.SkippedStmtNoMatchingCurrency
-- 2022-10-28T13:53:30.832Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545183,0,TO_TIMESTAMP('2022-10-28 16:53:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','Stmt mit Id={0} wird übersprungen, da die Währung des Auszugs nicht mit der Währung des Bankkontos übereinstimmt!','I',TO_TIMESTAMP('2022-10-28 16:53:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.camt53.BankStatementCamt53Service.SkippedStmtNoMatchingCurrency')
;

-- 2022-10-28T13:53:30.832Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545183 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.SkippedStmtNoMatchingCurrency
-- 2022-10-28T13:53:40.001Z
UPDATE AD_Message_Trl SET MsgText='Skipping AccountStatementId={0} because currency of the statement does not match currency of the bank account!',Updated=TO_TIMESTAMP('2022-10-28 16:53:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545183
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingReportEntryDate
-- 2022-10-28T13:54:04.080Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545184,0,TO_TIMESTAMP('2022-10-28 16:54:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','Stmt mit Id={0} wird übersprungen, da StatementLineDate fehlt! BankStatementId={1}','I',TO_TIMESTAMP('2022-10-28 16:54:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.camt53.BankStatementCamt53Service.MissingReportEntryDate')
;

-- 2022-10-28T13:54:04.080Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545184 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingReportEntryDate
-- 2022-10-28T13:54:11.113Z
UPDATE AD_Message_Trl SET MsgText='Skipping this ReportEntry={0} because StatementLineDate is missing! BankStatementId={1}',Updated=TO_TIMESTAMP('2022-10-28 16:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545184
;

