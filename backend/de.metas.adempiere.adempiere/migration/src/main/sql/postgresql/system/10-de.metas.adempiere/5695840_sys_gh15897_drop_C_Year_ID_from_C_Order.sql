-- 2023-07-14T11:07:05.484574400Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE C_Order DROP COLUMN IF EXISTS C_Year_ID')
;

-- Column: C_Order.C_Year_ID
-- 2023-07-14T11:07:07.471244300Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586895
;

-- 2023-07-14T11:07:07.477752800Z
DELETE FROM AD_Column WHERE AD_Column_ID=586895
;



-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Produkt
-- Column: C_Flatrate_Term.M_Product_ID
-- 2023-07-14T11:13:30.006121300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,559779,0,540859,618256,541106,'F',TO_TIMESTAMP('2023-07-14 14:13:29.853','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',70,0,0,TO_TIMESTAMP('2023-07-14 14:13:29.853','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Produkt
-- Column: C_Flatrate_Term.M_Product_ID
-- 2023-07-17T06:12:04.630425900Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''X''@=''Subscr''|@Type_Conditions/''X''@=''QualityBsd''|@Type_Conditions/''X''@=''Procuremnt''|@Type_Conditions/''X''@=''Refund''|@Type_Conditions/''X''@=''Commission''|@Type_Conditions/''X''@=''MediatedCommission''|@Type_Conditions/''X''@=''MarginCommission''|@Type_Conditions/''X''@=''ModularContract''',Updated=TO_TIMESTAMP('2023-07-17 09:12:04.63','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=559779
;



