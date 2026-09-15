-- 2026-04-24 https://github.com/metasfresh/me03/issues/29368
-- Iter 2: AD_Element for DueAmt_Actual — the actual allocated amount on the LC pay-schedule step.

-- =============================================================================
-- 1. AD_Element
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (584784 /*From ID Server*/, 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0,
        'DueAmt_Actual', 'Tatsächlich fälliger Betrag', 'Tatsächlich fälliger Betrag',
        'Tatsächlicher Betrag, der diesem Zahlungsplan-Schritt zugewiesen wurde',
        'Wird durch die LC-Autoritätsfunktion gesetzt, wenn eine Proforma-Rechnung zugewiesen wird. '
        || 'DueAmt bleibt an order.GrandTotal × Prozentsatz gebunden; DueAmt_Actual erfasst '
        || 'den tatsächlichen Betrag aus der Proforma-Rechnung. NULL bei allen Nicht-LC-Zeilen.',
        'D');

-- Skeleton Trl rows
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 584784
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation
UPDATE AD_Element_Trl
SET Name        = 'Actual due amount',
    PrintName   = 'Actual due amount',
    Description = 'Actual amount allocated to this pay-schedule step',
    Help        = 'Populated by the LC authority function when a proforma is allocated. '
                  || 'DueAmt stays bound to order.GrandTotal × break%; DueAmt_Actual tracks '
                  || 'the real amount from the proforma. NULL on all non-LC rows.',
    IsTranslated = 'Y',
    Updated     = '2026-04-24 21:00',
    UpdatedBy   = 0
WHERE AD_Element_ID = 584784 AND AD_Language = 'en_US';

-- German translation de_DE (base language)
UPDATE AD_Element_Trl
SET Name        = 'Tatsächlich fälliger Betrag',
    PrintName   = 'Tatsächlich fälliger Betrag',
    Description = 'Tatsächlicher Betrag, der diesem Zahlungsplan-Schritt zugewiesen wurde',
    Help        = 'Wird durch die LC-Autoritätsfunktion gesetzt, wenn eine Proforma-Rechnung zugewiesen wird. '
                  || 'DueAmt bleibt an order.GrandTotal × Prozentsatz gebunden; DueAmt_Actual erfasst '
                  || 'den tatsächlichen Betrag aus der Proforma-Rechnung. NULL bei allen Nicht-LC-Zeilen.',
    IsTranslated = 'N',
    Updated     = '2026-04-24 21:00',
    UpdatedBy   = 0
WHERE AD_Element_ID = 584784 AND AD_Language = 'de_DE';

-- German translation de_CH (same as de_DE)
UPDATE AD_Element_Trl
SET Name        = 'Tatsächlich fälliger Betrag',
    PrintName   = 'Tatsächlich fälliger Betrag',
    Description = 'Tatsächlicher Betrag, der diesem Zahlungsplan-Schritt zugewiesen wurde',
    Help        = 'Wird durch die LC-Autoritätsfunktion gesetzt, wenn eine Proforma-Rechnung zugewiesen wird. '
                  || 'DueAmt bleibt an order.GrandTotal × Prozentsatz gebunden; DueAmt_Actual erfasst '
                  || 'den tatsächlichen Betrag aus der Proforma-Rechnung. NULL bei allen Nicht-LC-Zeilen.',
    IsTranslated = 'N',
    Updated     = '2026-04-24 21:00',
    UpdatedBy   = 0
WHERE AD_Element_ID = 584784 AND AD_Language = 'de_CH';

-- Propagate translations from AD_Element to all dependent records
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584784);
