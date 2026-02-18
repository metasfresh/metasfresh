-- gh#28014: Create ascending-order pendant of "C_Period Ordered" (540658)
--
-- Reference 540658 ("C_Period Ordered") sorts descending (most recent first),
-- which makes sense for open-ended lookups (accounting reports without year filter).
--
-- But for processes that already have a C_Year_ID parameter pre-filtering to a single
-- year's 12 periods, descending order is confusing — users expect Jan → Dec.
-- Additionally, with the default WebUI lookup page size of 10, the last 2 months
-- (typically Jan + Feb) get cut off and hidden behind "Es gibt weitere Ergebnisse".
--
-- This migration:
-- 1) Creates reference 542051 "C_Period Ordered (asc)" with ascending StartDate order
-- 2) Switches all process parameters that have a sibling C_Year_ID parameter
--    from 540658 (desc) to 542051 (asc)

-- Step 1: Create the new AD_Reference
INSERT INTO AD_Reference (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive,
                          Created, CreatedBy, Updated, UpdatedBy,
                          Name, Description, ValidationType, EntityType, IsOrderByValue)
VALUES (542051, 0, 0, 'Y',
        now(), 100, now(), 100,
        'C_Period Ordered (asc)', 'C_Period ordered by StartDate ascending — use when year is already filtered', 'T', 'de.metas.fresh', 'N');

-- Step 2: Create the AD_Ref_Table (same as 540658 but with ascending order)
INSERT INTO AD_Ref_Table (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive,
                          Created, CreatedBy, Updated, UpdatedBy,
                          AD_Table_ID, AD_Key, AD_Display, OrderByClause, WhereClause,
                          IsValueDisplayed, EntityType)
VALUES (542051, 0, 0, 'Y',
        now(), 100, now(), 100,
        145, 837, null, 'C_Period.StartDate', null,
        'N', 'de.metas.fresh');

-- Step 3: Switch process parameters that have a sibling C_Year_ID parameter
-- from 540658 (desc) to 542051 (asc)
UPDATE AD_Process_Para pp
SET AD_Reference_Value_ID = 542051,
    Updated               = now(),
    UpdatedBy             = 100
WHERE pp.AD_Reference_Value_ID = 540658
  AND pp.IsActive = 'Y'
  AND EXISTS (
    SELECT 1
    FROM AD_Process_Para sibling
    WHERE sibling.AD_Process_ID = pp.AD_Process_ID
      AND sibling.ColumnName = 'C_Year_ID'
      AND sibling.IsActive = 'Y'
  );
