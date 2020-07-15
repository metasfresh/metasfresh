-- 2018-02-28T11:27:50.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,Description,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'Force include in generated model','D','IsForceIncludeInGeneratedModel',543918,'Force including this column in java generated interface and class',0,'Force include in generated model',100,TO_TIMESTAMP('2018-02-28 11:27:50','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-28 11:27:50','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-28T11:27:50.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543918 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-02-28T11:28:00.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (20,'N','N','N','N','N',0,'Y',100,543918,'Y','N','N','N','N','N','N','Y','N','N','N',101,'N','N','IsForceIncludeInGeneratedModel','N',559507,'N','Y','N','N','N','N','Force including this column in java generated interface and class',0,100,'Force include in generated model','D','N',1,0,0,TO_TIMESTAMP('2018-02-28 11:28:00','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-02-28 11:28:00','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-28T11:28:00.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559507 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-28T11:28:33.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (101,'Y','N','N','N','N',0,'Y',100,'N','D',563060,559507,'Force including this column in java generated interface and class',0,'Force include in generated model',100,1,TO_TIMESTAMP('2018-02-28 11:28:33','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-28 11:28:33','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-28T11:28:33.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-28T11:28:55.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=490,Updated=TO_TIMESTAMP('2018-02-28 11:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563060
;

-- 2018-02-28T11:28:55.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=500,Updated=TO_TIMESTAMP('2018-02-28 11:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555825
;

-- 2018-02-28T11:28:55.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=510,Updated=TO_TIMESTAMP('2018-02-28 11:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557471
;

-- 2018-02-28T11:28:55.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=520,Updated=TO_TIMESTAMP('2018-02-28 11:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4940
;

-- 2018-02-28T11:28:55.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=530,Updated=TO_TIMESTAMP('2018-02-28 11:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560613
;

-- 2018-02-28T11:28:55.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=540,Updated=TO_TIMESTAMP('2018-02-28 11:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560614
;

-- 2018-02-28T11:28:55.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=550,Updated=TO_TIMESTAMP('2018-02-28 11:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560615
;

-- 2018-02-28T11:29:04.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-28 11:29:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563060
;

