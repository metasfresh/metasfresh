-- 2018-02-21T14:10:05.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544647,0,TO_TIMESTAMP('2018-02-21 14:10:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Overdelivery is not allowed.','E',TO_TIMESTAMP('2018-02-21 14:10:05','YYYY-MM-DD HH24:MI:SS'),100,'M_Picking_Config_OverdeliveryNotAllowed')
;

-- 2018-02-21T14:10:05.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544647 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-02-21T14:10:30.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-21 14:10:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Eine Überlieferung ist nicht erlaubt.' WHERE AD_Message_ID=544647 AND AD_Language='de_CH'
;

