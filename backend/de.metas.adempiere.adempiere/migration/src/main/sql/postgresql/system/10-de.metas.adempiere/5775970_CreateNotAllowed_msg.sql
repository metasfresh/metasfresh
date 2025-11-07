-- Run mode: SWING_CLIENT

-- Value: de.metas.ui.web.window.model.DocumentCollection.CreateNotAllowed
-- 2025-11-07T13:54:28.732Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545608,0,TO_TIMESTAMP('2025-11-07 13:54:28.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Sie haben keine Berechtigung, neue Dokumente anzulegen.','E',TO_TIMESTAMP('2025-11-07 13:54:28.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.ui.web.window.model.DocumentCollection.CreateNotAllowed')
;

-- 2025-11-07T13:54:28.753Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545608 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.ui.web.window.model.DocumentCollection.CreateNotAllowed
-- 2025-11-07T13:54:32.623Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-07 13:54:32.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545608
;

-- Value: de.metas.ui.web.window.model.DocumentCollection.CreateNotAllowed
-- 2025-11-07T13:54:50.420Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='You do not have permission to create new documents.',Updated=TO_TIMESTAMP('2025-11-07 13:54:50.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545608
;

-- 2025-11-07T13:54:50.421Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.ui.web.window.model.DocumentCollection.CreateNotAllowed
-- 2025-11-07T13:54:53.064Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-07 13:54:53.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545608
;

