-- 2023-11-29T12:47:24.157Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582822,0,'IsReceiveMassDunningReports',TO_TIMESTAMP('2023-11-29 14:47:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Receives Dunning History Report','Receives Dunning History Report',TO_TIMESTAMP('2023-11-29 14:47:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-29T12:47:24.169Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582822 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-11-29T12:47:52.441Z
UPDATE AD_Element_Trl SET Name='Erhält Mahnhistorienbericht', PrintName='Erhält Mahnhistorienbericht',Updated=TO_TIMESTAMP('2023-11-29 14:47:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582822 AND AD_Language='de_CH'
;

-- 2023-11-29T12:47:52.468Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582822,'de_CH') 
;

-- 2023-11-29T12:47:56.055Z
UPDATE AD_Element_Trl SET Name='Erhält Mahnhistorienbericht', PrintName='Erhält Mahnhistorienbericht',Updated=TO_TIMESTAMP('2023-11-29 14:47:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582822 AND AD_Language='de_DE'
;

-- 2023-11-29T12:47:56.057Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582822,'de_DE') 
;

-- 2023-11-29T12:47:58.187Z
UPDATE AD_Element_Trl SET Name='Erhält Mahnhistorienbericht', PrintName='Erhält Mahnhistorienbericht',Updated=TO_TIMESTAMP('2023-11-29 14:47:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582822 AND AD_Language='fr_CH'
;

-- 2023-11-29T12:47:58.188Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582822,'fr_CH') 
;

-- 2023-11-29T12:48:01.494Z
UPDATE AD_Element_Trl SET Name='Erhält Mahnhistorienbericht', PrintName='Erhält Mahnhistorienbericht',Updated=TO_TIMESTAMP('2023-11-29 14:48:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582822 AND AD_Language='nl_NL'
;

-- 2023-11-29T12:48:01.496Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582822,'nl_NL') 
;

-- Column: AD_User.IsReceiveMassDunningReports
-- 2023-11-29T12:49:03.549Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587665,582822,0,20,114,'IsReceiveMassDunningReports',TO_TIMESTAMP('2023-11-29 14:49:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Receives Dunning History Report',0,0,TO_TIMESTAMP('2023-11-29 14:49:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-11-29T12:49:03.551Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587665 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-29T12:49:03.554Z
/* DDL */  select update_Column_Translation_From_AD_Element(582822) 
;

-- 2023-11-29T13:05:37.369Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582823,0,'Mass_Generate_Boilerplate_ID',TO_TIMESTAMP('2023-11-29 15:05:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mass generation text module','Mass generation text module',TO_TIMESTAMP('2023-11-29 15:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-29T13:05:37.371Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582823 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-11-29T13:05:43.717Z
UPDATE AD_Element_Trl SET Name='Textbaustein bei massenhafter Belegerstellung', PrintName='Textbaustein bei massenhafter Belegerstellung',Updated=TO_TIMESTAMP('2023-11-29 15:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582823 AND AD_Language='de_CH'
;

-- 2023-11-29T13:05:43.718Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582823,'de_CH') 
;

-- 2023-11-29T13:05:46.175Z
UPDATE AD_Element_Trl SET Name='Textbaustein bei massenhafter Belegerstellung', PrintName='Textbaustein bei massenhafter Belegerstellung',Updated=TO_TIMESTAMP('2023-11-29 15:05:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582823 AND AD_Language='de_DE'
;

-- 2023-11-29T13:05:46.177Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582823,'de_DE') 
;

-- 2023-11-29T13:05:47.831Z
UPDATE AD_Element_Trl SET Name='Textbaustein bei massenhafter Belegerstellung', PrintName='Textbaustein bei massenhafter Belegerstellung',Updated=TO_TIMESTAMP('2023-11-29 15:05:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582823 AND AD_Language='fr_CH'
;

-- 2023-11-29T13:05:47.832Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582823,'fr_CH') 
;

-- 2023-11-29T13:05:50.538Z
UPDATE AD_Element_Trl SET Name='Textbaustein bei massenhafter Belegerstellung', PrintName='Textbaustein bei massenhafter Belegerstellung',Updated=TO_TIMESTAMP('2023-11-29 15:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582823 AND AD_Language='nl_NL'
;

-- 2023-11-29T13:05:50.539Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582823,'nl_NL') 
;

-- 2023-11-29T13:06:31.646Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582824,0,'Mass_Generate_Line_Boilerplate_ID',TO_TIMESTAMP('2023-11-29 15:06:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mass generation line text module','Mass generation line text module',TO_TIMESTAMP('2023-11-29 15:06:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-29T13:06:31.647Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582824 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-11-29T13:07:15.326Z
UPDATE AD_Element_Trl SET Name='Textbaustein für Positionen massenhaft erstellter Belege', PrintName='Textbaustein für Positionen massenhaft erstellter Belege',Updated=TO_TIMESTAMP('2023-11-29 15:07:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582824 AND AD_Language='de_CH'
;

-- 2023-11-29T13:07:15.328Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582824,'de_CH') 
;

-- 2023-11-29T13:07:17.081Z
UPDATE AD_Element_Trl SET Name='Textbaustein für Positionen massenhaft erstellter Belege', PrintName='Textbaustein für Positionen massenhaft erstellter Belege',Updated=TO_TIMESTAMP('2023-11-29 15:07:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582824 AND AD_Language='de_DE'
;

-- 2023-11-29T13:07:17.083Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582824,'de_DE') 
;

-- 2023-11-29T13:07:18.551Z
UPDATE AD_Element_Trl SET Name='Textbaustein für Positionen massenhaft erstellter Belege', PrintName='Textbaustein für Positionen massenhaft erstellter Belege',Updated=TO_TIMESTAMP('2023-11-29 15:07:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582824 AND AD_Language='fr_CH'
;

-- 2023-11-29T13:07:18.552Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582824,'fr_CH') 
;

-- 2023-11-29T13:07:20.795Z
UPDATE AD_Element_Trl SET Name='Textbaustein für Positionen massenhaft erstellter Belege', PrintName='Textbaustein für Positionen massenhaft erstellter Belege',Updated=TO_TIMESTAMP('2023-11-29 15:07:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582824 AND AD_Language='nl_NL'
;

-- 2023-11-29T13:07:20.797Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582824,'nl_NL') 
;

-- Column: C_DocType.Mass_Generate_Boilerplate_ID
-- 2023-11-29T16:10:31.113Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587666,582823,0,30,540008,217,'Mass_Generate_Boilerplate_ID',TO_TIMESTAMP('2023-11-29 18:10:30','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Mass generation text module',0,0,TO_TIMESTAMP('2023-11-29 18:10:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-11-29T16:10:31.115Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587666 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-29T16:10:31.118Z
/* DDL */  select update_Column_Translation_From_AD_Element(582823) 
;

-- 2023-11-29T16:10:33.531Z
/* DDL */ SELECT public.db_alter_table('C_DocType','ALTER TABLE public.C_DocType ADD COLUMN Mass_Generate_Boilerplate_ID NUMERIC(10)')
;

-- 2023-11-29T16:10:34.235Z
ALTER TABLE C_DocType ADD CONSTRAINT MassGenerateBoilerplate_CDocType FOREIGN KEY (Mass_Generate_Boilerplate_ID) REFERENCES public.AD_BoilerPlate DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_DocType.Mass_Generate_Line_Boilerplate_ID
-- 2023-11-29T16:10:58.671Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587667,582824,0,30,540008,217,'Mass_Generate_Line_Boilerplate_ID',TO_TIMESTAMP('2023-11-29 18:10:58','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Mass generation line text module',0,0,TO_TIMESTAMP('2023-11-29 18:10:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-11-29T16:10:58.673Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587667 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-29T16:10:58.676Z
/* DDL */  select update_Column_Translation_From_AD_Element(582824) 
;

-- 2023-11-29T16:11:00.439Z
/* DDL */ SELECT public.db_alter_table('C_DocType','ALTER TABLE public.C_DocType ADD COLUMN Mass_Generate_Line_Boilerplate_ID NUMERIC(10)')
;

-- 2023-11-29T16:11:00.842Z
ALTER TABLE C_DocType ADD CONSTRAINT MassGenerateLineBoilerplate_CDocType FOREIGN KEY (Mass_Generate_Line_Boilerplate_ID) REFERENCES public.AD_BoilerPlate DEFERRABLE INITIALLY DEFERRED
;

-- 2023-11-29T16:11:39.461Z
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN IsReceiveMassDunningReports CHAR(1) DEFAULT ''N'' CHECK (IsReceiveMassDunningReports IN (''Y'',''N'')) NOT NULL')
;

-- 2023-11-29T16:12:47.013Z
UPDATE AD_Element_Trl SET Name='Text snippet for line items of documents created in bulk', PrintName='Text snippet for line items of documents created in bulk',Updated=TO_TIMESTAMP('2023-11-29 18:12:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582824 AND AD_Language='en_US'
;

-- 2023-11-29T16:12:47.015Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582824,'en_US') 
;

-- 2023-11-29T16:12:47.016Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582824,'en_US') 
;

-- 2023-11-29T16:13:04.372Z
UPDATE AD_Element_Trl SET Name='Text snippet for bulk document creation', PrintName='Text snippet for bulk document creation',Updated=TO_TIMESTAMP('2023-11-29 18:13:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582823 AND AD_Language='en_US'
;

-- 2023-11-29T16:13:04.373Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582823,'en_US') 
;

-- 2023-11-29T16:13:04.374Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582823,'en_US') 
;

-- 2023-11-29T16:39:29.785Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582825,0,'Deadletter_User_ID',TO_TIMESTAMP('2023-11-29 18:39:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Forward undeliverable emails to','Forward undeliverable emails to',TO_TIMESTAMP('2023-11-29 18:39:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-29T16:39:29.787Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582825 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-11-29T16:39:41.770Z
UPDATE AD_Element_Trl SET Name='Unzustellbare E-Mails weiterleiten an', PrintName='Unzustellbare E-Mails weiterleiten an',Updated=TO_TIMESTAMP('2023-11-29 18:39:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582825 AND AD_Language='de_CH'
;

-- 2023-11-29T16:39:41.771Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582825,'de_CH') 
;

-- 2023-11-29T16:39:43.789Z
UPDATE AD_Element_Trl SET Name='Unzustellbare E-Mails weiterleiten an', PrintName='Unzustellbare E-Mails weiterleiten an',Updated=TO_TIMESTAMP('2023-11-29 18:39:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582825 AND AD_Language='de_DE'
;

-- 2023-11-29T16:39:43.791Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582825,'de_DE') 
;

-- 2023-11-29T16:39:45.560Z
UPDATE AD_Element_Trl SET Name='Unzustellbare E-Mails weiterleiten an', PrintName='Unzustellbare E-Mails weiterleiten an',Updated=TO_TIMESTAMP('2023-11-29 18:39:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582825 AND AD_Language='fr_CH'
;

-- 2023-11-29T16:39:45.562Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582825,'fr_CH') 
;

-- 2023-11-29T16:39:47.950Z
UPDATE AD_Element_Trl SET Name='Unzustellbare E-Mails weiterleiten an', PrintName='Unzustellbare E-Mails weiterleiten an',Updated=TO_TIMESTAMP('2023-11-29 18:39:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582825 AND AD_Language='nl_NL'
;

-- 2023-11-29T16:39:47.952Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582825,'nl_NL') 
;

-- Column: AD_NotificationGroup.Deadletter_User_ID
-- 2023-11-29T16:41:30.955Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587668,582825,0,30,110,540959,'Deadletter_User_ID',TO_TIMESTAMP('2023-11-29 18:41:30','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Forward undeliverable emails to',0,0,TO_TIMESTAMP('2023-11-29 18:41:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-11-29T16:41:30.956Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587668 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-29T16:41:30.958Z
/* DDL */  select update_Column_Translation_From_AD_Element(582825) 
;

-- 2023-11-29T16:41:35.391Z
/* DDL */ SELECT public.db_alter_table('AD_NotificationGroup','ALTER TABLE public.AD_NotificationGroup ADD COLUMN Deadletter_User_ID NUMERIC(10)')
;

-- 2023-11-29T16:41:35.397Z
ALTER TABLE AD_NotificationGroup ADD CONSTRAINT DeadletterUser_ADNotificationGroup FOREIGN KEY (Deadletter_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2023-11-29T16:57:53.286Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582826,0,'IsRestrictToOrgBPSubscribers',TO_TIMESTAMP('2023-11-29 18:57:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Only organization business partner users can subscribe','Only organization business partner users can subscribe',TO_TIMESTAMP('2023-11-29 18:57:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-29T16:57:53.288Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582826 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-11-29T16:58:48.398Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=582826
;

-- 2023-11-29T16:58:48.404Z
DELETE FROM AD_Element WHERE AD_Element_ID=582826
;

-- 2023-11-29T17:00:38.749Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582827,0,'IsNotifyOrgBPUsersOnly',TO_TIMESTAMP('2023-11-29 19:00:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Notify only users of the organization''s business partners','Notify only users of the organization''s business partners',TO_TIMESTAMP('2023-11-29 19:00:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-29T17:00:38.750Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582827 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-11-29T17:00:56.492Z
UPDATE AD_Element_Trl SET Name='Nur Benutzer der Partner der Organisation benachrichtigen', PrintName='Nur Benutzer der Partner der Organisation benachrichtigen',Updated=TO_TIMESTAMP('2023-11-29 19:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582827 AND AD_Language='de_CH'
;

-- 2023-11-29T17:00:56.494Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582827,'de_CH')
;

-- 2023-11-29T17:00:58.777Z
UPDATE AD_Element_Trl SET Name='Nur Benutzer der Partner der Organisation benachrichtigen', PrintName='Nur Benutzer der Partner der Organisation benachrichtigen',Updated=TO_TIMESTAMP('2023-11-29 19:00:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582827 AND AD_Language='de_DE'
;

-- 2023-11-29T17:00:58.778Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582827,'de_DE')
;

-- 2023-11-29T17:01:00.641Z
UPDATE AD_Element_Trl SET Name='Nur Benutzer der Partner der Organisation benachrichtigen', PrintName='Nur Benutzer der Partner der Organisation benachrichtigen',Updated=TO_TIMESTAMP('2023-11-29 19:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582827 AND AD_Language='fr_CH'
;

-- 2023-11-29T17:01:00.642Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582827,'fr_CH')
;

-- 2023-11-29T17:01:03.075Z
UPDATE AD_Element_Trl SET Name='Nur Benutzer der Partner der Organisation benachrichtigen', PrintName='Nur Benutzer der Partner der Organisation benachrichtigen',Updated=TO_TIMESTAMP('2023-11-29 19:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582827 AND AD_Language='nl_NL'
;

-- 2023-11-29T17:01:03.077Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582827,'nl_NL')
;

-- Column: AD_NotificationGroup.IsNotifyOrgBPUsersOnly
-- 2023-11-29T17:01:21.794Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587669,582827,0,20,540959,'IsNotifyOrgBPUsersOnly',TO_TIMESTAMP('2023-11-29 19:01:21','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Notify only users of the organization''s business partners',0,0,TO_TIMESTAMP('2023-11-29 19:01:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-11-29T17:01:21.795Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587669 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-29T17:01:21.798Z
/* DDL */  select update_Column_Translation_From_AD_Element(582827) 
;

-- 2023-11-29T17:01:23.318Z
/* DDL */ SELECT public.db_alter_table('AD_NotificationGroup','ALTER TABLE public.AD_NotificationGroup ADD COLUMN IsNotifyOrgBPUsersOnly CHAR(1) DEFAULT ''N'' CHECK (IsNotifyOrgBPUsersOnly IN (''Y'',''N'')) NOT NULL')
;


-- Field: Belegart -> Belegart -> Textbaustein bei massenhafter Belegerstellung
-- Column: C_DocType.Mass_Generate_Boilerplate_ID
-- 2023-12-12T23:09:22.104Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587666,723198,0,167,0,TO_TIMESTAMP('2023-12-13 01:09:21','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Textbaustein bei massenhafter Belegerstellung',0,340,0,1,1,TO_TIMESTAMP('2023-12-13 01:09:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-12T23:09:22.105Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723198 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-12T23:09:22.107Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582823)
;

-- 2023-12-12T23:09:22.110Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723198
;

-- 2023-12-12T23:09:22.111Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723198)
;

-- Field: Belegart -> Belegart -> Textbaustein für Positionen massenhaft erstellter Belege
-- Column: C_DocType.Mass_Generate_Line_Boilerplate_ID
-- 2023-12-12T23:09:35.333Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587667,723199,0,167,0,TO_TIMESTAMP('2023-12-13 01:09:35','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Textbaustein für Positionen massenhaft erstellter Belege',0,350,0,1,1,TO_TIMESTAMP('2023-12-13 01:09:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-12T23:09:35.335Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723199 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-12T23:09:35.336Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582824)
;

-- 2023-12-12T23:09:35.338Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723199
;

-- 2023-12-12T23:09:35.338Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723199)
;

-- UI Element: Belegart -> Belegart.Textbaustein bei massenhafter Belegerstellung
-- Column: C_DocType.Mass_Generate_Boilerplate_ID
-- 2023-12-12T23:10:16.564Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723198,0,167,540408,621980,'F',TO_TIMESTAMP('2023-12-13 01:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Textbaustein bei massenhafter Belegerstellung',30,0,0,TO_TIMESTAMP('2023-12-13 01:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Belegart -> Belegart.Textbaustein für Positionen massenhaft erstellter Belege
-- Column: C_DocType.Mass_Generate_Line_Boilerplate_ID
-- 2023-12-12T23:10:26.012Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723199,0,167,540408,621981,'F',TO_TIMESTAMP('2023-12-13 01:10:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Textbaustein für Positionen massenhaft erstellter Belege',40,0,0,TO_TIMESTAMP('2023-12-13 01:10:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Belegart -> Belegart -> Textbaustein für Positionen massenhaft erstellter Belege
-- Column: C_DocType.Mass_Generate_Line_Boilerplate_ID
-- 2023-12-12T23:12:07.890Z
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=''DUN''',Updated=TO_TIMESTAMP('2023-12-13 01:12:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=723199
;

-- Field: Belegart -> Belegart -> Textbaustein bei massenhafter Belegerstellung
-- Column: C_DocType.Mass_Generate_Boilerplate_ID
-- 2023-12-12T23:12:13.814Z
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=''DUN''',Updated=TO_TIMESTAMP('2023-12-13 01:12:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=723198
;
