-- gh#28565: Apply WebUI design guide to Promotion Code window (542105)
--
-- Design guide rules applied:
-- 1. Two-column layout: left = mandatory/important fields, right = checkboxes/switches + Org/Client
-- 2. IsActive must be the first field in the right column
-- 3. AD_Org_ID and AD_Client_ID as the last fields in the right column
-- 4. AD_Org_ID shown in grid view as the last column
-- 5. Grid view sorted by Name ascending (master data default)
--
-- Before:
--   Single column with all fields. IsActive at seqno 40 (middle of left column).
--   AD_Org_ID not in grid view. No sort order defined.
--
-- After:
--   Left column: Value, Name, Description, ValidTo (mandatory/important fields)
--   Right column: IsActive (first), AD_Org_ID, AD_Client_ID (last two)
--   Grid: Value, Name, Description, ValidTo, IsActive, AD_Org_ID (last)
--   Sort: Name ascending

-- ==========================================================================
-- 1. Create right UI column (seqno 20)
-- ==========================================================================
INSERT INTO AD_UI_Column (
  AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID,
  Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy
) VALUES (
  0, 0, 549276, 547597,
  TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'), 100, 'Y', 20, TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'), 100
);

-- ==========================================================================
-- 2. Create element group in the right column
-- ==========================================================================
INSERT INTO AD_UI_ElementGroup (
  AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID,
  Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy
) VALUES (
  0, 0, 549276, 554985,
  TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'), 100, 'Y', 'flags', 10, 'primary', TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'), 100
);

-- ==========================================================================
-- 3. Move IsActive to the right column (first field, seqno 10)
-- ==========================================================================
UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID = 554985,
    SeqNo = 10,
    Updated = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 648480;  -- IsActive

-- ==========================================================================
-- 4. Move AD_Org_ID and AD_Client_ID to the right column (last two fields)
-- ==========================================================================
UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID = 554985,
    SeqNo = 20,
    Updated = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 648481;  -- AD_Org_ID

UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID = 554985,
    SeqNo = 30,
    Updated = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 648482;  -- AD_Client_ID

-- ==========================================================================
-- 5. Add AD_Org_ID to grid view as the last column (seqnogrid=50)
-- ==========================================================================
UPDATE AD_UI_Element
SET SeqNoGrid = 50,
    IsDisplayedGrid = 'Y',
    Updated = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 648481;  -- AD_Org_ID

-- ==========================================================================
-- 6. Set grid sort to Name ascending (master data default)
-- ==========================================================================
UPDATE AD_Tab
SET OrderByClause = 'Name',
    Updated = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE AD_Tab_ID = 549080;
