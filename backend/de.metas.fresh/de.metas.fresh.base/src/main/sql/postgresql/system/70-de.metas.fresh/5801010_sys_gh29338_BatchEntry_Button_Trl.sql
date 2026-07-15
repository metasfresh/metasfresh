-- Run mode: SWING_CLIENT

-- Value: webui.window.batchEntry.caption
-- 2026-05-06T07:20:08.442Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Schnellerfassung',Updated=TO_TIMESTAMP('2026-05-06 07:20:08.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544349
;

-- 2026-05-06T07:20:08.502Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: webui.window.batchEntry.caption
-- 2026-05-06T07:20:29.293Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Schnellerfassung',Updated=TO_TIMESTAMP('2026-05-06 07:20:29.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544349
;

-- 2026-05-06T07:20:29.350Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: webui.window.batchEntry.caption
-- 2026-05-06T07:20:55.054Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Capture rapide',Updated=TO_TIMESTAMP('2026-05-06 07:20:55.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=544349
;

-- 2026-05-06T07:20:55.111Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: webui.window.batchEntryClose.caption
-- 2026-05-06T07:23:44.893Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Massen Schnellerfassung',Updated=TO_TIMESTAMP('2026-05-06 07:23:44.893000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544350
;

-- 2026-05-06T07:23:44.949Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: webui.window.batchEntryClose.caption
-- 2026-05-06T07:23:56.489Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Massen Schnellerfassung',Updated=TO_TIMESTAMP('2026-05-06 07:23:56.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544350
;

-- 2026-05-06T07:23:56.547Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: webui.window.batchEntryClose.caption
-- 2026-05-06T07:24:17.234Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Fermer la saisie de données en masse',Updated=TO_TIMESTAMP('2026-05-06 07:24:17.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=544350
;

-- 2026-05-06T07:24:17.290Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

