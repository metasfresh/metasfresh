-- TRL External ID
-- 2021-06-23T12:13:12.625Z
-- 2021-06-23T12:13:12.625Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Externe ID', PrintName='Externe ID',Updated=TO_TIMESTAMP('2021-06-23 14:13:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543939 AND AD_Language='nl_NL'
;

-- 2021-06-23T12:13:12.644Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543939,'nl_NL')
;

-- 2021-06-23T12:13:17.171Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-23 14:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543939 AND AD_Language='en_US'
;

-- 2021-06-23T12:13:17.175Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543939,'en_US')
;

-- 2021-06-23T12:13:23.815Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Externe ID', PrintName='Externe ID',Updated=TO_TIMESTAMP('2021-06-23 14:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543939 AND AD_Language='de_CH'
;

-- 2021-06-23T12:13:23.816Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543939,'de_CH')
;

-- 2021-06-23T12:13:33.781Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Externe ID', PrintName='Externe ID',Updated=TO_TIMESTAMP('2021-06-23 14:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543939 AND AD_Language='de_DE'
;

-- 2021-06-23T12:13:33.785Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543939,'de_DE')
;

-- 2021-06-23T12:13:33.792Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(543939,'de_DE')
;

-- 2021-06-23T12:13:33.793Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='ExternalId', Name='Externe ID', Description=NULL, Help=NULL WHERE AD_Element_ID=543939
;

-- 2021-06-23T12:13:33.796Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ExternalId', Name='Externe ID', Description=NULL, Help=NULL, AD_Element_ID=543939 WHERE UPPER(ColumnName)='EXTERNALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T12:13:33.798Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ExternalId', Name='Externe ID', Description=NULL, Help=NULL WHERE AD_Element_ID=543939 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T12:13:33.798Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Externe ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543939) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543939)
;

-- 2021-06-23T12:13:33.817Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Externe ID', Name='Externe ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543939)
;

-- 2021-06-23T12:13:33.818Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Externe ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543939
;

-- 2021-06-23T12:13:33.819Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Externe ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 543939
;

-- 2021-06-23T12:13:33.820Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Externe ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543939
;

-- TRL Invoiced

-- 2021-06-23T12:16:29.205Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Fakturiert?', IsTranslated='Y', Name='Fakturiert', PrintName='Fakturiert',Updated=TO_TIMESTAMP('2021-06-23 14:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=387 AND AD_Language='fr_CH'
;

-- 2021-06-23T12:16:29.208Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(387,'fr_CH')
;

-- 2021-06-23T12:16:37.182Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-23 14:16:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=387 AND AD_Language='en_GB'
;

-- 2021-06-23T12:16:37.185Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(387,'en_GB')
;

-- 2021-06-23T12:16:45.183Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Fakturiert?', IsTranslated='Y', Name='Fakturiert', PrintName='Fakturiert',Updated=TO_TIMESTAMP('2021-06-23 14:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=387 AND AD_Language='de_CH'
;

-- 2021-06-23T12:16:45.183Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(387,'de_CH')
;

-- 2021-06-23T12:17:01.054Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Fakturiert?', IsTranslated='Y', Name='Fakturiert', PrintName='Fakturiert',Updated=TO_TIMESTAMP('2021-06-23 14:17:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=387 AND AD_Language='nl_NL'
;

-- 2021-06-23T12:17:01.054Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(387,'nl_NL')
;

-- 2021-06-23T12:17:08.905Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Fakturiert?', IsTranslated='Y', Name='Fakturiert', PrintName='Fakturiert',Updated=TO_TIMESTAMP('2021-06-23 14:17:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=387 AND AD_Language='de_DE'
;

-- 2021-06-23T12:17:08.907Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(387,'de_DE')
;

-- 2021-06-23T12:17:08.931Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(387,'de_DE')
;

-- 2021-06-23T12:17:08.932Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsInvoiced', Name='Fakturiert', Description='Fakturiert?', Help='If selected, invoices are created' WHERE AD_Element_ID=387
;

-- 2021-06-23T12:17:08.934Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsInvoiced', Name='Fakturiert', Description='Fakturiert?', Help='If selected, invoices are created', AD_Element_ID=387 WHERE UPPER(ColumnName)='ISINVOICED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T12:17:08.935Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsInvoiced', Name='Fakturiert', Description='Fakturiert?', Help='If selected, invoices are created' WHERE AD_Element_ID=387 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T12:17:08.936Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Fakturiert', Description='Fakturiert?', Help='If selected, invoices are created' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=387) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 387)
;

-- 2021-06-23T12:17:08.954Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Fakturiert', Name='Fakturiert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=387)
;

-- 2021-06-23T12:17:08.955Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Fakturiert', Description='Fakturiert?', Help='If selected, invoices are created', CommitWarning = NULL WHERE AD_Element_ID = 387
;

-- 2021-06-23T12:17:08.956Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Fakturiert', Description='Fakturiert?', Help='If selected, invoices are created' WHERE AD_Element_ID = 387
;

-- 2021-06-23T12:17:08.957Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Fakturiert', Description = 'Fakturiert?', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 387
;

-- TRL Delivery info
-- 2021-06-23T12:18:36.610Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferinformationen', PrintName='Lieferinformationen',Updated=TO_TIMESTAMP('2021-06-23 14:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578126 AND AD_Language='de_CH'
;

