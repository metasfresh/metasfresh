-- Correction for 5816340_sys_M_Forecast_IsBudgetForecast.sql: the de_DE and de_CH
-- AD_Element_Trl / AD_Field_Trl rows for IsBudgetForecast were left IsTranslated='Y'.
-- Convention for EntityType 'D' elements is de_DE/de_CH IsTranslated='N' (the base-language
-- text already carries German, so no separate translation is tracked) and en_US IsTranslated='Y'.
-- en_US is already correct and is left untouched here.

-- Element: IsBudgetForecast (de_DE, de_CH) -- flip IsTranslated to 'N' per convention
-- 2026-07-27T15:00:00.000000Z
UPDATE AD_Element_Trl SET IsTranslated='N', Updated=TO_TIMESTAMP('2026-07-27 15:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH')
  AND AD_Element_ID IN (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName='IsBudgetForecast')
;

-- Field: Budgetprognose (de_DE, de_CH) -- flip IsTranslated to 'N' per convention
-- 2026-07-27T15:00:01.000000Z
UPDATE AD_Field_Trl SET IsTranslated='N', Updated=TO_TIMESTAMP('2026-07-27 15:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH')
  AND AD_Field_ID IN (
      SELECT f.AD_Field_ID
      FROM AD_Field f
      JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID
      WHERE c.ColumnName = 'IsBudgetForecast'
  )
;
