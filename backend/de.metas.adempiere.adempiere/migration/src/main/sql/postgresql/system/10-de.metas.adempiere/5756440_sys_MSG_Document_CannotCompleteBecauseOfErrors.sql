-- Value: de.metas.document.engine.impl.DocumentEngine.CannotCompleteBecauseOfErrors
-- 2025-06-02T11:25:59.194Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545554,0,TO_TIMESTAMP('2025-06-02 11:25:59.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Cannot complete document: Errors detected.','E',TO_TIMESTAMP('2025-06-02 11:25:59.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.document.engine.impl.DocumentEngine.CannotCompleteBecauseOfErrors')
;

-- 2025-06-02T11:25:59.200Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545554 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.document.engine.impl.DocumentEngine.CannotCompleteBecauseOfErrors
-- 2025-06-02T11:26:12.797Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Dokument kann nicht fertiggestellt werden: Fehler erkannt.',Updated=TO_TIMESTAMP('2025-06-02 11:26:12.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545554
;

-- 2025-06-02T11:26:12.798Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.document.engine.impl.DocumentEngine.CannotCompleteBecauseOfErrors
-- 2025-06-02T11:26:20.393Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Dokument kann nicht fertiggestellt werden: Fehler erkannt.',Updated=TO_TIMESTAMP('2025-06-02 11:26:20.393000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545554
;

-- 2025-06-02T11:26:20.394Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;



-- Value: de.metas.document.engine.impl.DocumentEngine.CannotCompleteBecauseOfErrors
-- 2025-06-02T12:44:12.494Z
UPDATE AD_Message_Trl SET MsgText='Dokument kann nicht fertiggestellt werden: Warnhinweise mit dem Schweregrad ''Fehler'' erkannt.
',Updated=TO_TIMESTAMP('2025-06-02 12:44:12.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545554
;

-- 2025-06-02T12:44:12.498Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.document.engine.impl.DocumentEngine.CannotCompleteBecauseOfErrors
-- 2025-06-02T12:44:27.078Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Cannot complete document: Warnings with severity ''Error'' detected.',Updated=TO_TIMESTAMP('2025-06-02 12:44:27.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545554
;

-- 2025-06-02T12:44:27.083Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.document.engine.impl.DocumentEngine.CannotCompleteBecauseOfErrors
-- 2025-06-02T12:44:35.448Z
UPDATE AD_Message_Trl SET MsgText='Cannot complete document: Warnings with severity ''Error'' detected.',Updated=TO_TIMESTAMP('2025-06-02 12:44:35.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545554
;

-- 2025-06-02T12:44:35.449Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.document.engine.impl.DocumentEngine.CannotCompleteBecauseOfErrors
-- 2025-06-02T12:44:40.582Z
UPDATE AD_Message_Trl SET MsgText='Dokument kann nicht fertiggestellt werden: Warnhinweise mit dem Schweregrad ''Fehler'' erkannt.',Updated=TO_TIMESTAMP('2025-06-02 12:44:40.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545554
;

-- 2025-06-02T12:44:40.587Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;




