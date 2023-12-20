-- 2023-01-26T13:26:50.448Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581964,0,'FEC_Order_Currency_ID',TO_TIMESTAMP('2023-01-26 15:26:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Order Currency','Order Currency',TO_TIMESTAMP('2023-01-26 15:26:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T13:26:50.449Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581964 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_InOut.FEC_Order_Currency_ID
-- 2023-01-26T13:27:17.776Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585652,581964,0,30,112,319,'FEC_Order_Currency_ID',TO_TIMESTAMP('2023-01-26 15:27:17','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Order Currency',0,0,TO_TIMESTAMP('2023-01-26 15:27:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T13:27:17.777Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585652 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T13:27:17.780Z
/* DDL */  select update_Column_Translation_From_AD_Element(581964) 
;

-- 2023-01-26T13:27:20.873Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN FEC_Order_Currency_ID NUMERIC(10)')
;

-- 2023-01-26T13:27:21.368Z
ALTER TABLE M_InOut ADD CONSTRAINT FECOrderCurrency_MInOut FOREIGN KEY (FEC_Order_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_CurrencyRate
-- 2023-01-27T08:19:47.818Z
UPDATE AD_Process_Para SET SeqNo=60,Updated=TO_TIMESTAMP('2023-01-27 10:19:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542471
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_Order_Currency_ID
-- 2023-01-27T08:20:39.208Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581964,0,585192,542473,18,112,'FEC_Order_Currency_ID',TO_TIMESTAMP('2023-01-27 10:20:38','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','D',0,'Y','N','Y','N','N','N','Order Currency',50,TO_TIMESTAMP('2023-01-27 10:20:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T08:20:39.210Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542473 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-01-27T09:06:20.599Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581967,0,'FEC_From_Currency_ID',TO_TIMESTAMP('2023-01-27 11:06:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FEC Currency From','FEC Currency From',TO_TIMESTAMP('2023-01-27 11:06:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T09:06:20.613Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581967 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-01-27T09:06:48.236Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581968,0,'FEC_To_Currency_ID',TO_TIMESTAMP('2023-01-27 11:06:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FEC Currency To','FEC Currency To',TO_TIMESTAMP('2023-01-27 11:06:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T09:06:48.238Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581968 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_Order_Currency_ID
-- 2023-01-27T09:07:39.461Z
UPDATE AD_Process_Para SET SeqNo=35,Updated=TO_TIMESTAMP('2023-01-27 11:07:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542473
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_Order_Currency_ID
-- 2023-01-27T09:07:46.624Z
UPDATE AD_Process_Para SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-01-27 11:07:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542473
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_CurrencyRate
-- 2023-01-27T09:08:03.974Z
UPDATE AD_Process_Para SET SeqNo=70,Updated=TO_TIMESTAMP('2023-01-27 11:08:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542471
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_From_Currency_ID
-- 2023-01-27T09:08:36.203Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581967,0,585192,542474,18,112,'FEC_From_Currency_ID',TO_TIMESTAMP('2023-01-27 11:08:36','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','D',0,'Y','N','Y','N','N','N','FEC Currency From',50,TO_TIMESTAMP('2023-01-27 11:08:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T09:08:36.206Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542474 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_To_Currency_ID
-- 2023-01-27T09:09:25.562Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581968,0,585192,542475,30,'FEC_To_Currency_ID',TO_TIMESTAMP('2023-01-27 11:09:24','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','D',0,'Y','N','Y','N','N','N','FEC Currency To',60,TO_TIMESTAMP('2023-01-27 11:09:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T09:09:25.564Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542475 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Column: M_InOut.FEC_From_Currency_ID
-- 2023-01-27T09:43:55.437Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585653,581967,0,30,112,319,'FEC_From_Currency_ID',TO_TIMESTAMP('2023-01-27 11:43:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'FEC Currency From',0,0,TO_TIMESTAMP('2023-01-27 11:43:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-27T09:43:55.440Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585653 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-27T09:43:55.446Z
/* DDL */  select update_Column_Translation_From_AD_Element(581967) 
;

-- 2023-01-27T09:43:57.726Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN FEC_From_Currency_ID NUMERIC(10)')
;

-- 2023-01-27T09:43:58.260Z
ALTER TABLE M_InOut ADD CONSTRAINT FECFromCurrency_MInOut FOREIGN KEY (FEC_From_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_InOut.FEC_To_Currency_ID
-- 2023-01-27T09:44:20.820Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585654,581968,0,30,112,319,'FEC_To_Currency_ID',TO_TIMESTAMP('2023-01-27 11:44:20','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'FEC Currency To',0,0,TO_TIMESTAMP('2023-01-27 11:44:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-27T09:44:20.822Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585654 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-27T09:44:20.825Z
/* DDL */  select update_Column_Translation_From_AD_Element(581968) 
;

-- 2023-01-27T09:44:21.604Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN FEC_To_Currency_ID NUMERIC(10)')
;

-- 2023-01-27T09:44:22.099Z
ALTER TABLE M_InOut ADD CONSTRAINT FECToCurrency_MInOut FOREIGN KEY (FEC_To_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_To_Currency_ID
-- 2023-01-27T10:07:02.087Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=112,Updated=TO_TIMESTAMP('2023-01-27 12:07:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542475
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_To_Currency_ID
-- 2023-01-27T10:07:07.682Z
UPDATE AD_Process_Para SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2023-01-27 12:07:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542475
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_To_Currency_ID
-- 2023-01-27T10:07:13.919Z
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-01-27 12:07:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542475
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_From_Currency_ID
-- 2023-01-27T10:07:16.875Z
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-01-27 12:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542474
;

-- Column: M_InOut.IsFEC
-- 2023-01-27T10:55:59.463Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585656,581947,0,20,319,'IsFEC',TO_TIMESTAMP('2023-01-27 12:55:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'FEC',0,0,TO_TIMESTAMP('2023-01-27 12:55:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-27T10:55:59.465Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585656 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-27T10:55:59.470Z
/* DDL */  select update_Column_Translation_From_AD_Element(581947) 
;

-- 2023-01-27T10:56:00.298Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN IsFEC CHAR(1) DEFAULT ''N'' CHECK (IsFEC IN (''Y'',''N'')) NOT NULL')
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> FEC
-- Column: M_InOut.IsFEC
-- 2023-01-27T10:58:12.438Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585656,710682,0,296,TO_TIMESTAMP('2023-01-27 12:58:12','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','FEC',TO_TIMESTAMP('2023-01-27 12:58:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T10:58:12.440Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710682 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T10:58:12.442Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581947) 
;

-- 2023-01-27T10:58:12.447Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710682
;

-- 2023-01-27T10:58:12.448Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710682)
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> Order Currency
-- Column: M_InOut.FEC_Order_Currency_ID
-- 2023-01-27T10:58:26.850Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585652,710683,0,296,TO_TIMESTAMP('2023-01-27 12:58:26','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Order Currency',TO_TIMESTAMP('2023-01-27 12:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T10:58:26.852Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710683 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T10:58:26.854Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581964) 
;

-- 2023-01-27T10:58:26.859Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710683
;

-- 2023-01-27T10:58:26.860Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710683)
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> FEC Currency From
-- Column: M_InOut.FEC_From_Currency_ID
-- 2023-01-27T10:58:36.537Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585653,710684,0,296,TO_TIMESTAMP('2023-01-27 12:58:36','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Currency From',TO_TIMESTAMP('2023-01-27 12:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T10:58:36.539Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710684 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T10:58:36.541Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581967) 
;

-- 2023-01-27T10:58:36.545Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710684
;

-- 2023-01-27T10:58:36.546Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710684)
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> FEC Currency To
-- Column: M_InOut.FEC_To_Currency_ID
-- 2023-01-27T10:58:51.110Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585654,710685,0,296,TO_TIMESTAMP('2023-01-27 12:58:50','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Currency To',TO_TIMESTAMP('2023-01-27 12:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T10:58:51.112Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710685 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T10:58:51.113Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581968) 
;

-- 2023-01-27T10:58:51.117Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710685
;

-- 2023-01-27T10:58:51.118Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710685)
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> FEC
-- Column: M_InOut.IsFEC
-- 2023-01-27T10:59:29.219Z
UPDATE AD_Field SET DisplayLogic='1=2', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-27 12:59:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710682
;

-- UI Element: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 10 -> FEC.FEC
-- Column: M_InOut.IsFEC
-- 2023-01-27T11:00:08.480Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710682,0,296,550254,614881,'F',TO_TIMESTAMP('2023-01-27 13:00:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC',30,0,0,TO_TIMESTAMP('2023-01-27 13:00:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 10 -> FEC.FEC Currency From
-- Column: M_InOut.FEC_From_Currency_ID
-- 2023-01-27T11:00:28.754Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710684,0,296,550254,614882,'F',TO_TIMESTAMP('2023-01-27 13:00:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Currency From',40,0,0,TO_TIMESTAMP('2023-01-27 13:00:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 10 -> FEC.FEC Currency To
-- Column: M_InOut.FEC_To_Currency_ID
-- 2023-01-27T11:00:36.556Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710685,0,296,550254,614883,'F',TO_TIMESTAMP('2023-01-27 13:00:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Currency To',50,0,0,TO_TIMESTAMP('2023-01-27 13:00:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 10 -> FEC.FEC
-- Column: M_InOut.IsFEC
-- 2023-01-27T11:02:02.972Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2023-01-27 13:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614881
;

-- UI Element: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 10 -> FEC.Foreign Exchange Contract
-- Column: M_InOut.C_ForeignExchangeContract_ID
-- 2023-01-27T11:02:11.925Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-01-27 13:02:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614851
;

-- UI Element: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 10 -> FEC.FEC Currency Rate
-- Column: M_InOut.FEC_CurrencyRate
-- 2023-01-27T11:02:24.893Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2023-01-27 13:02:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614878
;

-- UI Element: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 10 -> FEC.Order Currency
-- Column: M_InOut.FEC_Order_Currency_ID
-- 2023-01-27T11:04:16.185Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710683,0,296,550254,614884,'F',TO_TIMESTAMP('2023-01-27 13:04:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Order Currency',70,0,0,TO_TIMESTAMP('2023-01-27 13:04:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 10 -> FEC.Order Currency
-- Column: M_InOut.FEC_Order_Currency_ID
-- 2023-01-27T11:04:24.923Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-01-27 13:04:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614884
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> FEC Currency To
-- Column: M_InOut.FEC_To_Currency_ID
-- 2023-01-27T11:04:58.240Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-27 13:04:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710685
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> FEC Currency From
-- Column: M_InOut.FEC_From_Currency_ID
-- 2023-01-27T11:05:00.871Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-27 13:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710684
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> FEC Currency From
-- Column: M_InOut.FEC_From_Currency_ID
-- 2023-01-27T11:05:03.801Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-27 13:05:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710684
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> FEC Currency To
-- Column: M_InOut.FEC_To_Currency_ID
-- 2023-01-27T11:05:05.116Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-27 13:05:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710685
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> Order Currency
-- Column: M_InOut.FEC_Order_Currency_ID
-- 2023-01-27T11:05:09.941Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-27 13:05:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710683
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> FEC Currency Rate
-- Column: M_InOut.FEC_CurrencyRate
-- 2023-01-27T11:05:16.362Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-27 13:05:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710680
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> Foreign Exchange Contract
-- Column: M_InOut.C_ForeignExchangeContract_ID
-- 2023-01-27T11:05:20.740Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-27 13:05:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710525
;

