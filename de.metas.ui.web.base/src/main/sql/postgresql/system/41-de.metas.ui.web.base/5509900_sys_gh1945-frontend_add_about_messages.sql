--
-- note: also clean up a bit in the respective AD_Elements
--
-- 2019-01-17T13:19:42.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544820,0,TO_TIMESTAMP('2019-01-17 13:19:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Über','I',TO_TIMESTAMP('2019-01-17 13:19:41','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.about.caption')
;

-- 2019-01-17T13:19:42.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544820 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-01-17T13:19:46.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:19:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544820 AND AD_Language='de_CH'
;

-- 2019-01-17T13:19:54.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:19:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='About' WHERE AD_Message_ID=544820 AND AD_Language='en_US'
;

-- 2019-01-17T13:21:17.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544821,0,TO_TIMESTAMP('2019-01-17 13:21:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Erstellt durch','I',TO_TIMESTAMP('2019-01-17 13:21:17','YYYY-MM-DD HH24:MI:SS'),100,'webui.view.about.createdBy')
;

-- 2019-01-17T13:21:17.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544821 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-01-17T13:21:23.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=246 AND AD_Language='fr_CH'
;

-- 2019-01-17T13:21:29.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=246 AND AD_Language='it_CH'
;

-- 2019-01-17T13:21:29.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=246 AND AD_Language='en_GB'
;

-- 2019-01-17T13:21:37.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:21:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=246 AND AD_Language='de_CH'
;

-- 2019-01-17T13:21:37.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(246,'de_CH') 
;

-- 2019-01-17T13:21:39.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:21:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=246 AND AD_Language='de_DE'
;

-- 2019-01-17T13:21:39.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(246,'de_DE') 
;

-- 2019-01-17T13:21:39.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(246,'de_DE') 
;

-- 2019-01-17T13:21:48.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:21:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544821 AND AD_Language='de_CH'
;

-- 2019-01-17T13:22:02.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:22:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Created By' WHERE AD_Message_ID=544821 AND AD_Language='en_US'
;

-- 2019-01-17T13:22:44.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544822,0,TO_TIMESTAMP('2019-01-17 13:22:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Erstellt','I',TO_TIMESTAMP('2019-01-17 13:22:44','YYYY-MM-DD HH24:MI:SS'),100,'webui.view.about.created')
;

-- 2019-01-17T13:22:44.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544822 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-01-17T13:22:55.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=245 AND AD_Language='it_CH'
;

-- 2019-01-17T13:22:57.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=245 AND AD_Language='en_GB'
;

-- 2019-01-17T13:23:01.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:23:01','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=245 AND AD_Language='de_CH'
;

-- 2019-01-17T13:23:01.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(245,'de_CH') 
;

-- 2019-01-17T13:23:08.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:23:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Created' WHERE AD_Message_ID=544822 AND AD_Language='en_US'
;

-- 2019-01-17T13:23:11.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:23:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544822 AND AD_Language='de_CH'
;

-- 2019-01-17T13:23:58.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544823,0,TO_TIMESTAMP('2019-01-17 13:23:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Aktualisiert','I',TO_TIMESTAMP('2019-01-17 13:23:58','YYYY-MM-DD HH24:MI:SS'),100,'webui.view.about.updated')
;

-- 2019-01-17T13:23:58.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544823 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-01-17T13:24:02.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:24:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544823 AND AD_Language='de_CH'
;

-- 2019-01-17T13:24:11.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=607 AND AD_Language='fr_CH'
;

-- 2019-01-17T13:24:11.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=607 AND AD_Language='it_CH'
;

-- 2019-01-17T13:24:11.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=607 AND AD_Language='en_GB'
;

-- 2019-01-17T13:24:17.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:24:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=607 AND AD_Language='de_DE'
;

-- 2019-01-17T13:24:17.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(607,'de_DE') 
;

-- 2019-01-17T13:24:17.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(607,'de_DE') 
;

-- 2019-01-17T13:24:27.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:24:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Updated' WHERE AD_Message_ID=544823 AND AD_Language='en_US'
;

-- 2019-01-17T13:24:50.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:24:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=607 AND AD_Language='de_CH'
;

-- 2019-01-17T13:24:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(607,'de_CH') 
;

-- 2019-01-17T13:25:00.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544824,0,TO_TIMESTAMP('2019-01-17 13:25:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Aktualisiert durch','I',TO_TIMESTAMP('2019-01-17 13:25:00','YYYY-MM-DD HH24:MI:SS'),100,'webui.view.about.updatedBy')
;

-- 2019-01-17T13:25:00.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544824 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-01-17T13:25:09.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=608 AND AD_Language='fr_CH'
;

-- 2019-01-17T13:25:09.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=608 AND AD_Language='it_CH'
;

-- 2019-01-17T13:25:09.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=608 AND AD_Language='en_GB'
;

-- 2019-01-17T13:25:14.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:25:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=608 AND AD_Language='de_CH'
;

-- 2019-01-17T13:25:14.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(608,'de_CH') 
;

-- 2019-01-17T13:25:21.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:25:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Updated By' WHERE AD_Message_ID=544824 AND AD_Language='en_US'
;

-- 2019-01-17T13:25:24.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:25:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544824 AND AD_Language='de_CH'
;

-- 2019-01-17T13:25:31.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 13:25:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=608 AND AD_Language='de_DE'
;

-- 2019-01-17T13:25:31.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(608,'de_DE') 
;

-- 2019-01-17T13:25:31.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(608,'de_DE') 
;

