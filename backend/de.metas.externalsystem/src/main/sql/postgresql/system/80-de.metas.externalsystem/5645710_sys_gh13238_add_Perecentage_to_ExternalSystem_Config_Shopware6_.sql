-- 2022-07-01T11:49:14.817Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581070,0,'PercentageOfAvailableStockToSync',TO_TIMESTAMP('2022-07-01 13:49:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prozent Abzug','Prozent Abzug',TO_TIMESTAMP('2022-07-01 13:49:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-01T11:49:14.824Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581070 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-01T11:52:13.703Z
UPDATE AD_Element_Trl SET Description='Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-01 13:52:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581070 AND AD_Language='de_CH'
;

-- 2022-07-01T11:52:13.745Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581070,'de_CH') 
;

-- 2022-07-01T11:52:17.690Z
UPDATE AD_Element_Trl SET Description='Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-01 13:52:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581070 AND AD_Language='de_DE'
;

-- 2022-07-01T11:52:17.693Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581070,'de_DE') 
;

-- 2022-07-01T11:52:17.703Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581070,'de_DE') 
;

-- 2022-07-01T11:52:17.705Z
UPDATE AD_Column SET ColumnName='PercentageOfAvailableStockToSync', Name='Prozent Abzug', Description='Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.', Help=NULL WHERE AD_Element_ID=581070
;

-- 2022-07-01T11:52:17.708Z
UPDATE AD_Process_Para SET ColumnName='PercentageOfAvailableStockToSync', Name='Prozent Abzug', Description='Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.', Help=NULL, AD_Element_ID=581070 WHERE UPPER(ColumnName)='PERCENTAGEOFAVAILABLESTOCKTOSYNC' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-01T11:52:17.710Z
UPDATE AD_Process_Para SET ColumnName='PercentageOfAvailableStockToSync', Name='Prozent Abzug', Description='Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.', Help=NULL WHERE AD_Element_ID=581070 AND IsCentrallyMaintained='Y'
;

-- 2022-07-01T11:52:17.711Z
UPDATE AD_Field SET Name='Prozent Abzug', Description='Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581070) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581070)
;

-- 2022-07-01T11:52:17.720Z
UPDATE AD_Tab SET Name='Prozent Abzug', Description='Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581070
;

-- 2022-07-01T11:52:17.722Z
UPDATE AD_WINDOW SET Name='Prozent Abzug', Description='Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.', Help=NULL WHERE AD_Element_ID = 581070
;

-- 2022-07-01T11:52:17.725Z
UPDATE AD_Menu SET   Name = 'Prozent Abzug', Description = 'Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581070
;

-- 2022-07-01T11:52:44.011Z
UPDATE AD_Element_Trl SET Description='Percentage that is subtracted from the actual available stock before it is transferred to Shopware. For example, with 25% only three quarters of the actually available stock is reported to the shop.', IsTranslated='Y', Name='Percentage deduction', PrintName='Percentage deduction',Updated=TO_TIMESTAMP('2022-07-01 13:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581070 AND AD_Language='en_US'
;

-- 2022-07-01T11:52:44.014Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581070,'en_US') 
;

