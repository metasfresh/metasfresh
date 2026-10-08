-- 01.11.2015 12:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543689,0,TO_TIMESTAMP('2015-11-01 12:05:31','YYYY-MM-DD HH24:MI:SS'),0,'de.metas.handlingunits','Y','de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel.MarkAsQualityInspection','I',TO_TIMESTAMP('2015-11-01 12:05:31','YYYY-MM-DD HH24:MI:SS'),0,'de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel.MarkAsQualityInspection')
;

-- 01.11.2015 12:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543689 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 01.11.2015 12:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543690,0,TO_TIMESTAMP('2015-11-01 12:06:04','YYYY-MM-DD HH24:MI:SS'),0,'de.metas.handlingunits','Y','de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel.UnMarkAsQualityInspection','I',TO_TIMESTAMP('2015-11-01 12:06:04','YYYY-MM-DD HH24:MI:SS'),0,'de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel.UnMarkAsQualityInspection')
;

-- 01.11.2015 12:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543690 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 01.11.2015 12:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Waschprobe',Updated=TO_TIMESTAMP('2015-11-01 12:29:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543689
;

-- 01.11.2015 12:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543689
;

-- 01.11.2015 12:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='keine Waschprobe',Updated=TO_TIMESTAMP('2015-11-01 12:29:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543690
;

-- 01.11.2015 12:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543690
;

