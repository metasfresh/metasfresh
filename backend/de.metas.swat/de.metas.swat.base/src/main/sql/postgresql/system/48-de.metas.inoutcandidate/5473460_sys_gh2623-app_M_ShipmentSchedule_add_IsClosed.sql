

-- 2017-10-02T17:42:42.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='', Help='',Updated=TO_TIMESTAMP('2017-10-02 17:42:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2723
;

-- 2017-10-02T17:42:42.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsClosed', Name='Geschlossen', Description='', Help='' WHERE AD_Element_ID=2723
;

-- 2017-10-02T17:42:42.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsClosed', Name='Geschlossen', Description='', Help='', AD_Element_ID=2723 WHERE UPPER(ColumnName)='ISCLOSED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-10-02T17:42:42.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsClosed', Name='Geschlossen', Description='', Help='' WHERE AD_Element_ID=2723 AND IsCentrallyMaintained='Y'
;

-- 2017-10-02T17:42:42.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geschlossen', Description='', Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2723) AND IsCentrallyMaintained='Y'
;

-- 2017-10-02T17:42:58.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557323,2723,0,20,500221,'N','IsClosed',TO_TIMESTAMP('2017-10-02 17:42:58','YYYY-MM-DD HH24:MI:SS'),100,'N','N','','de.metas.inoutcandidate',1,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Geschlossen',0,0,TO_TIMESTAMP('2017-10-02 17:42:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-10-02T17:42:58.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557323 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-02T17:43:48.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_shipmentschedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN IsClosed CHAR(1) DEFAULT ''N'' CHECK (IsClosed IN (''Y'',''N'')) NOT NULL')
;

-- 2017-10-02T17:45:04.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557323,560386,0,500221,0,TO_TIMESTAMP('2017-10-02 17:45:04','YYYY-MM-DD HH24:MI:SS'),100,'',0,'de.metas.inoutcandidate','',0,'Y','Y','Y','Y','N','N','N','N','N','Geschlossen',570,640,0,1,1,TO_TIMESTAMP('2017-10-02 17:45:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T17:45:04.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560386 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
