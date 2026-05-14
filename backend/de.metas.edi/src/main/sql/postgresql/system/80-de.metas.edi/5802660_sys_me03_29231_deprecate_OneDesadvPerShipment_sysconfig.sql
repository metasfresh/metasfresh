-- Run mode: SWING_CLIENT

-- AD_SysConfig: de.metas.edi.OneDesadvPerShipment
-- Mark as DEPRECATED — the implementation was never completed; use C_BPartner.IsEdiOneEDIDesadvPerShipment instead.
-- 2026-05-14T14:05:00.000Z
UPDATE AD_SysConfig
SET Description='DEPRECATED — setting this to ''Y'' does NOT work; the implementation was never completed. Use C_BPartner.IsEdiOneEDIDesadvPerShipment (per-BPartner, only effective when EdiDESADVSendingMode=''E'') instead. The replication-interface-based EDI export (EdiDESADVSendingMode=''R'') is itself deprecated; new EDI integrations must use the externalsystem-based path.',
    Updated=TO_TIMESTAMP('2026-05-14 14:05:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_SysConfig_ID=541723
;
