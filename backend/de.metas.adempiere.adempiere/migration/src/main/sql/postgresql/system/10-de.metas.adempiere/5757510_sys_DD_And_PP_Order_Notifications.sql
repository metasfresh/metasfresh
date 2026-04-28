-- Run mode: SWING_CLIENT

-- Value: EVENT_PP_Order_Created
-- 2025-06-12T07:47:58.365Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545558,0,TO_TIMESTAMP('2025-06-12 07:47:58.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y','Produktionsauftrag {0} wurde erstellt.','I',TO_TIMESTAMP('2025-06-12 07:47:58.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EVENT_PP_Order_Created')
;

-- 2025-06-12T07:47:58.368Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545558 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: EVENT_DD_Order_Created
-- 2025-06-12T07:48:34.424Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545559,0,TO_TIMESTAMP('2025-06-12 07:48:34.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Distributionsauftrag {0} wurde erstellt.','I',TO_TIMESTAMP('2025-06-12 07:48:34.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EVENT_DD_Order_Created')
;

-- 2025-06-12T07:48:34.426Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545559 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: EVENT_PP_Order_Created
-- 2025-06-12T07:48:39.364Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-12 07:48:39.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545558
;

-- Value: EVENT_PP_Order_Created
-- 2025-06-12T07:50:46.420Z
UPDATE AD_Message_Trl SET MsgText='Manufacturing Order {0} has been created.',Updated=TO_TIMESTAMP('2025-06-12 07:50:46.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545558
;

-- 2025-06-12T07:50:46.421Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: EVENT_DD_Order_Created
-- 2025-06-12T07:51:02.414Z
UPDATE AD_Message_Trl SET MsgText='Distribution Order {0} has been created.',Updated=TO_TIMESTAMP('2025-06-12 07:51:02.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545559
;

-- 2025-06-12T07:51:02.415Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: EVENT_DD_Order_Created
-- 2025-06-12T07:51:05.149Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-12 07:51:05.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545559
;

-- Value: EVENT_PP_Order_Created
-- 2025-06-12T07:51:11.157Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-12 07:51:11.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545558
;

-- Value: EVENT_PP_Order_Generated
-- 2025-06-12T08:22:25.172Z
UPDATE AD_Message SET Value='EVENT_PP_Order_Generated',Updated=TO_TIMESTAMP('2025-06-12 08:22:25.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545558
;

-- Value: EVENT_DD_Order_Generated
-- 2025-06-12T08:22:52.783Z
UPDATE AD_Message SET Value='EVENT_DD_Order_Generated',Updated=TO_TIMESTAMP('2025-06-12 08:22:52.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545559
;

-- 2025-06-12T08:26:36.342Z
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_NotificationGroup_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Name,Updated,UpdatedBy) VALUES (0,540023,0,TO_TIMESTAMP('2025-06-12 08:26:36.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','de.metas.manufacturing.UserNotifications','Y','Manufacturing Order',TO_TIMESTAMP('2025-06-12 08:26:36.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-12T08:29:03.393Z
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_NotificationGroup_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Name,Updated,UpdatedBy) VALUES (0,540024,0,TO_TIMESTAMP('2025-06-12 08:29:03.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','de.metas.distribution.UserNotifications','Y','Distribution Order',TO_TIMESTAMP('2025-06-12 08:29:03.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

