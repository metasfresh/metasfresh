-- 2018-01-09T12:04:16.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544619,0,TO_TIMESTAMP('2018-01-09 12:04:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Heute','I',TO_TIMESTAMP('2018-01-09 12:04:15','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.daterange.today')
;

-- 2018-01-09T12:04:16.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544619 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-01-09T12:25:24.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgTip='window.attachment.title',Updated=TO_TIMESTAMP('2018-01-09 12:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544619
;

-- 2018-01-09T12:28:23.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgTip='',Updated=TO_TIMESTAMP('2018-01-09 12:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544619
;

-- 2018-01-09T12:38:14.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value) VALUES (0,544620,0,TO_TIMESTAMP('2018-01-09 12:38:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Gestern','','I',TO_TIMESTAMP('2018-01-09 12:38:14','YYYY-MM-DD HH24:MI:SS'),100,'window.daterange.yesterday')
;

-- 2018-01-09T12:38:14.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544620 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-01-09T12:39:15.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value) VALUES (0,544621,0,TO_TIMESTAMP('2018-01-09 12:39:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Letzte 7 Tage','','I',TO_TIMESTAMP('2018-01-09 12:39:15','YYYY-MM-DD HH24:MI:SS'),100,'window.daterange.last7days')
;

-- 2018-01-09T12:39:15.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544621 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-01-09T12:39:44.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value) VALUES (0,544622,0,TO_TIMESTAMP('2018-01-09 12:39:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Letzte 30 Tage','','I',TO_TIMESTAMP('2018-01-09 12:39:44','YYYY-MM-DD HH24:MI:SS'),100,'window.daterange.last30days')
;

-- 2018-01-09T12:39:44.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544622 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-01-09T12:40:18.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value) VALUES (0,544623,0,TO_TIMESTAMP('2018-01-09 12:40:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Dieser Monat','','I',TO_TIMESTAMP('2018-01-09 12:40:18','YYYY-MM-DD HH24:MI:SS'),100,'window.daterange.thismonth')
;

-- 2018-01-09T12:40:18.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544623 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-01-09T12:40:57.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value) VALUES (0,544624,0,TO_TIMESTAMP('2018-01-09 12:40:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Letzter Monat','','I',TO_TIMESTAMP('2018-01-09 12:40:57','YYYY-MM-DD HH24:MI:SS'),100,'window.daterange.lastmonth')
;

-- 2018-01-09T12:40:57.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544624 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-01-09T13:34:11.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-09 13:34:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Today' WHERE AD_Message_ID=544619 AND AD_Language='en_US'
;

-- 2018-01-09T13:34:34.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-09 13:34:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Last 30 Days' WHERE AD_Message_ID=544622 AND AD_Language='en_US'
;

-- 2018-01-09T13:34:45.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-09 13:34:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Last 7 Days' WHERE AD_Message_ID=544621 AND AD_Language='en_US'
;

-- 2018-01-09T13:35:00.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-09 13:35:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Last Month' WHERE AD_Message_ID=544624 AND AD_Language='en_US'
;

-- 2018-01-09T13:35:22.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-09 13:35:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='This Month' WHERE AD_Message_ID=544623 AND AD_Language='en_US'
;

-- 2018-01-09T13:35:32.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-09 13:35:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Yesterday' WHERE AD_Message_ID=544620 AND AD_Language='en_US'
;

-- 2018-01-09T13:38:47.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='webui.window.daterange.last30days',Updated=TO_TIMESTAMP('2018-01-09 13:38:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544622
;

-- 2018-01-09T13:38:51.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='webui.window.daterange.last7days',Updated=TO_TIMESTAMP('2018-01-09 13:38:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544621
;

-- 2018-01-09T13:38:54.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='webui.window.daterange.lastmonth',Updated=TO_TIMESTAMP('2018-01-09 13:38:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544624
;

-- 2018-01-09T13:38:56.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='webui.window.daterange.thismonth',Updated=TO_TIMESTAMP('2018-01-09 13:38:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544623
;

-- 2018-01-09T13:39:01.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='webui.window.daterange.yesterday',Updated=TO_TIMESTAMP('2018-01-09 13:39:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544620
;

-- 2018-01-09T13:53:05.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value) VALUES (0,544625,0,TO_TIMESTAMP('2018-01-09 13:53:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Individuell','','I',TO_TIMESTAMP('2018-01-09 13:53:05','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.daterange.custom')
;

-- 2018-01-09T13:53:05.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544625 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-01-09T13:53:17.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-09 13:53:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Custom' WHERE AD_Message_ID=544625 AND AD_Language='en_US'
;

-- 2018-01-09T13:58:41.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value) VALUES (0,544626,0,TO_TIMESTAMP('2018-01-09 13:58:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Anwenden','','I',TO_TIMESTAMP('2018-01-09 13:58:41','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.daterange.apply')
;

-- 2018-01-09T13:58:41.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544626 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-01-09T13:58:49.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-09 13:58:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Apply' WHERE AD_Message_ID=544626 AND AD_Language='en_US'
;

-- 2018-01-09T13:59:23.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value) VALUES (0,544627,0,TO_TIMESTAMP('2018-01-09 13:59:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Abbrechen','','I',TO_TIMESTAMP('2018-01-09 13:59:22','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.daterange.cancel')
;

-- 2018-01-09T13:59:23.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544627 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-01-09T13:59:30.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-09 13:59:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Cancel' WHERE AD_Message_ID=544627 AND AD_Language='en_US'
;


