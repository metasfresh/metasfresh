
/* DDL */ SELECT public.db_alter_table('C_CallOrderSummary','ALTER TABLE public.C_CallOrderSummary DROP COLUMN POReference')
;



-- Column: C_CallOrderSummary.POReference
-- 2024-10-29T15:30:42.166Z
UPDATE AD_Column SET ColumnSQL='(SELECT POReference from C_Flatrate_Term ft where ft.C_Flatrate_Term_ID = C_CallOrderSummary.C_Flatrate_Term_ID)', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-10-29 17:30:42.166','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=579299
;

-- Column: C_CallOrderSummary.POReference
-- Column SQL (old): (SELECT POReference from C_Flatrate_Term ft where ft.C_Flatrate_Term_ID = C_CallOrderSummary.C_Flatrate_Term_ID)
-- 2024-10-29T15:32:39.582Z
UPDATE AD_Column SET ColumnSQL='', IsLazyLoading='N',Updated=TO_TIMESTAMP('2024-10-29 17:32:39.582','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=579299
;

-- Column: C_CallOrderSummary.POReference
-- 2024-10-29T15:32:48.656Z
UPDATE AD_Column SET ColumnSQL='(SELECT POReference from C_Flatrate_Term ft where ft.C_Flatrate_Term_ID = C_CallOrderSummary.C_Flatrate_Term_ID)', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2024-10-29 17:32:48.656','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=579299
;

