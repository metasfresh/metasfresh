

-- SFTP-Sync Product
update externalsystem_service
set enablecommand='startProductSyncSFTP', disablecommand='stopProductSyncSFTP', value='SFTPSyncProducts'
where externalsystem_service_id=540002
;

-- SFTP-Sync BPartner
update externalsystem_service
set enablecommand='startBPartnerSyncSFTP', disablecommand='stopBPartnerSyncSFTP', value='SFTPSyncBPartners'
where externalsystem_service_id=540003
;

-- SFTP-Sync CreditLimit
update externalsystem_service
set enablecommand='startCreditLimitSyncSFTP', disablecommand='stopCreditLimitSyncSFTP', value='SFTPSyncCreditLimits'
where externalsystem_service_id=540004
;

-- LocalFile-Sync Product
INSERT INTO public.externalsystem_service (ad_client_id, ad_org_id, created, createdby, externalsystem_service_id, isactive, updated, updatedby, type, value, name, description, enablecommand, disablecommand)
VALUES (1000000, 1000000, '2022-11-19 08:25:06.000000', 100, 540005, 'Y', '2022-11-19 08:25:06.000000', 100, 'SAP', 'LocalFileSyncProducts', 'Local-File Sync-Products', 'Local-File Sync-Products', 'startProductSyncLocalFile', 'stopProductSyncLocalFile')
;

-- LocalFile-Sync BPartner
INSERT INTO public.externalsystem_service (ad_client_id, ad_org_id, created, createdby, externalsystem_service_id, isactive, updated, updatedby, type, value, name, description, enablecommand, disablecommand)
VALUES (1000000, 1000000, '2022-11-19 08:25:06.000000', 100, 540006, 'Y', '2022-11-19 08:25:06.000000', 100, 'SAP', 'LocalFileSyncBPartners', 'Local-File Sync-BPartners', 'Local-File Sync-BPartners', 'startBPartnerSyncLocalFile', 'stopBPartnerSyncLocalFile')
;

-- LocalFile-Sync CreditLimit
INSERT INTO public.externalsystem_service (ad_client_id, ad_org_id, created, createdby, externalsystem_service_id, isactive, updated, updatedby, type, value, name, description, enablecommand, disablecommand)
VALUES (1000000, 1000000, '2022-11-19 08:25:06.000000', 100, 540007, 'Y', '2022-11-19 08:25:06.000000', 100, 'SAP', 'LocalFileSyncCreditLimits', 'Local-File Sync-CreditLimits', 'Local-File Sync-CreditLimits', 'startCreditLimitSyncLocalFile', 'stopCreditLimitSyncLocalFile')
;

