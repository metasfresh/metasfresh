-- Fix-up for M_DiscountSchema.ValidTo field (Task 1 of issue #28738)
-- Addresses: missing AD_UI_Element, missing AD_Column_Trl propagation, missing PersonalDataCategory, hardcoded timestamps
--
-- IDs allocated from idserver.metas.de on 2026-09-13:
--   AD_UI_Element  654742 (UI element for ValidTo field on Rabatt-Schema window)

-- ============================================================================
-- Step 1: Add hardcoded timestamps to the existing AD_Column row (was using NOW())
-- ============================================================================
-- The original migration used NOW() which produces non-reproducible timestamps.
-- Backfill with a consistent, ordered set of hardcoded timestamps.
UPDATE AD_Column
SET Created = TO_TIMESTAMP('2026-09-13 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    Updated = TO_TIMESTAMP('2026-09-13 10:00:00', 'YYYY-MM-DD HH24:MI:SS')
WHERE AD_Column_ID = 593552;

-- ============================================================================
-- Step 2: Add PersonalDataCategory='NP' to the AD_Column (was NULL)
-- ============================================================================
UPDATE AD_Column
SET PersonalDataCategory = 'NP'
WHERE AD_Column_ID = 593552;

-- ============================================================================
-- Step 3: Insert AD_Column_Trl skeleton rows for all active system languages
-- ============================================================================
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, c.Name, c.Description, 'N', c.AD_Client_ID, c.AD_Org_ID,
       TO_TIMESTAMP('2026-09-13 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-09-13 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
WHERE l.IsActive='Y'
  AND l.IsSystemLanguage='Y'
  AND c.AD_Column_ID = 593552
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl ct WHERE ct.AD_Language=l.AD_Language AND ct.AD_Column_ID=c.AD_Column_ID)
;

-- ============================================================================
-- Step 4: Propagate column translations from AD_Element to AD_Column_Trl
-- ============================================================================
SELECT update_column_translation_from_ad_element(618, NULL);

-- ============================================================================
-- Step 5: Fix timestamps on AD_Field row (was using NOW())
-- ============================================================================
UPDATE AD_Field
SET Created = TO_TIMESTAMP('2026-09-13 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    Updated = TO_TIMESTAMP('2026-09-13 10:00:02', 'YYYY-MM-DD HH24:MI:SS')
WHERE AD_Field_ID = 784974;

-- ============================================================================
-- Step 6: Fix timestamps on AD_Field_Trl rows (were using NOW())
-- ============================================================================
UPDATE AD_Field_Trl
SET Created = TO_TIMESTAMP('2026-09-13 10:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    Updated = TO_TIMESTAMP('2026-09-13 10:00:03', 'YYYY-MM-DD HH24:MI:SS')
WHERE AD_Field_ID = 784974;

-- ============================================================================
-- Step 7: Insert AD_UI_Element for the ValidTo field (NEW)
-- ============================================================================
-- Tab 404 ("Rabatt-Schema") is section-backed; fields need AD_UI_Element to render.
-- Place in same element group as ValidFrom (540610), SeqNo=45, IsDisplayed/IsDisplayedGrid='Y'
INSERT INTO AD_UI_Element (
  AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_Field_ID, AD_UI_ElementGroup_ID,
  Name, SeqNo, SeqNoGrid, IsDisplayed, IsDisplayedGrid, IsActive,
  Created, Updated, CreatedBy, UpdatedBy
) VALUES (
  654742, 0, 0, 404, 784974, 540610,
  'Gültig bis', 45, 85, 'Y', 'Y', 'Y',
  TO_TIMESTAMP('2026-09-13 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
  TO_TIMESTAMP('2026-09-13 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
  100, 100
) /*From ID Server*/;
