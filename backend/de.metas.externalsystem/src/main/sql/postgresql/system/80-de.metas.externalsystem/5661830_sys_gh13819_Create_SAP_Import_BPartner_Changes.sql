-- 2022-10-25T09:43:41.565025800Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581603,0,'SFTP_Product_TargetDirectory',TO_TIMESTAMP('2022-10-25 12:43:41','YYYY-MM-DD HH24:MI:SS'),100,'Das Verzeichnis, das zum Abrufen vom sftp-Server verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)','U','Y','SFTP-Produkt-Zielverzeichnis','SFTP-Produkt-Zielverzeichnis',TO_TIMESTAMP('2022-10-25 12:43:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T09:43:41.578927900Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581603 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T09:44:47.832475600Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2022-10-25 12:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603
;

-- 2022-10-25T09:44:47.875944700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'de_DE') 
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-10-25T09:45:05.628557600Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, in dem die Produkte vom sftp-Server abgerufen werden. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)',Updated=TO_TIMESTAMP('2022-10-25 12:45:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='en_US'
;

-- 2022-10-25T09:45:05.632235800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'en_US') 
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-10-25T09:45:12.540615400Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, in dem die Produkte vom sftp-Server abgerufen werden. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)',Updated=TO_TIMESTAMP('2022-10-25 12:45:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='nl_NL'
;

-- 2022-10-25T09:45:12.544669700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'nl_NL') 
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-10-25T09:45:20.988904100Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, in dem die Produkte vom sftp-Server abgerufen werden. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)',Updated=TO_TIMESTAMP('2022-10-25 12:45:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='de_CH'
;

-- 2022-10-25T09:45:20.991904500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'de_CH') 
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-10-25T09:45:24.983578300Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, in dem die Produkte vom sftp-Server abgerufen werden. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)',Updated=TO_TIMESTAMP('2022-10-25 12:45:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='de_DE'
;

-- 2022-10-25T09:45:24.987582500Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581603,'de_DE') 
;

-- 2022-10-25T09:45:24.989588700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'de_DE') 
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-10-25T09:45:55.946538400Z
UPDATE AD_Element_Trl SET Description='The directory used to pull products from the sftp server. (If no value set here, the files will be pulled from the root location of the sftp server.)', Name='SFTP Product Target Directory', PrintName='SFTP Product Target Directory',Updated=TO_TIMESTAMP('2022-10-25 12:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='en_US'
;

-- 2022-10-25T09:45:55.950540100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'en_US') 
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-10-25T09:46:08.281927900Z
UPDATE AD_Element_Trl SET Description='The directory used to pull products from the sftp server. (If no value set here, the files will be pulled from the root location of the sftp server.)', Name='SFTP Product Target Directory', PrintName='SFTP Product Target Directory',Updated=TO_TIMESTAMP('2022-10-25 12:46:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='nl_NL'
;

-- 2022-10-25T09:46:08.285786800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'nl_NL') 
;

-- Column: ExternalSystem_Config_SAP.SFTP_Product_TargetDirectory
-- 2022-10-25T09:46:57.575970400Z
UPDATE AD_Column SET AD_Element_ID=581603, ColumnName='SFTP_Product_TargetDirectory', Description='Das Verzeichnis, in dem die Produkte vom sftp-Server abgerufen werden. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)', Help=NULL, Name='SFTP-Produkt-Zielverzeichnis',Updated=TO_TIMESTAMP('2022-10-25 12:46:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584682
;

-- 2022-10-25T09:46:57.581871900Z
UPDATE AD_Field SET Name='SFTP-Produkt-Zielverzeichnis', Description='Das Verzeichnis, in dem die Produkte vom sftp-Server abgerufen werden. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)', Help=NULL WHERE AD_Column_ID=584682
;

-- 2022-10-25T09:46:57.587210Z
/* DDL */  select update_Column_Translation_From_AD_Element(581603) 
;

-- 2022-10-25T09:47:03.794223200Z
/* DDL */ select db_alter_table('ExternalSystem_Config_SAP', 'ALTER TABLE ExternalSystem_Config_SAP RENAME COLUMN SFTP_TargetDirectory to SFTP_Product_TargetDirectory;');
;

-- 2022-10-25T09:51:03.599926300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581604,0,'SFTP_BPartner_TargetDirectory',TO_TIMESTAMP('2022-10-25 12:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Das Verzeichnis, in dem die Produkte vom sftp-Server abgerufen werden. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)','U','Y','SFTP-Geschäftspartner-Zielverzeichnis','SFTP-Geschäftspartner-Zielverzeichnis',TO_TIMESTAMP('2022-10-25 12:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T09:51:03.605882500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581604 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-10-25T09:51:52.104674Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, das verwendet wird, um Geschäftspartner vom sftp-Server zu holen. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)',Updated=TO_TIMESTAMP('2022-10-25 12:51:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='nl_NL'
;

-- 2022-10-25T09:51:52.108669900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'nl_NL') 
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-10-25T09:51:58.505302700Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, das verwendet wird, um Geschäftspartner vom sftp-Server zu holen. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)',Updated=TO_TIMESTAMP('2022-10-25 12:51:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='de_CH'
;

-- 2022-10-25T09:51:58.508212900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'de_CH') 
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-10-25T09:52:03.615859800Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, das verwendet wird, um Geschäftspartner vom sftp-Server zu holen. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)',Updated=TO_TIMESTAMP('2022-10-25 12:52:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='de_DE'
;

