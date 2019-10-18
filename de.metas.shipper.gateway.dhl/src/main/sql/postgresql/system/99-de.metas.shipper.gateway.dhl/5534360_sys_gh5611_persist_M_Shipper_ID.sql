-- 2019-10-16T05:49:49.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='DHL_ShipmentOrder',Updated=TO_TIMESTAMP('2019-10-16 08:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541419
;

-- 2019-10-16T05:50:17.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569219,455,0,19,541419,'M_Shipper_ID',TO_TIMESTAMP('2019-10-16 08:50:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Methode oder Art der Warenlieferung','de.metas.shipper.gateway.dhl',10,'"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Lieferweg',0,0,TO_TIMESTAMP('2019-10-16 08:50:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-16T05:50:17.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569219 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-16T05:50:17.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(455) 
;

-- 2019-10-16T05:50:20.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DHL_ShipmentOrder','ALTER TABLE public.DHL_ShipmentOrder ADD COLUMN M_Shipper_ID NUMERIC(10)')
;

-- 2019-10-16T05:50:20.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE DHL_ShipmentOrder ADD CONSTRAINT MShipper_DHLShipmentOrder FOREIGN KEY (M_Shipper_ID) REFERENCES public.M_Shipper DEFERRABLE INITIALLY DEFERRED
;

