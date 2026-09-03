-- Run mode: SWING_CLIENT

-- 2025-09-25T16:17:31.433Z
INSERT INTO Mobile_Application_Action (AD_Client_ID,AD_Org_ID,Created,CreatedBy,InternalName,IsActive,Mobile_Application_Action_ID,Mobile_Application_ID,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2025-09-25 16:17:31.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'dispose','Y',540000,540002,TO_TIMESTAMP('2025-09-25 16:17:31.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T16:17:41.398Z
INSERT INTO Mobile_Application_Action (AD_Client_ID,AD_Org_ID,Created,CreatedBy,InternalName,IsActive,Mobile_Application_Action_ID,Mobile_Application_ID,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2025-09-25 16:17:41.224000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'move','Y',540001,540002,TO_TIMESTAMP('2025-09-25 16:17:41.224000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T16:17:58.524Z
INSERT INTO Mobile_Application_Action (AD_Client_ID,AD_Org_ID,Created,CreatedBy,InternalName,IsActive,Mobile_Application_Action_ID,Mobile_Application_ID,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2025-09-25 16:17:58.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'setClearanceStatus','Y',540002,540002,TO_TIMESTAMP('2025-09-25 16:17:58.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T16:18:17.105Z
INSERT INTO Mobile_Application_Action (AD_Client_ID,AD_Org_ID,Created,CreatedBy,InternalName,IsActive,Mobile_Application_Action_ID,Mobile_Application_ID,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2025-09-25 16:18:16.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'setCurrentLocator','Y',540003,540002,TO_TIMESTAMP('2025-09-25 16:18:16.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T16:18:30.176Z
INSERT INTO Mobile_Application_Action (AD_Client_ID,AD_Org_ID,Created,CreatedBy,InternalName,IsActive,Mobile_Application_Action_ID,Mobile_Application_ID,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2025-09-25 16:18:30.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'bulkActions','Y',540004,540002,TO_TIMESTAMP('2025-09-25 16:18:30.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T16:18:37.879Z
INSERT INTO Mobile_Application_Action (AD_Client_ID,AD_Org_ID,Created,CreatedBy,InternalName,IsActive,Mobile_Application_Action_ID,Mobile_Application_ID,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2025-09-25 16:18:37.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'changeQty','Y',540005,540002,TO_TIMESTAMP('2025-09-25 16:18:37.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T16:18:45.589Z
INSERT INTO Mobile_Application_Action (AD_Client_ID,AD_Org_ID,Created,CreatedBy,InternalName,IsActive,Mobile_Application_Action_ID,Mobile_Application_ID,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2025-09-25 16:18:44.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'printLabels','Y',540006,540002,TO_TIMESTAMP('2025-09-25 16:18:44.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;



update mobile_application_action set mobile_application_id=540005 where mobile_application_id=540002;

