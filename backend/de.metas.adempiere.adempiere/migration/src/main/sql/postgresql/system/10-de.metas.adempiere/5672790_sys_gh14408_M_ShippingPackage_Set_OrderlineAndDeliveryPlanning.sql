-- Column: M_ShippingPackage.C_OrderLine_ID
-- 2023-01-20T11:36:52.724Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585599,561,0,30,540031,'C_OrderLine_ID',TO_TIMESTAMP('2023-01-20 13:36:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftragsposition','METAS_SHIPPING',0,10,'"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftragsposition',0,0,TO_TIMESTAMP('2023-01-20 13:36:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-20T11:36:52.727Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585599 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-20T11:36:52.756Z
/* DDL */  select update_Column_Translation_From_AD_Element(561) 
;

-- 2023-01-20T11:36:53.898Z
/* DDL */ SELECT public.db_alter_table('M_ShippingPackage','ALTER TABLE public.M_ShippingPackage ADD COLUMN C_OrderLine_ID NUMERIC(10)')
;

-- 2023-01-20T11:36:54.078Z
ALTER TABLE M_ShippingPackage ADD CONSTRAINT COrderLine_MShippingPackage FOREIGN KEY (C_OrderLine_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_ShippingPackage.M_Delivery_Planning_ID
-- 2023-01-20T11:37:07.757Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585600,581677,0,30,540031,'M_Delivery_Planning_ID',TO_TIMESTAMP('2023-01-20 13:37:07','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferplanung',0,0,TO_TIMESTAMP('2023-01-20 13:37:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-20T11:37:07.759Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585600 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-20T11:37:07.761Z
/* DDL */  select update_Column_Translation_From_AD_Element(581677) 
;

-- 2023-01-20T11:37:08.507Z
/* DDL */ SELECT public.db_alter_table('M_ShippingPackage','ALTER TABLE public.M_ShippingPackage ADD COLUMN M_Delivery_Planning_ID NUMERIC(10)')
;

-- 2023-01-20T11:37:08.570Z
ALTER TABLE M_ShippingPackage ADD CONSTRAINT MDeliveryPlanning_MShippingPackage FOREIGN KEY (M_Delivery_Planning_ID) REFERENCES public.M_Delivery_Planning DEFERRABLE INITIALLY DEFERRED
;















