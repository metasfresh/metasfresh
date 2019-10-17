-- 2019-08-01T14:14:28.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568549,576953,0,18,114,540542,'Catch_UOM_ID',TO_TIMESTAMP('2019-08-01 16:14:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Aus dem Produktstamm übenommene Catch Weight Einheit.','de.metas.inoutcandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Catch Einheit',0,0,TO_TIMESTAMP('2019-08-01 16:14:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-08-01T14:14:28.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568549 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-08-01T14:14:28.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576953) 
;

-- 2019-08-01T14:14:32.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule_QtyPicked','ALTER TABLE public.M_ShipmentSchedule_QtyPicked ADD COLUMN Catch_UOM_ID NUMERIC(10)')
;

-- 2019-08-01T14:14:32.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_ShipmentSchedule_QtyPicked ADD CONSTRAINT CatchUOM_MShipmentScheduleQtyPicked FOREIGN KEY (Catch_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- 2019-08-01T14:15:07.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule_qtypicked','Catch_UOM_ID','NUMERIC(10)',null,null)
;

