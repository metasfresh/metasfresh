
-- 21.01.2016 07:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543749,0,TO_TIMESTAMP('2016-01-21 07:31:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','Y','Fehler beim verarbeiten eines Arbeitspakets','E',TO_TIMESTAMP('2016-01-21 07:31:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async.WorkpackageProcessorTask.ProcessingErrorNotificationTitle')
;

-- 21.01.2016 07:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543749 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 21.01.2016 07:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543750,0,TO_TIMESTAMP('2016-01-21 07:40:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','Y','Beim Verarbeiten des Arbeitspakets C_Queue_WorkPackage={0} 
ist folgender Fehler aufgetreten: {1}','I',TO_TIMESTAMP('2016-01-21 07:40:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async.WorkpackageProcessorTask.ProcessingErrorNotificationText')
;

-- 21.01.2016 07:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543750 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 21.01.2016 07:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Beim Verarbeiten des Arbeitspakets C_Queue_WorkPackage={0} 
ist folgender Fehler aufgetreten: 
{1}',Updated=TO_TIMESTAMP('2016-01-21 07:40:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543750
;

-- 21.01.2016 07:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543750
;
