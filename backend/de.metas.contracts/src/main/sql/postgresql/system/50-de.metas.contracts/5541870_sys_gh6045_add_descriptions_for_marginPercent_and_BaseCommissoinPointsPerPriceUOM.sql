-- 2020-01-17T12:42:22.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2020-01-17 14:42:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577454 AND AD_Language='de_CH'
;

-- 2020-01-17T12:42:22.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577454,'de_CH') 
;

-- 2020-01-17T12:42:54.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.', Help='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.',Updated=TO_TIMESTAMP('2020-01-17 14:42:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577454 AND AD_Language='de_CH'
;

-- 2020-01-17T12:42:54.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577454,'de_CH') 
;

-- 2020-01-17T12:43:06.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Provisionsbasispunkte pro Preiseinheit', PrintName='Provisionsbasispunkte pro Preiseinheit',Updated=TO_TIMESTAMP('2020-01-17 14:43:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577454 AND AD_Language='de_CH'
;

-- 2020-01-17T12:43:06.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577454,'de_CH') 
;

-- 2020-01-17T12:43:26.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', Name='Provisionsbasispunkte pro Preiseinheit', PrintName='Provisionsbasispunkte pro Preiseinheit',Updated=TO_TIMESTAMP('2020-01-17 14:43:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577454 AND AD_Language='de_DE'
;

-- 2020-01-17T12:43:26.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577454,'de_DE') 
;

-- 2020-01-17T12:43:26.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577454,'de_DE') 
;

-- 2020-01-17T12:43:26.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Base_Commission_Points_Per_Price_UOM', Name='Provisionsbasispunkte pro Preiseinheit', Description='', Help='' WHERE AD_Element_ID=577454
;

-- 2020-01-17T12:43:26.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Base_Commission_Points_Per_Price_UOM', Name='Provisionsbasispunkte pro Preiseinheit', Description='', Help='', AD_Element_ID=577454 WHERE UPPER(ColumnName)='BASE_COMMISSION_POINTS_PER_PRICE_UOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-17T12:43:26.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Base_Commission_Points_Per_Price_UOM', Name='Provisionsbasispunkte pro Preiseinheit', Description='', Help='' WHERE AD_Element_ID=577454 AND IsCentrallyMaintained='Y'
;

-- 2020-01-17T12:43:26.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Provisionsbasispunkte pro Preiseinheit', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577454) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577454)
;

-- 2020-01-17T12:43:26.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Provisionsbasispunkte pro Preiseinheit', Name='Provisionsbasispunkte pro Preiseinheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577454)
;

-- 2020-01-17T12:43:26.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Provisionsbasispunkte pro Preiseinheit', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 577454
;

-- 2020-01-17T12:43:26.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Provisionsbasispunkte pro Preiseinheit', Description='', Help='' WHERE AD_Element_ID = 577454
;

-- 2020-01-17T12:43:26.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Provisionsbasispunkte pro Preiseinheit', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577454
;

-- 2020-01-17T12:43:32.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.', Help='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.',Updated=TO_TIMESTAMP('2020-01-17 14:43:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577454 AND AD_Language='de_DE'
;

-- 2020-01-17T12:43:32.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577454,'de_DE') 
;

-- 2020-01-17T12:43:32.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577454,'de_DE') 
;

-- 2020-01-17T12:43:32.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Base_Commission_Points_Per_Price_UOM', Name='Provisionsbasispunkte pro Preiseinheit', Description='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.', Help='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.' WHERE AD_Element_ID=577454
;

-- 2020-01-17T12:43:32.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Base_Commission_Points_Per_Price_UOM', Name='Provisionsbasispunkte pro Preiseinheit', Description='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.', Help='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.', AD_Element_ID=577454 WHERE UPPER(ColumnName)='BASE_COMMISSION_POINTS_PER_PRICE_UOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-17T12:43:32.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Base_Commission_Points_Per_Price_UOM', Name='Provisionsbasispunkte pro Preiseinheit', Description='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.', Help='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.' WHERE AD_Element_ID=577454 AND IsCentrallyMaintained='Y'
;

