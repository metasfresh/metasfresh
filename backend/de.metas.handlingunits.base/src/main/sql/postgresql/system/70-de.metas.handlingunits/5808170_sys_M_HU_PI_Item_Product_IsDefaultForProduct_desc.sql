-- Document the M_HU_PI_Item_Product.IsDefaultForProduct flag (UI label
-- "Standard-Packvorschrift" / "Default for Product"). Its Description and Help were empty,
-- so the field was unclear. It marks a packing instruction as the default/preferred one for
-- a product; it is used as the fallback whenever a document (manufacturing goods-receipt,
-- order line, material cockpit qty conversion) needs a packing for the product and none is
-- chosen explicitly. A product can have several packing rows (per partner, validity period
-- and capacity), and more than one may be flagged default; uniqueness is not enforced and the
-- best match is resolved by a fixed ordering (partner-specific, specific product, most recent
-- valid-from).
--
-- AD_Element 577437 (M_HU_PI_Item_Product.IsDefaultForProduct), shared by 3 standard fields
-- (593804 / 593805 / 593842 on windows 540191 / 540717 / 140) with consistent meaning
-- (all AD_Name_ID NULL) -> update the shared element in place. Names/labels unchanged.
--
-- AFFECTED (Description + Help: empty -> set)
-- ---------------------------------------------------------------------
--   Lang  | Description     | Help
--   ------+-----------------+-----------------
--   de_DE | (set, German)   | (set, German)
--   de_CH | (set, German)   | (set, German)
--   en_US | (set, English)  | (set, English)
-- NOT AFFECTED: Name / PrintName (kept), all other AD_Element usages (none other -- single column).

-- ensure _Trl rows exist for all active system languages (copies base text, IsTranslated='N')
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name,
                            PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
                            WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name,
       t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
       t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577437
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- de_DE (German)
UPDATE AD_Element_Trl SET
  Description='Markiert diese Packvorschrift als Standard für das Produkt. Sie wird vorgeschlagen, wenn an einem Beleg (z. B. Fertigungs-Wareneingang, Auftragszeile) keine Packvorschrift explizit gewählt wurde.',
  Help='Pro Produkt kann es mehrere Packvorschriften geben – je Geschäftspartner, Gültigkeitszeitraum und Kapazität –, und mehrere davon können als Standard markiert sein. Bei der Auflösung wird die am besten passende gewählt: partnerspezifische vor partnerneutralen, spezifisches Produkt vor „beliebiges Produkt", danach die mit dem jüngsten „Gültig ab"-Datum. Es wird nicht erzwungen, dass nur eine Vorschrift Standard ist; sind im selben Geltungsbereich mehrere als Standard markiert, entscheidet diese Reihenfolge, welche verwendet wird.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-16 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=577437 AND AD_Language='de_DE';

-- de_CH (Swiss German, same text as de_DE)
UPDATE AD_Element_Trl SET
  Description='Markiert diese Packvorschrift als Standard für das Produkt. Sie wird vorgeschlagen, wenn an einem Beleg (z. B. Fertigungs-Wareneingang, Auftragszeile) keine Packvorschrift explizit gewählt wurde.',
  Help='Pro Produkt kann es mehrere Packvorschriften geben – je Geschäftspartner, Gültigkeitszeitraum und Kapazität –, und mehrere davon können als Standard markiert sein. Bei der Auflösung wird die am besten passende gewählt: partnerspezifische vor partnerneutralen, spezifisches Produkt vor „beliebiges Produkt", danach die mit dem jüngsten „Gültig ab"-Datum. Es wird nicht erzwungen, dass nur eine Vorschrift Standard ist; sind im selben Geltungsbereich mehrere als Standard markiert, entscheidet diese Reihenfolge, welche verwendet wird.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-16 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=577437 AND AD_Language='de_CH';

-- en_US (English)
UPDATE AD_Element_Trl SET
  Description='Marks this packing instruction as the default for the product. It is proposed whenever a document (e.g. manufacturing goods-receipt, order line) needs a packing for the product and none was chosen explicitly.',
  Help='A product can have several packing instructions - per business partner, validity period and capacity - and more than one may be flagged as default. On resolution the best match is chosen: partner-specific before partner-neutral, specific product before ''any product'', then the one with the most recent ''valid from'' date. Uniqueness is not enforced; if several in the same scope are flagged default, this ordering decides which one is used.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-16 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=577437 AND AD_Language='en_US';

-- propagate Name/Description/Help to AD_Column(_Trl) and AD_Field(_Trl)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577437);
