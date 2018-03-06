-- 2018-03-06T10:43:29.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543921,0,'TechnicalNote',TO_TIMESTAMP('2018-03-06 10:43:29','YYYY-MM-DD HH24:MI:SS'),100,'A note that is not indended for the user documentation, but for developers, customizers etc','D','Y','TechnicalNote','TechnicalNote',TO_TIMESTAMP('2018-03-06 10:43:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-06T10:43:29.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543921 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-03-06T10:43:47.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559529,543921,0,14,101,'N','TechnicalNote',TO_TIMESTAMP('2018-03-06 10:43:47','YYYY-MM-DD HH24:MI:SS'),100,'N','A note that is not indended for the user documentation, but for developers, customizers etc','D',10000,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','TechnicalNote',0,0,TO_TIMESTAMP('2018-03-06 10:43:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-03-06T10:43:47.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559529 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-06T10:43:52.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE public.AD_Column ADD COLUMN TechnicalNote TEXT')
;

-- 2018-03-06T10:44:48.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-03-06 10:44:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=154
;

-- 2018-03-06T10:45:08.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559529,563078,0,101,0,TO_TIMESTAMP('2018-03-06 10:45:07','YYYY-MM-DD HH24:MI:SS'),100,'A note that is not indended for the user documentation, but for developers, customizers etc',0,'D',0,'Y','Y','Y','N','N','N','N','N','TechnicalNote',101,111,0,1,1,TO_TIMESTAMP('2018-03-06 10:45:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-06T10:45:08.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-03-06T10:46:01.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SpanX=3,Updated=TO_TIMESTAMP('2018-03-06 10:46:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563078
;

-- 2018-03-06T10:46:21.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SpanX=999,Updated=TO_TIMESTAMP('2018-03-06 10:46:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563078
;

-- 2018-03-06T10:50:16.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='Y', TechnicalNote='When this record is crerated, the actual AD_Client_ID of the event-handling context is not yet known.
Therefore I made this column updatable, so it can be updated from AD_EventLog_Entries when they are created.',Updated=TO_TIMESTAMP('2018-03-06 10:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558405
;

-- 2018-03-06T10:50:38.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='Y', TechnicalNote='When this record is crerated, the actual AD_Org_ID of the event-handling context is not yet known.
Therefore I made this column updatable, so it can be updated from AD_EventLog_Entries when they are created.',Updated=TO_TIMESTAMP('2018-03-06 10:50:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558407
;

-- 2018-03-06T11:05:21.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Technical note', PrintName='Technical note',Updated=TO_TIMESTAMP('2018-03-06 11:05:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543921
;

-- 2018-03-06T11:05:21.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TechnicalNote', Name='Technical note', Description='A note that is not indended for the user documentation, but for developers, customizers etc', Help=NULL WHERE AD_Element_ID=543921
;

-- 2018-03-06T11:05:21.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TechnicalNote', Name='Technical note', Description='A note that is not indended for the user documentation, but for developers, customizers etc', Help=NULL, AD_Element_ID=543921 WHERE UPPER(ColumnName)='TECHNICALNOTE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-03-06T11:05:21.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TechnicalNote', Name='Technical note', Description='A note that is not indended for the user documentation, but for developers, customizers etc', Help=NULL WHERE AD_Element_ID=543921 AND IsCentrallyMaintained='Y'
;

-- 2018-03-06T11:05:21.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Technical note', Description='A note that is not indended for the user documentation, but for developers, customizers etc', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543921) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543921)
;

-- 2018-03-06T11:05:21.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Technical note', Name='Technical note' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543921)
;

