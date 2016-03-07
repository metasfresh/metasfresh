-- 04.03.2016 14:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540322,'C_ElementValue.C_ElementValue_ID IN (
SELECT DISTINCT ev.C_ElementValue_ID FROM C_ElementValue ev
INNER JOIN C_Tax_Acct ta ON ta.C_ElementValue_ID=ev.C_ElementValue_ID
INNER JOIN C_ValidCombination vc ON (vc.C_ValidCombination_ID in (ta.T_Liability_Acct, ta.T_Receivables_Acct, ta.T_Due_Acct, ta.T_Credit_Acct,ta.T_Expense_Acct)))',TO_TIMESTAMP('2016-03-04 14:20:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Account_ID_For_Tax','S',TO_TIMESTAMP('2016-03-04 14:20:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 14:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540322,Updated=TO_TIMESTAMP('2016-03-04 14:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540910
;


-- 04.03.2016 14:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_ElementValue.C_ElementValue_ID IN (
SELECT DISTINCT ev.C_ElementValue_ID FROM C_ElementValue ev
INNER JOIN C_ValidCombination vc ON vc.Account_ID = ev.C_ElementValue_ID
INNER JOIN C_Tax_Acct ta ON vc.C_ValidCombination_ID IN (ta.T_Liability_Acct, ta.T_Receivables_Acct, ta.T_Due_Acct, ta.T_Credit_Acct,ta.T_Expense_Acct))',Updated=TO_TIMESTAMP('2016-03-04 14:40:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540322
;

