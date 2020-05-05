-- 2019-02-14T20:17:07.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564181,576037,0,30,541169,'DataEntry_SubGroup_ID',TO_TIMESTAMP('2019-02-14 20:17:06','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.dataentry',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Untergruppe',0,0,TO_TIMESTAMP('2019-02-14 20:17:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-02-14T20:17:07.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564181 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T20:17:19.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-02-14 20:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564181
;

-- 2019-02-14T20:17:23.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DataEntry_Record','ALTER TABLE public.DataEntry_Record ADD COLUMN DataEntry_SubGroup_ID NUMERIC(10) NOT NULL')
;

-- 2019-02-14T20:17:23.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE DataEntry_Record ADD CONSTRAINT DataEntrySubGroup_DataEntryRecord FOREIGN KEY (DataEntry_SubGroup_ID) REFERENCES public.DataEntry_SubGroup DEFERRABLE INITIALLY DEFERRED
;

/* DDL */ SELECT public.db_alter_table('DataEntry_Record','ALTER TABLE public.DataEntry_Record DROP COLUMN DataEntry_Group_ID')
;
-- 2019-02-15T08:43:08.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564140
;

-- 2019-02-15T08:43:08.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564140
;

-- 2019-02-15T11:04:23.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=563982
;

-- 2019-02-15T11:04:23.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=563982
;

-- 2019-02-15T11:04:28.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=576044
;

-- 2019-02-15T11:04:28.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=576044
;

-- 2019-02-15T11:04:49.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=541170
;

-- 2019-02-15T11:04:49.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=541170
;

/* DDL */ SELECT public.db_alter_table('DataEntry_Record_Assignment','DROP TABLE DataEntry_Record_Assignment')
;
