-- UI Element: Effort control(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 20 -> flags.Over buget
-- Column: S_EffortControl.IsOverBuget
-- 2022-09-15T07:46:52.553Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612989
;

-- 2022-09-15T07:47:01.547Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707286
;

-- Field: Effort control(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Over buget
-- Column: S_EffortControl.IsOverBuget
-- 2022-09-15T07:47:01.561Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707286
;

-- 2022-09-15T07:47:01.579Z
DELETE FROM AD_Field WHERE AD_Field_ID=707286
;

-- 2022-09-15T07:47:23.859Z
/* DDL */ SELECT public.db_alter_table('S_EffortControl','ALTER TABLE S_EffortControl DROP COLUMN IF EXISTS IsOverBuget')
;

-- Column: S_EffortControl.IsOverBuget
-- 2022-09-15T07:47:23.956Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584345
;

-- 2022-09-15T07:47:23.960Z
DELETE FROM AD_Column WHERE AD_Column_ID=584345
;

-- 2022-09-15T07:47:32.529Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=581454
;

-- 2022-09-15T07:47:32.542Z
DELETE FROM AD_Element WHERE AD_Element_ID=581454
;

