--
-- Add Issued HUStatus
-- And localize the status names
--
-- 2017-09-15T09:02:56.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541304,540478,TO_TIMESTAMP('2017-09-15 09:02:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Issued',TO_TIMESTAMP('2017-09-15 09:02:56','YYYY-MM-DD HH24:MI:SS'),100,'I','Issued')
;

-- 2017-09-15T09:02:56.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541304 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2017-09-15T09:03:02.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Aktiv',Updated=TO_TIMESTAMP('2017-09-15 09:03:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540737
;

-- 2017-09-15T09:03:08.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Zugeteilt',Updated=TO_TIMESTAMP('2017-09-15 09:03:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541304
;

-- 2017-09-15T09:03:12.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Zerstört',Updated=TO_TIMESTAMP('2017-09-15 09:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540738
;

-- 2017-09-15T09:03:24.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Geliefert',Updated=TO_TIMESTAMP('2017-09-15 09:03:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540818
;

-- 2017-09-15T09:03:28.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Geplant',Updated=TO_TIMESTAMP('2017-09-15 09:03:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540736
;

-- 2017-09-15T09:03:34.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Kommissioniert',Updated=TO_TIMESTAMP('2017-09-15 09:03:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540800
;

-- 2017-09-15T09:03:40.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-15 09:03:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=540800 AND AD_Language='en_US'
;

-- 2017-09-15T09:03:49.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-15 09:03:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=540736 AND AD_Language='en_US'
;

-- 2017-09-15T09:03:54.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-15 09:03:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=540818 AND AD_Language='en_US'
;

-- 2017-09-15T09:03:57.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-15 09:03:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=540738 AND AD_Language='en_US'
;

-- 2017-09-15T09:04:01.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-15 09:04:01','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541304 AND AD_Language='it_CH'
;

-- 2017-09-15T09:04:05.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-15 09:04:05','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=540737 AND AD_Language='en_US'
;
