-- 2018-01-09T18:32:02.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (30,'N','N','N','N',0,'Y',100,455,'N','N','N','N','N','N','N','Y','N','N','N',540890,'N','N','"Lieferweg" bezeichnet die Art der Warenlieferung.','M_Shipper_ID','N',558547,'N','Y','N','N','N','N','Methode oder Art der Warenlieferung',0,100,'Lieferweg','de.metas.shipper.gateway.go','N',10,0,0,TO_TIMESTAMP('2018-01-09 18:32:02','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-01-09 18:32:02','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T18:32:02.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558547 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-09T18:32:25.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('GO_DeliveryOrder','ALTER TABLE public.GO_DeliveryOrder ADD COLUMN M_Shipper_ID NUMERIC(10) NOT NULL')
;

-- 2018-01-09T18:32:25.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE GO_DeliveryOrder ADD CONSTRAINT MShipper_GODeliveryOrder FOREIGN KEY (M_Shipper_ID) REFERENCES public.M_Shipper DEFERRABLE INITIALLY DEFERRED
;

