-- Value: de.metas.ui.web.window.exceptions.DocumentFieldReadonlyException.Msg
-- 2024-03-15T17:03:08.991Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545382,0,TO_TIMESTAMP('2024-03-15 17:03:08.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Die Änderung von {0} zu {1} ist nicht zulässig, da das Feld schreibgeschützt ist.','E',TO_TIMESTAMP('2024-03-15 17:03:08.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.ui.web.window.exceptions.DocumentFieldReadonlyException.Msg')
;

-- 2024-03-15T17:03:08.995Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545382 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.ui.web.window.exceptions.DocumentFieldReadonlyException.Msg
-- 2024-03-15T17:03:48.838Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Changing {0} to {1} is not allowed because the field is readonly.',Updated=TO_TIMESTAMP('2024-03-15 17:03:48.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545382
;

-- Value: de.metas.ui.web.window.exceptions.DocumentFieldReadonlyException.Msg
-- 2024-03-15T17:03:48.838Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-15 17:03:48.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545382
;

-- Value: de.metas.ui.web.window.exceptions.DocumentFieldReadonlyException.Msg
-- 2024-03-15T17:03:48.838Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-15 17:03:48.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545382
;

