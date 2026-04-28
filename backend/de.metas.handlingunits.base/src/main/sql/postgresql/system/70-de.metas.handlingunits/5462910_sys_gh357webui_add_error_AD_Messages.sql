-- 2017-05-18T11:04:12.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=544322
;

-- 2017-05-18T11:04:12.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=544322
;

-- 2017-05-18T11:04:26.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544324,0,TO_TIMESTAMP('2017-05-18 11:04:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Issuing aggregated TUs is not allowed. Please use the quick action which partially issues TUs.','E',TO_TIMESTAMP('2017-05-18 11:04:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingAggregatedTUsNotAllowed')
;

-- 2017-05-18T11:04:26.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544324 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-05-18T11:07:09.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544325,0,TO_TIMESTAMP('2017-05-18 11:07:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Issuing HUs with multiple Products is not allowed.','E',TO_TIMESTAMP('2017-05-18 11:07:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingHUsWithMultipleProductsNotAllowed')
;

-- 2017-05-18T11:07:09.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544325 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-05-18T11:08:02.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544326,0,TO_TIMESTAMP('2017-05-18 11:08:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Issuing VHUs is not allowed.','E',TO_TIMESTAMP('2017-05-18 11:08:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingVHUsNotAllowed')
;

-- 2017-05-18T11:08:02.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544326 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

