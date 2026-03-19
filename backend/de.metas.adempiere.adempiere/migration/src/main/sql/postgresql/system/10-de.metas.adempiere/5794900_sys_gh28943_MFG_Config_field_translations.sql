-- gh#28943: Add proper German base-language names and English translations for IsScanResourceRequired and ReceiveUnitType

--
-- IsScanResourceRequired (AD_Element_ID=581714)
--

-- Update base element (de_DE is base language)
UPDATE AD_Element
SET Name='Scan der Ressource erforderlich',
    PrintName='Scan der Ressource erforderlich',
    Description='Benutzer muss zuerst den Ressource-QR-Code scannen',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE AD_Element_ID=581714;

UPDATE AD_Element_Trl
SET Name='Scan der Ressource erforderlich',
    PrintName='Scan der Ressource erforderlich',
    Description='Benutzer muss zuerst den Ressource-QR-Code scannen',
    IsTranslated='N',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE AD_Element_ID=581714 AND AD_Language='de_DE';

UPDATE AD_Element_Trl
SET Name='Scan der Ressource erforderlich',
    PrintName='Scan der Ressource erforderlich',
    Description='Benutzer muss zuerst den Ressource-QR-Code scannen',
    IsTranslated='N',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE AD_Element_ID=581714 AND AD_Language='de_CH';

UPDATE AD_Element_Trl
SET Name='Scan Resource QR Code',
    PrintName='Scan Resource QR Code',
    Description='User needs to scan the resource QR code first',
    IsTranslated='Y',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE AD_Element_ID=581714 AND AD_Language='en_US';

SELECT update_Column_Translation_From_AD_Element(581714);
SELECT update_FieldTranslation_From_AD_Name_Element(581714);

--
-- ReceiveUnitType (AD_Element_ID=584558)
--

UPDATE AD_Element
SET Name='Empfangseinheitentyp',
    PrintName='Empfangseinheitentyp',
    Description='Bestimmt, ob die Empfangsmenge in CU oder TU erfasst wird',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE AD_Element_ID=584558;

UPDATE AD_Element_Trl
SET Name='Empfangseinheitentyp',
    PrintName='Empfangseinheitentyp',
    Description='Bestimmt, ob die Empfangsmenge in CU oder TU erfasst wird',
    IsTranslated='N',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE AD_Element_ID=584558 AND AD_Language='de_DE';

UPDATE AD_Element_Trl
SET Name='Empfangseinheitentyp',
    PrintName='Empfangseinheitentyp',
    Description='Bestimmt, ob die Empfangsmenge in CU oder TU erfasst wird',
    IsTranslated='N',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE AD_Element_ID=584558 AND AD_Language='de_CH';

UPDATE AD_Element_Trl
SET Name='Receive Unit Type',
    PrintName='Receive Unit Type',
    Description='Determines whether the receive quantity is entered in CU or TU',
    IsTranslated='Y',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE AD_Element_ID=584558 AND AD_Language='en_US';

SELECT update_Column_Translation_From_AD_Element(584558);
SELECT update_FieldTranslation_From_AD_Name_Element(584558);
