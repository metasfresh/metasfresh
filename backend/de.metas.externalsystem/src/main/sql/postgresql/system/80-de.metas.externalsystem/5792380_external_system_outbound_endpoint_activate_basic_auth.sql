-- Run mode: SWING_CLIENT

-- Reference: ExternalSystem_Outbound_Endpoint_AuthType
-- Value: Basic
-- ValueName: Basic
-- 2026-03-06T10:05:58.281Z
UPDATE AD_Ref_List SET IsActive='Y',Updated=TO_TIMESTAMP('2026-03-06 10:05:58.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544056
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Login User Name
-- Column: ExternalSystem_Outbound_Endpoint.LoginUsername
-- 2026-03-06T10:08:04.952Z
UPDATE AD_Field SET DisplayLogic='(@AuthType/X@=''OAuth'' | @AuthType/X@=''Basic'')',Updated=TO_TIMESTAMP('2026-03-06 10:08:04.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755949
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Kennwort
-- Column: ExternalSystem_Outbound_Endpoint.Password
-- 2026-03-06T10:08:08.032Z
UPDATE AD_Field SET DisplayLogic='(@AuthType/X@=''OAuth'' | @AuthType/X@=''Basic'')',Updated=TO_TIMESTAMP('2026-03-06 10:08:08.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755950
;

