-- 2022-08-09T13:47:51.662Z
UPDATE AD_Element_Trl SET Name='REST-API Benutzerdefinierte Spalte', PrintName='REST-API Benutzerdefinierte Spalte',Updated=TO_TIMESTAMP('2022-08-09 16:47:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581256 AND AD_Language='de_CH'
;

-- 2022-08-09T13:47:51.688Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581256,'de_CH') 
;

-- 2022-08-09T13:48:01.199Z
UPDATE AD_Element_Trl SET Name='REST-API Benutzerdefinierte Spalte', PrintName='REST-API Benutzerdefinierte Spalte',Updated=TO_TIMESTAMP('2022-08-09 16:48:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581256 AND AD_Language='de_DE'
;

-- 2022-08-09T13:48:01.200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581256,'de_DE') 
;

-- 2022-08-09T13:48:01.216Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581256,'de_DE') 
;

-- 2022-08-09T13:48:01.218Z
UPDATE AD_Column SET ColumnName='IsRestAPICustomColumn', Name='REST-API Benutzerdefinierte Spalte', Description=NULL, Help=NULL WHERE AD_Element_ID=581256
;

-- 2022-08-09T13:48:01.218Z
UPDATE AD_Process_Para SET ColumnName='IsRestAPICustomColumn', Name='REST-API Benutzerdefinierte Spalte', Description=NULL, Help=NULL, AD_Element_ID=581256 WHERE UPPER(ColumnName)='ISRESTAPICUSTOMCOLUMN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-09T13:48:01.219Z
UPDATE AD_Process_Para SET ColumnName='IsRestAPICustomColumn', Name='REST-API Benutzerdefinierte Spalte', Description=NULL, Help=NULL WHERE AD_Element_ID=581256 AND IsCentrallyMaintained='Y'
;

-- 2022-08-09T13:48:01.220Z
UPDATE AD_Field SET Name='REST-API Benutzerdefinierte Spalte', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581256) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581256)
;

-- 2022-08-09T13:48:01.230Z
UPDATE AD_PrintFormatItem pi SET PrintName='REST-API Benutzerdefinierte Spalte', Name='REST-API Benutzerdefinierte Spalte' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581256)
;

-- 2022-08-09T13:48:01.231Z
UPDATE AD_Tab SET Name='REST-API Benutzerdefinierte Spalte', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581256
;

-- 2022-08-09T13:48:01.232Z
UPDATE AD_WINDOW SET Name='REST-API Benutzerdefinierte Spalte', Description=NULL, Help=NULL WHERE AD_Element_ID = 581256
;

-- 2022-08-09T13:48:01.233Z
UPDATE AD_Menu SET   Name = 'REST-API Benutzerdefinierte Spalte', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581256
;

-- 2022-08-09T13:48:08.552Z
UPDATE AD_Element_Trl SET Name='REST-API Benutzerdefinierte Spalte', PrintName='REST-API Benutzerdefinierte Spalte',Updated=TO_TIMESTAMP('2022-08-09 16:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581256 AND AD_Language='nl_NL'
;

-- 2022-08-09T13:48:08.553Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581256,'nl_NL') 
;

-- 2022-08-09T13:48:12.183Z
UPDATE AD_Element_Trl SET Name='REST-API Custom Column', PrintName='REST-API Custom Column',Updated=TO_TIMESTAMP('2022-08-09 16:48:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581256 AND AD_Language='en_US'
;

-- 2022-08-09T13:48:12.185Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581256,'en_US') 
;

-- Field: Tabelle und Spalte -> Spalte -> REST-API Benutzerdefinierte Spalte
-- Column: AD_Column.IsRestAPICustomColumn
-- 2022-08-09T13:49:10.554Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584010,703948,0,101,0,TO_TIMESTAMP('2022-08-09 16:49:10','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','REST-API Benutzerdefinierte Spalte',0,530,0,1,1,TO_TIMESTAMP('2022-08-09 16:49:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T13:49:10.556Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703948 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T13:49:10.558Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581256) 
;

-- 2022-08-09T13:49:10.570Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703948
;

-- 2022-08-09T13:49:10.575Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703948)
;

-- Field: Tabelle und Spalte -> Spalte -> REST-API Benutzerdefinierte Spalte
-- Column: AD_Column.IsRestAPICustomColumn
-- 2022-08-09T13:49:31.197Z
UPDATE AD_Field SET SeqNo=535,Updated=TO_TIMESTAMP('2022-08-09 16:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703948
;

-- 2022-08-09T13:50:31.165Z
UPDATE AD_Element_Trl SET Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in der REST-API-Nutzlast des entsprechenden Modells aktualisieren. (nur wenn die REST-API-Definition diese Funktion unterstützt)',Updated=TO_TIMESTAMP('2022-08-09 16:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581256 AND AD_Language='de_CH'
;

-- 2022-08-09T13:50:31.166Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581256,'de_CH') 
;

-- 2022-08-09T13:50:32.604Z
UPDATE AD_Element_Trl SET Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in der REST-API-Nutzlast des entsprechenden Modells aktualisieren. (nur wenn die REST-API-Definition diese Funktion unterstützt)',Updated=TO_TIMESTAMP('2022-08-09 16:50:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581256 AND AD_Language='de_DE'
;

-- 2022-08-09T13:50:32.606Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581256,'de_DE') 
;

