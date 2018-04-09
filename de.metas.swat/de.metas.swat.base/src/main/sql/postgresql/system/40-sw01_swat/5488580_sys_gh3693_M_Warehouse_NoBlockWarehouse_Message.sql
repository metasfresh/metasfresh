-- 2018-03-16T17:15:01.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544656,0,TO_TIMESTAMP('2018-03-16 17:15:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Es konnte kein Sperrlager gefunden werden.','E',TO_TIMESTAMP('2018-03-16 17:15:01','YYYY-MM-DD HH24:MI:SS'),100,'NoBlockWarehouse')
;

-- 2018-03-16T17:15:01.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544656 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-03-16T17:15:28.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-16 17:15:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='No Block Warehouse Found' WHERE AD_Message_ID=544656 AND AD_Language='en_US'
;

-- 2018-03-16T17:16:44.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='M_Warehouse_NoBlockWarehouse',Updated=TO_TIMESTAMP('2018-03-16 17:16:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544656
;

