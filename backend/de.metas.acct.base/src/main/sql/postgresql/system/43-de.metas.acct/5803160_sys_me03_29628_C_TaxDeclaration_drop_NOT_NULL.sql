-- Tax Declaration Iter4: drop NOT NULL + IsMandatory on DateFrom/DateTo/DateTrx
-- These legacy date-range columns are hidden from the UI (5803040) and replaced
-- by C_Period_ID + DateAcct. The physical NOT NULL prevented saving new records.

-- 1. AD_Column: clear IsMandatory so framework no longer demands them
UPDATE AD_Column SET IsMandatory='N', Updated=TIMESTAMP '2026-05-19 00:00:00', UpdatedBy=100
WHERE AD_Table_ID=818 AND ColumnName IN ('DateFrom','DateTo','DateTrx');

-- 2. Physical DDL: drop NOT NULL on the columns
SELECT db_alter_table('C_TaxDeclaration', 'ALTER TABLE public.C_TaxDeclaration ALTER COLUMN DateFrom DROP NOT NULL');
SELECT db_alter_table('C_TaxDeclaration', 'ALTER TABLE public.C_TaxDeclaration ALTER COLUMN DateTo DROP NOT NULL');
SELECT db_alter_table('C_TaxDeclaration', 'ALTER TABLE public.C_TaxDeclaration ALTER COLUMN DateTrx DROP NOT NULL');