-- 2022-08-09T13:50:32.610Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581256,'de_DE') 
;

-- 2022-08-09T13:50:32.611Z
UPDATE AD_Column SET ColumnName='IsRestAPICustomColumn', Name='REST-API Benutzerdefinierte Spalte', Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in der REST-API-Nutzlast des entsprechenden Modells aktualisieren. (nur wenn die REST-API-Definition diese Funktion unterstützt)', Help=NULL WHERE AD_Element_ID=581256
;

-- 2022-08-09T13:50:32.612Z
UPDATE AD_Process_Para SET ColumnName='IsRestAPICustomColumn', Name='REST-API Benutzerdefinierte Spalte', Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in der REST-API-Nutzlast des entsprechenden Modells aktualisieren. (nur wenn die REST-API-Definition diese Funktion unterstützt)', Help=NULL, AD_Element_ID=581256 WHERE UPPER(ColumnName)='ISRESTAPICUSTOMCOLUMN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-09T13:50:32.613Z
UPDATE AD_Process_Para SET ColumnName='IsRestAPICustomColumn', Name='REST-API Benutzerdefinierte Spalte', Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in der REST-API-Nutzlast des entsprechenden Modells aktualisieren. (nur wenn die REST-API-Definition diese Funktion unterstützt)', Help=NULL WHERE AD_Element_ID=581256 AND IsCentrallyMaintained='Y'
;

-- 2022-08-09T13:50:32.613Z
UPDATE AD_Field SET Name='REST-API Benutzerdefinierte Spalte', Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in der REST-API-Nutzlast des entsprechenden Modells aktualisieren. (nur wenn die REST-API-Definition diese Funktion unterstützt)', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581256) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581256)
;

-- 2022-08-09T13:50:32.623Z
UPDATE AD_Tab SET Name='REST-API Benutzerdefinierte Spalte', Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in der REST-API-Nutzlast des entsprechenden Modells aktualisieren. (nur wenn die REST-API-Definition diese Funktion unterstützt)', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581256
;

-- 2022-08-09T13:50:32.624Z
UPDATE AD_WINDOW SET Name='REST-API Benutzerdefinierte Spalte', Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in der REST-API-Nutzlast des entsprechenden Modells aktualisieren. (nur wenn die REST-API-Definition diese Funktion unterstützt)', Help=NULL WHERE AD_Element_ID = 581256
;

-- 2022-08-09T13:50:32.625Z
UPDATE AD_Menu SET   Name = 'REST-API Benutzerdefinierte Spalte', Description = 'Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in der REST-API-Nutzlast des entsprechenden Modells aktualisieren. (nur wenn die REST-API-Definition diese Funktion unterstützt)', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581256
;

-- 2022-08-09T13:50:39.255Z
UPDATE AD_Element_Trl SET Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in der REST-API-Nutzlast des entsprechenden Modells aktualisieren. (nur wenn die REST-API-Definition diese Funktion unterstützt)',Updated=TO_TIMESTAMP('2022-08-09 16:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581256 AND AD_Language='nl_NL'
;

-- 2022-08-09T13:50:39.257Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581256,'nl_NL') 
;

-- 2022-08-09T13:50:41.141Z
UPDATE AD_Element_Trl SET Description='If true, it allows users to update the column''s value via "extendedProps" field in the corresponding model''s REST API payload. (only if the REST API definition supports the feature)',Updated=TO_TIMESTAMP('2022-08-09 16:50:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581256 AND AD_Language='en_US'
;

-- 2022-08-09T13:50:41.143Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581256,'en_US') 
;