-- 2021-06-23T12:18:36.612Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578126,'de_CH')
;

-- 2021-06-23T12:18:42.629Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferinformationen', PrintName='Lieferinformationen',Updated=TO_TIMESTAMP('2021-06-23 14:18:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578126 AND AD_Language='de_DE'
;

-- 2021-06-23T12:18:42.632Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578126,'de_DE')
;

-- 2021-06-23T12:18:42.656Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578126,'de_DE')
;

-- 2021-06-23T12:18:42.658Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DeliveryInfo', Name='Lieferinformationen', Description=NULL, Help=NULL WHERE AD_Element_ID=578126
;

-- 2021-06-23T12:18:42.660Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DeliveryInfo', Name='Lieferinformationen', Description=NULL, Help=NULL, AD_Element_ID=578126 WHERE UPPER(ColumnName)='DELIVERYINFO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T12:18:42.661Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DeliveryInfo', Name='Lieferinformationen', Description=NULL, Help=NULL WHERE AD_Element_ID=578126 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T12:18:42.662Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Lieferinformationen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578126) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578126)
;

-- 2021-06-23T12:18:42.678Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferinformationen', Name='Lieferinformationen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578126)
;

-- 2021-06-23T12:18:42.679Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Lieferinformationen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578126
;

-- 2021-06-23T12:18:42.680Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Lieferinformationen', Description=NULL, Help=NULL WHERE AD_Element_ID = 578126
;

-- 2021-06-23T12:18:42.680Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Lieferinformationen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578126
;

-- 2021-06-23T12:18:47.113Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-23 14:18:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578126 AND AD_Language='en_US'
;

-- 2021-06-23T12:18:47.115Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578126,'en_US')
;

-- 2021-06-23T12:18:53.164Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferinformationen', PrintName='Lieferinformationen',Updated=TO_TIMESTAMP('2021-06-23 14:18:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578126 AND AD_Language='nl_NL'
;

-- 2021-06-23T12:18:53.167Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578126,'nl_NL')
;

-- TRL Payment BPartner
-- 2021-06-23T12:27:07.637Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Zahlungspartner', IsTranslated='Y', Name='Zahlungspartner', PrintName='Zahlungspartner',Updated=TO_TIMESTAMP('2021-06-23 14:27:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2420 AND AD_Language='nl_NL'
;

-- 2021-06-23T12:27:07.639Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2420,'nl_NL')
;

-- 2021-06-23T12:27:22.361Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Zahlungspartner', IsTranslated='Y', Name='Zahlungspartner', PrintName='Zahlungspartner',Updated=TO_TIMESTAMP('2021-06-23 14:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2420 AND AD_Language='de_CH'
;

-- 2021-06-23T12:27:22.361Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2420,'de_CH')
;

-- 2021-06-23T12:27:44.238Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Zahlungspartner', IsTranslated='Y', Name='Zahlungspartner', PrintName='Zahlungspartner',Updated=TO_TIMESTAMP('2021-06-23 14:27:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2420 AND AD_Language='de_DE'
;

-- 2021-06-23T12:27:44.241Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2420,'de_DE')
;

-- 2021-06-23T12:27:44.266Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(2420,'de_DE')
;

-- 2021-06-23T12:27:44.269Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Pay_BPartner_ID', Name='Zahlungspartner', Description='Zahlungspartner', Help=NULL WHERE AD_Element_ID=2420
;

-- 2021-06-23T12:27:44.271Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Pay_BPartner_ID', Name='Zahlungspartner', Description='Zahlungspartner', Help=NULL, AD_Element_ID=2420 WHERE UPPER(ColumnName)='PAY_BPARTNER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T12:27:44.273Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Pay_BPartner_ID', Name='Zahlungspartner', Description='Zahlungspartner', Help=NULL WHERE AD_Element_ID=2420 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T12:27:44.275Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Zahlungspartner', Description='Zahlungspartner', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2420) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2420)
;

-- 2021-06-23T12:27:44.313Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zahlungspartner', Name='Zahlungspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2420)
;

-- 2021-06-23T12:27:44.314Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Zahlungspartner', Description='Zahlungspartner', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 2420
;

-- 2021-06-23T12:27:44.316Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Zahlungspartner', Description='Zahlungspartner', Help=NULL WHERE AD_Element_ID = 2420
;

-- 2021-06-23T12:27:44.317Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Zahlungspartner', Description = 'Zahlungspartner', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2420
;

-- TRL Payment Location
-- 2021-06-23T12:47:32.496Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Zahlungsanschrift', IsTranslated='Y', Name='Zahlungsanschrift', PrintName='Zahlungsanschrift',Updated=TO_TIMESTAMP('2021-06-23 14:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2421 AND AD_Language='de_DE'
;

-- 2021-06-23T12:47:32.497Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2421,'de_DE')
;

-- 2021-06-23T12:47:32.505Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(2421,'de_DE')
;

-- 2021-06-23T12:47:32.506Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Pay_Location_ID', Name='Zahlungsanschrift', Description='Zahlungsanschrift', Help=NULL WHERE AD_Element_ID=2421
;

