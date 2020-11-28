-- 2018-09-11T14:03:11.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Belegdatum beim Fertigstellen aktualisieren', PrintName='Belegdatum beim Fertigstellen aktualisieren',Updated=TO_TIMESTAMP('2018-09-11 14:03:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53322
;

-- 2018-09-11T14:03:11.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsOverwriteDateOnComplete', Name='Belegdatum beim Fertigstellen aktualisieren', Description=NULL, Help=NULL WHERE AD_Element_ID=53322
;

-- 2018-09-11T14:03:11.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOverwriteDateOnComplete', Name='Belegdatum beim Fertigstellen aktualisieren', Description=NULL, Help=NULL, AD_Element_ID=53322 WHERE UPPER(ColumnName)='ISOVERWRITEDATEONCOMPLETE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-11T14:03:11.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOverwriteDateOnComplete', Name='Belegdatum beim Fertigstellen aktualisieren', Description=NULL, Help=NULL WHERE AD_Element_ID=53322 AND IsCentrallyMaintained='Y'
;

-- 2018-09-11T14:03:11.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Belegdatum beim Fertigstellen aktualisieren', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53322) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 53322)
;

-- 2018-09-11T14:03:11.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Belegdatum beim Fertigstellen aktualisieren', Name='Belegdatum beim Fertigstellen aktualisieren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53322)
;

-- 2018-09-11T14:03:16.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=53322 AND AD_Language='fr_CH'
;

-- 2018-09-11T14:03:18.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=53322 AND AD_Language='it_CH'
;

-- 2018-09-11T14:03:19.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=53322 AND AD_Language='en_GB'
;

-- 2018-09-11T14:03:27.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-11 14:03:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Belegdatum beim Fertigstellen aktualisieren',PrintName='Belegdatum beim Fertigstellen aktualisieren' WHERE AD_Element_ID=53322 AND AD_Language='de_CH'
;

-- 2018-09-11T14:03:27.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53322,'de_CH') 
;

-- 2018-09-11T14:04:08.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-11 14:04:08','YYYY-MM-DD HH24:MI:SS'),Name='Overwrite document date on complete',PrintName='Overwrite document date on complete' WHERE AD_Element_ID=53322 AND AD_Language='en_US'
;

-- 2018-09-11T14:04:08.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53322,'en_US') 
;

-- 2018-09-11T14:04:40.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Überschreibt das Belegdatum mit dem aktuellen Datum, wenn der Beleg fertig gestellt wird.',Updated=TO_TIMESTAMP('2018-09-11 14:04:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53322
;

-- 2018-09-11T14:04:40.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsOverwriteDateOnComplete', Name='Belegdatum beim Fertigstellen aktualisieren', Description='Überschreibt das Belegdatum mit dem aktuellen Datum, wenn der Beleg fertig gestellt wird.', Help=NULL WHERE AD_Element_ID=53322
;

-- 2018-09-11T14:04:40.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOverwriteDateOnComplete', Name='Belegdatum beim Fertigstellen aktualisieren', Description='Überschreibt das Belegdatum mit dem aktuellen Datum, wenn der Beleg fertig gestellt wird.', Help=NULL, AD_Element_ID=53322 WHERE UPPER(ColumnName)='ISOVERWRITEDATEONCOMPLETE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-11T14:04:40.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOverwriteDateOnComplete', Name='Belegdatum beim Fertigstellen aktualisieren', Description='Überschreibt das Belegdatum mit dem aktuellen Datum, wenn der Beleg fertig gestellt wird.', Help=NULL WHERE AD_Element_ID=53322 AND IsCentrallyMaintained='Y'
;

-- 2018-09-11T14:04:40.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Belegdatum beim Fertigstellen aktualisieren', Description='Überschreibt das Belegdatum mit dem aktuellen Datum, wenn der Beleg fertig gestellt wird.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53322) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 53322)
;

-- 2018-09-11T14:18:17.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Belegnummer beim Fertigstellen überschreiben', PrintName='Belegnummer beim Fertigstellen überschreiben',Updated=TO_TIMESTAMP('2018-09-11 14:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53320
;

-- 2018-09-11T14:18:17.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsOverwriteSeqOnComplete', Name='Belegnummer beim Fertigstellen überschreiben', Description=NULL, Help=NULL WHERE AD_Element_ID=53320
;

-- 2018-09-11T14:18:17.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOverwriteSeqOnComplete', Name='Belegnummer beim Fertigstellen überschreiben', Description=NULL, Help=NULL, AD_Element_ID=53320 WHERE UPPER(ColumnName)='ISOVERWRITESEQONCOMPLETE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-11T14:18:17.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOverwriteSeqOnComplete', Name='Belegnummer beim Fertigstellen überschreiben', Description=NULL, Help=NULL WHERE AD_Element_ID=53320 AND IsCentrallyMaintained='Y'
;

-- 2018-09-11T14:18:17.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Belegnummer beim Fertigstellen überschreiben', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53320) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 53320)
;

-- 2018-09-11T14:18:17.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Belegnummer beim Fertigstellen überschreiben', Name='Belegnummer beim Fertigstellen überschreiben' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53320)
;

-- 2018-09-11T14:18:20.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=53320 AND AD_Language='fr_CH'
;

