-- gh30044: Show IsOnlyIfInProductAttributeSet in the M_HU_PI_Attribute grid
--          (window 540344, tab Merkmale) and fix PI 100/101 COO + weight entries.
--
-- Root cause: PI 100 (TU template) had IsOnlyIfInProductAttributeSet=Y for COO,
-- so COO was hidden in the HU editor for any product not carrying COO in its
-- M_AttributeSet.  PI 101 (VHU) already had N so VHUs always showed it.
-- Additionally PI 101 had WeightGross/WeightTare as Y while PI 100 had them as N.
--
-- IDs allocated from idserver.metas.de on 2026-06-05:
--   AD_MigrationScript 5806600
--
-- AFFECTED RECORDS
-- ================================================================
-- 1) AD_Element 544096 (IsOnlyIfInProductAttributeSet)
--    Language | Name (old -> new)
--    ---------+-------------------------------------------------
--    de_DE/CH | OnlyIfInProductAttributeSet -> Nur wenn in Produkt-Merkmalgruppe
--    en_US    | OnlyIfInProductAttributeSet -> Only if in Product Attribute Set
--    fr_CH    | OnlyIfInProductAttributeSet -> Seulement si dans le jeu d'attributs produit
--
-- 2) AD_UI_Element 552135: SeqNoGrid=50, IsDisplayedGrid='Y' (new grid column in tab 540825)
-- 3) AD_UI_Elements 544334-544333: SeqNoGrid 10-150 assigned (were all 0)
-- 4) M_HU_PI_Attribute 1000028/1000020/1000021/1000012: IsOnlyIfInProductAttributeSet -> 'N'
-- 5) AD_Element 368 (IsDisplayed):                  Displayed -> Anzeigen
-- 6) AD_Element 542193 (PropagationType):            Propagation Type -> Weitergabetyp
-- 7) AD_Element 542198 (AggregationStrategy):        Aggregation Strategy -> Aggregierungsstrategie
-- 8) AD_Element 542199 (SplitterStrategy):           Splitter Strategy -> Splitter-Strategie

-- 9) AD_Element 542463 (HU_TansferStrategy):         HU Transfer Attribute Strategy -> Transferstrategie
-- 10) AD_Element 542574 (UseInASI):                  Use in ASI -> In Merkmalausprägung verwenden
-- NOT AFFECTED: any other usages of AD_Element 544096

-- 1. Translations for the IsOnlyIfInProductAttributeSet element (AD_Element_ID=544096)
UPDATE AD_Element_Trl
SET    Name         = 'Nur wenn in Produkt-Merkmalgruppe',
       PrintName   = 'Nur wenn in Merkmalgruppe',
       Description = 'Merkmal nur anzeigen, wenn es in der Merkmalgruppe des Produktes enthalten ist. Nur wirksam wenn Anzeigen gesetzt ist.',
       IsTranslated = 'Y',
       Updated     = TO_TIMESTAMP('2026-06-05 00:00:10', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Element_ID = 544096 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET    Name         = 'Only if in Product Attribute Set',
       PrintName   = 'Only if in Attr. Set',
       Description = 'Show attribute only if it is part of the product''s attribute set. Only effective when Displayed is set.',
       IsTranslated = 'Y',
       Updated     = TO_TIMESTAMP('2026-06-05 00:00:11', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Element_ID = 544096 AND AD_Language = 'en_US';

UPDATE AD_Element_Trl
SET    Name         = 'Seulement si dans le jeu d''attributs produit',
       PrintName   = 'Seulement si dans jeu attr.',
       Description = 'Afficher l''attribut uniquement s''il fait partie du jeu d''attributs du produit. N''a d''effet que si Affiché est coché.',
       IsTranslated = 'Y',
       Updated     = TO_TIMESTAMP('2026-06-05 00:00:12', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Element_ID = 544096 AND AD_Language = 'fr_CH';

-- Propagate element translations to AD_Field_Trl
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(544096);

-- 1b. German translations for untranslated elements used in tab 540825
UPDATE AD_Element_Trl
SET    Name = 'Anzeigen', PrintName = 'Anzeigen',
       IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-06-05 00:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 368 AND AD_Language IN ('de_DE', 'de_CH');  -- IsDisplayed

UPDATE AD_Element_Trl
SET    Name = 'Weitergabetyp', PrintName = 'Weitergabetyp',
       IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-06-05 00:00:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 542193 AND AD_Language IN ('de_DE', 'de_CH');  -- PropagationType

UPDATE AD_Element_Trl
SET    Name = 'Aggregierungsstrategie', PrintName = 'Aggregierungsstrategie',
       IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-06-05 00:00:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 542198 AND AD_Language IN ('de_DE', 'de_CH');  -- AggregationStrategy_JavaClass_ID

UPDATE AD_Element_Trl
SET    Name = 'Splitter-Strategie', PrintName = 'Splitter-Strategie',
       IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-06-05 00:00:16', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 542199 AND AD_Language IN ('de_DE', 'de_CH');  -- SplitterStrategy_JavaClass_ID


UPDATE AD_Element_Trl
SET    Name = 'Transferstrategie', PrintName = 'Transferstrategie',
       IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-06-05 00:00:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 542463 AND AD_Language IN ('de_DE', 'de_CH');  -- HU_TansferStrategy_JavaClass_ID

UPDATE AD_Element_Trl
SET    Name = 'In Merkmalausprägung verwenden', PrintName = 'In Merkmalausprägung',
       IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-06-05 00:00:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 542574 AND AD_Language IN ('de_DE', 'de_CH');  -- UseInASI

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(368);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542193);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542198);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542199);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542463);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542574);

