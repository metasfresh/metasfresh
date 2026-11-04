-- Bank statement window (AD_Window 194 "Bankauszug"), tab "Auszugs-Position" (AD_Tab 329), field AD_Field 555325 (C_BankStatementLine.C_Order_ID "Auftrag"):
-- align the dropdown rule AD_Val_Rule 540811 (created in 5832820_sys_C_BankStatementLine_C_Order_ID_show.sql) with the camt.053 import.
-- The import (AbstractPaymentDAO, DRAFT_OR_COMPLETED_STATUSES) treats an order as carried by a payment only if the payment is
-- drafted, in progress, invalid, completed or closed (C_Payment.DocStatus IN ('DR','IP','IN','CO','CL')).
-- The rule excluded carried orders with C_Payment.DocStatus NOT IN ('VO','RE'), so an order whose only payment has another status
-- (AP, NA, WP, WC, ...) was matched by the import but hidden in the dropdown. The rule now uses the same allow-list.
--
-- Impact: AD_Val_Rule.Code of AD_Val_Rule 540811: C_Payment.DocStatus NOT IN ('VO','RE') -> C_Payment.DocStatus IN ('DR','IP','IN','CO','CL').
-- NOT affected: AD_Field 555325 (it keeps AD_Val_Rule_ID 540811), AD_Column 545950, all other AD_Val_Rule rows.

UPDATE AD_Val_Rule
SET Code = REPLACE(Code, 'p.DocStatus NOT IN (''VO'',''RE'')', 'p.DocStatus IN (''DR'',''IP'',''IN'',''CO'',''CL'')'),
    Updated = TO_TIMESTAMP('2026-11-04 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Val_Rule_ID = 540811
;