-- 2022-11-21T11:11:55.927Z
INSERT INTO t_alter_column values('externalsystem_config_sap_localfile','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2022-11-21T11:13:01.316Z
INSERT INTO t_alter_column values('externalsystem_config_sap_sftp','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2022-11-21T11:13:31.384Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN SFTP_CreditLimit_TargetDirectory VARCHAR(255)')
;

-- 2022-11-21T11:13:45.888Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN SFTP_CreditLimit_FileName_Pattern VARCHAR(255)')
;

-- 2022-11-21T11:14:07.981Z
INSERT INTO t_alter_column values('externalsystem_config_sap_localfile','LocalFile_CreditLimit_FileName_Pattern','VARCHAR(255)',null,null)
;

-- 2022-11-21T11:14:14.279Z
INSERT INTO t_alter_column values('externalsystem_config_sap_localfile','LocalFile_CreditLimit_TargetDirectory','VARCHAR(255)',null,null)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10
-- UI Element Group: Partner
-- 2022-11-21T11:16:49.204Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546448,550036,TO_TIMESTAMP('2022-11-21 13:16:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','Partner',20,TO_TIMESTAMP('2022-11-21 13:16:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10
-- UI Element Group: Product
-- 2022-11-21T11:16:53.305Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546448,550037,TO_TIMESTAMP('2022-11-21 13:16:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','Product',30,TO_TIMESTAMP('2022-11-21 13:16:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10
-- UI Element Group: Credit-Limit
-- 2022-11-21T11:16:59.603Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546448,550038,TO_TIMESTAMP('2022-11-21 13:16:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','Credit-Limit',40,TO_TIMESTAMP('2022-11-21 13:16:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> Credit-Limit.Kreditlimit Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_CreditLimit_FileName_Pattern
-- 2022-11-21T11:29:37.270Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550038, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-21 13:29:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613536
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> Credit-Limit.Kreditlimits Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_CreditLimit_TargetDirectory
-- 2022-11-21T11:29:52.022Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550038, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-21 13:29:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613535
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> Product.SFTP-Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Product_FileName_Pattern
-- 2022-11-21T11:30:00.107Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550037, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-21 13:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613474
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.ProcessedDirectory
-- 2022-11-21T11:31:51.818Z
UPDATE AD_UI_Element SET AD_Field_ID=708045, Description='Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung verschoben werden sollen. (Der Pfad sollte relativ zum aktuellen Zielort sein)', Name='Verarbeitet-Verzeichnis',Updated=TO_TIMESTAMP('2022-11-21 13:31:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613454
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.ProcessedDirectory
-- 2022-11-21T11:32:01.662Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613454
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> Product.SFTP-Produkt-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Product_TargetDirectory
-- 2022-11-21T11:32:19.042Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550037, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-21 13:32:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613472
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> Partner.SFTP-Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_BPartner_FileName_Pattern
-- 2022-11-21T11:32:47.356Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550036, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-21 13:32:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613473
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> Partner.SFTP-Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_BPartner_TargetDirectory
-- 2022-11-21T11:32:55.038Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550036, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-21 13:32:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613471
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10
-- UI Element Group: main
-- 2022-11-21T11:33:08.580Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2022-11-21 13:33:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550021
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10
-- UI Element Group: main
-- 2022-11-21T11:35:21.588Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2022-11-21 13:35:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550024
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10
-- UI Element Group: Partner
-- 2022-11-21T11:35:31.297Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546450,550039,TO_TIMESTAMP('2022-11-21 13:35:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','Partner',20,TO_TIMESTAMP('2022-11-21 13:35:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10
-- UI Element Group: Product
-- 2022-11-21T11:35:34.914Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546450,550040,TO_TIMESTAMP('2022-11-21 13:35:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','Product',30,TO_TIMESTAMP('2022-11-21 13:35:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10
-- UI Element Group: Credit-Limit
-- 2022-11-21T11:35:40.034Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546450,550041,TO_TIMESTAMP('2022-11-21 13:35:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','Credit-Limit',40,TO_TIMESTAMP('2022-11-21 13:35:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> Credit-Limit.Kreditlimit Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_CreditLimit_FileName_Pattern
-- 2022-11-21T11:35:53.330Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550041, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-21 13:35:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613538
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> Credit-Limit.Kreditlimits Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_CreditLimit_TargetDirectory
-- 2022-11-21T11:35:59.757Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550041, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-21 13:35:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613537
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> Product.Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_Product_FileName_Pattern
-- 2022-11-21T11:36:15.368Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550040, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-21 13:36:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613527
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> Product.Produkt-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_Product_TargetDirectory
-- 2022-11-21T11:36:29.788Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550040, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-21 13:36:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613525
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> Partner.Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_BPartner_FileName_Pattern
-- 2022-11-21T11:36:39.633Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550039, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-21 13:36:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613523
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> Partner.Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_BPartner_TargetDirectory
-- 2022-11-21T11:36:47.448Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550039, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-21 13:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613524
;

-- Element: LocalFile_CreditLimit_TargetDirectory
-- 2022-11-21T11:40:17.339Z
UPDATE AD_Element_Trl SET Name='Credit Limit Target Directory', PrintName='Credit Limit Target Directory',Updated=TO_TIMESTAMP('2022-11-21 13:40:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581711 AND AD_Language='en_US'
;

-- 2022-11-21T11:40:17.341Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581711,'en_US') 
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-21T11:42:44.778Z
UPDATE AD_Element_Trl SET Description='Directory used to pull products from the local machine. (If no value is specified here, the files are pulled from the configured local root location).',Updated=TO_TIMESTAMP('2022-11-21 13:42:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='en_US'
;

-- 2022-11-21T11:42:44.779Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'en_US') 
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-21T11:42:59.412Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen).',Updated=TO_TIMESTAMP('2022-11-21 13:42:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='nl_NL'
;

-- 2022-11-21T11:42:59.413Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'nl_NL') 
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-21T11:43:03.051Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen).',Updated=TO_TIMESTAMP('2022-11-21 13:43:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='de_DE'
;

-- 2022-11-21T11:43:03.053Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581706,'de_DE') 
;

-- 2022-11-21T11:43:03.054Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'de_DE') 
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-21T11:43:07.117Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen).',Updated=TO_TIMESTAMP('2022-11-21 13:43:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='de_CH'
;

-- 2022-11-21T11:43:07.118Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'de_CH') 
;

-- Element: LocalFile_CreditLimit_TargetDirectory
-- 2022-11-21T11:45:20.326Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, das zum Abrufen der Kreditlimits vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-21 13:45:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581711 AND AD_Language='de_CH'
;

-- 2022-11-21T11:45:20.329Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581711,'de_CH') 
;

-- Element: LocalFile_CreditLimit_TargetDirectory
-- 2022-11-21T11:45:25.242Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, das zum Abrufen der Kreditlimits vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-21 13:45:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581711 AND AD_Language='de_DE'
;

-- 2022-11-21T11:45:25.243Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581711,'de_DE') 
;

-- 2022-11-21T11:45:25.245Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581711,'de_DE') 
;

-- Element: LocalFile_CreditLimit_TargetDirectory
-- 2022-11-21T11:45:29.044Z
UPDATE AD_Element_Trl SET Description='The directory used to retrieve credit limits from the local machine. (If no value is specified here, the files are pulled from the configured local root location)',Updated=TO_TIMESTAMP('2022-11-21 13:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581711 AND AD_Language='en_US'
;

-- 2022-11-21T11:45:29.054Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581711,'en_US') 
;

-- Element: LocalFile_CreditLimit_TargetDirectory
-- 2022-11-21T11:45:32.213Z
UPDATE AD_Element_Trl SET Description='The directory used to retrieve credit limits from the local machine. (If no value is specified here, the files are pulled from the configured local root location)',Updated=TO_TIMESTAMP('2022-11-21 13:45:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581711 AND AD_Language='nl_NL'
;

-- 2022-11-21T11:45:32.215Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581711,'nl_NL') 
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-21T11:46:31.075Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-21 13:46:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='de_CH'
;

-- 2022-11-21T11:46:31.077Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'de_CH') 
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-21T11:46:34.508Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-21 13:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='de_DE'
;

-- 2022-11-21T11:46:34.511Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581706,'de_DE') 
;

-- 2022-11-21T11:46:34.513Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'de_DE') 
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-21T11:46:37.361Z
UPDATE AD_Element_Trl SET Description='Directory used to pull products from the local machine. (If no value is specified here, the files are pulled from the configured local root location)',Updated=TO_TIMESTAMP('2022-11-21 13:46:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='en_US'
;

-- 2022-11-21T11:46:37.362Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'en_US') 
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-21T11:46:40.369Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-21 13:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='nl_NL'
;

-- 2022-11-21T11:46:40.371Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'nl_NL') 
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-21T11:48:33.910Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, das zum Abrufen der Kreditlimits vom sftp-Server verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen)',Updated=TO_TIMESTAMP('2022-11-21 13:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='de_CH'
;

