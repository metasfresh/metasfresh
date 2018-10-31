-- 2018-08-09T11:37:24.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560761,1835,0,20,322,'IsCounted',TO_TIMESTAMP('2018-08-09 11:37:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Count number of not empty elements','D',1,'Calculate the total number (?) of not empty (NULL) elements (maximum is the number of lines).','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Calculate Count (?)',0,0,TO_TIMESTAMP('2018-08-09 11:37:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-08-09T11:37:24.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560761 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;



-- 2018-08-13T14:22:42.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Name='Gezählt',Updated=TO_TIMESTAMP('2018-08-13 14:22:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560761
;

-- 2018-08-13T14:22:42.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gezählt', Description='Count number of not empty elements', Help='Calculate the total number (?) of not empty (NULL) elements (maximum is the number of lines).' WHERE AD_Column_ID=560761
;

-- 2018-08-13T14:22:54.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET Name='Gezählt' WHERE AD_Column_ID=560761 AND AD_Language='de_CH'
;

-- 2018-08-13T14:22:55.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET IsTranslated='Y' WHERE AD_Column_ID=560761 AND AD_Language='de_CH'
;

-- 2018-08-13T14:23:13.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET Name='Gezählt' WHERE AD_Column_ID=560761 AND AD_Language='nl_NL'
;

-- 2018-08-13T14:23:21.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET Name='Counted',IsTranslated='Y' WHERE AD_Column_ID=560761 AND AD_Language='en_US'
;

