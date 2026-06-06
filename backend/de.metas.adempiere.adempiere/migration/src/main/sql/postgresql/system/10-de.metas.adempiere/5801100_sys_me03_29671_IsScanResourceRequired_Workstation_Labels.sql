-- me03#29671: rename "Arbeitsplatz-Scan erforderlich" → "Arbeitsstation-Scan erforderlich"
-- and add German translations for PP_Workstation_UserAssign.
--
-- Background: The AD_Element 581714 (`IsScanResourceRequired`) flag drives the
-- *workstation* (S_Resource type WS, "Arbeitsstation") scan dialog and the strict
-- WorkStation_ID launcher filter — NOT the Arbeitsplatz / C_Workplace flow. The
-- previous migration 5794900_sys_gh28943 set the German label to "Arbeitsplatz-Scan",
-- which collided with the C_Workplace concept and confused configurators (root
-- cause of part of me03#29671). The English en_US Trl is already correct
-- ("Workstation Scan Required") and is left untouched.
--
-- Locked terminology source-of-truth: ai-work/29671/terminology.md
--   Arbeitsstation = S_Resource type WS = Workstation (one word)
--   Arbeitsplatz   = C_Workplace        = Workplace

--
-- Block 1: AD_Element 581714 IsScanResourceRequired — base + de_DE + de_CH
--

UPDATE AD_Element
SET Name='Arbeitsstation-Scan erforderlich',
    PrintName='Arbeitsstation-Scan erforderlich',
    Description='Benutzer muss vor der Produktionsarbeit zuerst einen Arbeitsstation-QR-Code scannen. Es werden nur Aufträge der zugewiesenen Arbeitsstation angezeigt.',
    Updated='2026-05-06 16:30',
    UpdatedBy=100
WHERE AD_Element_ID=581714;

UPDATE AD_Element_Trl
SET Name='Arbeitsstation-Scan erforderlich',
    PrintName='Arbeitsstation-Scan erforderlich',
    Description='Benutzer muss vor der Produktionsarbeit zuerst einen Arbeitsstation-QR-Code scannen. Es werden nur Aufträge der zugewiesenen Arbeitsstation angezeigt.',
    IsTranslated='Y',
    Updated='2026-05-06 16:30',
    UpdatedBy=100
WHERE AD_Element_ID=581714 AND AD_Language='de_DE';

UPDATE AD_Element_Trl
SET Name='Arbeitsstation-Scan erforderlich',
    PrintName='Arbeitsstation-Scan erforderlich',
    Description='Benutzer muss vor der Produktionsarbeit zuerst einen Arbeitsstation-QR-Code scannen. Es werden nur Aufträge der zugewiesenen Arbeitsstation angezeigt.',
    IsTranslated='Y',
    Updated='2026-05-06 16:30',
    UpdatedBy=100
WHERE AD_Element_ID=581714 AND AD_Language='de_CH';

-- Propagate to AD_Column / AD_Field / AD_Tab / AD_Window via the standard helpers.
SELECT update_Column_Translation_From_AD_Element(581714);
SELECT update_FieldTranslation_From_AD_Name_Element(581714);

--
-- Block 2: AD_Element 583021 PP_Workstation_UserAssign_ID — add missing German
-- translations. Currently all four languages still hold the English fallback
-- "Workstation User Assignment". en_US/fr_CH stay as-is; only DE is filled.
--

UPDATE AD_Element_Trl
SET Name='Arbeitsstation-Benutzerzuweisung',
    PrintName='Arbeitsstation-Benutzerzuweisung',
    IsTranslated='Y',
    Updated='2026-05-06 16:30',
    UpdatedBy=100
WHERE AD_Element_ID=583021 AND AD_Language='de_DE';

UPDATE AD_Element_Trl
SET Name='Arbeitsstation-Benutzerzuweisung',
    PrintName='Arbeitsstation-Benutzerzuweisung',
    IsTranslated='Y',
    Updated='2026-05-06 16:30',
    UpdatedBy=100
WHERE AD_Element_ID=583021 AND AD_Language='de_CH';

SELECT update_Column_Translation_From_AD_Element(583021);
SELECT update_FieldTranslation_From_AD_Name_Element(583021);
