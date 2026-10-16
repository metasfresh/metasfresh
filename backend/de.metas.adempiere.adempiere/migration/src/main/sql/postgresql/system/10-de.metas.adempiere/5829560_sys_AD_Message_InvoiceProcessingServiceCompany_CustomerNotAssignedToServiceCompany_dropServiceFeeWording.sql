-- The refusal "customer not assigned to the invoice processing service company" now also fires when NO service fee is
-- entered, so the "mit Servicegebühr" / "with service fee" qualifier in the message text is wrong. Drop that qualifier,
-- keeping the {0}/{1}/{2} parameters and meaning.
-- IDs allocated from idserver.metas.de on 2026-10-16:
--   AD_MigrationScript 5829560
-- {0} = invoice DocumentNo, {1} = service company (payment business partner), {2} = invoice customer

UPDATE AD_Message SET MsgText='Rechnung {0}: Der Kunde {2} ist nicht dem Rechnungsserviceunternehmen {1} zugeordnet. Eine Zahlung von {1} kann nur Rechnungen der eigenen Kunden zugeordnet werden.', Updated=TO_TIMESTAMP('2026-10-16 08:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545922
;

UPDATE AD_Message_Trl SET MsgText='Rechnung {0}: Der Kunde {2} ist nicht dem Rechnungsserviceunternehmen {1} zugeordnet. Eine Zahlung von {1} kann nur Rechnungen der eigenen Kunden zugeordnet werden.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 08:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545922
;

UPDATE AD_Message_Trl SET MsgText='Rechnung {0}: Der Kunde {2} ist nicht dem Rechnungsserviceunternehmen {1} zugeordnet. Eine Zahlung von {1} kann nur Rechnungen der eigenen Kunden zugeordnet werden.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 08:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545922
;

UPDATE AD_Message_Trl SET MsgText='Invoice {0}: customer {2} is not assigned to the invoice processing service company {1}. A payment of {1} can only be allocated to invoices of its own customers.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 08:10:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545922
;
