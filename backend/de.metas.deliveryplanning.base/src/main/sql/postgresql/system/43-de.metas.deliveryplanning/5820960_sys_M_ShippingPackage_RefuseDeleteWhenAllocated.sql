-- Delivery Planning: a delivery instruction is CANCELLED or CLOSED, never deleted - so refuse to delete
-- a shipping package that any M_Delivery_Planning_Alloc points at, active or retired. A retired
-- allocation is exactly the history the retirement exists to keep, so "cancel it instead" is the answer
-- there as much as for a live booking.
--
-- The refusal lives in the application, not in a foreign-key action: a cascade would destroy the rows
-- silently, and NO ACTION would fail with a raw constraint violation naming nothing the operator can act
-- on. Guarding the PACKAGE also guards the INSTRUCTION -- deleting an instruction force-deletes its
-- M_ShippingPackage lines and so always reaches this guard first, which is why the instruction itself
-- carries no separate guard.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_Message   545814 (M_ShippingPackage.Allocated)
--
-- Any stack that applied this script BEFORE the _Trl seed covered a base language that is not
-- flagged as a system language needs this once -- the runner will not re-run an applied file.
-- A no-op wherever the base language is also flagged a system language, which is the usual setup:
--   INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
--   SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
--     FROM AD_Language l, AD_Message t
--    WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545814
--      AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgText, MsgType, EntityType)
VALUES (545814 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.deliveryplanning.M_ShippingPackage.Allocated',
        'Das Versandpaket kann nicht gelöscht werden, da es der Lieferanweisung {0} zugeordnet ist. Bitte die Lieferanweisung stornieren oder schließen, statt sie zu löschen.', 'E', 'D')
;

UPDATE AD_Message SET ErrorCode='DP_DELETE_ALLOCATED_PACKAGE', Updated=TO_TIMESTAMP('2026-08-27 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545814;

-- seed AD_Message_Trl for every active system or base language, copying the German base text
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545814
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The shipping package cannot be deleted because it is allocated to delivery instruction {0}. Cancel or close the delivery instruction instead of deleting it.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545814;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 13:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID=545814;
