-- SysConfig Name: de.metas.workflow.rest_api.TrolleyService.IncludeHolderNameInConflictError
-- SysConfig Value: N (default)
-- When set to 'Y', the trolley-conflict error message (raised by TrolleyService.setCurrent when
-- another user already holds the scanned trolley) includes the current holder's display name
-- (filled via the {0} placeholder of AD_Message WFRestAPI_TrolleyAlreadyAssignedTo).
-- When 'N' (default), the generic AD_Message WFRestAPI_TrolleyAlreadyAssigned is used (no name).
-- Privacy-safe default; customers opt in per-instance.
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
VALUES (0, 0, 541809 /*From ID Server*/, 'S',
        TO_TIMESTAMP('2026-05-13 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-05-13 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'D', 'Y',
        'de.metas.workflow.rest_api.TrolleyService.IncludeHolderNameInConflictError',
        'N',
        'When Y, the trolley-conflict error message includes the current holder''s name; when N (default), the message is generic.')
;
