-- Run mode: SWING_CLIENT

-- Column: C_Invoice.EDI_ExportStatus
-- 2025-05-29T05:58:48.523Z
-- old DefaultValue='P' (IsEdiEnabled DefaultValue='N')
UPDATE AD_Column SET DefaultValue='N', TechnicalNote='Keep DefaultValue in sync with IsEdiEnabled',Updated=TO_TIMESTAMP('2025-05-29 05:58:48.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=548468
;

-- Column: M_InOut.EDI_ExportStatus
-- 2025-05-29T05:59:40.458Z
-- old DefaultValue='P' (IsEdiEnabled DefaultValue='N')
UPDATE AD_Column SET DefaultValue='N', TechnicalNote='Keep DefaultValue in sync with IsEdiEnabled',Updated=TO_TIMESTAMP('2025-05-29 05:59:40.458000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549871
;

