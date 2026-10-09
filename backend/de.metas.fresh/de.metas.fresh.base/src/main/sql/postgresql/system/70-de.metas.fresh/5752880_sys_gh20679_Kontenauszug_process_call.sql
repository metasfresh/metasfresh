-- Value: RV_AccountSheet_Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-04-24T21:22:16.906Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_acct.AccountSheet_Report(p_Account_IDs := ARRAY[@Account_ID/-1@, @Account2_ID/-1@, @Account3_ID/-1@, @Account4_ID/-1@, @Account5_ID/-1@, @Account6_ID/-1@, @Account7_ID/-1@, @Account8_ID/-1@], p_C_AcctSchema_ID := @C_AcctSchema_ID@, p_DateAcctFrom := ''@DateAcctFrom@'', p_DateAcctTo := ''@DateAcctTo@'')',Updated=TO_TIMESTAMP('2025-04-25 00:22:16.905','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585341
;
