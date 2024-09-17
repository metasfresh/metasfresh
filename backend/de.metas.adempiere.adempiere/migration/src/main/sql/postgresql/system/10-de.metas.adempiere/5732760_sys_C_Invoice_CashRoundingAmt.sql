-- 2024-09-09T15:26:31.507Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583245,0,'CashRoundingAmt',TO_TIMESTAMP('2024-09-09 18:26:31.37','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoice','Y','CashRoundingAmt','CashRoundingAmt',TO_TIMESTAMP('2024-09-09 18:26:31.37','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-09T15:26:31.513Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583245 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Invoice.CashRoundingAmt
-- 2024-09-09T15:27:02.573Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588946,583245,0,12,318,'CashRoundingAmt',TO_TIMESTAMP('2024-09-09 18:27:02.455','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.invoice',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'CashRoundingAmt',0,0,TO_TIMESTAMP('2024-09-09 18:27:02.455','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-09T15:27:02.574Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588946 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-09T15:27:02.577Z
/* DDL */  select update_Column_Translation_From_AD_Element(583245)
;

-- Column: C_Invoice.CashRoundingAmt
-- 2024-09-09T15:27:20.209Z
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2024-09-09 18:27:20.209','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588946
;

-- 2024-09-09T15:27:21.253Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN CashRoundingAmt NUMERIC DEFAULT 0 NOT NULL')
;

-- Column: C_Invoice.CashRoundingAmt
-- 2024-09-09T16:01:46.506Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-09-09 19:01:46.506','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588946
;

-- 2024-09-09T16:01:49.025Z
INSERT INTO t_alter_column values('c_invoice','CashRoundingAmt','NUMERIC',null,'0')
;

-- 2024-09-09T16:01:49.030Z
UPDATE C_Invoice SET CashRoundingAmt=0 WHERE CashRoundingAmt IS NULL
;
