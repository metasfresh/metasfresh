-- 2021-12-16T12:22:04.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Synchrone revision', PrintName='Synchrone revision',Updated=TO_TIMESTAMP('2021-12-16 14:22:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='de_CH'
;

-- 2021-12-16T12:22:04.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'de_CH') 
;

-- 2021-12-16T12:22:07.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Synchrone revision', PrintName='Synchrone revision',Updated=TO_TIMESTAMP('2021-12-16 14:22:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='de_DE'
;

-- 2021-12-16T12:22:07.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'de_DE') 
;

-- 2021-12-16T12:22:07.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580370,'de_DE') 
;

-- 2021-12-16T12:22:07.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Synchrone revision', Description=NULL, Help=NULL WHERE AD_Element_ID=580370
;

-- 2021-12-16T12:22:07.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Synchrone revision', Description=NULL, Help=NULL, AD_Element_ID=580370 WHERE UPPER(ColumnName)='ISSYNCHRONOUSAUDITLOGGINGENABLED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-16T12:22:07.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Synchrone revision', Description=NULL, Help=NULL WHERE AD_Element_ID=580370 AND IsCentrallyMaintained='Y'
;

-- 2021-12-16T12:22:07.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Synchrone revision', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580370) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580370)
;

-- 2021-12-16T12:22:07.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Synchrone revision', Name='Synchrone revision' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580370)
;

-- 2021-12-16T12:22:07.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Synchrone revision', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580370
;

-- 2021-12-16T12:22:07.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Synchrone revision', Description=NULL, Help=NULL WHERE AD_Element_ID = 580370
;

-- 2021-12-16T12:22:07.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Synchrone revision', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580370
;

-- 2021-12-16T12:22:16.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Synchronous audit', PrintName='Synchronous audit',Updated=TO_TIMESTAMP('2021-12-16 14:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='en_US'
;

-- 2021-12-16T12:22:16.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'en_US') 
;

-- 2021-12-16T12:22:22.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Synchrone revision', PrintName='Synchrone revision',Updated=TO_TIMESTAMP('2021-12-16 14:22:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='nl_NL'
;

-- 2021-12-16T12:22:22.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'nl_NL') 
;

-- 2021-12-16T12:31:52.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.',Updated=TO_TIMESTAMP('2021-12-16 14:31:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='de_CH'
;

-- 2021-12-16T12:31:52.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'de_CH') 
;

-- 2021-12-16T12:31:58.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.',Updated=TO_TIMESTAMP('2021-12-16 14:31:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='de_DE'
;

-- 2021-12-16T12:31:58.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'de_DE') 
;

-- 2021-12-16T12:31:58.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580370,'de_DE') 
;

-- 2021-12-16T12:31:58.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Synchrone revision', Description=NULL, Help='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.' WHERE AD_Element_ID=580370
;

-- 2021-12-16T12:31:58.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Synchrone revision', Description=NULL, Help='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.', AD_Element_ID=580370 WHERE UPPER(ColumnName)='ISSYNCHRONOUSAUDITLOGGINGENABLED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-16T12:31:58.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Synchrone revision', Description=NULL, Help='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.' WHERE AD_Element_ID=580370 AND IsCentrallyMaintained='Y'
;

-- 2021-12-16T12:31:58.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Synchrone revision', Description=NULL, Help='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580370) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580370)
;

-- 2021-12-16T12:31:58.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Synchrone revision', Description=NULL, Help='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.', CommitWarning = NULL WHERE AD_Element_ID = 580370
;

-- 2021-12-16T12:31:58.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Synchrone revision', Description=NULL, Help='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.' WHERE AD_Element_ID = 580370
;

-- 2021-12-16T12:31:58.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Synchrone revision', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580370
;

-- 2021-12-16T12:32:01.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.',Updated=TO_TIMESTAMP('2021-12-16 14:32:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='nl_NL'
;

-- 2021-12-16T12:32:01.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'nl_NL') 
;

-- 2021-12-16T12:32:09.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='If false, then API Request and API Response records are written for the request, but in an asynchronous way, while the actual HTTP request might have already been performed. This implies better performance for the caller, but, no API Audit Log records will be created. Also note, that creating those audit records might fail without the API caller noticing it.',Updated=TO_TIMESTAMP('2021-12-16 14:32:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='en_US'
;

-- 2021-12-16T12:32:09.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'en_US') 
;

-- 2021-12-16T12:34:23.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', Name='Antwort verpacken', PrintName='Antwort verpacken',Updated=TO_TIMESTAMP('2021-12-16 14:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580371 AND AD_Language='de_CH'
;

