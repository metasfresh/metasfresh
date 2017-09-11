-- 2017-09-11T13:09:22.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543413,0,'ContractStartDate',TO_TIMESTAMP('2017-09-11 13:09:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.subscription','Y','Contract Start Date','Contract Start Date',TO_TIMESTAMP('2017-09-11 13:09:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-11T13:09:22.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543413 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-09-11T13:09:43.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543414,0,'ContractEndDate',TO_TIMESTAMP('2017-09-11 13:09:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.subscription','Y','Contract End Date','Contract End Date',TO_TIMESTAMP('2017-09-11 13:09:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-11T13:09:43.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543414 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-09-11T13:10:19.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.flatrate',Updated=TO_TIMESTAMP('2017-09-11 13:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543413
;

-- 2017-09-11T13:10:35.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557132,543413,0,15,540310,'N','ContractStartDate',TO_TIMESTAMP('2017-09-11 13:10:35','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.flatrate',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Contract Start Date',0,0,TO_TIMESTAMP('2017-09-11 13:10:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-11T13:10:35.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557132 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-11T13:10:38.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('c_flatrate_data','ALTER TABLE public.C_Flatrate_Data ADD COLUMN ContractStartDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2017-09-11T13:10:57.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.flatrate',Updated=TO_TIMESTAMP('2017-09-11 13:10:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543414
;

-- 2017-09-11T13:11:02.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557133,543414,0,15,540310,'N','ContractEndDate',TO_TIMESTAMP('2017-09-11 13:11:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.flatrate',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Contract End Date',0,0,TO_TIMESTAMP('2017-09-11 13:11:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-11T13:11:02.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557133 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-11T13:11:04.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('c_flatrate_data','ALTER TABLE public.C_Flatrate_Data ADD COLUMN ContractEndDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2017-09-11T13:12:48.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557132,559845,0,540328,0,TO_TIMESTAMP('2017-09-11 13:12:48','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.flatrate',0,'Y','Y','Y','Y','N','N','N','N','N','Contract Start Date',60,60,0,1,1,TO_TIMESTAMP('2017-09-11 13:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-11T13:12:48.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559845 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-11T13:13:01.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557133,559846,0,540328,0,TO_TIMESTAMP('2017-09-11 13:13:01','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.flatrate',0,'Y','Y','Y','Y','N','N','N','N','N','Contract End Date',60,60,0,1,1,TO_TIMESTAMP('2017-09-11 13:13:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-11T13:13:01.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559846 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-11T13:13:16.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2017-09-11 13:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547788
;

-- 2017-09-11T13:13:16.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2017-09-11 13:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547796
;

-- 2017-09-11T13:13:16.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2017-09-11 13:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547797
;

-- 2017-09-11T13:13:16.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2017-09-11 13:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547795
;

-- 2017-09-11T13:13:16.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2017-09-11 13:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559846
;

-- 2017-09-11T13:13:25.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-09-11 13:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559845
;

-- 2017-09-11T13:13:30.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-09-11 13:13:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548196
;

-- 2017-09-11T13:13:30.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-09-11 13:13:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559845
;

-- 2017-09-11T13:13:30.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-09-11 13:13:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559846
;

-- 2017-09-11T13:30:18.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557134,543413,0,15,540836,'N','ContractStartDate',TO_TIMESTAMP('2017-09-11 13:30:18','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.flatrate',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Contract Start Date',0,0,TO_TIMESTAMP('2017-09-11 13:30:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-11T13:30:18.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557134 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-11T13:30:21.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('i_flatrate_term','ALTER TABLE public.I_Flatrate_Term ADD COLUMN ContractStartDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2017-09-11T13:30:33.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557135,543414,0,15,540836,'N','ContractEndDate',TO_TIMESTAMP('2017-09-11 13:30:33','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.flatrate',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Contract End Date',0,0,TO_TIMESTAMP('2017-09-11 13:30:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-11T13:30:33.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557135 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-11T13:30:36.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('i_flatrate_term','ALTER TABLE public.I_Flatrate_Term ADD COLUMN ContractEndDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2017-09-11T13:35:10.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557134,559879,0,540858,0,TO_TIMESTAMP('2017-09-11 13:35:10','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','Y','N','N','N','N','N','Contract Start Date',180,180,0,1,1,TO_TIMESTAMP('2017-09-11 13:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-11T13:35:10.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559879 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-11T13:35:18.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.flatrate',Updated=TO_TIMESTAMP('2017-09-11 13:35:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559879
;

-- 2017-09-11T13:35:52.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557135,559881,0,540858,0,TO_TIMESTAMP('2017-09-11 13:35:51','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.flatrate',0,'Y','Y','Y','Y','N','N','N','N','N','Contract End Date',190,190,0,1,1,TO_TIMESTAMP('2017-09-11 13:35:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-11T13:35:52.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-11T13:36:13.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2017-09-11 13:36:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559879
;

-- 2017-09-11T13:36:13.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2017-09-11 13:36:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559881
;

-- 2017-09-11T13:36:13.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2017-09-11 13:36:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559600
;

-- 2017-09-11T13:36:13.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2017-09-11 13:36:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559601
;

-- 2017-09-11T13:36:13.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2017-09-11 13:36:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559599
;

-- 2017-09-11T13:36:13.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2017-09-11 13:36:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559606
;

-- 2017-09-11T13:36:24.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2017-09-11 13:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559879
;

-- 2017-09-11T13:36:24.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2017-09-11 13:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559881
;

-- 2017-09-11T13:36:24.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2017-09-11 13:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559600
;

-- 2017-09-11T13:36:24.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2017-09-11 13:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559601
;

-- 2017-09-11T13:36:24.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2017-09-11 13:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559599
;

-- 2017-09-11T13:36:24.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2017-09-11 13:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559606
;


---------------------------------------------------------


-- 2017-09-11T13:41:54.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557134,540010,540113,0,TO_TIMESTAMP('2017-09-11 13:41:54','YYYY-MM-DD HH24:MI:SS'),100,'yyyy-MM-dd','D','.','N',0,'Y','ContractStartDate_Contract Start Date',30,3,TO_TIMESTAMP('2017-09-11 13:41:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-11T13:42:07.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557135,540010,540114,0,TO_TIMESTAMP('2017-09-11 13:42:07','YYYY-MM-DD HH24:MI:SS'),100,'yyyy-MM-dd','D','.','N',0,'Y','Contract End Date',30,3,TO_TIMESTAMP('2017-09-11 13:42:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-11T13:42:12.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET Name='Contract Start Date',Updated=TO_TIMESTAMP('2017-09-11 13:42:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540113
;

-- 2017-09-11T13:42:44.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=80, StartNo=8,Updated=TO_TIMESTAMP('2017-09-11 13:42:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540113
;

-- 2017-09-11T13:42:52.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=90, StartNo=9,Updated=TO_TIMESTAMP('2017-09-11 13:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540114
;


---------------------------------------------------------

