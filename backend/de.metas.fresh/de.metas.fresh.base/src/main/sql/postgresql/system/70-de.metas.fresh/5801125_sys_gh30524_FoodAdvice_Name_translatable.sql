-- gh30524 pre-fix (runs before 5801130, which calls the DB-wide add_missing_translations()).
--
-- add_missing_translations() sweeps every *_Trl table. For M_FoodAdvice it hit three separate
-- defects left over from the table's creation (5643160_sys_gh13145_Food_Advice_Table.sql):
--
--   1. M_FoodAdvice.Name (AD_Column 583323, AD_Table 542165) was flagged IsTranslated='N', so
--      the generated INSERT omitted "name" and inserted NULL -> not-null violation on _Trl.Name.
--   2. M_FoodAdvice_Trl.Name is NOT NULL while the base M_FoodAdvice.Name is nullable
--      (VARCHAR(40)), so even selecting t.name yields NULL for base rows without a name.
--   3. M_FoodAdvice_Trl's primary key is (AD_Language) instead of (M_FoodAdvice_ID, AD_Language),
--      because the _Trl's M_FoodAdvice_ID column (AD_Column 583336, AD_Table 542166) was created
--      with IsKey='N'/IsParent='N'/IsMandatory='N' and a nullable physical column. That single-
--      column PK allows only ONE translation row per language across ALL food-advice records, so
--      the helper's second per-record INSERT for a language fails with a duplicate-key error.
--
-- Repair all three so the translation table behaves like a normal _Trl table.

--
-- 1. Name column: make it translatable and align nullability with the base column.
--
UPDATE AD_Column SET IsTranslated='Y', Updated=TIMESTAMP '2026-05-06 17:25:00', UpdatedBy=100
WHERE AD_Table_ID=542165 AND ColumnName='Name';

UPDATE AD_Column SET IsMandatory='N', Updated=TIMESTAMP '2026-05-06 17:25:00', UpdatedBy=100
WHERE AD_Table_ID=542166 AND ColumnName='Name';

SELECT db_alter_table('M_FoodAdvice_Trl', 'ALTER TABLE public.M_FoodAdvice_Trl ALTER COLUMN Name DROP NOT NULL');

--
-- 2. Primary key: replace (AD_Language) with the correct composite (M_FoodAdvice_ID, AD_Language).
--

-- 2a. Remove rows that cannot participate in the composite key: translations with no parent
--     record (M_FoodAdvice_ID is NULL, or points to a non-existent M_FoodAdvice). With the old
--     (AD_Language)-only PK every remaining row is already unique per language, hence unique per
--     (M_FoodAdvice_ID, AD_Language); the helper re-creates any legitimately missing rows.
DELETE FROM M_FoodAdvice_Trl WHERE M_FoodAdvice_ID IS NULL;
DELETE FROM M_FoodAdvice_Trl t WHERE NOT EXISTS (SELECT 1 FROM M_FoodAdvice a WHERE a.M_FoodAdvice_ID = t.M_FoodAdvice_ID);

-- 2b. Dictionary: make M_FoodAdvice_ID the parent key of the translation table.
UPDATE AD_Column SET IsKey='Y', IsParent='Y', IsMandatory='Y', Updated=TIMESTAMP '2026-05-06 17:25:00', UpdatedBy=100
WHERE AD_Table_ID=542166 AND ColumnName='M_FoodAdvice_ID';

-- 2c. Physical schema: enforce NOT NULL, then swap the single-column PK for the composite key.
SELECT db_alter_table('M_FoodAdvice_Trl', 'ALTER TABLE public.M_FoodAdvice_Trl ALTER COLUMN M_FoodAdvice_ID SET NOT NULL');
SELECT db_alter_table('M_FoodAdvice_Trl', 'ALTER TABLE public.M_FoodAdvice_Trl DROP CONSTRAINT IF EXISTS M_FoodAdvice_Trl_Key');
SELECT db_alter_table('M_FoodAdvice_Trl', 'ALTER TABLE public.M_FoodAdvice_Trl ADD CONSTRAINT M_FoodAdvice_Trl_Key PRIMARY KEY (M_FoodAdvice_ID, AD_Language)');
