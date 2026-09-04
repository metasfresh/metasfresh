-- AD_Message shown when a purchase order (or one of its lines) is added to a shipper transport order
-- whose TransportDirection has no receipt leg (i.e. an Outgoing-only transport order): a purchase
-- document is receipt-side and may only join a transport order that is Incoming or Dropship.
-- The message key (Value 'WrongTransportDirectionForPurchaseOrder') is the stable link to the Java code
-- (PurchaseOrderToShipperTransportationService).
--
-- AD_Message base text is in German (DE). Rationale: most users are German-speakers; if a translation
-- is missing the fallback (AD_Message.MsgText) shows German. en_US translation is provided below.
-- {0} is the purchase order's DocumentNo, {1} is the transport order's DocumentNo
-- (filled in by AdempiereException(MSG, order.getDocumentNo(), shipperTransportation.getDocumentNo())).

-- =============================================================================
-- WrongTransportDirectionForPurchaseOrder  (AD_Message_ID 545827, ErrorCode WRONG_TRANSPORT_DIRECTION)
-- =============================================================================

-- 2026-09-03T00:00:00.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545827 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-03 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Bestellung {0} kann nicht zum Transportauftrag {1} hinzugefügt werden: Bestellungen benötigen einen Transportauftrag mit Wareneingangsrichtung (eingehend oder Streckengeschäft).','E',TO_TIMESTAMP('2026-09-03 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'WrongTransportDirectionForPurchaseOrder')
;

-- AD_Message.ErrorCode is varchar(40); use a short form (the full key lives in AD_Message.Value).
-- 2026-09-03T00:00:01.000Z
UPDATE AD_Message SET ErrorCode='WRONG_TRANSPORT_DIRECTION', Updated=TO_TIMESTAMP('2026-09-03 00:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545827 /*From ID Server*/
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-09-03T00:00:02.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545827 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-09-03T00:00:03.000Z
UPDATE AD_Message_Trl SET MsgText='Purchase order {0} cannot be added to transport order {1}: purchase orders require a transport order with an incoming receipt leg (incoming or dropship).',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-03 00:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545827 /*From ID Server*/
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
-- 2026-09-03T00:00:04.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-03 00:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545827 /*From ID Server*/
;

-- 2026-09-03T00:00:05.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-03 00:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545827 /*From ID Server*/
;
