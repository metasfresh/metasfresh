-- 2026-04-24 https://github.com/metasfresh/me03/issues/29368
-- AD_Message entries for proforma-order allocation precondition errors.
-- {0} = order DocumentNo; {0}/{1} = proforma/order value for mismatch messages.

INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, MsgText, MsgType, EntityType)
VALUES
  (545667 /*From ID Server*/, 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0,
   'de.metas.invoice.proforma.NoLCBreakInOrder',
   'Order {0} has no Letter-of-Credit break in its payment term; cannot allocate a purchase proforma invoice.',
   'E', 'D'),
  (545668 /*From ID Server*/, 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0,
   'de.metas.invoice.proforma.MultipleLCBreaksUnsupported',
   'Order {0} has multiple Letter-of-Credit breaks in its payment term; this is not supported in iteration 2 (see https://github.com/metasfresh/me03/issues/29369).',
   'E', 'D'),
  (545669 /*From ID Server*/, 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0,
   'de.metas.invoice.proforma.CurrencyMismatch',
   'Proforma currency ({0}) does not match order currency ({1}).',
   'E', 'D'),
  (545670 /*From ID Server*/, 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0,
   'de.metas.invoice.proforma.VendorMismatch',
   'Proforma vendor ({0}) does not match order vendor ({1}).',
   'E', 'D')
;

UPDATE AD_Message SET ErrorCode='NoLCBreakInOrder',           Updated='2026-04-24 21:00', UpdatedBy=0 WHERE AD_Message_ID=545667 /*From ID Server*/;
UPDATE AD_Message SET ErrorCode='MultipleLCBreaksUnsupported', Updated='2026-04-24 21:00', UpdatedBy=0 WHERE AD_Message_ID=545668 /*From ID Server*/;
UPDATE AD_Message SET ErrorCode='CurrencyMismatch',           Updated='2026-04-24 21:00', UpdatedBy=0 WHERE AD_Message_ID=545669 /*From ID Server*/;
UPDATE AD_Message SET ErrorCode='VendorMismatch',             Updated='2026-04-24 21:00', UpdatedBy=0 WHERE AD_Message_ID=545670 /*From ID Server*/;

-- DE translations (proper umlauts: Währung, unterstützt, Meilensteine)
INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, MsgText, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES
  (545667 /*From ID Server*/, 'de_DE',
   'Bestellung {0} hat keinen Letter-of-Credit-Meilenstein in den Zahlungsbedingungen; Proforma-Rechnung kann nicht zugeordnet werden.',
   'Y', 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0),
  (545668 /*From ID Server*/, 'de_DE',
   'Bestellung {0} hat mehrere Letter-of-Credit-Meilensteine; dies wird in Iteration 2 nicht unterstützt (siehe https://github.com/metasfresh/me03/issues/29369).',
   'Y', 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0),
  (545669 /*From ID Server*/, 'de_DE',
   'Proforma-Währung ({0}) passt nicht zur Bestellwährung ({1}).',
   'Y', 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0),
  (545670 /*From ID Server*/, 'de_DE',
   'Proforma-Lieferant ({0}) passt nicht zum Bestell-Lieferant ({1}).',
   'Y', 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0)
;
