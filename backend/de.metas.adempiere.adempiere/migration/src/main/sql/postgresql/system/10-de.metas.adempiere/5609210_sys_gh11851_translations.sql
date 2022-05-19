-- 2021-10-13T14:47:40.832Z
-- URL zum Konzept
UPDATE AD_UserGroup SET Name='Notification group for partner created from another organization',Updated=TO_TIMESTAMP('2021-10-13 16:47:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UserGroup_ID=540003
;








-- 2021-10-13T14:53:46.566Z
-- URL zum Konzept
UPDATE R_RequestType SET InternalName='C_BPartner_CreatedFromAnotherOrg',Updated=TO_TIMESTAMP('2021-10-13 16:53:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_RequestType_ID=540108
;






-- 2021-10-13T15:02:30.379Z
-- URL zum Konzept
UPDATE R_RequestType_Trl SET Name='Partner Created From Another Org',Updated=TO_TIMESTAMP('2021-10-13 17:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND R_RequestType_ID=540108
;




-- 2021-10-13T16:00:19.567Z
-- URL zum Konzept
UPDATE R_RequestType_Trl SET Name='Externally recorded partner',Updated=TO_TIMESTAMP('2021-10-13 18:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=3542526 WHERE AD_Language='en_US' AND R_RequestType_ID=540108
;




-- 2021-10-13T16:01:31.513Z
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Nutzer/in {0}, angemeldet mit Organisation {1}, hat den Partner {2} für die Organisation {3} erstellt.',Updated=TO_TIMESTAMP('2021-10-13 19:01:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545064
;

-- 2021-10-13T16:01:40.801Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Nutzer/in {0}, angemeldet mit Organisation {1}, hat den Partner {2} für die Organisation {3} erstellt.',Updated=TO_TIMESTAMP('2021-10-13 19:01:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545064
;

-- 2021-10-13T16:01:49.467Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-10-13 19:01:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545064
;

-- 2021-10-13T16:01:54.148Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Nutzer/in {0}, angemeldet mit Organisation {1}, hat den Partner {2} für die Organisation {3} erstellt.',Updated=TO_TIMESTAMP('2021-10-13 19:01:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545064
;




-- 2021-10-13T16:06:15.929Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Notification group for externally recorded partners', PrintName='Notification group for externally recorded partners',Updated=TO_TIMESTAMP('2021-10-13 19:06:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580037 AND AD_Language='en_US'
;

-- 2021-10-13T16:06:16.020Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580037,'en_US') 
;

-- 2021-10-13T16:06:47.159Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Benachrichtigungsgruppe bei Fremdaufnahmen', PrintName='Benachrichtigungsgruppe bei Fremdaufnahmen',Updated=TO_TIMESTAMP('2021-10-13 19:06:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580037 AND AD_Language='de_DE'
;

-- 2021-10-13T16:06:47.236Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580037,'de_DE') 
;

-- 2021-10-13T16:06:47.411Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(580037,'de_DE') 
;

-- 2021-10-13T16:06:47.468Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID', Name='Benachrichtigungsgruppe bei Fremdaufnahmen', Description=NULL, Help=NULL WHERE AD_Element_ID=580037
;

-- 2021-10-13T16:06:47.530Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID', Name='Benachrichtigungsgruppe bei Fremdaufnahmen', Description=NULL, Help=NULL, AD_Element_ID=580037 WHERE UPPER(ColumnName)='C_BPARTNER_CREATEDFROMANOTHERORG_NOTIFY_USERGROUP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-13T16:06:47.593Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID', Name='Benachrichtigungsgruppe bei Fremdaufnahmen', Description=NULL, Help=NULL WHERE AD_Element_ID=580037 AND IsCentrallyMaintained='Y'
;

-- 2021-10-13T16:06:47.670Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Benachrichtigungsgruppe bei Fremdaufnahmen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580037) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580037)
;

-- 2021-10-13T16:06:47.822Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Benachrichtigungsgruppe bei Fremdaufnahmen', Name='Benachrichtigungsgruppe bei Fremdaufnahmen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580037)
;

-- 2021-10-13T16:06:47.899Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Benachrichtigungsgruppe bei Fremdaufnahmen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580037
;

-- 2021-10-13T16:06:47.961Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Benachrichtigungsgruppe bei Fremdaufnahmen', Description=NULL, Help=NULL WHERE AD_Element_ID = 580037
;

-- 2021-10-13T16:06:48.023Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Benachrichtigungsgruppe bei Fremdaufnahmen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580037
;

-- 2021-10-13T16:06:54.947Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Benachrichtigungsgruppe bei Fremdaufnahmen', PrintName='Benachrichtigungsgruppe bei Fremdaufnahmen',Updated=TO_TIMESTAMP('2021-10-13 19:06:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580037 AND AD_Language='de_CH'
;

-- 2021-10-13T16:06:55.008Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580037,'de_CH') 
;





-- 2021-10-13T16:07:59.433Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Notification group for expiring supplier approvals', PrintName='Notification group for expiring supplier approvals',Updated=TO_TIMESTAMP('2021-10-13 19:07:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580036 AND AD_Language='en_US'
;

-- 2021-10-13T16:07:59.512Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580036,'en_US') 
;

-- 2021-10-13T16:08:15.530Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben', PrintName='Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben',Updated=TO_TIMESTAMP('2021-10-13 19:08:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580036 AND AD_Language='de_DE'
;

-- 2021-10-13T16:08:15.594Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580036,'de_DE') 
;

-- 2021-10-13T16:08:15.765Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(580036,'de_DE') 
;

-- 2021-10-13T16:08:15.842Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID', Name='Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben', Description=NULL, Help=NULL WHERE AD_Element_ID=580036
;

-- 2021-10-13T16:08:15.918Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID', Name='Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben', Description=NULL, Help=NULL, AD_Element_ID=580036 WHERE UPPER(ColumnName)='C_BP_SUPPLIERAPPROVAL_EXPIRATION_NOTIFY_USERGROUP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-13T16:08:15.979Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID', Name='Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben', Description=NULL, Help=NULL WHERE AD_Element_ID=580036 AND IsCentrallyMaintained='Y'
;

-- 2021-10-13T16:08:16.055Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580036) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580036)
;

-- 2021-10-13T16:08:16.180Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben', Name='Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580036)
;

-- 2021-10-13T16:08:16.256Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580036
;

-- 2021-10-13T16:08:16.334Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben', Description=NULL, Help=NULL WHERE AD_Element_ID = 580036
;

-- 2021-10-13T16:08:16.396Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580036
;

-- 2021-10-13T16:08:20.609Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben', PrintName='Benachrichtigungsgruppe bei Ablauf von Lieferantenfreigaben',Updated=TO_TIMESTAMP('2021-10-13 19:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580036 AND AD_Language='de_CH'
;

-- 2021-10-13T16:08:20.685Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580036,'de_CH') 
;

-- 2021-10-13T16:08:26.450Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-10-13 19:08:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580036 AND AD_Language='de_CH'
;

-- 2021-10-13T16:08:26.511Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580036,'de_CH') 
;












-- 2021-10-13T16:09:27.970Z
-- URL zum Konzept
UPDATE AD_UserGroup SET Name='Benachrichtigungsgruppe bei Fremdaufnahmen',Updated=TO_TIMESTAMP('2021-10-13 18:09:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=3542526 WHERE AD_UserGroup_ID=540003
;


