-- 2019-02-07T18:10:19.539
-- #298 changing anz. stellen
UPDATE AD_Process SET IsTranslateExcelHeaders='N',Updated=TO_TIMESTAMP('2019-02-07 18:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541043
;

-- 2019-02-08T10:17:33.806
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-08 10:17:33','YYYY-MM-DD HH24:MI:SS'),Description='Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.' WHERE AD_Element_ID=576095 AND AD_Language='de_DE'
;

-- 2019-02-08T10:17:33.861
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576095,'de_DE') 
;

-- 2019-02-08T10:17:33.947
-- #298 changing anz. stellen
/* DDL */  select update_ad_element_on_ad_element_trl_update(576095,'de_DE') 
;

-- 2019-02-08T10:17:33.956
-- #298 changing anz. stellen
UPDATE AD_Column SET ColumnName='IsTranslateExcelHeaders', Name='Excel-Spaltenüberschriften übersetzen', Description='Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.', Help=NULL WHERE AD_Element_ID=576095
;

-- 2019-02-08T10:17:33.959
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='IsTranslateExcelHeaders', Name='Excel-Spaltenüberschriften übersetzen', Description='Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.', Help=NULL, AD_Element_ID=576095 WHERE UPPER(ColumnName)='ISTRANSLATEEXCELHEADERS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-08T10:17:33.964
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='IsTranslateExcelHeaders', Name='Excel-Spaltenüberschriften übersetzen', Description='Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.', Help=NULL WHERE AD_Element_ID=576095 AND IsCentrallyMaintained='Y'
;

-- 2019-02-08T10:17:33.967
-- #298 changing anz. stellen
UPDATE AD_Field SET Name='Excel-Spaltenüberschriften übersetzen', Description='Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576095) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576095)
;

-- 2019-02-08T10:17:34.170
-- #298 changing anz. stellen
UPDATE AD_Tab SET Name='Excel-Spaltenüberschriften übersetzen', Description='Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576095
;

-- 2019-02-08T10:17:34.173
-- #298 changing anz. stellen
UPDATE AD_WINDOW SET Name='Excel-Spaltenüberschriften übersetzen', Description='Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.', Help=NULL WHERE AD_Element_ID = 576095
;

-- 2019-02-08T10:17:34.176
-- #298 changing anz. stellen
UPDATE AD_Menu SET Name='Excel-Spaltenüberschriften übersetzen', Description='Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576095
;

-- 2019-02-08T10:17:51.604
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-08 10:17:51','YYYY-MM-DD HH24:MI:SS'),Description='If checked, then metasfresh will replace the header name with a translation, if one exists in Message (AD_Message) or Element (AD_Element).' WHERE AD_Element_ID=576095 AND AD_Language='en_US'
;

-- 2019-02-08T10:17:51.609
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576095,'en_US') 
;

-- 2019-02-08T10:18:06.772
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-08 10:18:06','YYYY-MM-DD HH24:MI:SS'),Description='Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.' WHERE AD_Element_ID=576095 AND AD_Language='de_CH'
;

-- 2019-02-08T10:18:06.776
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576095,'de_CH') 
;

