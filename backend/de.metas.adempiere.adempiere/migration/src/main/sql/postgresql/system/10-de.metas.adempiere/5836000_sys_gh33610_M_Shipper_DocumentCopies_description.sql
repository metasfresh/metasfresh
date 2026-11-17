-- Run mode: SWING_CLIENT

-- Base records (AD_Field, AD_Element) get the base language's text (German for de_*, else English).
-- Lieferweg field M_Shipper.DocumentCopies (AD_Field 785688, name element 585544):
-- the copy count now also drives the Bestellkontrolle Büro (order checkup, Plant), not only the delivery note.
-- Text only; name unchanged. Same structure as 5829670_sys_gh32661_M_Shipper_DocumentCopies_field_description.sql.

-- Field gets the EARLIER timestamp, element + element translations the LATER ones (propagation guard f.updated <> e_trl.updated)
-- 2026-11-17T12:00:00.000Z
UPDATE AD_Field
SET Description=CASE WHEN getBaseLanguage() LIKE 'de%' THEN 'Anzahl der Kopien von Lieferschein und Bestellkontrolle Büro bei diesem Lieferweg. Leer = Anzahl wie ohne Lieferweg-Angabe; 0 = kein automatischer Druck.' ELSE 'Number of copies of the delivery note and the Bestellkontrolle Büro for this shipping method. Empty = copies as without a shipping-method count; 0 = no automatic print.' END,
    Help=CASE WHEN getBaseLanguage() LIKE 'de%' THEN 'Anzahl der Kopien von Lieferschein und Bestellkontrolle Büro bei diesem Lieferweg. Leer = Anzahl wie ohne Lieferweg-Angabe; 0 = kein automatischer Druck.' ELSE 'Number of copies of the delivery note and the Bestellkontrolle Büro for this shipping method. Empty = copies as without a shipping-method count; 0 = no automatic print.' END,
    Updated=TO_TIMESTAMP('2026-11-17 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=785688
;

-- Element (base record, base-language text)
-- 2026-11-17T12:00:10.000Z
UPDATE AD_Element
SET Description=CASE WHEN getBaseLanguage() LIKE 'de%' THEN 'Anzahl der Kopien von Lieferschein und Bestellkontrolle Büro bei diesem Lieferweg. Leer = Anzahl wie ohne Lieferweg-Angabe; 0 = kein automatischer Druck.' ELSE 'Number of copies of the delivery note and the Bestellkontrolle Büro for this shipping method. Empty = copies as without a shipping-method count; 0 = no automatic print.' END,
    Help=CASE WHEN getBaseLanguage() LIKE 'de%' THEN 'Anzahl der Kopien von Lieferschein und Bestellkontrolle Büro bei diesem Lieferweg. Leer = Anzahl wie ohne Lieferweg-Angabe; 0 = kein automatischer Druck.' ELSE 'Number of copies of the delivery note and the Bestellkontrolle Büro for this shipping method. Empty = copies as without a shipping-method count; 0 = no automatic print.' END,
    Updated=TO_TIMESTAMP('2026-11-17 12:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585544
;

-- Element: de_CH
-- 2026-11-17T12:00:11.000Z
UPDATE AD_Element_Trl
SET Description='Anzahl der Kopien von Lieferschein und Bestellkontrolle Büro bei diesem Lieferweg. Leer = Anzahl wie ohne Lieferweg-Angabe; 0 = kein automatischer Druck.',
    Help='Anzahl der Kopien von Lieferschein und Bestellkontrolle Büro bei diesem Lieferweg. Leer = Anzahl wie ohne Lieferweg-Angabe; 0 = kein automatischer Druck.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-17 12:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585544 AND AD_Language='de_CH'
;

-- 2026-11-17T12:00:11.010Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(585544,'de_CH')
;

-- 2026-11-17T12:00:11.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585544,'de_CH')
;

-- Element: de_DE
-- 2026-11-17T12:00:12.000Z
UPDATE AD_Element_Trl
SET Description='Anzahl der Kopien von Lieferschein und Bestellkontrolle Büro bei diesem Lieferweg. Leer = Anzahl wie ohne Lieferweg-Angabe; 0 = kein automatischer Druck.',
    Help='Anzahl der Kopien von Lieferschein und Bestellkontrolle Büro bei diesem Lieferweg. Leer = Anzahl wie ohne Lieferweg-Angabe; 0 = kein automatischer Druck.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-17 12:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585544 AND AD_Language='de_DE'
;

-- 2026-11-17T12:00:12.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585544,'de_DE')
;

-- Element: every other language row that was never translated (e.g. fr_CH) carries a copy of the German text -> new German text
-- 2026-11-17T12:00:12.500Z
UPDATE AD_Element_Trl
SET Description='Anzahl der Kopien von Lieferschein und Bestellkontrolle Büro bei diesem Lieferweg. Leer = Anzahl wie ohne Lieferweg-Angabe; 0 = kein automatischer Druck.',
    Help='Anzahl der Kopien von Lieferschein und Bestellkontrolle Büro bei diesem Lieferweg. Leer = Anzahl wie ohne Lieferweg-Angabe; 0 = kein automatischer Druck.',
    Updated=TO_TIMESTAMP('2026-11-17 12:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585544 AND IsTranslated='N' AND AD_Language NOT IN ('de_CH', 'de_DE', 'en_US')
;

-- 2026-11-17T12:00:12.600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585544, AD_Language) FROM AD_Element_Trl WHERE AD_Element_ID=585544 AND IsTranslated='N' AND AD_Language NOT IN ('de_CH', 'de_DE', 'en_US')
;

-- Element: en_US
-- 2026-11-17T12:00:13.000Z
UPDATE AD_Element_Trl
SET Description='Number of copies of the delivery note and the Bestellkontrolle Büro for this shipping method. Empty = copies as without a shipping-method count; 0 = no automatic print.',
    Help='Number of copies of the delivery note and the Bestellkontrolle Büro for this shipping method. Empty = copies as without a shipping-method count; 0 = no automatic print.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-17 12:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585544 AND AD_Language='en_US'
;

-- base record follows the base-language translation (whichever language that is on the instance)
-- 2026-11-17T12:00:13.010Z
UPDATE AD_Element base
SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Element_Trl trl
WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language=getBaseLanguage() AND base.AD_Element_ID=585544
;

-- 2026-11-17T12:00:13.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585544,'en_US')
;

-- Propagate the name element to the field translations (field has AD_Name_ID=585544)
-- 2026-11-17T12:00:14.000Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585544)
;
