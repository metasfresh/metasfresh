-- 2024-06-05T07:25:48.332Z
UPDATE AD_Message SET MsgText='Erstellt: {0} Datensätze für Vertragsperiode {1,date} bis {2,date}. ',Updated=TO_TIMESTAMP('2024-06-05 09:25:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=540257
;

-- Column: C_Flatrate_Conditions.C_UOM_ID
-- 2024-06-05T07:38:48.844Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2024-06-05 09:38:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545655
;

-- Column: C_Flatrate_Term.C_UOM_ID
-- 2024-06-05T07:40:40.136Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2024-06-05 09:40:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546003
;

-- delete C_Flatrate_Conditions.UOMType
delete from ad_ui_element where ad_field_id in (select ad_field_id from ad_field where ad_column_id=545817);
delete from ad_field where ad_column_id=545817;
delete from ad_column where ad_column_id=545817;

-- delete C_Flatrate_Term.UOMType (sql-column)
delete from ad_ui_element where ad_field_id in (select ad_field_id from ad_field where ad_column_id=545640);
delete from ad_field where ad_column_id=545640;
delete from ad_column where ad_column_id=545640;

alter table c_flatrate_conditions drop column uomtype;
