-- Run mode: SWING_CLIENT

-- Value: de.metas.handlingunits.HUPI.NoCurrentVersionFound
-- 2025-10-09T10:56:28.787Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545597,0,TO_TIMESTAMP('2025-10-09 10:56:28.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','PI_NO_CURRENT_VERSION','Y','Keine aktuelle Version der Packvorschrift für {0} gefunden','E',TO_TIMESTAMP('2025-10-09 10:56:28.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.HUPI.NoCurrentVersionFound')
;

-- 2025-10-09T10:56:28.793Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545597 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.HUPI.NoCurrentVersionFound
-- 2025-10-09T10:56:40.417Z
UPDATE AD_Message_Trl SET MsgText='No current packaging instruction version found for {0}',Updated=TO_TIMESTAMP('2025-10-09 10:56:40.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545597
;

-- 2025-10-09T10:56:40.418Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;
