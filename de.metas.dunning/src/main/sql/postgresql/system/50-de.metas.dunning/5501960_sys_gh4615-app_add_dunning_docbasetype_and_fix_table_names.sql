-- 2018-09-19T14:46:33.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='C_Dunning',Updated=TO_TIMESTAMP('2018-09-19 14:46:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=301
;

-- 2018-09-19T14:46:47.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Mahnart',Updated=TO_TIMESTAMP('2018-09-19 14:46:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=301
;

-- 2018-09-19T14:46:51.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Trl WHERE AD_Language='fr_CH' AND AD_Table_ID=301
;

-- 2018-09-19T14:46:52.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Trl WHERE AD_Language='it_CH' AND AD_Table_ID=301
;

-- 2018-09-19T14:46:53.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Trl WHERE AD_Language='en_GB' AND AD_Table_ID=301
;

-- 2018-09-19T14:47:03.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-19 14:47:03','YYYY-MM-DD HH24:MI:SS'),Name='Mahnart',IsTranslated='Y' WHERE AD_Table_ID=301 AND AD_Language='de_CH'
;

-- 2018-09-19T14:47:11.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-19 14:47:11','YYYY-MM-DD HH24:MI:SS'),Name='Dunning type' WHERE AD_Table_ID=301 AND AD_Language='en_US'
;

-- 2018-09-19T14:47:18.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-19 14:47:18','YYYY-MM-DD HH24:MI:SS'),Name='Mahnart' WHERE AD_Table_ID=301 AND AD_Language='nl_NL'
;

-- 2018-09-19T14:47:28.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Mahndokument',Updated=TO_TIMESTAMP('2018-09-19 14:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540401
;

-- 2018-09-19T14:47:32.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Trl WHERE AD_Language='en_GB' AND AD_Table_ID=540401
;

-- 2018-09-19T14:47:42.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-19 14:47:42','YYYY-MM-DD HH24:MI:SS'),Name='Mahndokument',IsTranslated='Y' WHERE AD_Table_ID=540401 AND AD_Language='de_CH'
;

-- 2018-09-19T14:47:46.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-19 14:47:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Table_ID=540401 AND AD_Language='en_US'
;

-- 2018-09-19T14:49:30.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,Value,EntityType) VALUES (183,0,'Y',TO_TIMESTAMP('2018-09-19 14:49:30','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-09-19 14:49:30','YYYY-MM-DD HH24:MI:SS'),100,541732,'DunningDoc',0,'Mahnung','DUN','de.metas.dunning')
;

-- 2018-09-19T14:49:30.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541732 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

