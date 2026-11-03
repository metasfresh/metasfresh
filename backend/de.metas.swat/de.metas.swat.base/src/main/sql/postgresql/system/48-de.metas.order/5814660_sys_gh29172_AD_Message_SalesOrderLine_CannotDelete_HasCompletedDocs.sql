-- AD_Message thrown by the C_OrderLine TYPE_BEFORE_DELETE guards (de.metas.purchasecandidate.base,
-- de.metas.swat.base) when a sales order line still has a real downstream document
-- (a purchase candidate that produced a PO, a shipment schedule allocated to a non-voided inout,
-- or an invoice candidate with invoice lines).

-- 1. the message (base text = German)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545776 /*From ID Server*/,0,TO_TIMESTAMP('2026-07-20 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Auftragszeile kann nicht gelöscht werden, weil sie noch von einem abgeschlossenen Beleg (Rechnung, Lieferung oder Bestellung) referenziert wird. Stornieren Sie zuerst diesen Beleg.','E',TO_TIMESTAMP('2026-07-20 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'SalesOrderLine_CannotDelete_HasCompletedDocs')
;

-- 2. short ErrorCode
UPDATE AD_Message SET ErrorCode='SALES_ORDERLINE_CANNOT_DELETE', Updated=TO_TIMESTAMP('2026-07-20 14:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545776
;

-- 3. seed AD_Message_Trl for ALL active system languages with the base (DE) text, IsTranslated='N'
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545776
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 4. en_US override (the real English text) + IsTranslated='Y'
UPDATE AD_Message_Trl SET MsgText='Cannot delete the order line because it is still referenced by a completed document (invoice, shipment, or purchase order). Void or reverse that document first.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-20 14:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545776
;

-- 5. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the DE base)
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-20 14:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545776
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-20 14:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545776
;
