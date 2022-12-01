-- 2021-11-17T21:09:37.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545084,0,TO_TIMESTAMP('2021-11-17 23:09:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Not enough raw materials found for {0} in {1}. We still need {2}.','E',TO_TIMESTAMP('2021-11-17 23:09:37','YYYY-MM-DD HH24:MI:SS'),100,'PPOrderIssuePlanCreateCommand.CannotFullyAllocated')
;

-- 2021-11-17T21:09:37.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545084 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-11-17T21:21:39.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Not enough raw materials found for {0} in {1}. We still need {2} of {3} required.',Updated=TO_TIMESTAMP('2021-11-17 23:21:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545084
;

