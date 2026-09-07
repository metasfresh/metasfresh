-- gh#28631: Delivery/Order Stop — AD follow-ups
-- Covers the two remaining items that needed the ID server:
--   * Task A — dedicated AD_Elements for C_BPartner.IsDeliveryStop and
--              C_BPartner.DeliveryStopReason, referenced via AD_Field.AD_Name_ID
--              on the BPartner-window fields so the shared elements
--              (543441 / 584648, used by M_ShipmentSchedule / M_ReceiptSchedule /
--              M_Shipment_Constraint) stay untouched at the column level.
--              The base-language fix applied to AD_Element 543441 in
--              5793380 stays valid in itself — it is not undone here.
--   * Task B — AD_Field + AD_UI_Element for M_ReceiptSchedule.IsDeliveryStop
--              on the Wareneingangsdisposition Logistik window
--              (AD_Window_ID=541954, AD_Tab_ID=548451). Read-only flag,
--              propagated from M_Shipment_Constraint.
--
-- IDs allocated from idserver.metas.de on 2026-05-19:
--   AD_Element        584889 (Task A — C_BPartner.IsDeliveryStop label/desc,
--                              pre-allocated, consumed during skill testing)
--   AD_Element        584890 (Task A — C_BPartner.DeliveryStopReason label/desc)
--   AD_Field          780254 (Task B — IsDeliveryStop on M_ReceiptSchedule
--                              window Tab 548451)
--   AD_UI_Element     651700 (Task B — paired UI element in group 553592
--                              "advanced edit")
--   AD_Element        584895 (Task B — receipt-side label "Wareneingangs-/
--                              Bestellsperre" via AD_Field.AD_Name_ID override
--                              so the shared element 543441 keeps its sales-side
--                              wording for M_ShipmentSchedule / M_Shipment_Constraint)
--   AD_MigrationScript 5803170 (this file's prefix)

-- ============================================================================
-- TASK A — Dedicated AD_Elements for the C_BPartner fields
-- ============================================================================

-- ----------------------------------------------------------------------------
-- A.1 New AD_Element 584889 for C_BPartner.IsDeliveryStop
-- ----------------------------------------------------------------------------
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description, Help)
VALUES (584889 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        'C_BPartner_IsDeliveryStop', 'D',
        'Liefer-/Auftragssperre', 'Liefer-/Auftragssperre',
        'Wenn aktiv, sind Liefer- und Wareneingangsbelege für diesen Geschäftspartner blockiert.',
        'Sperrt für diesen Geschäftspartner sowohl die Belieferung (Verkaufsaufträge gegen Rechnungs-Empfänger) als auch den Wareneingang (Einkaufsaufträge gegen Lieferant). Aktive offene Verkaufs-/Einkaufsaufträge können bei aktiver Sperre nicht fertiggestellt werden.');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            Name, PrintName, Description, Help, IsTranslated)
SELECT 584889, AD_Language, 0, 0, 'Y', TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0,
       'Liefer-/Auftragssperre', 'Liefer-/Auftragssperre',
       'Wenn aktiv, sind Liefer- und Wareneingangsbelege für diesen Geschäftspartner blockiert.',
       'Sperrt für diesen Geschäftspartner sowohl die Belieferung (Verkaufsaufträge gegen Rechnungs-Empfänger) als auch den Wareneingang (Einkaufsaufträge gegen Lieferant). Aktive offene Verkaufs-/Einkaufsaufträge können bei aktiver Sperre nicht fertiggestellt werden.',
       'N'
FROM AD_Language
WHERE IsSystemLanguage = 'Y'
  AND AD_Language NOT IN (SELECT AD_Language FROM AD_Element_Trl WHERE AD_Element_ID = 584889);

UPDATE AD_Element_Trl
SET Name = 'Delivery / Order Block', PrintName = 'Delivery / Order Block',
    Description = 'When active, both shipment and receipt documents are blocked for this business partner.',
    Help = 'Blocks for this business partner both shipments (sales orders against the Bill-BPartner) and receipts (purchase orders against the vendor BPartner). Active open sales / purchase orders cannot be completed while the block is active.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 584889 AND AD_Language = 'en_US';

-- ----------------------------------------------------------------------------
-- A.2 New AD_Element 584890 for C_BPartner.DeliveryStopReason
-- ----------------------------------------------------------------------------
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description, Help)
VALUES (584890 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        'C_BPartner_DeliveryStopReason', 'D',
        'Lieferstopp Grund', 'Lieferstopp Grund',
        'Begründung für die Liefer-/Auftragssperre dieses Geschäftspartners.',
        'Begründung für die Liefer-/Auftragssperre dieses Geschäftspartners (für Buchhaltungs-/Vertriebsabsprache). Pflichtfeld, wenn ''Liefer-/Auftragssperre'' aktiv ist.');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            Name, PrintName, Description, Help, IsTranslated)
SELECT 584890, AD_Language, 0, 0, 'Y', TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0,
       'Lieferstopp Grund', 'Lieferstopp Grund',
       'Begründung für die Liefer-/Auftragssperre dieses Geschäftspartners.',
       'Begründung für die Liefer-/Auftragssperre dieses Geschäftspartners (für Buchhaltungs-/Vertriebsabsprache). Pflichtfeld, wenn ''Liefer-/Auftragssperre'' aktiv ist.',
       'N'
