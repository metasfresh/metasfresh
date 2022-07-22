-- 2022-03-11T09:55:05.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545110,0,TO_TIMESTAMP('2022-03-11 10:55:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Maßeinheit kann nicht geändert werden, wenn bereits eine Konvertierung vorhanden ist','E',TO_TIMESTAMP('2022-03-11 10:55:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order.model.interceptor.M_Product.Product_UOM_Conversion_Already_Linked')
;

-- 2022-03-11T09:55:05.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545110 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-03-11T09:55:29.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The UOM cannot be changed if a conversion already exists',Updated=TO_TIMESTAMP('2022-03-11 10:55:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545110
;

