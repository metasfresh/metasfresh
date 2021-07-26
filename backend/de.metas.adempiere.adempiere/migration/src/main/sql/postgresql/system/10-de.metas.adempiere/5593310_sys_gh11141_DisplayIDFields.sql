-- 2021-06-17T10:13:29.773Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646783,0,543956,545888,586790,'F',TO_TIMESTAMP('2021-06-17 12:13:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnung-ÃœberprÃ¼fungssatz',5,0,0,TO_TIMESTAMP('2021-06-17 12:13:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-17T10:16:16.423Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.',Updated=TO_TIMESTAMP('2021-06-17 12:16:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579233 AND AD_Language='de_CH'
;

-- 2021-06-17T10:16:16.439Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579233,'de_CH') 
;

-- 2021-06-17T10:16:23.892Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.',Updated=TO_TIMESTAMP('2021-06-17 12:16:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579233 AND AD_Language='de_DE'
;

-- 2021-06-17T10:16:23.896Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579233,'de_DE') 
;

-- 2021-06-17T10:16:23.924Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579233,'de_DE') 
;

-- 2021-06-17T10:16:23.929Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Invoice_Verification_Set_ID', Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help=NULL WHERE AD_Element_ID=579233
;

-- 2021-06-17T10:16:23.931Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Verification_Set_ID', Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help=NULL, AD_Element_ID=579233 WHERE UPPER(ColumnName)='C_INVOICE_VERIFICATION_SET_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-17T10:16:23.932Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Verification_Set_ID', Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help=NULL WHERE AD_Element_ID=579233 AND IsCentrallyMaintained='Y'
;

-- 2021-06-17T10:16:23.933Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579233) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579233)
;

-- 2021-06-17T10:16:23.948Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579233
;

-- 2021-06-17T10:16:23.950Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help=NULL WHERE AD_Element_ID = 579233
;

-- 2021-06-17T10:16:23.950Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungssatz', Description = 'Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579233
;

-- 2021-06-17T10:17:45.207Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='A verification set contains a selection of existing invoice lines to be verified against possibly altered master data or business logic.',Updated=TO_TIMESTAMP('2021-06-17 12:17:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579233 AND AD_Language='en_US'
;

-- 2021-06-17T10:17:45.208Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579233,'en_US') 
;

-- 2021-06-17T10:17:51.727Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.',Updated=TO_TIMESTAMP('2021-06-17 12:17:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579233 AND AD_Language='nl_NL'
;

-- 2021-06-17T10:17:51.728Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579233,'nl_NL') 
;

-- 2021-06-17T10:21:04.429Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646789,0,543957,545892,586791,'F',TO_TIMESTAMP('2021-06-17 12:21:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnung-Überprüfungselement',5,0,0,TO_TIMESTAMP('2021-06-17 12:21:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-17T10:24:34.185Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-17 12:24:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646800
;

-- 2021-06-17T10:25:07.687Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646800,0,543958,545897,586792,'F',TO_TIMESTAMP('2021-06-17 12:25:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnung-Überprüfungslauf',5,0,0,TO_TIMESTAMP('2021-06-17 12:25:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-17T10:27:50.075Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647455,0,544004,545973,586793,'F',TO_TIMESTAMP('2021-06-17 12:27:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Invoice Verification RunLine',5,0,0,TO_TIMESTAMP('2021-06-17 12:27:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-17T10:30:07.994Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungszeile', PrintName='Rechnung-Überprüfungszeile',Updated=TO_TIMESTAMP('2021-06-17 12:30:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579245 AND AD_Language='de_CH'
;

-- 2021-06-17T10:30:07.997Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579245,'de_CH') 
;

-- 2021-06-17T10:30:16.153Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungszeile', PrintName='Rechnung-Überprüfungszeile',Updated=TO_TIMESTAMP('2021-06-17 12:30:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579245 AND AD_Language='de_DE'
;

-- 2021-06-17T10:30:16.155Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579245,'de_DE') 
;

-- 2021-06-17T10:30:16.174Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579245,'de_DE') 
;

-- 2021-06-17T10:30:16.176Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Invoice_Verification_RunLine_ID', Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=579245
;

-- 2021-06-17T10:30:16.178Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Verification_RunLine_ID', Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL, AD_Element_ID=579245 WHERE UPPER(ColumnName)='C_INVOICE_VERIFICATION_RUNLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-17T10:30:16.179Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Verification_RunLine_ID', Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=579245 AND IsCentrallyMaintained='Y'
;

-- 2021-06-17T10:30:16.180Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579245) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579245)
;

-- 2021-06-17T10:30:16.200Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung-Überprüfungszeile', Name='Rechnung-Überprüfungszeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579245)
;

-- 2021-06-17T10:30:16.201Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579245
;

-- 2021-06-17T10:30:16.203Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL WHERE AD_Element_ID = 579245
;

-- 2021-06-17T10:30:16.204Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungszeile', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579245
;

-- 2021-06-17T10:30:20.099Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-17 12:30:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579245 AND AD_Language='en_US'
;

-- 2021-06-17T10:30:20.100Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579245,'en_US') 
;

-- 2021-06-17T10:30:25.935Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungszeile', PrintName='Rechnung-Überprüfungszeile',Updated=TO_TIMESTAMP('2021-06-17 12:30:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579245 AND AD_Language='nl_NL'
;

-- 2021-06-17T10:30:25.936Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579245,'nl_NL') 
;

