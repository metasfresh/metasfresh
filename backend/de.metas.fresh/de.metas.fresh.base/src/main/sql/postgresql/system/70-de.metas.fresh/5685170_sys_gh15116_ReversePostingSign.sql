
-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- ParameterName: NegateAmounts
-- 2023-04-18T14:44:20.789Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542612
;

-- 2023-04-18T14:44:21.442Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542612
;

-- Value: SAP_GLJournal_CopyDocument
-- Classname: de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument
-- 2023-04-20T12:49:44.599Z
UPDATE AD_Process SET Description='Klonen und Stornieren von Kontenbuchungen (Haben zu Soll und umgekehrt)', Help='Klonen und Stornieren von Kontenbuchungen (Haben zu Soll und umgekehrt)', Name='Reverse',Updated=TO_TIMESTAMP('2023-04-20 13:49:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585254
;

-- 2023-04-20T12:49:44.960Z
UPDATE AD_Process_Trl trl SET Description='Klonen und Stornieren von Kontenbuchungen (Haben zu Soll und umgekehrt)',Help='Klonen und Stornieren von Kontenbuchungen (Haben zu Soll und umgekehrt)',Name='Reverse' WHERE AD_Process_ID=585254 AND AD_Language='en_US'
;

-- Value: SAP_GLJournal_CopyDocument
-- Classname: de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument
-- 2023-04-20T12:50:24.432Z
UPDATE AD_Process SET Name='Umkehren',Updated=TO_TIMESTAMP('2023-04-20 13:50:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585254
;

-- 2023-04-20T12:50:24.539Z
UPDATE AD_Process_Trl trl SET Name='Umkehren' WHERE AD_Process_ID=585254 AND AD_Language='en_US'
;

-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- 2023-04-20T12:51:07.503Z
UPDATE AD_Process_Trl SET Description='Clone and reverse account postings (credit to debit and vice versa)', Help='Clone and reverse account postings (credit to debit and vice versa)', IsTranslated='Y', Name='Reverse',Updated=TO_TIMESTAMP('2023-04-20 13:51:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585254
;

-- 2023-04-20T12:51:07.607Z
UPDATE AD_Process SET Description='Clone and reverse account postings (credit to debit and vice versa)', Help='Clone and reverse account postings (credit to debit and vice versa)', Name='Reverse' WHERE AD_Process_ID=585254
;

-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- 2023-04-20T12:51:35.595Z
UPDATE AD_Process_Trl SET Description='Klonen und Stornieren von Kontenbuchungen (Haben zu Soll und umgekehrt)', IsTranslated='Y', Name='Umkehren',Updated=TO_TIMESTAMP('2023-04-20 13:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585254
;

-- Value: SAP_GLJournal_ReverseDocument
-- Classname: de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument
-- 2023-04-20T14:01:07.345Z
UPDATE AD_Process SET Value='SAP_GLJournal_ReverseDocument',Updated=TO_TIMESTAMP('2023-04-20 15:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585254
;

-- Value: SAP_GLJournal_ReverseDocument
-- Classname: de.metas.acct.gljournal_sap.process.SAP_GLJournal_ReverseDocument
-- 2023-04-20T16:14:52.265Z
UPDATE AD_Process SET Classname='de.metas.acct.gljournal_sap.process.SAP_GLJournal_ReverseDocument',Updated=TO_TIMESTAMP('2023-04-20 17:14:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585254
;