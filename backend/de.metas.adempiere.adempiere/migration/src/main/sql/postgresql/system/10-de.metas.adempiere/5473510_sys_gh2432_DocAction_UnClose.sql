-- 2017-10-03T14:02:30.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541319,135,TO_TIMESTAMP('2017-10-03 14:02:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Öffnen',TO_TIMESTAMP('2017-10-03 14:02:30','YYYY-MM-DD HH24:MI:SS'),100,'UC','UnClose')
;

-- 2017-10-03T14:02:30.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541319 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2017-10-03T14:02:59.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-03 14:02:59','YYYY-MM-DD HH24:MI:SS'),Name='Re-open',Description='Re-open closed document.' WHERE AD_Ref_List_ID=541319 AND AD_Language='en_US'
;

-- 2017-10-03T14:03:02.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-03 14:03:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541319 AND AD_Language='en_US'
;

-- 2017-10-03T14:05:35.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-03 14:05:35','YYYY-MM-DD HH24:MI:SS'),Description='Re-open closed document' WHERE AD_Ref_List_ID=541319 AND AD_Language='en_US'
;

