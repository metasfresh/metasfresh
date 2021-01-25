-- 2021-01-11T11:50:05.772Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Benutzerkonto ist gesperrt.',Updated=TO_TIMESTAMP('2021-01-11 13:50:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=540115
;

-- 2021-01-11T11:54:21.687Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Benutzerkonto ist gesperrt.',Updated=TO_TIMESTAMP('2021-01-11 13:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=540115
;

-- 2021-01-11T11:54:36.863Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Benutzerkonto ist gesperrt.',Updated=TO_TIMESTAMP('2021-01-11 13:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=540115
;

-- 2021-01-11T12:00:42.725Z
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545021,0,TO_TIMESTAMP('2021-01-11 14:00:42','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','No roles','E',TO_TIMESTAMP('2021-01-11 14:00:42','YYYY-MM-DD HH24:MI:SS'),100,'NoRoles')
;

-- 2021-01-11T12:00:42.767Z
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545021 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-01-11T12:01:12.045Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Keine Rollen.',Updated=TO_TIMESTAMP('2021-01-11 14:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545021
;

-- 2021-01-11T12:07:24.266Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Dem Benutzer ist keine Rolle zugeordnet.',Updated=TO_TIMESTAMP('2021-01-11 14:07:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545021
;

-- 2021-01-11T12:07:37.149Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='User account has no assigned role.',Updated=TO_TIMESTAMP('2021-01-11 14:07:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545021
;

-- 2021-01-11T12:07:50.527Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='User account has no assigned role.',Updated=TO_TIMESTAMP('2021-01-11 14:07:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545021
;

-- 2021-01-11T12:08:13.128Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='User account has no assigned role.',Updated=TO_TIMESTAMP('2021-01-11 14:08:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545021
;

-- 2021-01-11T12:09:55.177Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Der Benutzerzugang ist gesperrt.',Updated=TO_TIMESTAMP('2021-01-11 14:09:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=540115
;

-- 2021-01-11T12:10:05.885Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Der Benutzerzugang ist gesperrt.',Updated=TO_TIMESTAMP('2021-01-11 14:10:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=540115
;

-- 2021-01-11T12:11:16.497Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Nutzer oder Passwort ist nicht korrekt.',Updated=TO_TIMESTAMP('2021-01-11 14:11:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=540131
;

-- 2021-01-11T12:11:21.175Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Nutzer oder Passwort ist nicht korrekt.',Updated=TO_TIMESTAMP('2021-01-11 14:11:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=540131
;

-- 2021-01-11T12:11:25.720Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Nutzer oder Passwort ist nicht korrekt.',Updated=TO_TIMESTAMP('2021-01-11 14:11:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=540131
;

-- 2021-01-11T12:11:30.430Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Nutzer oder Passwort ist nicht korrekt.',Updated=TO_TIMESTAMP('2021-01-11 14:11:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=540131
;

-- 2021-01-11T13:55:00.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Something went wrong. The problem has been saved (system problem {0}) so that the metasfresh team can address it.',Updated=TO_TIMESTAMP('2021-01-11 15:55:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545020
;

-- 2021-01-11T13:55:17.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-01-11 15:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545020
;

-- 2021-01-11T15:08:14.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Dem Benutzer ist keine Rolle zugeordnet.',Updated=TO_TIMESTAMP('2021-01-11 17:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545021
;

-- 2021-01-11T12:11:34.349Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Nutzer oder Passwort ist nicht korrekt.',Updated=TO_TIMESTAMP('2021-01-11 14:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Message_ID=540131
;

-- 2021-01-11T12:11:38.402Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Nutzer oder Passwort ist nicht korrekt.',Updated=TO_TIMESTAMP('2021-01-11 14:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Message_ID=540131
;

-- 2021-01-11T12:11:43.300Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Nutzer oder Passwort ist nicht korrekt.',Updated=TO_TIMESTAMP('2021-01-11 14:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=540131
;

-- 2021-01-11T12:13:13.022Z
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Der Code zum Zurücksetzen Ihres Passworts ist nicht mehr gültig.',Updated=TO_TIMESTAMP('2021-01-11 14:13:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=53109
;

-- 2021-01-11T12:13:19.352Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Der Code zum Zurücksetzen Ihres Passworts ist nicht mehr gültig.',Updated=TO_TIMESTAMP('2021-01-11 14:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=53109
;

-- 2021-01-11T12:13:22.750Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Der Code zum Zurücksetzen Ihres Passworts ist nicht mehr gültig.',Updated=TO_TIMESTAMP('2021-01-11 14:13:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=53109
;

-- 2021-01-11T12:13:25.693Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Der Code zum Zurücksetzen Ihres Passworts ist nicht mehr gültig.',Updated=TO_TIMESTAMP('2021-01-11 14:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=53109
;

-- 2021-01-11T12:13:28.363Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Der Code zum Zurücksetzen Ihres Passworts ist nicht mehr gültig.',Updated=TO_TIMESTAMP('2021-01-11 14:13:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=53109
;

-- 2021-01-11T12:13:31.270Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Der Code zum Zurücksetzen Ihres Passworts ist nicht mehr gültig.',Updated=TO_TIMESTAMP('2021-01-11 14:13:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Message_ID=53109
;

-- 2021-01-11T12:13:34.001Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Der Code zum Zurücksetzen Ihres Passworts ist nicht mehr gültig.',Updated=TO_TIMESTAMP('2021-01-11 14:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Message_ID=53109
;

-- 2021-01-11T12:13:38.212Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Der Code zum Zurücksetzen Ihres Passworts ist nicht mehr gültig.',Updated=TO_TIMESTAMP('2021-01-11 14:13:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=53109
;

-- 2021-01-11T13:55:00.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Something went wrong. The problem has been saved (system problem {0}) so that the metasfresh team can address it.',Updated=TO_TIMESTAMP('2021-01-11 15:55:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545020
;

-- 2021-01-11T13:55:17.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-01-11 15:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545020
;

-- 2021-01-12T07:25:07.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Username or email address invalid.',Updated=TO_TIMESTAMP('2021-01-12 09:25:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=53110
;

-- 2021-01-12T07:25:23.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Username or email address invalid.',Updated=TO_TIMESTAMP('2021-01-12 09:25:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Message_ID=53110
;

-- 2021-01-12T07:32:19.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-01-12 09:32:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Message_ID=53110
;

-- 2021-01-12T07:32:23.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-01-12 09:32:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=53110
;

-- 2021-01-12T07:39:06.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='User or password is incorrect.',Updated=TO_TIMESTAMP('2021-01-12 09:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=540131
;

-- 2021-01-12T07:39:14.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='User or password is incorrect.',Updated=TO_TIMESTAMP('2021-01-12 09:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Message_ID=540131
;

-- 2021-01-12T07:54:33.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-01-12 09:54:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=540131
;

-- 2021-01-12T07:57:32.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='User account is locked.',Updated=TO_TIMESTAMP('2021-01-12 09:57:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=540115
;

-- 2021-01-12T07:57:36.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='User account is locked.',Updated=TO_TIMESTAMP('2021-01-12 09:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=540115
;

-- 2021-01-12T07:57:42.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-01-12 09:57:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=540115
;

-- 2021-01-12T07:57:47.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-01-12 09:57:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=540115
;

-- 2021-01-12T07:57:54.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='User account is locked.',Updated=TO_TIMESTAMP('2021-01-12 09:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Message_ID=540115
;

-- 2021-01-12T07:58:05.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='User account is locked.',Updated=TO_TIMESTAMP('2021-01-12 09:58:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Message_ID=540115
;

-- 2021-01-12T07:58:32.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Nutzer oder Passwort ist nicht korrekt.',Updated=TO_TIMESTAMP('2021-01-12 09:58:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=540131
;

-- 2021-01-12T07:58:41.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-01-12 09:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=540131
;

-- 2021-01-12T08:00:55.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The code to reset your password is no longer valid.',Updated=TO_TIMESTAMP('2021-01-12 10:00:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=53109
;

-- 2021-01-12T08:01:06.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-01-12 10:01:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=53109
;

-- 2021-01-12T08:02:22.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-01-12 10:02:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=53110
;
