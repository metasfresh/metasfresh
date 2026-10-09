-- User-facing error when allocating an invoice with a service fee in the payment allocation view:
--   545918: the business partner of the selected payment(s) is not configured as invoice processing service company
-- {0} = invoice DocumentNo, {1} = payment DocumentNo(s), {2} = payment business partner

-- 545918 InvoiceProcessingServiceCompany_NoConfigForPaymentPartner
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545918 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-08 21:40:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rechnung {0} mit Servicegebühr: Der Geschäftspartner {2} der Zahlung {1} ist nicht als Rechnungsserviceunternehmen hinterlegt. Bitte wählen Sie eine Zahlung des Rechnungsserviceunternehmens aus oder hinterlegen Sie den Geschäftspartner als Rechnungsserviceunternehmen.','E',TO_TIMESTAMP('2026-10-08 21:40:00','YYYY-MM-DD HH24:MI:SS'),100,'InvoiceProcessingServiceCompany_NoConfigForPaymentPartner')
;

UPDATE AD_Message SET ErrorCode='SERVICE_FEE_PAYMENT_PARTNER_NO_CONFIG', Updated=TO_TIMESTAMP('2026-10-08 21:40:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545918
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545918
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Invoice {0} with service fee: business partner {2} of payment {1} is not configured as an invoice processing service company. Please select a payment of the invoice processing service company or configure the business partner as an invoice processing service company.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-08 21:40:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545918
;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-08 21:40:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545918
;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-08 21:40:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545918
;
