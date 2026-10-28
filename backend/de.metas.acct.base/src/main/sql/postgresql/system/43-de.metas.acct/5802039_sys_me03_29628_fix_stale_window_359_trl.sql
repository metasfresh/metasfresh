-- Fixes failure of: 5802040_sys_me03_29628_window.sql (Tax Declaration Iter 4 — me03 issue 29628)
--
-- Pre-clean stale AD_Window_Trl rows for legacy window 359 (Steuererklärung, AD_Element_ID was 2862).
--
-- Root cause: https://github.com/metasfresh/metasfresh/pull/23924 (Tax Declaration Iter 4) makes
-- 5802040_sys_me03_29628_window.sql:
--   1. Inactivate legacy window 359.
--   2. Rename window 359's TRL rows for en_US ('Tax Declaration (legacy)') and de_DE / de_CH
--      ('Steuererklärung (alt)') — but skip every other active system language (en_GB, fr_CH,
--      it_CH, and any other language a customer DB may have activated).
--   3. Re-link window 359 to a dedicated legacy AD_Element 584857.
--   4. Insert AD_Window_Trl rows for the new window 542146 (IsTranslated='N', initial Name 'Tax Declaration')
--      for every active system language.
--   5. At the end call update_trl_tables_on_ad_element_trl_update(2862, NULL) which propagates
--      AD_Element_Trl[2862].Name into AD_Window_Trl rows linked to element 2862 — by then only
--      window 542146.
--
-- Step 5 sets AD_Window_Trl[542146, <lang>].Name='Steuererklärung' (or its translated equivalent)
-- for every active system language. On customer DBs that carry vintage stub rows
-- AD_Window_Trl[359, <other-lang>].Name='Steuererklärung' (IsTranslated='N', auto-copied when the
-- language was first activated), the COMMIT-time check of the deferred unique constraint
-- ad_window_trl_name_uc collides and rolls 5802040 back.
--
-- Fix: pre-clean any window 359 AD_Window_Trl row whose Name='Steuererklärung' for every active
-- language, using the same naming split as step 2 of 5802040 (de_* → 'Steuererklärung (alt)',
-- else → 'Tax Declaration (legacy)'). Language-agnostic WHERE clause covers every customer DB
-- regardless of which extra languages they activated. Idempotent on DBs where step 2 already
-- cleaned the standard languages (UPDATE matches 0 rows there).
--
-- Prefix 5802039 sub-slot exception (last digit 1–9 for forced ordering, per the metasfresh
-- migration-script naming convention): sorts immediately before 5802040 so this cleanup is
-- guaranteed to apply first. 5802040 then performs its own renames + element re-link +
-- propagation against a clean state, and its COMMIT passes.

UPDATE AD_Window_Trl
SET    Name      = CASE
                       WHEN AD_Language LIKE 'de_%' THEN 'Steuererklärung (alt)'
                       ELSE 'Tax Declaration (legacy)'
                   END,
       Updated   = TIMESTAMP '2026-05-28 12:00:00',
       UpdatedBy = 100
WHERE  AD_Window_ID = 359
  AND  Name = 'Steuererklärung'
;
