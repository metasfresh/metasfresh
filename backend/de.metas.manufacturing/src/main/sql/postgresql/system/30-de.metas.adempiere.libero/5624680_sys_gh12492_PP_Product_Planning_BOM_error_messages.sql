
-- 2022-02-07T16:44:13.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545096,0,TO_TIMESTAMP('2022-02-07 18:44:13','YYYY-MM-DD HH24:MI:SS'),100,'EE04','Y','PP_Product_Planning and PP_Product_BOM attributes doesn''t match.','E',TO_TIMESTAMP('2022-02-07 18:44:13','YYYY-MM-DD HH24:MI:SS'),100,'PP_Product_Planning_BOM_Attribute_Error')
;

-- 2022-02-07T16:44:13.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545096 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-02-09T11:38:23.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Die Attribute PP_Product_Planning und PP_Product_BOM stimmen nicht überein',Updated=TO_TIMESTAMP('2022-02-09 13:38:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545096
;

-- 2022-02-09T11:38:26.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Die Attribute PP_Product_Planning und PP_Product_BOM stimmen nicht überein',Updated=TO_TIMESTAMP('2022-02-09 13:38:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545096
;

-- 2022-02-09T11:38:31.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Die Attribute PP_Product_Planning und PP_Product_BOM stimmen nicht überein',Updated=TO_TIMESTAMP('2022-02-09 13:38:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545096
;

-- 2022-02-09T11:44:55.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Die Attribute des Produktplan-Datensatzes und der Stückliste stimmen nicht überein',Updated=TO_TIMESTAMP('2022-02-09 13:44:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545096
;

