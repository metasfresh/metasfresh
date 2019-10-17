-- 2018-10-12T18:22:43.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,IsCalculated,IsRangeFilter,IsShowFilterIncrementButtons,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType,ColumnName,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-12 18:22:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-12 18:22:43','YYYY-MM-DD HH24:MI:SS'),100,'N','N',105,'Das "System-Element" ermöglicht die zentrale  Verwaltung von Hilfe, Beschreibung und Terminologie für eine Tabellenspalte.',563263,'N','N','N','N','N','N','Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.',0,0,'N','N','N',106,'N','N','D','AD_Element_ID','System-Element')
;

-- 2018-10-12T18:22:43.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563263 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-12T18:54:16.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563263,569314,0,105,0,TO_TIMESTAMP('2018-10-12 18:54:16','YYYY-MM-DD HH24:MI:SS'),100,'Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.',0,'D','Das "System-Element" ermöglicht die zentrale  Verwaltung von Hilfe, Beschreibung und Terminologie für eine Tabellenspalte.',0,'Y','Y','Y','N','N','N','N','N','System-Element',25,25,0,1,1,TO_TIMESTAMP('2018-10-12 18:54:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-12T18:54:16.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569314 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