-- 2022-11-21T11:48:33.911Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'de_CH') 
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-21T11:48:35.804Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, das zum Abrufen der Kreditlimits vom sftp-Server verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen)',Updated=TO_TIMESTAMP('2022-11-21 13:48:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='de_DE'
;

-- 2022-11-21T11:48:35.807Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581623,'de_DE') 
;

-- 2022-11-21T11:48:35.808Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'de_DE') 
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-21T11:48:37.649Z
UPDATE AD_Element_Trl SET Description='The directory used to retrieve credit limits from the sftp server. (If no value is specified here, the files are pulled from the root directory of the sftp server)',Updated=TO_TIMESTAMP('2022-11-21 13:48:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='en_US'
;

-- 2022-11-21T11:48:37.651Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'en_US') 
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-21T11:48:40.086Z
UPDATE AD_Element_Trl SET Description='The directory used to retrieve credit limits from the sftp server. (If no value is specified here, the files are pulled from the root directory of the sftp server)',Updated=TO_TIMESTAMP('2022-11-21 13:48:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='nl_NL'
;

-- 2022-11-21T11:48:40.088Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'nl_NL') 
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> Partner.Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_BPartner_TargetDirectory
-- 2022-11-21T11:55:51.164Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-21 13:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613524
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> Credit-Limit.Kreditlimits Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_CreditLimit_TargetDirectory
-- 2022-11-21T11:55:51.170Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-21 13:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613537
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> Product.Produkt-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_Product_TargetDirectory
-- 2022-11-21T11:55:51.175Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-21 13:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613525
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.ProcessedDirectory
-- 2022-11-21T11:55:51.179Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-21 13:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613461
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Fehler-Verzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.ErroredDirectory
-- 2022-11-21T11:55:51.184Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-21 13:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613462
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Abfragefrequenz in Millisekunden
-- Column: ExternalSystem_Config_SAP_LocalFile.PollingFrequencyInMs
-- 2022-11-21T11:55:51.188Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-21 13:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613528
;


