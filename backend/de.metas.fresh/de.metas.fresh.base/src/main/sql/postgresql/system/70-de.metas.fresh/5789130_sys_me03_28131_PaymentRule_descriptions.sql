-- Reference: _Payment Rule (AD_Reference_ID=195)
-- Add descriptions to all PaymentRule ref-list entries explaining downstream behavior
-- (PaySelection eligibility, SEPA export, allocation paths)
-- https://github.com/metasfresh/me03/issues/28131

-- B = Cash (Bar)
-- AD_Ref_List_ID=332
UPDATE AD_Ref_List SET Description='Sofortige Barzahlung. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=332;
UPDATE AD_Ref_List_Trl SET Description='Sofortige Barzahlung. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=332 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Immediate cash payment. Not included in payment proposals (PaySelection).', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=332 AND AD_Language='en_US';

-- D = DirectDebit (Bankeinzug / SEPA-Lastschrift)
-- AD_Ref_List_ID=652
UPDATE AD_Ref_List SET Description='SEPA-Lastschrift vom Kundenkonto. In Zahlungsvorschlägen für Kundenrechnungen (IsSOTrx=Y) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=652;
UPDATE AD_Ref_List_Trl SET Description='SEPA-Lastschrift vom Kundenkonto. In Zahlungsvorschlägen für Kundenrechnungen (IsSOTrx=Y) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=652 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='SEPA direct debit from customer account. Included in payment proposals for sales invoices (IsSOTrx=Y).', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=652 AND AD_Language='en_US';

-- E = Reimbursement (Rückerstattung)
-- AD_Ref_List_ID=543787
UPDATE AD_Ref_List SET Description='Rückerstattung/Erstattung. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543787;
UPDATE AD_Ref_List_Trl SET Description='Rückerstattung/Erstattung. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543787 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Reimbursement/refund. Not included in payment proposals (PaySelection).', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543787 AND AD_Language='en_US';

-- F = Settlement (Verrechnung)
-- AD_Ref_List_ID=543788
UPDATE AD_Ref_List SET Description='Verrechnung/Aufrechnung zwischen Geschäftspartnern. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543788;
UPDATE AD_Ref_List_Trl SET Description='Verrechnung/Aufrechnung zwischen Geschäftspartnern. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543788 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Settlement/compensation between business partners. Not included in payment proposals (PaySelection).', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543788 AND AD_Language='en_US';

-- K = CreditCard (Kreditkarte intern) — currently inactive
-- AD_Ref_List_ID=333
UPDATE AD_Ref_List SET Description='Interne Kreditkartenzahlung. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=333;
UPDATE AD_Ref_List_Trl SET Description='Interne Kreditkartenzahlung. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=333 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Internal credit card payment. Not included in payment proposals (PaySelection).', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=333 AND AD_Language='en_US';

-- L = PayPal
-- AD_Ref_List_ID=541987
UPDATE AD_Ref_List SET Description='PayPal-Zahlung. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=541987;
UPDATE AD_Ref_List_Trl SET Description='PayPal-Zahlung. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=541987 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='PayPal payment. Not included in payment proposals (PaySelection).', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=541987 AND AD_Language='en_US';

-- M = Mixed — currently inactive
-- AD_Ref_List_ID=52000
UPDATE AD_Ref_List SET Description='Mehrere Zahlungsarten kombiniert. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=52000;
UPDATE AD_Ref_List_Trl SET Description='Mehrere Zahlungsarten kombiniert. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=52000 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Multiple payment methods combined. Not included in payment proposals (PaySelection).', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=52000 AND AD_Language='en_US';

-- P = OnCredit (Auf Ziel)
-- AD_Ref_List_ID=337
UPDATE AD_Ref_List SET Description='Zahlung auf Ziel gemäss Zahlungsbedingungen. In Zahlungsvorschlägen (PaySelection) enthalten: Kreditorenrechnungen und Debitorengutschriften. SEPA-Überweisungsdatei wird erstellt.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=337;
UPDATE AD_Ref_List_Trl SET Description='Zahlung auf Ziel gemäss Zahlungsbedingungen. In Zahlungsvorschlägen (PaySelection) enthalten: Kreditorenrechnungen und Debitorengutschriften. SEPA-Überweisungsdatei wird erstellt.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=337 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Payment on account per payment terms. Included in payment proposals (PaySelection): vendor invoices and customer credit memos. SEPA credit transfer file is generated.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=337 AND AD_Language='en_US';

