-- me03#29671 pre-fix (runs before 5801130, which calls the DB-wide add_missing_translations()).
--
-- add_missing_translations() sweeps every *_Trl table and, for M_FoodAdvice, produced:
--   ERROR: null value in column "name" of relation "m_foodadvice_trl" violates not-null constraint
--
-- Root cause is a pair of inconsistencies left over from the M_FoodAdvice table creation
-- (5643160_sys_gh13145_Food_Advice_Table.sql):
--   1. M_FoodAdvice.Name (AD_Column 583323, AD_Table 542165) was flagged IsTranslated='N',
--      so the helper's generated INSERT omitted "name" and inserted NULL.
--   2. M_FoodAdvice_Trl.Name is NOT NULL while the base M_FoodAdvice.Name is nullable
--      (VARCHAR(40)), so even selecting t.name yields NULL for any base row without a name.
--
-- Fix both so the helper can populate M_FoodAdvice_Trl regardless of the data.

-- 1. Mark the base Name column translatable so add_missing_translations() SELECTs t.name
--    into the generated INSERT (the M_FoodAdvice_Trl table exists precisely to hold these).
UPDATE AD_Column SET IsTranslated='Y', Updated=TIMESTAMP '2026-05-06 17:25:00', UpdatedBy=100
WHERE AD_Table_ID=542165 AND ColumnName='Name';

-- 2. Align the translation column with the nullable base column so untranslated rows
--    (base Name IS NULL) can be inserted. Clear IsMandatory in the dictionary first so the
--    framework/model no longer treats it as required, then drop the physical NOT NULL.
UPDATE AD_Column SET IsMandatory='N', Updated=TIMESTAMP '2026-05-06 17:25:00', UpdatedBy=100
WHERE AD_Table_ID=542166 AND ColumnName='Name';

SELECT db_alter_table('M_FoodAdvice_Trl', 'ALTER TABLE public.M_FoodAdvice_Trl ALTER COLUMN Name DROP NOT NULL');
