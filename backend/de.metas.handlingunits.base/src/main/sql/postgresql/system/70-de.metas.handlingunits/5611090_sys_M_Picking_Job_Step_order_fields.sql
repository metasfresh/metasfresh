-- 2021-10-29T05:54:12.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578097,558,0,30,541908,'C_Order_ID',TO_TIMESTAMP('2021-10-29 08:54:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftrag','de.metas.handlingunits',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftrag',0,0,TO_TIMESTAMP('2021-10-29 08:54:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-29T05:54:12.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578097 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-29T05:54:12.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(558) 
;

-- 2021-10-29T05:54:16.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step','ALTER TABLE public.M_Picking_Job_Step ADD COLUMN C_Order_ID NUMERIC(10)')
;

-- 2021-10-29T05:54:16.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Picking_Job_Step ADD CONSTRAINT COrder_MPickingJobStep FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- 2021-10-29T05:54:37.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578098,561,0,30,541908,'C_OrderLine_ID',TO_TIMESTAMP('2021-10-29 08:54:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftragsposition','de.metas.handlingunits',0,10,'"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftragsposition',0,0,TO_TIMESTAMP('2021-10-29 08:54:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-29T05:54:37.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578098 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-29T05:54:37.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(561) 
;

-- 2021-10-29T05:54:41.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step','ALTER TABLE public.M_Picking_Job_Step ADD COLUMN C_OrderLine_ID NUMERIC(10)')
;

-- 2021-10-29T05:54:41.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Picking_Job_Step ADD CONSTRAINT COrderLine_MPickingJobStep FOREIGN KEY (C_OrderLine_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED
;

-- 2021-10-29T06:07:52.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-10-29 09:07:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578097
;

-- 2021-10-29T06:07:55.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-10-29 09:07:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578098
;

