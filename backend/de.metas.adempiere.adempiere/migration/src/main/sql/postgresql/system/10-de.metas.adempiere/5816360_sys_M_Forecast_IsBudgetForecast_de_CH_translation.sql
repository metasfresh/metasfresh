-- Fix for 5816340_sys_M_Forecast_IsBudgetForecast.sql: the de_CH AD_Element_Trl / AD_Field_Trl
-- rows for IsBudgetForecast were marked IsTranslated='Y' without carrying the de_DE
-- Description/Help text (only Name/PrintName were seeded). de_CH mirrors de_DE here (no
-- Swiss-specific wording needed for this term).

-- Element: IsBudgetForecast (de_CH) -- mirror de_DE Description/Help
-- 2026-07-27T14:30:00.000Z
UPDATE AD_Element_Trl SET Description='Kennzeichnet diese Prognose als Budgetprognose',Help='Wenn aktiv, handelt es sich bei dieser Prognose um eine Budgetprognose.',Updated=TO_TIMESTAMP('2026-07-27 14:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585136 AND AD_Language='de_CH'
;

-- 2026-07-27T14:30:00.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585136,'de_CH')
;

-- 2026-07-27T14:30:00.200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585136)
;
