-- 2022-06-20T15:41:38.463Z
UPDATE AD_Element_Trl SET Name='Zum Verkauf verfügbar', PrintName='Zum Verkauf verfügbar',Updated=TO_TIMESTAMP('2022-06-20 18:41:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580998 AND AD_Language='de_CH'
;

-- 2022-06-20T15:41:38.565Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580998,'de_CH') 
;

-- 2022-06-20T15:41:41.548Z
UPDATE AD_Element_Trl SET Name='Zum Verkauf verfügbar', PrintName='Zum Verkauf verfügbar',Updated=TO_TIMESTAMP('2022-06-20 18:41:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580998 AND AD_Language='de_DE'
;

-- 2022-06-20T15:41:41.549Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580998,'de_DE') 
;

-- 2022-06-20T15:41:41.577Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580998,'de_DE') 
;

-- 2022-06-20T15:41:41.580Z
UPDATE AD_Column SET ColumnName='MD_Available_For_Sales_ID', Name='Zum Verkauf verfügbar', Description=NULL, Help=NULL WHERE AD_Element_ID=580998
;

-- 2022-06-20T15:41:41.583Z
UPDATE AD_Process_Para SET ColumnName='MD_Available_For_Sales_ID', Name='Zum Verkauf verfügbar', Description=NULL, Help=NULL, AD_Element_ID=580998 WHERE UPPER(ColumnName)='MD_AVAILABLE_FOR_SALES_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-20T15:41:41.585Z
UPDATE AD_Process_Para SET ColumnName='MD_Available_For_Sales_ID', Name='Zum Verkauf verfügbar', Description=NULL, Help=NULL WHERE AD_Element_ID=580998 AND IsCentrallyMaintained='Y'
;

-- 2022-06-20T15:41:41.587Z
UPDATE AD_Field SET Name='Zum Verkauf verfügbar', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580998) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580998)
;

-- 2022-06-20T15:41:41.618Z
UPDATE AD_PrintFormatItem pi SET PrintName='Zum Verkauf verfügbar', Name='Zum Verkauf verfügbar' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580998)
;

-- 2022-06-20T15:41:41.619Z
UPDATE AD_Tab SET Name='Zum Verkauf verfügbar', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580998
;

-- 2022-06-20T15:41:41.623Z
UPDATE AD_WINDOW SET Name='Zum Verkauf verfügbar', Description=NULL, Help=NULL WHERE AD_Element_ID = 580998
;

-- 2022-06-20T15:41:41.626Z
UPDATE AD_Menu SET   Name = 'Zum Verkauf verfügbar', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580998
;

-- 2022-06-20T15:41:44.773Z
UPDATE AD_Element_Trl SET Name='Zum Verkauf verfügbar', PrintName='Zum Verkauf verfügbar',Updated=TO_TIMESTAMP('2022-06-20 18:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580998 AND AD_Language='nl_NL'
;

-- 2022-06-20T15:41:44.777Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580998,'nl_NL') 
;

-- 2022-06-20T15:45:20.516Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581048,0,TO_TIMESTAMP('2022-06-20 18:45:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Storage Attributes Key','Storage Attributes Key',TO_TIMESTAMP('2022-06-20 18:45:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-20T15:45:20.519Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581048 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-06-20T15:45:28.724Z
UPDATE AD_Element_Trl SET Name='Bestandsrelevanter Merkmals-Key', PrintName='Bestandsrelevanter Merkmals-Key',Updated=TO_TIMESTAMP('2022-06-20 18:45:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581048 AND AD_Language='de_CH'
;

-- 2022-06-20T15:45:28.726Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581048,'de_CH') 
;

-- 2022-06-20T15:45:31.517Z
UPDATE AD_Element_Trl SET Name='Bestandsrelevanter Merkmals-Key', PrintName='Bestandsrelevanter Merkmals-Key',Updated=TO_TIMESTAMP('2022-06-20 18:45:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581048 AND AD_Language='de_DE'
;

-- 2022-06-20T15:45:31.519Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581048,'de_DE') 
;

-- 2022-06-20T15:45:31.538Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581048,'de_DE') 
;

-- 2022-06-20T15:45:31.540Z
UPDATE AD_Column SET ColumnName=NULL, Name='Bestandsrelevanter Merkmals-Key', Description=NULL, Help=NULL WHERE AD_Element_ID=581048
;

-- 2022-06-20T15:45:31.542Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Bestandsrelevanter Merkmals-Key', Description=NULL, Help=NULL WHERE AD_Element_ID=581048 AND IsCentrallyMaintained='Y'
;

-- 2022-06-20T15:45:31.543Z
UPDATE AD_Field SET Name='Bestandsrelevanter Merkmals-Key', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581048) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581048)
;

