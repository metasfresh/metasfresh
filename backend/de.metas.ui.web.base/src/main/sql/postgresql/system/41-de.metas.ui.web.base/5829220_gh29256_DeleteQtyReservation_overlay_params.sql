-- Run mode: SWING_CLIENT

-- F19012 / gh29256: Partial reservation reversal.
-- Adds the reverse-overlay parameters to AD_Process 585589 (MD_CockpitV2_DeleteQtyReservation),
-- mirroring the make overlay (AD_Process 585588): editable QtyTU + read-only QtyTU_Reserved, QtyAvailableTU.
--
-- IDs allocated from the central ID server (idserver.metas.de) on 2026-10-12:
--   AD_Element       585537 (new: QtyTU_Reserved)
--   AD_Process_Para  543340 (QtyTU_Reserved), 543341 (QtyAvailableTU), 543342 (QtyTU)
-- Reused existing elements: 542490 (QtyTU), 584637 (QtyAvailableTU)

-- ==========================================================================
-- 1. AD_Element QtyTU_Reserved (base language de_DE)
-- ==========================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (585537 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-12 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-10-12 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        'QtyTU_Reserved',
        'Reservierte TU Menge',
        'Reservierte TU Menge',
        'Reservierte TU Menge',
        'Bereits reservierte TU Menge; davon kann ein Teil storniert werden.',
        'D');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            Name, PrintName, Description, Help, IsTranslated)
SELECT 585537, l.AD_Language, 0, 0, 'Y', TO_TIMESTAMP('2026-10-12 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-10-12 10:00', 'YYYY-MM-DD HH24:MI'), 0,
       CASE WHEN l.AD_Language LIKE 'en%' THEN 'Reserved TU Quantity' ELSE 'Reservierte TU Menge' END,
       CASE WHEN l.AD_Language LIKE 'en%' THEN 'Reserved TU Qty' ELSE 'Reservierte TU Menge' END,
       CASE WHEN l.AD_Language LIKE 'en%' THEN 'Reserved TU Quantity' ELSE 'Reservierte TU Menge' END,
       CASE WHEN l.AD_Language LIKE 'en%' THEN 'TU quantity already reserved; a part of it can be reversed.'
            ELSE 'Bereits reservierte TU Menge; davon kann ein Teil storniert werden.' END,
       CASE WHEN l.AD_Language = 'en_US' THEN 'Y' ELSE 'N' END
FROM AD_Language l
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Element_ID = 585537 AND t.AD_Language = l.AD_Language);

-- ==========================================================================
-- 2. AD_Process_Para on 585589 (Delete Qty Reservation)
-- ==========================================================================
-- QtyTU_Reserved: read-only
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, AD_Reference_ID,
                             ColumnName, Name, Description, Help, SeqNo, FieldLength, IsMandatory, IsRange, ReadOnlyLogic, EntityType)
VALUES (543340 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-12 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-10-12 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        585589, 585537, 11,
        'QtyTU_Reserved', 'Reservierte TU Menge', 'Reservierte TU Menge', 'Bereits reservierte TU Menge; davon kann ein Teil storniert werden.',
        10, 10, 'N', 'N', '1=1', 'D');

-- QtyAvailableTU: read-only
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, AD_Reference_ID,
                             ColumnName, Name, Description, Help, SeqNo, FieldLength, IsMandatory, IsRange, ReadOnlyLogic, EntityType)
VALUES (543341 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-12 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-10-12 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        585589, 584637, 11,
        'QtyAvailableTU', 'Verfügbare TU Menge', 'Verfügbare TU Menge (Lagerbestand - Reserviert)',
        'Verfügbare TU Menge = Lagerbestand minus reservierte TU Menge',
        20, 10, 'N', 'N', '1=1', 'D');

-- QtyTU: editable, mandatory (quantity to reverse)
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, AD_Reference_ID,
                             ColumnName, Name, SeqNo, FieldLength, IsMandatory, IsRange, EntityType)
VALUES (543342 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-12 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-10-12 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        585589, 542490, 29,
        'QtyTU', 'TU Anzahl', 100, 0, 'Y', 'N', 'D');

-- ==========================================================================
-- 3. AD_Process_Para_Trl (rows for all system languages, then DE/EN texts)
-- ==========================================================================
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, p.AD_Process_Para_ID, p.Description, p.Help, p.Name, 'N', p.AD_Client_ID, p.AD_Org_ID, p.Created, p.CreatedBy, p.Updated, p.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para p
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND p.AD_Process_Para_ID IN (543340, 543341, 543342)
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Process_Para_ID = p.AD_Process_Para_ID);

UPDATE AD_Process_Para_Trl SET Name = 'Reserved TU Quantity', Description = 'Reserved TU Quantity',
       Help = 'TU quantity already reserved; a part of it can be reversed.', IsTranslated = 'Y'
WHERE AD_Process_Para_ID = 543340 AND AD_Language = 'en_US';

UPDATE AD_Process_Para_Trl SET Name = 'Available TU Quantity', Description = 'Available TU Quantity (On Hand - Reserved)',
       Help = 'TU quantity available to promise = On Hand TU minus Reserved TU', IsTranslated = 'Y'
WHERE AD_Process_Para_ID = 543341 AND AD_Language = 'en_US';

UPDATE AD_Process_Para_Trl SET Name = 'TU Quantity', IsTranslated = 'Y'
WHERE AD_Process_Para_ID = 543342 AND AD_Language = 'en_US';