-- 2021-06-23T12:47:32.507Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Pay_Location_ID', Name='Zahlungsanschrift', Description='Zahlungsanschrift', Help=NULL, AD_Element_ID=2421 WHERE UPPER(ColumnName)='PAY_LOCATION_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T12:47:32.508Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Pay_Location_ID', Name='Zahlungsanschrift', Description='Zahlungsanschrift', Help=NULL WHERE AD_Element_ID=2421 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T12:47:32.509Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Zahlungsanschrift', Description='Zahlungsanschrift', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2421) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2421)
;

-- 2021-06-23T12:47:32.523Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zahlungsanschrift', Name='Zahlungsanschrift' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2421)
;

-- 2021-06-23T12:47:32.525Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Zahlungsanschrift', Description='Zahlungsanschrift', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 2421
;

-- 2021-06-23T12:47:32.526Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Zahlungsanschrift', Description='Zahlungsanschrift', Help=NULL WHERE AD_Element_ID = 2421
;

-- 2021-06-23T12:47:32.526Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Zahlungsanschrift', Description = 'Zahlungsanschrift', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2421
;

-- 2021-06-23T12:47:40.374Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Zahlungsanschrift', IsTranslated='Y', Name='Zahlungsanschrift', PrintName='Zahlungsanschrift',Updated=TO_TIMESTAMP('2021-06-23 14:47:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2421 AND AD_Language='nl_NL'
;

-- 2021-06-23T12:47:40.375Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2421,'nl_NL')
;

-- 2021-06-23T12:47:50.698Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Zahlungsanschrift', IsTranslated='Y', Name='Zahlungsanschrift', PrintName='Zahlungsanschrift',Updated=TO_TIMESTAMP('2021-06-23 14:47:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2421 AND AD_Language='de_CH'
;

-- 2021-06-23T12:47:50.700Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2421,'de_CH')
;

-- TRL Cash Journal Line
-- 2021-06-23T12:51:04.337Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Kassenbuch Zeile', IsTranslated='Y', Name='Kassenbuch Zeile', PrintName='Kassenbuch Zeile',Updated=TO_TIMESTAMP('2021-06-23 14:51:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1464 AND AD_Language='de_CH'
;

-- 2021-06-23T12:51:04.339Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1464,'de_CH')
;

-- 2021-06-23T12:51:15.030Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Kassenbuch Zeile', IsTranslated='Y', Name='Kassenbuch Zeile', PrintName='Kassenbuch Zeile',Updated=TO_TIMESTAMP('2021-06-23 14:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1464 AND AD_Language='nl_NL'
;

-- 2021-06-23T12:51:15.032Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1464,'nl_NL')
;

-- 2021-06-23T12:51:23.933Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Kassenbuch Zeile', IsTranslated='Y', Name='Kassenbuch Zeile', PrintName='Kassenbuch Zeile',Updated=TO_TIMESTAMP('2021-06-23 14:51:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1464 AND AD_Language='de_DE'
;

-- 2021-06-23T12:51:23.933Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1464,'de_DE')
;

-- 2021-06-23T12:51:23.938Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(1464,'de_DE')
;

-- 2021-06-23T12:51:23.939Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_CashLine_ID', Name='Kassenbuch Zeile', Description='Kassenbuch Zeile', Help='The Cash Journal Line indicates a unique line in a cash journal.' WHERE AD_Element_ID=1464
;

-- 2021-06-23T12:51:23.940Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_CashLine_ID', Name='Kassenbuch Zeile', Description='Kassenbuch Zeile', Help='The Cash Journal Line indicates a unique line in a cash journal.', AD_Element_ID=1464 WHERE UPPER(ColumnName)='C_CASHLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T12:51:23.940Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_CashLine_ID', Name='Kassenbuch Zeile', Description='Kassenbuch Zeile', Help='The Cash Journal Line indicates a unique line in a cash journal.' WHERE AD_Element_ID=1464 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T12:51:23.941Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Kassenbuch Zeile', Description='Kassenbuch Zeile', Help='The Cash Journal Line indicates a unique line in a cash journal.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1464) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1464)
;

-- 2021-06-23T12:51:23.952Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Kassenbuch Zeile', Name='Kassenbuch Zeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1464)
;

-- 2021-06-23T12:51:23.952Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Kassenbuch Zeile', Description='Kassenbuch Zeile', Help='The Cash Journal Line indicates a unique line in a cash journal.', CommitWarning = NULL WHERE AD_Element_ID = 1464
;

-- 2021-06-23T12:51:23.953Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Kassenbuch Zeile', Description='Kassenbuch Zeile', Help='The Cash Journal Line indicates a unique line in a cash journal.' WHERE AD_Element_ID = 1464
;

-- 2021-06-23T12:51:23.954Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Kassenbuch Zeile', Description = 'Kassenbuch Zeile', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1464
;

-- TRL Promotion Code
-- 2021-06-23T12:53:28.276Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Werbecode', IsTranslated='Y', Name='Werbecode', PrintName='Werbecode',Updated=TO_TIMESTAMP('2021-06-23 14:53:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53809 AND AD_Language='de_DE'
;

-- 2021-06-23T12:53:28.277Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53809,'de_DE')
;

-- 2021-06-23T12:53:28.285Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(53809,'de_DE')
;

-- 2021-06-23T12:53:28.286Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PromotionCode', Name='Werbecode', Description='Werbecode', Help='If present, user entered the promotion code at sales time to get this promotion' WHERE AD_Element_ID=53809
;

