-- 2022-01-17T20:28:03.314Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580486,0,'C_Incoterms_Customer_ID',TO_TIMESTAMP('2022-01-17 22:28:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Incoterms (Kunde)','Incoterms (Kunde)',TO_TIMESTAMP('2022-01-17 22:28:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-17T20:28:03.389Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580486 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-01-17T20:28:32.464Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Incoterms (Customer)', PrintName='Incoterms (Customer)',Updated=TO_TIMESTAMP('2022-01-17 22:28:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580486 AND AD_Language='en_US'
;

-- 2022-01-17T20:28:32.542Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580486,'en_US') 
;

-- 2022-01-17T20:28:52.219Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580487,0,'C_Incoterms_Vendor_ID',TO_TIMESTAMP('2022-01-17 22:28:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Incoterms (Lieferant)','Incoterms (Lieferant)',TO_TIMESTAMP('2022-01-17 22:28:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-17T20:28:52.295Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580487 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-01-17T20:29:10.403Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Incoterms (Vendor)', PrintName='Incoterms (Vendor)',Updated=TO_TIMESTAMP('2022-01-17 22:29:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580487 AND AD_Language='en_US'
;

-- 2022-01-17T20:29:10.481Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580487,'en_US') 
;

-- 2022-01-17T20:33:00.100Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541534,TO_TIMESTAMP('2022-01-17 22:32:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Incoterms',TO_TIMESTAMP('2022-01-17 22:32:59','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-01-17T20:33:00.195Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541534 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-01-17T20:33:41.865Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,577181,0,541534,541870,TO_TIMESTAMP('2022-01-17 22:33:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-01-17 22:33:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-17T20:34:18.502Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579095,580487,0,30,541534,291,'C_Incoterms_Vendor_ID',TO_TIMESTAMP('2022-01-17 22:34:16','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Incoterms (Lieferant)',0,0,TO_TIMESTAMP('2022-01-17 22:34:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-17T20:34:18.580Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579095 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-17T20:34:18.752Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(580487) 
;

-- 2022-01-17T20:34:35.364Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN C_Incoterms_Vendor_ID NUMERIC(10)')
;

-- 2022-01-17T20:34:36.865Z
-- URL zum Konzept
ALTER TABLE C_BPartner ADD CONSTRAINT CIncotermsVendor_CBPartner FOREIGN KEY (C_Incoterms_Vendor_ID) REFERENCES public.C_Incoterms DEFERRABLE INITIALLY DEFERRED
;

-- 2022-01-17T20:35:18.977Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579096,580486,0,30,541534,291,'C_Incoterms_Customer_ID',TO_TIMESTAMP('2022-01-17 22:35:18','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Incoterms (Kunde)',0,0,TO_TIMESTAMP('2022-01-17 22:35:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-17T20:35:19.052Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579096 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-17T20:35:19.255Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(580486) 
;

-- 2022-01-17T20:35:36.315Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN C_Incoterms_Customer_ID NUMERIC(10)')
;

-- 2022-01-17T20:35:37.763Z
-- URL zum Konzept
ALTER TABLE C_BPartner ADD CONSTRAINT CIncotermsCustomer_CBPartner FOREIGN KEY (C_Incoterms_Customer_ID) REFERENCES public.C_Incoterms DEFERRABLE INITIALLY DEFERRED
;

-- 2022-01-17T22:20:07.764Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=577179,Updated=TO_TIMESTAMP('2022-01-18 00:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541534
;

