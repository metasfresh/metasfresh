-- Run mode: SWING_CLIENT

-- 2025-11-13T05:44:45.676Z
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'de.metas.inoutcandidate.async.CreatePackagesForShipmentWorkpackageProcessor',540113,TO_TIMESTAMP('2025-11-13 05:44:45.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Creates packages for an InOutLine and optionally adds them to today''s transportation order','de.metas.shipper.gateway.commons','CreatePackagesForShipmentWorkpackageProcessor','Y',TO_TIMESTAMP('2025-11-13 05:44:45.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-13T05:45:34.654Z
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540083,TO_TIMESTAMP('2025-11-13 05:45:34.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',0,'CreatePackagesForShipmentWorkpackageProcessor',1,TO_TIMESTAMP('2025-11-13 05:45:34.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-13T05:46:40.916Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540113,540127,540083,TO_TIMESTAMP('2025-11-13 05:46:40.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',TO_TIMESTAMP('2025-11-13 05:46:40.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

