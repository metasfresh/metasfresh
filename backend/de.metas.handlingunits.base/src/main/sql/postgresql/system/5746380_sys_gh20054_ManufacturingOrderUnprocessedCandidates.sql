-- Run mode: SWING_CLIENT

-- Value: ManufacturingOrderUnprocessedCandidates
-- 2025-02-12T12:20:45.065Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545497,0,TO_TIMESTAMP('2025-02-12 12:20:44.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Der Produktionsauftrag hat unbearbeitete Ausgabe- oder Empfangskandidaten. Bitte bearbeiten Sie diese, bevor Sie den Auftrag schließen!','E',TO_TIMESTAMP('2025-02-12 12:20:44.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ManufacturingOrderUnprocessedCandidates')
;

-- 2025-02-12T12:20:45.069Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545497 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ManufacturingOrderUnprocessedCandidates
-- 2025-02-12T12:20:58.361Z
UPDATE AD_Message_Trl SET MsgText='The Manufacturing Order has unprocessed Issue or Receipt candidates. Please process them before closing the order !',Updated=TO_TIMESTAMP('2025-02-12 12:20:58.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545497
;

-- 2025-02-12T12:20:58.362Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;
