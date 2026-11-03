-- Run mode: SWING_CLIENT

-- Value: de.metas.handlingunits.HUReceiptSchedule.PackageNumberNotMatching
-- 2025-08-25T08:42:46.508Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545572,0,TO_TIMESTAMP('2025-08-25 08:42:46.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Not enough unassigned packages to match all selected HUs.','E',TO_TIMESTAMP('2025-08-25 08:42:46.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.HUReceiptSchedule.PackageNumberNotMatching')
;

-- 2025-08-25T08:42:46.510Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545572 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.HUReceiptSchedule.PackageNumberNotMatching
-- 2025-08-25T08:43:21.834Z
UPDATE AD_Message_Trl SET MsgText='Nicht genügend nicht zugewiesene Pakete, um alle ausgewählten HUs abzudecken.',Updated=TO_TIMESTAMP('2025-08-25 08:43:21.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545572
;

-- 2025-08-25T08:43:21.835Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.HUReceiptSchedule.PackageNumberNotMatching
-- 2025-08-25T08:43:23.470Z
UPDATE AD_Message_Trl SET MsgText='Nicht genügend nicht zugewiesene Pakete, um alle ausgewählten HUs abzudecken.',Updated=TO_TIMESTAMP('2025-08-25 08:43:23.469000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545572
;

-- 2025-08-25T08:43:23.471Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.HUReceiptSchedule.PackageNumberNotMatching
-- 2025-08-25T08:43:25.841Z
UPDATE AD_Message_Trl SET MsgText='Nicht genügend nicht zugewiesene Pakete, um alle ausgewählten HUs abzudecken.',Updated=TO_TIMESTAMP('2025-08-25 08:43:25.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545572
;

-- 2025-08-25T08:43:25.842Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

