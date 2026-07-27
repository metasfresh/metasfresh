-- Widens the two picking-replenishment change refusals so they NAME the work that blocks the edit.
--
-- A consolidated replenishment DD_Order serves several deliveries, so the work that blocks a traffic
-- manager's edit is usually somebody else's. The previous texts named only the DD_Order, leaving the
-- reader with no way to find out who to talk to. Both now carry the blocking workstation assignment
-- and its shipment schedule; the movement text additionally carries the quantity already moved.
--
-- No new AD_Message row: the two existing messages are re-worded in place, so every consumer keeps
-- its ErrorCode. The base text stays German (fallback for German users); en_US is a real translation
-- and de_CH is derived from de_DE (no 'ß' occurs, so the Swiss form is identical here).
--
-- Every other active system language keeps an AD_Message_Trl row seeded from the OLD base text and
-- flagged IsTranslated='N'. MessagesMapRepository serves trl.MsgText without consulting IsTranslated,
-- so such a row would keep showing the superseded single-placeholder wording. Each message therefore
-- re-seeds any missing system-language row and refreshes every not-actively-translated row from the
-- new base text — the same all-active-system-languages coverage the seeding scripts used. Rows an
-- actual translator marked IsTranslated='Y' are left to that translator.
--
-- IDs used (all pre-existing, allocated from the ID server when the messages were created):
--   AD_Message 545725 DDOrderPickingReconcile_PickerBusy
--   AD_Message 545751 DDOrderPickingReconcile_MovementStarted

-- Message: DDOrderPickingReconcile_PickerBusy — {0} DD_Order, {1} blocking assignment, {2} its shipment schedule
-- 2026-07-26T01:00:00.000Z
UPDATE AD_Message SET MsgText='Änderung nicht möglich — der Distributionsauftrag Nr. {0} wird bereits kommissioniert: Kommissionierplan Nr. {1} (Lieferdisposition Nr. {2}) ist gerade in Arbeit. Der Distributionsauftrag versorgt mehrere Lieferdispositionen; die Änderung ist erst möglich, wenn diese Kommissionierung abgeschlossen oder abgebrochen ist.',Updated=TO_TIMESTAMP('2026-07-26 01:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545725 /*From ID Server*/
;

-- Seed AD_Message_Trl for any active system language that has no row yet, using the new base text.
-- 2026-07-26T01:00:01.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545725 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Refresh every not-actively-translated language from the new base text.
-- 2026-07-26T01:00:02.000Z
UPDATE AD_Message_Trl trl SET MsgText=m.MsgText,Updated=TO_TIMESTAMP('2026-07-26 01:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
FROM AD_Message m
WHERE m.AD_Message_ID=trl.AD_Message_ID AND trl.AD_Message_ID=545725 /*From ID Server*/ AND trl.IsTranslated='N'
;

-- 2026-07-26T01:00:03.000Z
UPDATE AD_Message_Trl SET MsgText='Änderung nicht möglich — der Distributionsauftrag Nr. {0} wird bereits kommissioniert: Kommissionierplan Nr. {1} (Lieferdisposition Nr. {2}) ist gerade in Arbeit. Der Distributionsauftrag versorgt mehrere Lieferdispositionen; die Änderung ist erst möglich, wenn diese Kommissionierung abgeschlossen oder abgebrochen ist.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-26 01:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545725 /*From ID Server*/
;

-- Swiss German: derived from the de_DE wording; the only systematic difference (ß -> ss) does not occur here.
-- 2026-07-26T01:00:04.000Z
UPDATE AD_Message_Trl SET MsgText='Änderung nicht möglich — der Distributionsauftrag Nr. {0} wird bereits kommissioniert: Kommissionierplan Nr. {1} (Lieferdisposition Nr. {2}) ist gerade in Arbeit. Der Distributionsauftrag versorgt mehrere Lieferdispositionen; die Änderung ist erst möglich, wenn diese Kommissionierung abgeschlossen oder abgebrochen ist.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-26 01:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545725 /*From ID Server*/
;

-- 2026-07-26T01:00:05.000Z
UPDATE AD_Message_Trl SET MsgText='Cannot change — distribution order No. {0} is already being picked: picking job schedule No. {1} (shipment candidate No. {2}) is in progress. The distribution order serves several shipment candidates; the change is only possible once that picking has been completed or aborted.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-26 01:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545725 /*From ID Server*/
;

-- Message: DDOrderPickingReconcile_MovementStarted — {0} DD_Order, {1} qty moved, {2} blocking assignment, {3} its shipment schedule
-- 2026-07-26T01:00:06.000Z
UPDATE AD_Message SET MsgText='Änderung nicht möglich — für den Distributionsauftrag Nr. {0} wurde die Ware bereits bewegt: {1} in Transit oder geliefert. Betroffen ist unter anderem der Kommissionierplan Nr. {2} (Lieferdisposition Nr. {3}). Der Distributionsauftrag versorgt mehrere Lieferdispositionen; die Änderung ist erst nach Abschluss der bereits begonnenen Bewegung möglich.',Updated=TO_TIMESTAMP('2026-07-26 01:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545751 /*From ID Server*/
;

-- Seed AD_Message_Trl for any active system language that has no row yet, using the new base text.
-- 2026-07-26T01:00:07.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545751 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Refresh every not-actively-translated language from the new base text.
-- 2026-07-26T01:00:08.000Z
UPDATE AD_Message_Trl trl SET MsgText=m.MsgText,Updated=TO_TIMESTAMP('2026-07-26 01:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
FROM AD_Message m
WHERE m.AD_Message_ID=trl.AD_Message_ID AND trl.AD_Message_ID=545751 /*From ID Server*/ AND trl.IsTranslated='N'
;

-- 2026-07-26T01:00:09.000Z
UPDATE AD_Message_Trl SET MsgText='Änderung nicht möglich — für den Distributionsauftrag Nr. {0} wurde die Ware bereits bewegt: {1} in Transit oder geliefert. Betroffen ist unter anderem der Kommissionierplan Nr. {2} (Lieferdisposition Nr. {3}). Der Distributionsauftrag versorgt mehrere Lieferdispositionen; die Änderung ist erst nach Abschluss der bereits begonnenen Bewegung möglich.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-26 01:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545751 /*From ID Server*/
;

-- Swiss German: derived from the de_DE wording; the only systematic difference (ß -> ss) does not occur here.
-- 2026-07-26T01:00:10.000Z
UPDATE AD_Message_Trl SET MsgText='Änderung nicht möglich — für den Distributionsauftrag Nr. {0} wurde die Ware bereits bewegt: {1} in Transit oder geliefert. Betroffen ist unter anderem der Kommissionierplan Nr. {2} (Lieferdisposition Nr. {3}). Der Distributionsauftrag versorgt mehrere Lieferdispositionen; die Änderung ist erst nach Abschluss der bereits begonnenen Bewegung möglich.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-26 01:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545751 /*From ID Server*/
;

-- 2026-07-26T01:00:11.000Z
UPDATE AD_Message_Trl SET MsgText='Cannot change — goods have already been moved for distribution order No. {0}: {1} in transit or delivered. Affected, among others: picking job schedule No. {2} (shipment candidate No. {3}). The distribution order serves several shipment candidates; the change is only possible once the movement that has already started is finished.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-26 01:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545751 /*From ID Server*/
;
