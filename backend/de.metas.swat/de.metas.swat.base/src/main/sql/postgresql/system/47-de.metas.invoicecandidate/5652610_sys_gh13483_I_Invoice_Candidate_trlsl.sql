-- Column: I_Invoice_Candidate.I_IsImported
-- 2022-08-23T13:39:06.301Z
UPDATE AD_Column SET IsCalculated='Y', IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2022-08-23 16:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584189
;

-- 2022-08-25T05:43:20.713Z
UPDATE AD_Element_Trl SET Name='Import - Rechnungskandidaten', PrintName='Import - Rechnungskandidaten',Updated=TO_TIMESTAMP('2022-08-25 08:43:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='de_CH'
;

-- 2022-08-25T05:43:20.744Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'de_CH') 
;

-- 2022-08-25T05:43:24.470Z
UPDATE AD_Element_Trl SET Name='Import - Rechnungskandidaten', PrintName='Import - Rechnungskandidaten',Updated=TO_TIMESTAMP('2022-08-25 08:43:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='de_DE'
;

-- 2022-08-25T05:43:24.472Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'de_DE') 
;

-- 2022-08-25T05:43:24.518Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581363,'de_DE') 
;

-- 2022-08-25T05:43:24.522Z
UPDATE AD_Column SET ColumnName=NULL, Name='Import - Rechnungskandidaten', Description=NULL, Help=NULL WHERE AD_Element_ID=581363
;

-- 2022-08-25T05:43:24.523Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Import - Rechnungskandidaten', Description=NULL, Help=NULL WHERE AD_Element_ID=581363 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T05:43:24.523Z
UPDATE AD_Field SET Name='Import - Rechnungskandidaten', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581363) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581363)
;

-- 2022-08-25T05:43:24.579Z
UPDATE AD_PrintFormatItem pi SET PrintName='Import - Rechnungskandidaten', Name='Import - Rechnungskandidaten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581363)
;

-- 2022-08-25T05:43:24.581Z
UPDATE AD_Tab SET Name='Import - Rechnungskandidaten', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581363
;

-- 2022-08-25T05:43:24.582Z
UPDATE AD_WINDOW SET Name='Import - Rechnungskandidaten', Description=NULL, Help=NULL WHERE AD_Element_ID = 581363
;

-- 2022-08-25T05:43:24.583Z
UPDATE AD_Menu SET   Name = 'Import - Rechnungskandidaten', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581363
;

-- 2022-08-25T05:43:29.419Z
UPDATE AD_Element_Trl SET Name='Import - Rechnungskandidaten', PrintName='Import - Rechnungskandidaten',Updated=TO_TIMESTAMP('2022-08-25 08:43:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='nl_NL'
;

-- 2022-08-25T05:43:29.448Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'nl_NL') 
;

-- 2022-08-25T05:44:14.157Z
UPDATE AD_Element_Trl SET Description='Import von Rechnungskandidaten',Updated=TO_TIMESTAMP('2022-08-25 08:44:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='de_CH'
;

-- 2022-08-25T05:44:14.158Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'de_CH') 
;

-- 2022-08-25T05:44:20.886Z
UPDATE AD_Element_Trl SET Description='Import von Rechnungskandidaten',Updated=TO_TIMESTAMP('2022-08-25 08:44:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='de_DE'
;

-- 2022-08-25T05:44:20.887Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'de_DE') 
;

-- 2022-08-25T05:44:20.893Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581363,'de_DE') 
;

-- 2022-08-25T05:44:20.893Z
UPDATE AD_Column SET ColumnName=NULL, Name='Import - Rechnungskandidaten', Description='Import von Rechnungskandidaten', Help=NULL WHERE AD_Element_ID=581363
;

-- 2022-08-25T05:44:20.894Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Import - Rechnungskandidaten', Description='Import von Rechnungskandidaten', Help=NULL WHERE AD_Element_ID=581363 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T05:44:20.895Z
UPDATE AD_Field SET Name='Import - Rechnungskandidaten', Description='Import von Rechnungskandidaten', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581363) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581363)
;

