-- Customer retention (C_Customer_Retention.CustomerRetention, AD_Element 575933, field in window Geschäftspartner):
-- the description and help say which contracts it is determined from.
-- de_DE/de_CH/en_US only; other languages (fr_*) are left untouched.

UPDATE AD_Element_Trl SET Description='Kundenbindung (Neukunde/Stammkunde), ermittelt nur aus Abo-Verträgen.', Help='Wird aus den Abo-Verträgen des Geschäftspartners und deren Rechnungen ermittelt. Andere Vertragsarten, z.B. Kompensationsgruppen-Verträge, zählen nicht.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-09 21:00:00.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=575933 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET Description='Kundenbindung (Neukunde/Stammkunde), ermittelt nur aus Abo-Verträgen.', Help='Wird aus den Abo-Verträgen des Geschäftspartners und deren Rechnungen ermittelt. Andere Vertragsarten, z.B. Kompensationsgruppen-Verträge, zählen nicht.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-09 21:00:01.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=575933 AND AD_Language='de_CH'
;

UPDATE AD_Element_Trl SET Description='Customer retention (new/regular customer), determined from subscription contracts only.', Help='Determined from the business partner''s subscription contracts and their invoices. Other contract types, e.g. compensation-group contracts, do not count.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-09 21:00:02.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=575933 AND AD_Language='en_US'
;

/* de_DE */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(575933, 'de_DE')
;

/* de_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(575933, 'de_CH')
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(575933, 'en_US')
;
