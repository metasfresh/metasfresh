-- 2018-05-14T11:40:26.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'de.metas.purchasecandidates.PurchaseCandidatesDueNotification','de.metas.purchasecandidate',544721,'{0} purchase candidate(s) for vendor {1} are almost due.',0,100,TO_TIMESTAMP('2018-05-14 11:40:26','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-05-14 11:40:26','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-14T11:40:26.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544721 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

