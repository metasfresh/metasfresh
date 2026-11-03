-- 2019-05-09T18:26:26.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544917,0,TO_TIMESTAMP('2019-05-09 18:26:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','Es wurde anhand der Prüfziffer festgestellt, dass die BtM Nummer {0} nicht gültig ist.','E',TO_TIMESTAMP('2019-05-09 18:26:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.model.interceptor.C_BPartner.Invalid_BTM')
;

-- 2019-05-09T18:26:26.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544917 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-05-09T18:26:55.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='According to the checksum verification, the BTM {0}  is not valid.',Updated=TO_TIMESTAMP('2019-05-09 18:26:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544917
;
