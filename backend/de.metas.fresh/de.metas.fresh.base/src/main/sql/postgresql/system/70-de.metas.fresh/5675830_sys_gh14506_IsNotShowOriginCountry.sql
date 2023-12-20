-- 2023-02-06T19:19:13.531Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582035,0,'IsNotShowOriginCountry',TO_TIMESTAMP('2023-02-06 20:19:12','YYYY-MM-DD HH24:MI:SS'),100,'If is NO, then the Country of Origin of the products is displyed in the invoice report','D','Y','Do not show Country of Origin','Do not show Country of Origin',TO_TIMESTAMP('2023-02-06 20:19:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T19:19:13.640Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582035 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsNotShowOriginCountry
-- 2023-02-06T19:22:46.576Z
UPDATE AD_Element_Trl SET Description='Wenn NEIN, dann wird das Herkunftsland der Produkte im Rechnungsbericht angezeigt', IsTranslated='Y', Name='Herkunftsland nicht anzeigen', PrintName='Herkunftsland nicht anzeigen',Updated=TO_TIMESTAMP('2023-02-06 20:22:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582035 AND AD_Language='de_DE'
;

-- 2023-02-06T19:22:46.810Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582035,'de_DE') 
;

-- Element: IsNotShowOriginCountry
-- 2023-02-06T19:24:56.888Z
UPDATE AD_Element_Trl SET Description='If is NO, then the Country of Origin of the products is displayed in the invoice report',Updated=TO_TIMESTAMP('2023-02-06 20:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582035 AND AD_Language='en_US'
;

-- 2023-02-06T19:24:56.997Z
UPDATE AD_Element SET Description='If is NO, then the Country of Origin of the products is displayed in the invoice report' WHERE AD_Element_ID=582035
;

-- 2023-02-06T19:25:50.300Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582035,'en_US') 
;

-- 2023-02-06T19:25:50.432Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582035,'en_US') 
;

-- Column: C_Order.IsNotShowOriginCountry
-- 2023-02-06T19:30:11.107Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585925,582035,0,20,259,'IsNotShowOriginCountry',TO_TIMESTAMP('2023-02-06 20:30:09','YYYY-MM-DD HH24:MI:SS'),100,'N','N','If is NO, then the Country of Origin of the products is displayed in the invoice report','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Do not show Country of Origin',0,0,TO_TIMESTAMP('2023-02-06 20:30:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T19:30:11.216Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585925 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T19:30:11.437Z
/* DDL */  select update_Column_Translation_From_AD_Element(582035) 
;

-- 2023-02-06T19:33:36.717Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN IsNotShowOriginCountry CHAR(1) DEFAULT ''N'' CHECK (IsNotShowOriginCountry IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_Invoice_Candidate.IsNotShowOriginCountry
-- 2023-02-06T19:36:35.065Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585926,582035,0,20,540270,'IsNotShowOriginCountry',TO_TIMESTAMP('2023-02-06 20:36:33','YYYY-MM-DD HH24:MI:SS'),100,'N','N','If is NO, then the Country of Origin of the products is displayed in the invoice report','de.metas.invoicecandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Do not show Country of Origin',0,0,TO_TIMESTAMP('2023-02-06 20:36:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T19:36:35.177Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585926 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T19:36:35.403Z
/* DDL */  select update_Column_Translation_From_AD_Element(582035) 
;

-- 2023-02-06T19:38:06.373Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN IsNotShowOriginCountry CHAR(1) DEFAULT ''N'' CHECK (IsNotShowOriginCountry IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_Invoice.IsNotShowOriginCountry
-- 2023-02-06T19:40:53.820Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585927,582035,0,20,318,'IsNotShowOriginCountry',TO_TIMESTAMP('2023-02-06 20:40:52','YYYY-MM-DD HH24:MI:SS'),100,'N','N','If is NO, then the Country of Origin of the products is displayed in the invoice report','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Do not show Country of Origin',0,0,TO_TIMESTAMP('2023-02-06 20:40:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T19:40:53.923Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585927 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T19:40:54.132Z
/* DDL */  select update_Column_Translation_From_AD_Element(582035) 
;

-- 2023-02-06T19:42:21.967Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN IsNotShowOriginCountry CHAR(1) DEFAULT ''N'' CHECK (IsNotShowOriginCountry IN (''Y'',''N'')) NOT NULL')
;

