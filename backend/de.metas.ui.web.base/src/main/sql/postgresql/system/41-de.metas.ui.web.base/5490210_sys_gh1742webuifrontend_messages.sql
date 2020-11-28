-- 2018-04-10T13:57:38.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.window.table.zoomInto','de.metas.ui.web',544672,'Zoom Into',0,100,TO_TIMESTAMP('2018-04-10 13:57:37','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-04-10 13:57:37','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-04-10T13:57:38.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544672 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-04-10T13:58:05.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.window.table.editField','de.metas.ui.web',544673,'Edit field value',0,100,TO_TIMESTAMP('2018-04-10 13:58:05','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-04-10 13:58:05','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-04-10T13:58:05.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544673 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-04-10T13:58:30.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.window.table.advancedEdit','de.metas.ui.web',544674,'Advanced edit',0,100,TO_TIMESTAMP('2018-04-10 13:58:30','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-04-10 13:58:30','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-04-10T13:58:30.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544674 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-04-10T13:59:00.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.window.table.openInNewTab','de.metas.ui.web',544675,'Open in new tab',0,100,TO_TIMESTAMP('2018-04-10 13:59:00','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-04-10 13:59:00','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-04-10T13:59:00.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544675 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

