-- AD_Message for advance payment without Letter of Credit validation.
-- Triggered when an order has multiple advance-payment breaks but no LC break.

INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, MsgText, MsgType, EntityType)
VALUES
  (545778 /*From ID Server*/, 0, 0, 'Y', '2026-07-21 12:00', 0, '2026-07-21 12:00', 0,
   'de.metas.invoice.proforma.MultipleAdvanceBreaksUnsupported',
   'Order {0} has more than one advance payment break without a Letter of Credit; this is not supported.',
   'E', 'D')
;

UPDATE AD_Message SET ErrorCode='MultipleAdvanceBreaksUnsupported', Updated='2026-07-21 12:00', UpdatedBy=0 WHERE AD_Message_ID=545778 /*From ID Server*/;

-- DE translation
INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, MsgText, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES
  (545778 /*From ID Server*/, 'de_DE',
   'Bestellung {0} hat mehrere Zahlungsfortschrittsmeilensteine ohne Letter of Credit; dies wird nicht unterstützt.',
   'Y', 0, 0, 'Y', '2026-07-21 12:00', 0, '2026-07-21 12:00', 0)
;
