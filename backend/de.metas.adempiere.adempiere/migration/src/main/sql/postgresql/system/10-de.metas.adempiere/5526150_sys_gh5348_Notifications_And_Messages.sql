-- 2019-06-26T16:28:35.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544930,0,TO_TIMESTAMP('2019-06-26 16:28:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bitte trage zuerst bei Produkt {0} eine Zolltarifnummer ein.','I',TO_TIMESTAMP('2019-06-26 16:28:35','YYYY-MM-DD HH24:MI:SS'),100,'M_Product_No_CustomsTariff')
;

-- 2019-06-26T16:28:35.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544930 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-06-26T16:29:00.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Please enter a customs tariff number for product {0} first.',Updated=TO_TIMESTAMP('2019-06-26 16:29:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544930
;

-- 2019-06-26T17:58:23.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_NotificationGroup_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Name,Updated,UpdatedBy) VALUES (0,540017,0,TO_TIMESTAMP('2019-06-26 17:58:22','YYYY-MM-DD HH24:MI:SS'),100,'D','de.metas.product.UserNotifications','Y','Product',TO_TIMESTAMP('2019-06-26 17:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-26T18:28:43.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544931,0,TO_TIMESTAMP('2019-06-26 18:28:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Zollrechnung kann nicht fertiggestellt werden. Bitte fügen Sie die Zolltarifnummern für alle Produkte hinzu.','E',TO_TIMESTAMP('2019-06-26 18:28:42','YYYY-MM-DD HH24:MI:SS'),100,'C_Customs_Invoice_Tariff_NotSet')
;

-- 2019-06-26T18:28:43.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544931 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-06-27T10:32:27.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Customs Invoice cannot be completed. Please, set the Customs Tariff to all the products, first.',Updated=TO_TIMESTAMP('2019-06-27 10:32:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544931
;

