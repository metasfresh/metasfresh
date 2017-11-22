-- 2017-11-22T16:41:35.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557885,459,0,19,720,189,'N','M_Warehouse_ID',TO_TIMESTAMP('2017-11-22 16:41:35','YYYY-MM-DD HH24:MI:SS'),100,'N','@M_Warehouse_ID@','Lager oder Ort für Dienstleistung','D',22,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Lager',0,0,TO_TIMESTAMP('2017-11-22 16:41:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-22T16:41:35.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557885 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-22T16:43:22.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557886,206,0,18,540658,720,199,'N','C_Period_ID',TO_TIMESTAMP('2017-11-22 16:43:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Periode des Kalenders','D',10,'"Periode" bezeichnet einen eklusiven Datumsbereich eines Kalenders.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Periode',0,0,TO_TIMESTAMP('2017-11-22 16:43:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-22T16:43:22.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557886 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-22T16:43:52.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557887,269,0,16,720,'N','DatePromised',TO_TIMESTAMP('2017-11-22 16:43:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Zugesagter Termin für diesen Auftrag','D',7,'Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Zugesagter Termin',0,0,TO_TIMESTAMP('2017-11-22 16:43:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-22T16:43:52.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557887 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-22T16:44:22.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=22,Updated=TO_TIMESTAMP('2017-11-22 16:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557886
;

-- 2017-11-22T16:44:56.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557888,187,0,19,720,'N','C_BPartner_ID',TO_TIMESTAMP('2017-11-22 16:44:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','D',22,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Geschäftspartner',0,0,TO_TIMESTAMP('2017-11-22 16:44:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-22T16:44:56.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557888 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-22T17:05:12.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-11-22 17:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557887
;

-- 2017-11-22T17:05:17.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Forecast','ALTER TABLE public.M_Forecast ADD COLUMN DatePromised TIMESTAMP WITH TIME ZONE')
;

-- 2017-11-22T17:05:29.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Forecast','ALTER TABLE public.M_Forecast ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2017-11-22T17:05:29.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Forecast ADD CONSTRAINT CBPartner_MForecast FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2017-11-22T17:05:44.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-11-22 17:05:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557886
;

-- 2017-11-22T17:05:46.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Forecast','ALTER TABLE public.M_Forecast ADD COLUMN C_Period_ID NUMERIC(10)')
;

-- 2017-11-22T17:05:46.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Forecast ADD CONSTRAINT CPeriod_MForecast FOREIGN KEY (C_Period_ID) REFERENCES public.C_Period DEFERRABLE INITIALLY DEFERRED
;

-- 2017-11-22T17:05:54.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Forecast','ALTER TABLE public.M_Forecast ADD COLUMN M_Warehouse_ID NUMERIC(10)')
;

-- 2017-11-22T17:05:54.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Forecast ADD CONSTRAINT MWarehouse_MForecast FOREIGN KEY (M_Warehouse_ID) REFERENCES public.M_Warehouse DEFERRABLE INITIALLY DEFERRED
;

