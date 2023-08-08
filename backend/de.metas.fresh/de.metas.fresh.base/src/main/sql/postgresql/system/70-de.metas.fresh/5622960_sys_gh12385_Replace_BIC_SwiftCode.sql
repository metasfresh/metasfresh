
UPDATE AD_Column
SET ColumnName ='SwiftCode'
WHERE AD_Column_ID = 575110
;

-- 2022-01-25T08:48:53.893Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('Revolut_Payment_Export','ALTER TABLE public.Revolut_Payment_Export ADD COLUMN SwiftCode VARCHAR(20)')
;

UPDATE Revolut_Payment_Export
SET SwiftCode = BIC
;

ALTER TABLE Revolut_Payment_Export
DROP COLUMN BIC
;
