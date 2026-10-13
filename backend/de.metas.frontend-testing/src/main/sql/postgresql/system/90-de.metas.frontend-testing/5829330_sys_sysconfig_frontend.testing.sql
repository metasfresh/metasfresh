-- Run mode: SWING_CLIENT

-- SysConfig Name: frontend.testing
-- SysConfig Value: N
-- Master switch for the frontend-testing REST endpoints (test-only masterdata creation). Default 'N' = endpoints refuse every call.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541859 /*From ID Server*/,'S',TO_TIMESTAMP('2026-10-13 09:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Enables the frontend testing (playwright) supporting REST endpoints. Must stay N on productive systems.','D','Y','frontend.testing',TO_TIMESTAMP('2026-10-13 09:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;
