-- Name: C_Order SO, Completed/Closed
-- 2023-03-01T07:40:06.206Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540622,'DocStatus IN (''CO'',''CL'') AND IsSOtrx=''Y''',TO_TIMESTAMP('2023-03-01 09:40:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Order SO, Completed/Closed','S',TO_TIMESTAMP('2023-03-01 09:40:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_OrderLine.C_OrderSO_ID
-- 2023-03-01T07:40:32.855Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586272,543479,0,30,290,260,540622,'C_OrderSO_ID',TO_TIMESTAMP('2023-03-01 09:40:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Order','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Order',0,0,TO_TIMESTAMP('2023-03-01 09:40:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-01T07:40:32.857Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586272 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-01T07:40:32.888Z
/* DDL */  select update_Column_Translation_From_AD_Element(543479) 
;

-- 2023-03-01T07:40:38.183Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN C_OrderSO_ID NUMERIC(10)')
;

-- 2023-03-01T07:40:39.226Z
ALTER TABLE C_OrderLine ADD CONSTRAINT COrderSO_COrderLine FOREIGN KEY (C_OrderSO_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;










