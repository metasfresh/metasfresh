-- 2021-10-19T08:21:44.666Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580059,0,'MandantoryOnReceipt',TO_TIMESTAMP('2021-10-19 11:21:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mandantory On Receipt','Mandantory On Receipt',TO_TIMESTAMP('2021-10-19 11:21:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-19T08:21:44.826Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580059 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-10-19T08:22:10.533Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580060,0,'MandatoryOnPicking',TO_TIMESTAMP('2021-10-19 11:22:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mandatory On Picking','Mandatory On Picking',TO_TIMESTAMP('2021-10-19 11:22:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-19T08:22:10.576Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580060 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-10-19T08:22:33.443Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580061,0,'MandatoryOnShipment',TO_TIMESTAMP('2021-10-19 11:22:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mandatory On Shipment','Mandatory On Shipment',TO_TIMESTAMP('2021-10-19 11:22:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-19T08:22:33.474Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580061 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-10-19T08:24:27.725Z
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='MandatoryOnReceipt',Updated=TO_TIMESTAMP('2021-10-19 11:24:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580059
;

-- 2021-10-19T08:24:27.851Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='MandatoryOnReceipt', Name='Mandantory On Receipt', Description=NULL, Help=NULL WHERE AD_Element_ID=580059
;

-- 2021-10-19T08:24:27.878Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='MandatoryOnReceipt', Name='Mandantory On Receipt', Description=NULL, Help=NULL, AD_Element_ID=580059 WHERE UPPER(ColumnName)='MANDATORYONRECEIPT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-19T08:24:27.930Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='MandatoryOnReceipt', Name='Mandantory On Receipt', Description=NULL, Help=NULL WHERE AD_Element_ID=580059 AND IsCentrallyMaintained='Y'
;

-- 2021-10-19T08:24:39.762Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Mandatory On Receipt', PrintName='Mandatory On Receipt',Updated=TO_TIMESTAMP('2021-10-19 11:24:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580059 AND AD_Language='de_DE'
;

-- 2021-10-19T08:24:39.819Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580059,'de_DE') 
;

-- 2021-10-19T08:24:39.903Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(580059,'de_DE') 
;

-- 2021-10-19T08:24:39.930Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='MandatoryOnReceipt', Name='Mandatory On Receipt', Description=NULL, Help=NULL WHERE AD_Element_ID=580059
;

-- 2021-10-19T08:24:39.963Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='MandatoryOnReceipt', Name='Mandatory On Receipt', Description=NULL, Help=NULL, AD_Element_ID=580059 WHERE UPPER(ColumnName)='MANDATORYONRECEIPT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-19T08:24:39.989Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='MandatoryOnReceipt', Name='Mandatory On Receipt', Description=NULL, Help=NULL WHERE AD_Element_ID=580059 AND IsCentrallyMaintained='Y'
;

-- 2021-10-19T08:24:40.031Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Mandatory On Receipt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580059) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580059)
;

-- 2021-10-19T08:24:40.093Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Mandatory On Receipt', Name='Mandatory On Receipt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580059)
;

-- 2021-10-19T08:24:40.135Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Mandatory On Receipt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580059
;

-- 2021-10-19T08:24:40.167Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Mandatory On Receipt', Description=NULL, Help=NULL WHERE AD_Element_ID = 580059
;

-- 2021-10-19T08:24:40.198Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Mandatory On Receipt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580059
;

-- 2021-10-19T08:24:49.106Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Mandatory On Receipt', PrintName='Mandatory On Receipt',Updated=TO_TIMESTAMP('2021-10-19 11:24:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580059 AND AD_Language='de_CH'
;

-- 2021-10-19T08:24:49.144Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580059,'de_CH') 
;

-- 2021-10-19T08:24:52.873Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Mandatory On Receipt', PrintName='Mandatory On Receipt',Updated=TO_TIMESTAMP('2021-10-19 11:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580059 AND AD_Language='en_US'
;

-- 2021-10-19T08:24:52.904Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580059,'en_US') 
;

-- 2021-10-19T08:24:57.256Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Mandatory On Receipt', PrintName='Mandatory On Receipt',Updated=TO_TIMESTAMP('2021-10-19 11:24:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580059 AND AD_Language='nl_NL'
;

-- 2021-10-19T08:24:57.288Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580059,'nl_NL') 
;

