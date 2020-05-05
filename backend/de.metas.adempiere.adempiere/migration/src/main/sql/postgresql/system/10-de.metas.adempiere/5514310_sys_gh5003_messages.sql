-- 2019-03-01T10:04:28.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544841,0,TO_TIMESTAMP('2019-03-01 10:04:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zu den folgenden Angebotszeilen gibt es kein Gruppierungsprodukt: {0}','E',TO_TIMESTAMP('2019-03-01 10:04:28','YYYY-MM-DD HH24:MI:SS'),100,'NoQuotationGrouppingProductFoundForLines')
;

-- 2019-03-01T10:04:28.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544841 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-03-01T10:04:35.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-01 10:04:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544841
;

-- 2019-03-01T10:07:30.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No quotation groupping product found for lines: {0}',Updated=TO_TIMESTAMP('2019-03-01 10:07:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544841
;

-- 2019-03-01T10:10:50.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544842,0,TO_TIMESTAMP('2019-03-01 10:10:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zu den folgenden Angebotszeilen gibt es mehr als ein Gruppierungsprodukt: {0}','E',TO_TIMESTAMP('2019-03-01 10:10:50','YYYY-MM-DD HH24:MI:SS'),100,'MoreThanOneQuotationGrouppingProductLinesFound')
;

-- 2019-03-01T10:10:50.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544842 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-03-01T10:11:14.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='More than one quotation groupping product lines found: {0}',Updated=TO_TIMESTAMP('2019-03-01 10:11:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544842
;

-- 2019-03-01T10:11:22.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-01 10:11:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544842
;

