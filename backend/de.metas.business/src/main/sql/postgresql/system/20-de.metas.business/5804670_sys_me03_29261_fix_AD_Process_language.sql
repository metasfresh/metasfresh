-- me03 #29261: Order Line Split — follow-up fix #2
-- Brings AD_Process and AD_Process_Para in line with the German-first convention
-- already applied to AD_Element / AD_Message in 5804660.
-- Convention: AD_*.Name holds German (base language); AD_*_Trl[en_US] holds English.
-- IDs from ID server (http://idserver.metas.de):
-- AD_MigrationScript -> 5804670

-- 2026-05-26T13:00:00.000Z

-- ============================================================================
-- AD_Process 585622 — base language to German + en_US Trl override
-- ============================================================================

UPDATE AD_Process
SET Name = 'Auftragsposition aufteilen',
    Description = 'Teilt diese Auftragsposition in zwei: Die ursprüngliche Position wird auf die gelieferte Menge reduziert, eine neue Position wird für die Restmenge erzeugt. Das Projekt wird in der neuen Position geleert.',
    Updated = NOW(),
    UpdatedBy = 100
WHERE AD_Process_ID = 585622 /*From ID Server*/;

INSERT INTO AD_Process_Trl (
    AD_Process_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, Description, Help, IsTranslated
) VALUES (
    585622 /*From ID Server*/, 'en_US', 0, 0, 'Y',
    NOW(), 100, NOW(), 100,
    'Split order line',
    'Splits this order line into two: original capped at delivered qty, new sibling line for the remainder. Project is cleared on the new line.',
    NULL, 'Y'
);

-- ============================================================================
-- AD_Process_Para 543209 — sync Name with AD_Element (German base)
-- ============================================================================

UPDATE AD_Process_Para
SET Name = 'Aufzuteilende Menge',
    Updated = NOW(),
    UpdatedBy = 100
WHERE AD_Process_Para_ID = 543209 /*From ID Server*/;

-- Sync auto-synced translation rows (IsTranslated='N') to the new German base
UPDATE AD_Process_Para_Trl
SET Name = 'Aufzuteilende Menge',
    Updated = NOW(),
    UpdatedBy = 100
WHERE AD_Process_Para_ID = 543209 /*From ID Server*/
  AND AD_Language IN ('de_DE', 'de_CH', 'fr_CH')
  AND IsTranslated = 'N';

-- Flip the en_US row to the English override + IsTranslated='Y'
UPDATE AD_Process_Para_Trl
SET Name = 'Qty to split off',
    IsTranslated = 'Y',
    Updated = NOW(),
    UpdatedBy = 100
WHERE AD_Process_Para_ID = 543209 /*From ID Server*/
  AND AD_Language = 'en_US';
