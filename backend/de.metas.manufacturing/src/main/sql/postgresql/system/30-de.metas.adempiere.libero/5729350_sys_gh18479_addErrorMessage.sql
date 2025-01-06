-- Value: de.metas.manufacturing.NO_QTY_TO_ISSUE
-- 2024-07-16T10:45:06.769Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545434,0,TO_TIMESTAMP('2024-07-16 13:45:05','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Es ist keine Menge zu vergeben','E',TO_TIMESTAMP('2024-07-16 13:45:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.manufacturing.NO_QTY_TO_ISSUE')
;

-- 2024-07-16T10:45:06.777Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545434 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.manufacturing.NO_QTY_TO_ISSUE
-- 2024-07-16T10:45:17.041Z
UPDATE AD_Message_Trl SET MsgText='There is not qty to be issued',Updated=TO_TIMESTAMP('2024-07-16 13:45:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545434
;

