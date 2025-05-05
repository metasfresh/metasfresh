-- Value: C_Campaign_Price_MandatoryFields
-- 2024-06-26T10:28:35.264Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545428,0,TO_TIMESTAMP('2024-06-26 11:28:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mindestens eines der Felder Geschäftspartner, Geschäftspartnergruppe oder Preissystem muss gesetzt sein','E',TO_TIMESTAMP('2024-06-26 11:28:35','YYYY-MM-DD HH24:MI:SS'),100,'C_Campaign_Price_MandatoryFields')
;

-- 2024-06-26T10:28:35.266Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545428 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_Campaign_Price_MandatoryFields
-- 2024-06-26T10:29:01.055Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='At least one of the fields Business Partner, Business Partner Group or Pricing System needs to be set',Updated=TO_TIMESTAMP('2024-06-26 11:29:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545428
;