-- 2. Assign proper SeqNoGrid to all grid columns in tab 540825 and enable the new one
UPDATE AD_UI_Element SET SeqNoGrid = 10,  Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544334; -- SeqNo
UPDATE AD_UI_Element SET SeqNoGrid = 20,  Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544335; -- M_Attribute_ID
UPDATE AD_UI_Element SET SeqNoGrid = 30,  Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544340; -- PropagationType
UPDATE AD_UI_Element SET SeqNoGrid = 40,  Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544338; -- UseInASI
UPDATE AD_UI_Element SET SeqNoGrid = 50,  IsDisplayedGrid = 'Y', Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 552135; -- IsOnlyIfInProductAttributeSet
UPDATE AD_UI_Element SET SeqNoGrid = 60,  Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544345; -- IsDisplayed
UPDATE AD_UI_Element SET SeqNoGrid = 70,  Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544346; -- IsReadOnly
UPDATE AD_UI_Element SET SeqNoGrid = 80,  Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544339; -- IsMandatory
UPDATE AD_UI_Element SET SeqNoGrid = 90,  Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544337; -- IsInstanceAttribute
UPDATE AD_UI_Element SET SeqNoGrid = 100, Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544341; -- AggregationStrategy
UPDATE AD_UI_Element SET SeqNoGrid = 110, Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544342; -- SplitterStrategy
UPDATE AD_UI_Element SET SeqNoGrid = 120, Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544343; -- HU Transfer Strategy
UPDATE AD_UI_Element SET SeqNoGrid = 130, Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544344; -- C_UOM_ID
UPDATE AD_UI_Element SET SeqNoGrid = 140, Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544336; -- IsActive
UPDATE AD_UI_Element SET SeqNoGrid = 150, Updated = TO_TIMESTAMP('2026-06-05 00:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 544333; -- AD_Org_ID

-- 3. Fix IsOnlyIfInProductAttributeSet:
--    PI 100 (TU template): COO was Y — inconsistent with PI 101 (VHU) which has N.
--    PI 101 (VHU): WeightGross and WeightTare were Y — inconsistent with PI 100 (TU template)
--    which has both as N.  Weight is universally relevant for logistics regardless of product
--    attribute set, so all three should be N across both PI versions.
SELECT backup_table('m_hu_pi_attribute', '_gh30044_coo_isonly');
UPDATE M_HU_PI_Attribute
SET    IsOnlyIfInProductAttributeSet = 'N',
       Updated                       = TO_TIMESTAMP('2026-06-05 00:00:30', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy                     = 99
WHERE  M_HU_PI_Attribute_ID IN (
    1000028,   -- COO         on PI 100 (TU template)
    1000020,   -- WeightGross on PI 101 (VHU)
    1000021,   -- WeightTare  on PI 101 (VHU)
    1000012    -- COO         on PI version 2002807 (EUR-Tauschpalette Holz LU)
);
