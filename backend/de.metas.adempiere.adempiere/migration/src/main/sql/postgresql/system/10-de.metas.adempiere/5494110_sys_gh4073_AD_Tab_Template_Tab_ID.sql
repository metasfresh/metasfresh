-- 2018-05-21T19:10:45.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,Help,ColumnName,AD_Element_ID,Description,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'Template Tab','D','','Template_Tab_ID',544090,'',0,'Template Tab',100,TO_TIMESTAMP('2018-05-21 19:10:45','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-05-21 19:10:45','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-21T19:10:45.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544090 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-21T19:12:02.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Val_Rule_ID,AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsForceIncludeInGeneratedModel,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated,IsGenericZoomOrigin) VALUES (211,30,'N','N','N','N',0,'Y',100,544090,'Y','N','N','N','N','N','N','N','Y','N','N','N',106,'N','N','',278,'Template_Tab_ID',560187,'N','N','N','N','N','N','',0,100,'Template Tab','D','N',10,0,0,TO_TIMESTAMP('2018-05-21 19:12:02','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-05-21 19:12:02','YYYY-MM-DD HH24:MI:SS'),'N')
;

-- 2018-05-21T19:12:02.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560187 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-05-21T19:17:13.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:17:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=430
;

-- 2018-05-21T19:17:18.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:17:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540567
;

-- 2018-05-21T19:17:29.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:17:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=107
;

-- 2018-05-21T19:17:48.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53565
;

-- 2018-05-21T19:17:54.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:17:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=115
;

-- 2018-05-21T19:17:59.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:17:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540751
;

-- 2018-05-21T19:18:04.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:18:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540827
;

-- 2018-05-21T19:18:07.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:18:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540752
;

-- 2018-05-21T19:18:11.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:18:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540753
;

-- 2018-05-21T19:18:17.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540754
;

-- 2018-05-21T19:18:21.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:18:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540755
;

-- 2018-05-21T19:18:35.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:18:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540757
;

-- 2018-05-21T19:18:40.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Template_Tab_ID/-1@ > 0',Updated=TO_TIMESTAMP('2018-05-21 19:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540758
;

-- 2018-05-21T19:23:44.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (106,'Y','N','N','N','N',0,'Y',100,'N','D','',564333,560187,'',0,'Template Tab',100,10,TO_TIMESTAMP('2018-05-21 19:23:43','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-05-21 19:23:43','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-21T19:23:44.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564333 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-05-21T19:24:42.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SortNo=115.000000000000, SeqNoGrid=115,Updated=TO_TIMESTAMP('2018-05-21 19:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564333
;

-- 2018-05-21T19:29:32.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:29:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=430
;

-- 2018-05-21T19:29:44.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:29:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540567
;

-- 2018-05-21T19:29:52.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:29:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=107
;

-- 2018-05-21T19:30:02.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53565
;

-- 2018-05-21T19:30:08.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:30:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=115
;

-- 2018-05-21T19:30:13.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:30:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540751
;

-- 2018-05-21T19:30:20.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:30:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540827
;

-- 2018-05-21T19:30:26.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540752
;

-- 2018-05-21T19:30:34.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:30:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540753
;

-- 2018-05-21T19:30:42.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:30:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540754
;

-- 2018-05-21T19:30:49.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:30:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540755
;

-- 2018-05-21T19:30:58.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:30:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540757
;

-- 2018-05-21T19:31:05.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET ReadOnlyLogic='@Template_Tab_ID/-1@ > 0', DisplayLogic='',Updated=TO_TIMESTAMP('2018-05-21 19:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540758
;

-- 2018-05-21T19:32:49.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL, SeqNo=115,Updated=TO_TIMESTAMP('2018-05-21 19:32:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564333
;

