-- 2024-06-17T08:47:52.167Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541723, 'S', TO_TIMESTAMP('2024-06-17 08:47:52.056000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'If enabled, the EDI document will be computed for each shipment.', 'de.metas.esb.edi', 'Y', 'de.metas.edi.OneDesadvPerShipment',
        TO_TIMESTAMP('2024-06-17 08:47:52.056000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'N')
;

-- 2024-06-20T10:00:01.933Z
UPDATE AD_SysConfig
SET Description='If enabled, the EDI document will be computed for each shipment. Note: when the sys config is enabled, the ''EXP_M_InOut_Desadv_V'' EXP_Format must be manually activated and the default ''EDI_Exp_Desadv'' inactivated.',
    Updated=TO_TIMESTAMP('2024-06-20 10:00:01.933000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_SysConfig_ID = 541723
;