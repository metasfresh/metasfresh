-- 2023-06-28T06:56:29Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582515,0,'Sales_Invoice_ID',TO_TIMESTAMP('2023-06-28 09:56:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Sales Invoice','Sales Invoice',TO_TIMESTAMP('2023-06-28 09:56:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-28T06:56:29.002Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582515 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-06-28T06:56:45.206Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582516,0,'Purchase_Invoice_ID',TO_TIMESTAMP('2023-06-28 09:56:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Purchase Invoice','Purchase Invoice',TO_TIMESTAMP('2023-06-28 09:56:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-28T06:56:45.207Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582516 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: GPLR_Report.Sales_Invoice_ID
-- 2023-06-28T06:57:00.725Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587012,582515,0,30,336,542341,'XX','Sales_Invoice_ID',TO_TIMESTAMP('2023-06-28 09:57:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Sales Invoice',0,0,TO_TIMESTAMP('2023-06-28 09:57:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-28T06:57:00.727Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587012 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-28T06:57:01.230Z
/* DDL */  select update_Column_Translation_From_AD_Element(582515) 
;

-- 2023-06-28T06:57:02.886Z
/* DDL */ SELECT public.db_alter_table('GPLR_Report','ALTER TABLE public.GPLR_Report ADD COLUMN Sales_Invoice_ID NUMERIC(10) NOT NULL')
;

-- 2023-06-28T06:57:02.896Z
ALTER TABLE GPLR_Report ADD CONSTRAINT SalesInvoice_GPLRReport FOREIGN KEY (Sales_Invoice_ID) REFERENCES public.C_Invoice DEFERRABLE INITIALLY DEFERRED
;

-- Column: GPLR_Report.Purchase_Invoice_ID
-- 2023-06-28T06:57:21.291Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587013,582516,0,30,336,542341,'XX','Purchase_Invoice_ID',TO_TIMESTAMP('2023-06-28 09:57:21','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Purchase Invoice',0,0,TO_TIMESTAMP('2023-06-28 09:57:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-28T06:57:21.293Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587013 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-28T06:57:21.844Z
/* DDL */  select update_Column_Translation_From_AD_Element(582516) 
;

-- 2023-06-28T06:57:22.622Z
/* DDL */ SELECT public.db_alter_table('GPLR_Report','ALTER TABLE public.GPLR_Report ADD COLUMN Purchase_Invoice_ID NUMERIC(10) NOT NULL')
;

-- 2023-06-28T06:57:22.628Z
ALTER TABLE GPLR_Report ADD CONSTRAINT PurchaseInvoice_GPLRReport FOREIGN KEY (Purchase_Invoice_ID) REFERENCES public.C_Invoice DEFERRABLE INITIALLY DEFERRED
;

-- 2023-06-28T06:57:37.551Z
/* DDL */ SELECT public.db_alter_table('GPLR_Report','ALTER TABLE GPLR_Report DROP COLUMN IF EXISTS C_Invoice_ID')
;

-- Column: GPLR_Report.C_Invoice_ID
-- 2023-06-28T06:57:37.639Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586866
;

-- 2023-06-28T06:57:37.648Z
DELETE FROM AD_Column WHERE AD_Column_ID=586866
;

