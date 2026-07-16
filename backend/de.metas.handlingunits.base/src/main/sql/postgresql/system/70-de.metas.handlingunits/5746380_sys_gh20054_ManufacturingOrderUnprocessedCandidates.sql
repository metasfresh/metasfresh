-- Run mode: SWING_CLIENT

-- Value: ManufacturingOrderUnprocessedCandidates
-- 2025-02-12T12:20:45.065Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545497,0,TO_TIMESTAMP('2025-02-12 12:20:44.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Bitte führen Sie die Aktion "Verarbeiten" im "Produzieren"-Dialog aus, bevor Sie den Produktionsauftrag abschließen.','E',TO_TIMESTAMP('2025-02-12 12:20:44.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ManufacturingOrderUnprocessedCandidates')
;

-- 2025-02-12T12:20:45.069Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545497 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Run mode: SWING_CLIENT

-- Value: ManufacturingOrderUnprocessedCandidates
-- 2025-02-13T13:18:57.291Z
UPDATE AD_Message_Trl SET MsgText='Please execute the "Process" action in the "Issue/Receipt"-Dialog before closing this manufacturing order.',Updated=TO_TIMESTAMP('2025-02-13 13:18:57.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545497
;

-- 2025-02-13T13:18:57.292Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ManufacturingOrderUnprocessedCandidates
-- 2025-02-13T13:19:06.748Z
UPDATE AD_Message_Trl SET MsgText='Bitte führen Sie die Aktion "Verarbeiten" im "Produzieren"-Dialog aus, bevor Sie den Produktionsauftrag abschließen.',Updated=TO_TIMESTAMP('2025-02-13 13:19:06.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545497
;

-- 2025-02-13T13:19:06.749Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ManufacturingOrderUnprocessedCandidates
-- 2025-02-13T13:19:12.536Z
UPDATE AD_Message_Trl SET MsgText='Bitte führen Sie die Aktion "Verarbeiten" im "Produzieren"-Dialog aus, bevor Sie den Produktionsauftrag abschliessen.',Updated=TO_TIMESTAMP('2025-02-13 13:19:12.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545497
;

-- 2025-02-13T13:19:12.536Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ManufacturingOrderUnprocessedCandidates
-- 2025-02-13T13:19:16.836Z
UPDATE AD_Message_Trl SET MsgText='Bitte führen Sie die Aktion "Verarbeiten" im "Produzieren"-Dialog aus, bevor Sie den Produktionsauftrag abschließen.',Updated=TO_TIMESTAMP('2025-02-13 13:19:16.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545497
;

-- 2025-02-13T13:19:16.837Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;
