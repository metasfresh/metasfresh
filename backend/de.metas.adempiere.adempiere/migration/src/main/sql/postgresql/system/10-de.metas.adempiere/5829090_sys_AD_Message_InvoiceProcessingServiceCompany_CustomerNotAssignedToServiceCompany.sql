-- User-facing error when allocating a payment of an invoice processing service company to an invoice with service fee:
--   the invoice's customer is not assigned to that service company, so the service company must not retain a service fee on it
-- IDs allocated from idserver.metas.de on 2026-10-10:
--   AD_Message 545922 InvoiceProcessingServiceCompany_CustomerNotAssignedToServiceCompany
-- {0} = invoice DocumentNo, {1} = service company (payment business partner), {2} = invoice customer

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545922 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-10 08:10:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rechnung {0} mit Servicegebühr: Der Kunde {2} ist nicht dem Rechnungsserviceunternehmen {1} zugeordnet. Eine Zahlung von {1} kann nur Rechnungen der eigenen Kunden mit Servicegebühr zugeordnet werden.','E',TO_TIMESTAMP('2026-10-10 08:10:00','YYYY-MM-DD HH24:MI:SS'),100,'InvoiceProcessingServiceCompany_CustomerNotAssignedToServiceCompany')
;

UPDATE AD_Message SET ErrorCode='SERVICE_FEE_CUSTOMER_NOT_ASSIGNED', Updated=TO_TIMESTAMP('2026-10-10 08:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545922
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545922
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Invoice {0} with service fee: customer {2} is not assigned to the invoice processing service company {1}. A payment of {1} can only be allocated with service fee to invoices of its own customers.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-10 08:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545922
;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-10 08:10:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545922
;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-10 08:10:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545922
;