-- 2021-06-23T12:53:28.286Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PromotionCode', Name='Werbecode', Description='Werbecode', Help='If present, user entered the promotion code at sales time to get this promotion', AD_Element_ID=53809 WHERE UPPER(ColumnName)='PROMOTIONCODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T12:53:28.287Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PromotionCode', Name='Werbecode', Description='Werbecode', Help='If present, user entered the promotion code at sales time to get this promotion' WHERE AD_Element_ID=53809 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T12:53:28.287Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Werbecode', Description='Werbecode', Help='If present, user entered the promotion code at sales time to get this promotion' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53809) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 53809)
;

-- 2021-06-23T12:53:28.297Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Werbecode', Name='Werbecode' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53809)
;

-- 2021-06-23T12:53:28.298Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Werbecode', Description='Werbecode', Help='If present, user entered the promotion code at sales time to get this promotion', CommitWarning = NULL WHERE AD_Element_ID = 53809
;

-- 2021-06-23T12:53:28.299Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Werbecode', Description='Werbecode', Help='If present, user entered the promotion code at sales time to get this promotion' WHERE AD_Element_ID = 53809
;

-- 2021-06-23T12:53:28.299Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Werbecode', Description = 'Werbecode', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 53809
;

-- 2021-06-23T12:53:35.167Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Werbecode', IsTranslated='Y', Name='Werbecode', PrintName='Werbecode',Updated=TO_TIMESTAMP('2021-06-23 14:53:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53809 AND AD_Language='nl_NL'
;

-- 2021-06-23T12:53:35.168Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53809,'nl_NL')
;

-- 2021-06-23T12:53:44.924Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Werbecode', IsTranslated='Y', Name='Werbecode', PrintName='Werbecode',Updated=TO_TIMESTAMP('2021-06-23 14:53:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53809 AND AD_Language='de_CH'
;

-- 2021-06-23T12:53:44.926Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53809,'de_CH')
;

-- TRL Description Only
-- 2021-06-23T13:07:03.291Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Nur Beschreibung', IsTranslated='Y', Name='Nur Beschreibung', PrintName='Nur Beschreibung',Updated=TO_TIMESTAMP('2021-06-23 15:07:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2183 AND AD_Language='de_CH'
;

-- 2021-06-23T13:07:03.292Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2183,'de_CH')
;

-- 2021-06-23T13:07:24.328Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Nur Beschreibung', IsTranslated='Y', Name='Nur Beschreibung', PrintName='Nur Beschreibung',Updated=TO_TIMESTAMP('2021-06-23 15:07:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2183 AND AD_Language='nl_NL'
;

-- 2021-06-23T13:07:24.330Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2183,'nl_NL')
;

-- 2021-06-23T13:07:34.983Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Nur Beschreibung', Name='Nur Beschreibung', PrintName='Nur Beschreibung',Updated=TO_TIMESTAMP('2021-06-23 15:07:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2183 AND AD_Language='de_DE'
;

-- 2021-06-23T13:07:34.985Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2183,'de_DE')
;

-- 2021-06-23T13:07:35.012Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(2183,'de_DE')
;

-- 2021-06-23T13:07:35.015Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsDescription', Name='Nur Beschreibung', Description='Nur Beschreibung', Help='If a line is Description Only, e.g. Product Inventory is not corrected. No accounting transactions are created and the amount or totals are not included in the document.  This for including descriptional detail lines, e.g. for an Work Order.' WHERE AD_Element_ID=2183
;

-- 2021-06-23T13:07:35.018Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDescription', Name='Nur Beschreibung', Description='Nur Beschreibung', Help='If a line is Description Only, e.g. Product Inventory is not corrected. No accounting transactions are created and the amount or totals are not included in the document.  This for including descriptional detail lines, e.g. for an Work Order.', AD_Element_ID=2183 WHERE UPPER(ColumnName)='ISDESCRIPTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T13:07:35.021Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDescription', Name='Nur Beschreibung', Description='Nur Beschreibung', Help='If a line is Description Only, e.g. Product Inventory is not corrected. No accounting transactions are created and the amount or totals are not included in the document.  This for including descriptional detail lines, e.g. for an Work Order.' WHERE AD_Element_ID=2183 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T13:07:35.022Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Nur Beschreibung', Description='Nur Beschreibung', Help='If a line is Description Only, e.g. Product Inventory is not corrected. No accounting transactions are created and the amount or totals are not included in the document.  This for including descriptional detail lines, e.g. for an Work Order.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2183) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2183)
;

-- 2021-06-23T13:07:35.053Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Nur Beschreibung', Name='Nur Beschreibung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2183)
;

-- 2021-06-23T13:07:35.055Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Nur Beschreibung', Description='Nur Beschreibung', Help='If a line is Description Only, e.g. Product Inventory is not corrected. No accounting transactions are created and the amount or totals are not included in the document.  This for including descriptional detail lines, e.g. for an Work Order.', CommitWarning = NULL WHERE AD_Element_ID = 2183
;

-- 2021-06-23T13:07:35.056Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Nur Beschreibung', Description='Nur Beschreibung', Help='If a line is Description Only, e.g. Product Inventory is not corrected. No accounting transactions are created and the amount or totals are not included in the document.  This for including descriptional detail lines, e.g. for an Work Order.' WHERE AD_Element_ID = 2183
;

