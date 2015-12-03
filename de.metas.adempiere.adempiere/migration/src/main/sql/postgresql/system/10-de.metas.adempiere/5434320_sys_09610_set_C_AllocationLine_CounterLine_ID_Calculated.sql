-- 01.12.2015 10:30
-- URL zum Konzept
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2015-12-01 10:30:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552813
;

-- ..and also fix an annoining syntax error in a display-rule, and transalate Counter_AllocationLine_ID
-- 01.12.2015 15:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_Invoice_ID/0@=0',Updated=TO_TIMESTAMP('2015-12-01 15:53:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556329
;

-- 01.12.2015 15:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@ReversalLine_ID/0@>0',Updated=TO_TIMESTAMP('2015-12-01 15:53:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=62723
;

-- 01.12.2015 15:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Description='Das Feld wird verwendet, wenn Rechnung gegen Rechnung oder Zahlung gegen Zahlung alloziert wird und referenziert die Zuordnungszeile des jeweiligen Pendants.', Name='Partner-Zeile',Updated=TO_TIMESTAMP('2015-12-01 15:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552813
;

-- 01.12.2015 15:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET IsTranslated='N' WHERE AD_Column_ID=552813
;

-- 01.12.2015 15:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Partner-Zeile', Description='Das Feld wird verwendet, wenn Rechnung gegen Rechnung oder Zahlung gegen Zahlung alloziert wird und referenziert die Zuordnungszeile des jeweiligen Pendants.', Help=NULL WHERE AD_Column_ID=552813 AND IsCentrallyMaintained='Y'
;

