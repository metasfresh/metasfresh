-- 2017-12-20T15:22:55.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,AD_Message_ID,MsgText,AD_Org_ID,EntityType,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'Event_ShipmentError',544614,'Cannot generate shipment for ''{0}'' because: {1}.',0,'de.metas.inout',100,TO_TIMESTAMP('2017-12-20 15:22:54','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-20 15:22:54','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-20T15:22:55.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544614 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

