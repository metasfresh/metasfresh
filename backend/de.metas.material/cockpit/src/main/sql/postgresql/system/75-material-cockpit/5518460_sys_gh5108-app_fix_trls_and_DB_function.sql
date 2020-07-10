-- 2019-04-07T06:29:02.692
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Max. Wartezeit auf asynchrone Antwort (ms).',Updated=TO_TIMESTAMP('2019-04-07 06:29:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576559 AND AD_Language='de_CH'
;

-- 2019-04-07T06:29:02.767
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576559,'de_CH') 
;

-- 2019-04-07T06:29:05.945
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Max. Wartezeit auf asynchrone Antwort (ms)',Updated=TO_TIMESTAMP('2019-04-07 06:29:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576559 AND AD_Language='de_CH'
;

-- 2019-04-07T06:29:06.006
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576559,'de_CH') 
;

-- 2019-04-07T06:29:55.207
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Async timeout (ms) ',Updated=TO_TIMESTAMP('2019-04-07 06:29:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576559 AND AD_Language='en_US'
;

-- 2019-04-07T06:29:55.253
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576559,'en_US') 
;

-- 2019-04-07T06:29:58.376
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Async timeout (ms)',Updated=TO_TIMESTAMP('2019-04-07 06:29:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576559 AND AD_Language='en_US'
;

-- 2019-04-07T06:29:58.422
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576559,'en_US') 
;

-- 2019-04-07T06:30:48.348
-- URL zum Konzept
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=275 AND AD_Language='fr_CH'
;

-- 2019-04-07T06:30:48.430
-- URL zum Konzept
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=275 AND AD_Language='it_CH'
;

-- 2019-04-07T06:30:48.509
-- URL zum Konzept
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=275 AND AD_Language='en_GB'
;

-- 2019-04-07T06:30:53.975
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-07 06:30:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=275 AND AD_Language='de_CH'
;

-- 2019-04-07T06:30:54.050
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(275,'de_CH') 
;

-- 2019-04-07T06:31:01.761
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2019-04-07 06:31:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=275 AND AD_Language='en_US'
;

-- 2019-04-07T06:31:01.844
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(275,'en_US') 
;

-- 2019-04-07T06:31:08.624
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Beschreibung ',Updated=TO_TIMESTAMP('2019-04-07 06:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=275 AND AD_Language='de_DE'
;

-- 2019-04-07T06:31:08.695
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(275,'de_DE') 
;

-- 2019-04-07T06:31:08.755
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(275,'de_DE') 
;

-- 2019-04-07T06:31:08.757
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Description', Name='Beschreibung ', Description=NULL, Help=NULL WHERE AD_Element_ID=275
;

-- 2019-04-07T06:31:08.794
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Description', Name='Beschreibung ', Description=NULL, Help=NULL, AD_Element_ID=275 WHERE UPPER(ColumnName)='DESCRIPTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-07T06:31:08.797
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Description', Name='Beschreibung ', Description=NULL, Help=NULL WHERE AD_Element_ID=275 AND IsCentrallyMaintained='Y'
;

-- 2019-04-07T06:31:08.798
-- URL zum Konzept
UPDATE AD_Field SET Name='Beschreibung ', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=275) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 275)
;

-- 2019-04-07T06:31:08.845
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Beschreibung', Name='Beschreibung ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=275)
;

-- 2019-04-07T06:31:08.854
-- URL zum Konzept
UPDATE AD_Tab SET Name='Beschreibung ', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 275
;

-- 2019-04-07T06:31:08.856
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Beschreibung ', Description=NULL, Help=NULL WHERE AD_Element_ID = 275
;

-- 2019-04-07T06:31:08.858
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Beschreibung ', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 275
;

-- 2019-04-07T06:31:10.874
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Beschreibung',Updated=TO_TIMESTAMP('2019-04-07 06:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=275 AND AD_Language='de_DE'
;

-- 2019-04-07T06:31:10.925
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(275,'de_DE') 
;

-- 2019-04-07T06:31:10.991
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(275,'de_DE') 
;

-- 2019-04-07T06:31:10.993
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Description', Name='Beschreibung', Description=NULL, Help=NULL WHERE AD_Element_ID=275
;

-- 2019-04-07T06:31:11.011
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Description', Name='Beschreibung', Description=NULL, Help=NULL, AD_Element_ID=275 WHERE UPPER(ColumnName)='DESCRIPTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-07T06:31:11.014
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Description', Name='Beschreibung', Description=NULL, Help=NULL WHERE AD_Element_ID=275 AND IsCentrallyMaintained='Y'
;