-- 2022-08-25T05:44:20.902Z
UPDATE AD_Tab SET Name='Import - Rechnungskandidaten', Description='Import von Rechnungskandidaten', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581363
;

-- 2022-08-25T05:44:20.903Z
UPDATE AD_WINDOW SET Name='Import - Rechnungskandidaten', Description='Import von Rechnungskandidaten', Help=NULL WHERE AD_Element_ID = 581363
;

-- 2022-08-25T05:44:20.903Z
UPDATE AD_Menu SET   Name = 'Import - Rechnungskandidaten', Description = 'Import von Rechnungskandidaten', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581363
;

-- 2022-08-25T05:44:22.270Z
UPDATE AD_Element_Trl SET Description='Import von Rechnungskandidaten',Updated=TO_TIMESTAMP('2022-08-25 08:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='nl_NL'
;

-- 2022-08-25T05:44:22.271Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'nl_NL') 
;

-- 2022-08-25T05:44:34.276Z
UPDATE AD_Element_Trl SET Description='Import of invoice candidates',Updated=TO_TIMESTAMP('2022-08-25 08:44:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='en_US'
;

-- 2022-08-25T05:44:34.277Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'en_US') 
;

-- 2022-08-25T05:52:39.036Z
UPDATE AD_Process_Trl SET Name='Import - Rechnungskandidaten',Updated=TO_TIMESTAMP('2022-08-25 08:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585101
;

-- 2022-08-25T05:52:44.488Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Import - Rechnungskandidaten',Updated=TO_TIMESTAMP('2022-08-25 08:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585101
;

-- 2022-08-25T05:52:44.482Z
UPDATE AD_Process_Trl SET Name='Import - Rechnungskandidaten',Updated=TO_TIMESTAMP('2022-08-25 08:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585101
;

-- 2022-08-25T05:52:46.782Z
UPDATE AD_Process_Trl SET Name='Import - Rechnungskandidaten',Updated=TO_TIMESTAMP('2022-08-25 08:52:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585101
;

-- 2022-08-25T05:56:13.797Z
UPDATE AD_Process_Trl SET Description='Rechnungskandidaten in das System importieren',Updated=TO_TIMESTAMP('2022-08-25 08:56:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585101
;

-- 2022-08-25T05:56:14.786Z
UPDATE AD_Process SET Description='Rechnungskandidaten in das System importieren', Help=NULL, Name='Import - Rechnungskandidaten',Updated=TO_TIMESTAMP('2022-08-25 08:56:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585101
;

-- 2022-08-25T05:56:14.781Z
UPDATE AD_Process_Trl SET Description='Rechnungskandidaten in das System importieren',Updated=TO_TIMESTAMP('2022-08-25 08:56:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585101
;

-- 2022-08-25T05:56:15.560Z
UPDATE AD_Process_Trl SET Description='Rechnungskandidaten in das System importieren',Updated=TO_TIMESTAMP('2022-08-25 08:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585101
;

-- 2022-08-25T05:57:22.534Z
UPDATE AD_Process_Trl SET Description='Import invoice candidates into the system',Updated=TO_TIMESTAMP('2022-08-25 08:57:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585101
;

-- 2022-08-25T06:00:27.728Z
UPDATE AD_Element_Trl SET Name='Rechnung Geschäftspartnerwert', PrintName='Rechnung Geschäftspartnerwert',Updated=TO_TIMESTAMP('2022-08-25 09:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='de_CH'
;

-- 2022-08-25T06:00:27.729Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'de_CH') 
;

-- 2022-08-25T06:00:30.311Z
UPDATE AD_Element_Trl SET PrintName='Rechnung Geschäftspartnerwert ',Updated=TO_TIMESTAMP('2022-08-25 09:00:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='de_DE'
;

-- 2022-08-25T06:00:30.312Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'de_DE') 
;

-- 2022-08-25T06:00:30.325Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581361,'de_DE') 
;

-- 2022-08-25T06:00:30.326Z
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung Geschäftspartnerwert ', Name='Bill_BPartner_Value' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581361)
;

-- 2022-08-25T06:00:32.855Z
UPDATE AD_Element_Trl SET PrintName='Rechnung Geschäftspartnerwert ',Updated=TO_TIMESTAMP('2022-08-25 09:00:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='nl_NL'
;

-- 2022-08-25T06:00:32.856Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'nl_NL') 
;

-- 2022-08-25T06:00:40.708Z
UPDATE AD_Element_Trl SET PrintName='Bill Business Partner Value',Updated=TO_TIMESTAMP('2022-08-25 09:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='en_US'
;

-- 2022-08-25T06:00:40.709Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'en_US') 
;

-- 2022-08-25T06:00:50.673Z
UPDATE AD_Element_Trl SET Name='Bill Business Partner Value',Updated=TO_TIMESTAMP('2022-08-25 09:00:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='en_US'
;

-- 2022-08-25T06:00:50.676Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'en_US') 
;

-- 2022-08-25T06:00:54.181Z
UPDATE AD_Element_Trl SET Name='Rechnung Geschäftspartnerwert ',Updated=TO_TIMESTAMP('2022-08-25 09:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='nl_NL'
;

-- 2022-08-25T06:00:54.182Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'nl_NL') 
;

-- 2022-08-25T06:00:55.914Z
UPDATE AD_Element_Trl SET Name='Rechnung Geschäftspartnerwert ',Updated=TO_TIMESTAMP('2022-08-25 09:00:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='de_DE'
;

-- 2022-08-25T06:00:55.916Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'de_DE') 
;

-- 2022-08-25T06:00:55.920Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581361,'de_DE') 
;

-- 2022-08-25T06:00:55.922Z
UPDATE AD_Column SET ColumnName='Bill_BPartner_Value', Name='Rechnung Geschäftspartnerwert ', Description=NULL, Help=NULL WHERE AD_Element_ID=581361
;

-- 2022-08-25T06:00:55.923Z
UPDATE AD_Process_Para SET ColumnName='Bill_BPartner_Value', Name='Rechnung Geschäftspartnerwert ', Description=NULL, Help=NULL, AD_Element_ID=581361 WHERE UPPER(ColumnName)='BILL_BPARTNER_VALUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T06:00:55.924Z
UPDATE AD_Process_Para SET ColumnName='Bill_BPartner_Value', Name='Rechnung Geschäftspartnerwert ', Description=NULL, Help=NULL WHERE AD_Element_ID=581361 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T06:00:55.925Z
UPDATE AD_Field SET Name='Rechnung Geschäftspartnerwert ', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581361) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581361)
;

-- 2022-08-25T06:00:55.936Z
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung Geschäftspartnerwert ', Name='Rechnung Geschäftspartnerwert ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581361)
;

-- 2022-08-25T06:00:55.937Z
UPDATE AD_Tab SET Name='Rechnung Geschäftspartnerwert ', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581361
;

-- 2022-08-25T06:00:55.938Z
UPDATE AD_WINDOW SET Name='Rechnung Geschäftspartnerwert ', Description=NULL, Help=NULL WHERE AD_Element_ID = 581361
;

-- 2022-08-25T06:00:55.938Z
UPDATE AD_Menu SET   Name = 'Rechnung Geschäftspartnerwert ', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581361
;

-- 2022-08-25T06:01:49.363Z
UPDATE AD_Element_Trl SET Name='UOM Wert', PrintName='UOM Wert',Updated=TO_TIMESTAMP('2022-08-25 09:01:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581362 AND AD_Language='de_CH'
;

-- 2022-08-25T06:01:49.364Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581362,'de_CH') 
;

-- 2022-08-25T06:01:51.058Z
UPDATE AD_Element_Trl SET Name='UOM Wert', PrintName='UOM Wert',Updated=TO_TIMESTAMP('2022-08-25 09:01:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581362 AND AD_Language='de_DE'
;

-- 2022-08-25T06:01:51.060Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581362,'de_DE') 
;

-- 2022-08-25T06:01:51.066Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581362,'de_DE') 
;

-- 2022-08-25T06:01:51.094Z
UPDATE AD_Column SET ColumnName='C_UOM_X12DE355', Name='UOM Wert', Description=NULL, Help=NULL WHERE AD_Element_ID=581362
;

-- 2022-08-25T06:01:51.096Z
UPDATE AD_Process_Para SET ColumnName='C_UOM_X12DE355', Name='UOM Wert', Description=NULL, Help=NULL, AD_Element_ID=581362 WHERE UPPER(ColumnName)='C_UOM_X12DE355' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T06:01:51.098Z
UPDATE AD_Process_Para SET ColumnName='C_UOM_X12DE355', Name='UOM Wert', Description=NULL, Help=NULL WHERE AD_Element_ID=581362 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T06:01:51.098Z
UPDATE AD_Field SET Name='UOM Wert', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581362) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581362)
;

