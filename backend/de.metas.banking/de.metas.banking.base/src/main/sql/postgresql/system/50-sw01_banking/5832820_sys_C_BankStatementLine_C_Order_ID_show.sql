-- Bank statement window (AD_Window 194 "Bankauszug"), tab "Auszugs-Position" (AD_Tab 329, table C_BankStatementLine):
-- show the sales order of a bank line (AD_Field 555325, AD_Column 545950 C_BankStatementLine.C_Order_ID, which was hidden so far)
-- next to the invoice, and offer only the orders that can really be paid by the line.
-- 1. A new validation rule on the field (AD_Field.AD_Val_Rule_ID; the WebUI takes the field's rule before the column's,
--    COALESCE(AD_Field.AD_Val_Rule_ID, AD_Column.AD_Val_Rule_ID) in views AD_Field_v / AD_Field_vt) offers only active prepay sales orders (C_DocType.DocSubType='PR' of C_Order.C_DocType_ID)
--    that wait for payment (C_Order.DocStatus='WP'), that are not already on another unprocessed bank statement line and that
--    are not carried by a completed or closed payment (C_Payment.DocStatus IN ('CO','CL'), like the bank statement imports;
--    the first version of this rule excluded only voided/reversed payments, 5832940 aligned it). If the line has a business partner,
--    only orders of that partner are offered (bill partner or ordering partner). The order already selected on the line stays selectable.
--    The existing rule AD_Val_Rule 212 (C_Order.DocStatus='WP') is not changed: it may be used elsewhere.
-- 2. The field is placed on the tab right after the invoice (form view and grid view).
--
-- Impact:
--   AD_Field 555325: AD_Val_Rule_ID null -> new rule 540811; IsDisplayed N -> Y, IsDisplayedGrid N -> Y, SeqNo 0 -> 305, SeqNoGrid 0 -> 305 (legacy client ordering, free on the tab).
--   New AD_UI_Element 656039 for AD_Field 555325 in AD_UI_ElementGroup 540272: SeqNo 105, SeqNoGrid 105 (after "Rechnung" 100,
--   before business partner (AD_Field 8935) 110, free in both orderings).
-- NOT affected: AD_Column 545950 (AD_Val_Rule_ID stays null) and so the other fields on this column, AD_Field 589353 (window "Kassenbuch")
--   and 548369 (window "Kassenjournal"): the rule is set on the field of window "Bankauszug" only; field name / translations (shared AD_Element 558 "Auftrag" / en_US "Sales order" is unchanged), AD_Column.ReadOnlyLogic
--   of 545950 (@C_Payment_ID@!0: the field is read-only once a payment is linked), all other fields of the tab.

-- 1. Validation rule
INSERT INTO AD_Val_Rule (AD_Client_ID, AD_Org_ID, AD_Val_Rule_ID, Code, Created, CreatedBy, Description, EntityType, IsActive, Name, Type, Updated, UpdatedBy)
VALUES (0, 0, 540811 /*From ID Server*/,
        'C_Order.C_Order_ID=@C_Order_ID/0@ OR (C_Order.IsActive=''Y'' AND C_Order.IsSOTrx=''Y'' AND C_Order.DocStatus=''WP''
 AND EXISTS (SELECT 1 FROM C_DocType dt WHERE dt.C_DocType_ID=C_Order.C_DocType_ID AND dt.DocSubType=''PR'')
 AND (@C_BPartner_ID/0@=0 OR C_Order.Bill_BPartner_ID=@C_BPartner_ID/0@ OR C_Order.C_BPartner_ID=@C_BPartner_ID/0@)
 AND NOT EXISTS (SELECT 1 FROM C_BankStatementLine bsl WHERE bsl.C_Order_ID=C_Order.C_Order_ID AND bsl.IsActive=''Y'' AND bsl.Processed=''N'' AND bsl.C_BankStatementLine_ID<>@C_BankStatementLine_ID/0@)
 AND NOT EXISTS (SELECT 1 FROM C_Payment p WHERE p.C_Order_ID=C_Order.C_Order_ID AND p.IsActive=''Y'' AND p.DocStatus NOT IN (''VO'',''RE'')))',
        TO_TIMESTAMP('2026-11-03 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Prepay sales orders waiting for payment that are not yet carried by another bank statement line or payment', 'D', 'Y',
        'C_Order_PrepayWaitingForPayment_ForBankStatementLine', 'S',
        TO_TIMESTAMP('2026-11-03 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2. Rule on the field and placement
UPDATE AD_Field
SET AD_Val_Rule_ID = 540811 /*From ID Server*/, IsDisplayed = 'Y', IsDisplayedGrid = 'Y', SeqNo = 305, SeqNoGrid = 305, Updated = TO_TIMESTAMP('2026-11-03 17:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 555325
;

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID, AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 0, 656039 /*From ID Server*/, 555325, 329, 540272, 'F', TO_TIMESTAMP('2026-11-03 17:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
        'N', 'Y', 'Y', 'N', 'Auftrag', 105, 105, 0, TO_TIMESTAMP('2026-11-03 17:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
