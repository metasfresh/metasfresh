

-- 2022-03-17T09:14:29.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545111,0,TO_TIMESTAMP('2022-03-17 11:14:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Sales contract {0} in line {1} cannot be used in purchase documents.','E',TO_TIMESTAMP('2022-03-17 11:14:28','YYYY-MM-DD HH24:MI:SS'),100,'SALES_CALL_ORDER_CONTRACT_TRX_NOT_MATCH')
;

-- 2022-03-17T09:14:29.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545111 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-03-17T09:14:39.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Verkaufs-Vertrag {0} in Zeile {1} kann nicht in Einkaufsbelegen verwendet werden.',Updated=TO_TIMESTAMP('2022-03-17 11:14:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545111
;

-- 2022-03-17T09:14:43.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Verkaufs-Vertrag {0} in Zeile {1} kann nicht in Einkaufsbelegen verwendet werden.',Updated=TO_TIMESTAMP('2022-03-17 11:14:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545111
;

-- 2022-03-17T09:14:47.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Verkaufs-Vertrag {0} in Zeile {1} kann nicht in Einkaufsbelegen verwendet werden.',Updated=TO_TIMESTAMP('2022-03-17 11:14:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545111
;

-- 2022-03-17T09:16:19.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545112,0,TO_TIMESTAMP('2022-03-17 11:16:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Der Einkaufs-Vertrag {0} in Zeile {1} kann nicht in Verkaufsbelegen verwendet werden.','E',TO_TIMESTAMP('2022-03-17 11:16:19','YYYY-MM-DD HH24:MI:SS'),100,'PURCHASE_CALL_ORDER_CONTRACT_TRX_NOT_MATCH')
;

-- 2022-03-17T09:16:19.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545112 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-03-17T09:16:31.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Purchase contract {0} in line {1} cannot be used in sales documents.',Updated=TO_TIMESTAMP('2022-03-17 11:16:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545112
;


-- 2022-03-17T11:34:39.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Verkaufs-Vertrag {0} in Zeile {1} kann nicht in Einkaufsbelegen verwendet werden.',Updated=TO_TIMESTAMP('2022-03-17 13:34:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545111
;

