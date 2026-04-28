-- 2018-03-07T17:23:10.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsForceIncludeInGeneratedModel,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (30,'N','N','N','N',0,'Y',100,542140,'Y','N','N','N','N','N','N','N','Y','N','N','N',322,'N','N','M_HU_ID','N',559545,'N','N','N','N','N','N',0,100,'Handling Units','de.metas.handlingunits','N',10,0,0,TO_TIMESTAMP('2018-03-07 17:23:10','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-03-07 17:23:10','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-07T17:23:10.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559545 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-07T17:23:21.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_InventoryLine','ALTER TABLE public.M_InventoryLine ADD COLUMN M_HU_ID NUMERIC(10)')
;

-- 2018-03-07T17:23:21.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_InventoryLine ADD CONSTRAINT MHU_MInventoryLine FOREIGN KEY (M_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;

