-- Column: C_PurchaseCandidate.Price_UOM_ID
-- 2022-05-17T12:07:59.144Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582988,542464,0,18,114,540861,540472,'Price_UOM_ID',TO_TIMESTAMP('2022-05-17 15:07:58','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.purchasecandidate',0,10,'Y','N','Y','N','Y','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Preiseinheit',0,0,TO_TIMESTAMP('2022-05-17 15:07:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-17T12:07:59.146Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582988 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-17T12:07:59.196Z
/* DDL */  select update_Column_Translation_From_AD_Element(542464)
;

-- 2022-05-17T12:08:01.835Z
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN Price_UOM_ID NUMERIC(10)')
;

-- 2022-05-17T12:08:01.998Z
ALTER TABLE C_PurchaseCandidate ADD CONSTRAINT PriceUOM_CPurchaseCandidate FOREIGN KEY (Price_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Product_TaxCategory.C_Country_ID
-- 2022-05-17T12:59:02.035Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-05-17 15:59:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582925
;

-- 2022-05-17T12:59:06.802Z
INSERT INTO t_alter_column values('m_product_taxcategory','C_Country_ID','NUMERIC(10)',null,null)
;

-- 2022-05-17T12:59:06.823Z
INSERT INTO t_alter_column values('m_product_taxcategory','C_Country_ID',null,'NULL',null)
;

-- Column: C_PurchaseCandidate.Price_UOM_ID
-- 2022-05-18T11:26:25.932Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2022-05-18 14:26:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582988
;

-- 2022-05-18T11:26:29.298Z
INSERT INTO t_alter_column values('c_purchasecandidate','Price_UOM_ID','NUMERIC(10)',null,null)
;

-- Column: C_PurchaseCandidate.Price_UOM_ID
-- 2022-05-18T11:30:40.790Z
UPDATE AD_Column SET IsAutoApplyValidationRule='N',Updated=TO_TIMESTAMP('2022-05-18 14:30:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582988
;

-- 2022-05-18T11:30:44.514Z
INSERT INTO t_alter_column values('c_purchasecandidate','Price_UOM_ID','NUMERIC(10)',null,null)
;