-- 2022-08-25T06:01:51.109Z
UPDATE AD_PrintFormatItem pi SET PrintName='UOM Wert', Name='UOM Wert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581362)
;

-- 2022-08-25T06:01:51.109Z
UPDATE AD_Tab SET Name='UOM Wert', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581362
;

-- 2022-08-25T06:01:51.110Z
UPDATE AD_WINDOW SET Name='UOM Wert', Description=NULL, Help=NULL WHERE AD_Element_ID = 581362
;

-- 2022-08-25T06:01:51.111Z
UPDATE AD_Menu SET   Name = 'UOM Wert', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581362
;

-- 2022-08-25T06:01:53.220Z
UPDATE AD_Element_Trl SET Name='UOM Wert', PrintName='UOM Wert',Updated=TO_TIMESTAMP('2022-08-25 09:01:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581362 AND AD_Language='nl_NL'
;

-- 2022-08-25T06:01:53.221Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581362,'nl_NL') 
;

-- 2022-08-25T06:02:04.759Z
UPDATE AD_Element_Trl SET Name='UOM Value', PrintName='UOM Value',Updated=TO_TIMESTAMP('2022-08-25 09:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581362 AND AD_Language='en_US'
;

-- 2022-08-25T06:02:04.759Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581362,'en_US') 
;

