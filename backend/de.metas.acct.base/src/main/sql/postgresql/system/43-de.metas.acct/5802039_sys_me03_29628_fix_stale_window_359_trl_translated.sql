-- Fixes the remaining gap left by `5802039_sys_me03_29628_fix_stale_window_359_trl.sql`
-- (merged in https://github.com/metasfresh/metasfresh/pull/24322) when applied against customer
-- DBs whose stale window-359 stubs have already been TRANSLATED away from 'Steuererklärung'.
--
-- Original 5802039 used `WHERE AD_Window_ID = 359 AND Name = 'Steuererklärung'`. That covers
-- untranslated stubs (IsTranslated='N', auto-copied German default at language activation), but
-- skips stubs that a customer has since translated to the target language. A customer whose
-- window-359 stubs were already translated to the target language (e.g. fr_CH = 'déclaration
-- d'impôts', en_GB = 'Tax Declaration', it_CH = 'dichiarazione fiscale') hit the collision:
-- `5802040_sys_me03_29628_window.sql`'s closing `update_trl_tables_on_ad_element_trl_update(2862, NULL)`
-- tried to write the same translated Name that already existed on the stale window-359 row,
-- colliding at COMMIT on the deferred unique constraint `ad_window_trl_name_uc`:
--
--   ERROR:  duplicate key value violates unique constraint "ad_window_trl_name_uc"
--   DETAIL: Key (name, ad_language)=(déclaration d'impôts, fr_CH) already exists.
--
-- The blind spot applies to any language a customer has already translated the legacy window into.
-- See https://github.com/metasfresh/me03/issues/29628
--
-- Fix: complete the rename for window 359 across ALL non-(en_US/de_DE/de_CH) languages regardless
-- of current Name. The three covered languages are already handled by 5802040's own renames (per
-- the comment in 5802040 — en_US → 'Tax Declaration (legacy)', de_DE / de_CH → 'Steuererklärung (alt)').
-- Window 359 is being deprecated; overwriting the Name to its legacy marker is correct regardless
-- of whatever the row used to say.
--
-- NOTE on shared numeric prefix: this file intentionally shares the `5802039` prefix with
-- `5802039_sys_me03_29628_fix_stale_window_359_trl.sql`. There is no integer between 5802039
-- and 5802040, so a unique prefix that still sorts before 5802040 is impossible. Both scripts
-- must execute before 5802040 (which triggers the constraint collision). Lexicographic sort
-- guarantees: `_trl.sql` < `_trl_translated.sql` < `5802040_`. AD_MigrationScript tracking
-- keys on the full filename, so both entries are distinct and tracked independently.
--
-- Idempotent: matches 0 rows on DBs where the original 5802039 (or 5802040 itself, on a re-run)
-- has already cleaned the stubs.

UPDATE AD_Window_Trl
SET    Name      = CASE
                       WHEN AD_Language LIKE 'de_%' THEN 'Steuererklärung (alt)'
                       ELSE 'Tax Declaration (legacy)'
                   END,
       Updated   = TIMESTAMP '2026-05-28 18:00:00',
       UpdatedBy = 100
WHERE  AD_Window_ID = 359
  AND  AD_Language NOT IN ('en_US', 'de_DE', 'de_CH')
;
