-- VAT-ID format validation error message. me03 30503.
-- {0} = the offending VAT-ID value passed by the interceptor.
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545761 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-19 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die USt-IdNr. „{0}" hat kein gültiges Format.','E',TO_TIMESTAMP('2026-06-19 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'VATaxID_Invalid_Format')
;

-- 2026-06-19T10:00:01
UPDATE AD_Message SET ErrorCode='VAT_ID_INVALID_FORMAT', Updated=TO_TIMESTAMP('2026-06-19 10:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545761
;

-- 2026-06-19T10:00:02 — seed _Trl rows for every active system language (DE base text, IsTranslated='N')
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545761
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2026-06-19T10:00:03 — en_US override
UPDATE AD_Message_Trl SET MsgText='The VAT-ID "{0}" has an invalid format.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-19 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545761
;

-- 2026-06-19T10:00:04 — de_DE (same as base, flip IsTranslated='Y')
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Die USt-IdNr. „{0}" hat kein gültiges Format.',Updated=TO_TIMESTAMP('2026-06-19 10:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545761
;

-- 2026-06-19T10:00:05 — de_CH (same as base, flip IsTranslated='Y')
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Die USt-IdNr. „{0}" hat kein gültiges Format.',Updated=TO_TIMESTAMP('2026-06-19 10:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545761
;
