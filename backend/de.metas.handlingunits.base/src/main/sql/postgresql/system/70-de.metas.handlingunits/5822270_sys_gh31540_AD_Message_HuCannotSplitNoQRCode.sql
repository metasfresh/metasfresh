-- MobileUI: user-friendly error message for splitting an aggregate HU that has no QR code assigned yet
-- Creates 1 AD_Message record for de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages

-- Value: de.metas.mobile.qr.HuCannotSplitNoQRCode
-- 2026-09-03 09:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545826 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-03 09:00','YYYY-MM-DD HH24:MI'),100,'D','Y','Diese Handling Unit kann nicht geteilt werden, weil ihr noch kein QR-Code zugewiesen ist','E','QR_HU_CANNOT_SPLIT_NO_QR_CODE',TO_TIMESTAMP('2026-09-03 09:00','YYYY-MM-DD HH24:MI'),100,'de.metas.mobile.qr.HuCannotSplitNoQRCode')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545826
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Cannot split this handling unit because it has no QR code assigned yet',Updated=TO_TIMESTAMP('2026-09-03 09:01','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545826
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-03 09:01','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545826
;