-- 2021-10-19T08:27:07.640Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,577789,580059,0,17,319,563,'MandatoryOnReceipt',TO_TIMESTAMP('2021-10-19 11:27:07','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Mandatory On Receipt',0,0,TO_TIMESTAMP('2021-10-19 11:27:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-19T08:27:07.679Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=577789 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-19T08:27:07.757Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(580059) 
;

-- 2021-10-19T08:27:19.502Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2021-10-19 11:27:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577789
;

-- 2021-10-19T08:27:24.539Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_AttributeUse','ALTER TABLE public.M_AttributeUse ADD COLUMN MandatoryOnReceipt CHAR(1)')
;

-- 2021-10-19T08:28:00.945Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,577790,580060,0,17,319,563,'MandatoryOnPicking',TO_TIMESTAMP('2021-10-19 11:28:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Mandatory On Picking',0,0,TO_TIMESTAMP('2021-10-19 11:28:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-19T08:28:00.988Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=577790 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-19T08:28:01.051Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(580060) 
;

-- 2021-10-19T08:28:20.656Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2021-10-19 11:28:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577789
;

-- 2021-10-19T08:28:24.203Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_attributeuse','MandatoryOnReceipt','VARCHAR(10)',null,null)
;

-- 2021-10-19T08:28:32.031Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=1,Updated=TO_TIMESTAMP('2021-10-19 11:28:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577789
;

-- 2021-10-19T08:28:35.664Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_attributeuse','MandatoryOnReceipt','CHAR(1)',null,null)
;

-- 2021-10-19T08:28:42.232Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=1,Updated=TO_TIMESTAMP('2021-10-19 11:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577790
;

-- 2021-10-19T08:28:45.828Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_AttributeUse','ALTER TABLE public.M_AttributeUse ADD COLUMN MandatoryOnPicking CHAR(1)')
;

-- 2021-10-19T08:29:09.386Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,577791,580061,0,17,319,563,'MandatoryOnShipment',TO_TIMESTAMP('2021-10-19 11:29:08','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Mandatory On Shipment',0,0,TO_TIMESTAMP('2021-10-19 11:29:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-19T08:29:09.424Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=577791 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-19T08:29:09.485Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(580061) 
;

-- 2021-10-19T08:29:13.631Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_AttributeUse','ALTER TABLE public.M_AttributeUse ADD COLUMN MandatoryOnShipment CHAR(1)')
;

-- 2021-10-19T08:29:30.922Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,577789,664619,0,467,TO_TIMESTAMP('2021-10-19 11:29:30','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Mandatory On Receipt',TO_TIMESTAMP('2021-10-19 11:29:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-19T08:29:30.959Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=664619 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-10-19T08:29:31.006Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580059) 
;

-- 2021-10-19T08:29:31.043Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=664619
;

-- 2021-10-19T08:29:31.090Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(664619)
;

-- 2021-10-19T08:29:31.560Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,577790,664620,0,467,TO_TIMESTAMP('2021-10-19 11:29:31','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Mandatory On Picking',TO_TIMESTAMP('2021-10-19 11:29:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-19T08:29:31.591Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=664620 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-10-19T08:29:31.623Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580060) 
;

-- 2021-10-19T08:29:31.660Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=664620
;

-- 2021-10-19T08:29:31.691Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(664620)
;

-- 2021-10-19T08:29:32.145Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,577791,664621,0,467,TO_TIMESTAMP('2021-10-19 11:29:31','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Mandatory On Shipment',TO_TIMESTAMP('2021-10-19 11:29:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-19T08:29:32.177Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=664621 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-10-19T08:29:32.223Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580061) 
;

-- 2021-10-19T08:29:32.245Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=664621
;

-- 2021-10-19T08:29:32.277Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(664621)
;

-- 2021-10-19T08:33:36.775Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2021-10-19 11:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544733
;

-- 2021-10-19T08:33:40.469Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2021-10-19 11:33:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544732
;

-- 2021-10-19T08:34:08.547Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,664619,0,467,540488,593347,'F',TO_TIMESTAMP('2021-10-19 11:34:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandatory On Receipt',40,0,0,TO_TIMESTAMP('2021-10-19 11:34:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-19T08:34:26.430Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,664620,0,467,540488,593348,'F',TO_TIMESTAMP('2021-10-19 11:34:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandatory On Picking',50,0,0,TO_TIMESTAMP('2021-10-19 11:34:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-19T08:34:43.301Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,664621,0,467,540488,593349,'F',TO_TIMESTAMP('2021-10-19 11:34:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandatory On Shipment',60,0,0,TO_TIMESTAMP('2021-10-19 11:34:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-19T08:35:03.165Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-10-19 11:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=593347
;

-- 2021-10-19T08:35:03.297Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-10-19 11:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=593348
;

-- 2021-10-19T08:35:03.435Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-10-19 11:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=593349
;

-- 2021-10-19T08:35:03.551Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-10-19 11:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544733
;


















-- 2021-10-26T10:24:47.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545072,0,TO_TIMESTAMP('2021-10-26 13:24:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','The attribute {0}  is mandatory on picking for the product {1}.','I',TO_TIMESTAMP('2021-10-26 13:24:47','YYYY-MM-DD HH24:MI:SS'),100,'M_AttributeUse_MandatoryOnPicking')
;

-- 2021-10-26T10:24:47.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545072 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-10-26T10:27:18.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545073,0,TO_TIMESTAMP('2021-10-26 13:27:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','The attribute {0}  is mandatory on shipment for the product {1}.','E',TO_TIMESTAMP('2021-10-26 13:27:18','YYYY-MM-DD HH24:MI:SS'),100,'M_AttributeUse_MandatoryOnShipment')
;

-- 2021-10-26T10:27:18.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545073 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-10-26T10:27:22.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgType='E',Updated=TO_TIMESTAMP('2021-10-26 13:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545072
;










-- 2021-10-26T13:34:39.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mandatory for Picking', PrintName='Mandatory for Picking',Updated=TO_TIMESTAMP('2021-10-26 16:34:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580060 AND AD_Language='en_US'
;

-- 2021-10-26T13:34:39.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580060,'en_US') 
;

-- 2021-10-26T13:34:51.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erforderlich für Kommissionierung', PrintName='Erforderlich für Kommissionierung',Updated=TO_TIMESTAMP('2021-10-26 16:34:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580060 AND AD_Language='de_CH'
;

-- 2021-10-26T13:34:51.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580060,'de_CH') 
;

-- 2021-10-26T13:34:54.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erforderlich für Kommissionierung', PrintName='Erforderlich für Kommissionierung',Updated=TO_TIMESTAMP('2021-10-26 16:34:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580060 AND AD_Language='de_DE'
;

-- 2021-10-26T13:34:54.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580060,'de_DE') 
;

-- 2021-10-26T13:34:54.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580060,'de_DE') 
;

-- 2021-10-26T13:34:54.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MandatoryOnPicking', Name='Erforderlich für Kommissionierung', Description=NULL, Help=NULL WHERE AD_Element_ID=580060
;

-- 2021-10-26T13:34:54.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MandatoryOnPicking', Name='Erforderlich für Kommissionierung', Description=NULL, Help=NULL, AD_Element_ID=580060 WHERE UPPER(ColumnName)='MANDATORYONPICKING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-26T13:34:54.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MandatoryOnPicking', Name='Erforderlich für Kommissionierung', Description=NULL, Help=NULL WHERE AD_Element_ID=580060 AND IsCentrallyMaintained='Y'
;

-- 2021-10-26T13:34:54.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erforderlich für Kommissionierung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580060) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580060)
;

-- 2021-10-26T13:34:54.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erforderlich für Kommissionierung', Name='Erforderlich für Kommissionierung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580060)
;

-- 2021-10-26T13:34:54.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erforderlich für Kommissionierung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580060
;

-- 2021-10-26T13:34:54.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erforderlich für Kommissionierung', Description=NULL, Help=NULL WHERE AD_Element_ID = 580060
;

-- 2021-10-26T13:34:54.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erforderlich für Kommissionierung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580060
;

-- 2021-10-26T13:42:38.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mandatory for Receipt', PrintName='Mandatory for Receipt',Updated=TO_TIMESTAMP('2021-10-26 16:42:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580059 AND AD_Language='en_US'
;

-- 2021-10-26T13:42:38.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580059,'en_US') 
;

-- 2021-10-26T13:42:50.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erforderlich für Warenempfang', PrintName='Erforderlich für Warenempfang',Updated=TO_TIMESTAMP('2021-10-26 16:42:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580059 AND AD_Language='de_CH'
;

-- 2021-10-26T13:42:50.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580059,'de_CH') 
;

-- 2021-10-26T13:42:53.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erforderlich für Warenempfang', PrintName='Erforderlich für Warenempfang',Updated=TO_TIMESTAMP('2021-10-26 16:42:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580059 AND AD_Language='de_DE'
;

-- 2021-10-26T13:42:53.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580059,'de_DE') 
;

-- 2021-10-26T13:42:53.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580059,'de_DE') 
;

-- 2021-10-26T13:42:53.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MandatoryOnReceipt', Name='Erforderlich für Warenempfang', Description=NULL, Help=NULL WHERE AD_Element_ID=580059
;

-- 2021-10-26T13:42:53.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MandatoryOnReceipt', Name='Erforderlich für Warenempfang', Description=NULL, Help=NULL, AD_Element_ID=580059 WHERE UPPER(ColumnName)='MANDATORYONRECEIPT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-26T13:42:53.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MandatoryOnReceipt', Name='Erforderlich für Warenempfang', Description=NULL, Help=NULL WHERE AD_Element_ID=580059 AND IsCentrallyMaintained='Y'
;

-- 2021-10-26T13:42:53.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erforderlich für Warenempfang', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580059) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580059)
;

-- 2021-10-26T13:42:53.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erforderlich für Warenempfang', Name='Erforderlich für Warenempfang' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580059)
;

-- 2021-10-26T13:42:53.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erforderlich für Warenempfang', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580059
;

-- 2021-10-26T13:42:53.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erforderlich für Warenempfang', Description=NULL, Help=NULL WHERE AD_Element_ID = 580059
;

-- 2021-10-26T13:42:53.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erforderlich für Warenempfang', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580059
;

-- 2021-10-26T13:43:19.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mandatory for Shipping', PrintName='Mandatory for Shipping',Updated=TO_TIMESTAMP('2021-10-26 16:43:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580061 AND AD_Language='en_US'
;

-- 2021-10-26T13:43:19.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580061,'en_US') 
;

-- 2021-10-26T13:43:47Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erforderlich für Versand', PrintName='Erforderlich für Versand',Updated=TO_TIMESTAMP('2021-10-26 16:43:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580061 AND AD_Language='de_CH'
;

-- 2021-10-26T13:43:47.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580061,'de_CH') 
;

-- 2021-10-26T13:43:50.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erforderlich für Versand', PrintName='Erforderlich für Versand',Updated=TO_TIMESTAMP('2021-10-26 16:43:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580061 AND AD_Language='de_DE'
;

-- 2021-10-26T13:43:50.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580061,'de_DE') 
;

-- 2021-10-26T13:43:50.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580061,'de_DE') 
;

-- 2021-10-26T13:43:50.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MandatoryOnShipment', Name='Erforderlich für Versand', Description=NULL, Help=NULL WHERE AD_Element_ID=580061
;

-- 2021-10-26T13:43:50.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MandatoryOnShipment', Name='Erforderlich für Versand', Description=NULL, Help=NULL, AD_Element_ID=580061 WHERE UPPER(ColumnName)='MANDATORYONSHIPMENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-26T13:43:50.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MandatoryOnShipment', Name='Erforderlich für Versand', Description=NULL, Help=NULL WHERE AD_Element_ID=580061 AND IsCentrallyMaintained='Y'
;

-- 2021-10-26T13:43:50.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erforderlich für Versand', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580061) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580061)
;

-- 2021-10-26T13:43:50.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erforderlich für Versand', Name='Erforderlich für Versand' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580061)
;

-- 2021-10-26T13:43:50.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erforderlich für Versand', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580061
;

-- 2021-10-26T13:43:50.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erforderlich für Versand', Description=NULL, Help=NULL WHERE AD_Element_ID = 580061
;

-- 2021-10-26T13:43:50.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erforderlich für Versand', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580061
;

-- 2021-10-26T13:44:30.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The attribute "{0}" is mandatory for shipping the product "{1}".',Updated=TO_TIMESTAMP('2021-10-26 16:44:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545073
;

-- 2021-10-26T13:44:35.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-10-26 16:44:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545073
;

-- 2021-10-26T13:44:50.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Das Merkmal "{0}" ist zwingend erforderlich für den Versand des Produktes "{1}".',Updated=TO_TIMESTAMP('2021-10-26 16:44:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545073
;

-- 2021-10-26T13:44:54.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Das Merkmal "{0}" ist zwingend erforderlich für den Versand des Produktes "{1}".',Updated=TO_TIMESTAMP('2021-10-26 16:44:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545073
;

-- 2021-10-26T13:44:57.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Das Merkmal "{0}" ist zwingend erforderlich für den Versand des Produktes "{1}".',Updated=TO_TIMESTAMP('2021-10-26 16:44:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545073
;

-- 2021-10-26T13:45:18.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The attribute "{0}" is mandatory for picking the product "{1}".',Updated=TO_TIMESTAMP('2021-10-26 16:45:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545072
;

-- 2021-10-26T13:45:29.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Das Merkmal "{0}" ist zwingend erforderlich bei Kommissionierung des Produktes "{1}".',Updated=TO_TIMESTAMP('2021-10-26 16:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545072
;

-- 2021-10-26T13:45:33.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Das Merkmal "{0}" ist zwingend erforderlich bei Kommissionierung des Produktes "{1}".',Updated=TO_TIMESTAMP('2021-10-26 16:45:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545072
;

-- 2021-10-26T13:45:37.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Das Merkmal "{0}" ist zwingend erforderlich bei Kommissionierung des Produktes "{1}".',Updated=TO_TIMESTAMP('2021-10-26 16:45:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545072
;











