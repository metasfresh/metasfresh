

-- 2022-05-27T15:11:48.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545128,0,TO_TIMESTAMP('2022-05-27 18:11:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Jedem Preis mit Maßeinheit={0} muss eine Packvorschrift zugeordnet sein.','E',TO_TIMESTAMP('2022-05-27 18:11:48','YYYY-MM-DD HH24:MI:SS'),100,'MSG_ERR_M_PRODUCT_PRICE_MISSING_PACKING_ITEM')
;

-- 2022-05-27T15:11:48.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545128 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-05-27T15:12:03.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Any price with UOM={0} must also have a packing instruction assigned.',Updated=TO_TIMESTAMP('2022-05-27 18:12:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545128
;

-- 2022-05-27T15:12:32.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545129,0,TO_TIMESTAMP('2022-05-27 18:12:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Packvorschrift {0} hat eine unbestimmte Kapazität! Bitte wählen Sie einen anderen Artikel mit einer Kapazitätsangabe.','E',TO_TIMESTAMP('2022-05-27 18:12:31','YYYY-MM-DD HH24:MI:SS'),100,'MSG_ERR_M_PRODUCT_PRICE_PACKING_ITEM_INFINITE_CAPACITY')
;

-- 2022-05-27T15:12:32.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545129 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-05-27T15:12:40.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Packing item {0} has infinite capacity! Please select another one with defined capacity.',Updated=TO_TIMESTAMP('2022-05-27 18:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545129
;

-- 2022-05-27T15:13:11.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545130,0,TO_TIMESTAMP('2022-05-27 18:13:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Für die ausgewählte Maßeinheit ist keine UOM-Konvertierung verfügbar.','E',TO_TIMESTAMP('2022-05-27 18:13:11','YYYY-MM-DD HH24:MI:SS'),100,'MSG_ERR_M_PRODUCT_PRICE_NO_UOM_CONVERSION')
;

-- 2022-05-27T15:13:11.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545130 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-05-27T15:13:19.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='There is no UOM conversion available for the selected uom.',Updated=TO_TIMESTAMP('2022-05-27 18:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545130
;

