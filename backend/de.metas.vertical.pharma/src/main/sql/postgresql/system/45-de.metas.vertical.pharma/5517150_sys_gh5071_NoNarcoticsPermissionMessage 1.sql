-- 2019-03-26T14:45:07.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544885,0,TO_TIMESTAMP('2019-03-26 14:45:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','The product {0} is narcotic and may not be sold to the customer {1} since it lacks narcotic delivery right.','I',TO_TIMESTAMP('2019-03-26 14:45:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.PharmaOrderLineQuickInputValidator.NoNarcoticPermissions')
;

-- 2019-03-26T14:45:07.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544885 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-03-26T14:48:44.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Produkt {0} ist ein Betäubungsmittel und darf nicht an den Kunden {1} abgegeben werden.',Updated=TO_TIMESTAMP('2019-03-26 14:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544885
;

-- 2019-03-26T14:48:53.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The product {0} is narcotic and may not be sold to the customer {1}.',Updated=TO_TIMESTAMP('2019-03-26 14:48:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544885
;

-- 2019-03-26T14:50:23.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The product {0} is a narcotic and may not be sold to the customer {1}.',Updated=TO_TIMESTAMP('2019-03-26 14:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544885
;

-- 2019-03-26T14:53:20.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-26 14:53:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544885
;