-- 2022-08-25T06:18:28.595Z
UPDATE AD_Element_Trl SET Description='UOM Code',Updated=TO_TIMESTAMP('2022-08-25 09:18:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581362 AND AD_Language='en_US'
;

-- 2022-08-25T06:18:28.596Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581362,'en_US') 
;

-- 2022-08-25T06:18:30.243Z
UPDATE AD_Element_Trl SET Description='Kodierung der Mengeneinheit',Updated=TO_TIMESTAMP('2022-08-25 09:18:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581362 AND AD_Language='de_CH'
;

-- 2022-08-25T06:18:30.245Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581362,'de_CH') 
;

-- 2022-08-25T06:18:31.598Z
UPDATE AD_Element_Trl SET Description='Kodierung der Mengeneinheit',Updated=TO_TIMESTAMP('2022-08-25 09:18:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581362 AND AD_Language='de_DE'
;

-- 2022-08-25T06:18:31.599Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581362,'de_DE') 
;

-- 2022-08-25T06:18:31.606Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581362,'de_DE') 
;

-- 2022-08-25T06:18:31.606Z
UPDATE AD_Column SET ColumnName='C_UOM_X12DE355', Name='UOM Wert', Description='Kodierung der Mengeneinheit', Help=NULL WHERE AD_Element_ID=581362
;

-- 2022-08-25T06:18:31.607Z
UPDATE AD_Process_Para SET ColumnName='C_UOM_X12DE355', Name='UOM Wert', Description='Kodierung der Mengeneinheit', Help=NULL, AD_Element_ID=581362 WHERE UPPER(ColumnName)='C_UOM_X12DE355' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T06:18:31.608Z
UPDATE AD_Process_Para SET ColumnName='C_UOM_X12DE355', Name='UOM Wert', Description='Kodierung der Mengeneinheit', Help=NULL WHERE AD_Element_ID=581362 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T06:18:31.608Z
UPDATE AD_Field SET Name='UOM Wert', Description='Kodierung der Mengeneinheit', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581362) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581362)
;

-- 2022-08-25T06:18:31.619Z
UPDATE AD_Tab SET Name='UOM Wert', Description='Kodierung der Mengeneinheit', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581362
;

