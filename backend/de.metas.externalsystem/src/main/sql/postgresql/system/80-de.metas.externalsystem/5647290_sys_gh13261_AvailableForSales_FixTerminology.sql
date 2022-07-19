-- 2022-07-18T10:21:06.727Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='de.metas.externalsystem.export.stock.ExportAvailableForSalesToShopwareExternalSystem.debouncer.bufferMaxSize',Updated=TO_TIMESTAMP('2022-07-18 13:21:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541459
;

-- 2022-07-18T10:21:51.636Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='de.metas.externalsystem.export.stock.ExportAvailableForSalesToShopwareExternalSystem.debouncer.delayInMillis',Updated=TO_TIMESTAMP('2022-07-18 13:21:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541460
;

-- UI Element: External System Config -> Shopware6.PercentageOfAvailableStockToSync
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-18T12:21:13.207Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=609685
;

-- 2022-07-18T12:21:13.217Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700840
;

-- Field: External System Config -> Shopware6 -> Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-18T12:21:13.223Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=700840
;

-- 2022-07-18T12:21:13.224Z
DELETE FROM AD_Field WHERE AD_Field_ID=700840
;

-- UI Element: External system config Shopware 6 -> External system config Shopware6.PercentageOfAvailableStockToSync
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-18T12:21:13.233Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=609686
;

-- 2022-07-18T12:21:13.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700841
;

-- Field: External system config Shopware 6 -> External system config Shopware6 -> Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-18T12:21:13.236Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=700841
;

-- 2022-07-18T12:21:13.237Z
DELETE FROM AD_Field WHERE AD_Field_ID=700841
;

-- 2022-07-18T12:21:13.269Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE ExternalSystem_Config_Shopware6 DROP COLUMN IF EXISTS PercentageOfAvailableStockToSync')
;

-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-18T12:21:13.300Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583550
;

-- 2022-07-18T12:21:13.302Z
DELETE FROM AD_Column WHERE AD_Column_ID=583550
;

-- UI Element: External System Config -> Shopware6.IsSyncStockToShopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncStockToShopware6
-- 2022-07-18T12:21:21.863Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=609602
;

-- 2022-07-18T12:21:21.865Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700722
;

-- Field: External System Config -> Shopware6 -> Sync verfügb. Menge nach Shopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncStockToShopware6
-- 2022-07-18T12:21:21.869Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=700722
;

-- 2022-07-18T12:21:21.872Z
DELETE FROM AD_Field WHERE AD_Field_ID=700722
;

-- UI Element: External system config Shopware 6 -> External system config Shopware6.IsSyncStockToShopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncStockToShopware6
-- 2022-07-18T12:21:21.890Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=609603
;

-- 2022-07-18T12:21:21.892Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700723
;

-- Field: External system config Shopware 6 -> External system config Shopware6 -> Sync verfügb. Menge nach Shopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncStockToShopware6
-- 2022-07-18T12:21:21.896Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=700723
;

-- 2022-07-18T12:21:21.899Z
DELETE FROM AD_Field WHERE AD_Field_ID=700723
;

-- 2022-07-18T12:21:21.902Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE ExternalSystem_Config_Shopware6 DROP COLUMN IF EXISTS IsSyncStockToShopware6')
;

-- Column: ExternalSystem_Config_Shopware6.IsSyncStockToShopware6
-- 2022-07-18T12:21:21.922Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583380
;

-- 2022-07-18T12:21:21.925Z
DELETE FROM AD_Column WHERE AD_Column_ID=583380
;

-- 2022-07-18T12:22:14.507Z
UPDATE AD_Element SET ColumnName='IsSyncAvailableForSalesToShopware6',Updated=TO_TIMESTAMP('2022-07-18 15:22:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040
;

-- 2022-07-18T12:22:14.514Z
UPDATE AD_Column SET ColumnName='IsSyncAvailableForSalesToShopware6', Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.', Help=NULL WHERE AD_Element_ID=581040
;

-- 2022-07-18T12:22:14.514Z
UPDATE AD_Process_Para SET ColumnName='IsSyncAvailableForSalesToShopware6', Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.', Help=NULL, AD_Element_ID=581040 WHERE UPPER(ColumnName)='ISSYNCAVAILABLEFORSALESTOSHOPWARE6' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-18T12:22:14.517Z
UPDATE AD_Process_Para SET ColumnName='IsSyncAvailableForSalesToShopware6', Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.', Help=NULL WHERE AD_Element_ID=581040 AND IsCentrallyMaintained='Y'
;

-- 2022-07-18T12:22:39.965Z
UPDATE AD_Element_Trl SET Name='Is sync available for sales to Shopware6', PrintName='Is sync available for sales to Shopware6',Updated=TO_TIMESTAMP('2022-07-18 15:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='en_US'
;

-- 2022-07-18T12:22:39.968Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'en_US')
;

-- Column: ExternalSystem_Config_Shopware6.IsSyncAvailableForSalesToShopware6
-- 2022-07-18T12:24:07.510Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583705,581040,0,20,541585,'IsSyncAvailableForSalesToShopware6',TO_TIMESTAMP('2022-07-18 15:24:07','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Sync verfügb. Menge nach Shopware6',0,0,TO_TIMESTAMP('2022-07-18 15:24:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-18T12:24:07.515Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583705 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-18T12:24:07.520Z
/* DDL */  select update_Column_Translation_From_AD_Element(581040)
;

-- 2022-07-18T12:24:54.161Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN IsSyncAvailableForSalesToShopware6 CHAR(1) DEFAULT ''N'' CHECK (IsSyncAvailableForSalesToShopware6 IN (''Y'',''N'')) NOT NULL')
;

-- 2022-07-18T12:27:24.647Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE ExternalSystem_Config_Shopware6 DROP COLUMN IF EXISTS IsSyncAvailableForSalesToShopware6')
;

-- Column: ExternalSystem_Config_Shopware6.IsSyncAvailableForSalesToShopware6
-- 2022-07-18T12:27:24.666Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583705
;

-- 2022-07-18T12:27:24.668Z
DELETE FROM AD_Column WHERE AD_Column_ID=583705
;

-- 2022-07-18T12:27:38.957Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=581040
;

-- 2022-07-18T12:27:38.966Z
DELETE FROM AD_Element WHERE AD_Element_ID=581040
;

-- 2022-07-18T12:28:03.229Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581145,0,'IsSyncAvailableForSalesToShopware6',TO_TIMESTAMP('2022-07-18 15:28:03','YYYY-MM-DD HH24:MI:SS'),100,'Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.','D','Y','Sync verfügb. Menge nach Shopware6','Sync verfügb. Menge nach Shopware6',TO_TIMESTAMP('2022-07-18 15:28:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-18T12:28:03.235Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581145 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-18T12:28:25.498Z
UPDATE AD_Element_Trl SET Description='If checked, the current planned quantity available for sales is automatically sent to Shopware6.', Name='Is sync available for sales to Shopware6', PrintName='Is sync available for sales to Shopware6',Updated=TO_TIMESTAMP('2022-07-18 15:28:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581145 AND AD_Language='en_US'
;

-- 2022-07-18T12:28:25.499Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581145,'en_US')
;

-- Column: ExternalSystem_Config_Shopware6.IsSyncAvailableForSalesToShopware6
-- 2022-07-18T12:28:52.892Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583706,581145,0,20,541585,'IsSyncAvailableForSalesToShopware6',TO_TIMESTAMP('2022-07-18 15:28:52','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Sync verfügb. Menge nach Shopware6',0,0,TO_TIMESTAMP('2022-07-18 15:28:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-18T12:28:52.894Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583706 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-18T12:28:52.899Z
/* DDL */  select update_Column_Translation_From_AD_Element(581145)
;

-- 2022-07-18T12:30:34.780Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=581070
;

-- 2022-07-18T12:30:34.783Z
DELETE FROM AD_Element WHERE AD_Element_ID=581070
;

-- 2022-07-18T12:31:33.590Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581146,0,'PercentageOfAvailableForSalesToSync',TO_TIMESTAMP('2022-07-18 15:31:33','YYYY-MM-DD HH24:MI:SS'),100,'Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.','D','Y','Prozent Abzug','Prozent Abzug',TO_TIMESTAMP('2022-07-18 15:31:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-18T12:31:33.593Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581146 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-18T12:32:15.269Z
UPDATE AD_Element_Trl SET Description='Percentage that is subtracted from the actual available for sales before it is transferred to Shopware. For example, with 25% only three quarters of the actually available for sales is reported to the shop.', Name='Percentage deduction', PrintName='Percentage deduction',Updated=TO_TIMESTAMP('2022-07-18 15:32:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581146 AND AD_Language='en_US'
;

-- 2022-07-18T12:32:15.270Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581146,'en_US')
;

-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableForSalesToSync
-- 2022-07-18T12:33:50.991Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,ValueMax,ValueMin,Version) VALUES (0,583707,581146,0,22,541585,'PercentageOfAvailableForSalesToSync',TO_TIMESTAMP('2022-07-18 15:33:50','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.','de.metas.externalsystem',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','@IsSyncAvailableForSalesToShopware6/N@=Y',0,'Prozent Abzug',0,0,TO_TIMESTAMP('2022-07-18 15:33:50','YYYY-MM-DD HH24:MI:SS'),100,'100','0',0)
;

-- 2022-07-18T12:33:50.994Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583707 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-18T12:33:50.997Z
/* DDL */  select update_Column_Translation_From_AD_Element(581146)
;

-- 2022-07-18T12:33:52.368Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN PercentageOfAvailableForSalesToSync NUMERIC DEFAULT 0 NOT NULL')
;

-- Field: External system config Shopware 6 -> External system config Shopware6 -> Sync verfügb. Menge nach Shopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncAvailableForSalesToShopware6
-- 2022-07-18T12:34:51.359Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583706,702028,0,543838,TO_TIMESTAMP('2022-07-18 15:34:51','YYYY-MM-DD HH24:MI:SS'),100,'Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.',1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Sync verfügb. Menge nach Shopware6',TO_TIMESTAMP('2022-07-18 15:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-18T12:34:51.361Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-18T12:34:51.363Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581145)
;

-- 2022-07-18T12:34:51.366Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702028
;

-- 2022-07-18T12:34:51.367Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(702028)
;

-- Field: External system config Shopware 6 -> External system config Shopware6 -> Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableForSalesToSync
-- 2022-07-18T12:34:51.459Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583707,702029,0,543838,TO_TIMESTAMP('2022-07-18 15:34:51','YYYY-MM-DD HH24:MI:SS'),100,'Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.',14,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Prozent Abzug',TO_TIMESTAMP('2022-07-18 15:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-18T12:34:51.460Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-18T12:34:51.462Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581146)
;

-- 2022-07-18T12:34:51.464Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702029
;

-- 2022-07-18T12:34:51.464Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(702029)
;

-- Field: External system config Shopware 6 -> External system config Shopware6 -> Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableForSalesToSync
-- 2022-07-18T12:36:17.280Z
UPDATE AD_Field SET DisplayLogic='@IsSyncAvailableForSalesToShopware6/N@=Y', IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-07-18 15:36:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702029
;

-- UI Element: External system config Shopware 6 -> External system config Shopware6.Sync verfügb. Menge nach Shopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncAvailableForSalesToShopware6
-- 2022-07-18T12:36:49.648Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,702028,0,543838,610433,545679,'F',TO_TIMESTAMP('2022-07-18 15:36:49','YYYY-MM-DD HH24:MI:SS'),100,'Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.','Y','N','N','Y','N','N','N',0,'Sync verfügb. Menge nach Shopware6',20,0,0,TO_TIMESTAMP('2022-07-18 15:36:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config Shopware 6 -> External system config Shopware6.Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableForSalesToSync
-- 2022-07-18T12:36:59.117Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,702029,0,543838,610434,545679,'F',TO_TIMESTAMP('2022-07-18 15:36:58','YYYY-MM-DD HH24:MI:SS'),100,'Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.','Y','N','N','Y','N','N','N',0,'Prozent Abzug',30,0,0,TO_TIMESTAMP('2022-07-18 15:36:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: External System Config -> Shopware6 -> Sync verfügb. Menge nach Shopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncAvailableForSalesToShopware6
-- 2022-07-18T12:37:23.481Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583706,702030,0,543435,TO_TIMESTAMP('2022-07-18 15:37:23','YYYY-MM-DD HH24:MI:SS'),100,'Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.',1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Sync verfügb. Menge nach Shopware6',TO_TIMESTAMP('2022-07-18 15:37:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-18T12:37:23.482Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702030 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-18T12:37:23.483Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581145)
;

-- 2022-07-18T12:37:23.485Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702030
;

-- 2022-07-18T12:37:23.485Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(702030)
;

-- Field: External System Config -> Shopware6 -> Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableForSalesToSync
-- 2022-07-18T12:37:23.593Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583707,702031,0,543435,TO_TIMESTAMP('2022-07-18 15:37:23','YYYY-MM-DD HH24:MI:SS'),100,'Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.',14,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Prozent Abzug',TO_TIMESTAMP('2022-07-18 15:37:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-18T12:37:23.594Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702031 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-18T12:37:23.595Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581146)
;

-- 2022-07-18T12:37:23.596Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702031
;

-- 2022-07-18T12:37:23.597Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(702031)
;

-- Field: External System Config -> Shopware6 -> Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableForSalesToSync
-- 2022-07-18T12:38:26.347Z
UPDATE AD_Field SET DisplayLogic='@IsSyncAvailableForSalesToShopware6/N@=Y', IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-07-18 15:38:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702031
;

-- UI Element: External System Config -> Shopware6.Sync verfügb. Menge nach Shopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncAvailableForSalesToShopware6
-- 2022-07-18T12:38:53.427Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,702030,0,543435,610435,544976,'F',TO_TIMESTAMP('2022-07-18 15:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.','Y','N','N','Y','N','N','N',0,'Sync verfügb. Menge nach Shopware6',20,0,0,TO_TIMESTAMP('2022-07-18 15:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External System Config -> Shopware6.Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableForSalesToSync
-- 2022-07-18T12:38:59.500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,702031,0,543435,610436,544976,'F',TO_TIMESTAMP('2022-07-18 15:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.','Y','N','N','Y','N','N','N',0,'Prozent Abzug',30,0,0,TO_TIMESTAMP('2022-07-18 15:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External System Config -> Shopware6.Sync verfügb. Menge nach Shopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncAvailableForSalesToShopware6
-- 2022-07-18T12:39:07.682Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-07-18 15:39:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610435
;

-- 2022-07-18T12:41:27.069Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN IsSyncAvailableForSalesToShopware6 CHAR(1) DEFAULT ''N'' CHECK (IsSyncAvailableForSalesToShopware6 IN (''Y'',''N'')) NOT NULL')
;

-- UI Element: External System Config -> Shopware6.Sync verfügb. Menge nach Shopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncAvailableForSalesToShopware6
-- 2022-07-19T16:26:05.573Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-07-19 19:26:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610435
;

-- UI Element: External System Config -> Shopware6.Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableForSalesToSync
-- 2022-07-19T16:26:09.828Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-07-19 19:26:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610436
;
