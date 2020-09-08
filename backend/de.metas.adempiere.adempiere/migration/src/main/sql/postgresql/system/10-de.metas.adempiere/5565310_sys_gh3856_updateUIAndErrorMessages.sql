-- 2020-08-19T05:38:58.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2020-08-19 08:38:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=11912
;

-- 2020-08-19T06:57:13.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543136
;

-- 2020-08-19T06:58:12.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543142
;

-- 2020-08-19T06:58:14.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543141
;

-- 2020-08-19T06:58:19.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549292
;

-- 2020-08-19T07:05:25.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544996,0,TO_TIMESTAMP('2020-08-19 10:05:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','The Calendar {0} does not contain the Promised Date {1,date}.','I',TO_TIMESTAMP('2020-08-19 10:05:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.interceptor.M_Forecast.CalendarDoesNotContainPromisedDate')
;

-- 2020-08-19T07:05:25.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544996 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-08-19T07:07:41.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544997,0,TO_TIMESTAMP('2020-08-19 10:07:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Please set a default Calendar or an Organisation Calendar','I',TO_TIMESTAMP('2020-08-19 10:07:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.calendar.impl.CalendarBL.SetDefaultCalendarOrOrgCalendar')
;

-- 2020-08-19T07:07:41.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544997 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-08-19T07:15:55.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Standardkalender oder Organisationskalender einstellen',Updated=TO_TIMESTAMP('2020-08-19 10:15:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544997
;

-- 2020-08-19T07:16:03.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Standardkalender oder Organisationskalender einstellen',Updated=TO_TIMESTAMP('2020-08-19 10:16:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544997
;

-- 2020-08-19T07:16:49.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The Calendar {0} does not contain the Promised Date {1,date}',Updated=TO_TIMESTAMP('2020-08-19 10:16:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544996
;

-- 2020-08-19T07:16:56.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The Calendar {0} does not contain the Promised Date {1,date}',Updated=TO_TIMESTAMP('2020-08-19 10:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544996
;

-- 2020-08-19T07:17:04.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The Calendar {0} does not contain the Promised Date {1,date}',Updated=TO_TIMESTAMP('2020-08-19 10:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544996
;

-- 2020-08-19T07:17:06.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Kalender {0} enthält nicht den zugesagten Termin {1.date}',Updated=TO_TIMESTAMP('2020-08-19 10:17:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544996
;

-- 2020-08-19T07:17:11.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der Kalender {0} enthält nicht den zugesagten Termin {1.date}',Updated=TO_TIMESTAMP('2020-08-19 10:17:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544996
;