-- 2022-08-25T06:18:31.620Z
UPDATE AD_WINDOW SET Name='UOM Wert', Description='Kodierung der Mengeneinheit', Help=NULL WHERE AD_Element_ID = 581362
;

-- 2022-08-25T06:18:31.621Z
UPDATE AD_Menu SET   Name = 'UOM Wert', Description = 'Kodierung der Mengeneinheit', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581362
;

-- 2022-08-25T06:18:39.745Z
UPDATE AD_Element_Trl SET Description='Kodierung der Mengeneinheit',Updated=TO_TIMESTAMP('2022-08-25 09:18:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581362 AND AD_Language='nl_NL'
;

-- 2022-08-25T06:18:39.746Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581362,'nl_NL') 
;

-- 2022-08-25T06:20:30.046Z
UPDATE AD_Element_Trl SET Description='Business partner value for billing',Updated=TO_TIMESTAMP('2022-08-25 09:20:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='en_US'
;

-- 2022-08-25T06:20:30.047Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'en_US') 
;

-- 2022-08-25T06:20:31.146Z
UPDATE AD_Element_Trl SET Description='Geschäftspartnerwert für die Rechnungsstellung',Updated=TO_TIMESTAMP('2022-08-25 09:20:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='de_CH'
;

-- 2022-08-25T06:20:31.147Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'de_CH') 
;

-- 2022-08-25T06:20:32.681Z
UPDATE AD_Element_Trl SET Description='Geschäftspartnerwert für die Rechnungsstellung',Updated=TO_TIMESTAMP('2022-08-25 09:20:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='de_DE'
;

-- 2022-08-25T06:20:32.683Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'de_DE') 
;

-- 2022-08-25T06:20:32.688Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581361,'de_DE') 
;

-- 2022-08-25T06:20:32.689Z
UPDATE AD_Column SET ColumnName='Bill_BPartner_Value', Name='Rechnung Geschäftspartnerwert ', Description='Geschäftspartnerwert für die Rechnungsstellung', Help=NULL WHERE AD_Element_ID=581361
;

-- 2022-08-25T06:20:32.689Z
UPDATE AD_Process_Para SET ColumnName='Bill_BPartner_Value', Name='Rechnung Geschäftspartnerwert ', Description='Geschäftspartnerwert für die Rechnungsstellung', Help=NULL, AD_Element_ID=581361 WHERE UPPER(ColumnName)='BILL_BPARTNER_VALUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T06:20:32.690Z
UPDATE AD_Process_Para SET ColumnName='Bill_BPartner_Value', Name='Rechnung Geschäftspartnerwert ', Description='Geschäftspartnerwert für die Rechnungsstellung', Help=NULL WHERE AD_Element_ID=581361 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T06:20:32.690Z
UPDATE AD_Field SET Name='Rechnung Geschäftspartnerwert ', Description='Geschäftspartnerwert für die Rechnungsstellung', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581361) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581361)
;

-- 2022-08-25T06:20:32.700Z
UPDATE AD_Tab SET Name='Rechnung Geschäftspartnerwert ', Description='Geschäftspartnerwert für die Rechnungsstellung', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581361
;

-- 2022-08-25T06:20:32.701Z
UPDATE AD_WINDOW SET Name='Rechnung Geschäftspartnerwert ', Description='Geschäftspartnerwert für die Rechnungsstellung', Help=NULL WHERE AD_Element_ID = 581361
;

-- 2022-08-25T06:20:32.701Z
UPDATE AD_Menu SET   Name = 'Rechnung Geschäftspartnerwert ', Description = 'Geschäftspartnerwert für die Rechnungsstellung', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581361
;

-- 2022-08-25T06:20:35.232Z
UPDATE AD_Element_Trl SET Description='Geschäftspartnerwert für die Rechnungsstellung',Updated=TO_TIMESTAMP('2022-08-25 09:20:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='nl_NL'
;

-- 2022-08-25T06:20:35.233Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'nl_NL') 
;

