-- 2022-01-26T14:58:37.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545094,0,TO_TIMESTAMP('2022-01-26 16:58:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Stückliste kann nicht aufgelöst werden aufgrund abweichender Attribute: {0} and {1}.','E',TO_TIMESTAMP('2022-01-26 16:58:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.order.BOMExploderCommand.attributesNotMatching')
;

-- 2022-01-26T14:58:37.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545094 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-01-26T14:58:54.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Cannot explode BOM because attributes are different: {0} and {1}.',Updated=TO_TIMESTAMP('2022-01-26 16:58:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545094
;

