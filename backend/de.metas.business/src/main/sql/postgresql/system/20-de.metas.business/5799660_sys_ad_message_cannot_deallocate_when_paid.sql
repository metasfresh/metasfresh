-- AD_Message for the deallocate guard: prevents removing a proforma↔order allocation
-- while there is still a completed/closed proforma payment pointing at the proforma.
-- Without this guard, the payment row keeps Proforma_Invoice_ID pointing at a now-
-- unallocated proforma, leaving an inconsistent state.

INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, MsgText, MsgType, EntityType)
VALUES
  (545673 /*From ID Server*/, 0, 0, 'Y', '2026-04-26 19:00', 0, '2026-04-26 19:00', 0,
   'de.metas.invoice.proforma.CannotDeallocateWhenPaid',
   'Cannot deallocate proforma {0} from order {1} — a completed payment is still tagged with this proforma. Reverse the payment first.',
   'E', 'D')
;

UPDATE AD_Message SET ErrorCode='CannotDeallocateWhenPaid', Updated='2026-04-26 19:00', UpdatedBy=0 WHERE AD_Message_ID=545673 /*From ID Server*/;

-- DE translation
INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, MsgText, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES
  (545673 /*From ID Server*/, 'de_DE',
   'Proforma {0} kann nicht von der Bestellung {1} entkoppelt werden — eine abgeschlossene Zahlung verweist noch auf diese Proforma. Bitte stornieren Sie zuerst die Zahlung.',
   'Y', 0, 0, 'Y', '2026-04-26 19:00', 0, '2026-04-26 19:00', 0)
;
