-- 2022-04-04T15:49:26.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545116,0,TO_TIMESTAMP('2022-04-04 18:49:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Only cleared HUs can be processed for picking!','E',TO_TIMESTAMP('2022-04-04 18:49:26','YYYY-MM-DD HH24:MI:SS'),100,'OnlyClearedHusCanBePicked')
;

-- 2022-04-04T15:49:26.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545116 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-04-04T15:50:39.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545117,0,TO_TIMESTAMP('2022-04-04 18:50:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Only cleared HUs can be finalized with their PP_Cost_Collector and destroyed','E',TO_TIMESTAMP('2022-04-04 18:50:39','YYYY-MM-DD HH24:MI:SS'),100,'OnlyClearedHUsCanBeIssued')
;

-- 2022-04-04T15:50:39.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545117 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-04-04T15:54:47.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='C', Value='C',Updated=TO_TIMESTAMP('2022-04-04 18:54:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543121
;

-- 2022-04-04T15:54:58.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='L', Value='L',Updated=TO_TIMESTAMP('2022-04-04 18:54:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543122
;

-- 2022-04-04T15:55:21.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Q', Value='Q',Updated=TO_TIMESTAMP('2022-04-04 18:55:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543123
;

update m_hu set clearancestatus='L',Updated=TO_TIMESTAMP('2022-04-04 18:55:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=99 where clearancestatus='Locked'
;

update m_hu set clearancestatus='C',Updated=TO_TIMESTAMP('2022-04-04 18:55:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=99 where clearancestatus='Cleared'
;

update m_hu set clearancestatus='Q',Updated=TO_TIMESTAMP('2022-04-04 18:55:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=99 where clearancestatus='Quarantined'
;
