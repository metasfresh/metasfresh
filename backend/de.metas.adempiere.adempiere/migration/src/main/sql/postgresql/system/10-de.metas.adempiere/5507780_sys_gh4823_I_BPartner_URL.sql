-- 2018-12-10T13:53:14.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,IsCalculated,IsRangeFilter,IsShowFilterIncrementButtons,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType,ColumnName,Name,Description,IsAutoApplyValidationRule) VALUES (10,120,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-12-10 13:53:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-12-10 13:53:14','YYYY-MM-DD HH24:MI:SS'),100,'N','N',533,'The URL defines an fully qualified web address like http://www.adempiere.org',563689,'N','N','N','N','N','N',0,0,'N','N','N',983,'N','N','D','URL','URL','Full URL address - e.g. http://www.adempiere.org','N')
;

-- 2018-12-10T13:53:14.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=563689 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
