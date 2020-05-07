
--
-- move and rename Applysubscriptions
--
-- 03.03.2016 10:52
-- URL zum Konzept
UPDATE AD_Process SET AccessLevel='3', Classname='de.metas.contracts.subscription.process.C_SubscriptionProgress_Evaluate', Description='Überprüft laufende Abos und erstellt bei bedarf neue Abo-Verlaufs- und Lieferdispo-Datensätze', Help=NULL, Name='Abolieferplan und Lieferdispo aktualisieren', Value='C_SubscriptionProgress_Evaluate',Updated=TO_TIMESTAMP('2016-03-03 10:52:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540003
;

-- 03.03.2016 10:52
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540003
;

-- 03.03.2016 10:52
-- URL zum Konzept
UPDATE AD_Menu SET Description='Überprüft laufende Abos und erstellt bei bedarf neue Abo-Verlaufs- und Lieferdispo-Datensätze', IsActive='Y', Name='Abolieferplan und Lieferdispo aktualisieren',Updated=TO_TIMESTAMP('2016-03-03 10:52:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540015
;

-- 03.03.2016 10:52
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540015
;


-- 03.03.2016 11:11
-- URL zum Konzept
UPDATE AD_Process SET EntityType='de.metas.contracts.subscription',Updated=TO_TIMESTAMP('2016-03-03 11:11:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540003
;
-- delete ancient inactive parameter
-- 03.03.2016 11:12
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=540072
;

-- 03.03.2016 11:12
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=540072
;
