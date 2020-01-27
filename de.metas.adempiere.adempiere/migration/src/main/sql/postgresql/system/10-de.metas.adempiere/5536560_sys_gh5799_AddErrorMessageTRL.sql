-- 2019-11-22T06:49:02.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,Value,AD_Org_ID,EntityType) VALUES ('I','The Price List Schema should have no Lines.',0,'Y',TO_TIMESTAMP('2019-11-22 08:49:02','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-11-22 08:49:02','YYYY-MM-DD HH24:MI:SS'),100,544945,'de.metas.pricing.process.ImportPriceListSchemaLinesFromAttachment.PriceListSchemaShouldHaveNoLines',0,'D')
;

-- 2019-11-22T06:49:02.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544945 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-11-22T06:50:11.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Das Preislistenschema sollte keine Zeilen enthalten.',Updated=TO_TIMESTAMP('2019-11-22 08:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544945
;

-- 2019-11-22T06:50:17.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das Preislistenschema sollte keine Zeilen enthalten.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-22 08:50:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544945
;

-- 2019-11-22T06:50:19.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-22 08:50:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544945
;

