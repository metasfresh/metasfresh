
-- 2018-06-07T16:13:09.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544746,0,TO_TIMESTAMP('2018-06-07 16:13:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Attribute berücksichtigen','I',TO_TIMESTAMP('2018-06-07 16:13:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.picking.HUsToPickViewFilters.ConsiderAttributes')
;

-- 2018-06-07T16:13:09.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544746 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


-- 2018-06-07T16:13:49.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-07 16:13:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Consider Attributes' WHERE AD_Message_ID=544746 AND AD_Language='en_US'
;