-- 2022-06-20T15:45:31.581Z
UPDATE AD_PrintFormatItem pi SET PrintName='Bestandsrelevanter Merkmals-Key', Name='Bestandsrelevanter Merkmals-Key' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581048)
;

-- 2022-06-20T15:45:31.583Z
UPDATE AD_Tab SET Name='Bestandsrelevanter Merkmals-Key', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581048
;

-- 2022-06-20T15:45:31.585Z
UPDATE AD_WINDOW SET Name='Bestandsrelevanter Merkmals-Key', Description=NULL, Help=NULL WHERE AD_Element_ID = 581048
;

-- 2022-06-20T15:45:31.587Z
UPDATE AD_Menu SET   Name = 'Bestandsrelevanter Merkmals-Key', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581048
;

-- 2022-06-20T15:45:35.876Z
UPDATE AD_Element_Trl SET Name='Bestandsrelevanter Merkmals-Key', PrintName='Bestandsrelevanter Merkmals-Key',Updated=TO_TIMESTAMP('2022-06-20 18:45:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581048 AND AD_Language='nl_NL'
;

-- 2022-06-20T15:45:35.878Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581048,'nl_NL') 
;

-- Field: Zum Verkauf verfügbar -> Zum Verkauf verfügbar -> Bestandsrelevanter Merkmals-Key
-- Column: MD_Available_For_Sales.StorageAttributesKey
-- 2022-06-20T15:46:11.423Z
UPDATE AD_Field SET AD_Name_ID=581048, Description=NULL, Help=NULL, Name='Bestandsrelevanter Merkmals-Key',Updated=TO_TIMESTAMP('2022-06-20 18:46:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698924
;

-- 2022-06-20T15:46:11.425Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581048) 
;

-- 2022-06-20T15:46:11.443Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698924
;

-- 2022-06-20T15:46:11.448Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698924)
;

-- 2022-06-20T15:46:59.929Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581049,0,TO_TIMESTAMP('2022-06-20 18:46:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Qty to be shipped','Qty to be shipped',TO_TIMESTAMP('2022-06-20 18:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-20T15:46:59.930Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581049 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-06-20T15:47:07.884Z
UPDATE AD_Element_Trl SET Name='Zu liefernede Menge', PrintName='Zu liefernede Menge',Updated=TO_TIMESTAMP('2022-06-20 18:47:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581049 AND AD_Language='de_CH'
;

-- 2022-06-20T15:47:07.887Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581049,'de_CH') 
;

-- 2022-06-20T15:47:10.571Z
UPDATE AD_Element_Trl SET Name='Zu liefernede Menge', PrintName='Zu liefernede Menge',Updated=TO_TIMESTAMP('2022-06-20 18:47:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581049 AND AD_Language='de_DE'
;

-- 2022-06-20T15:47:10.574Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581049,'de_DE') 
;

-- 2022-06-20T15:47:10.588Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581049,'de_DE') 
;

-- 2022-06-20T15:47:10.590Z
UPDATE AD_Column SET ColumnName=NULL, Name='Zu liefernede Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=581049
;

-- 2022-06-20T15:47:10.591Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Zu liefernede Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=581049 AND IsCentrallyMaintained='Y'
;

-- 2022-06-20T15:47:10.592Z
UPDATE AD_Field SET Name='Zu liefernede Menge', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581049) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581049)
;

-- 2022-06-20T15:47:10.624Z
UPDATE AD_PrintFormatItem pi SET PrintName='Zu liefernede Menge', Name='Zu liefernede Menge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581049)
;

-- 2022-06-20T15:47:10.626Z
UPDATE AD_Tab SET Name='Zu liefernede Menge', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581049
;

-- 2022-06-20T15:47:10.628Z
UPDATE AD_WINDOW SET Name='Zu liefernede Menge', Description=NULL, Help=NULL WHERE AD_Element_ID = 581049
;

-- 2022-06-20T15:47:10.630Z
UPDATE AD_Menu SET   Name = 'Zu liefernede Menge', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581049
;

-- 2022-06-20T15:47:14.516Z
UPDATE AD_Element_Trl SET Name='Zu liefernede Menge', PrintName='Zu liefernede Menge',Updated=TO_TIMESTAMP('2022-06-20 18:47:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581049 AND AD_Language='nl_NL'
;

-- 2022-06-20T15:47:14.518Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581049,'nl_NL') 
;

