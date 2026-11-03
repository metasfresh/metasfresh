-- me03 #29368 — drop the `@C_Order_ID@` branch from the C_PaySelectionLine.C_BP_BankAccount_ID
-- validation rule. After iter-2, pay-selection lines reference invoices only (regular + proforma);
-- C_PaySelectionLine no longer carries a C_Order_ID column, so the `UNION select ... from C_Order`
-- subselect references a context variable that is never resolved. Window health check fails:
--     "No context variable `C_Order_ID` found in C_PaySelection/206.C_PaySelectionLine/353.C_BP_BankAccount_ID.LookupDescriptor."
-- The line always has C_Invoice_ID set, so the invoice branch alone covers both the regular- and
-- proforma-invoice cases.

UPDATE AD_Val_Rule
SET Code    = 'C_BP_BankAccount.C_BPartner_ID=@C_BPartner_ID@ /* the invoice''s currency needs to match the account''s currency */ AND C_BP_BankAccount.C_Currency_ID=(select i.C_Currency_ID from C_Invoice i where i.C_Invoice_ID=@C_Invoice_ID@) AND (C_BP_BankAccount.BPBankAcctUse = ''B'' OR C_BP_BankAccount.BPBankAcctUse = ''@PaymentRule@'' /*Allow selection of NON-Standard types of usage like "Provision", because we can''t know if they make sense or not*/ OR C_BP_BankAccount.BPBankAcctUse NOT IN (''B'',''D'',''N'',''T'') )',
    Updated = TO_TIMESTAMP('2026-04-28 21:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 100
WHERE AD_Val_Rule_ID = 540104;
