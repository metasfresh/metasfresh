-- AD_Message base text is in German (DE).
-- Rationale: most users are German-speakers. If a translation is missing or broken, the
-- fallback (AD_Message.MsgText) should show German to German users — better to show DE
-- to EN users (mostly developers) than EN to DE users.
-- en_US translation is provided via the AD_Message_Trl override below.

-- Message 1: DDOrderPickingReconcile_PickerBusy
-- 2026-05-27T01:00:00.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545725 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-27 01:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Änderung nicht möglich — die Kommissionierung läuft bereits für den Distributionsauftrag dieser Lieferdisposition (Nr. {0}).','E',TO_TIMESTAMP('2026-05-27 01:00:00','YYYY-MM-DD HH24:MI:SS'),100,'DDOrderPickingReconcile_PickerBusy')
;

-- 2026-05-27T01:00:01.000Z
UPDATE AD_Message SET ErrorCode='DDOrderPickingReconcile_PickerBusy', Updated=TO_TIMESTAMP('2026-05-27 01:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545725 /*From ID Server*/
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-05-27T01:00:02.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545725 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-05-27T01:00:03.000Z
UPDATE AD_Message_Trl SET MsgText='Cannot change — picking is already in progress for the DD_Order of this shipment schedule (No {0}).',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-27 01:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545725 /*From ID Server*/
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
-- 2026-05-27T01:00:04.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-27 01:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545725 /*From ID Server*/
;

-- 2026-05-27T01:00:05.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-27 01:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545725 /*From ID Server*/
;

-- Message 2: DDOrderPickingReconcile_NetworkGap
-- 2026-05-27T01:00:06.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545726 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-27 01:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kein Quelllager im Verteilungsnetz {0} für das Kommissionierungslager gefunden.','E',TO_TIMESTAMP('2026-05-27 01:00:06','YYYY-MM-DD HH24:MI:SS'),100,'DDOrderPickingReconcile_NetworkGap')
;

-- 2026-05-27T01:00:07.000Z
UPDATE AD_Message SET ErrorCode='DDOrderPickingReconcile_NetworkGap', Updated=TO_TIMESTAMP('2026-05-27 01:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545726 /*From ID Server*/
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-05-27T01:00:08.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545726 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-05-27T01:00:09.000Z
UPDATE AD_Message_Trl SET MsgText='No source warehouse found in distribution network {0} for the packing warehouse.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-27 01:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545726 /*From ID Server*/
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
-- 2026-05-27T01:00:10.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-27 01:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545726 /*From ID Server*/
;

-- 2026-05-27T01:00:11.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-27 01:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545726 /*From ID Server*/
;

-- Message 3: DDOrderPickingReconcile_MandatoryNetwork
-- 2026-05-27T01:00:12.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545727 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-27 01:00:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ein Verteilungsnetz ist Pflicht, wenn Kommissionierungslager=Ja gesetzt ist.','E',TO_TIMESTAMP('2026-05-27 01:00:12','YYYY-MM-DD HH24:MI:SS'),100,'DDOrderPickingReconcile_MandatoryNetwork')
;

-- 2026-05-27T01:00:13.000Z
UPDATE AD_Message SET ErrorCode='DDOrderPickingReconcile_MandatoryNetwork', Updated=TO_TIMESTAMP('2026-05-27 01:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545727 /*From ID Server*/
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-05-27T01:00:14.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545727 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-05-27T01:00:15.000Z
UPDATE AD_Message_Trl SET MsgText='A distribution network (DD_NetworkDistribution_ID) is mandatory when IsPackingWarehouse=Y.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-27 01:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545727 /*From ID Server*/
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
-- 2026-05-27T01:00:16.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-27 01:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545727 /*From ID Server*/
;

-- 2026-05-27T01:00:17.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-27 01:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545727 /*From ID Server*/
;
