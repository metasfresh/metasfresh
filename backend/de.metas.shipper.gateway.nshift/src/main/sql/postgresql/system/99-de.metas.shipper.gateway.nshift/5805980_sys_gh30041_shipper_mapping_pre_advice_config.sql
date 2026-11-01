-- nShift: add M_Shipper_Mapping_Config row for IsPreAdviceRequired
-- Enables passing pre-advice flag as custom reference kind=65 in the nShift advise request
-- for the default nShift shipper (M_Shipper_ID=540019).
--
-- IDs allocated from idserver.metas.de:
--   M_Shipper_Mapping_Config_ID: 540025 (IsPreAdviceRequired)

-- M_Shipper_Mapping_Config: IsPreAdviceRequired (Kind=65, SeqNo=220)
-- 2026-06-03T10:01:02.000Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID, AD_Org_ID, M_Shipper_Mapping_Config_ID,
                                      Created, CreatedBy, IsActive,
                                      M_Shipper_ID, MappingAttributeKey, MappingAttributeType, MappingAttributeValue,
                                      SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 540025 /*From ID Server*/,
        TO_TIMESTAMP('2026-06-03 10:01:02', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Y',
        540019, '65', 'Reference', 'IsPreAdviceRequired',
        220,
        TO_TIMESTAMP('2026-06-03 10:01:02', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;
