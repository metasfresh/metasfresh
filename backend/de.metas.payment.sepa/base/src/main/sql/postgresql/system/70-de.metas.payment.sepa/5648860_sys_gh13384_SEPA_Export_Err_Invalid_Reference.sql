-- 2022-08-02T07:35:42.482Z
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545142,0,TO_TIMESTAMP('2022-08-02 09:35:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','SEPA Export fehlgeschlagen. Eine Zeile enthält eine ungültige Referenz. {0}','E',TO_TIMESTAMP('2022-08-02 09:35:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_InvalidReference')
;

-- 2022-08-02T07:35:42.584Z
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545142 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-08-02T08:14:18.343Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='SEPA export failed. A line contains an invalid reference. {0}',Updated=TO_TIMESTAMP('2022-08-02 10:14:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545142
;

