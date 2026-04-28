-- Run mode: SWING_CLIENT

-- Value: ERR_INVALID_EXTERNAL_SYSTEM_VALUE
-- 2025-09-25T15:50:45.839Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545585,0,TO_TIMESTAMP('2025-09-25 15:50:45.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','INVALID_EXTERNAL_SYSTEM_VALUE','Y','Ungültiger Wert für externes System. Der Wert darf nur Buchstaben, Ziffern oder Unterstriche enthalten','E',TO_TIMESTAMP('2025-09-25 15:50:45.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_INVALID_EXTERNAL_SYSTEM_VALUE')
;

-- 2025-09-25T15:50:45.846Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545585 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_INVALID_EXTERNAL_SYSTEM_VALUE
-- 2025-09-25T15:51:48.252Z
UPDATE AD_Message_Trl SET MsgText='External System value is invalid. It must consist only of letters, digits, or underscores',Updated=TO_TIMESTAMP('2025-09-25 15:51:48.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545585
;

-- 2025-09-25T15:51:48.253Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;