-- 2019-04-07T06:31:11.015
-- URL zum Konzept
UPDATE AD_Field SET Name='Beschreibung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=275) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 275)
;

-- 2019-04-07T06:31:11.041
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Beschreibung', Name='Beschreibung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=275)
;

-- 2019-04-07T06:31:11.051
-- URL zum Konzept
UPDATE AD_Tab SET Name='Beschreibung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 275
;

-- 2019-04-07T06:31:11.053
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Beschreibung', Description=NULL, Help=NULL WHERE AD_Element_ID = 275
;

-- 2019-04-07T06:31:11.055
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Beschreibung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 275
;

-- 2019-04-07T06:34:01.040
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Vorausschauinterval zu gepl. Lieferungen (Std) ',Updated=TO_TIMESTAMP('2019-04-07 06:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='de_CH'
;

-- 2019-04-07T06:34:01.098
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'de_CH') 
;

-- 2019-04-07T06:34:05.809
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Vorausschauinterval zu gepl. Lieferungen (Std) ',Updated=TO_TIMESTAMP('2019-04-07 06:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='de_DE'
;

-- 2019-04-07T06:34:05.862
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'de_DE') 
;

-- 2019-04-07T06:34:05.869
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(576545,'de_DE') 
;

-- 2019-04-07T06:34:05.872
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std) ', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE AD_Element_ID=576545
;

-- 2019-04-07T06:34:05.874
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std) ', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.', AD_Element_ID=576545 WHERE UPPER(ColumnName)='SHIPMENTDATELOOKAHEADHOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-07T06:34:05.876
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std) ', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE AD_Element_ID=576545 AND IsCentrallyMaintained='Y'
;

-- 2019-04-07T06:34:05.877
-- URL zum Konzept
UPDATE AD_Field SET Name='Vorausschauinterval zu gepl. Lieferungen (Std) ', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576545) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576545)
;

-- 2019-04-07T06:34:05.888
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Vorausschauinterval zu gepl. Lieferungen (Std)', Name='Vorausschauinterval zu gepl. Lieferungen (Std) ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576545)
;

-- 2019-04-07T06:34:05.890
-- URL zum Konzept
UPDATE AD_Tab SET Name='Vorausschauinterval zu gepl. Lieferungen (Std) ', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.', CommitWarning = NULL WHERE AD_Element_ID = 576545
;

-- 2019-04-07T06:34:05.892
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Vorausschauinterval zu gepl. Lieferungen (Std) ', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE AD_Element_ID = 576545
;

-- 2019-04-07T06:34:05.897
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Vorausschauinterval zu gepl. Lieferungen (Std) ', Description = 'Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576545
;

-- 2019-04-07T06:34:08.505
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)',Updated=TO_TIMESTAMP('2019-04-07 06:34:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='de_DE'
;

-- 2019-04-07T06:34:08.563
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'de_DE') 
;

-- 2019-04-07T06:34:08.574
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(576545,'de_DE') 
;

-- 2019-04-07T06:34:08.576
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE AD_Element_ID=576545
;

-- 2019-04-07T06:34:08.577
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.', AD_Element_ID=576545 WHERE UPPER(ColumnName)='SHIPMENTDATELOOKAHEADHOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-07T06:34:08.579
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE AD_Element_ID=576545 AND IsCentrallyMaintained='Y'
;

-- 2019-04-07T06:34:08.580
-- URL zum Konzept
UPDATE AD_Field SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576545) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576545)
;

-- 2019-04-07T06:34:08.591
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Vorausschauinterval zu gepl. Lieferungen (Std)', Name='Vorausschauinterval zu gepl. Lieferungen (Std)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576545)
;

-- 2019-04-07T06:34:08.593
-- URL zum Konzept
UPDATE AD_Tab SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.', CommitWarning = NULL WHERE AD_Element_ID = 576545
;

-- 2019-04-07T06:34:08.595
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE AD_Element_ID = 576545
;

-- 2019-04-07T06:34:08.596
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Vorausschauinterval zu gepl. Lieferungen (Std)', Description = 'Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576545
;

-- 2019-04-07T06:34:11.478
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Lookahead interval for planned shipments (hr) ',Updated=TO_TIMESTAMP('2019-04-07 06:34:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='en_US'
;

