-- 2017-06-17T17:05:47.607
-- URL zum Konzept
UPDATE AD_Process SET Description='Cache des Systems leeren ** Schließen Sie alle Fenster, bevor Sie fortsetzen **
Deactivating the AD_Process because it''s potentially dangerous. See metasfresh/metasfresh#1843
', Help='Um die Leistung der Anwendung zu steigern, speichert Compiere wiederholt genutzte Daten zwischen. Dieser Prozeß löscht den dafür genutzten lokalen Cache.
', IsActive='N',Updated=TO_TIMESTAMP('2017-06-17 17:05:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=205
;

-- 2017-06-17T17:05:47.619
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=205
;

-- 2017-06-17T17:05:47.625
-- URL zum Konzept
UPDATE AD_Menu SET Description='Cache des Systems leeren ** Schließen Sie alle Fenster, bevor Sie fortsetzen **
Deactivating the AD_Process because it''s potentially dangerous. See metasfresh/metasfresh#1843
', IsActive='N', Name='Cache leeren',Updated=TO_TIMESTAMP('2017-06-17 17:05:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=383
;

-- 2017-06-17T17:05:47.627
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=383
;

