-- 2022-03-01T13:09:31.543Z
-- URL zum Konzept
UPDATE AD_Process SET Name='Zahlungsaufforderung für Eingangsrechnung importieren',Updated=TO_TIMESTAMP('2022-03-01 14:09:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541196
;
-- 2022-03-01T13:35:17.158Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Zahlungsaufforderung für Eingangsrechnung importieren',Updated=TO_TIMESTAMP('2022-03-01 14:35:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541196
;

-- 2022-03-01T14:46:53.468Z
-- URL zum Konzept
UPDATE AD_Process SET Name='Zollrechnung erstellen',Updated=TO_TIMESTAMP('2022-03-01 15:46:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541136
;

-- 2022-03-01T14:47:21.649Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Zollrechnung erstellen',Updated=TO_TIMESTAMP('2022-03-01 15:47:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541136
;

-- 2022-03-01T14:53:16.297Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Add shipments to transportation order',Updated=TO_TIMESTAMP('2022-03-01 15:53:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541225
;

-- 2022-03-01T14:53:54.561Z
-- URL zum Konzept
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Lieferungen zum Transportauftrag hinzufügen',Updated=TO_TIMESTAMP('2022-03-01 15:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541225
;

-- 2022-03-01T14:53:54.559Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Lieferungen zum Transportauftrag hinzufügen',Updated=TO_TIMESTAMP('2022-03-01 15:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541225
;

-- 2022-03-01T14:56:52.397Z
-- URL zum Konzept
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Lieferungen zur Zollrechnung hinzufügen',Updated=TO_TIMESTAMP('2022-03-01 15:56:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541240
;

-- 2022-03-01T14:56:52.395Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Lieferungen zur Zollrechnung hinzufügen',Updated=TO_TIMESTAMP('2022-03-01 15:56:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541240
;
-- 2022-03-01T15:21:27.545Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Beschreibung für alle aktualisieren',Updated=TO_TIMESTAMP('2022-03-01 16:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540474
;

-- 2022-03-01T15:22:09.652Z
-- URL zum Konzept
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Beschreibung für alle aktualisieren',Updated=TO_TIMESTAMP('2022-03-01 16:22:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540474
;

-- 2022-03-01T15:22:09.631Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Beschreibung für alle aktualisieren',Updated=TO_TIMESTAMP('2022-03-01 16:22:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540474
;

-- 2022-03-10T06:34:24.992Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Restbetrag', Name='Restbetrag', PrintName='Restbetrag',Updated=TO_TIMESTAMP('2022-03-10 07:34:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2834 AND AD_Language='de_DE'
;

-- 2022-03-10T06:34:25.012Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2834,'de_DE')
;

-- 2022-03-10T06:34:25.022Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(2834,'de_DE')
;

-- 2022-03-10T06:34:25.023Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='RemainingAmt', Name='Restbetrag', Description='Restbetrag', Help=NULL WHERE AD_Element_ID=2834
;

-- 2022-03-10T06:34:25.024Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='RemainingAmt', Name='Restbetrag', Description='Restbetrag', Help=NULL, AD_Element_ID=2834 WHERE UPPER(ColumnName)='REMAININGAMT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-10T06:34:25.025Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='RemainingAmt', Name='Restbetrag', Description='Restbetrag', Help=NULL WHERE AD_Element_ID=2834 AND IsCentrallyMaintained='Y'
;

-- 2022-03-10T06:34:25.025Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Restbetrag', Description='Restbetrag', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2834) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2834)
;

-- 2022-03-10T06:34:25.040Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Restbetrag', Name='Restbetrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2834)
;

-- 2022-03-10T06:34:25.041Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Restbetrag', Description='Restbetrag', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 2834
;

-- 2022-03-10T06:34:25.042Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Restbetrag', Description='Restbetrag', Help=NULL WHERE AD_Element_ID = 2834
;

-- 2022-03-10T06:34:25.043Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Restbetrag', Description = 'Restbetrag', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2834
;

-- 2022-03-10T06:34:52.784Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Restbetrag', IsTranslated='Y', Name='Restbetrag', PrintName='Restbetrag',Updated=TO_TIMESTAMP('2022-03-10 07:34:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2834 AND AD_Language='de_CH'
;

-- 2022-03-10T06:34:52.785Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2834,'de_CH')
;

-- 2022-03-10T06:35:31.532Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Balance', Name='Balance', PrintName='Balance',Updated=TO_TIMESTAMP('2022-03-10 07:35:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2834 AND AD_Language='en_GB'
;