-- 2021-12-16T12:34:23.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580371,'de_CH') 
;

-- 2021-12-16T12:34:34.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', Name='Antwort verpacken', PrintName='Antwort verpacken',Updated=TO_TIMESTAMP('2021-12-16 14:34:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580371 AND AD_Language='de_DE'
;

-- 2021-12-16T12:34:34.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580371,'de_DE') 
;

-- 2021-12-16T12:34:34.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580371,'de_DE') 
;

-- 2021-12-16T12:34:34.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsWrapApiResponse', Name='Antwort verpacken', Description=NULL, Help='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.' WHERE AD_Element_ID=580371
;

-- 2021-12-16T12:34:34.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsWrapApiResponse', Name='Antwort verpacken', Description=NULL, Help='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', AD_Element_ID=580371 WHERE UPPER(ColumnName)='ISWRAPAPIRESPONSE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-16T12:34:34.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsWrapApiResponse', Name='Antwort verpacken', Description=NULL, Help='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.' WHERE AD_Element_ID=580371 AND IsCentrallyMaintained='Y'
;

-- 2021-12-16T12:34:34.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Antwort verpacken', Description=NULL, Help='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580371) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580371)
;

-- 2021-12-16T12:34:34.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Antwort verpacken', Name='Antwort verpacken' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580371)
;

-- 2021-12-16T12:34:34.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Antwort verpacken', Description=NULL, Help='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', CommitWarning = NULL WHERE AD_Element_ID = 580371
;

-- 2021-12-16T12:34:34.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Antwort verpacken', Description=NULL, Help='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.' WHERE AD_Element_ID = 580371
;

-- 2021-12-16T12:34:34.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Antwort verpacken', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580371
;

-- 2021-12-16T12:34:48.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='If true, the actual API response will be wrapped into a standard V2 response JSON structure carrying the API Request Audit identifier. If false, the actual API response is returned "as is" and the API Request Audit identifier is reported in the response header XXXX.',Updated=TO_TIMESTAMP('2021-12-16 14:34:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580371 AND AD_Language='en_US'
;

-- 2021-12-16T12:34:48.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580371,'en_US') 
;

-- 2021-12-16T12:35:01.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', Name='Antwort verpacken', PrintName='Antwort verpacken',Updated=TO_TIMESTAMP('2021-12-16 14:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580371 AND AD_Language='nl_NL'
;

-- 2021-12-16T12:35:01.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580371,'nl_NL') 
;

-- 2021-12-16T12:36:51.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Name='Pfad', PrintName='Pfad',Updated=TO_TIMESTAMP('2021-12-16 14:36:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579103 AND AD_Language='de_CH'
;

-- 2021-12-16T12:36:51.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579103,'de_CH') 
;

-- 2021-12-16T12:36:55.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"',Updated=TO_TIMESTAMP('2021-12-16 14:36:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579103 AND AD_Language='de_DE'
;

-- 2021-12-16T12:36:55.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579103,'de_DE') 
;

-- 2021-12-16T12:36:56Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579103,'de_DE') 
;

-- 2021-12-16T12:36:56.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PathPrefix', Name='Pfad-Prefix', Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Help=NULL WHERE AD_Element_ID=579103
;

-- 2021-12-16T12:36:56.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PathPrefix', Name='Pfad-Prefix', Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Help=NULL, AD_Element_ID=579103 WHERE UPPER(ColumnName)='PATHPREFIX' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-16T12:36:56.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PathPrefix', Name='Pfad-Prefix', Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Help=NULL WHERE AD_Element_ID=579103 AND IsCentrallyMaintained='Y'
;

-- 2021-12-16T12:36:56.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Pfad-Prefix', Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579103) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579103)
;

-- 2021-12-16T12:36:56.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Pfad-Prefix', Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579103
;

-- 2021-12-16T12:36:56.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Pfad-Prefix', Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Help=NULL WHERE AD_Element_ID = 579103
;

-- 2021-12-16T12:36:56.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Pfad-Prefix', Description = 'Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579103
;

-- 2021-12-16T12:36:58.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"',Updated=TO_TIMESTAMP('2021-12-16 14:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579103 AND AD_Language='nl_NL'
;

-- 2021-12-16T12:36:58.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579103,'nl_NL') 
;

-- 2021-12-16T12:37:23.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Pfad', PrintName='Pfad',Updated=TO_TIMESTAMP('2021-12-16 14:37:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579103 AND AD_Language='de_DE'
;

