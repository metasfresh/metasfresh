
INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby,
                          value, modularcontracthandlertype, columnname)
SELECT 1000000, 1000000, '2024-05-28 11:15:39.851000 +00:00', 100, NULL, 'Y',
        540024, 'UserElementNumber1 - AverageAVOnShippedQtyComputingMethod', '2024-05-29 06:42:51.106000 +00:00',
        100, 'UserElementNumber1 - AverageAVOnShippedQtyComputingMethod', 'AverageAddedValueOnShippedQuantity',
        'UserElementNumber1'
WHERE NOt exists (select 1 from modcntr_type where modcntr_type_id = 540024)
;

INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated,
                          updatedby, value, modularcontracthandlertype, columnname)
SELECT 1000000, 1000000,
        '2024-05-29 06:43:09.348000 +00:00',
        100, NULL, 'Y', 540025, 'UserElementNumber2 - AverageAVOnShippedQtyComputingMethod',
        '2024-05-29 06:43:09.348000 +00:00', 100, 'UserElementNumber2 - AverageAVOnShippedQtyComputingMethod',
        'AverageAddedValueOnShippedQuantity', 'UserElementNumber2'
WHERE NOt exists (select 1 from modcntr_type where modcntr_type_id = 540025)
;