-- 2020-09-21T05:12:19.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578117,0,'FilteredItemsPerCart',TO_TIMESTAMP('2020-09-21 07:12:19','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter','de.metas.inoutcandidate','Y','Filter-Anz. mit Kopfaggregationsmerkmal','Filter-Anz. mit Kopfaggregationsmerkmal',TO_TIMESTAMP('2020-09-21 07:12:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-21T05:12:19.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578117 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-09-21T05:12:23.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-21 07:12:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578117 AND AD_Language='de_CH'
;

-- 2020-09-21T05:12:23.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578117,'de_CH') 
;

-- 2020-09-21T05:12:25.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-21 07:12:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578117 AND AD_Language='de_DE'
;

-- 2020-09-21T05:12:25.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578117,'de_DE') 
;

-- 2020-09-21T05:12:25.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578117,'de_DE') 
;

-- 2020-09-21T05:12:51.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='FilteredItemsWithHeaderAggregationKey',Updated=TO_TIMESTAMP('2020-09-21 07:12:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578117
;

-- 2020-09-21T05:12:51.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FilteredItemsWithHeaderAggregationKey', Name='Filter-Anz. mit Kopfaggregationsmerkmal', Description='Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', Help=NULL WHERE AD_Element_ID=578117
;

-- 2020-09-21T05:12:51.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilteredItemsWithHeaderAggregationKey', Name='Filter-Anz. mit Kopfaggregationsmerkmal', Description='Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', Help=NULL, AD_Element_ID=578117 WHERE UPPER(ColumnName)='FILTEREDITEMSWITHHEADERAGGREGATIONKEY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-09-21T05:12:51.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilteredItemsWithHeaderAggregationKey', Name='Filter-Anz. mit Kopfaggregationsmerkmal', Description='Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', Help=NULL WHERE AD_Element_ID=578117 AND IsCentrallyMaintained='Y'
;

-- 2020-09-21T05:34:37.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='FilteredItemsWithSameHeaderAggregationKey',Updated=TO_TIMESTAMP('2020-09-21 07:34:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578117
;

-- 2020-09-21T05:34:37.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FilteredItemsWithSameHeaderAggregationKey', Name='Filter-Anz. mit Kopfaggregationsmerkmal', Description='Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', Help=NULL WHERE AD_Element_ID=578117
;

-- 2020-09-21T05:34:37.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilteredItemsWithSameHeaderAggregationKey', Name='Filter-Anz. mit Kopfaggregationsmerkmal', Description='Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', Help=NULL, AD_Element_ID=578117 WHERE UPPER(ColumnName)='FILTEREDITEMSWITHSAMEHEADERAGGREGATIONKEY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-09-21T05:34:37.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilteredItemsWithSameHeaderAggregationKey', Name='Filter-Anz. mit Kopfaggregationsmerkmal', Description='Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', Help=NULL WHERE AD_Element_ID=578117 AND IsCentrallyMaintained='Y'
;

-- 2020-09-21T05:37:26.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Lieferung gibt.',Updated=TO_TIMESTAMP('2020-09-21 07:37:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578117 AND AD_Language='de_DE'
;

-- 2020-09-21T05:37:26.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578117,'de_DE') 
;

-- 2020-09-21T05:37:26.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578117,'de_DE') 
;

-- 2020-09-21T05:37:26.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FilteredItemsWithSameHeaderAggregationKey', Name='Filter-Anz. mit Kopfaggregationsmerkmal', Description='Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Lieferung gibt.' WHERE AD_Element_ID=578117
;

-- 2020-09-21T05:37:26.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilteredItemsWithSameHeaderAggregationKey', Name='Filter-Anz. mit Kopfaggregationsmerkmal', Description='Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Lieferung gibt.', AD_Element_ID=578117 WHERE UPPER(ColumnName)='FILTEREDITEMSWITHSAMEHEADERAGGREGATIONKEY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-09-21T05:37:26.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilteredItemsWithSameHeaderAggregationKey', Name='Filter-Anz. mit Kopfaggregationsmerkmal', Description='Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Lieferung gibt.' WHERE AD_Element_ID=578117 AND IsCentrallyMaintained='Y'
;

-- 2020-09-21T05:37:26.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Filter-Anz. mit Kopfaggregationsmerkmal', Description='Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Lieferung gibt.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578117) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578117)
;

-- 2020-09-21T05:37:26.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Filter-Anz. mit Kopfaggregationsmerkmal', Description='Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Lieferung gibt.', CommitWarning = NULL WHERE AD_Element_ID = 578117
;

-- 2020-09-21T05:37:26.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Filter-Anz. mit Kopfaggregationsmerkmal', Description='Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Lieferung gibt.' WHERE AD_Element_ID = 578117
;

-- 2020-09-21T05:37:26.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Filter-Anz. mit Kopfaggregationsmerkmal', Description = 'Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578117
;

-- 2020-09-21T05:37:31.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Lieferung gibt.',Updated=TO_TIMESTAMP('2020-09-21 07:37:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578117 AND AD_Language='de_CH'
;

-- 2020-09-21T05:37:31.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578117,'de_CH') 
;

-- 2020-09-21T05:38:20.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Filtered-Count with header aggregation Key', PrintName='Filtered-Count with header aggregation Key',Updated=TO_TIMESTAMP('2020-09-21 07:38:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578117 AND AD_Language='en_US'
;

-- 2020-09-21T05:38:20.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578117,'en_US') 
;

-- 2020-09-21T05:42:05.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,571515,578117,0,11,500221,'FilteredItemsWithSameHeaderAggregationKey','count(1) over (partition by headeraggregationkey)',TO_TIMESTAMP('2020-09-21 07:42:05','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter','de.metas.inoutcandidate',0,14,'Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Lieferung gibt.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Filter-Anz. mit Kopfaggregationsmerkmal',0,0,'Lazy-Loading=N; we need it in the REST-API. Also I played with it and think it''s not a big performance-burden',TO_TIMESTAMP('2020-09-21 07:42:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-21T05:42:05.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571515 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-21T05:42:05.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578117) 
;

-- 2020-09-21T05:42:08.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2020-09-21 07:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571515
;

-- 2020-09-21T05:45:07.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571515,617675,0,500221,0,TO_TIMESTAMP('2020-09-21 07:45:07','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Datensätze mit dem selben Kopfaggregationsmerkmal, je nach aktuell angewendetem Filter',0,'de.metas.inoutcandidate','Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Lieferung gibt.',0,'Y','Y','Y','N','N','N','N','N','Filter-Anz. mit Kopfaggregationsmerkmal',620,690,0,1,1,TO_TIMESTAMP('2020-09-21 07:45:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-21T05:45:07.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=617675 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-21T05:45:07.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578117) 
;

-- 2020-09-21T05:45:07.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=617675
;

-- 2020-09-21T05:45:07.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(617675)
;

-- 2020-09-21T05:45:16.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2020-09-21 07:45:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=617675
;