-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T12:38:31.061Z
UPDATE AD_Ref_List_Trl SET Name='Start Credit Limit Synchronization Local-File',Updated=TO_TIMESTAMP('2022-11-21 14:38:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T12:38:34.923Z
UPDATE AD_Ref_List_Trl SET Name='Start Credit Limit Synchronization Local-File',Updated=TO_TIMESTAMP('2022-11-21 14:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T12:38:40.355Z
UPDATE AD_Ref_List_Trl SET Name='Start der Kreditlimitsynchronisation Local File',Updated=TO_TIMESTAMP('2022-11-21 14:38:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T12:38:58.351Z
UPDATE AD_Ref_List_Trl SET Name='Start der Kreditlimitsynchronisation Lokale Datei',Updated=TO_TIMESTAMP('2022-11-21 14:38:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T12:39:02.991Z
UPDATE AD_Ref_List_Trl SET Name='Start der Kreditlimitsynchronisation Lokale Datei',Updated=TO_TIMESTAMP('2022-11-21 14:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T12:39:09.501Z
UPDATE AD_Ref_List_Trl SET Name='Start der Kreditlimitsynchronisation Lokale Datei',Updated=TO_TIMESTAMP('2022-11-21 14:39:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543351
;

-- Reference: External_Request SAP
-- Value: startCreditLimitSyncLocalFile
-- ValueName: Start Credit Limits Synchronization Local File
-- 2022-11-21T12:39:43.170Z
UPDATE AD_Ref_List SET Name='Start der Kreditlimitsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-21 14:39:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T12:39:46.258Z
UPDATE AD_Ref_List_Trl SET Name='Start der Kreditlimitsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-21 14:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T12:39:50.724Z
UPDATE AD_Ref_List_Trl SET Name='Start der Kreditlimitsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-21 14:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T12:39:57.194Z
UPDATE AD_Ref_List_Trl SET Name='Start der Kreditlimitsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-21 14:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T12:42:56.038Z
UPDATE AD_Ref_List_Trl SET Name='Start Credit Limit Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-21 14:42:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543351
;

-- Reference: External_Request SAP
-- Value: startCreditLimitSyncLocalFile
-- ValueName: Start Credit Limits Synchronization Local File
-- 2022-11-21T18:57:55.100Z
UPDATE AD_Ref_List SET Name='Start der Kreditlimitsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-21 20:57:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T18:58:03.475Z
UPDATE AD_Ref_List_Trl SET Name='Start der Kreditlimitsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-21 20:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T18:58:06.877Z
UPDATE AD_Ref_List_Trl SET Name='Start der Kreditlimitsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-21 20:58:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T18:58:12.493Z
UPDATE AD_Ref_List_Trl SET Name='Start Credit Limit Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-21 20:58:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-21T18:58:18.982Z
UPDATE AD_Ref_List_Trl SET Name='Start Credit Limit Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-21 20:58:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543351
;

