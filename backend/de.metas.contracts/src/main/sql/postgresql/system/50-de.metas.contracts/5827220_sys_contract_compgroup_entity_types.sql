-- Contract compensation groups: entity types matching the owning module.
-- The settings window, its tabs, menu entry and fields belong to de.metas.contracts (like the table and its elements);
-- the IsAdditive column and element on C_CompensationGroup_Schema belong to de.metas.order (like its sibling columns).

UPDATE AD_Window
SET EntityType = 'de.metas.contracts',
    Updated    = TO_TIMESTAMP('2026-09-30 14:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy  = 100
WHERE AD_Window_ID = 542194
;

UPDATE AD_Tab
SET EntityType = 'de.metas.contracts',
    Updated    = TO_TIMESTAMP('2026-09-30 14:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy  = 100
WHERE AD_Tab_ID IN (549507, 549508)
;

UPDATE AD_Menu
SET EntityType = 'de.metas.contracts',
    Updated    = TO_TIMESTAMP('2026-09-30 14:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy  = 100
WHERE AD_Menu_ID = 542364
;

UPDATE AD_Field
SET EntityType = 'de.metas.contracts',
    Updated    = TO_TIMESTAMP('2026-09-30 14:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy  = 100
WHERE AD_Field_ID BETWEEN 785583 AND 785590
;

-- C_CompensationGroup_Schema.IsAdditive
UPDATE AD_Column
SET EntityType = 'de.metas.order',
    Updated    = TO_TIMESTAMP('2026-09-30 14:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy  = 100
WHERE AD_Column_ID = 593648
;

UPDATE AD_Element
SET EntityType = 'de.metas.order',
    Updated    = TO_TIMESTAMP('2026-09-30 14:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy  = 100
WHERE AD_Element_ID = 585491
;
