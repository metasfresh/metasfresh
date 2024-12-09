-- 2023-06-19T16:34:30.291Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582450,0,'UserElementDate1',TO_TIMESTAMP('2023-06-19 19:34:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','UserElementDate1','UserElementDate1',TO_TIMESTAMP('2023-06-19 19:34:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-19T16:34:30.302Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582450 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-06-19T16:34:40.761Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582451,0,'UserElementDate2',TO_TIMESTAMP('2023-06-19 19:34:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','UserElementDate2','UserElementDate2',TO_TIMESTAMP('2023-06-19 19:34:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-19T16:34:40.767Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582451 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Invoice_Candidate.UserElementDate1
-- 2023-06-19T16:45:26.464Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586824,582450,0,15,540270,'UserElementDate1',TO_TIMESTAMP('2023-06-19 19:45:26','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vom Datum',0,0,TO_TIMESTAMP('2023-06-19 19:45:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-19T16:45:26.465Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586824 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-19T16:45:26.470Z
/* DDL */  select update_Column_Translation_From_AD_Element(582450) 
;

-- Column: C_Invoice_Candidate.DateToInvoice_Override
-- 2023-06-19T16:45:44.023Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-06-19 19:45:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546340
;

-- Column: C_Invoice_Candidate.UserElementDate2
-- 2023-06-19T16:46:18.011Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586825,582451,0,15,540270,'UserElementDate2',TO_TIMESTAMP('2023-06-19 19:46:17','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bis Datum',0,0,TO_TIMESTAMP('2023-06-19 19:46:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-19T16:46:18.013Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586825 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-19T16:46:18.016Z
/* DDL */  select update_Column_Translation_From_AD_Element(582451) 
;

-- 2023-06-19T16:46:20.429Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN UserElementDate2 TIMESTAMP WITHOUT TIME ZONE')
;

-- 2023-06-19T16:46:29.621Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN UserElementDate1 TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: C_Invoice_Candidate.UserElementDate1
-- 2023-06-19T17:28:02.320Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2023-06-19 20:28:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586824
;

-- Column: C_InvoiceLine.UserElementDate1
-- 2023-06-19T17:29:11.604Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586826,582450,0,15,333,'UserElementDate1',TO_TIMESTAMP('2023-06-19 20:29:11','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vom Datum',0,0,TO_TIMESTAMP('2023-06-19 20:29:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-19T17:29:11.611Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586826 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-19T17:29:11.623Z
/* DDL */  select update_Column_Translation_From_AD_Element(582450) 
;

-- 2023-06-19T17:29:13.149Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN UserElementDate1 TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: C_InvoiceLine.UserElementDate2
-- 2023-06-19T17:29:31.244Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586827,582451,0,15,333,'UserElementDate2',TO_TIMESTAMP('2023-06-19 20:29:31','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bis Datum',0,0,TO_TIMESTAMP('2023-06-19 20:29:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-19T17:29:31.247Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586827 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-19T17:29:31.254Z
/* DDL */  select update_Column_Translation_From_AD_Element(582451) 
;

-- 2023-06-19T17:29:32.663Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN UserElementDate2 TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: Fact_Acct.UserElementDate1
-- 2023-06-19T17:31:21.973Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586828,582450,0,15,270,'UserElementDate1',TO_TIMESTAMP('2023-06-19 20:31:21','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vom Datum',0,0,TO_TIMESTAMP('2023-06-19 20:31:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-19T17:31:21.974Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586828 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-19T17:31:21.976Z
/* DDL */  select update_Column_Translation_From_AD_Element(582450) 
;

-- 2023-06-19T17:31:23.765Z
/* DDL */ SELECT public.db_alter_table('Fact_Acct','ALTER TABLE public.Fact_Acct ADD COLUMN UserElementDate1 TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: Fact_Acct.UserElementDate2
-- 2023-06-19T17:31:37.045Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586829,582451,0,15,270,'UserElementDate2',TO_TIMESTAMP('2023-06-19 20:31:36','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bis Datum',0,0,TO_TIMESTAMP('2023-06-19 20:31:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-19T17:31:37.049Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586829 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-19T17:31:37.054Z
/* DDL */  select update_Column_Translation_From_AD_Element(582451) 
;

-- 2023-06-19T17:31:38.284Z
/* DDL */ SELECT public.db_alter_table('Fact_Acct','ALTER TABLE public.Fact_Acct ADD COLUMN UserElementDate2 TIMESTAMP WITHOUT TIME ZONE')
;

-- 2023-06-19T17:47:29.569Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543505,181,TO_TIMESTAMP('2023-06-19 20:47:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nutzer-Element Datum 1',TO_TIMESTAMP('2023-06-19 20:47:29','YYYY-MM-DD HH24:MI:SS'),100,'D1','UserElementDate1')
;

-- 2023-06-19T17:47:29.571Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543505 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2023-06-19T17:47:55.120Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543506,181,TO_TIMESTAMP('2023-06-19 20:47:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nutzer-Element Datum 1',TO_TIMESTAMP('2023-06-19 20:47:54','YYYY-MM-DD HH24:MI:SS'),100,'D2','UserElementDate2')
;

-- 2023-06-19T17:47:55.122Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543506 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_ValidCombination.UserElementDate1
-- 2023-06-19T18:01:04.472Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586832,582450,0,15,176,'UserElementDate1',TO_TIMESTAMP('2023-06-19 21:01:04','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vom Datum',0,0,TO_TIMESTAMP('2023-06-19 21:01:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-19T18:01:04.477Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586832 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-19T18:01:04.480Z
/* DDL */  select update_Column_Translation_From_AD_Element(582450) 
;

-- 2023-06-19T18:01:06.177Z
/* DDL */ SELECT public.db_alter_table('C_ValidCombination','ALTER TABLE public.C_ValidCombination ADD COLUMN UserElementDate1 TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: C_ValidCombination.UserElementDate2
-- 2023-06-19T18:01:18.974Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586833,582451,0,15,176,'UserElementDate2',TO_TIMESTAMP('2023-06-19 21:01:18','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bis Datum',0,0,TO_TIMESTAMP('2023-06-19 21:01:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-19T18:01:18.977Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586833 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-19T18:01:18.987Z
/* DDL */  select update_Column_Translation_From_AD_Element(582451) 
;

-- 2023-06-19T18:01:20.668Z
/* DDL */ SELECT public.db_alter_table('C_ValidCombination','ALTER TABLE public.C_ValidCombination ADD COLUMN UserElementDate2 TIMESTAMP WITHOUT TIME ZONE')
;

-- Field: Rechnungsdisposition -> Rechnungskandidaten -> Vom Datum
-- Column: C_Invoice_Candidate.UserElementDate1
-- 2023-06-20T09:46:56.838Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586824,716379,0,540279,TO_TIMESTAMP('2023-06-20 12:46:56','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Vom Datum',TO_TIMESTAMP('2023-06-20 12:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-20T09:46:56.843Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716379 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-20T09:46:56.888Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582450) 
;

-- 2023-06-20T09:46:56.902Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716379
;

-- 2023-06-20T09:46:56.912Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716379)
;

-- Field: Rechnungsdisposition -> Rechnungskandidaten -> Bis Datum
-- Column: C_Invoice_Candidate.UserElementDate2
-- 2023-06-20T09:47:11.473Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586825,716380,0,540279,TO_TIMESTAMP('2023-06-20 12:47:11','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Bis Datum',TO_TIMESTAMP('2023-06-20 12:47:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-20T09:47:11.475Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716380 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-20T09:47:11.477Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582451) 
;

-- 2023-06-20T09:47:11.482Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716380
;

-- 2023-06-20T09:47:11.483Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716380)
;

-- Field: Rechnungsdisposition -> Rechnungskandidaten -> Vom Datum
-- Column: C_Invoice_Candidate.UserElementDate1
-- 2023-06-20T09:47:37.978Z
UPDATE AD_Field SET DisplayLogic='@$Element_D1@=Y',Updated=TO_TIMESTAMP('2023-06-20 12:47:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716379
;

-- Field: Rechnungsdisposition -> Rechnungskandidaten -> Bis Datum
-- Column: C_Invoice_Candidate.UserElementDate2
-- 2023-06-20T09:47:48.304Z
UPDATE AD_Field SET DisplayLogic='@$Element_D2@=Y',Updated=TO_TIMESTAMP('2023-06-20 12:47:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716380
;

-- UI Element: Rechnungsdisposition -> Rechnungskandidaten.Vom Datum
-- Column: C_Invoice_Candidate.UserElementDate1
-- 2023-06-20T09:48:35.537Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716379,0,540279,618019,540058,'F',TO_TIMESTAMP('2023-06-20 12:48:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vom Datum',60,0,0,TO_TIMESTAMP('2023-06-20 12:48:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition -> Rechnungskandidaten.Bis Datum
-- Column: C_Invoice_Candidate.UserElementDate2
-- 2023-06-20T09:48:47.095Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716380,0,540279,618020,540058,'F',TO_TIMESTAMP('2023-06-20 12:48:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Bis Datum',70,0,0,TO_TIMESTAMP('2023-06-20 12:48:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Rechnung -> Rechnungsposition -> Vom Datum
-- Column: C_InvoiceLine.UserElementDate1
-- 2023-06-20T11:57:22.508Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586826,716381,0,270,TO_TIMESTAMP('2023-06-20 14:57:22','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Vom Datum',TO_TIMESTAMP('2023-06-20 14:57:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-20T11:57:22.511Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716381 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-20T11:57:22.519Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582450) 
;

-- 2023-06-20T11:57:22.533Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716381
;

-- 2023-06-20T11:57:22.541Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716381)
;

-- Field: Rechnung -> Rechnungsposition -> Bis Datum
-- Column: C_InvoiceLine.UserElementDate2
-- 2023-06-20T11:57:33.265Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586827,716382,0,270,TO_TIMESTAMP('2023-06-20 14:57:33','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Bis Datum',TO_TIMESTAMP('2023-06-20 14:57:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-20T11:57:33.269Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716382 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-20T11:57:33.275Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582451) 
;

-- 2023-06-20T11:57:33.294Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716382
;

-- 2023-06-20T11:57:33.300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716382)
;

-- UI Element: Rechnung -> Rechnungsposition.Vom Datum
-- Column: C_InvoiceLine.UserElementDate1
-- 2023-06-20T11:58:19.199Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716381,0,270,618021,540023,'F',TO_TIMESTAMP('2023-06-20 14:58:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vom Datum',198,0,0,TO_TIMESTAMP('2023-06-20 14:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnung -> Rechnungsposition.Bis Datum
-- Column: C_InvoiceLine.UserElementDate2
-- 2023-06-20T11:58:29.576Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716382,0,270,618022,540023,'F',TO_TIMESTAMP('2023-06-20 14:58:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Bis Datum',199,0,0,TO_TIMESTAMP('2023-06-20 14:58:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Rechnungsdisposition Einkauf -> Rechnungskandidaten -> Vom Datum
-- Column: C_Invoice_Candidate.UserElementDate1
-- 2023-06-20T12:18:22.096Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586824,716383,0,543052,TO_TIMESTAMP('2023-06-20 15:18:21','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Vom Datum',TO_TIMESTAMP('2023-06-20 15:18:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-20T12:18:22.098Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716383 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-20T12:18:22.102Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582450) 
;

-- 2023-06-20T12:18:22.110Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716383
;

-- 2023-06-20T12:18:22.114Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716383)
;

-- Field: Rechnungsdisposition Einkauf -> Rechnungskandidaten -> Bis Datum
-- Column: C_Invoice_Candidate.UserElementDate2
-- 2023-06-20T12:18:30.648Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586825,716384,0,543052,TO_TIMESTAMP('2023-06-20 15:18:30','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Bis Datum',TO_TIMESTAMP('2023-06-20 15:18:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-20T12:18:30.649Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716384 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-20T12:18:30.651Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582451) 
;

-- 2023-06-20T12:18:30.658Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716384
;

-- 2023-06-20T12:18:30.658Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716384)
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungskandidaten.Vom Datum
-- Column: C_Invoice_Candidate.UserElementDate1
-- 2023-06-20T12:18:54.778Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716383,0,543052,618023,544365,'F',TO_TIMESTAMP('2023-06-20 15:18:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vom Datum',60,0,0,TO_TIMESTAMP('2023-06-20 15:18:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungskandidaten.Bis Datum
-- Column: C_Invoice_Candidate.UserElementDate2
-- 2023-06-20T12:19:03.593Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716384,0,543052,618024,544365,'F',TO_TIMESTAMP('2023-06-20 15:19:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Bis Datum',70,0,0,TO_TIMESTAMP('2023-06-20 15:19:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Eingangsrechnung -> Rechnungsposition -> Vom Datum
-- Column: C_InvoiceLine.UserElementDate1
-- 2023-06-20T12:20:51.669Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586826,716385,0,291,TO_TIMESTAMP('2023-06-20 15:20:51','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Vom Datum',TO_TIMESTAMP('2023-06-20 15:20:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-20T12:20:51.671Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716385 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-20T12:20:51.674Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582450) 
;

-- 2023-06-20T12:20:51.681Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716385
;

-- 2023-06-20T12:20:51.682Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716385)
;

-- Field: Eingangsrechnung -> Rechnungsposition -> Bis Datum
-- Column: C_InvoiceLine.UserElementDate2
-- 2023-06-20T12:20:59.379Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586827,716386,0,291,TO_TIMESTAMP('2023-06-20 15:20:59','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Bis Datum',TO_TIMESTAMP('2023-06-20 15:20:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-20T12:20:59.381Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716386 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-20T12:20:59.382Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582451) 
;

-- 2023-06-20T12:20:59.388Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716386
;

-- 2023-06-20T12:20:59.389Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716386)
;

-- UI Element: Eingangsrechnung -> Rechnungsposition.Vom Datum
-- Column: C_InvoiceLine.UserElementDate1
-- 2023-06-20T12:21:29.732Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716385,0,291,618025,540219,'F',TO_TIMESTAMP('2023-06-20 15:21:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vom Datum',91,0,0,TO_TIMESTAMP('2023-06-20 15:21:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Eingangsrechnung -> Rechnungsposition.Bis Datum
-- Column: C_InvoiceLine.UserElementDate2
-- 2023-06-20T12:21:39.394Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716386,0,291,618026,540219,'F',TO_TIMESTAMP('2023-06-20 15:21:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Bis Datum',92,0,0,TO_TIMESTAMP('2023-06-20 15:21:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: Fact_Acct_Transactions_View.UserElementDate1
-- 2023-06-20T12:25:40.357Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586835,582450,0,15,541485,'UserElementDate1',TO_TIMESTAMP('2023-06-20 15:25:40','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vom Datum',0,0,TO_TIMESTAMP('2023-06-20 15:25:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-20T12:25:40.361Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586835 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-20T12:25:40.367Z
/* DDL */  select update_Column_Translation_From_AD_Element(582450) 
;

-- Column: Fact_Acct_Transactions_View.UserElementDate2
-- 2023-06-20T12:26:15.025Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586836,582451,0,15,541485,'UserElementDate2',TO_TIMESTAMP('2023-06-20 15:26:14','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bis Datum',0,0,TO_TIMESTAMP('2023-06-20 15:26:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-20T12:26:15.032Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586836 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-20T12:26:15.042Z
/* DDL */  select update_Column_Translation_From_AD_Element(582451) 
;

-- Field: Buchführungs-Details -> Buchführung -> Vom Datum
-- Column: Fact_Acct_Transactions_View.UserElementDate1
-- 2023-06-20T12:26:29.571Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586835,716387,0,242,TO_TIMESTAMP('2023-06-20 15:26:29','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Vom Datum',TO_TIMESTAMP('2023-06-20 15:26:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-20T12:26:29.574Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716387 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-20T12:26:29.578Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582450) 
;

-- 2023-06-20T12:26:29.588Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716387
;

-- 2023-06-20T12:26:29.595Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716387)
;

-- Field: Buchführungs-Details -> Buchführung -> Bis Datum
-- Column: Fact_Acct_Transactions_View.UserElementDate2
-- 2023-06-20T12:26:36.881Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586836,716388,0,242,TO_TIMESTAMP('2023-06-20 15:26:36','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Bis Datum',TO_TIMESTAMP('2023-06-20 15:26:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-20T12:26:36.883Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716388 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-20T12:26:36.885Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582451) 
;

-- 2023-06-20T12:26:36.892Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716388
;

-- 2023-06-20T12:26:36.893Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716388)
;

-- UI Element: Buchführungs-Details -> Buchführung.Vom Datum
-- Column: Fact_Acct_Transactions_View.UserElementDate1
-- 2023-06-20T12:27:19.992Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716387,0,242,618027,540307,'F',TO_TIMESTAMP('2023-06-20 15:27:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vom Datum',70,0,0,TO_TIMESTAMP('2023-06-20 15:27:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Buchführungs-Details -> Buchführung.Bis Datum
-- Column: Fact_Acct_Transactions_View.UserElementDate2
-- 2023-06-20T12:27:27.107Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716388,0,242,618028,540307,'F',TO_TIMESTAMP('2023-06-20 15:27:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Bis Datum',80,0,0,TO_TIMESTAMP('2023-06-20 15:27:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Rechnungsdisposition Einkauf -> Rechnungskandidaten -> Vom Datum
-- Column: C_Invoice_Candidate.UserElementDate1
-- 2023-06-20T14:13:11.859Z
UPDATE AD_Field SET DisplayLogic='@$Element_D1@=Y',Updated=TO_TIMESTAMP('2023-06-20 17:13:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716383
;

-- Field: Rechnungsdisposition Einkauf -> Rechnungskandidaten -> Bis Datum
-- Column: C_Invoice_Candidate.UserElementDate2
-- 2023-06-20T14:13:25.138Z
UPDATE AD_Field SET DisplayLogic='@$Element_D2@=Y',Updated=TO_TIMESTAMP('2023-06-20 17:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716384
;

-- Field: Rechnung -> Rechnungsposition -> Vom Datum
-- Column: C_InvoiceLine.UserElementDate1
-- 2023-06-20T14:14:25.336Z
UPDATE AD_Field SET DisplayLogic='@$Element_D1@=Y',Updated=TO_TIMESTAMP('2023-06-20 17:14:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716381
;

-- Field: Rechnung -> Rechnungsposition -> Bis Datum
-- Column: C_InvoiceLine.UserElementDate2
-- 2023-06-20T14:14:35.242Z
UPDATE AD_Field SET DisplayLogic='@$Element_D2@=Y',Updated=TO_TIMESTAMP('2023-06-20 17:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716382
;

-- Field: Eingangsrechnung -> Rechnungsposition -> Vom Datum
-- Column: C_InvoiceLine.UserElementDate1
-- 2023-06-20T14:15:12.075Z
UPDATE AD_Field SET DisplayLogic='@$Element_D1@=Y',Updated=TO_TIMESTAMP('2023-06-20 17:15:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716385
;

-- Field: Eingangsrechnung -> Rechnungsposition -> Bis Datum
-- Column: C_InvoiceLine.UserElementDate2
-- 2023-06-20T14:15:23.338Z
UPDATE AD_Field SET DisplayLogic='@$Element_D2@=Y',Updated=TO_TIMESTAMP('2023-06-20 17:15:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716386
;

-- Field: Buchführungs-Details -> Buchführung -> Vom Datum
-- Column: Fact_Acct_Transactions_View.UserElementDate1
-- 2023-06-20T14:15:43.969Z
UPDATE AD_Field SET DisplayLogic='@$Element_D1@=Y',Updated=TO_TIMESTAMP('2023-06-20 17:15:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716387
;

-- Field: Buchführungs-Details -> Buchführung -> Bis Datum
-- Column: Fact_Acct_Transactions_View.UserElementDate2
-- 2023-06-20T14:15:52.617Z
UPDATE AD_Field SET DisplayLogic='@$Element_D2@=Y',Updated=TO_TIMESTAMP('2023-06-20 17:15:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716388
;

-- 2023-06-20T18:14:49.290Z
UPDATE AD_Ref_List SET Name='Nutzer-Element Datum 2',Updated=TO_TIMESTAMP('2023-06-20 21:14:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543506
;