-- 2021-06-23T13:07:35.057Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Nur Beschreibung', Description = 'Nur Beschreibung', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2183
;

-- 2021-06-23T13:08:59.968Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-23 15:08:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2183 AND AD_Language='de_DE'
;

-- 2021-06-23T13:08:59.969Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2183,'de_DE')
;

-- 2021-06-23T13:08:59.974Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(2183,'de_DE')
;

-- TRL Order Compensation Group

-- 2021-06-23T13:15:41.346Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Auftrag Kompensationsgruppe', PrintName='Auftrag Kompensationsgruppe',Updated=TO_TIMESTAMP('2021-06-23 15:15:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543469 AND AD_Language='de_CH'
;

-- 2021-06-23T13:15:41.346Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543469,'de_CH')
;

-- 2021-06-23T13:15:46.249Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-23 15:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543469 AND AD_Language='en_US'
;

-- 2021-06-23T13:15:46.251Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543469,'en_US')
;

-- 2021-06-23T13:15:52.751Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Auftrag Kompensationsgruppe', PrintName='Auftrag Kompensationsgruppe',Updated=TO_TIMESTAMP('2021-06-23 15:15:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543469 AND AD_Language='nl_NL'
;

-- 2021-06-23T13:15:52.751Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543469,'nl_NL')
;

-- 2021-06-23T13:15:58.843Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Auftrag Kompensationsgruppe', PrintName='Auftrag Kompensationsgruppe',Updated=TO_TIMESTAMP('2021-06-23 15:15:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543469 AND AD_Language='de_DE'
;

-- 2021-06-23T13:15:58.844Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543469,'de_DE')
;

-- 2021-06-23T13:15:58.854Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(543469,'de_DE')
;

-- 2021-06-23T13:15:58.857Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Order_CompensationGroup_ID', Name='Auftrag Kompensationsgruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=543469
;

-- 2021-06-23T13:15:58.858Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Order_CompensationGroup_ID', Name='Auftrag Kompensationsgruppe', Description=NULL, Help=NULL, AD_Element_ID=543469 WHERE UPPER(ColumnName)='C_ORDER_COMPENSATIONGROUP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T13:15:58.859Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Order_CompensationGroup_ID', Name='Auftrag Kompensationsgruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=543469 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T13:15:58.859Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Auftrag Kompensationsgruppe', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543469) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543469)
;

-- 2021-06-23T13:15:58.870Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Auftrag Kompensationsgruppe', Name='Auftrag Kompensationsgruppe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543469)
;

-- 2021-06-23T13:15:58.871Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Auftrag Kompensationsgruppe', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543469
;

-- 2021-06-23T13:15:58.872Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Auftrag Kompensationsgruppe', Description=NULL, Help=NULL WHERE AD_Element_ID = 543469
;

-- 2021-06-23T13:15:58.873Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Auftrag Kompensationsgruppe', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543469
;

-- TRL Compensation percentage

-- 2021-06-23T13:18:44.317Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gruppen Preisminderung', PrintName='Gruppen Preisminderung',Updated=TO_TIMESTAMP('2021-06-23 15:18:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543460 AND AD_Language='de_CH'
;

-- 2021-06-23T13:18:44.317Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543460,'de_CH')
;

-- 2021-06-23T13:18:48.784Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-23 15:18:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543460 AND AD_Language='en_US'
;

-- 2021-06-23T13:18:48.785Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543460,'en_US')
;

-- 2021-06-23T13:18:55.726Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gruppen Preisminderung', PrintName='Gruppen Preisminderung',Updated=TO_TIMESTAMP('2021-06-23 15:18:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543460 AND AD_Language='nl_NL'
;

-- 2021-06-23T13:18:55.727Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543460,'nl_NL')
;

-- 2021-06-23T13:19:01.722Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gruppen Preisminderung', PrintName='Gruppen Preisminderung',Updated=TO_TIMESTAMP('2021-06-23 15:19:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543460 AND AD_Language='de_DE'
;

-- 2021-06-23T13:19:01.724Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543460,'de_DE')
;

-- 2021-06-23T13:19:01.746Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(543460,'de_DE')
;

-- 2021-06-23T13:19:01.749Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='GroupCompensationPercentage', Name='Gruppen Preisminderung', Description=NULL, Help=NULL WHERE AD_Element_ID=543460
;

-- 2021-06-23T13:19:01.751Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GroupCompensationPercentage', Name='Gruppen Preisminderung', Description=NULL, Help=NULL, AD_Element_ID=543460 WHERE UPPER(ColumnName)='GROUPCOMPENSATIONPERCENTAGE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T13:19:01.754Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GroupCompensationPercentage', Name='Gruppen Preisminderung', Description=NULL, Help=NULL WHERE AD_Element_ID=543460 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T13:19:01.755Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Gruppen Preisminderung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543460) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543460)
;

-- 2021-06-23T13:19:01.782Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Gruppen Preisminderung', Name='Gruppen Preisminderung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543460)
;

-- 2021-06-23T13:19:01.783Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Gruppen Preisminderung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543460
;

-- 2021-06-23T13:19:01.784Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Gruppen Preisminderung', Description=NULL, Help=NULL WHERE AD_Element_ID = 543460
;