-- 2020-01-17T12:43:32.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Provisionsbasispunkte pro Preiseinheit', Description='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.', Help='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577454) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577454)
;

-- 2020-01-17T12:43:32.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Provisionsbasispunkte pro Preiseinheit', Description='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.', Help='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.', CommitWarning = NULL WHERE AD_Element_ID = 577454
;

-- 2020-01-17T12:43:32.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Provisionsbasispunkte pro Preiseinheit', Description='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.', Help='Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.' WHERE AD_Element_ID = 577454
;

-- 2020-01-17T12:43:32.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Provisionsbasispunkte pro Preiseinheit', Description = 'Gibt an, wie viele Provisionspunkte pro 1 Menge des Produktes in der Preiseinheit berechnet werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577454
;

-- 2020-01-17T12:43:43.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2020-01-17 14:43:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577454 AND AD_Language='en_US'
;

-- 2020-01-17T12:43:43.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577454,'en_US') 
;

-- 2020-01-17T12:43:47.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies how many commission points are calculated per 1 quantity of the product in the price UOM.', Help='Specifies how many commission points are calculated per 1 quantity of the product in the price UOM.',Updated=TO_TIMESTAMP('2020-01-17 14:43:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577454 AND AD_Language='en_US'
;

-- 2020-01-17T12:43:47.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577454,'en_US') 
;

-- 2020-01-17T12:44:02.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2020-01-17 14:44:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577454 AND AD_Language='nl_NL'
;

-- 2020-01-17T12:44:02.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577454,'nl_NL') 
;

-- 2020-01-17T12:45:55.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies the percentage that the sales partner takes out of his commission to cede to the configured customer as a price deduction.', Help='Specifies the percentage that the sales partner takes out of his commission to cede to the configured customer as a price deduction.',Updated=TO_TIMESTAMP('2020-01-17 14:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577415 AND AD_Language='en_US'
;

-- 2020-01-17T12:45:55.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577415,'en_US') 
;

-- 2020-01-17T12:46:07.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.', Help='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.',Updated=TO_TIMESTAMP('2020-01-17 14:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577415 AND AD_Language='de_DE'
;

-- 2020-01-17T12:46:07.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577415,'de_DE') 
;

-- 2020-01-17T12:46:07.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577415,'de_DE') 
;

-- 2020-01-17T12:46:07.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Margin_Percent', Name='Marge %', Description='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.', Help='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.' WHERE AD_Element_ID=577415
;

-- 2020-01-17T12:46:07.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Margin_Percent', Name='Marge %', Description='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.', Help='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.', AD_Element_ID=577415 WHERE UPPER(ColumnName)='MARGIN_PERCENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-17T12:46:07.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Margin_Percent', Name='Marge %', Description='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.', Help='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.' WHERE AD_Element_ID=577415 AND IsCentrallyMaintained='Y'
;

-- 2020-01-17T12:46:07.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Marge %', Description='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.', Help='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577415) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577415)
;

-- 2020-01-17T12:46:07.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Marge %', Description='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.', Help='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.', CommitWarning = NULL WHERE AD_Element_ID = 577415
;

-- 2020-01-17T12:46:07.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Marge %', Description='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.', Help='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.' WHERE AD_Element_ID = 577415
;

-- 2020-01-17T12:46:07.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Marge %', Description = 'Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577415
;

-- 2020-01-17T12:46:13.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.', Help='Gibt den Prozentsatz an, den der Vertriebspartner von seiner Provision abzieht, um sie an den konfigurierten Kunden als Preisnachlass abzutreten.',Updated=TO_TIMESTAMP('2020-01-17 14:46:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577415 AND AD_Language='de_CH'
;

-- 2020-01-17T12:46:13.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577415,'de_CH') 
;

