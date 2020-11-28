-- 2017-10-11T14:24:41.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_Board (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,Name,Updated,UpdatedBy,WEBUI_Board_ID) VALUES (0,0,417,TO_TIMESTAMP('2017-10-11 14:24:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','Project Opportunities',TO_TIMESTAMP('2017-10-11 14:24:41','YYYY-MM-DD HH24:MI:SS'),100,540001)
;

-- 2017-10-11T14:26:59.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_Board_Lane (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,WEBUI_Board_ID,WEBUI_Board_Lane_ID) VALUES (0,0,TO_TIMESTAMP('2017-10-11 14:26:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','Backlog',10,TO_TIMESTAMP('2017-10-11 14:26:59','YYYY-MM-DD HH24:MI:SS'),100,540001,540006)
;

-- 2017-10-11T14:27:23.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_Board_Lane (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,WEBUI_Board_ID,WEBUI_Board_Lane_ID) VALUES (0,0,TO_TIMESTAMP('2017-10-11 14:27:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','ToDo',20,TO_TIMESTAMP('2017-10-11 14:27:23','YYYY-MM-DD HH24:MI:SS'),100,540001,540007)
;

-- 2017-10-11T14:27:32.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_Board_Lane (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,WEBUI_Board_ID,WEBUI_Board_Lane_ID) VALUES (0,0,TO_TIMESTAMP('2017-10-11 14:27:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','InProgress',30,TO_TIMESTAMP('2017-10-11 14:27:31','YYYY-MM-DD HH24:MI:SS'),100,540001,540008)
;

-- 2017-10-11T14:27:45.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_Board_Lane (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,WEBUI_Board_ID,WEBUI_Board_Lane_ID) VALUES (0,0,TO_TIMESTAMP('2017-10-11 14:27:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','Invoiceable',40,TO_TIMESTAMP('2017-10-11 14:27:45','YYYY-MM-DD HH24:MI:SS'),100,540001,540009)
;

-- 2017-10-11T14:29:46.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_Board_CardField (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,WEBUI_Board_CardField_ID,WEBUI_Board_ID) VALUES (0,5433,0,TO_TIMESTAMP('2017-10-11 14:29:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-10-11 14:29:46','YYYY-MM-DD HH24:MI:SS'),100,540009,540001)
;

-- 2017-10-11T14:31:41.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM WEBUI_Board_CardField WHERE WEBUI_Board_CardField_ID=540009
;

-- 2017-10-11T14:31:52.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM WEBUI_Board_Lane WHERE WEBUI_Board_Lane_ID=540006
;

-- 2017-10-11T14:31:52.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM WEBUI_Board_Lane WHERE WEBUI_Board_Lane_ID=540007
;

-- 2017-10-11T14:31:52.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM WEBUI_Board_Lane WHERE WEBUI_Board_Lane_ID=540008
;

-- 2017-10-11T14:31:52.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM WEBUI_Board_Lane WHERE WEBUI_Board_Lane_ID=540009
;

-- 2017-10-11T14:31:56.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM WEBUI_Board WHERE WEBUI_Board_ID=540001
;

-- 2017-10-11T14:32:49.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_Board_CardField (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,WEBUI_Board_CardField_ID,WEBUI_Board_ID) VALUES (0,2762,0,TO_TIMESTAMP('2017-10-11 14:32:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-10-11 14:32:49','YYYY-MM-DD HH24:MI:SS'),100,540010,540000)
;

-- 2017-10-11T14:32:58.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_Board_CardField SET SeqNo=20,Updated=TO_TIMESTAMP('2017-10-11 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_Board_CardField_ID=540003
;

-- 2017-10-11T14:33:01.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_Board_CardField SET SeqNo=30,Updated=TO_TIMESTAMP('2017-10-11 14:33:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_Board_CardField_ID=540004
;

-- 2017-10-11T14:33:04.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_Board_CardField SET SeqNo=40,Updated=TO_TIMESTAMP('2017-10-11 14:33:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_Board_CardField_ID=540005
;

-- 2017-10-11T15:24:03.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Aktualisiert die Mahnkarenzen in Rechnungen', Name='Mahnkarenz in Rechnungen aktualisieren',Updated=TO_TIMESTAMP('2017-10-11 15:24:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540322
;

-- 2017-10-11T15:24:38.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 15:24:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Update Dunning Grace on open Invoices' WHERE AD_Process_ID=540322 AND AD_Language='en_US'
;

-- 2017-10-11T15:26:05.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 15:26:05','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Dunning Trigger' WHERE AD_Reference_ID=540382 AND AD_Language='en_US'
;

-- 2017-10-11T15:26:10.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 15:26:10','YYYY-MM-DD HH24:MI:SS'),Description='Dunning Trigger' WHERE AD_Reference_ID=540382 AND AD_Language='en_US'
;

-- 2017-10-11T15:26:25.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Mahnkarenz Automatik',Updated=TO_TIMESTAMP('2017-10-11 15:26:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540527
;

-- 2017-10-11T15:26:44.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 15:26:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Automatic Dunning Grace' WHERE AD_Ref_List_ID=540527 AND AD_Language='en_US'
;

-- 2017-10-11T15:27:05.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 15:27:05','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Default Paymentterm' WHERE AD_Ref_List_ID=540526 AND AD_Language='en_US'
;

