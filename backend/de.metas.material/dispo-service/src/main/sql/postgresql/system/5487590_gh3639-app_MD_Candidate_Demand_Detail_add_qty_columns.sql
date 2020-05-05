
-- 2018-03-07T13:24:22.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsGenericZoomOrigin,IsForceIncludeInGeneratedModel) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-03-07 13:24:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-03-07 13:24:22','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540815,'N','N','','PlannedQty',559532,'N','N','N','N','N','N','N','N','',0,0,'Geplante Menge',1568,'de.metas.material.dispo','N','N')
;

-- 2018-03-07T13:24:22.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559532 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-07T13:24:24.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Demand_Detail','ALTER TABLE public.MD_Candidate_Demand_Detail ADD COLUMN PlannedQty NUMERIC')
;

-- 2018-03-07T13:24:38.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsGenericZoomOrigin,IsForceIncludeInGeneratedModel) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-03-07 13:24:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-03-07 13:24:38','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540815,'N','N','ActualQty',559533,'N','N','N','N','N','N','N','N','',0,0,'Istmenge',1544,'de.metas.material.dispo','N','N')
;

-- 2018-03-07T13:24:38.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559533 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-07T13:24:40.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Demand_Detail','ALTER TABLE public.MD_Candidate_Demand_Detail ADD COLUMN ActualQty NUMERIC')
;

-- 2018-03-07T13:26:05.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558512,563091,0,540814,TO_TIMESTAMP('2018-03-07 13:26:05','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.material.dispo','Y','Y','N','N','N','N','N','Abo-Verlauf',TO_TIMESTAMP('2018-03-07 13:26:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-07T13:26:05.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563091 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-03-07T13:26:05.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559532,563092,0,540814,TO_TIMESTAMP('2018-03-07 13:26:05','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.material.dispo','','Y','Y','N','N','N','N','N','Geplante Menge',TO_TIMESTAMP('2018-03-07 13:26:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-07T13:26:05.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563092 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-03-07T13:26:05.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559533,563093,0,540814,TO_TIMESTAMP('2018-03-07 13:26:05','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.material.dispo','Y','Y','N','N','N','N','N','Istmenge',TO_TIMESTAMP('2018-03-07 13:26:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-07T13:26:05.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563093 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

