-- Contract compensation groups: PR-review follow-up clarifying the schema window.
--
-- 1) C_CompensationGroup_Schema.IsAdditive (AD_Element 585491): the Description was too vague
--    ("computed on its own base instead of the running total") -- rewrite it to spell out BOTH
--    values with a worked example, in de_DE / de_CH / en_US.
-- 2) C_CompensationGroup_SchemaLine.M_Product_Category_ID (AD_Field 785582 on the schema-line
--    grid tab 541042): the matching column (which order lines count toward this discount line)
--    sat amid the configuration columns (discount product, percentage, ...). Move it right after
--    the line's own SeqNo -- ahead of every configuration column -- so it reads as visibly apart,
--    and give it a field-specific name + description instead of the generic "Product Category"
--    wording it inherited from the shared M_Product_Category_ID element.
--
-- IDs allocated from idserver.metas.de on 2026-09-28:
--   AD_Element 585496 (field-specific "Applies to product category" label, wired via
--                      AD_Field.AD_Name_ID -- see Task 2 below for why a plain AD_Field override
--                      does not survive)
-- ============================================================================
-- 1) IsAdditive (AD_Element_ID=585491, AD_Field_ID=785581): explicit Y/N wording
-- ============================================================================
UPDATE AD_Element
SET Description = 'Y = jede Rabattzeile wird auf ihrer eigenen Bemessungsgrundlage berechnet, sodass sich die Prozentsätze addieren (3 % + 2 % = 5 % der Bemessungsgrundlage). N (Standard) = jede Rabattzeile wird auf dem nach den vorherigen Rabattzeilen derselben Bemessungsgrundlage verbleibenden Betrag berechnet (kumulativ).',
    Updated = TO_TIMESTAMP('2026-09-28 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585491
;
UPDATE AD_Element_Trl
SET Description = 'Y = jede Rabattzeile wird auf ihrer eigenen Bemessungsgrundlage berechnet, sodass sich die Prozentsätze addieren (3 % + 2 % = 5 % der Bemessungsgrundlage). N (Standard) = jede Rabattzeile wird auf dem nach den vorherigen Rabattzeilen derselben Bemessungsgrundlage verbleibenden Betrag berechnet (kumulativ).',
    Updated = TO_TIMESTAMP('2026-09-28 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585491 AND AD_Language = 'de_DE' -- base language; IsTranslated stays 'N'
;
UPDATE AD_Element_Trl
SET Description = 'Y = jede Rabattzeile wird auf ihrer eigenen Bemessungsgrundlage berechnet, sodass sich die Prozentsätze addieren (3 % + 2 % = 5 % der Bemessungsgrundlage). N (Standard) = jede Rabattzeile wird auf dem nach den vorherigen Rabattzeilen derselben Bemessungsgrundlage verbleibenden Betrag berechnet (kumulativ).',
    Updated = TO_TIMESTAMP('2026-09-28 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585491 AND AD_Language = 'de_CH' -- mirrors de_DE, as the original insert did
;
UPDATE AD_Element_Trl
SET Description = 'Y = every discount line is computed on its own base amount, so percentages add up (3% + 2% = 5% of the base). N (default) = every discount line is computed on the amount remaining after the earlier discount lines of the same base (compounding).',
    Updated = TO_TIMESTAMP('2026-09-28 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585491 AND AD_Language = 'en_US'
;
-- The field-level override on the Schema header tab (541041) is linked to this element via its
-- column (AD_Field.AD_Name_ID IS NULL, AD_Column.AD_Element_ID=585491), so the standard cascade
-- propagates the new Description down to AD_Field + AD_Field_Trl (all languages) in one call --
-- no manual AD_Field UPDATEs needed (direct UPDATEs would just get overwritten on the next sync).
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585491, NULL)
;
-- ============================================================================
-- 2) M_Product_Category_ID on the SchemaLine grid tab (541042): separate from the configuration
--    columns + give it a field-specific name/description
-- ============================================================================
-- 2a) Reposition: right after the line's own SeqNo (10), ahead of every configuration column
--     (Produkt=20, Aktiv=30/70, Gesamtauftragsrabatt%=40, Art=50/30, Break Value=60/50,
--     Vertragsbedingungen=70/60). 15 is free on both the form (SeqNo) and grid (SeqNoGrid) layers.
UPDATE AD_UI_Element
SET SeqNo = 15, SeqNoGrid = 15, Updated = TO_TIMESTAMP('2026-09-28 12:00:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 654910
;
-- 2b) Field-specific name + description via a dedicated AD_Name_ID element -- NOT a plain
--     AD_Field/AD_Field_Trl override. A direct override does not survive: the framework's
--     after_migration() hook runs update_FieldTranslation_From_AD_Name_Element(NULL, NULL) (ALL
--     elements) after every migration batch, and its AD_Column path re-syncs any field whose
--     AD_Field.AD_Name_ID IS NULL from its column's element (453, the generic M_Product_Category_ID
--     label) whenever f_trl.updated <> e_trl.updated -- which a fresh override always is. Setting
--     AD_Field.AD_Name_ID to a field-specific element removes it from that generic-element path
--     and puts it on the AD_Name_ID path instead, which syncs FROM this element and is therefore
--     stable across future syncs.
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585496 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 12:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 12:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.order', 'Gilt für Produkt Kategorie', 'Gilt für Produkt Kategorie',
        'Nur Auftragszeilen, deren Produkt zu dieser Kategorie oder einer ihrer Unterkategorien gehört, zählen zu dieser Rabattzeile. Leer = alle Zeilen.');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585496, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585496
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Applies to product category', PrintName = 'Applies to product category',
    Description = 'Only order lines whose product belongs to this category or one of its sub-categories count toward this discount line. Empty = all lines.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-09-28 12:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585496 AND AD_Language = 'en_US'
;
-- de_CH mirrors de_DE (already copied by the skeleton insert above); de_DE stays IsTranslated='N'
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-09-28 12:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585496 AND AD_Language = 'de_CH'
;
UPDATE AD_Field
SET AD_Name_ID = 585496,
    Updated = TO_TIMESTAMP('2026-09-28 12:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785582
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785582;
SELECT AD_Element_Link_Create_Missing_Field(785582)
;
-- Propagate the new element's Name/Description into AD_Field + AD_Field_Trl (all languages) now,
-- via the AD_Name_ID path -- stable, since future syncs of element 585496 keep coming from here.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585496, NULL)
;
UPDATE AD_UI_Element
SET Name = 'Gilt für Produkt Kategorie', Updated = TO_TIMESTAMP('2026-09-28 12:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 654910
;
