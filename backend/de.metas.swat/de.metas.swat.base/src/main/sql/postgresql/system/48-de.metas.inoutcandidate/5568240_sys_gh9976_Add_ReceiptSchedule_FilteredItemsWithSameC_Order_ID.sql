-- 2020-09-21T14:40:43.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578118,0,'FilteredItemsWithSameC_Order_ID',TO_TIMESTAMP('2020-09-21 16:40:43','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Datensätze aus der selben Bestellung, je nach aktuell angewendetem Filter','de.metas.inoutcandidate','Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden, zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.','Y','Filter-Anz. mit Bestell-ID','Filter-Anz. mit Bestell-ID',TO_TIMESTAMP('2020-09-21 16:40:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-21T14:40:43.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578118 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-09-21T14:41:15.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-21 16:41:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578118 AND AD_Language='de_CH'
;

-- 2020-09-21T14:41:15.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578118,'de_CH') 
;

-- 2020-09-21T14:41:28.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.', IsTranslated='Y', Name='Filter-Anz. mit Bestellung', PrintName='Filter-Anz. mit Bestellung',Updated=TO_TIMESTAMP('2020-09-21 16:41:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578118 AND AD_Language='de_DE'
;

-- 2020-09-21T14:41:28.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578118,'de_DE') 
;

-- 2020-09-21T14:41:28.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578118,'de_DE') 
;

-- 2020-09-21T14:41:28.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FilteredItemsWithSameC_Order_ID', Name='Filter-Anz. mit Bestellung', Description='Anzahl der Datensätze aus der selben Bestellung, je nach aktuell angewendetem Filter', Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.' WHERE AD_Element_ID=578118
;

-- 2020-09-21T14:41:28.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilteredItemsWithSameC_Order_ID', Name='Filter-Anz. mit Bestellung', Description='Anzahl der Datensätze aus der selben Bestellung, je nach aktuell angewendetem Filter', Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.', AD_Element_ID=578118 WHERE UPPER(ColumnName)='FILTEREDITEMSWITHSAMEC_ORDER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-09-21T14:41:28.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilteredItemsWithSameC_Order_ID', Name='Filter-Anz. mit Bestellung', Description='Anzahl der Datensätze aus der selben Bestellung, je nach aktuell angewendetem Filter', Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.' WHERE AD_Element_ID=578118 AND IsCentrallyMaintained='Y'
;

-- 2020-09-21T14:41:28.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Filter-Anz. mit Bestellung', Description='Anzahl der Datensätze aus der selben Bestellung, je nach aktuell angewendetem Filter', Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578118) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578118)
;

-- 2020-09-21T14:41:28.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Filter-Anz. mit Bestellung', Name='Filter-Anz. mit Bestellung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578118)
;

-- 2020-09-21T14:41:28.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Filter-Anz. mit Bestellung', Description='Anzahl der Datensätze aus der selben Bestellung, je nach aktuell angewendetem Filter', Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.', CommitWarning = NULL WHERE AD_Element_ID = 578118
;

-- 2020-09-21T14:41:28.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Filter-Anz. mit Bestellung', Description='Anzahl der Datensätze aus der selben Bestellung, je nach aktuell angewendetem Filter', Help='Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.' WHERE AD_Element_ID = 578118
;

-- 2020-09-21T14:41:28.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Filter-Anz. mit Bestellung', Description = 'Anzahl der Datensätze aus der selben Bestellung, je nach aktuell angewendetem Filter', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578118
;

-- 2020-09-21T14:41:40.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Filter-Anz. mit Bestellung', PrintName='Filter-Anz. mit Bestellung',Updated=TO_TIMESTAMP('2020-09-21 16:41:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578118 AND AD_Language='de_CH'
;

-- 2020-09-21T14:41:40.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578118,'de_CH') 
;

-- 2020-09-21T14:42:17.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Filtered-Count with order', PrintName='Filtered-Count with order',Updated=TO_TIMESTAMP('2020-09-21 16:42:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578118 AND AD_Language='en_US'
;

-- 2020-09-21T14:42:17.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578118,'en_US') 
;

-- 2020-09-21T14:43:06.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,571516,578118,0,11,540524,'FilteredItemsWithSameC_Order_ID','count(1) over (partition by c_order_id)',TO_TIMESTAMP('2020-09-21 16:43:06','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Anzahl der Datensätze aus der selben Bestellung, je nach aktuell angewendetem Filter','de.metas.inoutcandidate',0,10,'Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Filter-Anz. mit Bestellung',0,0,'Lazy-Loading=N; we need it in the REST-API. Also I played with it and think it''s not a big performance-burden',TO_TIMESTAMP('2020-09-21 16:43:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-21T14:43:06.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571516 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-21T14:43:06.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578118) 
;

-- 2020-09-21T14:46:45.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571516,617676,0,540526,0,TO_TIMESTAMP('2020-09-21 16:46:44','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Datensätze aus der selben Bestellung, je nach aktuell angewendetem Filter',0,'de.metas.inoutcandidate','Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.',0,'Y','N','N','N','N','N','N','N','Filter-Anz. mit Bestellung',460,200,0,1,1,TO_TIMESTAMP('2020-09-21 16:46:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-21T14:46:45.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=617676 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-21T14:46:45.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578118) 
;

-- 2020-09-21T14:46:45.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=617676
;

-- 2020-09-21T14:46:45.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(617676)
;

