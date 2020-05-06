-- 2017-07-26T14:35:14.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544464,0,TO_TIMESTAMP('2017-07-26 14:35:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Kommissionierung','I',TO_TIMESTAMP('2017-07-26 14:35:14','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_Caption')
;

-- 2017-07-26T14:35:14.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544464 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-26T14:35:22.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 14:35:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Picking' WHERE AD_Message_ID=544464 AND AD_Language='en_US'
;

-- 2017-07-26T18:29:06.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544465,0,TO_TIMESTAMP('2017-07-26 18:29:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Es müssen weniger als {0} Datensätze selektiert sein.','I',TO_TIMESTAMP('2017-07-26 18:29:06','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_Too_Many_Packageables')
;

-- 2017-07-26T18:29:06.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544465 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-26T18:29:33.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 18:29:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='There shall be less than {0} selected items' WHERE AD_Message_ID=544465 AND AD_Language='en_US'
;

-- 2017-07-26T18:31:03.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544466,0,TO_TIMESTAMP('2017-07-26 18:31:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Alle selektierten Datensätze müssen die selbe Lieferaddresse haben.','I',TO_TIMESTAMP('2017-07-26 18:31:03','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_Diverging_Locations')
;

-- 2017-07-26T18:31:03.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544466 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-26T18:31:30.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 18:31:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='All selected items shall have the same delivery address' WHERE AD_Message_ID=544466 AND AD_Language='en_US'
;

