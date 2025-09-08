-- Value: C_OLCandEnqueueForSalesOrderCreation.NoValidRecordSelected
-- 2024-01-31T17:19:17.966Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545370,0,TO_TIMESTAMP('2024-01-31 17:19:17.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Es wurde kein gültiger Datensatz ausgewählt.','E',TO_TIMESTAMP('2024-01-31 17:19:17.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_OLCandEnqueueForSalesOrderCreation.NoValidRecordSelected')
;

-- 2024-01-31T17:19:17.968Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545370 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_OLCandEnqueueForSalesOrderCreation.NoValidRecordSelected
-- 2024-01-31T17:19:48.741Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No valid record was selected.',Updated=TO_TIMESTAMP('2024-01-31 17:19:48.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545370
;

