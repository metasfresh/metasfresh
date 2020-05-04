-- 02.01.2017 17:25
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=17, AD_Reference_Value_ID=540528, DefaultValue='', Description='Legt fest, ob das System die automatisch neu erzeugte Vertragsperiode sofort fertigstellen oder den Betreuer informieren soll.', Help='Falls Leer wird die Einstellung aus der Vertragsbedingung (bzw. Laufzeit) angewendet.
Wenn die neue Vertragperiode nicht automatisch fertig gestellt wird, hat der Betruer noch die Möglichkeit, z.B. die geplante Mege pro Maßeinheit anzupassen.', IsMandatory='N',Updated=TO_TIMESTAMP('2017-01-02 17:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540292
;

-- 02.01.2017 17:25
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=540292
;

