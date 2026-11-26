-- nShift: add M_Shipper_Mapping_Config row for TopLevelType (HU unit type LU/TU/CU)
-- Enables emitting the HU type as nShift line reference kind 135 on both ship and advise requests.
-- Mirrors the shape of 5807360_sys_shipper_mapping_incoterms_extsys_config.sql.
--
-- Prerequisite: 5807350_sys_shipper_mapping_incoterms_extsys_reflist.sql adds AD_Ref_List
-- Value='TopLevelType' to ref 542001 (alongside IncotermsValue/ExternalSystemValue); 5807350 < 5807810
-- so it applies first.
--
-- IDs allocated from idserver.metas.de:
--   M_Shipper_Mapping_Config_ID: 540026 (TopLevelType)

-- M_Shipper_Mapping_Config: TopLevelType (LineReference kind=135, SeqNo=220)
-- 2026-06-15T10:01:00.000Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID, AD_Org_ID, M_Shipper_Mapping_Config_ID,
                                      Created, CreatedBy, IsActive,
                                      M_Shipper_ID, MappingAttributeKey, MappingAttributeType, MappingAttributeValue,
                                      SeqNo, Updated, UpdatedBy)
VALUES (1000000, 1000000, 540026 /*From ID Server*/,
        TO_TIMESTAMP('2026-06-15 10:01:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Y',
        540019, '135', 'LineReference', 'TopLevelType',
        220,
        TO_TIMESTAMP('2026-06-15 10:01:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;
