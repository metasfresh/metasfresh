
-- 09.11.2015 07:54
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2015-11-09 07:54:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540722
;

-- also update the help text a bit
-- 09.11.2015 08:33
-- URL zum Konzept
UPDATE AD_Process SET Help='Achtung: <ul>
<li>Der Prozess versucht, eventuell vorhandene Rechnungskandidaten zu löschen und schlägt fehl, wenn das misslingt.</li>
<li>Der Prozess verhindert nicht die Zuordnung von Material-Vorgängen fremder Geschäftspartner.</li>
</ul>',Updated=TO_TIMESTAMP('2015-11-09 08:33:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540589
;

-- 09.11.2015 08:33
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540589
;

-- 09.11.2015 08:34
-- URL zum Konzept
UPDATE AD_Process SET Help='Achtung: Der Prozess<ul>
<li>versucht, eventuell vorhandene Rechnungskandidaten zu löschen und schlägt fehl, wenn das misslingt.</li>
<li>verhindert nicht die Zuordnung von Material-Vorgängen fremder Geschäftspartner.</li>
</ul>',Updated=TO_TIMESTAMP('2015-11-09 08:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540589
;

-- 09.11.2015 08:34
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540589
;

