-- 11.01.2017 12:24
-- URL zum Konzept
UPDATE AD_Process SET EntityType='de.metas.invoice',Updated=TO_TIMESTAMP('2017-01-11 12:24:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540405
;

-- 11.01.2017 12:25
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=196, ColumnName='C_DocType_ID', Description='Belegart oder Verarbeitungsvorgaben', EntityType='de.metas.invoice', Help='Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.', Name='Belegart',Updated=TO_TIMESTAMP('2017-01-11 12:25:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540501
;

-- 11.01.2017 12:25
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=540501
;