-- Column: I_Invoice_Candidate.C_DocType_ID
-- 2022-08-25T06:42:41.676Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584227,196,0,19,542207,'C_DocType_ID',TO_TIMESTAMP('2022-08-25 09:42:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Belegart oder Verarbeitungsvorgaben','D',0,10,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Belegart',0,0,TO_TIMESTAMP('2022-08-25 09:42:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-25T06:42:41.682Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584227 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-25T06:42:41.688Z
/* DDL */  select update_Column_Translation_From_AD_Element(196) 
;

-- 2022-08-25T06:42:42.359Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN C_DocType_ID NUMERIC(10)')
;

-- 2022-08-25T06:42:42.386Z
ALTER TABLE I_Invoice_Candidate ADD CONSTRAINT CDocType_IInvoiceCandidate FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
;

-- 2022-08-25T06:43:26.883Z
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2022-08-25 09:43:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='de_CH'
;

-- 2022-08-25T06:43:26.884Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'de_CH') 
;

-- 2022-08-25T06:43:29.328Z
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2022-08-25 09:43:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='de_DE'
;

-- 2022-08-25T06:43:29.329Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'de_DE') 
;

-- 2022-08-25T06:43:29.352Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581361,'de_DE') 
;

-- 2022-08-25T06:43:29.353Z
UPDATE AD_Column SET ColumnName='Bill_BPartner_Value', Name='Rechnung Geschäftspartnerwert ', Description='', Help=NULL WHERE AD_Element_ID=581361
;

-- 2022-08-25T06:43:29.354Z
UPDATE AD_Process_Para SET ColumnName='Bill_BPartner_Value', Name='Rechnung Geschäftspartnerwert ', Description='', Help=NULL, AD_Element_ID=581361 WHERE UPPER(ColumnName)='BILL_BPARTNER_VALUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T06:43:29.355Z
UPDATE AD_Process_Para SET ColumnName='Bill_BPartner_Value', Name='Rechnung Geschäftspartnerwert ', Description='', Help=NULL WHERE AD_Element_ID=581361 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T06:43:29.355Z
UPDATE AD_Field SET Name='Rechnung Geschäftspartnerwert ', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581361) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581361)
;

-- 2022-08-25T06:43:29.368Z
UPDATE AD_Tab SET Name='Rechnung Geschäftspartnerwert ', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581361
;

-- 2022-08-25T06:43:29.369Z
UPDATE AD_WINDOW SET Name='Rechnung Geschäftspartnerwert ', Description='', Help=NULL WHERE AD_Element_ID = 581361
;

-- 2022-08-25T06:43:29.370Z
UPDATE AD_Menu SET   Name = 'Rechnung Geschäftspartnerwert ', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581361
;

-- 2022-08-25T06:43:31.344Z
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2022-08-25 09:43:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='en_US'
;

-- 2022-08-25T06:43:31.345Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'en_US') 
;

-- 2022-08-25T06:43:34.040Z
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2022-08-25 09:43:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='nl_NL'
;

-- 2022-08-25T06:43:34.041Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'nl_NL') 
;

-- 2022-08-25T06:43:59.342Z
UPDATE AD_Element_Trl SET Name='Bill partner search key', PrintName='Bill partner search key',Updated=TO_TIMESTAMP('2022-08-25 09:43:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='en_US'
;

-- 2022-08-25T06:43:59.342Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'en_US') 
;

-- 2022-08-25T06:44:08.620Z
UPDATE AD_Element_Trl SET Name='Rechnungspartner Suchschlüssel', PrintName='Rechnungspartner Suchschlüssel',Updated=TO_TIMESTAMP('2022-08-25 09:44:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='de_CH'
;

-- 2022-08-25T06:44:08.621Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'de_CH') 
;

-- 2022-08-25T06:44:12.366Z
UPDATE AD_Element_Trl SET Name='Rechnungspartner Suchschlüssel', PrintName='Rechnungspartner Suchschlüssel',Updated=TO_TIMESTAMP('2022-08-25 09:44:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='de_DE'
;

-- 2022-08-25T06:44:12.367Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'de_DE') 
;

-- 2022-08-25T06:44:12.373Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581361,'de_DE') 
;

-- 2022-08-25T06:44:12.406Z
UPDATE AD_Column SET ColumnName='Bill_BPartner_Value', Name='Rechnungspartner Suchschlüssel', Description='', Help=NULL WHERE AD_Element_ID=581361
;

