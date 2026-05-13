-- Add IsDropShipWarehouse to M_Warehouse
-- Script 1 of 3: AD_Element + AD_Element_Trl (de_DE, de_CH, en_US)

-- =============================================================================
-- 1. AD_Element
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help)
VALUES (584854 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        'IsDropShipWarehouse',
        'Streckengeschäft-Lager',
        'Streckengeschäft-Lager',
        'Wenn Ja, werden Verkaufsaufträge auf diesem Lager als Streckengeschäft (Dropship) abgewickelt. Bei Auftragsabschluss wird automatisch eine Bestellung an den Lieferanten erzeugt.',
        'Markiert dieses Lager als Streckengeschäft-Lager (Dropship). Auf solchen Lagern wird bei Auftragsabschluss automatisch genau eine Bestellung pro Verkaufsauftrag an den Lieferanten erzeugt — ohne Umweg über die normale Materialdisposition / Bestellkandidaten. Die Lieferant-Auswahl pro Position wird bei dieser Lager-Art strenger geprüft.');

-- Skeleton Trl rows
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 584854
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation
-- NOTE: AD_Element_Trl.Updated MUST be later than the downstream AD_Column_Trl / AD_Field_Trl /
-- AD_UI_Element_Trl skeleton inserts (those inherit Updated from their parent AD_Column / AD_Field /
-- AD_UI_Element insert timestamps in scripts 5801720 and 5801730 — up to 14:02:01). The propagation
-- function update_TRL_Tables_On_AD_Element_TRL_Update only copies AD_Element_Trl down when it is the
-- newer row; if AD_Element_Trl is older than the downstream Trl row, IsTranslated never flips to 'Y'
-- on the downstream side and the WebUI falls back to the base-language Name.
UPDATE AD_Element_Trl
SET Name        = 'Dropship Warehouse',
    PrintName   = 'Dropship Warehouse',
    Description = 'If Yes, sales orders on this warehouse are handled as dropship. On sales-order completion a single purchase order is automatically created for the vendor.',
    Help        = 'Marks this warehouse as a dropship warehouse. On such warehouses, completing a sales order automatically creates exactly one purchase order per sales order — bypassing the normal material-disposition / purchase-candidate path. Per-line vendor selection is enforced more strictly for dropship warehouses.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-05-11 14:03:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584854 AND AD_Language = 'en_US';

-- German translation (de_DE — base language)
UPDATE AD_Element_Trl
SET Name        = 'Streckengeschäft-Lager',
    PrintName   = 'Streckengeschäft-Lager',
    Description = 'Wenn Ja, werden Verkaufsaufträge auf diesem Lager als Streckengeschäft (Dropship) abgewickelt. Bei Auftragsabschluss wird automatisch eine Bestellung an den Lieferanten erzeugt.',
    Help        = 'Markiert dieses Lager als Streckengeschäft-Lager (Dropship). Auf solchen Lagern wird bei Auftragsabschluss automatisch genau eine Bestellung pro Verkaufsauftrag an den Lieferanten erzeugt — ohne Umweg über die normale Materialdisposition / Bestellkandidaten. Die Lieferant-Auswahl pro Position wird bei dieser Lager-Art strenger geprüft.',
    IsTranslated = 'N',
    Updated     = TO_TIMESTAMP('2026-05-11 14:03:01','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584854 AND AD_Language = 'de_DE';

-- de_CH = same as de_DE
UPDATE AD_Element_Trl
SET Name        = 'Streckengeschäft-Lager',
    PrintName   = 'Streckengeschäft-Lager',
    Description = 'Wenn Ja, werden Verkaufsaufträge auf diesem Lager als Streckengeschäft (Dropship) abgewickelt. Bei Auftragsabschluss wird automatisch eine Bestellung an den Lieferanten erzeugt.',
    Help        = 'Markiert dieses Lager als Streckengeschäft-Lager (Dropship). Auf solchen Lagern wird bei Auftragsabschluss automatisch genau eine Bestellung pro Verkaufsauftrag an den Lieferanten erzeugt — ohne Umweg über die normale Materialdisposition / Bestellkandidaten. Die Lieferant-Auswahl pro Position wird bei dieser Lager-Art strenger geprüft.',
    IsTranslated = 'N',
    Updated     = TO_TIMESTAMP('2026-05-11 14:03:02','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584854 AND AD_Language = 'de_CH';
