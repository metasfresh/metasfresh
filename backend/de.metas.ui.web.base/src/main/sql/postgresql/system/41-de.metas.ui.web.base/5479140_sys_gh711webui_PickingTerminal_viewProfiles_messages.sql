-- 2017-12-03T22:40:40.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=544587
;

-- 2017-12-03T22:40:40.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=544587
;

-- 2017-12-03T22:40:43.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=544588
;

-- 2017-12-03T22:40:43.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=544588
;

-- 2017-12-03T22:46:32.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,AD_Message_ID,MsgText,AD_Org_ID,EntityType,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.view.pickingTerminal.profile.groupByOrder.caption',544589,'Group by order',0,'de.metas.ui.web',100,TO_TIMESTAMP('2017-12-03 22:46:32','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-03 22:46:32','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-03T22:46:32.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544589 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-12-03T22:48:02.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,AD_Message_ID,MsgText,AD_Org_ID,EntityType,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.view.pickingTerminal.profile.groupByWarehouseAndProduct.caption',544590,'Group by Product',0,'de.metas.ui.web',100,TO_TIMESTAMP('2017-12-03 22:48:02','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-03 22:48:02','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-03T22:48:02.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544590 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