-- 2021-12-16T12:37:23.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579103,'de_DE') 
;

-- 2021-12-16T12:37:23.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579103,'de_DE') 
;

-- 2021-12-16T12:37:23.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PathPrefix', Name='Pfad', Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Help=NULL WHERE AD_Element_ID=579103
;

-- 2021-12-16T12:37:23.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PathPrefix', Name='Pfad', Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Help=NULL, AD_Element_ID=579103 WHERE UPPER(ColumnName)='PATHPREFIX' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-16T12:37:23.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PathPrefix', Name='Pfad', Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Help=NULL WHERE AD_Element_ID=579103 AND IsCentrallyMaintained='Y'
;

-- 2021-12-16T12:37:23.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Pfad', Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579103) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579103)
;

-- 2021-12-16T12:37:23.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Pfad', Name='Pfad' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579103)
;

-- 2021-12-16T12:37:23.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Pfad', Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579103
;

-- 2021-12-16T12:37:23.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Pfad', Description='Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', Help=NULL WHERE AD_Element_ID = 579103
;

-- 2021-12-16T12:37:23.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Pfad', Description = 'Muster der Anfrage-URL, die von dieser Konfiguration abgeglichen werden soll. Kann ein beliebiger Teil der eigentlichen URL, oder ein Pfadmuster im Stil von Ant sein, siehe "spring AntPathMatcher"', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579103
;

-- 2021-12-16T12:37:29.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Pfad', PrintName='Pfad',Updated=TO_TIMESTAMP('2021-12-16 14:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579103 AND AD_Language='nl_NL'
;

-- 2021-12-16T12:37:29.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579103,'nl_NL') 
;

-- 2021-12-16T12:37:55.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Pattern of the request URL to be matched by this config. It can be any part of the actual URL, or it can be an Ant-style path pattern, see "spring AntPathMatcher"', Name='Path', PrintName='Path',Updated=TO_TIMESTAMP('2021-12-16 14:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579103 AND AD_Language='en_US'
;

-- 2021-12-16T12:37:55.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579103,'en_US') 
;

-- 2021-12-16T12:55:20.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2021-12-16 14:55:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2647
;

-- 2021-12-16T12:55:23.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_element_trl','Description','VARCHAR(2000)',null,null)
;

-- 2021-12-16T13:24:10.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.', Help='',Updated=TO_TIMESTAMP('2021-12-16 15:24:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='de_CH'
;

-- 2021-12-16T13:24:10.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'de_CH') 
;

-- 2021-12-16T13:26:00.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2021-12-16 15:26:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2823
;

-- 2021-12-16T13:26:22.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2021-12-16 15:26:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2841
;

-- 2021-12-16T13:27:00.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_process_para_trl','Description','VARCHAR(2000)',null,null)
;

-- 2021-12-16T13:27:20.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_process_para','Description','VARCHAR(2000)',null,null)
;

-- 2021-12-16T13:28:43.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2021-12-16 15:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=162
;

-- 2021-12-16T13:28:45.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_tab','Description','VARCHAR(2000)',null,null)
;

-- 2021-12-16T13:29:49.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2021-12-16 15:29:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=267
;

-- 2021-12-16T13:29:50.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_tab_trl','Description','VARCHAR(2000)',null,null)
;

-- 2021-12-16T13:30:09.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2021-12-16 15:30:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=157
;

-- 2021-12-16T13:30:11.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_window','Description','VARCHAR(2000)',null,null)
;

-- 2021-12-16T13:30:33.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2021-12-16 15:30:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=308
;

-- 2021-12-16T13:30:34.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_window_trl','Description','VARCHAR(2000)',null,null)
;

-- 2021-12-16T13:31:26.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2021-12-16 15:31:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=230
;

-- 2021-12-16T13:31:28.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_menu','Description','VARCHAR(2000)',null,null)
;

-- 2021-12-16T13:31:47.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2021-12-16 15:31:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=248
;

-- 2021-12-16T13:31:49.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_menu_trl','Description','VARCHAR(2000)',null,null)
;

-- 2021-12-16T13:32:05.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.', Help='',Updated=TO_TIMESTAMP('2021-12-16 15:32:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='de_DE'
;

-- 2021-12-16T13:32:05.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'de_DE') 
;

-- 2021-12-16T13:32:05.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580370,'de_DE') 
;

-- 2021-12-16T13:32:05.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Synchrone revision', Description='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.', Help='' WHERE AD_Element_ID=580370
;

