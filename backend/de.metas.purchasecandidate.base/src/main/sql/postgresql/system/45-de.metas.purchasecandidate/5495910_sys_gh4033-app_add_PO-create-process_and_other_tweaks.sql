-- 2018-06-15T16:57:41.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-15 16:57:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564580
;

-- 2018-06-15T16:57:43.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-15 16:57:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560574
;

-- 2018-06-15T16:57:46.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-15 16:57:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560576
;

-- 2018-06-15T16:57:48.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-15 16:57:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560575
;

-- 2018-06-15T16:57:51.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-15 16:57:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564579
;

-- 2018-06-15T16:57:52.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-15 16:57:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558122
;

-- 2018-06-15T16:57:53.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-15 16:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558131
;

-- 2018-06-15T16:57:54.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-15 16:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563011
;

-- 2018-06-15T16:57:56.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-15 16:57:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563010
;

-- 2018-06-15T17:01:06.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-06-15 17:01:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564579
;

-- 2018-06-15T17:01:46.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2018-06-15 17:01:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560576
;

-- 2018-06-15T17:02:31.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2018-06-15 17:02:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563011
;

-- 2018-06-15T17:02:37.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=37,Updated=TO_TIMESTAMP('2018-06-15 17:02:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558151
;


--
-- delete C_PurchaseCandidate.Processing
SELECT db_alter_table('C_PurchaseCandidate','ALTER TABLE C_PurchaseCandidate DROP COLUMN Processing');

DELETE FROM AD_UI_Element WHERE AD_Field_ID=560590;
DELETE FROM AD_Field WHERE AD_Field_ID=560590;

-- 2018-06-15T19:30:08.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=557862
;

-- 2018-06-15T19:30:08.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=557862
;
-- 2018-06-15T20:03:00.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540979,'Y','N',TO_TIMESTAMP('2018-06-15 20:02:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','N','N','N','N','N','N','Y',0,'C_PurchaseCandiate_Create_PurchaseOrders','N','Y','Java',TO_TIMESTAMP('2018-06-15 20:02:59','YYYY-MM-DD HH24:MI:SS'),100,'Auswahl bestellen')
;

-- 2018-06-15T20:03:00.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540979 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-06-15T20:03:03.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-15 20:03:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540979 AND AD_Language='de_CH'
;

-- 2018-06-15T20:03:09.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-15 20:03:09','YYYY-MM-DD HH24:MI:SS'),Name='Auswahl bestellen' WHERE AD_Process_ID=540979 AND AD_Language='de_CH'
;

-- 2018-06-15T20:03:25.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-15 20:03:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Create orders for selection' WHERE AD_Process_ID=540979 AND AD_Language='en_US'
;

-- 2018-06-15T20:03:54.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.purchasecandidate.process.C_PurchaseCandiate_Create_PurchaseOrders',Updated=TO_TIMESTAMP('2018-06-15 20:03:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540979
;

-- 2018-06-15T20:04:29.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540979,540861,TO_TIMESTAMP('2018-06-15 20:04:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y',TO_TIMESTAMP('2018-06-15 20:04:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N')
;

-- 2018-06-15T20:05:05.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Auswahl schließen',Updated=TO_TIMESTAMP('2018-06-15 20:05:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540920
;

-- 2018-06-15T20:05:15.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-15 20:05:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Auswahl schließen' WHERE AD_Process_ID=540920 AND AD_Language='de_CH'
;

-- 2018-06-15T20:05:26.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-15 20:05:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Close selection',Description='' WHERE AD_Process_ID=540920 AND AD_Language='en_US'
;

-- 2018-06-15T20:10:30.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_QuickAction_Default='Y',Updated=TO_TIMESTAMP('2018-06-15 20:10:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540979 AND AD_Table_ID=540861
;

-- 2018-06-15T20:10:32.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_QuickAction_Default='Y',Updated=TO_TIMESTAMP('2018-06-15 20:10:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540972 AND AD_Table_ID=540861
;

-- 2018-06-15T20:10:58.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Auswahl bestellen', Value='C_PurchaseCandiate_Create_PurchaseOrders',Updated=TO_TIMESTAMP('2018-06-15 20:10:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540979
;

