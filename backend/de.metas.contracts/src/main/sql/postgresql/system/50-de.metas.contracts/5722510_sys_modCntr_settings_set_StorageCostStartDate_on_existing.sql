UPDATE modcntr_settings
SET storageCostStartDate = TO_TIMESTAMP('2024-04-23 14:35:26.907','YYYY-MM-DD HH24:MI:SS.US'),
    updatedby = 99,
    updated = TO_TIMESTAMP('2024-04-23 14:35:26.907','YYYY-MM-DD HH24:MI:SS.US')
WHERE storageCostStartDate IS NULL
;

