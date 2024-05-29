-- Run mode: SWING_CLIENT

-- 2024-05-20T14:08:35.150Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583112,0,'UserElementNumber1',TO_TIMESTAMP('2024-05-20 17:08:34.949','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','UserElementNumber1','UserElementNumber1',TO_TIMESTAMP('2024-05-20 17:08:34.949','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-20T14:08:35.164Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583112 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-05-20T14:09:00.318Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583113,0,'UserElementNumber2',TO_TIMESTAMP('2024-05-20 17:09:00.203','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','UserElementNumber2','UserElementNumber2',TO_TIMESTAMP('2024-05-20 17:09:00.203','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-20T14:09:00.321Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583113 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_InOutLine.UserElementNumber1
-- 2024-05-20T14:09:43.157Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588271,583112,0,22,320,'UserElementNumber1',TO_TIMESTAMP('2024-05-20 17:09:43.017','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber1',0,0,TO_TIMESTAMP('2024-05-20 17:09:43.017','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-20T14:09:43.161Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588271 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-20T14:09:43.189Z
/* DDL */  select update_Column_Translation_From_AD_Element(583112)
;

-- 2024-05-20T14:09:45.820Z
/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN UserElementNumber1 NUMERIC')
;

-- Column: M_InOutLine.UserElementNumber2
-- 2024-05-20T14:10:04.666Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588272,583113,0,22,320,'UserElementNumber2',TO_TIMESTAMP('2024-05-20 17:10:04.53','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber2',0,0,TO_TIMESTAMP('2024-05-20 17:10:04.53','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-20T14:10:04.668Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588272 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-20T14:10:04.671Z
/* DDL */  select update_Column_Translation_From_AD_Element(583113)
;

-- 2024-05-20T14:10:07.013Z
/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN UserElementNumber2 NUMERIC')
;

-- Column: C_Invoice_Candidate.UserElementNumber1
-- 2024-05-20T14:10:35.447Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588273,583112,0,22,540270,'UserElementNumber1',TO_TIMESTAMP('2024-05-20 17:10:35.31','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber1',0,0,TO_TIMESTAMP('2024-05-20 17:10:35.31','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-20T14:10:35.449Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588273 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-20T14:10:35.452Z
/* DDL */  select update_Column_Translation_From_AD_Element(583112)
;

-- 2024-05-20T14:10:38.420Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN UserElementNumber1 NUMERIC')
;

-- Column: C_Invoice_Candidate.UserElementNumber2
-- 2024-05-20T14:10:46.818Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588274,583113,0,22,540270,'UserElementNumber2',TO_TIMESTAMP('2024-05-20 17:10:46.707','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber2',0,0,TO_TIMESTAMP('2024-05-20 17:10:46.707','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-20T14:10:46.820Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588274 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-20T14:10:46.822Z
/* DDL */  select update_Column_Translation_From_AD_Element(583113)
;

-- 2024-05-20T14:10:51.694Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN UserElementNumber2 NUMERIC')
;

-- Column: C_InvoiceLine.UserElementNumber1
-- 2024-05-20T14:11:17.801Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588275,583112,0,22,333,'UserElementNumber1',TO_TIMESTAMP('2024-05-20 17:11:17.657','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber1',0,0,TO_TIMESTAMP('2024-05-20 17:11:17.657','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-20T14:11:17.803Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588275 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-20T14:11:17.806Z
/* DDL */  select update_Column_Translation_From_AD_Element(583112)
;

-- 2024-05-20T14:11:20.280Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN UserElementNumber1 NUMERIC')
;

-- Column: C_InvoiceLine.UserElementNumber2
-- 2024-05-20T14:11:31.457Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588276,583113,0,22,333,'UserElementNumber2',TO_TIMESTAMP('2024-05-20 17:11:31.349','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber2',0,0,TO_TIMESTAMP('2024-05-20 17:11:31.349','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-20T14:11:31.459Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588276 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-20T14:11:31.462Z
/* DDL */  select update_Column_Translation_From_AD_Element(583113)
;

-- 2024-05-20T14:11:33.351Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN UserElementNumber2 NUMERIC')
;

-- Column: Fact_Acct.UserElementNumber1
-- 2024-05-21T06:00:53.118Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588277,583112,0,22,270,'UserElementNumber1',TO_TIMESTAMP('2024-05-21 09:00:52.865','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber1',0,0,TO_TIMESTAMP('2024-05-21 09:00:52.865','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T06:00:53.127Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588277 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T06:00:53.140Z
/* DDL */  select update_Column_Translation_From_AD_Element(583112)
;

-- 2024-05-21T06:00:55.253Z
/* DDL */ SELECT public.db_alter_table('Fact_Acct','ALTER TABLE public.Fact_Acct ADD COLUMN UserElementNumber1 NUMERIC')
;

-- Column: Fact_Acct.UserElementNumber2
-- 2024-05-21T06:01:10.467Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588278,583113,0,22,270,'UserElementNumber2',TO_TIMESTAMP('2024-05-21 09:01:10.36','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber2',0,0,TO_TIMESTAMP('2024-05-21 09:01:10.36','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T06:01:10.471Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588278 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T06:01:10.476Z
/* DDL */  select update_Column_Translation_From_AD_Element(583113)
;

-- 2024-05-21T06:01:12.917Z
/* DDL */ SELECT public.db_alter_table('Fact_Acct','ALTER TABLE public.Fact_Acct ADD COLUMN UserElementNumber2 NUMERIC')
;

-- Column: C_AcctSchema_Element.UserElementNumber1
-- 2024-05-21T06:21:57.556Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588279,583112,0,22,279,'UserElementNumber1',TO_TIMESTAMP('2024-05-21 09:21:57.411','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber1',0,0,TO_TIMESTAMP('2024-05-21 09:21:57.411','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T06:21:57.560Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588279 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T06:21:57.565Z
/* DDL */  select update_Column_Translation_From_AD_Element(583112)
;

-- 2024-05-21T06:21:59.424Z
/* DDL */ SELECT public.db_alter_table('C_AcctSchema_Element','ALTER TABLE public.C_AcctSchema_Element ADD COLUMN UserElementNumber1 NUMERIC')
;

-- Column: C_AcctSchema_Element.UserElementNumber2
-- 2024-05-21T06:22:06.232Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588280,583113,0,22,279,'UserElementNumber2',TO_TIMESTAMP('2024-05-21 09:22:06.112','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber2',0,0,TO_TIMESTAMP('2024-05-21 09:22:06.112','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T06:22:06.235Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588280 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T06:22:06.239Z
/* DDL */  select update_Column_Translation_From_AD_Element(583113)
;

-- 2024-05-21T06:22:08.022Z
/* DDL */ SELECT public.db_alter_table('C_AcctSchema_Element','ALTER TABLE public.C_AcctSchema_Element ADD COLUMN UserElementNumber2 NUMERIC')
;

-- Column: C_ValidCombination.UserElementNumber1
-- 2024-05-21T06:23:44.630Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588281,583112,0,22,176,'UserElementNumber1',TO_TIMESTAMP('2024-05-21 09:23:44.48','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber1',0,0,TO_TIMESTAMP('2024-05-21 09:23:44.48','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T06:23:44.633Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588281 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T06:23:44.637Z
/* DDL */  select update_Column_Translation_From_AD_Element(583112)
;

-- 2024-05-21T06:23:46.337Z
/* DDL */ SELECT public.db_alter_table('C_ValidCombination','ALTER TABLE public.C_ValidCombination ADD COLUMN UserElementNumber1 NUMERIC')
;

-- Column: C_ValidCombination.UserElementNumber2
-- 2024-05-21T06:23:54.396Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588282,583113,0,22,176,'UserElementNumber2',TO_TIMESTAMP('2024-05-21 09:23:54.28','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber2',0,0,TO_TIMESTAMP('2024-05-21 09:23:54.28','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T06:23:54.398Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588282 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T06:23:54.400Z
/* DDL */  select update_Column_Translation_From_AD_Element(583113)
;

-- 2024-05-21T06:23:56.144Z
/* DDL */ SELECT public.db_alter_table('C_ValidCombination','ALTER TABLE public.C_ValidCombination ADD COLUMN UserElementNumber2 NUMERIC')
;

-- Reference: C_AcctSchema ElementType
-- Value: N1
-- ValueName: UserElementNumber1
-- 2024-05-21T06:27:07.206Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,181,543684,TO_TIMESTAMP('2024-05-21 09:27:07.048','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Nutzer-Element Number 1',TO_TIMESTAMP('2024-05-21 09:27:07.048','YYYY-MM-DD HH24:MI:SS.US'),100,'N1','UserElementNumber1')
;

-- 2024-05-21T06:27:07.215Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543684 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_AcctSchema ElementType
-- Value: N2
-- ValueName: UserElementNumber2
-- 2024-05-21T06:27:17.022Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,181,543685,TO_TIMESTAMP('2024-05-21 09:27:16.88','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Nutzer-Element Number 2',TO_TIMESTAMP('2024-05-21 09:27:16.88','YYYY-MM-DD HH24:MI:SS.US'),100,'N2','UserElementNumber2')
;

-- 2024-05-21T06:27:17.024Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543685 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: Fact_Acct_Transactions_View.UserElementNumber1
-- 2024-05-21T06:53:28.597Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588283,583112,0,22,541485,'UserElementNumber1',TO_TIMESTAMP('2024-05-21 09:53:28.459','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber1',0,0,TO_TIMESTAMP('2024-05-21 09:53:28.459','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T06:53:28.601Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588283 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T06:53:28.604Z
/* DDL */  select update_Column_Translation_From_AD_Element(583112)
;

-- Column: Fact_Acct_Transactions_View.UserElementNumber2
-- 2024-05-21T06:53:37.945Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588284,583113,0,22,541485,'UserElementNumber2',TO_TIMESTAMP('2024-05-21 09:53:37.844','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementNumber2',0,0,TO_TIMESTAMP('2024-05-21 09:53:37.844','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T06:53:37.948Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588284 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T06:53:37.950Z
/* DDL */  select update_Column_Translation_From_AD_Element(583113)
;

-- Column: M_ShipmentSchedule_Split.UserElementNumber1
-- 2024-05-21T07:13:48.764Z
UPDATE AD_Column SET AD_Element_ID=583112, AD_Reference_ID=22, ColumnName='UserElementNumber1', Description=NULL, FieldLength=10, Help=NULL, Name='UserElementNumber1',Updated=TO_TIMESTAMP('2024-05-21 10:13:48.764','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588130
;

-- 2024-05-21T07:13:48.768Z
UPDATE AD_Column_Trl trl SET Name='UserElementNumber1' WHERE AD_Column_ID=588130 AND AD_Language='de_DE'
;

-- 2024-05-21T07:13:48.769Z
UPDATE AD_Field SET Name='UserElementNumber1', Description=NULL, Help=NULL WHERE AD_Column_ID=588130
;

-- 2024-05-21T07:13:48.771Z
/* DDL */  select update_Column_Translation_From_AD_Element(583112)
;

-- 2024-05-21T07:13:53.204Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule_Split','ALTER TABLE public.M_ShipmentSchedule_Split ADD COLUMN UserElementNumber1 NUMERIC')
;

-- Column: M_ShipmentSchedule_Split.UserElementNumber2
-- 2024-05-21T07:14:05.828Z
UPDATE AD_Column SET AD_Element_ID=583113, AD_Reference_ID=22, ColumnName='UserElementNumber2', Description=NULL, FieldLength=10, Help=NULL, Name='UserElementNumber2',Updated=TO_TIMESTAMP('2024-05-21 10:14:05.828','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588131
;

-- 2024-05-21T07:14:05.830Z
UPDATE AD_Column_Trl trl SET Name='UserElementNumber2' WHERE AD_Column_ID=588131 AND AD_Language='de_DE'
;

-- 2024-05-21T07:14:05.831Z
UPDATE AD_Field SET Name='UserElementNumber2', Description=NULL, Help=NULL WHERE AD_Column_ID=588131
;

-- 2024-05-21T07:14:05.833Z
/* DDL */  select update_Column_Translation_From_AD_Element(583113)
;

-- 2024-05-21T07:14:08.231Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule_Split','ALTER TABLE public.M_ShipmentSchedule_Split ADD COLUMN UserElementNumber2 NUMERIC')
;


SELECT public.db_alter_table('M_ShipmentSchedule_Split','ALTER TABLE public.M_ShipmentSchedule_Split DROP COLUMN UserElementString1')
;

SELECT public.db_alter_table('M_ShipmentSchedule_Split','ALTER TABLE public.M_ShipmentSchedule_Split DROP COLUMN UserElementString2')
;



-- Column: ModCntr_Type.AD_Column_ID
-- 2024-05-21T08:03:31.385Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588285,104,0,19,542337,'AD_Column_ID',TO_TIMESTAMP('2024-05-21 11:03:31.218','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Spalte in der Tabelle','de.metas.contracts',0,10,'Verbindung zur Spalte der Tabelle','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Spalte',0,0,TO_TIMESTAMP('2024-05-21 11:03:31.218','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T08:03:31.388Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588285 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T08:03:31.391Z
/* DDL */  select update_Column_Translation_From_AD_Element(104)
;

-- Name: UserElementNumberX
-- 2024-05-21T08:04:17.359Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540676,TO_TIMESTAMP('2024-05-21 11:04:17.247','YYYY-MM-DD HH24:MI:SS.US'),100,'UserElementNumberX from M_InOutLine/C_Invoice_Candidate.','D','Y','UserElementNumberX ','S',TO_TIMESTAMP('2024-05-21 11:04:17.247','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: UserElementNumberX
-- 2024-05-21T08:07:08.890Z
UPDATE AD_Val_Rule SET Code='AD_Column.AD_Table_ID in (320,540270) AND AD_Column.ColumnName ilike ''UserElementNumber%''',Updated=TO_TIMESTAMP('2024-05-21 11:07:08.887','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540676
;

-- Column: ModCntr_Type.AD_Column_ID
-- 2024-05-21T08:07:25.754Z
UPDATE AD_Column SET AD_Val_Rule_ID=540676,Updated=TO_TIMESTAMP('2024-05-21 11:07:25.754','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588285
;

-- 2024-05-21T08:07:33.114Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Type','ALTER TABLE public.ModCntr_Type ADD COLUMN AD_Column_ID NUMERIC(10)')
;

-- 2024-05-21T08:07:33.122Z
ALTER TABLE ModCntr_Type ADD CONSTRAINT ADColumn_ModCntrType FOREIGN KEY (AD_Column_ID) REFERENCES public.AD_Column DEFERRABLE INITIALLY DEFERRED
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Spalte
-- Column: ModCntr_Type.AD_Column_ID
-- 2024-05-21T08:07:56.179Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588285,728716,0,547011,0,TO_TIMESTAMP('2024-05-21 11:07:56.033','YYYY-MM-DD HH24:MI:SS.US'),100,'Spalte in der Tabelle',0,'U','Verbindung zur Spalte der Tabelle',0,'Y','Y','Y','N','N','N','N','N','N','Spalte',0,10,0,1,1,TO_TIMESTAMP('2024-05-21 11:07:56.033','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-21T08:07:56.181Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728716 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-21T08:07:56.183Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(104)
;

-- 2024-05-21T08:07:56.189Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728716
;

-- 2024-05-21T08:07:56.190Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728716)
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Spalte
-- Column: ModCntr_Type.AD_Column_ID
-- 2024-05-21T08:09:17.514Z
UPDATE AD_Field SET DisplayLogic='@ModularContractHandlerType@=''AverageAddedValueOnShippedQuantity''',Updated=TO_TIMESTAMP('2024-05-21 11:09:17.514','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728716
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Spalte
-- Column: ModCntr_Type.AD_Column_ID
-- 2024-05-21T08:10:08.402Z
UPDATE AD_Field SET DisplayLength=10,Updated=TO_TIMESTAMP('2024-05-21 11:10:08.402','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728716
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Spalte
-- Column: ModCntr_Type.AD_Column_ID
-- 2024-05-21T08:11:19.353Z
UPDATE AD_Field SET DisplayLogic='@ModularContractHandlerType@=AverageAddedValueOnShippedQuantity',Updated=TO_TIMESTAMP('2024-05-21 11:11:19.353','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728716
;

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 10 -> infos.Link Column
-- Column: AD_SQLColumn_SourceTableColumn.Link_Column_ID
-- 2024-05-21T08:24:40.572Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728716,0,547011,550769,624737,'F',TO_TIMESTAMP('2024-05-21 11:24:40.445','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Link Column',30,0,0,TO_TIMESTAMP('2024-05-21 11:24:40.445','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 10 -> infos.Column
-- Column: ModCntr_Type.AD_Column_ID
-- 2024-05-21T08:25:59.547Z
UPDATE AD_UI_Element SET Name='Column',Updated=TO_TIMESTAMP('2024-05-21 11:25:59.547','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624737
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Spalte
-- Column: ModCntr_Type.AD_Column_ID
-- 2024-05-21T08:27:00.799Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2024-05-21 11:27:00.799','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728716
;

-- Column: ModCntr_Type.AD_Column_ID
-- 2024-05-21T08:27:12.552Z
UPDATE AD_Column SET MandatoryLogic='@ModularContractHandlerType@=AverageAddedValueOnShippedQuantity',Updated=TO_TIMESTAMP('2024-05-21 11:27:12.552','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588285
;

-- 2024-05-21T08:30:36.116Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583115,0,'ScalePriceQuantityFrom',TO_TIMESTAMP('2024-05-21 11:30:35.996','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Scale price quantity from','Scale price quantity from',TO_TIMESTAMP('2024-05-21 11:30:35.996','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-21T08:30:36.118Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583115 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ScalePriceQuantityFrom
-- 2024-05-21T08:30:51.288Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Staffelpreis Menge ab', PrintName='Staffelpreis Menge ab',Updated=TO_TIMESTAMP('2024-05-21 11:30:51.288','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583115 AND AD_Language='de_CH'
;

-- 2024-05-21T08:30:51.292Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583115,'de_CH')
;

-- Element: ScalePriceQuantityFrom
-- 2024-05-21T08:30:54.421Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Staffelpreis Menge ab', PrintName='Staffelpreis Menge ab',Updated=TO_TIMESTAMP('2024-05-21 11:30:54.421','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583115 AND AD_Language='de_DE'
;

-- 2024-05-21T08:30:54.424Z
UPDATE AD_Element SET Name='Staffelpreis Menge ab', PrintName='Staffelpreis Menge ab' WHERE AD_Element_ID=583115
;

-- 2024-05-21T08:30:54.655Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583115,'de_DE')
;

-- 2024-05-21T08:30:54.657Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583115,'de_DE')
;

-- Element: ScalePriceQuantityFrom
-- 2024-05-21T08:30:57.611Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-21 11:30:57.611','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583115 AND AD_Language='en_US'
;

-- 2024-05-21T08:30:57.615Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583115,'en_US')
;

-- Column: M_ProductPrice.ScalePriceQuantityFrom
-- 2024-05-21T08:31:17.595Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588286,583115,0,10,251,'ScalePriceQuantityFrom',TO_TIMESTAMP('2024-05-21 11:31:17.482','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,5,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Staffelpreis Menge ab',0,0,TO_TIMESTAMP('2024-05-21 11:31:17.482','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T08:31:17.597Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588286 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T08:31:17.601Z
/* DDL */  select update_Column_Translation_From_AD_Element(583115)
;

-- 2024-05-21T08:31:22.093Z
/* DDL */ SELECT public.db_alter_table('M_ProductPrice','ALTER TABLE public.M_ProductPrice ADD COLUMN ScalePriceQuantityFrom VARCHAR(5)')
;

-- Name: Scale price quantity from
-- 2024-05-21T08:32:01.596Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541870,TO_TIMESTAMP('2024-05-21 11:32:01.456','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','Scale price quantity from',TO_TIMESTAMP('2024-05-21 11:32:01.456','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2024-05-21T08:32:01.599Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541870 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Scale price quantity from
-- Value: Q
-- ValueName: Quantity
-- 2024-05-21T08:32:19.943Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541870,543686,TO_TIMESTAMP('2024-05-21 11:32:19.839','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Quantity',TO_TIMESTAMP('2024-05-21 11:32:19.839','YYYY-MM-DD HH24:MI:SS.US'),100,'Q','Quantity')
;

-- 2024-05-21T08:32:19.945Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543686 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Scale price quantity from
-- Value: UEN1
-- ValueName: UserElementNumber1
-- 2024-05-21T08:42:45.142Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541870,543687,TO_TIMESTAMP('2024-05-21 11:42:44.922','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','User Element Number 1',TO_TIMESTAMP('2024-05-21 11:42:44.922','YYYY-MM-DD HH24:MI:SS.US'),100,'UEN1','UserElementNumber1')
;

-- 2024-05-21T08:42:45.145Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543687 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Scale price quantity from
-- Value: UEN2
-- ValueName: UserElementNumber2
-- 2024-05-21T08:42:57.673Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541870,543688,TO_TIMESTAMP('2024-05-21 11:42:57.551','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','User Element Number 2',TO_TIMESTAMP('2024-05-21 11:42:57.551','YYYY-MM-DD HH24:MI:SS.US'),100,'UEN2','UserElementNumber2')
;

-- 2024-05-21T08:42:57.675Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543688 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: M_ProductPrice.ScalePriceQuantityFrom
-- 2024-05-21T08:46:10.955Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541870,Updated=TO_TIMESTAMP('2024-05-21 11:46:10.955','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588286
;

-- 2024-05-21T08:46:17.504Z
INSERT INTO t_alter_column values('m_productprice','ScalePriceQuantityFrom','VARCHAR(5)',null,null)
;

-- Field: Produkt Preise_OLD(540325,D) -> Produkt Preis(540787,D) -> Staffelpreis Menge ab
-- Column: M_ProductPrice.ScalePriceQuantityFrom
-- 2024-05-21T08:49:52.134Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588286,728717,0,540787,0,TO_TIMESTAMP('2024-05-21 11:49:51.969','YYYY-MM-DD HH24:MI:SS.US'),100,5,'D',0,'Y','Y','Y','N','N','N','N','N','N','Staffelpreis Menge ab',0,200,0,1,1,TO_TIMESTAMP('2024-05-21 11:49:51.969','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-21T08:49:52.137Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728717 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-21T08:49:52.139Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583115)
;

-- 2024-05-21T08:49:52.142Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728717
;

-- 2024-05-21T08:49:52.144Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728717)
;

-- Field: Produkt Preise_OLD(540325,D) -> Produkt Preis(540787,D) -> Staffelpreis Menge ab
-- Column: M_ProductPrice.ScalePriceQuantityFrom
-- 2024-05-21T08:50:03.438Z
UPDATE AD_Field SET DisplayLogic='@UseScalePrice@=''Y''',Updated=TO_TIMESTAMP('2024-05-21 11:50:03.437','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728717
;

-- Field: Produkt Preise_OLD(540325,D) -> Produkt Preis(540787,D) -> Staffelpreis Menge ab
-- Column: M_ProductPrice.ScalePriceQuantityFrom
-- 2024-05-21T08:53:08.809Z
UPDATE AD_Field SET DisplayLogic='@UseScalePrice@ !=''N''',Updated=TO_TIMESTAMP('2024-05-21 11:53:08.809','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728717
;

-- UI Element: Produkt Preise_OLD(540325,D) -> Produkt Preis(540787,D) -> main -> 20 -> flags.Staffelpreis Menge ab
-- Column: M_ProductPrice.ScalePriceQuantityFrom
-- 2024-05-21T08:53:56.777Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728717,0,540787,540126,624738,'F',TO_TIMESTAMP('2024-05-21 11:53:56.63','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','Y','N','N','Staffelpreis Menge ab',45,130,0,TO_TIMESTAMP('2024-05-21 11:53:56.63','YYYY-MM-DD HH24:MI:SS.US'),100)
;


-- Field: Produkt Preise_OLD(540325,D) -> Produkt Preis(540787,D) -> Staffelpreis Menge ab
-- Column: M_ProductPrice.ScalePriceQuantityFrom
-- 2024-05-21T09:55:14.616Z
UPDATE AD_Field SET DisplayLogic='@UseScalePrice@ != N',Updated=TO_TIMESTAMP('2024-05-21 12:55:14.616','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728717
;



-- Column: ModCntr_Specific_Price.IsScalePrice
-- 2024-05-23T12:50:44.030Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588320,580373,0,37,542405,'IsScalePrice',TO_TIMESTAMP('2024-05-23 15:50:43.844','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Staffelpreis',0,0,TO_TIMESTAMP('2024-05-23 15:50:43.844','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-23T12:50:44.038Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588320 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-23T12:50:44.042Z
/* DDL */  select update_Column_Translation_From_AD_Element(580373)
;

-- Column: ModCntr_Specific_Price.IsScalePrice
-- 2024-05-23T12:50:58.989Z
UPDATE AD_Column SET DefaultValue='''N''',Updated=TO_TIMESTAMP('2024-05-23 15:50:58.989','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588320
;

-- Column: ModCntr_Specific_Price.IsScalePrice
-- 2024-05-23T12:51:41.597Z
UPDATE AD_Column SET AD_Reference_ID=20, DefaultValue='N', FieldLength=1,Updated=TO_TIMESTAMP('2024-05-23 15:51:41.597','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588320
;

-- 2024-05-23T12:51:44.910Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE public.ModCntr_Specific_Price ADD COLUMN IsScalePrice CHAR(1) DEFAULT ''N'' CHECK (IsScalePrice IN (''Y'',''N'')) NOT NULL')
;

-- Column: ModCntr_Specific_Price.MinValue
-- 2024-05-23T12:53:33.800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588321,53400,0,12,542405,'MinValue',TO_TIMESTAMP('2024-05-23 15:53:33.676','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Min Value',0,0,TO_TIMESTAMP('2024-05-23 15:53:33.676','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-23T12:53:33.803Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588321 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-23T12:53:33.808Z
/* DDL */  select update_Column_Translation_From_AD_Element(53400)
;

-- 2024-05-23T12:53:37.417Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE public.ModCntr_Specific_Price ADD COLUMN MinValue NUMERIC DEFAULT 0 NOT NULL')
;

-- Element: MinValue
-- 2024-05-23T12:53:51.092Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mindestwert', PrintName='Mindestwert',Updated=TO_TIMESTAMP('2024-05-23 15:53:51.092','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53400 AND AD_Language='it_CH'
;

-- 2024-05-23T12:53:51.098Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53400,'it_CH')
;

-- Element: MinValue
-- 2024-05-23T12:53:53.667Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-23 15:53:53.667','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53400 AND AD_Language='en_GB'
;

-- 2024-05-23T12:53:53.673Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53400,'en_GB')
;

-- Element: MinValue
-- 2024-05-23T12:53:57.496Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mindestwert', PrintName='Mindestwert',Updated=TO_TIMESTAMP('2024-05-23 15:53:57.496','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53400 AND AD_Language='de_CH'
;

-- 2024-05-23T12:53:57.499Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53400,'de_CH')
;

-- Element: MinValue
-- 2024-05-23T12:54:02.905Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mindestwert', PrintName='Mindestwert',Updated=TO_TIMESTAMP('2024-05-23 15:54:02.905','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53400 AND AD_Language='de_DE'
;

-- 2024-05-23T12:54:02.907Z
UPDATE AD_Element SET Name='Mindestwert', PrintName='Mindestwert' WHERE AD_Element_ID=53400
;

-- 2024-05-23T12:54:03.128Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(53400,'de_DE')
;

-- 2024-05-23T12:54:03.130Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53400,'de_DE')
;


-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- ParameterName: MinValue
-- 2024-05-27T11:15:02.369Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,53400,0,585382,542828,12,'MinValue',TO_TIMESTAMP('2024-05-27 14:15:02.115','YYYY-MM-DD HH24:MI:SS.US'),100,'@MinValue@','@IsScalePrice@=''Y''','EE02',0,'Y','N','Y','N','N','N','Mindestwert',40,TO_TIMESTAMP('2024-05-27 14:15:02.115','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-27T11:15:02.376Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542828 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-27T11:15:02.382Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(53400)
;

-- 2024-05-27T11:17:22.712Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583127,0,TO_TIMESTAMP('2024-05-27 14:17:22.593','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','AsNewPrice','As New Price',TO_TIMESTAMP('2024-05-27 14:17:22.593','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-27T11:17:22.718Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583127 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2024-05-27T11:17:30.583Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Neupreis',Updated=TO_TIMESTAMP('2024-05-27 14:17:30.583','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583127 AND AD_Language='de_CH'
;

-- 2024-05-27T11:17:30.590Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583127,'de_CH')
;

-- Element: null
-- 2024-05-27T11:17:33.694Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Neupreis',Updated=TO_TIMESTAMP('2024-05-27 14:17:33.694','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583127 AND AD_Language='de_DE'
;

-- 2024-05-27T11:17:33.696Z
UPDATE AD_Element SET PrintName='Neupreis' WHERE AD_Element_ID=583127
;

-- 2024-05-27T11:17:33.993Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583127,'de_DE')
;

-- 2024-05-27T11:17:33.996Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583127,'de_DE')
;


CREATE UNIQUE INDEX ModCntr_Specific_Price_MinValue
    ON ModCntr_Specific_Price (ModCntr_Module_ID, MinValue, IsScalePrice, m_product_id, c_flatrate_term_id);

	
-- Value: ModCntr_Specific_Delete_Price
-- Classname: de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price
-- 2024-05-27T11:42:00.183Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585393,'Y','de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price','N',TO_TIMESTAMP('2024-05-27 14:42:00.027','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Preis löschen','json','N','N','xls','Delete  the selected ModCntr_Specific_Price record ','Java',TO_TIMESTAMP('2024-05-27 14:42:00.027','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Specific_Delete_Price')
;

-- 2024-05-27T11:42:00.198Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585393 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ModCntr_Specific_Delete_Price(de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price)
-- 2024-05-27T11:42:06.840Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-27 14:42:06.84','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585393
;

-- Process: ModCntr_Specific_Delete_Price(de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price)
-- 2024-05-27T11:42:11.703Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-27 14:42:11.703','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585393
;

-- Process: ModCntr_Specific_Delete_Price(de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price)
-- 2024-05-27T11:42:16.439Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Delete price',Updated=TO_TIMESTAMP('2024-05-27 14:42:16.439','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585393
;

-- Process: ModCntr_Specific_Delete_Price(de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price)
-- Table: ModCntr_Specific_Price
-- EntityType: de.metas.contracts
-- 2024-05-27T11:42:46.423Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585393,542405,541490,TO_TIMESTAMP('2024-05-27 14:42:46.301','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2024-05-27 14:42:46.301','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

	