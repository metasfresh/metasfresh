-- me03#29347 — distinguish destroyed-HU from product-mismatch when scanning a QR during picking.
-- Value: de.metas.handlingunits.picking.job.QR_CODE_HU_DESTROYED_ERROR_MSG
-- 2026-05-05 12:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545677 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-05 12:00','YYYY-MM-DD HH24:MI'),0,'D','Y','HU wurde zerstört. Bitte neuen QR-Code anfordern.','E','QR_CODE_HU_DESTROYED',TO_TIMESTAMP('2026-05-05 12:00','YYYY-MM-DD HH24:MI'),0,'de.metas.handlingunits.picking.job.QR_CODE_HU_DESTROYED_ERROR_MSG')
;

-- 2026-05-05 12:00
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545677
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- en_US translation
-- 2026-05-05 12:00
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HU was destroyed. Please request a new QR code.', Updated=TO_TIMESTAMP('2026-05-05 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=0
WHERE AD_Language='en_US' AND AD_Message_ID=545677
;

-- de_DE / de_CH inherit base text via AD_Message; flag as translated so the UI shows them.
-- 2026-05-05 12:00
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-05 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=0
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545677
;
