-- UI Element: Effort control(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 20 -> flags.Processed
-- Column: S_EffortControl.IsProcessed
-- 2022-10-11T07:06:39.229Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612991
;

-- 2022-10-11T07:06:39.262Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707288
;

-- Field: Effort control(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Processed
-- Column: S_EffortControl.IsProcessed
-- 2022-10-11T07:06:39.279Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707288
;

-- 2022-10-11T07:06:39.288Z
DELETE FROM AD_Field WHERE AD_Field_ID=707288
;

-- 2022-10-11T07:06:39.370Z
/* DDL */ SELECT public.db_alter_table('S_EffortControl','ALTER TABLE S_EffortControl DROP COLUMN IF EXISTS IsProcessed')
;

-- Column: S_EffortControl.IsProcessed
-- 2022-10-11T07:06:39.537Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584347
;

-- 2022-10-11T07:06:39.555Z
DELETE FROM AD_Column WHERE AD_Column_ID=584347
;


-- Column: S_EffortControl.IsIssueClosed
-- Column SQL (old): (coalesce((select case when max(Processed) = min(Processed) and max(Processed) = 'Y' then 'Y' else 'N' end                   from S_Issue iss                   where iss.IsEffortIssue = 'Y'                     and iss.C_Activity_ID = S_EffortControl.C_Activity_ID                     and iss.C_Project_ID = S_EffortControl.C_Project_ID                     and iss.AD_Org_ID = S_EffortControl.AD_Org_ID                   group by iss.C_Activity_ID, iss.C_Project_ID, iss.AD_Org_ID), 'N'))
-- 2022-10-14T07:39:33.016Z
UPDATE AD_Column SET ColumnSQL='(coalesce((select case when max(Processed) = min(Processed) and max(Processed) = ''Y'' then ''Y'' else ''N'' end                   from S_Issue iss                   where iss.C_Activity_ID = S_EffortControl.C_Activity_ID                     and iss.C_Project_ID = S_EffortControl.C_Project_ID                     and iss.AD_Org_ID = S_EffortControl.AD_Org_ID                   group by iss.C_Activity_ID, iss.C_Project_ID, iss.AD_Org_ID), ''N''))',Updated=TO_TIMESTAMP('2022-10-14 10:39:33.015','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584346
;

