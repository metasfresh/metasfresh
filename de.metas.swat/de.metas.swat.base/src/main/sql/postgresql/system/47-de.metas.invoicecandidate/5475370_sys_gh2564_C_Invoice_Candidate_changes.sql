-- 2017-10-25T10:21:20.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-10-25 10:21:20','YYYY-MM-DD HH24:MI:SS'),100,543458,'Y','N','N','N','Y','N',540270,'IsGroupCompensationLine',557788,'Y',0,TO_TIMESTAMP('2017-10-25 10:21:20','YYYY-MM-DD HH24:MI:SS'),100,'Group Compensation Line','de.metas.invoicecandidate')
;

-- 2017-10-25T10:21:20.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557788 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-25T10:21:20.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (17,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-10-25 10:21:20','YYYY-MM-DD HH24:MI:SS'),100,543461,'Y','N','N','N','Y','N',540270,540758,'GroupCompensationType',557789,'N',0,TO_TIMESTAMP('2017-10-25 10:21:20','YYYY-MM-DD HH24:MI:SS'),100,'Compensation Type','de.metas.invoicecandidate')
;

-- 2017-10-25T10:21:20.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557789 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-25T10:21:21.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (17,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-10-25 10:21:20','YYYY-MM-DD HH24:MI:SS'),100,543462,'Y','N','N','N','Y','N',540270,540759,'GroupCompensationAmtType',557790,'N',0,TO_TIMESTAMP('2017-10-25 10:21:20','YYYY-MM-DD HH24:MI:SS'),100,'Compensation Amount Type','de.metas.invoicecandidate')
;

-- 2017-10-25T10:21:21.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557790 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-25T10:21:21.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (22,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-10-25 10:21:21','YYYY-MM-DD HH24:MI:SS'),100,543460,'Y','N','N','N','Y','N',540270,'GroupCompensationPercentage',557791,'N',0,TO_TIMESTAMP('2017-10-25 10:21:21','YYYY-MM-DD HH24:MI:SS'),100,'Compensation percentage','de.metas.invoicecandidate')
;

-- 2017-10-25T10:21:21.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557791 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-25T10:21:21.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (12,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-10-25 10:21:21','YYYY-MM-DD HH24:MI:SS'),100,543464,'Y','N','N','N','Y','N',540270,'GroupCompensationBaseAmt',557792,'N','Base amount for calculating percentage group compensation',0,TO_TIMESTAMP('2017-10-25 10:21:21','YYYY-MM-DD HH24:MI:SS'),100,'Compensation base amount','de.metas.invoicecandidate')
;

-- 2017-10-25T10:21:21.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557792 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-25T10:24:45.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN IsGroupCompensationLine CHAR(1) DEFAULT ''N'' CHECK (IsGroupCompensationLine IN (''Y'',''N'')) NOT NULL')
;

-- 2017-10-25T10:29:55.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN GroupCompensationAmtType VARCHAR(10)')
;

-- 2017-10-25T10:30:01.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN GroupCompensationBaseAmt NUMERIC')
;

-- 2017-10-25T10:30:08.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN GroupCompensationPercentage NUMERIC')
;

-- 2017-10-25T10:30:18.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN GroupCompensationType CHAR(1)')
;

-- 2017-10-25T11:35:02.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,IsCentrallyMaintained,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (540279,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2017-10-25 11:35:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',560416,557788,0,'Group Compensation Line','de.metas.invoicecandidate',TO_TIMESTAMP('2017-10-25 11:35:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-25T11:35:02.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560416 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-25T11:35:19.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,IsCentrallyMaintained,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (540279,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2017-10-25 11:35:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',560417,557790,0,'Compensation Amount Type','de.metas.invoicecandidate',TO_TIMESTAMP('2017-10-25 11:35:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-25T11:35:19.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560417 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-25T11:35:29.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,IsCentrallyMaintained,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (540279,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2017-10-25 11:35:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',560418,557792,'Base amount for calculating percentage group compensation',0,'Compensation base amount','de.metas.invoicecandidate',TO_TIMESTAMP('2017-10-25 11:35:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-25T11:35:29.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560418 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-25T11:35:43.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,IsCentrallyMaintained,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (540279,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2017-10-25 11:35:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',560419,557791,0,'Compensation percentage','de.metas.invoicecandidate',TO_TIMESTAMP('2017-10-25 11:35:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-25T11:35:43.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560419 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-25T11:35:56.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,IsCentrallyMaintained,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (540279,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2017-10-25 11:35:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',560420,557789,0,'Compensation Type','de.metas.invoicecandidate',TO_TIMESTAMP('2017-10-25 11:35:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-25T11:35:56.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560420 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-25T11:36:31.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2017-10-25 11:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560416
;

-- 2017-10-25T11:36:35.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2017-10-25 11:36:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560419
;

-- 2017-10-25T11:36:39.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-10-25 11:36:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560416
;

-- 2017-10-25T11:36:46.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2017-10-25 11:36:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560417
;

-- 2017-10-25T11:36:51.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2017-10-25 11:36:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560418
;

-- 2017-10-25T11:36:57.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2017-10-25 11:36:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560420
;

-- 2017-10-25T11:38:47.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (11,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-10-25 11:38:47','YYYY-MM-DD HH24:MI:SS'),100,543453,'Y','N','N','N','N','N','Y','N','N','N',540270,'N','N','GroupNo','N',557794,'N','N','N','N','N','N',0,0,TO_TIMESTAMP('2017-10-25 11:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Group','de.metas.invoicecandidate')
;

-- 2017-10-25T11:38:47.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557794 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-25T11:38:52.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN GroupNo NUMERIC(10)')
;

-- 2017-10-25T11:39:07.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,IsCentrallyMaintained,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (540279,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2017-10-25 11:39:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',560421,557794,0,'Group','de.metas.invoicecandidate',TO_TIMESTAMP('2017-10-25 11:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-25T11:39:07.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560421 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-25T11:39:24.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2017-10-25 11:39:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560421
;