-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-01T11:53:46.763Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,ValueMax,ValueMin,Version) VALUES (0,583550,581070,0,22,541585,'PercentageOfAvailableStockToSync',TO_TIMESTAMP('2022-07-01 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,'N','10','Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.','de.metas.externalsystem',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Prozent Abzug',0,0,TO_TIMESTAMP('2022-07-01 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,'100','0',0)
;

-- 2022-07-01T11:53:46.767Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583550 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-01T11:53:46.773Z
/* DDL */  select update_Column_Translation_From_AD_Element(581070) 
;

-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-01T11:54:07.243Z
UPDATE AD_Column SET MandatoryLogic='@IsSyncStockToShopware6/N@=Y',Updated=TO_TIMESTAMP('2022-07-01 13:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583550
;

-- 2022-07-01T11:54:12.332Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN PercentageOfAvailableStockToSync NUMERIC DEFAULT 10 NOT NULL')
;

-- Field: External System Config -> Shopware6 -> Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path
-- Column: ExternalSystem_Config_Shopware6.JSONPathMetasfreshID
-- 2022-07-01T11:54:46.392Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582801,700838,0,543435,TO_TIMESTAMP('2022-07-01 13:54:46','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Pfad, der angibt, wo die Metasfresh-ID des Kunden (C_BPartner_ID) gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md',255,'de.metas.externalsystem','Y','Y','N','N','N','N','N','Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path',TO_TIMESTAMP('2022-07-01 13:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-01T11:54:46.395Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-01T11:54:46.398Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580786) 
;

-- 2022-07-01T11:54:46.410Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700838
;

-- 2022-07-01T11:54:46.413Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700838)
;

-- Field: External System Config -> Shopware6 -> Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path
-- Column: ExternalSystem_Config_Shopware6.JSONPathShopwareID
-- 2022-07-01T11:54:46.520Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582802,700839,0,543435,TO_TIMESTAMP('2022-07-01 13:54:46','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Pfad, der angibt, wo die Shopware6-Referenz des Kunden gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe, in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md',255,'de.metas.externalsystem','Y','Y','N','N','N','N','N','Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path',TO_TIMESTAMP('2022-07-01 13:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-01T11:54:46.523Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-01T11:54:46.526Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580787) 
;

-- 2022-07-01T11:54:46.530Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700839
;

-- 2022-07-01T11:54:46.532Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700839)
;

-- Field: External System Config -> Shopware6 -> Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-01T11:54:46.653Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583550,700840,0,543435,TO_TIMESTAMP('2022-07-01 13:54:46','YYYY-MM-DD HH24:MI:SS'),100,'Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.',14,'de.metas.externalsystem','Y','Y','N','N','N','N','N','Prozent Abzug',TO_TIMESTAMP('2022-07-01 13:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-01T11:54:46.656Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-01T11:54:46.659Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581070) 
;

-- 2022-07-01T11:54:46.663Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700840
;

-- 2022-07-01T11:54:46.665Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700840)
;

-- UI Element: External System Config -> Shopware6.PercentageOfAvailableStockToSync
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-01T11:55:37.137Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,700840,0,543435,544976,609685,'F',TO_TIMESTAMP('2022-07-01 13:55:36','YYYY-MM-DD HH24:MI:SS'),100,'Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.','Y','N','N','Y','N','N','N',0,'PercentageOfAvailableStockToSync',30,0,0,TO_TIMESTAMP('2022-07-01 13:55:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: External System Config -> Shopware6 -> Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-01T11:56:08Z
UPDATE AD_Field SET DisplayLogic='@IsSyncStockToShopware6/N@=Y',Updated=TO_TIMESTAMP('2022-07-01 13:56:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700840
;

-------------

-- Field: External system config Shopware 6 -> External system config Shopware6 -> Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-01T12:25:28.892Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583550,700841,0,543838,TO_TIMESTAMP('2022-07-01 14:25:28','YYYY-MM-DD HH24:MI:SS'),100,'Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.',14,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Prozent Abzug',TO_TIMESTAMP('2022-07-01 14:25:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-01T12:25:28.894Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-01T12:25:28.926Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581070)
;

-- 2022-07-01T12:25:28.939Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700841
;

-- 2022-07-01T12:25:28.941Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700841)
;

-- Field: External system config Shopware 6 -> External system config Shopware6 -> Prozent Abzug
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-01T12:25:50.557Z
UPDATE AD_Field SET DisplayLogic='@IsSyncStockToShopware6/N@=Y', IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-07-01 14:25:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700841
;

-- UI Element: External system config Shopware 6 -> External system config Shopware6.PercentageOfAvailableStockToSync
-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-01T12:26:27.062Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,700841,0,543838,545679,609686,'F',TO_TIMESTAMP('2022-07-01 14:26:26','YYYY-MM-DD HH24:MI:SS'),100,'Prozentsatz, der vom eigentlich verfügbaren Bestand abgezogen wird, bevor dieser an Shopware übertragen wird. Zum Beispiel werden mit 25% nur drei viertel des eigentlich verfügbaren Bestandes an den Shop gemeldet.','Y','N','N','Y','N','N','N',0,'PercentageOfAvailableStockToSync',30,0,0,TO_TIMESTAMP('2022-07-01 14:26:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: ExternalSystem_Config_Shopware6.PercentageOfAvailableStockToSync
-- 2022-07-01T12:32:26.502Z
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2022-07-01 14:32:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583550
;

-- 2022-07-01T12:32:33.848Z
INSERT INTO t_alter_column values('externalsystem_config_shopware6','PercentageOfAvailableStockToSync','NUMERIC',null,'0')
;

-- 2022-07-01T12:32:33.854Z
UPDATE ExternalSystem_Config_Shopware6 SET PercentageOfAvailableStockToSync=0 WHERE PercentageOfAvailableStockToSync IS NULL
;

