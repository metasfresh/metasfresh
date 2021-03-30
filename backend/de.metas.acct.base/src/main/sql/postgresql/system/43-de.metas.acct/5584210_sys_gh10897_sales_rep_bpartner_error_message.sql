-- 2021-03-29T05:35:01.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545029,0,TO_TIMESTAMP('2021-03-29 08:35:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geschäftspartner und Vertriebspartner dürfen nicht übereinstimmen.','E',TO_TIMESTAMP('2021-03-29 08:35:01','YYYY-MM-DD HH24:MI:SS'),100,'SALES_REP_EQUALS_BPARTNER')
;

-- 2021-03-29T05:35:01.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545029 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-03-29T05:35:35.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Geschäftspartner und Vertriebspartner dürfen nicht übereinstimmen.',Updated=TO_TIMESTAMP('2021-03-29 08:35:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545029
;

-- 2021-03-29T05:35:37.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Geschäftspartner und Vertriebspartner dürfen nicht übereinstimmen.',Updated=TO_TIMESTAMP('2021-03-29 08:35:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545029
;

-- 2021-03-29T05:35:39.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Geschäftspartner und Vertriebspartner dürfen nicht übereinstimmen.',Updated=TO_TIMESTAMP('2021-03-29 08:35:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545029
;

-- 2021-03-29T05:35:44.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Business partner and sales partner must not match.',Updated=TO_TIMESTAMP('2021-03-29 08:35:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545029
;

