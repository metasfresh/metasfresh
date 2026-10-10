-- Customer retention (C_Customer_Retention.CustomerRetention, AD_Element 575933; field 570757 and tab 541397 in window Geschäftspartner):
-- German name 'Kundenbindung' (de_DE/de_CH), and description/help saying which contracts it is determined from.
-- de_DE/de_CH/en_US only; other languages (fr_*) are left untouched. en_US keeps its name 'Customer Retention'.

UPDATE AD_Element_Trl SET Name='Kundenbindung', PrintName='Kundenbindung', Description='Wird aus Abo- und Rückvergütungsverträgen ermittelt; Kompensationsgruppen-Verträge zählen nicht.', Help='Neukunde oder Stammkunde. Wird aus Abo- und Rückvergütungsverträgen ermittelt; Kompensationsgruppen-Verträge zählen nicht.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-10 09:10:00.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=575933 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET Name='Kundenbindung', PrintName='Kundenbindung', Description='Wird aus Abo- und Rückvergütungsverträgen ermittelt; Kompensationsgruppen-Verträge zählen nicht.', Help='Neukunde oder Stammkunde. Wird aus Abo- und Rückvergütungsverträgen ermittelt; Kompensationsgruppen-Verträge zählen nicht.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-10 09:10:01.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=575933 AND AD_Language='de_CH'
;

UPDATE AD_Element_Trl SET Description='Determined from subscription and refund contracts. Compensation-group contracts do not count.', Help='New customer or regular customer. Determined from subscription and refund contracts. Compensation-group contracts do not count.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-10 09:10:02.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=575933 AND AD_Language='en_US'
;

/* de_DE */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(575933, 'de_DE')
;

/* de_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(575933, 'de_CH')
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(575933, 'en_US')
;
