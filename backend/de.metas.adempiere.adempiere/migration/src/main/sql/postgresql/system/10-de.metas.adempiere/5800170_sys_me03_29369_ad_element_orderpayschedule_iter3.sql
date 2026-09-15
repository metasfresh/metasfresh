-- 2026-04-28 https://github.com/metasfresh/me03/issues/29369
-- Iter 3: AD_Element rows for the three new C_OrderPaySchedule columns:
--   BaseAmt, M_InOut_ID (display override "Wareneingang"), C_Invoice_ID (reuse existing element).
--
-- C_Invoice_ID (element 1008, name "Rechnung") already matches the spec — no new element needed.
-- M_InOut_ID (element 1025) is named "Lieferung/Wareneingang" across 71+ fields; spec wants
--   "Wareneingang" on these specific pay-schedule fields → new element 584793 used via AD_Name_ID.
-- BaseAmt has no pre-existing element → new element 584792.

-- =============================================================================
-- 1. AD_Element for BaseAmt (new)
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (584792 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
        'BaseAmt',
        'Berechtigter Betrag',
        'Berechtigter Betrag',
        'Betrag, auf den der Prozentsatz angewendet wird',
        'Entspricht order.GrandTotal für die LC-Zeile, dem Wareneingangs-Bruttobetrag für die '
        || 'Lieferungs-Unterzeile und dem verbleibenden Restbetrag für die Restzeile.',
        'D');

-- Skeleton Trl rows for BaseAmt element
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 584792
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation for BaseAmt
UPDATE AD_Element_Trl
SET Name         = 'Eligible amount',
    PrintName    = 'Eligible amount',
    Description  = 'Amount the break% is applied to',
    Help         = 'Equals order.GrandTotal on the LC row, the receipt gross amount on the '
                   || 'Delivery sub-row, and the remainder on the residual row.',
    IsTranslated = 'Y',
    Updated      = '2026-04-28 21:30',
    UpdatedBy    = 0
WHERE AD_Element_ID = 584792 AND AD_Language = 'en_US';

-- German translation de_DE for BaseAmt
UPDATE AD_Element_Trl
SET Name         = 'Berechtigter Betrag',
    PrintName    = 'Berechtigter Betrag',
    Description  = 'Betrag, auf den der Prozentsatz angewendet wird',
    Help         = 'Entspricht order.GrandTotal für die LC-Zeile, dem Wareneingangs-Bruttobetrag für die '
                   || 'Lieferungs-Unterzeile und dem verbleibenden Restbetrag für die Restzeile.',
    IsTranslated = 'N',
    Updated      = '2026-04-28 21:30',
    UpdatedBy    = 0
WHERE AD_Element_ID = 584792 AND AD_Language = 'de_DE';

-- German translation de_CH for BaseAmt (explicit — not auto-mirrored from de_DE)
UPDATE AD_Element_Trl
SET Name         = 'Berechtigter Betrag',
    PrintName    = 'Berechtigter Betrag',
    Description  = 'Betrag, auf den der Prozentsatz angewendet wird',
    Help         = 'Entspricht order.GrandTotal für die LC-Zeile, dem Wareneingangs-Bruttobetrag für die '
                   || 'Lieferungs-Unterzeile und dem verbleibenden Restbetrag für die Restzeile.',
    IsTranslated = 'N',
    Updated      = '2026-04-28 21:30',
    UpdatedBy    = 0
WHERE AD_Element_ID = 584792 AND AD_Language = 'de_CH';

-- Propagate BaseAmt translations to dependent AD tables
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584792);

-- =============================================================================
-- 2. AD_Element for M_InOut_ID display override — "Wareneingang" (new)
--    Used only via AD_Field.AD_Name_ID on C_OrderPaySchedule fields.
--    The canonical M_InOut_ID element (1025, "Lieferung/Wareneingang") is kept unchanged.
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (584793 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
        'M_InOut_ID_Wareneingang',
        'Wareneingang',
        'Wareneingang',
        'Der Wareneingang, an den diese Lieferungs-Unterzeile gebunden ist',
        'NULL für die LC-Zeile und die Restzeile. Nur bei Lieferungs-Unterzeilen belegt.',
        'D');

-- Skeleton Trl rows for Wareneingang element
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 584793
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation for Wareneingang element
UPDATE AD_Element_Trl
SET Name         = 'Goods receipt',
    PrintName    = 'Goods receipt',
    Description  = 'The goods receipt this Delivery sub-row is tied to',
    Help         = 'NULL on the LC row and on the remainder row. Populated on Delivery sub-rows only.',
    IsTranslated = 'Y',
    Updated      = '2026-04-28 21:30',
    UpdatedBy    = 0
WHERE AD_Element_ID = 584793 AND AD_Language = 'en_US';

-- German translation de_DE for Wareneingang element
UPDATE AD_Element_Trl
SET Name         = 'Wareneingang',
    PrintName    = 'Wareneingang',
    Description  = 'Der Wareneingang, an den diese Lieferungs-Unterzeile gebunden ist',
    Help         = 'NULL für die LC-Zeile und die Restzeile. Nur bei Lieferungs-Unterzeilen belegt.',
    IsTranslated = 'N',
    Updated      = '2026-04-28 21:30',
    UpdatedBy    = 0
WHERE AD_Element_ID = 584793 AND AD_Language = 'de_DE';

-- German translation de_CH for Wareneingang element (explicit — not auto-mirrored)
UPDATE AD_Element_Trl
SET Name         = 'Wareneingang',
    PrintName    = 'Wareneingang',
    Description  = 'Der Wareneingang, an den diese Lieferungs-Unterzeile gebunden ist',
    Help         = 'NULL für die LC-Zeile und die Restzeile. Nur bei Lieferungs-Unterzeilen belegt.',
    IsTranslated = 'N',
    Updated      = '2026-04-28 21:30',
    UpdatedBy    = 0
WHERE AD_Element_ID = 584793 AND AD_Language = 'de_CH';

-- Propagate Wareneingang translations to dependent AD tables
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584793);
