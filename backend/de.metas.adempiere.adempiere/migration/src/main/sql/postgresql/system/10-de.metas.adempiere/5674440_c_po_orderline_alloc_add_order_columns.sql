-- Column: C_PO_OrderLine_Alloc.C_OrderPO_ID
-- 2023-01-31T08:21:50.911Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585670,2180,0,30,290,541863,'C_OrderPO_ID',TO_TIMESTAMP('2023-01-31 10:21:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Purchase Order','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Purchase Order',0,0,TO_TIMESTAMP('2023-01-31 10:21:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-31T08:21:50.925Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585670 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-31T08:21:51.020Z
/* DDL */  select update_Column_Translation_From_AD_Element(2180) 
;

-- 2023-01-31T08:22:03.394Z
/* DDL */ SELECT public.db_alter_table('C_PO_OrderLine_Alloc','ALTER TABLE public.C_PO_OrderLine_Alloc ADD COLUMN C_OrderPO_ID NUMERIC(10)')
;

-- 2023-01-31T08:22:03.669Z
ALTER TABLE C_PO_OrderLine_Alloc ADD CONSTRAINT COrderPO_CPOOrderLineAlloc FOREIGN KEY (C_OrderPO_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_PO_OrderLine_Alloc.C_OrderSO_ID
-- 2023-01-31T08:22:21.249Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585671,543479,0,30,290,541863,'C_OrderSO_ID',TO_TIMESTAMP('2023-01-31 10:22:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Order','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Order',0,0,TO_TIMESTAMP('2023-01-31 10:22:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-31T08:22:21.253Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585671 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-31T08:22:21.262Z
/* DDL */  select update_Column_Translation_From_AD_Element(543479) 
;

-- 2023-01-31T08:22:23.951Z
/* DDL */ SELECT public.db_alter_table('C_PO_OrderLine_Alloc','ALTER TABLE public.C_PO_OrderLine_Alloc ADD COLUMN C_OrderSO_ID NUMERIC(10)')
;

-- 2023-01-31T08:22:24.126Z
ALTER TABLE C_PO_OrderLine_Alloc ADD CONSTRAINT COrderSO_CPOOrderLineAlloc FOREIGN KEY (C_OrderSO_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