-- 2022-03-10T06:35:31.536Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2834,'en_GB')
;

-- 2022-03-10T06:36:00.753Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Balance', Name='Balance', PrintName='Balance',Updated=TO_TIMESTAMP('2022-03-10 07:36:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2834 AND AD_Language='en_US'
;

-- 2022-03-10T06:36:00.753Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2834,'en_US')
;

-- 2022-03-03T08:13:35.934Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2834,'de_DE')
;

-- 2022-03-03T08:13:35.947Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(2834,'de_DE')
;

-- 2022-03-03T08:23:27.106Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-03 09:23:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542728 AND AD_Language='de_DE'
;

-- 2022-03-03T08:23:27.110Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542728,'de_DE')
;

-- 2022-03-03T08:23:27.136Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(542728,'de_DE')
;

-- 2022-03-03T08:24:53.640Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Erster Lieferort',Updated=TO_TIMESTAMP('2022-03-03 09:24:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542728 AND AD_Language='de_DE'
;

-- 2022-03-03T08:57:36.592Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000072,'de_DE')
;

-- 2022-03-03T08:57:36.599Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(1000072,'de_DE')
;

-- 2022-03-03T11:04:13.990Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-03 12:04:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541233
;

-- 2022-03-03T11:07:38.826Z
-- URL zum Konzept
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Lieferungen zum Transportauftrag hinzufügen',Updated=TO_TIMESTAMP('2022-03-03 12:07:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541233
;

-- 2022-03-03T11:07:38.819Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Lieferungen zum Transportauftrag hinzufügen',Updated=TO_TIMESTAMP('2022-03-03 12:07:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541233
;
-- 2022-03-10T06:45:42.338Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='BParter location of primary shipment/receipt', PrintName='Primary ship-to location',Updated=TO_TIMESTAMP('2022-03-10 07:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542728 AND AD_Language='en_US'
;

-- 2022-03-10T06:45:42.339Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542728,'en_US')
;

-- 2022-03-10T06:46:22.908Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Primärer Lieferort', PrintName='Primärer Lieferort',Updated=TO_TIMESTAMP('2022-03-10 07:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542728 AND AD_Language='de_DE'
;

-- 2022-03-10T06:46:22.909Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542728,'de_DE')
;

-- 2022-03-10T06:46:22.915Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(542728,'de_DE')
;

-- 2022-03-10T06:46:22.915Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='First_Ship_BPLocation_ID', Name='Primärer Lieferort', Description='BParter location of first shipment/receipt', Help=NULL WHERE AD_Element_ID=542728
;

-- 2022-03-10T06:46:22.916Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='First_Ship_BPLocation_ID', Name='Primärer Lieferort', Description='BParter location of first shipment/receipt', Help=NULL, AD_Element_ID=542728 WHERE UPPER(ColumnName)='FIRST_SHIP_BPLOCATION_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-10T06:46:22.916Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='First_Ship_BPLocation_ID', Name='Primärer Lieferort', Description='BParter location of first shipment/receipt', Help=NULL WHERE AD_Element_ID=542728 AND IsCentrallyMaintained='Y'
;

-- 2022-03-10T06:46:22.917Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Primärer Lieferort', Description='BParter location of first shipment/receipt', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542728) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542728)
;

-- 2022-03-10T06:46:22.928Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Primärer Lieferort', Name='PrimÃ¤rer Lieferort' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542728)
;

-- 2022-03-10T06:46:22.928Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Primärer Lieferort', Description='BParter location of first shipment/receipt', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542728
;

-- 2022-03-10T06:46:22.929Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Primärer Lieferort', Description='BParter location of first shipment/receipt', Help=NULL WHERE AD_Element_ID = 542728
;

-- 2022-03-10T06:46:22.930Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Primärer Lieferort', Description = 'BParter location of first shipment/receipt', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542728
;

-- 2022-03-10T06:46:50.837Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Primärer Lieferort', PrintName='Primärer Lieferort',Updated=TO_TIMESTAMP('2022-03-10 07:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542728 AND AD_Language='de_CH'
;

-- 2022-03-10T06:46:50.840Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542728,'de_CH')
;
-- 2022-03-10T06:46:50.840Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542728,'de_CH')
;

-- 2022-03-10T06:57:47.424Z
-- URL zum Konzept
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Zahlung zuordnen',Updated=TO_TIMESTAMP('2022-03-10 07:57:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540554
;

-- 2022-03-10T06:57:47.358Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zahlung zuordnen',Updated=TO_TIMESTAMP('2022-03-10 07:57:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540554
;


