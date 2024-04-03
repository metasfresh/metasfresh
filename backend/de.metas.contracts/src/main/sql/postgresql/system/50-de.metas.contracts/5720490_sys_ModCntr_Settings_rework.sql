-- Run mode: SWING_CLIENT

-- 2024-04-01T10:26:49.822Z
UPDATE AD_Table_Trl SET Name='Computing Method',Updated=TO_TIMESTAMP('2024-04-01 13:26:49.821','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542337
;

-- 2024-04-01T10:26:51.191Z
UPDATE AD_Table_Trl SET Name='Computing Method',Updated=TO_TIMESTAMP('2024-04-01 13:26:51.19','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542337
;

-- 2024-04-01T10:26:53.291Z
UPDATE AD_Table_Trl SET Name='Computing Method',Updated=TO_TIMESTAMP('2024-04-01 13:26:53.289','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Table_ID=542337
;

-- Element: ModCntr_Type_ID
-- 2024-04-01T10:27:18.560Z
UPDATE AD_Element_Trl SET Name='Computing Method',Updated=TO_TIMESTAMP('2024-04-01 13:27:18.56','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582395 AND AD_Language='en_US'
;

-- 2024-04-01T10:27:18.593Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582395,'en_US')
;

-- Reference Item: InvoicingGroup -> Leistung_Leistung
-- 2024-04-01T10:32:21.267Z
UPDATE AD_Ref_List_Trl SET Name='Service',Updated=TO_TIMESTAMP('2024-04-01 13:32:21.267','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543498
;

-- 2024-04-01T12:09:12.562Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583066,0,'CoProduct_ID',TO_TIMESTAMP('2024-04-01 15:09:12.299','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Co-Produkt','Co-Produkt',TO_TIMESTAMP('2024-04-01 15:09:12.299','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-01T12:09:12.569Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583066 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CoProduct_ID
-- 2024-04-01T12:09:40.908Z
UPDATE AD_Element_Trl SET PrintName='Co-Product',Updated=TO_TIMESTAMP('2024-04-01 15:09:40.907','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583066 AND AD_Language='en_US'
;

-- 2024-04-01T12:09:40.909Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583066,'en_US')
;

-- 2024-04-01T12:15:44.846Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583068,0,'M_Raw_Product_ID',TO_TIMESTAMP('2024-04-01 15:15:44.706','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Rohprodukt','Rohprodukt',TO_TIMESTAMP('2024-04-01 15:15:44.706','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-01T12:15:44.848Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583068 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: M_Raw_Product_ID
-- 2024-04-01T12:15:56.272Z
UPDATE AD_Element_Trl SET Name='Raw Product', PrintName='Raw Product',Updated=TO_TIMESTAMP('2024-04-01 15:15:56.272','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583068 AND AD_Language='en_US'
;

-- 2024-04-01T12:15:56.274Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583068,'en_US')
;

-- 2024-04-01T12:16:23.893Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583069,0,'M_Processed_Product_ID',TO_TIMESTAMP('2024-04-01 15:16:23.753','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Verarbeitetes Produkt','Verarbeitetes Produkt',TO_TIMESTAMP('2024-04-01 15:16:23.753','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-01T12:16:23.895Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583069 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: M_Processed_Product_ID
-- 2024-04-01T12:16:32.343Z
UPDATE AD_Element_Trl SET Name='Processed Product', PrintName='Processed Product',Updated=TO_TIMESTAMP('2024-04-01 15:16:32.343','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583069 AND AD_Language='en_US'
;

-- 2024-04-01T12:16:32.345Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583069,'en_US')
;

-- 2024-04-01T12:18:06.581Z
UPDATE AD_Element SET ColumnName='M_Co_Product_ID',Updated=TO_TIMESTAMP('2024-04-01 15:18:06.581','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583066
;

-- 2024-04-01T12:18:06.583Z
UPDATE AD_Column SET ColumnName='M_Co_Product_ID' WHERE AD_Element_ID=583066
;

-- 2024-04-01T12:18:06.584Z
UPDATE AD_Process_Para SET ColumnName='M_Co_Product_ID' WHERE AD_Element_ID=583066
;

-- 2024-04-01T12:18:06.586Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583066,'de_DE')
;

-- Column: ModCntr_Settings.M_Raw_Product_ID
-- 2024-04-01T12:19:22.127Z
UPDATE AD_Column SET AD_Element_ID=583068, ColumnName='M_Raw_Product_ID', Description=NULL, Help=NULL, Name='Rohprodukt',Updated=TO_TIMESTAMP('2024-04-01 15:19:22.127','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586790
;

-- 2024-04-01T12:19:22.129Z
UPDATE AD_Column_Trl trl SET Name='Rohprodukt' WHERE AD_Column_ID=586790 AND AD_Language='de_DE'
;

-- 2024-04-01T12:19:22.131Z
UPDATE AD_Field SET Name='Rohprodukt', Description=NULL, Help=NULL WHERE AD_Column_ID=586790
;

-- 2024-04-01T12:19:22.132Z
/* DDL */  select update_Column_Translation_From_AD_Element(583068)
;

-- Column: ModCntr_Settings.M_Raw_Product_ID
-- 2024-04-01T12:20:09.615Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-04-01 15:20:09.615','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586790
;

-- 2024-04-01T12:20:12.244Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE public.ModCntr_Settings ADD COLUMN M_Raw_Product_ID NUMERIC(10)')
;

-- 2024-04-01T12:20:12.249Z
ALTER TABLE ModCntr_Settings ADD CONSTRAINT MRawProduct_ModCntrSettings FOREIGN KEY (M_Raw_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Settings.M_Product_ID
-- 2024-04-01T12:21:47.359Z
UPDATE AD_Column SET AD_Element_ID=454, AD_Reference_ID=19, ColumnName='M_Product_ID', Description='Produkt, Leistung, Artikel', Help='Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.', IsExcludeFromZoomTargets='N', Name='Produkt',Updated=TO_TIMESTAMP('2024-04-01 15:21:47.359','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586790
;

-- 2024-04-01T12:21:47.361Z
UPDATE AD_Column_Trl trl SET Name='Produkt' WHERE AD_Column_ID=586790 AND AD_Language='de_DE'
;

-- 2024-04-01T12:21:47.362Z
UPDATE AD_Field SET Name='Produkt', Description='Produkt, Leistung, Artikel', Help='Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.' WHERE AD_Column_ID=586790
;

-- 2024-04-01T12:21:47.363Z
/* DDL */  select update_Column_Translation_From_AD_Element(454)
;

-- Column: ModCntr_Settings.M_Raw_Product_ID
-- 2024-04-01T12:22:39.965Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588085,583068,0,30,540272,542339,'M_Raw_Product_ID',TO_TIMESTAMP('2024-04-01 15:22:39.802','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rohprodukt',0,0,TO_TIMESTAMP('2024-04-01 15:22:39.802','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T12:22:39.966Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588085 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T12:22:39.968Z
/* DDL */  select update_Column_Translation_From_AD_Element(583068)
;

-- Column: ModCntr_Settings.M_Processed_Product_ID
-- 2024-04-01T12:30:49.243Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588086,583069,0,30,540272,542339,'M_Processed_Product_ID',TO_TIMESTAMP('2024-04-01 15:30:49.07','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verarbeitetes Produkt',0,0,TO_TIMESTAMP('2024-04-01 15:30:49.07','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T12:30:49.244Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588086 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T12:30:49.246Z
/* DDL */  select update_Column_Translation_From_AD_Element(583069)
;

-- 2024-04-01T12:30:56.809Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE public.ModCntr_Settings ADD COLUMN M_Processed_Product_ID NUMERIC(10)')
;

-- 2024-04-01T12:30:56.814Z
ALTER TABLE ModCntr_Settings ADD CONSTRAINT MProcessedProduct_ModCntrSettings FOREIGN KEY (M_Processed_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Settings.M_Co_Product_ID
-- 2024-04-01T12:31:17.856Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588087,583066,0,30,540272,542339,'M_Co_Product_ID',TO_TIMESTAMP('2024-04-01 15:31:17.726','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Co-Produkt',0,0,TO_TIMESTAMP('2024-04-01 15:31:17.726','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T12:31:17.857Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588087 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T12:31:17.858Z
/* DDL */  select update_Column_Translation_From_AD_Element(583066)
;

-- 2024-04-01T12:31:19.837Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE public.ModCntr_Settings ADD COLUMN M_Co_Product_ID NUMERIC(10)')
;

-- 2024-04-01T12:31:19.842Z
ALTER TABLE ModCntr_Settings ADD CONSTRAINT MCoProduct_ModCntrSettings FOREIGN KEY (M_Co_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Type.Value
-- 2024-04-01T13:01:37.881Z
UPDATE AD_Column SET FieldLength=250,Updated=TO_TIMESTAMP('2024-04-01 16:01:37.881','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586748
;

-- 2024-04-01T13:01:39.705Z
INSERT INTO t_alter_column values('modcntr_type','Value','VARCHAR(250)',null,null)
;

-- Column: ModCntr_Type.Name
-- 2024-04-01T13:01:55.960Z
UPDATE AD_Column SET FieldLength=250,Updated=TO_TIMESTAMP('2024-04-01 16:01:55.96','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586749
;

-- 2024-04-01T13:01:57.923Z
INSERT INTO t_alter_column values('modcntr_type','Name','VARCHAR(250)',null,null)
;

-- Column: ModCntr_Module.ModCntr_Settings_ID
-- 2024-04-01T13:03:20.977Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=541775, IsExcludeFromZoomTargets='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-01 16:03:20.977','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586804
;

-- Name: ModCntr_Type
-- 2024-04-01T13:03:46.936Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541861,TO_TIMESTAMP('2024-04-01 16:03:46.792','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','ModCntr_Type',TO_TIMESTAMP('2024-04-01 16:03:46.792','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2024-04-01T13:03:46.937Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541861 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Type
-- Table: ModCntr_Type
-- Key: ModCntr_Type.ModCntr_Type_ID
-- 2024-04-01T13:06:26.913Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,586747,0,541861,542337,541710,TO_TIMESTAMP('2024-04-01 16:06:26.905','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2024-04-01 16:06:26.905','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: ModCntr_Module.ModCntr_Type_ID
-- 2024-04-01T13:09:42.293Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=541861, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2024-04-01 16:09:42.293','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586809
;

-- Column: ModCntr_Type.Description
-- 2024-04-01T13:14:15.972Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2024-04-01 16:14:15.972','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586750
;

-- 2024-04-01T13:14:18.030Z
INSERT INTO t_alter_column values('modcntr_type','Description','VARCHAR(2000)',null,null)
;

-- 2024-04-01T13:39:35.989Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE ModCntr_Log DROP COLUMN IF EXISTS IsBillable')
;



-------------------



DELETE FROM ad_ui_element e WHERE exists ( select 1 from ad_field f where e.ad_field_id = f.ad_field_id and f.ad_column_id = 587302 );



DELETE FROM ad_field where AD_Column_ID=587302;



------------

-- Column: ModCntr_Log.IsBillable
-- 2024-04-01T13:39:36.135Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587302
;

-- 2024-04-01T13:39:36.138Z
DELETE FROM AD_Column WHERE AD_Column_ID=587302
;

-- Column: ModCntr_Log.ProductName
-- 2024-04-01T13:40:01.405Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588088,2659,0,10,542338,'ProductName',TO_TIMESTAMP('2024-04-01 16:40:01.181','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Name des Produktes','de.metas.contracts',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Produktname',0,0,TO_TIMESTAMP('2024-04-01 16:40:01.181','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T13:40:01.406Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588088 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T13:40:01.408Z
/* DDL */  select update_Column_Translation_From_AD_Element(2659)
;

-- 2024-04-01T13:40:03.412Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN ProductName VARCHAR(255)')
;

-- Column: ModCntr_Settings.M_PricingSystem_ID
-- 2024-04-01T14:21:41.363Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-01 17:21:41.363','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586794
;

-- 2024-04-01T14:21:43.382Z
INSERT INTO t_alter_column values('modcntr_settings','M_PricingSystem_ID','NUMERIC(10)',null,null)
;

-- 2024-04-01T14:21:43.385Z
INSERT INTO t_alter_column values('modcntr_settings','M_PricingSystem_ID',null,'NOT NULL',null)
;

-- Table: ModCntr_Specific_Price
-- 2024-04-01T14:25:55.927Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TechnicalNote,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542405,'N',TO_TIMESTAMP('2024-04-01 17:25:55.746','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','N','N','N','N','N','N','N',0,'Contract Specific Prices','NP','L','ModCntr_Specific_Price','Maintain contract specific prices for modular contracts.','DTI',TO_TIMESTAMP('2024-04-01 17:25:55.746','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:25:55.928Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542405 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-04-01T14:25:56.039Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556341,TO_TIMESTAMP('2024-04-01 17:25:55.951','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table ModCntr_Specific_Price',1,'Y','N','Y','Y','ModCntr_Specific_Price','N',1000000,TO_TIMESTAMP('2024-04-01 17:25:55.951','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-01T14:25:56.048Z
CREATE SEQUENCE MODCNTR_SPECIFIC_PRICE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: ModCntr_Specific_Price.AD_Client_ID
-- 2024-04-01T14:26:33.024Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588089,102,0,19,542405,'AD_Client_ID',TO_TIMESTAMP('2024-04-01 17:26:32.855','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-04-01 17:26:32.855','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:26:33.026Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588089 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:26:33.028Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: ModCntr_Specific_Price.AD_Org_ID
-- 2024-04-01T14:26:33.621Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588090,113,0,30,542405,'AD_Org_ID',TO_TIMESTAMP('2024-04-01 17:26:33.329','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-04-01 17:26:33.329','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:26:33.622Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588090 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:26:33.624Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: ModCntr_Specific_Price.Created
-- 2024-04-01T14:26:34.149Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588091,245,0,16,542405,'Created',TO_TIMESTAMP('2024-04-01 17:26:33.925','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-04-01 17:26:33.925','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:26:34.151Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588091 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:26:34.152Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: ModCntr_Specific_Price.CreatedBy
-- 2024-04-01T14:26:34.689Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588092,246,0,18,110,542405,'CreatedBy',TO_TIMESTAMP('2024-04-01 17:26:34.449','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-04-01 17:26:34.449','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:26:34.690Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588092 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:26:34.692Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: ModCntr_Specific_Price.IsActive
-- 2024-04-01T14:26:35.217Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588093,348,0,20,542405,'IsActive',TO_TIMESTAMP('2024-04-01 17:26:34.977','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-04-01 17:26:34.977','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:26:35.218Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588093 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:26:35.220Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: ModCntr_Specific_Price.Updated
-- 2024-04-01T14:26:35.782Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588094,607,0,16,542405,'Updated',TO_TIMESTAMP('2024-04-01 17:26:35.557','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-04-01 17:26:35.557','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:26:35.783Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588094 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:26:35.785Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: ModCntr_Specific_Price.UpdatedBy
-- 2024-04-01T14:26:36.371Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588095,608,0,18,110,542405,'UpdatedBy',TO_TIMESTAMP('2024-04-01 17:26:36.11','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-04-01 17:26:36.11','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:26:36.372Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588095 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:26:36.374Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2024-04-01T14:26:36.816Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583070,0,'ModCntr_Specific_Price_ID',TO_TIMESTAMP('2024-04-01 17:26:36.704','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Contract Specific Prices','Contract Specific Prices',TO_TIMESTAMP('2024-04-01 17:26:36.704','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-01T14:26:36.817Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583070 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ModCntr_Specific_Price.ModCntr_Specific_Price_ID
-- 2024-04-01T14:26:37.410Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588096,583070,0,13,542405,'ModCntr_Specific_Price_ID',TO_TIMESTAMP('2024-04-01 17:26:36.701','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Contract Specific Prices',0,0,TO_TIMESTAMP('2024-04-01 17:26:36.701','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:26:37.411Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588096 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:26:37.414Z
/* DDL */  select update_Column_Translation_From_AD_Element(583070)
;

-- 2024-04-01T14:26:37.741Z
/* DDL */ CREATE TABLE public.ModCntr_Specific_Price (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, ModCntr_Specific_Price_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ModCntr_Specific_Price_Key PRIMARY KEY (ModCntr_Specific_Price_ID))
;

-- 2024-04-01T14:26:37.758Z
INSERT INTO t_alter_column values('modcntr_specific_price','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2024-04-01T14:26:37.768Z
INSERT INTO t_alter_column values('modcntr_specific_price','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-04-01T14:26:37.777Z
INSERT INTO t_alter_column values('modcntr_specific_price','CreatedBy','NUMERIC(10)',null,null)
;

-- 2024-04-01T14:26:37.786Z
INSERT INTO t_alter_column values('modcntr_specific_price','IsActive','CHAR(1)',null,null)
;

-- 2024-04-01T14:26:37.800Z
INSERT INTO t_alter_column values('modcntr_specific_price','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-04-01T14:26:37.809Z
INSERT INTO t_alter_column values('modcntr_specific_price','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2024-04-01T14:26:37.818Z
INSERT INTO t_alter_column values('modcntr_specific_price','ModCntr_Specific_Price_ID','NUMERIC(10)',null,null)
;

-- Column: ModCntr_Specific_Price.C_Flatrate_Term_ID
-- 2024-04-01T14:35:39.281Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588097,541447,0,19,542405,'C_Flatrate_Term_ID',TO_TIMESTAMP('2024-04-01 17:35:39.143','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Pauschale - Vertragsperiode',0,0,TO_TIMESTAMP('2024-04-01 17:35:39.143','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:35:39.282Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588097 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:35:39.284Z
/* DDL */  select update_Column_Translation_From_AD_Element(541447)
;

-- 2024-04-01T14:35:41.161Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE public.ModCntr_Specific_Price ADD COLUMN C_Flatrate_Term_ID NUMERIC(10) NOT NULL')
;

-- 2024-04-01T14:35:41.166Z
ALTER TABLE ModCntr_Specific_Price ADD CONSTRAINT CFlatrateTerm_ModCntrSpecificPrice FOREIGN KEY (C_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Specific_Price.SeqNo
-- 2024-04-01T14:36:18.247Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588098,566,0,11,542405,'SeqNo',TO_TIMESTAMP('2024-04-01 17:36:18.113','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',0,22,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2024-04-01 17:36:18.113','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:36:18.248Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588098 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:36:18.250Z
/* DDL */  select update_Column_Translation_From_AD_Element(566)
;

-- 2024-04-01T14:36:25.154Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE public.ModCntr_Specific_Price ADD COLUMN SeqNo NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-04-01T14:37:00.516Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588099,582426,0,19,542405,'ModCntr_Module_ID',TO_TIMESTAMP('2024-04-01 17:37:00.322','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Bausteine',0,0,TO_TIMESTAMP('2024-04-01 17:37:00.322','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:37:00.517Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588099 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:37:00.519Z
/* DDL */  select update_Column_Translation_From_AD_Element(582426)
;

-- 2024-04-01T14:37:02.795Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE public.ModCntr_Specific_Price ADD COLUMN ModCntr_Module_ID NUMERIC(10) NOT NULL')
;

-- 2024-04-01T14:37:02.800Z
ALTER TABLE ModCntr_Specific_Price ADD CONSTRAINT ModCntrModule_ModCntrSpecificPrice FOREIGN KEY (ModCntr_Module_ID) REFERENCES public.ModCntr_Module DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Specific_Price.M_Product_ID
-- 2024-04-01T14:37:32.260Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588100,454,0,19,542405,'M_Product_ID',TO_TIMESTAMP('2024-04-01 17:37:32.126','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Produkt, Leistung, Artikel','D',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2024-04-01 17:37:32.126','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:37:32.261Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588100 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:37:32.263Z
/* DDL */  select update_Column_Translation_From_AD_Element(454)
;

-- 2024-04-01T14:37:34.227Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE public.ModCntr_Specific_Price ADD COLUMN M_Product_ID NUMERIC(10) NOT NULL')
;

-- 2024-04-01T14:37:34.231Z
ALTER TABLE ModCntr_Specific_Price ADD CONSTRAINT MProduct_ModCntrSpecificPrice FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Specific_Price.Price
-- 2024-04-01T14:37:46.970Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588101,1416,0,37,542405,'Price',TO_TIMESTAMP('2024-04-01 17:37:46.839','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Preis','D',0,10,'Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Preis',0,0,TO_TIMESTAMP('2024-04-01 17:37:46.839','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:37:46.972Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588101 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:37:46.974Z
/* DDL */  select update_Column_Translation_From_AD_Element(1416)
;

-- 2024-04-01T14:38:03.822Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE public.ModCntr_Specific_Price ADD COLUMN Price NUMERIC NOT NULL')
;

-- Column: ModCntr_Specific_Price.PriceUOM
-- 2024-04-01T14:44:21.563Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588102,582754,0,10,542405,'PriceUOM',TO_TIMESTAMP('2024-04-01 17:44:21.322','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Preiseinheit',0,0,TO_TIMESTAMP('2024-04-01 17:44:21.322','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:44:21.564Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588102 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:44:21.566Z
/* DDL */  select update_Column_Translation_From_AD_Element(582754)
;

-- 2024-04-01T14:44:23.360Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE public.ModCntr_Specific_Price ADD COLUMN PriceUOM VARCHAR(40)')
;

-- Column: ModCntr_Specific_Price.C_Currency_ID
-- 2024-04-01T14:44:42.882Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588103,193,0,19,542405,'C_Currency_ID',TO_TIMESTAMP('2024-04-01 17:44:42.745','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Die Währung für diesen Eintrag','D',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2024-04-01 17:44:42.745','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:44:42.883Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588103 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:44:42.885Z
/* DDL */  select update_Column_Translation_From_AD_Element(193)
;

-- 2024-04-01T14:44:45.021Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE public.ModCntr_Specific_Price ADD COLUMN C_Currency_ID NUMERIC(10)')
;

-- 2024-04-01T14:44:45.026Z
ALTER TABLE ModCntr_Specific_Price ADD CONSTRAINT CCurrency_ModCntrSpecificPrice FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Specific_Price.C_TaxCategory_ID
-- 2024-04-01T14:46:51.574Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588104,211,0,19,542405,'C_TaxCategory_ID',TO_TIMESTAMP('2024-04-01 17:46:51.42','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Steuerkategorie','D',0,10,'Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Steuerkategorie',0,0,TO_TIMESTAMP('2024-04-01 17:46:51.42','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-01T14:46:51.575Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588104 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-01T14:46:51.577Z
/* DDL */  select update_Column_Translation_From_AD_Element(211)
;

-- 2024-04-01T14:46:54.035Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE public.ModCntr_Specific_Price ADD COLUMN C_TaxCategory_ID NUMERIC(10)')
;

-- 2024-04-01T14:46:54.039Z
ALTER TABLE ModCntr_Specific_Price ADD CONSTRAINT CTaxCategory_ModCntrSpecificPrice FOREIGN KEY (C_TaxCategory_ID) REFERENCES public.C_TaxCategory DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Specific_Price.C_TaxCategory_ID
-- 2024-04-01T14:52:45.304Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-01 17:52:45.304','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588104
;

-- 2024-04-01T14:52:47.256Z
INSERT INTO t_alter_column values('modcntr_specific_price','C_TaxCategory_ID','NUMERIC(10)',null,null)
;

-- 2024-04-01T14:52:47.257Z
INSERT INTO t_alter_column values('modcntr_specific_price','C_TaxCategory_ID',null,'NOT NULL',null)
;

-- Column: ModCntr_Specific_Price.C_Currency_ID
-- 2024-04-01T14:53:27.245Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-01 17:53:27.244','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588103
;

-- 2024-04-01T14:53:27.781Z
INSERT INTO t_alter_column values('modcntr_specific_price','C_Currency_ID','NUMERIC(10)',null,null)
;

-- 2024-04-01T14:53:27.782Z
INSERT INTO t_alter_column values('modcntr_specific_price','C_Currency_ID',null,'NOT NULL',null)
;

-- Column: ModCntr_Specific_Price.PriceUOM
-- 2024-04-01T14:53:32.175Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-01 17:53:32.175','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588102
;

-- 2024-04-01T14:53:32.732Z
INSERT INTO t_alter_column values('modcntr_specific_price','PriceUOM','VARCHAR(40)',null,null)
;

-- 2024-04-01T14:53:32.734Z
INSERT INTO t_alter_column values('modcntr_specific_price','PriceUOM',null,'NOT NULL',null)
;