-- 2021-06-23T13:19:01.785Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Gruppen Preisminderung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543460
;


-- TRL Compensation Type
-- 2021-06-23T13:20:26.640Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gruppenart', PrintName='Gruppenart',Updated=TO_TIMESTAMP('2021-06-23 15:20:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543461 AND AD_Language='de_CH'
;

-- 2021-06-23T13:20:26.640Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543461,'de_CH')
;

-- 2021-06-23T13:20:31.087Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-23 15:20:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543461 AND AD_Language='en_US'
;

-- 2021-06-23T13:20:31.087Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543461,'en_US')
;

-- 2021-06-23T13:20:36.422Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Gruppenart', PrintName='Gruppenart',Updated=TO_TIMESTAMP('2021-06-23 15:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543461 AND AD_Language='nl_NL'
;

-- 2021-06-23T13:20:36.423Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543461,'nl_NL')
;

-- 2021-06-23T13:20:39.013Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-23 15:20:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543461 AND AD_Language='nl_NL'
;

-- 2021-06-23T13:20:39.015Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543461,'nl_NL')
;

-- 2021-06-23T13:20:45.303Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gruppenart', PrintName='Gruppenart',Updated=TO_TIMESTAMP('2021-06-23 15:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543461 AND AD_Language='de_DE'
;

-- 2021-06-23T13:20:45.305Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543461,'de_DE')
;

-- 2021-06-23T13:20:45.325Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(543461,'de_DE')
;

-- 2021-06-23T13:20:45.327Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='GroupCompensationType', Name='Gruppenart', Description=NULL, Help=NULL WHERE AD_Element_ID=543461
;

-- 2021-06-23T13:20:45.330Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GroupCompensationType', Name='Gruppenart', Description=NULL, Help=NULL, AD_Element_ID=543461 WHERE UPPER(ColumnName)='GROUPCOMPENSATIONTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T13:20:45.333Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GroupCompensationType', Name='Gruppenart', Description=NULL, Help=NULL WHERE AD_Element_ID=543461 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T13:20:45.334Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Gruppenart', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543461) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543461)
;

-- 2021-06-23T13:20:45.360Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Gruppenart', Name='Gruppenart' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543461)
;

-- 2021-06-23T13:20:45.361Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Gruppenart', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543461
;

-- 2021-06-23T13:20:45.363Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Gruppenart', Description=NULL, Help=NULL WHERE AD_Element_ID = 543461
;

-- 2021-06-23T13:20:45.363Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Gruppenart', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543461
;


-- TRL Compensation Amount Type
-- 2021-06-23T13:23:15.088Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Preisminderung Betrag Art', PrintName='Preisminderung Betrag Art',Updated=TO_TIMESTAMP('2021-06-23 15:23:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543462 AND AD_Language='de_CH'
;

-- 2021-06-23T13:23:15.090Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543462,'de_CH')
;

-- 2021-06-23T13:23:18.820Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-23 15:23:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543462 AND AD_Language='en_US'
;

-- 2021-06-23T13:23:18.821Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543462,'en_US')
;

-- 2021-06-23T13:23:24.500Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Preisminderung Betrag Art', PrintName='Preisminderung Betrag Art',Updated=TO_TIMESTAMP('2021-06-23 15:23:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543462 AND AD_Language='nl_NL'
;

-- 2021-06-23T13:23:24.502Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543462,'nl_NL')
;

-- 2021-06-23T13:23:30.084Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Preisminderung Betrag Art', PrintName='Preisminderung Betrag Art',Updated=TO_TIMESTAMP('2021-06-23 15:23:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543462 AND AD_Language='de_DE'
;

-- 2021-06-23T13:23:30.086Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543462,'de_DE')
;

-- 2021-06-23T13:23:30.108Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(543462,'de_DE')
;

-- 2021-06-23T13:23:30.110Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='GroupCompensationAmtType', Name='Preisminderung Betrag Art', Description=NULL, Help=NULL WHERE AD_Element_ID=543462
;

-- 2021-06-23T13:23:30.112Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GroupCompensationAmtType', Name='Preisminderung Betrag Art', Description=NULL, Help=NULL, AD_Element_ID=543462 WHERE UPPER(ColumnName)='GROUPCOMPENSATIONAMTTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T13:23:30.114Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GroupCompensationAmtType', Name='Preisminderung Betrag Art', Description=NULL, Help=NULL WHERE AD_Element_ID=543462 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T13:23:30.115Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Preisminderung Betrag Art', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543462) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543462)
;

-- 2021-06-23T13:23:30.143Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Preisminderung Betrag Art', Name='Preisminderung Betrag Art' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543462)
;

-- 2021-06-23T13:23:30.144Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Preisminderung Betrag Art', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543462
;

-- 2021-06-23T13:23:30.146Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Preisminderung Betrag Art', Description=NULL, Help=NULL WHERE AD_Element_ID = 543462
;

-- 2021-06-23T13:23:30.146Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Preisminderung Betrag Art', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543462
;


-- TRL Group Discount Line
-- 2021-06-23T13:31:27.256Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gruppenrabatt Zeile', PrintName='Gruppenrabatt Zeile',Updated=TO_TIMESTAMP('2021-06-23 15:31:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543458 AND AD_Language='de_CH'
;

