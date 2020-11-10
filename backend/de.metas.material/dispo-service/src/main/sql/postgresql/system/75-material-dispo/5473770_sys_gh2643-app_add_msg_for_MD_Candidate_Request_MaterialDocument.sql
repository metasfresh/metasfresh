
--
-- AD_Message for process MD_Candidate_Request_MaterialDocument
--
-- 2017-10-05T18:36:27.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544534,0,TO_TIMESTAMP('2017-10-05 18:36:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Es müssen Produtions- oder Verteilungsdatensätze ausgewählt werden','I',TO_TIMESTAMP('2017-10-05 18:36:27','YYYY-MM-DD HH24:MI:SS'),100,'MD_Candidate_Request_MaterialDocument_Missing_Production_Or_Distributrion_Records')
;

-- 2017-10-05T18:36:27.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544534 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

