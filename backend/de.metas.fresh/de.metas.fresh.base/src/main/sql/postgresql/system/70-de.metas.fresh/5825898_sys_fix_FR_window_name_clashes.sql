-- Fixes failure of: 5825900_sys_gh32176_AD_Element_Trl_FR_Update.sql
--
-- The FR import batch 5825420 (first) then 5825900 re-translates French AD_Element names. Window
-- titles are driven by AD_Window.AD_Element_ID, and the deferred unique index ad_window_trl_name_uc
-- (Name, AD_Language) is checked at 5825900's COMMIT. On DBs that already carry the earlier FR
-- import, two windows end up with the same French (fr_CH) title and the COMMIT aborts the whole run.
-- This sub-slot (5825898) runs BEFORE 5825900, which is integrated and immutable, and clears both
-- clashes up front so 5825900 commits cleanly.
--
-- Clash 1 - "Caractéristique":
--   5825900 correctly renames AD_Element 574064 (Attribute, window 260 "Merkmal") to "Caractéristique".
--   But the earlier FR import gave AD_Element 574483 (Attribute Search, window 261 "Merkmal Suche")
--   that same French name - a mistranslation ("Attribute Search" is not "Attribute"). We re-translate
--   574483 to "Recherche de caractéristique", freeing "Caractéristique" for the real Attribute element.
--
-- Clash 2 - "Partenaire commercial":
--   5825420 renames AD_Element 187 (C_BPartner, current Business Partner window 540706) to
--   "Partenaire commercial"; 5825900 renames AD_Element 574089 to the same name. On DBs where the
--   legacy window 123 ("Geschäftspartner_OLD") is still driven by 574089, both windows collide.
--   574089 is a shared element (canonically "Business Partner") that 5825900 renames correctly and
--   overwrites, so it cannot be pre-translated. Following the window-359 precedent (migration 5802040),
--   we create a
--   dedicated legacy element and re-link window 123 to it, so 5825900's rename of 574089 no longer
--   titles window 123. The current Business Partner window 540706 keeps "Partenaire commercial".
--   (Same approach as prior migration 5802040, which re-linked legacy window 359 to element 584857.)
--
-- Idempotent: ON CONFLICT / NOT EXISTS guards + plain UPDATEs make a re-run a no-op.

-- ============================================================
-- Clash 1: correct AD_Element 574483 (Attribute Search) -> "Recherche de caractéristique"
-- ============================================================
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Recherche de caractéristique',
       Updated=TIMESTAMP '2026-10-15 00:00:00', UpdatedBy=100
WHERE AD_Element_ID=574483 AND AD_Language='fr_FR';
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(574483,'fr_FR');

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Recherche de caractéristique',
       Updated=TIMESTAMP '2026-10-15 00:00:00', UpdatedBy=100
WHERE AD_Element_ID=574483 AND AD_Language='fr_CH';
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(574483,'fr_CH');

-- ============================================================
-- Clash 2: dedicated legacy element for the old Business Partner window (123) + re-link
-- ============================================================
-- Dedicated legacy element. Base Name matches the legacy window's own base name so no base-name
-- change is needed and the post-migration translation sync stays consistent.
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, PrintName, Name)
VALUES (585543 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-10-15 00:00:00', 100, TIMESTAMP '2026-10-15 00:00:00', 100,
    'C_BPartner_Legacy', 'D', 'Geschäftspartner_OLD', 'Geschäftspartner_OLD')
ON CONFLICT (AD_Element_ID) DO NOTHING;

-- Translation rows for every active system language (copied from the base element, IsTranslated='N').
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Description, Help, Name, PO_Description, PO_Help, PO_Name,
    PO_PrintName, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name,
    t.PO_PrintName, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=585543
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- French name for the dedicated legacy element.
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Partenaire commercial (ancien)',
       PrintName='Partenaire commercial (ancien)', Updated=TIMESTAMP '2026-10-15 00:00:00', UpdatedBy=100
WHERE AD_Element_ID=585543 AND AD_Language IN ('fr_FR','fr_CH');

-- Re-link the legacy window to the dedicated element (base AD_Window.Name is already "Geschäftspartner_OLD").
UPDATE AD_Window SET AD_Element_ID=585543, Updated=TIMESTAMP '2026-10-15 00:00:00', UpdatedBy=100
WHERE AD_Window_ID=123;

-- Propagate the dedicated element's names into window 123's AD_Window_Trl rows (all languages).
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585543, NULL);