-- Field: Zum Verkauf verfügbar -> Zum Verkauf verfügbar -> Zu liefernede Menge
-- Column: MD_Available_For_Sales.QtyToBeShipped
-- 2022-06-20T15:47:45.136Z
UPDATE AD_Field SET AD_Name_ID=581049, Description=NULL, Help=NULL, Name='Zu liefernede Menge',Updated=TO_TIMESTAMP('2022-06-20 18:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698926
;

-- 2022-06-20T15:47:45.137Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581049) 
;

-- 2022-06-20T15:47:45.141Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698926
;

-- 2022-06-20T15:47:45.142Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698926)
;

-- 2022-06-20T15:50:12.620Z
UPDATE AD_Element_Trl SET Name='Sync verfügb. Menge nach Shopware6', PrintName='Sync verfügb. Menge nach Shopware6',Updated=TO_TIMESTAMP('2022-06-20 18:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='de_CH'
;

-- 2022-06-20T15:50:12.623Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'de_CH') 
;

-- 2022-06-20T15:50:15.276Z
UPDATE AD_Element_Trl SET Name='Sync verfügb. Menge nach Shopware6', PrintName='Sync verfügb. Menge nach Shopware6',Updated=TO_TIMESTAMP('2022-06-20 18:50:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='de_DE'
;

-- 2022-06-20T15:50:15.279Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'de_DE') 
;

-- 2022-06-20T15:50:15.299Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581040,'de_DE') 
;

-- 2022-06-20T15:50:15.301Z
UPDATE AD_Column SET ColumnName='IsSyncStockToShopware6', Name='Sync verfügb. Menge nach Shopware6', Description=NULL, Help=NULL WHERE AD_Element_ID=581040
;

-- 2022-06-20T15:50:15.304Z
UPDATE AD_Process_Para SET ColumnName='IsSyncStockToShopware6', Name='Sync verfügb. Menge nach Shopware6', Description=NULL, Help=NULL, AD_Element_ID=581040 WHERE UPPER(ColumnName)='ISSYNCSTOCKTOSHOPWARE6' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-20T15:50:15.306Z
UPDATE AD_Process_Para SET ColumnName='IsSyncStockToShopware6', Name='Sync verfügb. Menge nach Shopware6', Description=NULL, Help=NULL WHERE AD_Element_ID=581040 AND IsCentrallyMaintained='Y'
;

-- 2022-06-20T15:50:15.307Z
UPDATE AD_Field SET Name='Sync verfügb. Menge nach Shopware6', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581040) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581040)
;

-- 2022-06-20T15:50:15.348Z
UPDATE AD_PrintFormatItem pi SET PrintName='Sync verfügb. Menge nach Shopware6', Name='Sync verfügb. Menge nach Shopware6' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581040)
;

-- 2022-06-20T15:50:15.350Z
UPDATE AD_Tab SET Name='Sync verfügb. Menge nach Shopware6', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581040
;

-- 2022-06-20T15:50:15.353Z
UPDATE AD_WINDOW SET Name='Sync verfügb. Menge nach Shopware6', Description=NULL, Help=NULL WHERE AD_Element_ID = 581040
;

-- 2022-06-20T15:50:15.355Z
UPDATE AD_Menu SET   Name = 'Sync verfügb. Menge nach Shopware6', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581040
;

-- 2022-06-20T15:50:21.236Z
UPDATE AD_Element_Trl SET Name='Is sync stock to Shopware6', PrintName='Is sync stock to Shopware6',Updated=TO_TIMESTAMP('2022-06-20 18:50:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='en_US'
;

-- 2022-06-20T15:50:21.238Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'en_US') 
;

/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2022-06-20T15:50:26.604Z
UPDATE AD_Element_Trl SET Name='Sync verfügb. Menge nach Shopware6', PrintName='Sync verfügb. Menge nach Shopware6',Updated=TO_TIMESTAMP('2022-06-20 18:50:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='nl_NL'
;

-- 2022-06-20T15:50:26.606Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'nl_NL') 
;

-- 2022-06-20T16:05:15.399Z
UPDATE AD_Process_Trl SET Name='Tabelle aktualisieren',Updated=TO_TIMESTAMP('2022-06-20 19:05:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585064
;

-- 2022-06-20T16:05:17.374Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Tabelle aktualisieren',Updated=TO_TIMESTAMP('2022-06-20 19:05:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585064
;

-- 2022-06-20T16:05:17.326Z
UPDATE AD_Process_Trl SET Name='Tabelle aktualisieren',Updated=TO_TIMESTAMP('2022-06-20 19:05:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585064
;

-- 2022-06-20T16:05:20.025Z
UPDATE AD_Process_Trl SET Name='Tabelle aktualisieren',Updated=TO_TIMESTAMP('2022-06-20 19:05:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585064
;
