
-- 2021-06-24T15:38:04.462Z
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545038,0,TO_TIMESTAMP('2021-06-24 17:38:04','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','The country display sequence is wrong. Tokens BP/CON and BP_Name/BP_GR cannot be together in the same sequence.','I',TO_TIMESTAMP('2021-06-24 17:38:04','YYYY-MM-DD HH24:MI:SS'),100,'MSG_AddressBuilder_WrongDisplaySequence')
;

-- 2021-06-24T15:38:04.464Z
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545038 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-06-24T16:08:37.585Z
-- URL zum Konzept
UPDATE AD_Message SET EntityType='D', MsgText='BP/CON and BP_Name/BP_GR cannot be together in the same display sequence.',Updated=TO_TIMESTAMP('2021-06-24 18:08:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545038
;

-- 2021-06-24T16:09:00.076Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='BP/CON und BP_Name/BP_GR können nicht zusammen im gleichen Adress-Druckformat sein.',Updated=TO_TIMESTAMP('2021-06-24 18:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545038
;

-- 2021-06-24T16:09:24.370Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='BP/CON und BP_Name/BP_GR können nicht zusammen im gleichen Adress-Druckformat sein.',Updated=TO_TIMESTAMP('2021-06-24 18:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545038
;

-- 2021-06-24T16:09:39.596Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='BP/CON and BP_Name/BP_GR cannot be together in the same display sequence.',Updated=TO_TIMESTAMP('2021-06-24 18:09:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545038
;

