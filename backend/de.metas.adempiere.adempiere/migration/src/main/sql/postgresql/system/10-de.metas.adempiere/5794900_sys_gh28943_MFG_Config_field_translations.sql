-- gh#28943: Add proper German base-language names and English translations for IsScanResourceRequired and ReceiveUnitType

--
-- IsScanResourceRequired (AD_Element_ID=581714)
--

-- Update base element (de_DE is base language)
UPDATE AD_Element
SET Name='Arbeitsplatz-Scan erforderlich',
    PrintName='Arbeitsplatz-Scan erforderlich',
    Description='Benutzer muss vor der Produktionsarbeit zuerst einen Arbeitsplatz-QR-Code scannen. Es werden nur Aufträge des zugewiesenen Arbeitsplatzes angezeigt.',
    Updated='2026-03-20 06:00',
    UpdatedBy=100
WHERE AD_Element_ID=581714;

UPDATE AD_Element_Trl
SET Name='Arbeitsplatz-Scan erforderlich',
    PrintName='Arbeitsplatz-Scan erforderlich',
    Description='Benutzer muss vor der Produktionsarbeit zuerst einen Arbeitsplatz-QR-Code scannen. Es werden nur Aufträge des zugewiesenen Arbeitsplatzes angezeigt.',
    IsTranslated='N',
    Updated='2026-03-20 06:00',
    UpdatedBy=100
WHERE AD_Element_ID=581714 AND AD_Language='de_DE';

UPDATE AD_Element_Trl
SET Name='Arbeitsplatz-Scan erforderlich',
    PrintName='Arbeitsplatz-Scan erforderlich',
    Description='Benutzer muss vor der Produktionsarbeit zuerst einen Arbeitsplatz-QR-Code scannen. Es werden nur Aufträge des zugewiesenen Arbeitsplatzes angezeigt.',
    IsTranslated='N',
    Updated='2026-03-20 06:00',
    UpdatedBy=100
WHERE AD_Element_ID=581714 AND AD_Language='de_CH';

UPDATE AD_Element_Trl
SET Name='Workstation Scan Required',
    PrintName='Workstation Scan Required',
    Description='User must scan a workstation QR code before starting manufacturing work. Only orders for the assigned workstation are displayed.',
    IsTranslated='Y',
    Updated='2026-03-20 06:00',
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
    Updated='2026-03-20 06:00',
    UpdatedBy=100
WHERE AD_Element_ID=584558;

UPDATE AD_Element_Trl
SET Name='Empfangseinheitentyp',
    PrintName='Empfangseinheitentyp',
    Description='Bestimmt, ob die Empfangsmenge in CU oder TU erfasst wird',
    IsTranslated='N',
    Updated='2026-03-20 06:00',
    UpdatedBy=100
WHERE AD_Element_ID=584558 AND AD_Language='de_DE';

UPDATE AD_Element_Trl
SET Name='Empfangseinheitentyp',
    PrintName='Empfangseinheitentyp',
    Description='Bestimmt, ob die Empfangsmenge in CU oder TU erfasst wird',
    IsTranslated='N',
    Updated='2026-03-20 06:00',
    UpdatedBy=100
WHERE AD_Element_ID=584558 AND AD_Language='de_CH';

UPDATE AD_Element_Trl
SET Name='Receive Unit Type',
    PrintName='Receive Unit Type',
    Description='Determines whether the receive quantity is entered in CU or TU',
    IsTranslated='Y',
    Updated='2026-03-20 06:00',
    UpdatedBy=100
WHERE AD_Element_ID=584558 AND AD_Language='en_US';

SELECT update_Column_Translation_From_AD_Element(584558);
SELECT update_FieldTranslation_From_AD_Name_Element(584558);
