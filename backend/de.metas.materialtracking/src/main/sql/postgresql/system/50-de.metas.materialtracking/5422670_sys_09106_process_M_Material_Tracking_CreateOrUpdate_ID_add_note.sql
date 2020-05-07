

-- 21.07.2015 07:57
-- URL zum Konzept
UPDATE AD_Process SET Description='Aktualisiert die Material-Vorgangs-ID der betreffenden Bestellzeile, zugehöriger Wareneingangszeilen und HUs.', Help='Achtung: <ul>
<li>Der Prozess versucht, eventuell vorhandene Rechnungskandidaten zu löschen und schlägt fehl, wenn das misslingt.</li>
<li>Der Prozess nimmt *keinen* Einfluss auf eventuell schon vorhandenene Waschproben oder Produktionsaufträge.</li>
<li>Der Prozess verhindert nicht die Zuordnung von Material-Vorgängen fremder Geschäftspartner.</li>
</ul>',Updated=TO_TIMESTAMP('2015-07-21 07:57:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540589
;

-- 21.07.2015 07:57
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540589
;
