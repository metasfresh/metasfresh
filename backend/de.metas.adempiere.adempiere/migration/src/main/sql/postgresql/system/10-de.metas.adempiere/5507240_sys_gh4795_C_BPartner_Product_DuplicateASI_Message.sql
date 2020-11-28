-- 2018-11-27T17:32:16.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544813,0,TO_TIMESTAMP('2018-11-27 17:32:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Es gibt schon einen Datensatz mit den selben Merkmalswerten.','E',TO_TIMESTAMP('2018-11-27 17:32:16','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_Product_Duplicate_ASI')
;

-- 2018-11-27T17:32:16.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544813 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-11-27T17:32:34.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-27 17:32:34','YYYY-MM-DD HH24:MI:SS'),MsgText='A Customer Product with the same attribute values already exists.' WHERE AD_Message_ID=544813 AND AD_Language='en_US'
;

