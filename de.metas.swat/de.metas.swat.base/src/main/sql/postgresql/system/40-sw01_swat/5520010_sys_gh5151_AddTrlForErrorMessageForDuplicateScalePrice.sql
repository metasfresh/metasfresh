-- 2019-04-22T13:26:40.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544912,0,TO_TIMESTAMP('2019-04-22 13:26:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Für jede Menge kann nur ein Staffelpreis hinterlegt werden','E',TO_TIMESTAMP('2019-04-22 13:26:40','YYYY-MM-DD HH24:MI:SS'),100,'MProductScalePrice.DuplicateQty')
;

-- 2019-04-22T13:26:40.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544912 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-04-22T13:28:10.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Duplicate scale price for quantity',Updated=TO_TIMESTAMP('2019-04-22 13:28:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544912
;

-- 2019-04-22T13:29:22.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgType='I',Updated=TO_TIMESTAMP('2019-04-22 13:29:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544912
;

-- 2019-04-22T13:30:14.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgType='E',Updated=TO_TIMESTAMP('2019-04-22 13:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544912
;

