
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




-- 2024-10-30T13:57:02.657Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,579299,0,540168,541984,TO_TIMESTAMP('2024-10-30 15:57:02','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',545802,589361,540320,TO_TIMESTAMP('2024-10-30 15:57:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-30T14:11:09.699Z
UPDATE AD_SQLColumn_SourceTableColumn SET Source_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-10-30 16:11:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540168
;



-- Run mode: SWING_CLIENT

-- UI Element: Abrufauftrag Übersicht(541430,de.metas.contracts) -> Abrufauftrag Übersicht(545426,de.metas.contracts) -> main -> 20 -> ref.Referenz
-- Column: C_CallOrderSummary.POReference
-- 2024-10-30T14:21:02.250Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-10-30 16:21:02.25','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=601376
;

-- UI Element: Abrufauftrag Übersicht(541430,de.metas.contracts) -> Abrufauftrag Übersicht(545426,de.metas.contracts) -> main -> 10 -> default.Produkt
-- Column: C_CallOrderSummary.M_Product_ID
-- 2024-10-30T14:21:02.261Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-10-30 16:21:02.261','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=601366
;

-- UI Element: Abrufauftrag Übersicht(541430,de.metas.contracts) -> Abrufauftrag Übersicht(545426,de.metas.contracts) -> main -> 10 -> sec.Merkmale
-- Column: C_CallOrderSummary.M_AttributeSetInstance_ID
-- 2024-10-30T14:21:02.268Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-10-30 16:21:02.268','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=601369
;

-- UI Element: Abrufauftrag Übersicht(541430,de.metas.contracts) -> Abrufauftrag Übersicht(545426,de.metas.contracts) -> main -> 10 -> default.Verkaufstransaktion
-- Column: C_CallOrderSummary.IsSOTrx
-- 2024-10-30T14:21:02.274Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-10-30 16:21:02.274','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=605243
;