-- 2022-08-25T06:44:12.408Z
UPDATE AD_Process_Para SET ColumnName='Bill_BPartner_Value', Name='Rechnungspartner Suchschlüssel', Description='', Help=NULL, AD_Element_ID=581361 WHERE UPPER(ColumnName)='BILL_BPARTNER_VALUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T06:44:12.409Z
UPDATE AD_Process_Para SET ColumnName='Bill_BPartner_Value', Name='Rechnungspartner Suchschlüssel', Description='', Help=NULL WHERE AD_Element_ID=581361 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T06:44:12.410Z
UPDATE AD_Field SET Name='Rechnungspartner Suchschlüssel', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581361) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581361)
;

-- 2022-08-25T06:44:12.421Z
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnungspartner Suchschlüssel', Name='Rechnungspartner Suchschlüssel' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581361)
;

-- 2022-08-25T06:44:12.421Z
UPDATE AD_Tab SET Name='Rechnungspartner Suchschlüssel', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581361
;

-- 2022-08-25T06:44:12.422Z
UPDATE AD_WINDOW SET Name='Rechnungspartner Suchschlüssel', Description='', Help=NULL WHERE AD_Element_ID = 581361
;

-- 2022-08-25T06:44:12.423Z
UPDATE AD_Menu SET   Name = 'Rechnungspartner Suchschlüssel', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581361
;

-- 2022-08-25T06:44:16.334Z
UPDATE AD_Element_Trl SET Name='Rechnungspartner Suchschlüssel', PrintName='Rechnungspartner Suchschlüssel',Updated=TO_TIMESTAMP('2022-08-25 09:44:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361 AND AD_Language='nl_NL'
;

-- 2022-08-25T06:44:16.334Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581361,'nl_NL') 
;

-- Column: I_Invoice_Candidate.X12DE355
-- 2022-08-25T06:44:51.396Z
UPDATE AD_Column SET AD_Element_ID=634, ColumnName='X12DE355', Description='Kodierung gemäß UOM EDI X12', Help='"Kodierung" gibt die Kodierung gemäß EDI X12 Code Data Element 355 (Unit or Basis for Measurement) an.', Name='Kodierung der Mengeneinheit',Updated=TO_TIMESTAMP('2022-08-25 09:44:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584200
;

-- 2022-08-25T06:44:51.421Z
UPDATE AD_Field SET Name='Kodierung der Mengeneinheit', Description='Kodierung gemäß UOM EDI X12', Help='"Kodierung" gibt die Kodierung gemäß EDI X12 Code Data Element 355 (Unit or Basis for Measurement) an.' WHERE AD_Column_ID=584200
;

-- 2022-08-25T06:44:51.422Z
/* DDL */  select update_Column_Translation_From_AD_Element(634) 
;

-- 2022-08-25T06:44:52.019Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN X12DE355 VARCHAR(255)')
;

-- 2022-08-25T06:48:14.107Z
UPDATE AD_Element_Trl SET Description='The "Import Invoice candidate" window contains invoice candidates to be imported into metasfresh through the "Import Invoice Candidate" process',Updated=TO_TIMESTAMP('2022-08-25 09:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='en_US'
;

-- 2022-08-25T06:48:14.108Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'en_US') 
;

-- 2022-08-25T06:50:11.008Z
UPDATE AD_Element_Trl SET Description='The "Import Invoice Candidate" window displays the invoice candidates to be imported into metasfresh via the "Import Invoice Candidate" process.',Updated=TO_TIMESTAMP('2022-08-25 09:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='en_US'
;

-- 2022-08-25T06:50:11.009Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'en_US') 
;

-- 2022-08-25T06:50:16.854Z
UPDATE AD_Element_Trl SET Description='Das Fenster "Rechnungskandidat importieren" zeigt die Rechnungskandidaten an, die über den Prozess "Rechnungskandidat importieren" in metasfresh importiert werden sollen',Updated=TO_TIMESTAMP('2022-08-25 09:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='de_CH'
;

-- 2022-08-25T06:50:16.855Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'de_CH') 
;

-- 2022-08-25T06:50:18.429Z
UPDATE AD_Element_Trl SET Description='Das Fenster "Rechnungskandidat importieren" zeigt die Rechnungskandidaten an, die über den Prozess "Rechnungskandidat importieren" in metasfresh importiert werden sollen',Updated=TO_TIMESTAMP('2022-08-25 09:50:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='de_DE'
;

-- 2022-08-25T06:50:18.430Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'de_DE') 
;

-- 2022-08-25T06:50:18.436Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581363,'de_DE') 
;

