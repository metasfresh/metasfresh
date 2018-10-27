-- 2018-10-05T17:12:32.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,TechnicalNote,IsGenericZoomOrigin,ColumnName,Name) VALUES (15,7,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-05 17:12:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-05 17:12:32','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540244,'N','"Rechnungsdatum" bezeichnet das auf der Rechnung verwendete Datum.',563247,'N','N','N','N','N','N','N','N','Datum auf der Rechnung',0,0,267,'de.metas.ordercandidate','N','This column is used if dataDestInternalName is DEST.de.metas.invoicecandidate','N','DateInvoiced','Rechnungsdatum')
;

-- 2018-10-05T17:12:32.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563247 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-05T17:12:44.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='This column is used if AD_DataDestination_ID is DEST.de.metas.invoicecandidate.',Updated=TO_TIMESTAMP('2018-10-05 17:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563247
;

-- 2018-10-05T17:12:46.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN DateInvoiced TIMESTAMP WITHOUT TIME ZONE')
;

-- 2018-10-05T17:14:09.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_olcand','DateInvoiced','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

