-- 2017-12-06T02:32:34.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=544595
;

-- 2017-12-06T02:32:34.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=544595
;

-- 2017-12-06T02:32:58.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,AD_Message_ID,MsgText,AD_Org_ID,EntityType,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.view.husToPick.filters.locatorBarcodeFilter',544596,'Scan Locator',0,'de.metas.picking',100,TO_TIMESTAMP('2017-12-06 02:32:58','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-06 02:32:58','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-06T02:32:58.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544596 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

