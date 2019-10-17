-- 2019-02-26T18:30:52.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564258,280,0,12,540963,'Discount',TO_TIMESTAMP('2019-02-26 18:30:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Abschlag in Prozent','D',14,'"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Rabatt %',0,0,TO_TIMESTAMP('2019-02-26 18:30:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-02-26T18:30:52.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564258 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T18:31:03.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_DiscountSchema','ALTER TABLE public.I_DiscountSchema ADD COLUMN Discount NUMERIC')
;

-- 2019-02-26T18:34:40.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564258,575082,0,541081,TO_TIMESTAMP('2019-02-26 18:34:40','YYYY-MM-DD HH24:MI:SS'),100,'Abschlag in Prozent',14,'D','"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.','Y','N','N','N','N','N','N','N','Rabatt %',TO_TIMESTAMP('2019-02-26 18:34:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T18:34:40.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575082 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-02-26T18:35:03.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2019-02-26 18:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563815
;

-- 2019-02-26T18:35:03.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2019-02-26 18:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563814
;

-- 2019-02-26T18:35:03.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2019-02-26 18:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563831
;

-- 2019-02-26T18:35:03.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=200,Updated=TO_TIMESTAMP('2019-02-26 18:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575082
;

-- 2019-02-26T18:35:03.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2019-02-26 18:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565110
;

-- 2019-02-26T18:35:03.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2019-02-26 18:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563819
;

-- 2019-02-26T18:36:58.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,564258,540019,541029,0,TO_TIMESTAMP('2019-02-26 18:36:58','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Rabatt',110,0,TO_TIMESTAMP('2019-02-26 18:36:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T18:36:59.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DataType='C',Updated=TO_TIMESTAMP('2019-02-26 18:36:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540545
;

-- 2019-02-26T18:37:04.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DataType='N',Updated=TO_TIMESTAMP('2019-02-26 18:37:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541029
;

