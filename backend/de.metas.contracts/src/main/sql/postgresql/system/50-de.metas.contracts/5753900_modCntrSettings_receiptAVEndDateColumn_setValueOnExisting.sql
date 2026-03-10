
UPDATE modcntr_settings
SET receiptavenddate = TO_TIMESTAMP('2024-08-15 14:35:26.907','YYYY-MM-DD HH24:MI:SS.US'),
    updatedby = 99,
    updated = TO_TIMESTAMP('2025-05-08 14:35:26.907','YYYY-MM-DD HH24:MI:SS.US')
WHERE receiptavenddate IS NULL
;

