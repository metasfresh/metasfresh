-- 2018-01-31T14:12:24.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN M_Product_PlanningSchema_ID NUMERIC(10)')
;

-- 2018-01-31T14:12:24.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PP_Product_Planning ADD CONSTRAINT MProductPlanningSchema_PPProdu FOREIGN KEY (M_Product_PlanningSchema_ID) REFERENCES public.M_Product_PlanningSchema DEFERRABLE INITIALLY DEFERRED
;

