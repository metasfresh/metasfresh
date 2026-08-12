-- Run mode: SWING_CLIENT

-- Value: ERR_RECEIPT_SCHEDULE_NOT_FOUND
-- 2025-12-08T18:56:34.363Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545618,0,TO_TIMESTAMP('2025-12-08 18:56:34.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Für die angegebenen Parameter wurde kein Wareneingangsplan gefunden.','E',TO_TIMESTAMP('2025-12-08 18:56:34.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_RECEIPT_SCHEDULE_NOT_FOUND')
;

-- 2025-12-08T18:56:34.378Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545618 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_RECEIPT_SCHEDULE_NOT_FOUND
-- 2025-12-08T18:56:46.428Z
UPDATE AD_Message_Trl SET MsgText='No receipt schedule found for the specified parameters.',Updated=TO_TIMESTAMP('2025-12-08 18:56:46.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545618
;

-- 2025-12-08T18:56:46.428Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ERR_RECEIPT_SCHEDULE_INVALID_IDENTIFICATION_METHOD
-- 2025-12-08T19:17:05.382Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545619,0,TO_TIMESTAMP('2025-12-08 19:17:05.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Es muss genau eine Identifikationsmethode angegeben werden: receiptScheduleId, (externalHeaderId und externalLineId) oder orderLineId.','E',TO_TIMESTAMP('2025-12-08 19:17:05.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_RECEIPT_SCHEDULE_INVALID_IDENTIFICATION_METHOD')
;

-- 2025-12-08T19:17:05.383Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545619 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_RECEIPT_SCHEDULE_INVALID_IDENTIFICATION_METHOD
-- 2025-12-08T19:17:14.622Z
UPDATE AD_Message_Trl SET MsgText='Exactly one identification method must be provided: receiptScheduleId, (externalHeaderId and externalLineId), or orderLineId.',Updated=TO_TIMESTAMP('2025-12-08 19:17:14.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545619
;

-- 2025-12-08T19:17:14.622Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ERR_RECEIPT_SCHEDULE_INVALID_IDENTIFICATION_METHOD
-- 2025-12-08T19:26:42.272Z
UPDATE AD_Message SET ErrorCode='INVALID_IDENTIFICATION_METHOD',Updated=TO_TIMESTAMP('2025-12-08 19:26:42.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545619
;

-- Value: ERR_RECEIPT_SCHEDULE_NOT_FOUND
-- 2025-12-08T19:27:28.928Z
UPDATE AD_Message SET ErrorCode='RECEIPT_SCHEDULE_NOT_FOUND',Updated=TO_TIMESTAMP('2025-12-08 19:27:28.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545618
;