-- 2019-04-07T06:34:11.535
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'en_US') 
;

-- 2019-04-07T06:34:14.542
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Lookahead interval for planned shipments (hr)',Updated=TO_TIMESTAMP('2019-04-07 06:34:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='en_US'
;

-- 2019-04-07T06:34:14.596
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'en_US') 
;

-- 2019-04-07T06:36:03.154
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Feature aktivtiert ',Updated=TO_TIMESTAMP('2019-04-07 06:36:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576552 AND AD_Language='de_CH'
;

-- 2019-04-07T06:36:03.243
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576552,'de_CH') 
;

-- 2019-04-07T06:36:31.890
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Feature aktivtiert',Updated=TO_TIMESTAMP('2019-04-07 06:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576552 AND AD_Language='de_CH'
;

-- 2019-04-07T06:36:31.978
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576552,'de_CH') 
;

-- 2019-04-07T06:36:34.057
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Feature aktivtiert ',Updated=TO_TIMESTAMP('2019-04-07 06:36:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576552 AND AD_Language='de_DE'
;

-- 2019-04-07T06:36:34.116
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576552,'de_DE') 
;

-- 2019-04-07T06:36:34.125
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(576552,'de_DE') 
;

-- 2019-04-07T06:36:34.127
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsFeatureActivated', Name='Feature aktivtiert ', Description=NULL, Help=NULL WHERE AD_Element_ID=576552
;

-- 2019-04-07T06:36:34.128
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsFeatureActivated', Name='Feature aktivtiert ', Description=NULL, Help=NULL, AD_Element_ID=576552 WHERE UPPER(ColumnName)='ISFEATUREACTIVATED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-07T06:36:34.130
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsFeatureActivated', Name='Feature aktivtiert ', Description=NULL, Help=NULL WHERE AD_Element_ID=576552 AND IsCentrallyMaintained='Y'
;

-- 2019-04-07T06:36:34.131
-- URL zum Konzept
UPDATE AD_Field SET Name='Feature aktivtiert ', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576552) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576552)
;

-- 2019-04-07T06:36:34.142
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Feature aktivtiert', Name='Feature aktivtiert ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576552)
;

-- 2019-04-07T06:36:34.144
-- URL zum Konzept
UPDATE AD_Tab SET Name='Feature aktivtiert ', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576552
;

-- 2019-04-07T06:36:34.146
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Feature aktivtiert ', Description=NULL, Help=NULL WHERE AD_Element_ID = 576552
;

-- 2019-04-07T06:36:34.147
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Feature aktivtiert ', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576552
;

-- 2019-04-07T06:36:36.407
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Feature aktivtiert',Updated=TO_TIMESTAMP('2019-04-07 06:36:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576552 AND AD_Language='de_DE'
;

-- 2019-04-07T06:36:36.489
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576552,'de_DE') 
;

-- 2019-04-07T06:36:36.503
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(576552,'de_DE') 
;

-- 2019-04-07T06:36:36.505
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsFeatureActivated', Name='Feature aktivtiert', Description=NULL, Help=NULL WHERE AD_Element_ID=576552
;

-- 2019-04-07T06:36:36.506
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsFeatureActivated', Name='Feature aktivtiert', Description=NULL, Help=NULL, AD_Element_ID=576552 WHERE UPPER(ColumnName)='ISFEATUREACTIVATED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-07T06:36:36.507
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsFeatureActivated', Name='Feature aktivtiert', Description=NULL, Help=NULL WHERE AD_Element_ID=576552 AND IsCentrallyMaintained='Y'
;

-- 2019-04-07T06:36:36.508
-- URL zum Konzept
UPDATE AD_Field SET Name='Feature aktivtiert', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576552) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576552)
;

-- 2019-04-07T06:36:36.519
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Feature aktivtiert', Name='Feature aktivtiert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576552)
;

-- 2019-04-07T06:36:36.521
-- URL zum Konzept
UPDATE AD_Tab SET Name='Feature aktivtiert', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576552
;

-- 2019-04-07T06:36:36.523
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Feature aktivtiert', Description=NULL, Help=NULL WHERE AD_Element_ID = 576552
;

-- 2019-04-07T06:36:36.524
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Feature aktivtiert', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576552
;

-- 2019-04-07T06:36:38.760
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Feature activated ',Updated=TO_TIMESTAMP('2019-04-07 06:36:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576552 AND AD_Language='en_US'
;

