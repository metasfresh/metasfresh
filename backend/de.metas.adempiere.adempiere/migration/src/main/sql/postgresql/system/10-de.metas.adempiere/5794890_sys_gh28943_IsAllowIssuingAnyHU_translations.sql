-- gh#28943: Add proper German base-language names and English translations for IsAllowIssuingAnyHU
-- AD_Element_ID=583889

-- Update base element (de_DE is base language) with German name and description
UPDATE AD_Element
SET Name='Ausgabe beliebiger HU erlauben',
    PrintName='Ausgabe beliebiger HU erlauben',
    Description='Erlaubt das Scannen und Ausgeben von HUs, die nicht im Ausgabeplan enthalten sind',
    Help='Wenn aktiv, können Benutzer in der mobilen Produktions-UI beliebige HUs scannen und ausgeben, auch wenn diese nicht im automatisch erstellten Ausgabeplan enthalten sind. Das System erstellt dann automatisch einen Ausgabeschritt für die gescannte HU.',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE AD_Element_ID=583889;

-- Update de_DE translation (base language, IsTranslated='N')
UPDATE AD_Element_Trl
SET Name='Ausgabe beliebiger HU erlauben',
    PrintName='Ausgabe beliebiger HU erlauben',
    Description='Erlaubt das Scannen und Ausgeben von HUs, die nicht im Ausgabeplan enthalten sind',
    Help='Wenn aktiv, können Benutzer in der mobilen Produktions-UI beliebige HUs scannen und ausgeben, auch wenn diese nicht im automatisch erstellten Ausgabeplan enthalten sind. Das System erstellt dann automatisch einen Ausgabeschritt für die gescannte HU.',
    IsTranslated='N',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE AD_Element_ID=583889 AND AD_Language='de_DE';

-- Update de_CH translation (same as de_DE)
UPDATE AD_Element_Trl
SET Name='Ausgabe beliebiger HU erlauben',
    PrintName='Ausgabe beliebiger HU erlauben',
    Description='Erlaubt das Scannen und Ausgeben von HUs, die nicht im Ausgabeplan enthalten sind',
    Help='Wenn aktiv, können Benutzer in der mobilen Produktions-UI beliebige HUs scannen und ausgeben, auch wenn diese nicht im automatisch erstellten Ausgabeplan enthalten sind. Das System erstellt dann automatisch einen Ausgabeschritt für die gescannte HU.',
    IsTranslated='N',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE AD_Element_ID=583889 AND AD_Language='de_CH';

-- Update en_US translation
UPDATE AD_Element_Trl
SET Name='Allow Issuing Any HU',
    PrintName='Allow Issuing Any HU',
    Description='Allows scanning and issuing HUs that are not in the issue plan',
    Help='When active, users can scan and issue any HU in the manufacturing mobile UI, even if it is not in the automatically created issue plan. The system will automatically create an issue schedule step for the scanned HU.',
    IsTranslated='Y',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE AD_Element_ID=583889 AND AD_Language='en_US';

-- Propagate element translations to columns and fields
SELECT update_Column_Translation_From_AD_Element(583889);
SELECT update_FieldTranslation_From_AD_Name_Element(583889);
