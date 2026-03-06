-- Run mode: SWING_CLIENT

-- Value: CopiedOn
-- 2025-08-29T10:01:18.637Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='kopiert am {0} von',Updated=TO_TIMESTAMP('2025-08-29 10:01:18.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=540124
;

-- 2025-08-29T10:01:18.693Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: CopiedOn
-- 2025-08-29T10:01:35.995Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='kopiert am {0} von',Updated=TO_TIMESTAMP('2025-08-29 10:01:35.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=540124
;

-- 2025-08-29T10:01:36.050Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