-- 2022-08-25T06:50:18.437Z
UPDATE AD_Column SET ColumnName=NULL, Name='Import - Rechnungskandidaten', Description='Das Fenster "Rechnungskandidat importieren" zeigt die Rechnungskandidaten an, die über den Prozess "Rechnungskandidat importieren" in metasfresh importiert werden sollen', Help=NULL WHERE AD_Element_ID=581363
;

-- 2022-08-25T06:50:18.437Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Import - Rechnungskandidaten', Description='Das Fenster "Rechnungskandidat importieren" zeigt die Rechnungskandidaten an, die über den Prozess "Rechnungskandidat importieren" in metasfresh importiert werden sollen', Help=NULL WHERE AD_Element_ID=581363 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T06:50:18.438Z
UPDATE AD_Field SET Name='Import - Rechnungskandidaten', Description='Das Fenster "Rechnungskandidat importieren" zeigt die Rechnungskandidaten an, die über den Prozess "Rechnungskandidat importieren" in metasfresh importiert werden sollen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581363) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581363)
;

-- 2022-08-25T06:50:18.445Z
UPDATE AD_Tab SET Name='Import - Rechnungskandidaten', Description='Das Fenster "Rechnungskandidat importieren" zeigt die Rechnungskandidaten an, die über den Prozess "Rechnungskandidat importieren" in metasfresh importiert werden sollen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581363
;

-- 2022-08-25T06:50:18.446Z
UPDATE AD_WINDOW SET Name='Import - Rechnungskandidaten', Description='Das Fenster "Rechnungskandidat importieren" zeigt die Rechnungskandidaten an, die über den Prozess "Rechnungskandidat importieren" in metasfresh importiert werden sollen', Help=NULL WHERE AD_Element_ID = 581363
;

-- 2022-08-25T06:50:18.447Z
UPDATE AD_Menu SET   Name = 'Import - Rechnungskandidaten', Description = 'Das Fenster "Rechnungskandidat importieren" zeigt die Rechnungskandidaten an, die über den Prozess "Rechnungskandidat importieren" in metasfresh importiert werden sollen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581363
;

/*
 * #%L
 * de.metas.swat.base
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

-- 2022-08-25T06:50:20.963Z
UPDATE AD_Element_Trl SET Description='Das Fenster "Rechnungskandidat importieren" zeigt die Rechnungskandidaten an, die über den Prozess "Rechnungskandidat importieren" in metasfresh importiert werden sollen',Updated=TO_TIMESTAMP('2022-08-25 09:50:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581363 AND AD_Language='nl_NL'
;

-- 2022-08-25T06:50:20.964Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581363,'nl_NL') 
;

-- 2022-08-25T06:50:56.916Z
UPDATE AD_Process_Trl SET Description='',Updated=TO_TIMESTAMP('2022-08-25 09:50:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585101
;

-- 2022-08-25T06:50:58.770Z
UPDATE AD_Process SET Description='', Help=NULL, Name='Import - Rechnungskandidaten',Updated=TO_TIMESTAMP('2022-08-25 09:50:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585101
;

-- 2022-08-25T06:50:58.767Z
UPDATE AD_Process_Trl SET Description='',Updated=TO_TIMESTAMP('2022-08-25 09:50:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585101
;

-- 2022-08-25T06:51:00.441Z
UPDATE AD_Process_Trl SET Description='',Updated=TO_TIMESTAMP('2022-08-25 09:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585101
;

-- 2022-08-25T06:51:34.965Z
UPDATE AD_Process_Trl SET Description='',Updated=TO_TIMESTAMP('2022-08-25 09:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585101
;

