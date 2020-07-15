-- 2018-01-26T15:15:42.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (30,'N','N','N','N',0,'Y',100,1689,'N','N','N','N','N','N','N','Y','N','N','N',808,'N','N','M_MatchInv_ID','N',558650,'N','N','N','N','N','N','Match Shipment/Receipt to Invoice',0,100,'Abgleich Rechnung','D','N',10,0,0,TO_TIMESTAMP('2018-01-26 15:15:42','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-01-26 15:15:42','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-26T15:15:42.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558650 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-26T15:15:47.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_CostDetail','ALTER TABLE public.M_CostDetail ADD COLUMN M_MatchInv_ID NUMERIC(10)')
;

-- 2018-01-26T15:15:47.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_CostDetail ADD CONSTRAINT MMatchInv_MCostDetail FOREIGN KEY (M_MatchInv_ID) REFERENCES public.M_MatchInv DEFERRABLE INITIALLY DEFERRED
;