-- 2022-08-10T08:59:43.972Z
UPDATE AD_Message SET MsgText='{0} ist nicht als benutzerdefinierte REST API-Spalte markiert (AD_Column.IsRestAPICustomColumn=''N'')',Updated=TO_TIMESTAMP('2022-08-10 11:59:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545145
;

-- 2022-08-10T09:00:02.510Z
UPDATE AD_Message_Trl SET MsgText='{0} ist nicht als benutzerdefinierte REST API-Spalte markiert (AD_Column.IsRestAPICustomColumn=''N'')',Updated=TO_TIMESTAMP('2022-08-10 12:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545145
;

-- 2022-08-10T09:00:06.887Z
UPDATE AD_Message_Trl SET MsgText='{0} ist nicht als benutzerdefinierte REST API-Spalte markiert (AD_Column.IsRestAPICustomColumn=''N'')',Updated=TO_TIMESTAMP('2022-08-10 12:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545145
;

-- 2022-08-10T09:00:14.877Z
UPDATE AD_Message_Trl SET MsgText='{0} ist nicht als benutzerdefinierte REST API-Spalte markiert (AD_Column.IsRestAPICustomColumn=''N'')',Updated=TO_TIMESTAMP('2022-08-10 12:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545145
;

-- 2022-08-10T09:00:21.044Z
UPDATE AD_Message_Trl SET MsgText='{0} is not flagged as custom REST API column (AD_Column.IsRestAPICustomColumn=''N'')',Updated=TO_TIMESTAMP('2022-08-10 12:00:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545145
;

-- 2022-08-10T09:01:52.797Z
UPDATE AD_Element_Trl SET Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in den REST-API-Aufrufdaten des entsprechenden Modells aktualisieren. (sofern der jeweilige REST-API-Endpunkt diese Funktion unterstützt)',Updated=TO_TIMESTAMP('2022-08-10 12:01:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581256 AND AD_Language='de_CH'
;

-- 2022-08-10T09:01:52.823Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581256,'de_CH')
;

-- 2022-08-10T09:02:00.878Z
UPDATE AD_Element_Trl SET Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in den REST-API-Aufrufdaten des entsprechenden Modells aktualisieren. (sofern der jeweilige REST-API-Endpunkt diese Funktion unterstützt)',Updated=TO_TIMESTAMP('2022-08-10 12:02:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581256 AND AD_Language='de_DE'
;

-- 2022-08-10T09:02:00.879Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581256,'de_DE')
;

-- 2022-08-10T09:02:00.895Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581256,'de_DE')
;

-- 2022-08-10T09:02:00.896Z
UPDATE AD_Column SET ColumnName='IsRestAPICustomColumn', Name='REST-API Benutzerdefinierte Spalte', Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in den REST-API-Aufrufdaten des entsprechenden Modells aktualisieren. (sofern der jeweilige REST-API-Endpunkt diese Funktion unterstützt)', Help=NULL WHERE AD_Element_ID=581256
;

-- 2022-08-10T09:02:00.897Z
UPDATE AD_Process_Para SET ColumnName='IsRestAPICustomColumn', Name='REST-API Benutzerdefinierte Spalte', Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in den REST-API-Aufrufdaten des entsprechenden Modells aktualisieren. (sofern der jeweilige REST-API-Endpunkt diese Funktion unterstützt)', Help=NULL, AD_Element_ID=581256 WHERE UPPER(ColumnName)='ISRESTAPICUSTOMCOLUMN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-10T09:02:00.897Z
UPDATE AD_Process_Para SET ColumnName='IsRestAPICustomColumn', Name='REST-API Benutzerdefinierte Spalte', Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in den REST-API-Aufrufdaten des entsprechenden Modells aktualisieren. (sofern der jeweilige REST-API-Endpunkt diese Funktion unterstützt)', Help=NULL WHERE AD_Element_ID=581256 AND IsCentrallyMaintained='Y'
;

-- 2022-08-10T09:02:00.898Z
UPDATE AD_Field SET Name='REST-API Benutzerdefinierte Spalte', Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in den REST-API-Aufrufdaten des entsprechenden Modells aktualisieren. (sofern der jeweilige REST-API-Endpunkt diese Funktion unterstützt)', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581256) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581256)
;

-- 2022-08-10T09:02:00.913Z
UPDATE AD_Tab SET Name='REST-API Benutzerdefinierte Spalte', Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in den REST-API-Aufrufdaten des entsprechenden Modells aktualisieren. (sofern der jeweilige REST-API-Endpunkt diese Funktion unterstützt)', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581256
;

-- 2022-08-10T09:02:00.914Z
UPDATE AD_WINDOW SET Name='REST-API Benutzerdefinierte Spalte', Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in den REST-API-Aufrufdaten des entsprechenden Modells aktualisieren. (sofern der jeweilige REST-API-Endpunkt diese Funktion unterstützt)', Help=NULL WHERE AD_Element_ID = 581256
;

-- 2022-08-10T09:02:00.915Z
UPDATE AD_Menu SET   Name = 'REST-API Benutzerdefinierte Spalte', Description = 'Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in den REST-API-Aufrufdaten des entsprechenden Modells aktualisieren. (sofern der jeweilige REST-API-Endpunkt diese Funktion unterstützt)', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581256
;

-- 2022-08-10T09:02:03.100Z
UPDATE AD_Element_Trl SET Description='Bei "true" können Benutzer den Wert der Spalte über das Feld "extendedProps" in den REST-API-Aufrufdaten des entsprechenden Modells aktualisieren. (sofern der jeweilige REST-API-Endpunkt diese Funktion unterstützt)',Updated=TO_TIMESTAMP('2022-08-10 12:02:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581256 AND AD_Language='nl_NL'
;

-- 2022-08-10T09:02:03.101Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581256,'nl_NL')
;

-- 2022-08-10T09:02:20.230Z
UPDATE AD_Element_Trl SET Description='If true, it allows users to update the column''s value via "extendedProps" field in the corresponding model''s REST API payload. (only for REST API endpoints which support the feature)',Updated=TO_TIMESTAMP('2022-08-10 12:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581256 AND AD_Language='en_US'
;

-- 2022-08-10T09:02:20.231Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581256,'en_US')
;


