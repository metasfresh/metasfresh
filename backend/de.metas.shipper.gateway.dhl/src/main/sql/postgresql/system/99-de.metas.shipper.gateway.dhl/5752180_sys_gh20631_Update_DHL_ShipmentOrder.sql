-- Column: DHL_ShipmentOrder.M_Package_ID
-- Column: DHL_ShipmentOrder.M_Package_ID
-- 2025-04-16T13:13:19.220Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589907,2410,0,30,541419,'XX','M_Package_ID',TO_TIMESTAMP('2025-04-16 13:13:18.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Shipment Package','de.metas.shipper.gateway.dhl',0,10,'A Shipment can have one or more Packages.  A Package may be individually tracked.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Packstück',0,0,TO_TIMESTAMP('2025-04-16 13:13:18.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-16T13:13:19.271Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589907 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-16T13:13:19.390Z
/* DDL */  select update_Column_Translation_From_AD_Element(2410) 
;

-- 2025-04-16T13:13:43.694Z
/* DDL */ SELECT public.db_alter_table('DHL_ShipmentOrder','ALTER TABLE public.DHL_ShipmentOrder ADD COLUMN M_Package_ID NUMERIC(10)')
;

-- 2025-04-16T13:13:43.754Z
ALTER TABLE DHL_ShipmentOrder ADD CONSTRAINT MPackage_DHLShipmentOrder FOREIGN KEY (M_Package_ID) REFERENCES public.M_Package DEFERRABLE INITIALLY DEFERRED
;

-- Column: DHL_ShipmentOrder.M_Package_ID
-- Column: DHL_ShipmentOrder.M_Package_ID
-- 2025-04-16T13:14:12.091Z
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2025-04-16 13:14:12.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589907
;
