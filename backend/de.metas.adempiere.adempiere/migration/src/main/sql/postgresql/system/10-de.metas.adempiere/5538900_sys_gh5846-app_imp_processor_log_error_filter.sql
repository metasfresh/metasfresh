-- 2019-12-13T10:59:45.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UserQuery (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_UserQuery_ID,Code,Created,CreatedBy,IsActive,IsManadatoryParams,IsShowAllParams,Name,Updated,UpdatedBy) VALUES (0,0,542068,53079,540058,'AND<^>IsError<^>=<^>Y<^>',TO_TIMESTAMP('2019-12-13 11:59:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Logeinträge mit Fehler',TO_TIMESTAMP('2019-12-13 11:59:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-13T11:04:15.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UserQuery SET Name='Einträge mit Fehler',Updated=TO_TIMESTAMP('2019-12-13 12:04:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UserQuery_ID=540058
;