-- 2022-10-25T09:52:03.619995200Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581604,'de_DE') 
;

-- 2022-10-25T09:52:03.621861400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'de_DE') 
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-10-25T09:52:37.787432100Z
UPDATE AD_Element_Trl SET Description='The directory used to pull business partners from the sftp server. (If no value set here, the files will be pulled from the root location of the sftp server.)', Name='SFTP Business Partner Target Directory', PrintName='SFTP Business Partner Target Directory',Updated=TO_TIMESTAMP('2022-10-25 12:52:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='en_US'
;

-- 2022-10-25T09:52:37.790434800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'en_US') 
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-10-25T09:52:53.764318200Z
UPDATE AD_Element_Trl SET Description='The directory used to pull business partners from the sftp server. (If no value set here, the files will be pulled from the root location of the sftp server.)', Name='SFTP Business Partner Target Directory', PrintName='SFTP Business Partner Target Directory',Updated=TO_TIMESTAMP('2022-10-25 12:52:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='nl_NL'
;

-- 2022-10-25T09:52:53.801314600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'nl_NL') 
;

-- 2022-10-25T09:53:09.487160100Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2022-10-25 12:53:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604
;

-- 2022-10-25T09:53:09.491103900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'de_DE') 
;

-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_TargetDirectory
-- 2022-10-25T09:54:24.995601700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584802,581604,0,10,542238,'SFTP_BPartner_TargetDirectory',TO_TIMESTAMP('2022-10-25 12:54:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Das Verzeichnis, das verwendet wird, um Geschäftspartner vom sftp-Server zu holen. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SFTP-Geschäftspartner-Zielverzeichnis',0,0,TO_TIMESTAMP('2022-10-25 12:54:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T09:54:25.001606900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584802 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T09:54:25.007726300Z
/* DDL */  select update_Column_Translation_From_AD_Element(581604) 
;

-- 2022-10-25T09:54:29.292915400Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN SFTP_BPartner_TargetDirectory VARCHAR(255)')
;

-- Reference: External_Request SAP
-- Value: startBPartnerSync
-- ValueName: import
-- 2022-10-25T10:16:09.479749Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543316,541661,TO_TIMESTAMP('2022-10-25 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,'Startet die Geschäftspartner-Synchronisation mit dem SAP-Außensystem','U','Y','Start der Geschäftspartner-Synchronisation',TO_TIMESTAMP('2022-10-25 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,'startBPartnerSync','import')
;

-- 2022-10-25T10:16:09.483751300Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543316 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: External_Request SAP -> startBPartnerSync_import
-- 2022-10-25T10:17:28.904630900Z
UPDATE AD_Ref_List_Trl SET Description='Starts the business partners sychronization with SAP external system', Name='Start Business Partners Synchronization',Updated=TO_TIMESTAMP('2022-10-25 13:17:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543316
;

-- Reference Item: External_Request SAP -> startBPartnerSync_import
-- 2022-10-25T10:17:53.893713800Z
UPDATE AD_Ref_List_Trl SET Description='Starts the business partners sychronization with SAP external system', Name='Start Business Partners Synchronization',Updated=TO_TIMESTAMP('2022-10-25 13:17:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543316
;

-- Reference: External_Request SAP
-- Value: stopBPartnerSync
-- ValueName: import
-- 2022-10-25T10:19:28.323409300Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543317,541661,TO_TIMESTAMP('2022-10-25 13:19:28','YYYY-MM-DD HH24:MI:SS'),100,'Stoppt die Geschäftspartner-Synchronisation mit dem externen SAP-System','D','Y','Geschäftspartner-Synchronisation stoppen',TO_TIMESTAMP('2022-10-25 13:19:28','YYYY-MM-DD HH24:MI:SS'),100,'stopBPartnerSync','import')
;

-- 2022-10-25T10:19:28.326484500Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543317 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: External_Request SAP -> stopBPartnerSync_import
-- 2022-10-25T10:20:52.277316700Z
UPDATE AD_Ref_List_Trl SET Description='Stops the business partner synchronization with SAP external system', Name='Stop Business Partner Synchronization',Updated=TO_TIMESTAMP('2022-10-25 13:20:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543317
;

-- Reference Item: External_Request SAP -> stopBPartnerSync_import
-- 2022-10-25T10:21:04.085948300Z
UPDATE AD_Ref_List_Trl SET Description='Stops the business partner synchronization with SAP external system', Name='Stop Business Partner Synchronization',Updated=TO_TIMESTAMP('2022-10-25 13:21:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543317
;

-- Reference: External_Request SAP
-- Value: startBPartnerSync
-- ValueName: import
-- 2022-10-25T10:21:37.100890100Z
UPDATE AD_Ref_List SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-10-25 13:21:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543316
;

/*
 * #%L
 * de.metas.externalsystem
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

-- Reference: External_Request SAP
-- Value: stopBPartnerSync
-- ValueName: import
-- 2022-10-25T10:21:49.978798Z
UPDATE AD_Ref_List SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-10-25 13:21:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543317
;

-- Tab: External System Service(541340,de.metas.externalsystem) -> ExternalSystem_Service
-- Table: ExternalSystem_Service
-- 2022-10-25T10:59:55.710265100Z
UPDATE AD_Tab SET IsReadOnly='N',Updated=TO_TIMESTAMP('2022-10-25 13:59:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544882
;

-- Tab: External System Service(541340,de.metas.externalsystem) -> ExternalSystem_Service
-- Table: ExternalSystem_Service
-- 2022-10-25T11:02:19.884575800Z
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2022-10-25 14:02:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544882
;

-- 2022-10-25T11:03:33.940464200Z
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Service_ID,IsActive,Name,Type,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2022-10-25 14:03:33','YYYY-MM-DD HH24:MI:SS'),100,540003,'Y','SFTPSyncBPartners','SAP',TO_TIMESTAMP('2022-10-25 14:03:33','YYYY-MM-DD HH24:MI:SS'),100,'SFTPSyncBPartners')
;

-- 2022-10-25T11:03:39.512262100Z
UPDATE ExternalSystem_Service SET Name='SFTP Sync-BPartners',Updated=TO_TIMESTAMP('2022-10-25 14:03:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540003
;

-- 2022-10-25T11:03:49.654085400Z
UPDATE ExternalSystem_Service SET Description='SFTP Sync-BPartners',Updated=TO_TIMESTAMP('2022-10-25 14:03:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540003
;

-- 2022-10-25T11:04:17.835032400Z
UPDATE ExternalSystem_Service SET EnableCommand='startBPartnerSync',Updated=TO_TIMESTAMP('2022-10-25 14:04:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540003
;

-- 2022-10-25T11:04:24.853012500Z
UPDATE ExternalSystem_Service SET DisableCommand='stopBPartnerSync',Updated=TO_TIMESTAMP('2022-10-25 14:04:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540003
;

-- Tab: External System Service(541340,de.metas.externalsystem) -> ExternalSystem_Service
-- Table: ExternalSystem_Service
-- 2022-10-25T11:04:58.462013900Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-10-25 14:04:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544882
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_TargetDirectory
-- 2022-10-25T11:38:57.736549300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584802,707860,0,546647,0,TO_TIMESTAMP('2022-10-25 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Das Verzeichnis, das verwendet wird, um Geschäftspartner vom sftp-Server zu holen. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','SFTP-Geschäftspartner-Zielverzeichnis',0,100,0,1,1,TO_TIMESTAMP('2022-10-25 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

/*
 * #%L
 * de.metas.externalsystem
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

-- 2022-10-25T11:38:57.745008100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T11:38:57.806356600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581604)
;

-- 2022-10-25T11:38:57.836743600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707860
;

-- 2022-10-25T11:38:57.846585500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707860)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_TargetDirectory
-- 2022-10-25T11:39:55.726164400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707860,0,546647,613302,549954,'F',TO_TIMESTAMP('2022-10-25 14:39:55','YYYY-MM-DD HH24:MI:SS'),100,'Das Verzeichnis, das verwendet wird, um Geschäftspartner vom sftp-Server zu holen. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)','Y','N','N','Y','N','N','N',0,'SFTP-Geschäftspartner-Zielverzeichnis',60,0,0,TO_TIMESTAMP('2022-10-25 14:39:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Produkt-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_Product_TargetDirectory
-- 2022-10-25T11:40:21.372941Z
UPDATE AD_UI_Element SET Name='SFTP-Produkt-Zielverzeichnis',Updated=TO_TIMESTAMP('2022-10-25 14:40:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613230
;

-- Reference Item: External_Request SAP -> startBPartnerSync_import
-- 2022-11-09T16:20:01.685Z
UPDATE AD_Ref_List_Trl SET Description='Starts the business partners synchronization with SAP external system',Updated=TO_TIMESTAMP('2022-11-09 18:20:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543316
;

-- Reference Item: External_Request SAP -> startBPartnerSync_import
-- 2022-11-09T16:20:07.857Z
UPDATE AD_Ref_List_Trl SET Description='Starts the business partners synchronization with SAP external system',Updated=TO_TIMESTAMP('2022-11-09 18:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543316
;

