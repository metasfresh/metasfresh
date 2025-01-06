-- 2024-10-07T12:44:33.267Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545445,0,TO_TIMESTAMP('2024-10-07 15:44:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','Die importierte ESR-Referenznummer {0} hat weniger als 26 Zeichen','E',TO_TIMESTAMP('2024-10-07 15:44:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr.UnfitEsrNo')
;

-- 2024-10-07T12:44:33.275Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545445 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2024-10-07T12:45:13.658Z
UPDATE AD_Message_Trl SET MsgText='The imported ESR number {0} has less than 26 characters',Updated=TO_TIMESTAMP('2024-10-07 15:45:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545445
;

