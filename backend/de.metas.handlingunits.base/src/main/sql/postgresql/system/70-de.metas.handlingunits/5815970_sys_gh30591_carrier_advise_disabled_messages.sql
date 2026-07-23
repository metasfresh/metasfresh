-- gh30591 nShift Lieferweg AC9: AD_Messages explaining why the mobile "Advise Carrier" button
-- is shown but disabled.  Two reasons:
--   NoTarget  — no pick target yet; carrier advise only available after picking
--   ReadOnly  — carrier has already been set manually; re-advising not possible
--
-- IDs allocated from idserver.metas.de on 2026-07-23:
--   AD_Message 545786  (de.metas.picking.CarrierAdvise.Disabled.NoTarget)
--   AD_Message 545787  (de.metas.picking.CarrierAdvise.Disabled.ReadOnly)

-- ============================================================
-- Message 1: de.metas.picking.CarrierAdvise.Disabled.NoTarget
-- ============================================================

-- Value: de.metas.picking.CarrierAdvise.Disabled.NoTarget
-- 2026-07-23T10:10:00.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545786 /*From ID Server*/,0,TO_TIMESTAMP('2026-07-23 10:10:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Noch kein Kommissionierziel vorhanden – die Lieferweg-Abfrage ist erst nach dem Kommissionieren möglich.','I',TO_TIMESTAMP('2026-07-23 10:10:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking.CarrierAdvise.Disabled.NoTarget')
;

-- 2026-07-23T10:10:01.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545786
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2026-07-23T10:10:02.000Z
UPDATE AD_Message_Trl SET MsgText='No pick target yet – carrier advise is only available after picking.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-23 10:10:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545786
;

-- 2026-07-23T10:10:03.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-23 10:10:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545786
;

-- 2026-07-23T10:10:04.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-23 10:10:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545786
;


-- ============================================================
-- Message 2: de.metas.picking.CarrierAdvise.Disabled.ReadOnly
-- ============================================================

-- Value: de.metas.picking.CarrierAdvise.Disabled.ReadOnly
-- 2026-07-23T10:10:10.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545787 /*From ID Server*/,0,TO_TIMESTAMP('2026-07-23 10:10:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Der Lieferweg wurde bereits (manuell) festgelegt – eine erneute Abfrage ist nicht möglich.','I',TO_TIMESTAMP('2026-07-23 10:10:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking.CarrierAdvise.Disabled.ReadOnly')
;

-- 2026-07-23T10:10:11.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545787
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2026-07-23T10:10:12.000Z
UPDATE AD_Message_Trl SET MsgText='The carrier has already been set (manually) – re-advising is not possible.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-23 10:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545787
;

-- 2026-07-23T10:10:13.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-23 10:10:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545787
;

-- 2026-07-23T10:10:14.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-23 10:10:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545787
;
