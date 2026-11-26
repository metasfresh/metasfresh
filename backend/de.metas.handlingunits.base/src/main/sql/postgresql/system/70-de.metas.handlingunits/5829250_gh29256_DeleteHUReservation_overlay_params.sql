-- Run mode: SWING_CLIENT

-- F19012 / gh29256: Partial reservation reversal (HU reservation, HU editor).
-- Adds the reverse-overlay parameters to AD_Process 540985 (WEBUI_C_OrderLineSO_Delete_HUReservation),
-- mirroring the make process' QtyToReserve (AD_Process 540983, AD_Process_Para 541321:
-- AD_Reference_ID=29 Quantity, FieldLength=0, no AD_Element):
-- editable QtyToUnreserve + read-only QtyCU_Reserved, QtyAvailableCU.
--
-- IDs allocated from the central ID server (idserver.metas.de) on 2026-10-13:
--   AD_MigrationScript 5829250 (file name prefix)
--   AD_Process_Para    543344 (QtyCU_Reserved), 543345 (QtyAvailableCU), 543346 (QtyToUnreserve)

-- ==========================================================================
-- 1. AD_Process_Para on 540985 (Delete HU Reservation)
-- ==========================================================================
-- QtyCU_Reserved: read-only
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Reference_ID,
                             ColumnName, Name, Description, Help, SeqNo, FieldLength, IsMandatory, IsRange, ReadOnlyLogic, EntityType)
VALUES (543344 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-13 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-10-13 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        540985, 29,
        'QtyCU_Reserved', 'Reservierte Menge', 'Reservierte Menge', 'Bereits reservierte Menge; davon kann ein Teil storniert werden.',
        10, 0, 'N', 'N', '1=1', 'de.metas.handlingunits');

-- QtyAvailableCU: read-only
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Reference_ID,
                             ColumnName, Name, Description, Help, SeqNo, FieldLength, IsMandatory, IsRange, ReadOnlyLogic, EntityType)
VALUES (543345 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-13 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-10-13 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        540985, 29,
        'QtyAvailableCU', 'Verfügbare Menge', 'Verfügbare Menge (Lagerbestand - Reserviert)',
        'Verfügbare Menge = Lagerbestand minus reservierte Menge',
        20, 0, 'N', 'N', '1=1', 'de.metas.handlingunits');

-- QtyToUnreserve: editable, mandatory (quantity to reverse)
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Reference_ID,
                             ColumnName, Name, SeqNo, FieldLength, IsMandatory, IsRange, EntityType)
VALUES (543346 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-13 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-10-13 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        540985, 29,
        'QtyToUnreserve', 'Menge', 100, 0, 'Y', 'N', 'de.metas.handlingunits');

-- ==========================================================================
-- 2. AD_Process_Para_Trl (rows for all system languages, then EN texts)
-- ==========================================================================
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, p.AD_Process_Para_ID, p.Description, p.Help, p.Name, 'N', p.AD_Client_ID, p.AD_Org_ID, p.Created, p.CreatedBy, p.Updated, p.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para p
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND p.AD_Process_Para_ID IN (543344, 543345, 543346)
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Process_Para_ID = p.AD_Process_Para_ID);

-- German (de_DE, de_CH): the base texts are already German
UPDATE AD_Process_Para_Trl SET IsTranslated = 'Y'
WHERE AD_Process_Para_ID IN (543344, 543345, 543346) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl SET Name = 'Reserved Quantity', Description = 'Reserved Quantity',
       Help = 'Quantity already reserved; a part of it can be reversed.', IsTranslated = 'Y'
WHERE AD_Process_Para_ID = 543344 AND AD_Language = 'en_US';

UPDATE AD_Process_Para_Trl SET Name = 'Available Quantity', Description = 'Available Quantity (On Hand - Reserved)',
       Help = 'Quantity available to promise = On Hand minus Reserved', IsTranslated = 'Y'
WHERE AD_Process_Para_ID = 543345 AND AD_Language = 'en_US';

UPDATE AD_Process_Para_Trl SET Name = 'Quantity', IsTranslated = 'Y'
WHERE AD_Process_Para_ID = 543346 AND AD_Language = 'en_US';
