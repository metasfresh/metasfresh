-- gh#28943: Add proper German base-language names and English translations for IsAllowIssuingAnyHU
-- AD_Element_ID=583889

-- Update base element (de_DE is base language) with German name and description
UPDATE AD_Element
SET Name='Zuteilung beliebiger HU erlauben',
    PrintName='Zuteilung beliebiger HU erlauben',
    Description='Erlaubt das Scannen und Zuteilen von HUs, die nicht in der Produktions-Zuteilungsdispo enthalten sind',
    Help='Wenn aktiv, können Benutzer in der mobilen Produktions-UI beliebige HUs scannen und zuteilen, auch wenn diese nicht in der automatisch erstellten Produktions-Zuteilungsdispo enthalten sind. Das System erstellt dann automatisch einen Zuteilungsschritt für die gescannte HU.',
    Updated='2026-03-20 06:00',
    UpdatedBy=100
WHERE AD_Element_ID=583889;

-- Update de_DE translation (base language, IsTranslated='N')
UPDATE AD_Element_Trl
SET Name='Zuteilung beliebiger HU erlauben',
    PrintName='Zuteilung beliebiger HU erlauben',
    Description='Erlaubt das Scannen und Zuteilen von HUs, die nicht in der Produktions-Zuteilungsdispo enthalten sind',
    Help='Wenn aktiv, können Benutzer in der mobilen Produktions-UI beliebige HUs scannen und zuteilen, auch wenn diese nicht in der automatisch erstellten Produktions-Zuteilungsdispo enthalten sind. Das System erstellt dann automatisch einen Zuteilungsschritt für die gescannte HU.',
    IsTranslated='N',
    Updated='2026-03-20 06:00',
    UpdatedBy=100
WHERE AD_Element_ID=583889 AND AD_Language='de_DE';

-- Update de_CH translation (same as de_DE)
UPDATE AD_Element_Trl
SET Name='Zuteilung beliebiger HU erlauben',
    PrintName='Zuteilung beliebiger HU erlauben',
    Description='Erlaubt das Scannen und Zuteilen von HUs, die nicht in der Produktions-Zuteilungsdispo enthalten sind',
    Help='Wenn aktiv, können Benutzer in der mobilen Produktions-UI beliebige HUs scannen und zuteilen, auch wenn diese nicht in der automatisch erstellten Produktions-Zuteilungsdispo enthalten sind. Das System erstellt dann automatisch einen Zuteilungsschritt für die gescannte HU.',
    IsTranslated='N',
    Updated='2026-03-20 06:00',
    UpdatedBy=100
WHERE AD_Element_ID=583889 AND AD_Language='de_CH';

-- Update en_US translation
UPDATE AD_Element_Trl
SET Name='Allow Issuing Any HU',
    PrintName='Allow Issuing Any HU',
    Description='Allows scanning and issuing HUs that are not in the manufacturing issue plan',
    Help='When active, users can scan and issue any HU in the manufacturing mobile UI, even if it is not in the automatically created manufacturing issue plan. The system will automatically create an issue schedule step for the scanned HU.',
    IsTranslated='Y',
    Updated='2026-03-20 06:00',
    UpdatedBy=100
WHERE AD_Element_ID=583889 AND AD_Language='en_US';

-- Propagate element translations to columns and fields
SELECT update_Column_Translation_From_AD_Element(583889);
SELECT update_FieldTranslation_From_AD_Name_Element(583889);
