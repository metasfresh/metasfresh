-- Column: M_ShipperTransportation.M_Delivery_Planning_ID
-- 2023-01-25T12:50:19.637Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585609,581677,0,19,540030,'M_Delivery_Planning_ID',TO_TIMESTAMP('2023-01-25 14:50:19','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferplanung',0,0,TO_TIMESTAMP('2023-01-25 14:50:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-25T12:50:19.639Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585609 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-25T12:50:19.663Z
/* DDL */  select update_Column_Translation_From_AD_Element(581677) 
;

-- 2023-01-25T12:50:43.690Z
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation ADD COLUMN M_Delivery_Planning_ID NUMERIC(10)')
;

-- 2023-01-25T12:50:43.759Z
ALTER TABLE M_ShipperTransportation ADD CONSTRAINT MDeliveryPlanning_MShipperTransportation FOREIGN KEY (M_Delivery_Planning_ID) REFERENCES public.M_Delivery_Planning DEFERRABLE INITIALLY DEFERRED
;

CREATE INDEX idx_M_ShipperTransportation_m_delivery_planning_id ON M_ShipperTransportation(m_delivery_planning_id);