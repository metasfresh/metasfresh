-- Messages of the VAT ID check at sales invoice completion.
-- IDs from the central ID server (2026-11-28): AD_Message 546038, 546039.

-- AD_Message C_Invoice_ExemptLineVATaxIdMismatch (546036): it is about the customer's VAT ID only; the own VAT ID has its own message (546039)
UPDATE AD_Message SET MsgText='Zeile {0} ist steuerbefreit auf die USt-IdNr. {1} des Kunden, die Rechnung weist aber die USt-IdNr. {2} des Kunden aus. Bitte die Zeile auf eine eigene Rechnung setzen oder ihren Steuersatz ändern.', Updated=TO_TIMESTAMP('2026-11-28 09:00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Message_ID=546036
;

UPDATE AD_Message_Trl SET MsgText='Zeile {0} ist steuerbefreit auf die USt-IdNr. {1} des Kunden, die Rechnung weist aber die USt-IdNr. {2} des Kunden aus. Bitte die Zeile auf eine eigene Rechnung setzen oder ihren Steuersatz ändern.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-28 09:00:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546036
;

UPDATE AD_Message_Trl SET MsgText='Zeile {0} ist steuerbefreit auf die USt-IdNr. {1} des Kunden, die Rechnung weist aber die USt-IdNr. {2} des Kunden aus. Bitte die Zeile auf eine eigene Rechnung setzen oder ihren Steuersatz ändern.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-28 09:00:00:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546036
;

UPDATE AD_Message_Trl SET MsgText='Line {0} is tax-exempt on the customer''s VAT ID {1}, but the invoice shows the customer''s VAT ID {2}. Put the line on its own invoice or change its tax.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-28 09:00:00:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546036
;

-- AD_Message C_Invoice_SellerVATaxIdMismatch (546039)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546039 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-28 09:01:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Zeile {0} wird unter der eigenen USt-IdNr. {1} versteuert, die Rechnung weist aber die eigene USt-IdNr. {2} aus. Bitte die Zeile auf eine eigene Rechnung setzen oder ihren Steuersatz ändern.','E',TO_TIMESTAMP('2026-11-28 09:01:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Invoice_SellerVATaxIdMismatch')
;

UPDATE AD_Message SET ErrorCode='INVOICE_SELLER_VATAXID_MISMATCH', Updated=TO_TIMESTAMP('2026-11-28 09:01:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Message_ID=546039
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546039 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Line {0} is taxed under the own VAT ID {1}, but the invoice shows the own VAT ID {2}. Put the line on its own invoice or change its tax.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-28 09:01:00:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546039
;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-28 09:01:00:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546039
;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-28 09:01:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546039
;

-- AD_Message C_Invoice_ExemptLineNoDeliveryToTaxCountry (546038)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546038 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-28 09:02:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Zeile {0} hat einen steuerbefreiten Steuersatz für Lieferungen nach {1}, aber weder ihre Lieferung noch ihre Auftragszeile noch die Rechnungsadresse liegt in {1}. Die USt-IdNr., auf der die Steuerbefreiung beruht, kann daher nicht bestimmt werden. Bitte die Zeile aus ihrer Lieferung oder Auftragszeile anlegen oder ihren Steuersatz ändern.','E',TO_TIMESTAMP('2026-11-28 09:02:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Invoice_ExemptLineNoDeliveryToTaxCountry')
;

UPDATE AD_Message SET ErrorCode='INVOICE_EXEMPT_LINE_NOT_IN_TAX_COUNTRY', Updated=TO_TIMESTAMP('2026-11-28 09:02:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Message_ID=546038
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546038 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Line {0} has a tax-exempt tax for deliveries to {1}, but neither its shipment, its order line nor the invoice''s billing address is in {1}. So the VAT ID the exemption rests on cannot be determined. Create the line from its shipment or order line, or change its tax.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-28 09:02:00:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546038
;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-28 09:02:00:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546038
;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-28 09:02:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546038
;
