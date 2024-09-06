-- Value: NotFound_AD_Printer_Config
-- 2024-06-19T18:18:21.107Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545424,0,TO_TIMESTAMP('2024-06-19 21:18:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','No Printer Configuration defined','E',TO_TIMESTAMP('2024-06-19 21:18:20','YYYY-MM-DD HH24:MI:SS'),100,'NotFound_AD_Printer_Config')
;

-- 2024-06-19T18:18:21.113Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545424 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