-- 2021-06-23T13:31:27.257Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543458,'de_CH')
;

-- 2021-06-23T13:31:33.301Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-23 15:31:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543458 AND AD_Language='en_US'
;

-- 2021-06-23T13:31:33.303Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543458,'en_US')
;

-- 2021-06-23T13:31:38.951Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gruppenrabatt Zeile', PrintName='Gruppenrabatt Zeile',Updated=TO_TIMESTAMP('2021-06-23 15:31:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543458 AND AD_Language='nl_NL'
;

-- 2021-06-23T13:31:38.953Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543458,'nl_NL')
;

-- 2021-06-23T13:31:44.552Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gruppenrabatt Zeile', PrintName='Gruppenrabatt Zeile',Updated=TO_TIMESTAMP('2021-06-23 15:31:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543458 AND AD_Language='de_DE'
;

-- 2021-06-23T13:31:44.554Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543458,'de_DE')
;

-- 2021-06-23T13:31:44.573Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(543458,'de_DE')
;

-- 2021-06-23T13:31:44.575Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsGroupCompensationLine', Name='Gruppenrabatt Zeile', Description=NULL, Help=NULL WHERE AD_Element_ID=543458
;

-- 2021-06-23T13:31:44.578Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsGroupCompensationLine', Name='Gruppenrabatt Zeile', Description=NULL, Help=NULL, AD_Element_ID=543458 WHERE UPPER(ColumnName)='ISGROUPCOMPENSATIONLINE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T13:31:44.580Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsGroupCompensationLine', Name='Gruppenrabatt Zeile', Description=NULL, Help=NULL WHERE AD_Element_ID=543458 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T13:31:44.581Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Gruppenrabatt Zeile', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543458) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543458)
;

-- 2021-06-23T13:31:44.610Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Gruppenrabatt Zeile', Name='Gruppenrabatt Zeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543458)
;

-- 2021-06-23T13:31:44.611Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Gruppenrabatt Zeile', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543458
;

-- 2021-06-23T13:31:44.612Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Gruppenrabatt Zeile', Description=NULL, Help=NULL WHERE AD_Element_ID = 543458
;

-- 2021-06-23T13:31:44.612Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Gruppenrabatt Zeile', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543458
;


-- TRL Discount Schema Break-- 2021-06-23T13:35:28.357Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Rabattschema Stufe', Help='Rabattschema Stufe', IsTranslated='Y', Name='Rabattschema Stufe', PrintName='Rabattschema Stufe',Updated=TO_TIMESTAMP('2021-06-23 15:35:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1715 AND AD_Language='de_DE'
;

-- 2021-06-23T13:35:28.358Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1715,'de_DE')
;

-- 2021-06-23T13:35:28.366Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(1715,'de_DE')
;

-- 2021-06-23T13:35:28.367Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_DiscountSchemaBreak_ID', Name='Rabattschema Stufe', Description='Rabattschema Stufe', Help='Rabattschema Stufe' WHERE AD_Element_ID=1715
;

-- 2021-06-23T13:35:28.367Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_DiscountSchemaBreak_ID', Name='Rabattschema Stufe', Description='Rabattschema Stufe', Help='Rabattschema Stufe', AD_Element_ID=1715 WHERE UPPER(ColumnName)='M_DISCOUNTSCHEMABREAK_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T13:35:28.368Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_DiscountSchemaBreak_ID', Name='Rabattschema Stufe', Description='Rabattschema Stufe', Help='Rabattschema Stufe' WHERE AD_Element_ID=1715 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T13:35:28.368Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rabattschema Stufe', Description='Rabattschema Stufe', Help='Rabattschema Stufe' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1715) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1715)
;

-- 2021-06-23T13:35:28.380Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rabattschema Stufe', Name='Rabattschema Stufe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1715)
;

-- 2021-06-23T13:35:28.381Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rabattschema Stufe', Description='Rabattschema Stufe', Help='Rabattschema Stufe', CommitWarning = NULL WHERE AD_Element_ID = 1715
;

-- 2021-06-23T13:35:28.381Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rabattschema Stufe', Description='Rabattschema Stufe', Help='Rabattschema Stufe' WHERE AD_Element_ID = 1715
;

-- 2021-06-23T13:35:28.382Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rabattschema Stufe', Description = 'Rabattschema Stufe', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1715
;

-- 2021-06-23T13:35:37.112Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Rabattschema Stufe', Help='Rabattschema Stufe', IsTranslated='Y', Name='Rabattschema Stufe', PrintName='Rabattschema Stufe',Updated=TO_TIMESTAMP('2021-06-23 15:35:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1715 AND AD_Language='nl_NL'
;

-- 2021-06-23T13:35:37.113Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1715,'nl_NL')
;

-- 2021-06-23T13:35:50.559Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Rabattschema Stufe', Help='Rabattschema Stufe', IsTranslated='Y', Name='Rabattschema Stufe', PrintName='Rabattschema Stufe',Updated=TO_TIMESTAMP('2021-06-23 15:35:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1715 AND AD_Language='de_CH'
;

-- 2021-06-23T13:35:50.561Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1715,'de_CH')
;


