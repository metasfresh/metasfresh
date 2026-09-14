-- Align External-System window names to German (base-language convention).
--
-- Two External-System windows carried an English name in the German base column
-- and de_DE/de_CH translations (the rest of the family is German), and one of
-- them additionally held the German text misplaced in the fr_CH/it_CH slots:
--   AD_Window 541967 (element 584191) "ExternalSystem Endpoint"
--   AD_Window 541540 (element 581062) "External system config Leich + Mehl"
--
-- Both windows are element-driven (AD_Window.AD_Element_ID), so the rename is
-- applied on the AD_Element_Trl rows and cascaded via
-- update_TRL_Tables_On_AD_Element_TRL_Update — which also syncs the element base
-- row and every dependent (AD_Window, AD_Tab, AD_Menu, AD_Column) + their _Trl.
-- German in the base column, English via the en_US _Trl override; fr_CH/it_CH
-- carry the German fallback (untranslated).

-- ============================================================
-- AD_Element 584191 → "Externes System Endpunkt"
-- ============================================================
UPDATE AD_Element_Trl SET Name='Externes System Endpunkt', PrintName='Externes System Endpunkt', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:20:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584191 AND AD_Language='de_DE';

UPDATE AD_Element_Trl SET Name='Externes System Endpunkt', PrintName='Externes System Endpunkt', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:20:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584191 AND AD_Language='de_CH';

UPDATE AD_Element_Trl SET Name='External System Endpoint', PrintName='External System Endpoint', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:20:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584191 AND AD_Language='en_US';

UPDATE AD_Element_Trl SET Name='Externes System Endpunkt', PrintName='Externes System Endpunkt', IsTranslated='N',
       Updated=TO_TIMESTAMP('2026-08-28 10:20:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584191 AND AD_Language IN ('fr_CH','it_CH');

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584191);

-- ============================================================
-- AD_Element 581062 → "Externe System Konfiguration Leich + Mehl"
-- (matches the config-window siblings "Externe System Konfiguration <X>")
-- ============================================================
UPDATE AD_Element_Trl SET Name='Externe System Konfiguration Leich + Mehl', PrintName='Externe System Konfiguration Leich + Mehl', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:21:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=581062 AND AD_Language='de_DE';

UPDATE AD_Element_Trl SET Name='Externe System Konfiguration Leich + Mehl', PrintName='Externe System Konfiguration Leich + Mehl', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:21:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=581062 AND AD_Language='de_CH';

UPDATE AD_Element_Trl SET Name='External system config Leich + Mehl', PrintName='External system config Leich + Mehl', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:21:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=581062 AND AD_Language='en_US';

UPDATE AD_Element_Trl SET Name='Externe System Konfiguration Leich + Mehl', PrintName='Externe System Konfiguration Leich + Mehl', IsTranslated='N',
       Updated=TO_TIMESTAMP('2026-08-28 10:21:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=581062 AND AD_Language IN ('fr_CH','it_CH');

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581062);
