-- Customer retention (C_Customer_Retention.CustomerRetention, AD_Element 575933, field in window Geschäftspartner):
-- the description and help say that it is determined from subscription contracts only.
-- de_DE/de_CH/fr_CH get the German text (IsTranslated='N'), en_US the English text (IsTranslated='Y').

UPDATE AD_Element_Trl SET Description='Kundenbindung (Neukunde/Stammkunde), ermittelt nur aus Abo-Verträgen.', Help='Wird aus den Abo-Verträgen des Geschäftspartners und deren Rechnungen ermittelt. Andere Vertragsarten, z.B. Kompensationsgruppen-Verträge, zählen nicht.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-10-09 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=575933 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET Description='Kundenbindung (Neukunde/Stammkunde), ermittelt nur aus Abo-Verträgen.', Help='Wird aus den Abo-Verträgen des Geschäftspartners und deren Rechnungen ermittelt. Andere Vertragsarten, z.B. Kompensationsgruppen-Verträge, zählen nicht.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-10-09 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=575933 AND AD_Language='de_CH'
;

UPDATE AD_Element_Trl SET Description='Kundenbindung (Neukunde/Stammkunde), ermittelt nur aus Abo-Verträgen.', Help='Wird aus den Abo-Verträgen des Geschäftspartners und deren Rechnungen ermittelt. Andere Vertragsarten, z.B. Kompensationsgruppen-Verträge, zählen nicht.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-10-09 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=575933 AND AD_Language='fr_CH'
;

UPDATE AD_Element_Trl SET Description='Customer retention (new/regular customer), determined from subscription contracts only.', Help='Determined from the business partner''s subscription contracts and their invoices. Other contract types, e.g. compensation-group contracts, do not count.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-09 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=575933 AND AD_Language='en_US'
;

/* de_DE */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(575933, 'de_DE')
;

/* de_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(575933, 'de_CH')
;

/* fr_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(575933, 'fr_CH')
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(575933, 'en_US')
;
