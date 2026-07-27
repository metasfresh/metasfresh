-- Fix for 5816340_sys_M_Forecast_IsBudgetForecast.sql: AD_Field.Help (the base-language column
-- on AD_Field itself, distinct from AD_Field_Trl) was never populated for AD_Field_ID=781847.
-- update_FieldTranslation_From_AD_Name_Element() only syncs Name+Description to the AD_Field
-- base column, never Help (known pitfall; see metasfresh-application-dictionary skill,
-- and precedent fix 5794340_sys_gh28675_fix_forecast_field_descriptions_de_DE.sql on this
-- same Forecast window). AD_Field_Trl already carries the correct Help text for all three
-- languages (set directly by 5816340's AD_Field_Trl seed + de_CH follow-up); only the base
-- AD_Field.Help column needs the direct UPDATE.

-- 2026-07-27T14:45:00.000Z
UPDATE AD_Field SET Help='Wenn aktiv, handelt es sich bei dieser Prognose um eine Budgetprognose.',Updated=TO_TIMESTAMP('2026-07-27 14:45:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=781847
;
