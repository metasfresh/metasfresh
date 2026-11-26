-- Fixes failure of: 5824250_sys_gh28738_M_DiscountSchema_ValidTo_fixes.sql
--
-- That script (already pushed, immutable) copies AD_Column.Name/Description into new
-- AD_Column_Trl rows (NOT NULL columns). AD_Column.Name is NULL for AD_Column_ID=593552 on a
-- fresh DB, because 5824240 never set it (it is normally populated by a later propagation call,
-- but 5824250 reads it before that happens) -- so 5824250's Step 3 fails with
-- "null value in column name of relation ad_column_trl violates not-null constraint" and the
-- whole single-transaction script rolls back.
--
-- Pre-populating AD_Column.Name/Description here (before 5824250 runs, per its lower numeric
-- prefix) lets 5824250 proceed and succeed exactly as originally written.
--
-- A raw UPDATE (not update_column_translation_from_ad_element/update_TRL_Tables_On_AD_Element_TRL_Update)
-- is used deliberately: 5824250's own Step 4 calls update_column_translation_from_ad_element(618, NULL)
-- immediately after, which re-syncs Name/Description/Updated from AD_Element_Trl_Effective_v
-- unconditionally (guarded only by updated<>e_trl.updated) -- so this raw seed is superseded within
-- the same script/transaction and cannot leave stale data behind.

UPDATE AD_Column c
SET Name        = e.Name,
    Description = e.Description
FROM AD_Element e
WHERE c.AD_Column_ID = 593552
  AND e.AD_Element_ID = c.AD_Element_ID
  AND c.Name IS NULL;
