-- 2017-11-27T16:35:39.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,PrintName,ColumnName,AD_Element_ID,Description,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (0,'Y',TO_TIMESTAMP('2017-11-27 16:35:39','YYYY-MM-DD HH24:MI:SS'),100,'Range','IsRangeFilter',543489,'Check if the filter by this column shall render a range component',0,'Range filter','D',TO_TIMESTAMP('2017-11-27 16:35:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-27T16:35:39.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543489 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-11-27T16:37:43.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,PrintName,ColumnName,AD_Element_ID,Description,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (0,'Y',TO_TIMESTAMP('2017-11-27 16:37:42','YYYY-MM-DD HH24:MI:SS'),100,'Filter +/- buttons','IsShowFilterIncrementButtons',543490,'Show filter increment/decrement buttons',0,'Filter +/- buttons','D',TO_TIMESTAMP('2017-11-27 16:37:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-27T16:37:43.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543490 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-11-27T16:39:33.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,PrintName,ColumnName,AD_Element_ID,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (0,'Y',TO_TIMESTAMP('2017-11-27 16:39:32','YYYY-MM-DD HH24:MI:SS'),100,'Filter Default Value','FilterDefaultValue',543491,0,'Filter Default Value','D',TO_TIMESTAMP('2017-11-27 16:39:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-27T16:39:33.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543491 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-11-27T16:40:09.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-11-27 16:40:09','YYYY-MM-DD HH24:MI:SS'),100,543489,'Y','N','N','N','N','N','Y','N','N','N',101,'N','N','IsRangeFilter','N',557951,'N','Y','N','N','N','N','Check if the filter by this column shall render a range component',0,0,TO_TIMESTAMP('2017-11-27 16:40:09','YYYY-MM-DD HH24:MI:SS'),100,'Range filter','D')
;

-- 2017-11-27T16:40:09.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557951 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-27T16:40:40.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-11-27 16:40:40','YYYY-MM-DD HH24:MI:SS'),100,543490,'Y','N','N','N','N','N','Y','N','N','N',101,'N','N','IsShowFilterIncrementButtons','N',557952,'N','Y','N','N','N','N','Show filter increment/decrement buttons',0,0,TO_TIMESTAMP('2017-11-27 16:40:40','YYYY-MM-DD HH24:MI:SS'),100,'Filter +/- buttons','D')
;

-- 2017-11-27T16:40:40.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557952 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-27T16:41:26.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (10,255,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-11-27 16:41:25','YYYY-MM-DD HH24:MI:SS'),100,543491,'Y','N','N','N','N','N','Y','N','N','N',101,'N','N','FilterDefaultValue','N',557953,'N','N','N','N','N','N',0,0,TO_TIMESTAMP('2017-11-27 16:41:25','YYYY-MM-DD HH24:MI:SS'),100,'Filter Default Value','D')
;

-- 2017-11-27T16:41:26.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557953 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-27T16:46:28.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,IsCentrallyMaintained,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (101,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2017-11-27 16:46:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',560612,557079,0,'Selection column ordering','D',TO_TIMESTAMP('2017-11-27 16:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-27T16:46:28.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560612 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-27T16:46:28.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,IsCentrallyMaintained,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (101,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2017-11-27 16:46:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',560613,557951,'Check if the filter by this column shall render a range component',0,'Range filter','D',TO_TIMESTAMP('2017-11-27 16:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-27T16:46:28.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560613 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-27T16:46:28.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,IsCentrallyMaintained,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (101,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2017-11-27 16:46:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',560614,557952,'Show filter increment/decrement buttons',0,'Filter +/- buttons','D',TO_TIMESTAMP('2017-11-27 16:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-27T16:46:28.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560614 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-27T16:46:28.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,IsCentrallyMaintained,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (101,'Y',255,'N','N','N','N',0,'Y',TO_TIMESTAMP('2017-11-27 16:46:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',560615,557953,0,'Filter Default Value','D',TO_TIMESTAMP('2017-11-27 16:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-27T16:46:28.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560615 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-27T16:47:33.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2017-11-27 16:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560612
;

-- 2017-11-27T16:47:33.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2017-11-27 16:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=61142
;

-- 2017-11-27T16:47:33.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2017-11-27 16:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=152
;

-- 2017-11-27T16:47:33.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2017-11-27 16:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=153
;

-- 2017-11-27T16:47:33.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2017-11-27 16:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=154
;

-- 2017-11-27T16:47:33.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2017-11-27 16:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=250
;

-- 2017-11-27T16:47:33.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2017-11-27 16:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=155
;

-- 2017-11-27T16:47:33.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2017-11-27 16:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=158
;

-- 2017-11-27T16:47:33.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2017-11-27 16:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=542913
;

-- 2017-11-27T16:47:34.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=170
;

-- 2017-11-27T16:47:34.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=172
;

-- 2017-11-27T16:47:34.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=310
;

-- 2017-11-27T16:47:34.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56279
;

-- 2017-11-27T16:47:34.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56374
;

-- 2017-11-27T16:47:34.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=200,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5122
;

-- 2017-11-27T16:47:34.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=210,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2526
;

-- 2017-11-27T16:47:34.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=171
;

-- 2017-11-27T16:47:34.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54403
;

-- 2017-11-27T16:47:34.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2574
;

-- 2017-11-27T16:47:34.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=250,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2573
;

-- 2017-11-27T16:47:34.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=160
;

-- 2017-11-27T16:47:34.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=270,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=161
;

-- 2017-11-27T16:47:34.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=280,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=162
;

-- 2017-11-27T16:47:34.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=290,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=166
;

-- 2017-11-27T16:47:34.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=300,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2370
;

-- 2017-11-27T16:47:34.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=310,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=169
;

-- 2017-11-27T16:47:34.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=320,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10128
;

-- 2017-11-27T16:47:34.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=330,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4941
;

-- 2017-11-27T16:47:34.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=340,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=50188
;

-- 2017-11-27T16:47:34.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=350,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=168
;

-- 2017-11-27T16:47:34.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=360,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=159
;

-- 2017-11-27T16:47:34.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=370,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=825
;

-- 2017-11-27T16:47:34.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=380,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=167
;

-- 2017-11-27T16:47:34.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=390,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5121
;

-- 2017-11-27T16:47:34.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=400,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56317
;

-- 2017-11-27T16:47:34.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=410,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=549455
;

-- 2017-11-27T16:47:34.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=420,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=151
;

-- 2017-11-27T16:47:34.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=430,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547483
;

-- 2017-11-27T16:47:34.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=440,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=549348
;

-- 2017-11-27T16:47:34.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=450,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551629
;

-- 2017-11-27T16:47:34.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=460,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=70266
;

-- 2017-11-27T16:47:34.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=470,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=72812
;

-- 2017-11-27T16:47:34.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=480,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555402
;

-- 2017-11-27T16:47:34.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=490,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555825
;

-- 2017-11-27T16:47:34.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=500,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557471
;

-- 2017-11-27T16:47:34.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=510,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4940
;

-- 2017-11-27T16:47:34.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=520,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560613
;

-- 2017-11-27T16:47:34.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=530,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560614
;

-- 2017-11-27T16:47:34.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=540,Updated=TO_TIMESTAMP('2017-11-27 16:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560615
;

-- 2017-11-27T16:50:25.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560615
;

-- 2017-11-27T16:50:25.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560613
;

-- 2017-11-27T16:50:25.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560614
;

-- 2017-11-27T16:50:25.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560612
;

-- 2017-11-27T16:50:25.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=61142
;

-- 2017-11-27T16:50:25.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=152
;

-- 2017-11-27T16:50:25.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4940
;

-- 2017-11-27T16:50:25.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=153
;

-- 2017-11-27T16:50:25.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=154
;

-- 2017-11-27T16:50:25.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=250
;

-- 2017-11-27T16:50:25.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=155
;

-- 2017-11-27T16:50:25.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=158
;

-- 2017-11-27T16:50:25.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=542913
;

-- 2017-11-27T16:50:25.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=170
;

-- 2017-11-27T16:50:25.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=172
;

-- 2017-11-27T16:50:25.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=310
;

-- 2017-11-27T16:50:25.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56279
;

-- 2017-11-27T16:50:25.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56374
;

-- 2017-11-27T16:50:25.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5122
;

-- 2017-11-27T16:50:25.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2526
;

-- 2017-11-27T16:50:25.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=171
;

-- 2017-11-27T16:50:25.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54403
;

-- 2017-11-27T16:50:25.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2574
;

-- 2017-11-27T16:50:25.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2573
;

-- 2017-11-27T16:50:25.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=160
;

-- 2017-11-27T16:50:25.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=161
;

-- 2017-11-27T16:50:25.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=162
;

-- 2017-11-27T16:50:25.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=166
;

-- 2017-11-27T16:50:25.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=310,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2370
;

-- 2017-11-27T16:50:25.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=320,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=169
;

-- 2017-11-27T16:50:25.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=330,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10128
;

-- 2017-11-27T16:50:25.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=340,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4941
;

-- 2017-11-27T16:50:25.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=350,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=50188
;

-- 2017-11-27T16:50:25.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=360,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=168
;

-- 2017-11-27T16:50:25.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=370,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=825
;

-- 2017-11-27T16:50:25.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=480,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=72812
;

-- 2017-11-27T16:50:25.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=490,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555402
;

-- 2017-11-27T16:50:25.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=500,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555825
;

-- 2017-11-27T16:50:25.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=510,Updated=TO_TIMESTAMP('2017-11-27 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557471
;

-- 2017-11-27T16:51:31.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_FieldGroup (Created,CreatedBy,AD_Client_ID,IsActive,IsCollapsedByDefault,AD_FieldGroup_ID,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (TO_TIMESTAMP('2017-11-27 16:51:31','YYYY-MM-DD HH24:MI:SS'),100,0,'Y','N',540091,0,'Filtering','D',TO_TIMESTAMP('2017-11-27 16:51:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-27T16:51:31.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_FieldGroup_Trl (AD_Language,AD_FieldGroup_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_FieldGroup_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_FieldGroup t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_FieldGroup_ID=540091 AND NOT EXISTS (SELECT 1 FROM AD_FieldGroup_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_FieldGroup_ID=t.AD_FieldGroup_ID)
;

-- 2017-11-27T16:51:35.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_FieldGroup SET FieldGroupType='C',Updated=TO_TIMESTAMP('2017-11-27 16:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_FieldGroup_ID=540091
;

-- 2017-11-27T16:51:46.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2017-11-27 16:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4940
;

-- 2017-11-27T16:52:12.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSelectionColumn@=Y', AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2017-11-27 16:52:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560613
;

-- 2017-11-27T16:52:19.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSelectionColumn@=Y', AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2017-11-27 16:52:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560614
;

-- 2017-11-27T16:52:25.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSelectionColumn@=Y', AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2017-11-27 16:52:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560615
;

