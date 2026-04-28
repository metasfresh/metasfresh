-- 2026-04-28 https://github.com/metasfresh/me03/issues/29369
-- Iter 3: shared AD_Element for IsPartialInvoice on C_DocType + C_Invoice.
-- SAP terminology: "Teilrechnung" (de_DE / de_CH), "Partial invoice" (en_US).

-- =============================================================================
-- 1. AD_Element (base language = German)
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (584794 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
        'IsPartialInvoice', 'Teilrechnung', 'Teilrechnung',
        'Wenn aktiviert, ist diese Rechnung eine Teilrechnung.',
        'Bei einer Eingangsrechnung zu einem vorausbezahlten Auftrag bestimmt dieses Kennzeichen, '
        || 'wie der Anzahlungsbetrag zugeordnet wird: '
        || 'Teilrechnung (Y) → MIN(Wareneingang × LC%, verbleibende Anzahlung); '
        || 'Endrechnung (N) → verbleibende Anzahlung vollständig verbrauchen. '
        || 'Standard Y (Teilrechnung) auf Beleargt-Ebene; pro Rechnung überschreibbar, '
        || 'solange der Belegstatus Entwurf (DR) oder In Bearbeitung (IP) ist.',
        'D');

-- =============================================================================
-- 2. Skeleton AD_Element_Trl rows for all system languages
-- =============================================================================
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 584794
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- =============================================================================
-- 3. Explicit translations — de_DE, de_CH, en_US
-- =============================================================================

-- German translation de_DE (base language)
UPDATE AD_Element_Trl
SET Name        = 'Teilrechnung',
    PrintName   = 'Teilrechnung',
    Description = 'Wenn aktiviert, ist diese Rechnung eine Teilrechnung.',
    Help        = 'Bei einer Eingangsrechnung zu einem vorausbezahlten Auftrag bestimmt dieses Kennzeichen, '
                  || 'wie der Anzahlungsbetrag zugeordnet wird: '
                  || 'Teilrechnung (Y) → MIN(Wareneingang × LC%, verbleibende Anzahlung); '
                  || 'Endrechnung (N) → verbleibende Anzahlung vollständig verbrauchen. '
                  || 'Standard Y (Teilrechnung) auf Beleargt-Ebene; pro Rechnung überschreibbar, '
                  || 'solange der Belegstatus Entwurf (DR) oder In Bearbeitung (IP) ist.',
    IsTranslated = 'N',
    Updated     = '2026-04-28 21:30',
    UpdatedBy   = 0
WHERE AD_Element_ID = 584794 AND AD_Language = 'de_DE';

-- German translation de_CH (Schweizer Deutsch — gleich wie de_DE, kein ß)
UPDATE AD_Element_Trl
SET Name        = 'Teilrechnung',
    PrintName   = 'Teilrechnung',
    Description = 'Wenn aktiviert, ist diese Rechnung eine Teilrechnung.',
    Help        = 'Bei einer Eingangsrechnung zu einem vorausbezahlten Auftrag bestimmt dieses Kennzeichen, '
                  || 'wie der Anzahlungsbetrag zugeordnet wird: '
                  || 'Teilrechnung (Y) → MIN(Wareneingang × LC%, verbleibende Anzahlung); '
                  || 'Endrechnung (N) → verbleibende Anzahlung vollständig verbrauchen. '
                  || 'Standard Y (Teilrechnung) auf Belegart-Ebene; pro Rechnung überschreibbar, '
                  || 'solange der Belegstatus Entwurf (DR) oder In Bearbeitung (IP) ist.',
    IsTranslated = 'N',
    Updated     = '2026-04-28 21:30',
    UpdatedBy   = 0
WHERE AD_Element_ID = 584794 AND AD_Language = 'de_CH';

-- English translation en_US
UPDATE AD_Element_Trl
SET Name        = 'Partial invoice',
    PrintName   = 'Partial invoice',
    Description = 'When checked, this invoice is a partial invoice.',
    Help        = 'On a financial purchase invoice for a proforma''d purchase order, '
                  || 'the iter-3 prepayment allocation rule treats Partial invoices as '
                  || 'MIN(receipt × LC%, remaining_prepay) and Final (unchecked) invoices as '
                  || 'remaining_prepay (consumes all remaining prepay). '
                  || 'Default Y (Partial) at doctype level; per-invoice override is editable '
                  || 'while the invoice is Drafted (DR) or In-Progress (IP); readonly afterwards.',
    IsTranslated = 'Y',
    Updated     = '2026-04-28 21:30',
    UpdatedBy   = 0
WHERE AD_Element_ID = 584794 AND AD_Language = 'en_US';

-- =============================================================================
-- 4. Propagate translations to all dependent AD tables
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584794);
