-- 2019-04-04T16:49:30.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Interval in Stunden bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.',Updated=TO_TIMESTAMP('2019-04-04 16:49:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576547 AND AD_Language='de_CH'
;

-- 2019-04-04T16:49:30.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576547,'de_CH') 
;

-- 2019-04-04T16:49:36.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Interval in Stunden bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.',Updated=TO_TIMESTAMP('2019-04-04 16:49:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576547 AND AD_Language='de_DE'
;

-- 2019-04-04T16:49:36.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576547,'de_DE') 
;

-- 2019-04-04T16:49:36.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576547,'de_DE') 
;

-- 2019-04-04T16:49:36.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SalesOrderLookBehindHours', Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', Description='Interval in Stunden bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Help=NULL WHERE AD_Element_ID=576547
;

-- 2019-04-04T16:49:36.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesOrderLookBehindHours', Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', Description='Interval in Stunden bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Help=NULL, AD_Element_ID=576547 WHERE UPPER(ColumnName)='SALESORDERLOOKBEHINDHOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-04T16:49:36.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesOrderLookBehindHours', Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', Description='Interval in Stunden bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Help=NULL WHERE AD_Element_ID=576547 AND IsCentrallyMaintained='Y'
;

-- 2019-04-04T16:49:36.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', Description='Interval in Stunden bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576547) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576547)
;

-- 2019-04-04T16:49:36.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', Description='Interval in Stunden bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576547
;

-- 2019-04-04T16:49:36.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', Description='Interval in Stunden bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Help=NULL WHERE AD_Element_ID = 576547
;

-- 2019-04-04T16:49:36.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rückschauinterval Auftragspositionen in Bearb. (Std)', Description = 'Interval in Stunden bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576547
;

-- 2019-04-04T16:50:11.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.',Updated=TO_TIMESTAMP('2019-04-04 16:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='de_CH'
;

-- 2019-04-04T16:50:11.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'de_CH') 
;

-- 2019-04-04T16:50:16.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.',Updated=TO_TIMESTAMP('2019-04-04 16:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='de_DE'
;

-- 2019-04-04T16:50:16.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'de_DE') 
;

-- 2019-04-04T16:50:16.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576545,'de_DE') 
;

-- 2019-04-04T16:50:16.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE AD_Element_ID=576545
;

-- 2019-04-04T16:50:16.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.', AD_Element_ID=576545 WHERE UPPER(ColumnName)='SHIPMENTDATELOOKAHEADHOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-04T16:50:16.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE AD_Element_ID=576545 AND IsCentrallyMaintained='Y'
;

-- 2019-04-04T16:50:16.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576545) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576545)
;

-- 2019-04-04T16:50:16.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.', CommitWarning = NULL WHERE AD_Element_ID = 576545
;

-- 2019-04-04T16:50:16.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE AD_Element_ID = 576545
;

-- 2019-04-04T16:50:16.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vorausschauinterval zu gepl. Lieferungen (Std)', Description = 'Interval in Stunden ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576545
;

