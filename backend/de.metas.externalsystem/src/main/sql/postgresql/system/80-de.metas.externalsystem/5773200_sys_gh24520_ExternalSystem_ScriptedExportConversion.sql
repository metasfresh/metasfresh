UPDATE externalsystem
SET ad_client_id = 0, Updated=TO_TIMESTAMP('2025-10-13 14:44:18.356000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=99
WHERE externalsystem_id = 540056
;

DELETE
FROM ad_sequence
WHERE ad_sequence_id = 556546
;
