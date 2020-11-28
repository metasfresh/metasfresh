-- 13.03.2017 17:54:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556405,2619,0,10,540805,'N','DisplaySequenceLocal',TO_TIMESTAMP('2017-03-13 17:54:56','YYYY-MM-DD HH24:MI:SS'),100,'@C@, @R@ @P@','Format for printing this Address locally','D',300,'The optional Local Address Print format defines the format to be used when this address prints for the Country.  If defined, this format is used for printing the address for the country rather then the standard address format.
 The following notations are used: @C@=City  @P@=Postal  @A@=PostalAdd  @R@=Region','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Local Address Format',0,TO_TIMESTAMP('2017-03-13 17:54:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 13.03.2017 17:54:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556405 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 13.03.2017 17:57:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-03-13 17:57:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556405
;

-- 13.03.2017 17:57:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Country_Sequence ADD DisplaySequenceLocal VARCHAR(300) DEFAULT NULL 
;

-- 13.03.2017 17:57:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-03-13 17:57:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556376
;

-- 13.03.2017 17:57:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Country_Sequence ALTER COLUMN DisplaySequence  SET DEFAULT NULL 
;

-- 13.03.2017 18:06:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556405,557973,0,540792,0,TO_TIMESTAMP('2017-03-13 18:06:35','YYYY-MM-DD HH24:MI:SS'),100,'Format for printing this Address locally',0,'D','The optional Local Address Print format defines the format to be used when this address prints for the Country.  If defined, this format is used for printing the address for the country rather then the standard address format.
 The following notations are used: @C@=City  @P@=Postal  @A@=PostalAdd  @R@=Region',0,'Y','Y','Y','N','N','N','N','Y','Local Address Format',65,0,1,1,TO_TIMESTAMP('2017-03-13 18:06:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 13.03.2017 18:06:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557973 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 13.03.2017 18:07:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=40,IsDisplayed='Y' WHERE AD_Field_ID=557973
;

-- 13.03.2017 18:07:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=50,IsDisplayed='Y' WHERE AD_Field_ID=557968
;

-- 13.03.2017 18:07:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=60,IsDisplayed='Y' WHERE AD_Field_ID=557960
;


-- 13.03.2017 18:08:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLength=0,Updated=TO_TIMESTAMP('2017-03-13 18:08:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557959
;

-- 13.03.2017 18:11:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2017-03-13 18:11:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557973
;


-- 13.03.2017 18:26:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556406,192,0,19,540805,'N','C_Country_ID',TO_TIMESTAMP('2017-03-13 18:26:39','YYYY-MM-DD HH24:MI:SS'),100,'Land','D',10,'"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Land',0,TO_TIMESTAMP('2017-03-13 18:26:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 13.03.2017 18:26:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556406 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 13.03.2017 18:26:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Country_Sequence ADD C_Country_ID NUMERIC(10) DEFAULT NULL 
;


-- 13.03.2017 18:27:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=556406,Updated=TO_TIMESTAMP('2017-03-13 18:27:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540792
;

-- 13.03.2017 18:38:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2017-03-13 18:38:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556406
;

-- 13.03.2017 18:40:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2017-03-13 18:40:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540792
;

-- 13.03.2017 18:46:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Country_Sequence AlTER COLUMN C_Country_ID TYPE NUMERIC(10)
;


-- 13.03.2017 18:46:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Country_Sequence AlTER COLUMN C_Country_ID set NOT NULL
;

-- 13.03.2017 18:51:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=22, IsUpdateable='N',Updated=TO_TIMESTAMP('2017-03-13 18:51:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556406
;

-- 13.03.2017 18:53:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556406,557974,0,540792,0,TO_TIMESTAMP('2017-03-13 18:53:34','YYYY-MM-DD HH24:MI:SS'),100,'Land',30,'D','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.',0,'Y','Y','Y','N','N','N','N','N','Land',0,0,1,1,TO_TIMESTAMP('2017-03-13 18:53:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 13.03.2017 18:53:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557974 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 13.03.2017 18:53:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=30,IsDisplayed='Y' WHERE AD_Field_ID=557974
;

-- 13.03.2017 18:53:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=40,IsDisplayed='Y' WHERE AD_Field_ID=557959
;

-- 13.03.2017 18:53:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=50,IsDisplayed='Y' WHERE AD_Field_ID=557973
;

-- 13.03.2017 18:53:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=60,IsDisplayed='Y' WHERE AD_Field_ID=557968
;

-- 13.03.2017 18:53:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=70,IsDisplayed='Y' WHERE AD_Field_ID=557960
;

