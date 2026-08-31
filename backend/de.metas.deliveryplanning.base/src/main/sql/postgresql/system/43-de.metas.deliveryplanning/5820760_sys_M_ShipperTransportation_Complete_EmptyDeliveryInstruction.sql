-- Delivery Planning: refuse to COMPLETE a delivery instruction that has zero active allocations --
-- the reports that resolve through the allocation (docs_deliveryinstructions_description /
-- _forwarder / _productdetails) would print a blank document for it. A transport order legitimately
-- never has allocations and is unaffected: the two document roles sharing M_ShipperTransportation
-- are told apart by C_DocType.DocSubType.
-- No AD_Process / AD_Table_Process: this is a @DocValidate(TIMING_BEFORE_COMPLETE) guard on
-- M_ShipperTransportation.completeIt(), not a WebUI action with its own menu entry.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_Message   545811 (EmptyDeliveryInstruction)

INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgText, MsgType, EntityType)
VALUES (545811 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 14:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 14:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.deliveryplanning.CompleteDeliveryInstruction.EmptyDeliveryInstruction',
        'Die Lieferanweisung kann nicht abgeschlossen werden, da ihr keine aktiven Lieferplanungen zugeordnet sind.', 'E', 'D')
;

UPDATE AD_Message SET ErrorCode='DP_COMPLETE_EMPTY_INSTRUCTION', Updated=TO_TIMESTAMP('2026-08-27 14:30:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545811;

-- seed AD_Message_Trl for every active system language, copying the German base text
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545811
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The delivery instruction cannot be completed because it has no active delivery plannings allocated to it.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 14:30:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545811;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 14:30:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID=545811;
