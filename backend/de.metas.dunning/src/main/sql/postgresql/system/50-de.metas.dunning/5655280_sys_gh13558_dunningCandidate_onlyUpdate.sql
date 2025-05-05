-- 2022-09-08T09:08:40.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn dieses Kästchen angehakt ist, werden keine neuen Mahnkandidaten erstellt, sondern die vorhandenen Kandidaten unter Berücksichtigung der aktuellen Auswahl aktualisiert (wenn keine Auswahl getroffen wird, werden alle vorhandenen Datensätze aktualisiert)',Updated=TO_TIMESTAMP('2022-09-08 12:08:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581415 AND AD_Language='de_CH'
;

-- 2022-09-08T09:08:40.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581415,'de_CH')
;

-- 2022-09-08T09:08:44.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn dieses Kästchen angehakt ist, werden keine neuen Mahnkandidaten erstellt, sondern die vorhandenen Kandidaten unter Berücksichtigung der aktuellen Auswahl aktualisiert (wenn keine Auswahl getroffen wird, werden alle vorhandenen Datensätze aktualisiert)',Updated=TO_TIMESTAMP('2022-09-08 12:08:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581415 AND AD_Language='de_DE'
;

-- 2022-09-08T09:08:44.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581415,'de_DE')
;

-- 2022-09-08T09:08:44.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581415,'de_DE')
;

-- 2022-09-08T09:08:44.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsOnlyUpdate', Name='Nur aktualisieren', Description='Wenn dieses Kästchen angehakt ist, werden keine neuen Mahnkandidaten erstellt, sondern die vorhandenen Kandidaten unter Berücksichtigung der aktuellen Auswahl aktualisiert (wenn keine Auswahl getroffen wird, werden alle vorhandenen Datensätze aktualisiert)', Help=NULL WHERE AD_Element_ID=581415
;

-- 2022-09-08T09:08:44.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOnlyUpdate', Name='Nur aktualisieren', Description='Wenn dieses Kästchen angehakt ist, werden keine neuen Mahnkandidaten erstellt, sondern die vorhandenen Kandidaten unter Berücksichtigung der aktuellen Auswahl aktualisiert (wenn keine Auswahl getroffen wird, werden alle vorhandenen Datensätze aktualisiert)', Help=NULL, AD_Element_ID=581415 WHERE UPPER(ColumnName)='ISONLYUPDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-08T09:08:44.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOnlyUpdate', Name='Nur aktualisieren', Description='Wenn dieses Kästchen angehakt ist, werden keine neuen Mahnkandidaten erstellt, sondern die vorhandenen Kandidaten unter Berücksichtigung der aktuellen Auswahl aktualisiert (wenn keine Auswahl getroffen wird, werden alle vorhandenen Datensätze aktualisiert)', Help=NULL WHERE AD_Element_ID=581415 AND IsCentrallyMaintained='Y'
;

-- 2022-09-08T09:08:44.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nur aktualisieren', Description='Wenn dieses Kästchen angehakt ist, werden keine neuen Mahnkandidaten erstellt, sondern die vorhandenen Kandidaten unter Berücksichtigung der aktuellen Auswahl aktualisiert (wenn keine Auswahl getroffen wird, werden alle vorhandenen Datensätze aktualisiert)', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581415) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581415)
;

-- 2022-09-08T09:08:50.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If checked, the process will not create any new candidates but update existing ones taking into consideration the current selection (if no selection is made, it will update all existing records)',Updated=TO_TIMESTAMP('2022-09-08 12:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581415 AND AD_Language='en_US'
;

-- 2022-09-08T09:08:50.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581415,'en_US')
;

-- 2022-09-08T09:08:52.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn dieses Kästchen angehakt ist, werden keine neuen Mahnkandidaten erstellt, sondern die vorhandenen Kandidaten unter Berücksichtigung der aktuellen Auswahl aktualisiert (wenn keine Auswahl getroffen wird, werden alle vorhandenen Datensätze aktualisiert)',Updated=TO_TIMESTAMP('2022-09-08 12:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581415 AND AD_Language='nl_NL'
;

-- 2022-09-08T09:08:52.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581415,'nl_NL')
;