-- 2017-07-07T07:17:05.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544438,0,TO_TIMESTAMP('2017-07-07 07:17:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Alle Datensätze sind offen.','E',TO_TIMESTAMP('2017-07-07 07:17:04','YYYY-MM-DD HH24:MI:SS'),100,'M_ReceiptSchedule_ReOpen.ReceiptScheduleAllOpen')
;

-- 2017-07-07T07:17:05.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544438 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-07T07:19:17.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544439,0,TO_TIMESTAMP('2017-07-07 07:19:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','Alle Datensätze sind gesclossen.','E',TO_TIMESTAMP('2017-07-07 07:19:17','YYYY-MM-DD HH24:MI:SS'),100,'M_ReceiptSchedule_Close.ReceiptSchedulesAllClosed')
;

-- 2017-07-07T07:19:17.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544439 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-07T07:20:18.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='M_ReceiptSchedule_ReOpen.ReceiptSchedulesAllOpen',Updated=TO_TIMESTAMP('2017-07-07 07:20:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544438
;

-- 2017-07-07T07:26:11.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544440,0,TO_TIMESTAMP('2017-07-07 07:26:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Überspringe bereits geschlossenen Datensatz {0}.','I',TO_TIMESTAMP('2017-07-07 07:26:11','YYYY-MM-DD HH24:MI:SS'),100,'M_ReceiptSchedule_Close.SkipClosed_1P')
;

-- 2017-07-07T07:26:11.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544440 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-07T07:26:52.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544441,0,TO_TIMESTAMP('2017-07-07 07:26:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Überspringe bereits geöffneten Datensatz {0}.','I',TO_TIMESTAMP('2017-07-07 07:26:52','YYYY-MM-DD HH24:MI:SS'),100,'M_ReceiptSchedule_Close.SkipOpen_1P')
;

-- 2017-07-07T07:26:52.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544441 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-07T07:26:58.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgType='I',Updated=TO_TIMESTAMP('2017-07-07 07:26:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544439
;

-- 2017-07-07T07:27:04.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgType='I',Updated=TO_TIMESTAMP('2017-07-07 07:27:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544438
;

-- 2017-07-07T07:30:17.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-07 07:30:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='All records closed' WHERE AD_Message_ID=544439 AND AD_Language='en_US'
;

-- 2017-07-07T07:30:28.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-07 07:30:28','YYYY-MM-DD HH24:MI:SS'),MsgText='All records closed already.' WHERE AD_Message_ID=544439 AND AD_Language='en_US'
;

-- 2017-07-07T07:30:30.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-07 07:30:30','YYYY-MM-DD HH24:MI:SS'),MsgText='M_ReceiptSchedule' WHERE AD_Message_ID=544439 AND AD_Language='en_US'
;

-- 2017-07-07T07:30:53.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-07 07:30:53','YYYY-MM-DD HH24:MI:SS'),MsgText='All records are already closed.' WHERE AD_Message_ID=544439 AND AD_Language='en_US'
;

-- 2017-07-07T07:31:08.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-07 07:31:08','YYYY-MM-DD HH24:MI:SS'),MsgText='All records are still open.' WHERE AD_Message_ID=544438 AND AD_Language='en_US'
;

-- 2017-07-07T07:31:29.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-07 07:31:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Skipping already closed record {0}.' WHERE AD_Message_ID=544440 AND AD_Language='en_US'
;

-- 2017-07-07T07:31:42.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-07 07:31:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544441 AND AD_Language='en_US'
;

-- 2017-07-07T07:32:04.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-07 07:32:04','YYYY-MM-DD HH24:MI:SS'),MsgText='Skipping still open record {0}.' WHERE AD_Message_ID=544441 AND AD_Language='en_US'
;

-- 2017-07-07T07:32:21.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Überspringe geöffneten Datensatz {0}.',Updated=TO_TIMESTAMP('2017-07-07 07:32:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544441
;

-- 2017-07-07T07:32:27.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-07 07:32:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544438 AND AD_Language='en_US'
;

-- 2017-07-07T08:30:09.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Auswahl - Zeilen schließen',Updated=TO_TIMESTAMP('2017-07-07 08:30:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540547
;

-- 2017-07-07T08:30:48.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Auswahl - Zeilen reaktivieren', RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2017-07-07 08:30:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540546
;

-- 2017-07-07T08:30:55.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2017-07-07 08:30:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540547
;

