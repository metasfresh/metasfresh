-- 2023-03-06T10:27:38.784Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545250,0,TO_TIMESTAMP('2023-03-06 12:27:38.296','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Das Projekt kann nicht rückgängig gemacht werden, solange es noch aktiv ist.','E',TO_TIMESTAMP('2023-03-06 12:27:38.296','YYYY-MM-DD HH24:MI:SS.US'),100,'project_still_active_reverse_not_possible')
;

-- 2023-03-06T10:27:38.822Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545250 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-03-06T10:28:09.403Z
UPDATE AD_Message_Trl SET MsgText='The project cannot be reversed while it is still active.',Updated=TO_TIMESTAMP('2023-03-06 12:28:09.306','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545250
;

