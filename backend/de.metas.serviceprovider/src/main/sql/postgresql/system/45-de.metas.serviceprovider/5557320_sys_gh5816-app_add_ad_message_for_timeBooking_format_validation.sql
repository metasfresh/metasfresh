-- 2020-04-15T10:02:46.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544973,0,TO_TIMESTAMP('2020-04-15 13:02:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Incorrect format! Please enter a value in H:mm format.
( e.g.
- 2:02 - two hours and two minutes,
- 100:02 - one hundred hours and two minutes,
- 2:00 - two hours,
- 0:02 - two minutes
)','E',TO_TIMESTAMP('2020-04-15 13:02:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider.incorrectHmmFormat')
;

-- 2020-04-15T10:02:46.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544973 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-04-15T10:52:54.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Formatfehler! Bitte gib den Wert im Format H:mm ein.
(z.B.
- 2:02 - zwei Stunden und zwei Minuten
- 100:02 - 100 Stunden und zwei Minuten
- 2:00 - zwei Stunden
- 0:02 - zwei Minuten
)',Updated=TO_TIMESTAMP('2020-04-15 13:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544973
;

-- 2020-04-15T10:53:01.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Formatfehler! Bitte gib den Wert im Format H:mm ein.
(z.B.
- 2:02 - zwei Stunden und zwei Minuten
- 100:02 - 100 Stunden und zwei Minuten
- 2:00 - zwei Stunden
- 0:02 - zwei Minuten
)',Updated=TO_TIMESTAMP('2020-04-15 13:53:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544973
;

