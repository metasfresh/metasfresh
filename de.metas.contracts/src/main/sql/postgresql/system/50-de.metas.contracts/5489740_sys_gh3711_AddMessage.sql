-- 2018-03-30T14:16:17.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544667,0,TO_TIMESTAMP('2018-03-30 14:16:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Infinite loop detected! The condition {} was already seen!','I',TO_TIMESTAMP('2018-03-30 14:16:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.impl.FlatrateBL.extendContract.seenFlatrateConditionIds')
;

-- 2018-03-30T14:16:17.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544667 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-03-30T14:31:08.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Infinite loop detected! Are you tryng to extend a contract using condition {0}, but these conditions {1} already seen.',Updated=TO_TIMESTAMP('2018-03-30 14:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544667
;

-- 2018-03-30T14:52:18.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-30 14:52:18','YYYY-MM-DD HH24:MI:SS'),MsgText='Infinite loop detected! Are you tryng to extend a contract using condition {0}, but these conditions {1} already seen.' WHERE AD_Message_ID=544667 AND AD_Language='de_CH'
;

-- 2018-03-30T14:52:20.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-30 14:52:20','YYYY-MM-DD HH24:MI:SS'),MsgText='Infinite loop detected! Are you tryng to extend a contract using condition {0}, but these conditions {1} already seen.' WHERE AD_Message_ID=544667 AND AD_Language='nl_NL'
;

-- 2018-03-30T14:52:23.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-30 14:52:23','YYYY-MM-DD HH24:MI:SS'),MsgText='Infinite loop detected! Are you tryng to extend a contract using condition {0}, but these conditions {1} already seen.' WHERE AD_Message_ID=544667 AND AD_Language='en_US'
;

-- 2018-04-02T11:50:34.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.contracts.impl.FlatrateBL.extendContract.InfinitLoopError',Updated=TO_TIMESTAMP('2018-04-02 11:50:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544667
;


