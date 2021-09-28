-- 2021-08-17T07:56:43.179Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_Ingredients','ALTER TABLE public.M_Ingredients ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2021-08-17T07:56:43.503Z
-- URL zum Konzept
ALTER TABLE M_Ingredients ADD CONSTRAINT MProduct_MIngredients FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;