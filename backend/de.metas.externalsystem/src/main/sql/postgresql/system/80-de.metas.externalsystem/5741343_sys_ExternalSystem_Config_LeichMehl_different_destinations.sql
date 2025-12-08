
-- Reference: PluFileDestination
-- Value: 2Dsk
-- ValueName: Disk
-- 2024-12-12T16:05:13.027Z
UPDATE AD_Ref_List SET Value='2Dsk',Updated=TO_TIMESTAMP('2024-12-12 16:05:13.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543780
;

-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-12T16:11:14.647Z
UPDATE AD_Column SET DefaultValue='', TechnicalNote='',Updated=TO_TIMESTAMP('2024-12-12 16:11:14.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589480
;

-- Field: External system config Leich + Mehl -> External system config Leich + Mehl -> Server-Verzeichnis
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- Field: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> Server-Verzeichnis
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-12T16:12:34.743Z
UPDATE AD_Field SET DisplayLogic='@PluFileDestination/1TCP@=2DSK',Updated=TO_TIMESTAMP('2024-12-12 16:12:34.742000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734078
;

-- Field: Externes System -> External system config Leich + Mehl -> Server-Verzeichnis
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- Field: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> Server-Verzeichnis
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-12T16:13:27.250Z
UPDATE AD_Field SET DisplayLogic='@PluFileDestination/1TCP@=2DSK',Updated=TO_TIMESTAMP('2024-12-12 16:13:27.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734082
;

-- Reference: PluFileDestination
-- Value: 2DSK
-- ValueName: Disk
-- 2024-12-12T16:19:50.269Z
UPDATE AD_Ref_List SET Value='2DSK',Updated=TO_TIMESTAMP('2024-12-12 16:19:50.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543780
;

