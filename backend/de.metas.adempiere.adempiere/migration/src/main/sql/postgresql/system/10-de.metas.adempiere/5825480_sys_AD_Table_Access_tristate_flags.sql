-- 2026-09-21
-- AD_Table_Access: convert IsExclude / IsReadOnly / IsCanReport / IsCanExport to three-state.
--
-- Shape copied from the established precedent C_BPartner.IsAutoInvoice (migration 5776320):
-- a nullable CHAR(1) column with no DB default and no CHECK constraint, exposed in the
-- application dictionary as AD_Reference_ID=17 (List) over AD_Reference_Value_ID=319 (_YesNo).
--   NULL      = the role expresses no opinion on this aspect
--   'Y' / 'N' = the opinion was set explicitly
-- AD_Column.IsMandatory must always match the physical NOT NULL, so it flips together with it.
--
-- No data migration is required: the script only relaxes constraints and defaults, it never
-- rewrites an existing 'Y' / 'N' value. (The table also held zero rows on every local stack
-- checked on 2026-09-21.)

-- 1. Drop the two-valued CHECK constraints.
--    They do not by themselves reject NULL (a CHECK is satisfied when it evaluates to NULL),
--    but the target shape carries no CHECK at all and a constraint named after a two-state
--    invariant no longer describes the column.
SELECT db_alter_table('AD_Table_Access', 'ALTER TABLE public.AD_Table_Access DROP CONSTRAINT IF EXISTS ad_table_access_isexclude_check');
SELECT db_alter_table('AD_Table_Access', 'ALTER TABLE public.AD_Table_Access DROP CONSTRAINT IF EXISTS ad_table_access_isreadonly_check');
SELECT db_alter_table('AD_Table_Access', 'ALTER TABLE public.AD_Table_Access DROP CONSTRAINT IF EXISTS ad_table_access_iscanreport_check');
SELECT db_alter_table('AD_Table_Access', 'ALTER TABLE public.AD_Table_Access DROP CONSTRAINT IF EXISTS ad_table_access_iscanexport_check');

-- 2. Make the physical columns nullable and drop their DB defaults.
--    t_alter_column reads the literal string 'null' for the null clause as "drop not null"
--    and for the default clause as "drop default"; a SQL NULL there is a no-op.
--    Dropping the default matters as much as dropping NOT NULL: a default would re-assert
--    an opinion on every new row, which is exactly what the third state exists to avoid.
INSERT INTO t_alter_column values('AD_Table_Access','IsExclude','CHAR(1)','null','null');
INSERT INTO t_alter_column values('AD_Table_Access','IsReadOnly','CHAR(1)','null','null');
INSERT INTO t_alter_column values('AD_Table_Access','IsCanReport','CHAR(1)','null','null');
INSERT INTO t_alter_column values('AD_Table_Access','IsCanExport','CHAR(1)','null','null');

-- 3. Application dictionary: List(17) over the _YesNo reference (319), no longer mandatory.
UPDATE AD_Column
   SET AD_Reference_ID       = 17,
       AD_Reference_Value_ID = 319,
       IsMandatory           = 'N',
       Updated               = TO_TIMESTAMP('2026-09-21 09:00:01','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy             = 100
 WHERE ColumnName IN ('IsExclude','IsReadOnly','IsCanReport','IsCanExport')
   AND AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName='AD_Table_Access');

-- 4. Remove IsExclude's dictionary default.
--    It read '@SQL=SELECT getDefaultValue_Column(p_AD_Column_ID => 8844)', which on an instance
--    carrying the AD_DefaultValue table resolved to 'Y'. That table is not part of metasfresh core -
--    it is created by a support-module migration, so a freshly built database does not have it - and
--    a core migration must therefore not touch it. It does not need to: nulling DefaultValue here is
--    what removes the default. The '@SQL=' string is the only thing that ever calls
--    getDefaultValue_Column for this column, so once it is gone the row behind it, where one exists
--    at all, is unreachable and can no longer reach a new AD_Table_Access row. Same reasoning as the
--    DB default in step 2: a default would give every new row an opinion it was never meant to carry.
UPDATE AD_Column
   SET DefaultValue = NULL,
       Updated      = TO_TIMESTAMP('2026-09-21 09:00:02','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE ColumnName = 'IsExclude'
   AND AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName='AD_Table_Access');
