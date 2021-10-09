-- 2017-10-05T17:41:37.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557365,2499,0,30,540815,'N','M_ForecastLine_ID',TO_TIMESTAMP('2017-10-05 17:41:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Prognose-Position','de.metas.material.dispo',10,'Forecast of Product Qyantity by Period','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Prognose-Position',0,0,TO_TIMESTAMP('2017-10-05 17:41:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-10-05T17:41:37.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557365 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-05T17:41:43.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Demand_Detail','ALTER TABLE public.MD_Candidate_Demand_Detail ADD COLUMN M_ForecastLine_ID NUMERIC(10)')
;

-- 2017-10-05T17:41:43.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE MD_Candidate_Demand_Detail ADD CONSTRAINT MForecastLine_MDCandidateDeman FOREIGN KEY (M_ForecastLine_ID) REFERENCES public.M_ForecastLine DEFERRABLE INITIALLY DEFERRED
;
