-- Run mode: SWING_CLIENT

-- HU Manager mobile app - per-role action "createHU" (create an HU from a scanned product label).
-- Mobile_Application_ID 540005 = HU Manager (same as the other HU Manager actions, e.g. scanGRAI = 540007).
-- Mobile_Application_Action_ID from the ID server.
INSERT INTO Mobile_Application_Action (AD_Client_ID,AD_Org_ID,Created,CreatedBy,InternalName,IsActive,Mobile_Application_Action_ID,Mobile_Application_ID,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'createHU','Y',540008/*From ID Server*/,540005,TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
