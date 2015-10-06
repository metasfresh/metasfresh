-- 01.09.2015 09:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_ContextMenu (AD_Client_ID,AD_Field_ContextMenu_ID,AD_Org_ID,Classname,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,540019,0,'org.compiere.grid.ed.CopyPasteMenuAction',TO_TIMESTAMP('2015-09-01 09:27:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',0,TO_TIMESTAMP('2015-09-01 09:27:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.09.2015 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543583,0,TO_TIMESTAMP('2015-09-01 11:27:49','YYYY-MM-DD HH24:MI:SS'),0,'U','Y','CopyPasteAction.Cut','I',TO_TIMESTAMP('2015-09-01 11:27:49','YYYY-MM-DD HH24:MI:SS'),0,'CopyPasteAction.Cut')
;

-- 01.09.2015 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543583 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 01.09.2015 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543584,0,TO_TIMESTAMP('2015-09-01 11:27:49','YYYY-MM-DD HH24:MI:SS'),0,'U','Y','CopyPasteAction.Copy','I',TO_TIMESTAMP('2015-09-01 11:27:49','YYYY-MM-DD HH24:MI:SS'),0,'CopyPasteAction.Copy')
;

-- 01.09.2015 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543584 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 01.09.2015 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543585,0,TO_TIMESTAMP('2015-09-01 11:27:49','YYYY-MM-DD HH24:MI:SS'),0,'U','Y','CopyPasteAction.Paste','I',TO_TIMESTAMP('2015-09-01 11:27:49','YYYY-MM-DD HH24:MI:SS'),0,'CopyPasteAction.Paste')
;

-- 01.09.2015 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543585 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 01.09.2015 14:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543589,0,TO_TIMESTAMP('2015-09-01 14:08:58','YYYY-MM-DD HH24:MI:SS'),0,'U','Y','CopyPasteAction.SelectAll','I',TO_TIMESTAMP('2015-09-01 14:08:58','YYYY-MM-DD HH24:MI:SS'),0,'CopyPasteAction.SelectAll')
;

-- 01.09.2015 14:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543589 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 01.09.2015 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='D', MsgText='Kopieren', MsgType='M',Updated=TO_TIMESTAMP('2015-09-01 15:58:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543584
;

-- 01.09.2015 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543584
;

-- 01.09.2015 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='D', MsgText='Ausschneiden', MsgType='M',Updated=TO_TIMESTAMP('2015-09-01 15:58:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543583
;

-- 01.09.2015 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543583
;

-- 01.09.2015 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='D', MsgText='Einfügen', MsgType='M',Updated=TO_TIMESTAMP('2015-09-01 15:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543585
;

-- 01.09.2015 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543585
;

-- 01.09.2015 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='D', MsgText='Alles auswählen', MsgType='M',Updated=TO_TIMESTAMP('2015-09-01 15:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543589
;

-- 01.09.2015 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543589
;

-- 01.09.2015 16:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_ContextMenu SET Classname='org.compiere.grid.ed.CopyPasteContextMenuAction',Updated=TO_TIMESTAMP('2015-09-01 16:04:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ContextMenu_ID=540019
;