FROM AD_Language
WHERE IsSystemLanguage = 'Y'
  AND AD_Language NOT IN (SELECT AD_Language FROM AD_Element_Trl WHERE AD_Element_ID = 584890);

UPDATE AD_Element_Trl
SET Name = 'Delivery / Order Block Reason', PrintName = 'Delivery / Order Block Reason',
    Description = 'Reason for the delivery / order block on this business partner.',
    Help = 'Reason for the delivery / order block on this business partner (for finance / sales coordination). Mandatory when ''Delivery / Order Block'' is active.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 584890 AND AD_Language = 'en_US';

-- ----------------------------------------------------------------------------
-- A.3 Wire the existing BPartner AD_Field rows to the new AD_Elements
--     (rows created by 5793400_sys_gh28631_BPartner_Window_DeliveryStop_fields.sql)
-- ----------------------------------------------------------------------------
UPDATE AD_Field
SET AD_Name_ID = 584889,
    Updated = TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Field_ID = 774882;

UPDATE AD_Field
SET AD_Name_ID = 584890,
    Updated = TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Field_ID = 774883;

-- Propagate Name/Description/Help from the new AD_Elements to the dependent
-- AD_Field_Trl rows (the standard pattern for AD_Name_ID-overridden fields).
SELECT update_FieldTranslation_From_AD_Name_Element(584889);
SELECT update_FieldTranslation_From_AD_Name_Element(584890);

-- ============================================================================
-- TASK B — M_ReceiptSchedule.IsDeliveryStop field on the Logistics window
-- (AD_Window_ID=541954, AD_Tab_ID=548451). Read-only flag, system-managed.
-- ============================================================================

-- B.1a New AD_Element 584895 — receipt-side label "Wareneingangs-/Bestellsperre"
--      used via AD_Field.AD_Name_ID so we keep AD_Element 543441's sales-side
--      wording ("Lieferstopp") for M_ShipmentSchedule / M_Shipment_Constraint.
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description, Help)
VALUES (584895 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        'M_ReceiptSchedule_IsDeliveryStop', 'de.metas.inoutcandidate',
        'Wareneingangs-/Bestellsperre', 'Wareneingangs-/Bestellsperre',
        'Wenn aktiv, sind Wareneingang und Bestellungen für diesen Lieferanten blockiert.',
        'Wird systemseitig aus M_Shipment_Constraint propagiert und kann hier nicht bearbeitet werden. Aktive offene Bestellungen können bei aktiver Sperre nicht fertiggestellt werden.');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            Name, PrintName, Description, Help, IsTranslated)
SELECT 584895, AD_Language, 0, 0, 'Y', TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0,
       'Wareneingangs-/Bestellsperre', 'Wareneingangs-/Bestellsperre',
       'Wenn aktiv, sind Wareneingang und Bestellungen für diesen Lieferanten blockiert.',
       'Wird systemseitig aus M_Shipment_Constraint propagiert und kann hier nicht bearbeitet werden. Aktive offene Bestellungen können bei aktiver Sperre nicht fertiggestellt werden.',
       'N'
FROM AD_Language
WHERE IsSystemLanguage = 'Y'
  AND AD_Language NOT IN (SELECT AD_Language FROM AD_Element_Trl WHERE AD_Element_ID = 584895);

UPDATE AD_Element_Trl
SET Name = 'Receipt / Purchase-Order Block', PrintName = 'Receipt / Purchase-Order Block',
    Description = 'When active, both goods-receipts and purchase orders are blocked for this vendor.',
    Help = 'Propagated by the system from M_Shipment_Constraint; not user-editable here. Open purchase orders cannot be completed while the block is active.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 584895 AND AD_Language = 'en_US';

-- B.1 AD_Field — IsDeliveryStop on M_ReceiptSchedule (column 592212).
--     AD_Name_ID points at the receipt-side element 584895 so the rendered label
--     and description use Wareneingang/Bestellung terminology, while the underlying
--     column keeps the shared AD_Element 543441 (sales-side) for compatibility.
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsReadOnly, IsSameLine, IsEncrypted,
                      IsFieldOnly, IsHeading, EntityType, DisplayLength)
VALUES (780254 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        548451, 592212, 584895,
        'Wareneingangs-/Bestellsperre',
        'Wenn aktiv, sind Wareneingang und Bestellungen für diesen Lieferanten blockiert.',
        'Y', 'Y', 'N', 'N',
        'N', 'N', 'de.metas.inoutcandidate', 1);

-- Translation skeleton — filled by the AD_Name_ID propagation below.
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                          AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 780254
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- B.2 AD_UI_Element — pair the field into the existing "advanced edit"
--     ElementGroup 553592 of Tab 548451.
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList,
                           IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           IsAllowFiltering, IsMultiLine)
VALUES (651700 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-19 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        548451, 553592, 780254, 'F',
        'Wareneingangs-/Bestellsperre', 200, 0, 0,
        'Y', 'Y', 'N', 'N',
        'N', 'N');

-- Propagate Name/Description/Help from the receipt-side element 584895
-- into the dependent AD_Field_Trl rows (the AD_Name_ID-overridden pattern).
SELECT update_FieldTranslation_From_AD_Name_Element(584895);
