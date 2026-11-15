-- Bank statement window (AD_Window 194 "Bankauszug"), tab "Auszugs-Position" (AD_Tab 329), field AD_Field 555325 (C_BankStatementLine.C_Order_ID "Auftrag"):
-- align the dropdown rule AD_Val_Rule 540811 (created in 5832820_sys_C_BankStatementLine_C_Order_ID_show.sql) with the order matching of the bank statement imports
-- (camt.053 and standard import) and with the completion check of the bank statement.
-- All of them treat an order as carried by a payment only if that payment is completed or closed (C_Payment.DocStatus IN ('CO','CL'));
-- a drafted, in progress, invalid, voided, reversed, approved or otherwise not completed payment does not carry the order (human decision 2026-11-04: "yes, CO and CL!").
-- The first version of the rule excluded carried orders with C_Payment.DocStatus NOT IN ('VO','RE').
--
-- The script sets the whole rule text (not a text replace), so that the result does not depend on the text found.
--
-- Impact: AD_Val_Rule.Code of AD_Val_Rule 540811: C_Payment.DocStatus NOT IN ('VO','RE') -> C_Payment.DocStatus IN ('CO','CL').
-- NOT affected: AD_Field 555325 (it keeps AD_Val_Rule_ID 540811), AD_Column 545950, all other AD_Val_Rule rows.

UPDATE AD_Val_Rule
SET Code = 'C_Order.C_Order_ID=@C_Order_ID/0@ OR (C_Order.IsActive=''Y'' AND C_Order.IsSOTrx=''Y'' AND C_Order.DocStatus=''WP''
 AND EXISTS (SELECT 1 FROM C_DocType dt WHERE dt.C_DocType_ID=C_Order.C_DocType_ID AND dt.DocSubType=''PR'')
 AND (@C_BPartner_ID/0@=0 OR C_Order.Bill_BPartner_ID=@C_BPartner_ID/0@ OR C_Order.C_BPartner_ID=@C_BPartner_ID/0@)
 AND NOT EXISTS (SELECT 1 FROM C_BankStatementLine bsl WHERE bsl.C_Order_ID=C_Order.C_Order_ID AND bsl.IsActive=''Y'' AND bsl.Processed=''N'' AND bsl.C_BankStatementLine_ID<>@C_BankStatementLine_ID/0@)
 AND NOT EXISTS (SELECT 1 FROM C_Payment p WHERE p.C_Order_ID=C_Order.C_Order_ID AND p.IsActive=''Y'' AND p.DocStatus IN (''CO'',''CL'')))',
    Updated = TO_TIMESTAMP('2026-11-04 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Val_Rule_ID = 540811
;