-- TRL Transferred
-- 2021-06-23T13:40:37.305Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Indicates whether the transactions associated with this document are transferred to the General Ledger.', Help='Transferred to General Ledger (i.e. accounted)', IsTranslated='Y', Name='Transfer to General Ledger', PrintName='Transfer to General Ledger',Updated=TO_TIMESTAMP('2021-06-23 15:40:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=419 AND AD_Language='en_GB'
;

-- 2021-06-23T13:40:37.306Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(419,'en_GB')
;

-- 2021-06-23T13:40:57.065Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Indicates whether the transactions associated with this document are transferred to the General Ledger.', Help='Transferred to General Ledger (i.e. accounted)',Updated=TO_TIMESTAMP('2021-06-23 15:40:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=419 AND AD_Language='en_US'
;

-- 2021-06-23T13:40:57.065Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(419,'en_US')
;

-- 2021-06-23T13:41:20.490Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Transfer to General Ledger', PrintName='Transfer to General Ledger',Updated=TO_TIMESTAMP('2021-06-23 15:41:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=419 AND AD_Language='en_US'
;

-- 2021-06-23T13:41:20.491Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(419,'en_US')
;

-- 2021-06-23T13:43:34.391Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Gibt an, ob die mit diesem Dokument verbundenen Transaktionen in die Hauptbuchhaltung übertragen werden.', Help='', Name='In Hauptbuch übertragen', PrintName='In Hauptbuch übertragen',Updated=TO_TIMESTAMP('2021-06-23 15:43:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=419 AND AD_Language='de_DE'
;

-- 2021-06-23T13:43:34.392Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(419,'de_DE')
;

-- 2021-06-23T13:43:34.399Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(419,'de_DE')
;

-- 2021-06-23T13:43:34.400Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsTransferred', Name='In Hauptbuch übertragen', Description='Gibt an, ob die mit diesem Dokument verbundenen Transaktionen in die Hauptbuchhaltung übertragen werden.', Help='' WHERE AD_Element_ID=419
;

-- 2021-06-23T13:43:34.400Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTransferred', Name='In Hauptbuch übertragen', Description='Gibt an, ob die mit diesem Dokument verbundenen Transaktionen in die Hauptbuchhaltung übertragen werden.', Help='', AD_Element_ID=419 WHERE UPPER(ColumnName)='ISTRANSFERRED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T13:43:34.401Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTransferred', Name='In Hauptbuch übertragen', Description='Gibt an, ob die mit diesem Dokument verbundenen Transaktionen in die Hauptbuchhaltung übertragen werden.', Help='' WHERE AD_Element_ID=419 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T13:43:34.401Z
-- URL zum Konzept
UPDATE AD_Field SET Name='In Hauptbuch übertragen', Description='Gibt an, ob die mit diesem Dokument verbundenen Transaktionen in die Hauptbuchhaltung übertragen werden.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=419) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 419)
;

-- 2021-06-23T13:43:34.413Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='In Hauptbuch übertragen', Name='In Hauptbuch übertragen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=419)
;

-- 2021-06-23T13:43:34.413Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='In Hauptbuch übertragen', Description='Gibt an, ob die mit diesem Dokument verbundenen Transaktionen in die Hauptbuchhaltung übertragen werden.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 419
;

-- 2021-06-23T13:43:34.414Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='In Hauptbuch übertragen', Description='Gibt an, ob die mit diesem Dokument verbundenen Transaktionen in die Hauptbuchhaltung übertragen werden.', Help='' WHERE AD_Element_ID = 419
;

-- 2021-06-23T13:43:34.415Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'In Hauptbuch übertragen', Description = 'Gibt an, ob die mit diesem Dokument verbundenen Transaktionen in die Hauptbuchhaltung übertragen werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 419
;

-- 2021-06-23T13:43:36.367Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-23 15:43:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=419 AND AD_Language='de_DE'
;

-- 2021-06-23T13:43:36.368Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(419,'de_DE')
;

-- 2021-06-23T13:43:36.371Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(419,'de_DE')
;

-- 2021-06-23T13:43:47.865Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Gibt an, ob die mit diesem Dokument verbundenen Transaktionen in die Hauptbuchhaltung übertragen werden.', Help='', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-23 15:43:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=419 AND AD_Language='nl_NL'
;

-- 2021-06-23T13:43:47.865Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(419,'nl_NL')
;

-- 2021-06-23T13:43:57.820Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Gibt an, ob die mit diesem Dokument verbundenen Transaktionen in die Hauptbuchhaltung übertragen werden.', Help='',Updated=TO_TIMESTAMP('2021-06-23 15:43:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=419 AND AD_Language='de_CH'
;

-- 2021-06-23T13:43:57.820Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(419,'de_CH')
;

-- 2021-06-23T13:44:13.855Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='In Hauptbuch übertragen', PrintName='In Hauptbuch übertragen',Updated=TO_TIMESTAMP('2021-06-23 15:44:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=419 AND AD_Language='nl_NL'
;

-- 2021-06-23T13:44:13.855Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(419,'nl_NL')
;

-- 2021-06-23T13:44:20.617Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='In Hauptbuch übertragen', PrintName='In Hauptbuch übertragen',Updated=TO_TIMESTAMP('2021-06-23 15:44:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=419 AND AD_Language='de_CH'
;

-- 2021-06-23T13:44:20.617Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(419,'de_CH')
;
