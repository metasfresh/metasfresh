-- nShift: add M_Shipper_Mapping_Config rows for IncotermsValue, ExternalSystemValue
-- These enable passing Incoterms and external system value as custom references
-- in the nShift advise/ship request for the default nShift shipper (M_Shipper_ID=540019).
--
-- IDs allocated from idserver.metas.de:
--   M_Shipper_Mapping_Config_ID: 540023 (IncotermsValue)
--   M_Shipper_Mapping_Config_ID: 540024 (ExternalSystemValue)

-- M_Shipper_Mapping_Config: IncotermsValue (Kind=63, SeqNo=200)
-- 2026-06-03T10:01:00.000Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID, AD_Org_ID, M_Shipper_Mapping_Config_ID,
                                      Created, CreatedBy, IsActive,
                                      M_Shipper_ID, MappingAttributeKey, MappingAttributeType, MappingAttributeValue,
                                      SeqNo, Updated, UpdatedBy)
VALUES (1000000, 1000000, 540023 /*From ID Server*/,
        TO_TIMESTAMP('2026-06-03 10:01:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Y',
        540019, '63', 'Reference', 'IncotermsValue',
        200,
        TO_TIMESTAMP('2026-06-03 10:01:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- M_Shipper_Mapping_Config: ExternalSystemValue (Kind=64, SeqNo=210)
-- 2026-06-03T10:01:01.000Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID, AD_Org_ID, M_Shipper_Mapping_Config_ID,
                                      Created, CreatedBy, IsActive,
                                      M_Shipper_ID, MappingAttributeKey, MappingAttributeType, MappingAttributeValue,
                                      SeqNo, Updated, UpdatedBy)
VALUES (1000000, 1000000, 540024 /*From ID Server*/,
        TO_TIMESTAMP('2026-06-03 10:01:01', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Y',
        540019, '64', 'Reference', 'ExternalSystemValue',
        210,
        TO_TIMESTAMP('2026-06-03 10:01:01', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;