-- 2019-04-07T06:36:38.819
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576552,'en_US') 
;

-- 2019-04-07T06:36:42.157
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Feature activated',Updated=TO_TIMESTAMP('2019-04-07 06:36:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576552 AND AD_Language='en_US'
;

-- 2019-04-07T06:36:42.204
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576552,'en_US') 
;

--
-- delete date result columns; 
-- they prevent matching oder lines from being aggregated and therefore cause the onStock-qty to be multiplied in the end result.
--

-- 2019-04-07T07:17:59.751
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567364
;

-- 2019-04-07T07:17:59.759
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567364
;

-- 2019-04-07T07:18:10.540
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567363
;

-- 2019-04-07T07:18:10.547
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567363
;


DROP FUNCTION IF EXISTS de_metas_material.retrieve_available_for_sales(integer, numeric, character varying, timestamptz, integer, integer);
CREATE FUNCTION de_metas_material.retrieve_available_for_sales(
  IN p_QueryNo integer,
  IN p_M_Product_ID numeric, 
  IN p_StorageAttributesKey character varying, 
  IN p_PreparationDate timestamptz,
  IN p_shipmentDateLookAheadHours integer,
  IN p_salesOrderLookBehindHours integer)
RETURNS TABLE
( QueryNo integer,
  M_Product_ID numeric,
  StorageAttributesKey character varying,
  QtyToBeShipped numeric,
  QtyOnHandStock numeric,
  C_UOM_ID numeric
)
AS
$BODY$
  SELECT 
  	p_QueryNo, 
	p_M_Product_ID, 
	p_StorageAttributesKey,
	QtyToBeShipped, 
	QtyOnHandStock, 
	stock.C_UOM_ID 
  FROM
  (
    SELECT 
      SUM(QtyToBeShipped) AS QtyToBeShipped, 
      C_UOM_ID
    FROM de_metas_material.MD_ShipmentQty_V sq
    WHERE sq.M_Product_ID = p_M_Product_ID
      AND sq.AttributesKey ILIKE '%' || p_StorageAttributesKey || '%'
      AND ( /*max. future PreparationDates we still care about; avoiding coalesce in an attempt to make it easier for the planner */
        sq.s_PreparationDate_Override <= (p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL)
        OR sq.s_PreparationDate_Override IS NULL AND sq.s_PreparationDate <= (p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL)
        OR sq.s_PreparationDate_Override IS NULL AND sq.s_PreparationDate IS NULL AND sq.o_PreparationDate <= (p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL)
      )
      AND (sq.SalesOrderLastUpdated IS NULL /*could have used COALESCE(sq.SalesOrderLastUpdated, now()), but i hope this makes it easier for the planner*/
        OR sq.SalesOrderLastUpdated >= (now() - (p_salesOrderLookBehindHours || ' hours')::INTERVAL) /*min updated value that is not yet too old for us to care */
      )
    GROUP BY C_UOM_ID
  ) sales
  FULL OUTER JOIN (
    SELECT SUM(QtyOnHand) AS QtyOnHandStock, p.C_UOM_ID
    FROM MD_Stock s
      JOIN M_Product p ON p.M_Product_ID=s.M_Product_ID
    WHERE s.M_Product_ID = p_M_Product_ID
      AND s.AttributesKey ILIKE '%' || p_StorageAttributesKey || '%'
      AND s.IsActive='Y'
    GROUP BY C_UOM_ID
  ) stock ON TRUE
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION de_metas_material.retrieve_available_for_sales(integer, numeric, character varying, timestamptz, integer, integer) IS
'Returns the current stock and the shipments to be expected in the nearest future.
Parameters:
* p_QueryNo: the given value is returend in the QueryNo result column; used by the function invoker in the context of select .. union.
* p_M_Product_ID
* p_StorageAttributesKey: the function will return rows whose attributesKey is a substring of this parameter. -1002 means "no storage attributes".
* p_PreparationDate: the date in question, where the respective quantity would have to be available. Returned rows have a ShipmentPreparationDate and SalesOrderLastUpdated within a certain range around this parameter''s value.
* p_shipmentDateLookAheadHours: returned rows have a ShipmentPreparationDate not after p_PreparationDate plus the given number of hours.
* p_salesOrderLookBehindHours: Used to ignore old/stale sales order lines. Returned rows have a SalesOrderLastUpdated date not before now() minus the given number of hours.

Also see https://github.com/metasfresh/metasfresh/issues/5108'
;
