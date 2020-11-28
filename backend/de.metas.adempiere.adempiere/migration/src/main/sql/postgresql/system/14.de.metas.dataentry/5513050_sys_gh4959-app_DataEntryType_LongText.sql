-- 2019-02-18T15:41:37.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540956,541873,TO_TIMESTAMP('2019-02-18 15:41:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dataentry','Y','Langtext',TO_TIMESTAMP('2019-02-18 15:41:37','YYYY-MM-DD HH24:MI:SS'),100,'LT','LongText')
;

-- 2019-02-18T15:41:37.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541873 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-02-18T15:41:41.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-18 15:41:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541873 AND AD_Language='de_CH'
;

-- 2019-02-18T15:41:48.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-18 15:41:48','YYYY-MM-DD HH24:MI:SS'),Name='Long text' WHERE AD_Ref_List_ID=541873 AND AD_Language='en_US'
;

-- 2019-02-18T15:41:51.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-18 15:41:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541873 AND AD_Language='en_US'
;

-- 2019-02-18T15:42:03.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2,Updated=TO_TIMESTAMP('2019-02-18 15:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563948
;

-- 2019-02-18T15:42:05.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dataentry_field','DataEntry_RecordType','VARCHAR(2)',null,'T')
;

-- 2019-02-18T15:42:05.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE DataEntry_Field SET DataEntry_RecordType='T' WHERE DataEntry_RecordType IS NULL
;