-- 2018-09-11T14:18:22.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=53320 AND AD_Language='it_CH'
;

-- 2018-09-11T14:18:23.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=53320 AND AD_Language='en_GB'
;

-- 2018-09-11T14:18:35.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-11 14:18:35','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Belegnummer beim Fertigstellen überschreiben',PrintName='Belegnummer beim Fertigstellen überschreiben' WHERE AD_Element_ID=53320 AND AD_Language='de_CH'
;

-- 2018-09-11T14:18:35.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53320,'de_CH') 
;

-- 2018-09-11T14:59:30.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SpanX=2,Updated=TO_TIMESTAMP('2018-09-11 14:59:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=332
;

-- 2018-09-11T14:59:34.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SpanX=2,Updated=TO_TIMESTAMP('2018-09-11 14:59:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=333
;

-- 2018-09-11T14:59:50.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SpanX=2,Updated=TO_TIMESTAMP('2018-09-11 14:59:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=324
;

-- 2018-09-11T14:59:52.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SpanX=2,Updated=TO_TIMESTAMP('2018-09-11 14:59:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=325
;

-- 2018-09-11T15:25:52.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_JavaClass_Type (AD_Client_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Name,Updated,UpdatedBy) VALUES (0,540040,0,'de.metas.document.DocumentNumberProvider',TO_TIMESTAMP('2018-09-11 15:25:52','YYYY-MM-DD HH24:MI:SS'),100,'Can be invoked when a document number is generated','de.metas.document','de.metas.document.DocumentNumberProvider','Y','Document number provider',TO_TIMESTAMP('2018-09-11 15:25:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-11T15:53:17.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544252,0,'CustomSequenceNoProvider_JavaClass_ID',TO_TIMESTAMP('2018-09-11 15:53:17','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Abw. Sequenznummer-Implementierung','Abw. Sequenznummer-Implementierung',TO_TIMESTAMP('2018-09-11 15:53:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-11T15:53:17.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544252 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-09-11T15:53:29.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2018-09-11 15:53:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544252
;

-- 2018-09-11T15:53:34.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-11 15:53:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544252 AND AD_Language='de_CH'
;

-- 2018-09-11T15:53:34.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544252,'de_CH') 
;

-- 2018-09-11T15:54:02.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-11 15:54:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Custom sequence number provider',PrintName='Custom sequence number provider' WHERE AD_Element_ID=544252 AND AD_Language='en_US'
;

-- 2018-09-11T15:54:02.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544252,'en_US') 
;

-- 2018-09-11T15:55:45.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540407,'AD_JavaClass.AD_JavaClass_Type_ID=540040',TO_TIMESTAMP('2018-09-11 15:55:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_JavaClass_CustomSequenceNoProvider','S',TO_TIMESTAMP('2018-09-11 15:55:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-11T15:56:09.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560993,544252,0,18,540408,115,540407,'CustomSequenceNoProvider_JavaClass_ID',TO_TIMESTAMP('2018-09-11 15:56:09','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Abw. Sequenznummer-Implementierung',0,0,TO_TIMESTAMP('2018-09-11 15:56:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-09-11T15:56:09.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560993 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-11T15:56:11.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Sequence','ALTER TABLE public.AD_Sequence ADD COLUMN CustomSequenceNoProvider_JavaClass_ID NUMERIC(10)')
;

-- 2018-09-11T15:56:11.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Sequence ADD CONSTRAINT CustomSequenceNoProviderJavaClass_ADSequence FOREIGN KEY (CustomSequenceNoProvider_JavaClass_ID) REFERENCES public.AD_JavaClass DEFERRABLE INITIALLY DEFERRED
;

-- 2018-09-11T15:56:20.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2018-09-11 15:56:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=115
;

-- 2018-09-11T15:56:39.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_JavaClass_Type SET Classname='de.metas.document.CustomSequenceNoProvider', InternalName='de.metas.document.CustomSequenceNoProvider',Updated=TO_TIMESTAMP('2018-09-11 15:56:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JavaClass_Type_ID=540040
;

-- 2018-09-12T08:29:34.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_JavaClass_Type SET Classname='de.metas.document.sequenceno.CustomSequenceNoProvider', Description='Invoked when a document number is generated, to provide a custom value for the document number''s "sequence number" part.', InternalName='de.metas.document.sequenceno.CustomSequenceNoProvider', Name='Sequence number provider',Updated=TO_TIMESTAMP('2018-09-12 08:29:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JavaClass_Type_ID=540040
;

-- 2018-09-12T08:40:57.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_JavaClass (AD_Client_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,Description,EntityType,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,540049,540040,0,'de.metas.document.sequenceno.POReferenceAsSequenceNoProvider',TO_TIMESTAMP('2018-09-12 08:40:57','YYYY-MM-DD HH24:MI:SS'),100,'This class is applicable if the respective document model has a POReference column. In that case, it returns the trimmed POReference value','de.metas.document','Y','N','POReferenceAsSequenceNoProvider',TO_TIMESTAMP('2018-09-12 08:40:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-12T08:44:00.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_sequence','CustomSequenceNoProvider_JavaClass_ID','NUMERIC(10)',null,null)
;
