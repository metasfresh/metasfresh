-- 2020-12-17T11:13:21.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545020,0,TO_TIMESTAMP('2020-12-17 13:13:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Etwas ist schief gegangen. Das Problem wurde abgespeichert (System-Problem <URL or #ID>), sodass das metasfresh-Team es adressieren kann.','E',TO_TIMESTAMP('2020-12-17 13:13:20','YYYY-MM-DD HH24:MI:SS'),100,'UserLoginDBError')
;

-- 2020-12-18T08:45:49.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='UserLoginInternalError',Updated=TO_TIMESTAMP('2020-12-18 10:45:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545020
;

-- 2020-12-18T08:50:04.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Etwas ist schief gegangen. Das Problem wurde abgespeichert (System-Problem {0}), sodass das metasfresh-Team es adressieren kann.',Updated=TO_TIMESTAMP('2020-12-18 10:50:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545020
;

-- 2020-12-18T08:50:12.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Etwas ist schief gegangen. Das Problem wurde abgespeichert (System-Problem {0}), sodass das metasfresh-Team es adressieren kann.',Updated=TO_TIMESTAMP('2020-12-18 10:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545020
;

-- 2020-12-18T08:50:21.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Etwas ist schief gegangen. Das Problem wurde abgespeichert (System-Problem {0}), sodass das metasfresh-Team es adressieren kann.',Updated=TO_TIMESTAMP('2020-12-18 10:50:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545020
;

-- 2020-12-18T08:50:29.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Etwas ist schief gegangen. Das Problem wurde abgespeichert (System-Problem {0}), sodass das metasfresh-Team es adressieren kann.',Updated=TO_TIMESTAMP('2020-12-18 10:50:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545020
;

-- 2020-12-18T08:45:49.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='UserLoginInternalError',Updated=TO_TIMESTAMP('2020-12-18 10:45:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545020
;

