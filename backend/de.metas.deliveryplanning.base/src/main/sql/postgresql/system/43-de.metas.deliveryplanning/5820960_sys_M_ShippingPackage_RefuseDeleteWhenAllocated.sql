-- Delivery Planning: a delivery instruction is CANCELLED or CLOSED, never deleted - so refuse to delete
-- a shipping package that any M_Delivery_Planning_Alloc points at, active or retired.
--
-- The acceptance criteria know only close / remove / void / cancel; deleting an instruction or one of its
-- packages is not an operation this feature offers. Two things nevertheless make the delete reachable
-- today: M_ShipperTransportation and M_ShippingPackage both carry IsDeleteable='Y', and a DRAFTED
-- instruction's only guard is MMShipperTransportation.beforeDelete()'s isProcessed() check. Which foreign-key
-- action the allocation carries is not what makes the delete wrong, so the refusal is stated in the
-- application rather than in the schema: a cascade would destroy the rows silently, and NO ACTION would fail
-- with a raw constraint violation naming nothing the operator can act on.
--
-- Guarding the PACKAGE also guards the INSTRUCTION: PO.delete0() runs beforeDelete() before it fires
-- TYPE_BEFORE_DELETE, and MMShipperTransportation.beforeDelete() force-deletes every one of its
-- M_ShippingPackage lines, so deleting an instruction always reaches this guard first. A separate guard on
-- the instruction itself would be unreachable code.
--
-- Retired (IsActive='N') allocations refuse the delete too, deliberately: an instruction that once carried
-- a planning is exactly the document whose history the retirement exists to keep, and "cancel it instead"
-- is the answer there as much as for a live booking. The M_Delivery_Planning leg is the one place a delete
-- is legitimate - a shipment/receipt-schedule delete cascades into its plannings - and it is handled in
-- Java too: interceptor/M_Delivery_Planning.onDelete refuses the live case, then deletes the retired rows
-- itself, so the NO ACTION foreign keys declared in 5820400 never have to guess which kind they are looking
-- at.
--
-- Scoped by construction, not by a filter: only delivery planning creates allocations, so this message is
-- unreachable for the transport-order and handling-units packages that share M_ShippingPackage.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_Message   545814 (M_ShippingPackage.Allocated)

INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgText, MsgType, EntityType)
VALUES (545814 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.deliveryplanning.M_ShippingPackage.Allocated',
        'Das Versandpaket kann nicht gelöscht werden, da es der Lieferanweisung {0} zugeordnet ist. Bitte die Lieferanweisung stornieren oder schließen, statt sie zu löschen.', 'E', 'D')
;

UPDATE AD_Message SET ErrorCode='DP_DELETE_ALLOCATED_PACKAGE', Updated=TO_TIMESTAMP('2026-08-27 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545814;

-- seed AD_Message_Trl for every active system language, copying the German base text
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545814
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The shipping package cannot be deleted because it is allocated to delivery instruction {0}. Cancel or close the delivery instruction instead of deleting it.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545814;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 13:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID=545814;