-- 2021-12-16T13:32:05.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Synchrone revision', Description='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.', Help='', AD_Element_ID=580370 WHERE UPPER(ColumnName)='ISSYNCHRONOUSAUDITLOGGINGENABLED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-16T13:32:05.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Synchrone revision', Description='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.', Help='' WHERE AD_Element_ID=580370 AND IsCentrallyMaintained='Y'
;

-- 2021-12-16T13:32:05.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Synchrone revision', Description='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580370) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580370)
;

-- 2021-12-16T13:32:05.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Synchrone revision', Description='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 580370
;

-- 2021-12-16T13:32:05.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Synchrone revision', Description='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.', Help='' WHERE AD_Element_ID = 580370
;

-- 2021-12-16T13:32:05.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Synchrone revision', Description = 'Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580370
;

-- 2021-12-16T13:32:16.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If false, then API Request and API Response records are written for the request, but in an asynchronous way, while the actual HTTP request might have already been performed. This implies better performance for the caller, but, no API Audit Log records will be created. Also note, that creating those audit records might fail without the API caller noticing it.', Help='',Updated=TO_TIMESTAMP('2021-12-16 15:32:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='en_US'
;

-- 2021-12-16T13:32:16.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'en_US') 
;

-- 2021-12-16T13:32:20.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei "Nein" werden API Aufruf Revision und API Antwort Revision für die Anforderung geschrieben, allerdings auf asynchrone Weise, während die eigentliche HTTP-Anforderung möglicherweise bereits ausgeführt wurde. Dies bedeutet eine bessere Leistung für den Aufrufer, aber es werden keine durch den Aufruf zugegriffenen Datensätze geloggt. Außerdem kann die Erstellung dieser Audit-Datensätze fehlschlagen, ohne dass der API-Aufrufer dies merkt.', Help='',Updated=TO_TIMESTAMP('2021-12-16 15:32:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='nl_NL'
;

-- 2021-12-16T13:32:20.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'nl_NL') 
;

-- 2021-12-16T13:32:56.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', Help='',Updated=TO_TIMESTAMP('2021-12-16 15:32:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580371 AND AD_Language='de_CH'
;

-- 2021-12-16T13:32:56.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580371,'de_CH') 
;

-- 2021-12-16T13:33:00.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', Help='',Updated=TO_TIMESTAMP('2021-12-16 15:33:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580371 AND AD_Language='de_DE'
;

-- 2021-12-16T13:33:00.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580371,'de_DE') 
;

-- 2021-12-16T13:33:00.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580371,'de_DE') 
;

-- 2021-12-16T13:33:00.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsWrapApiResponse', Name='Antwort verpacken', Description='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', Help='' WHERE AD_Element_ID=580371
;

-- 2021-12-16T13:33:00.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsWrapApiResponse', Name='Antwort verpacken', Description='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', Help='', AD_Element_ID=580371 WHERE UPPER(ColumnName)='ISWRAPAPIRESPONSE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-16T13:33:00.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsWrapApiResponse', Name='Antwort verpacken', Description='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', Help='' WHERE AD_Element_ID=580371 AND IsCentrallyMaintained='Y'
;

-- 2021-12-16T13:33:00.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Antwort verpacken', Description='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580371) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580371)
;

-- 2021-12-16T13:33:00.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Antwort verpacken', Description='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 580371
;

-- 2021-12-16T13:33:00.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Antwort verpacken', Description='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', Help='' WHERE AD_Element_ID = 580371
;

-- 2021-12-16T13:33:00.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Antwort verpacken', Description = 'Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580371
;

-- 2021-12-16T13:33:06.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If true, the actual API response will be wrapped into a standard V2 response JSON structure carrying the API Request Audit identifier. If false, the actual API response is returned "as is" and the API Request Audit identifier is reported in the response header XXXX.', Help='',Updated=TO_TIMESTAMP('2021-12-16 15:33:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580371 AND AD_Language='en_US'
;

-- 2021-12-16T13:33:06.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580371,'en_US') 
;

-- 2021-12-16T13:33:10.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei "true" wird die eigentliche API-Antwort in eine JSON-Struktur der Standard-V2-Antwort mit der Kennung für die API Aufruf Revision verpackt. Bei "false" wird die eigentliche API-Antwort so zurückgegeben, wie sie ist, und die ID der API Aufruf Revision wird im Antwort-Header XXXX zurückgegeben.', Help='',Updated=TO_TIMESTAMP('2021-12-16 15:33:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580371 AND AD_Language='nl_NL'
;

-- 2021-12-16T13:33:10.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580371,'nl_NL') 
;