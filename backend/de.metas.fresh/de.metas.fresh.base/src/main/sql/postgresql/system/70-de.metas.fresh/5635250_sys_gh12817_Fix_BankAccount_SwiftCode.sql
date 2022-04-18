
UPDATE AD_Column
SET ColumnName ='SwiftCode'
WHERE AD_Column_ID = 575110
;


/* DDL */ SELECT public.db_alter_table('Revolut_Payment_Export','ALTER TABLE public.Revolut_Payment_Export ADD COLUMN SwiftCode VARCHAR(20)')
;