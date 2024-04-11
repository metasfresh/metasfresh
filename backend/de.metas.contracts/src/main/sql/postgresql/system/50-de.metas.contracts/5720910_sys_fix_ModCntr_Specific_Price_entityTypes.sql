-- Run mode: SWING_CLIENT

-- Column: C_Invoice_Candidate.ModCntr_Module_ID
-- 2024-04-05T12:16:31.436Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588132,582426,0,19,540270,'ModCntr_Module_ID',TO_TIMESTAMP('2024-04-05 15:16:31.153','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bausteine',0,0,TO_TIMESTAMP('2024-04-05 15:16:31.153','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-05T12:16:31.446Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588132 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-05T12:16:31.480Z
/* DDL */  select update_Column_Translation_From_AD_Element(582426)
;

-- 2024-04-05T12:16:33.906Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN ModCntr_Module_ID NUMERIC(10)')
;

-- 2024-04-05T12:16:34.245Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT ModCntrModule_CInvoiceCandidate FOREIGN KEY (ModCntr_Module_ID) REFERENCES public.ModCntr_Module DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Specific_Price.C_Flatrate_Term_ID
-- 2024-04-05T12:39:34.116Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-04-05 15:39:34.116','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588097
;

-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-04-05T12:39:36.374Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-04-05 15:39:36.374','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588112
;

-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-04-05T12:39:39.509Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-04-05 15:39:39.509','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588099
;

-- Column: ModCntr_Specific_Price.AD_Client_ID
-- 2024-04-05T12:46:31.308Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:31.308','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588089
;

-- Column: ModCntr_Specific_Price.UpdatedBy
-- 2024-04-05T12:46:32.938Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:32.938','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588095
;

-- Column: ModCntr_Specific_Price.Updated
-- 2024-04-05T12:46:34.047Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:34.047','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588094
;

-- Column: ModCntr_Specific_Price.SeqNo
-- 2024-04-05T12:46:34.965Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:34.965','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588098
;

-- Column: ModCntr_Specific_Price.Price
-- 2024-04-05T12:46:35.881Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:35.881','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588101
;

-- Column: ModCntr_Specific_Price.M_Product_ID
-- 2024-04-05T12:46:36.750Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:36.75','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588100
;

-- Column: ModCntr_Specific_Price.ModCntr_Specific_Price_ID
-- 2024-04-05T12:46:37.746Z
UPDATE AD_Column SET EntityType='de.metas.contracts', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-05 15:46:37.746','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588096
;

-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-04-05T12:46:38.652Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:38.652','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588099
;

-- Column: ModCntr_Specific_Price.IsActive
-- 2024-04-05T12:46:40.270Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:40.27','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588093
;

-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-04-05T12:46:42.535Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:42.535','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588112
;

-- Column: ModCntr_Specific_Price.C_TaxCategory_ID
-- 2024-04-05T12:46:43.639Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:43.639','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588104
;

-- Column: ModCntr_Specific_Price.CreatedBy
-- 2024-04-05T12:46:44.539Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:44.539','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588092
;

-- Column: ModCntr_Specific_Price.Created
-- 2024-04-05T12:46:45.482Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:45.482','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588091
;

-- Column: ModCntr_Specific_Price.C_Flatrate_Term_ID
-- 2024-04-05T12:46:46.467Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:46.467','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588097
;

-- Column: ModCntr_Specific_Price.C_Currency_ID
-- 2024-04-05T12:46:47.468Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:47.468','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588103
;

-- Column: ModCntr_Specific_Price.AD_Org_ID
-- 2024-04-05T12:46:48.832Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-05 15:46:48.832','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588090
;