-- R = InstantBankTransfer (Sofortüberweisung) — currently inactive
-- AD_Ref_List_ID=543127
UPDATE AD_Ref_List SET Description='Sofortüberweisung (z.B. Klarna). Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543127;
UPDATE AD_Ref_List_Trl SET Description='Sofortüberweisung (z.B. Klarna). Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543127 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Instant bank transfer (e.g. Klarna). Not included in payment proposals (PaySelection).', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543127 AND AD_Language='en_US';

-- S = Check (Scheck) — currently inactive
-- AD_Ref_List_ID=335
UPDATE AD_Ref_List SET Description='Zahlung per Scheck. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=335;
UPDATE AD_Ref_List_Trl SET Description='Zahlung per Scheck. Nicht in Zahlungsvorschlägen (PaySelection) enthalten.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=335 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Payment by check. Not included in payment proposals (PaySelection).', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=335 AND AD_Language='en_US';

-- T = DirectDeposit (Überweisung)
-- AD_Ref_List_ID=334
UPDATE AD_Ref_List SET Description='Banküberweisung an Lieferant/Geschäftspartner. In Zahlungsvorschlägen (PaySelection) enthalten: Kreditorenrechnungen und Debitorengutschriften. SEPA-Überweisungsdatei wird erstellt.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=334;
UPDATE AD_Ref_List_Trl SET Description='Banküberweisung an Lieferant/Geschäftspartner. In Zahlungsvorschlägen (PaySelection) enthalten: Kreditorenrechnungen und Debitorengutschriften. SEPA-Überweisungsdatei wird erstellt.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=334 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Bank transfer to vendor/business partner. Included in payment proposals (PaySelection): vendor invoices and customer credit memos. SEPA credit transfer file is generated.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=334 AND AD_Language='en_US';

-- U = CreditCardExtern (Kreditkarte Extern) — currently inactive
-- AD_Ref_List_ID=543126
UPDATE AD_Ref_List SET Description='Extern abgewickelte Kreditkartenzahlung (z.B. Stripe, Adyen). Nicht in Zahlungsvorschlägen (PaySelection) enthalten, da Zahlung bereits extern erfolgt ist. Zuordnung über Zahlung-Zuordnung (WebUI), REST-API oder Avis.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543126;
UPDATE AD_Ref_List_Trl SET Description='Extern abgewickelte Kreditkartenzahlung (z.B. Stripe, Adyen). Nicht in Zahlungsvorschlägen (PaySelection) enthalten, da Zahlung bereits extern erfolgt ist. Zuordnung über Zahlung-Zuordnung (WebUI), REST-API oder Avis.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543126 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Externally processed credit card payment (e.g. Stripe, Adyen). Not included in payment proposals (PaySelection) since payment is already handled externally. Allocate via Payment Allocation (WebUI), REST API, or Remittance Advice.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543126 AND AD_Language='en_US';

-- V = PayPalExtern (PayPal Extern) — currently inactive
-- AD_Ref_List_ID=543125
UPDATE AD_Ref_List SET Description='Extern abgewickelte PayPal-Zahlung. Nicht in Zahlungsvorschlägen (PaySelection) enthalten, da Zahlung bereits extern erfolgt ist. Zuordnung über Zahlung-Zuordnung (WebUI), REST-API oder Avis.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543125;
UPDATE AD_Ref_List_Trl SET Description='Extern abgewickelte PayPal-Zahlung. Nicht in Zahlungsvorschlägen (PaySelection) enthalten, da Zahlung bereits extern erfolgt ist. Zuordnung über Zahlung-Zuordnung (WebUI), REST-API oder Avis.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543125 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Externally processed PayPal payment. Not included in payment proposals (PaySelection) since payment is already handled externally. Allocate via Payment Allocation (WebUI), REST API, or Remittance Advice.', Updated='2026-02-20 12:00', UpdatedBy=100 WHERE AD_Ref_List_ID=543125 AND AD_Language='en_US';
