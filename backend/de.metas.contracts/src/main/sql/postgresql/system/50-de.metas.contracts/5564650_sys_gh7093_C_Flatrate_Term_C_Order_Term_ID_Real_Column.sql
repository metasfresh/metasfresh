

CREATE TABLE backup.BKP_C_Flatrate_Term_04082020 
AS
SELECT * FROM C_Flatrate_Term;






-- 2020-08-04T11:13:21.686Z
-- #298 changing anz. stellen
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2020-08-04 14:13:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547320
;

-- 2020-08-04T11:13:25.896Z
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Term','ALTER TABLE public.C_Flatrate_Term ADD COLUMN C_Order_Term_ID NUMERIC(10)')
;

UPDATE C_Flatrate_Term term
SET C_Order_Term_ID = (SELECT ol.C_Order_ID
                       FROM C_OrderLine ol
                       WHERE ol.C_OrderLine_ID = term.C_OrderLine_Term_ID)
WHERE term.C_OrderLine_Term_ID IS NOT NULL
  AND term.C_Order_Term_ID IS NULL;