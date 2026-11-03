-- Run mode: SWING_CLIENT

-- 2025-10-18T11:07:21.048Z
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'de.metas.shipper.gateway.commons.async.AdviseDeliveryOrderWorkpackageProcessor',540112,TO_TIMESTAMP('2025-10-18 11:07:20.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Requests various advice from a shipper (Product, Goods type, Services) before creating the actual shipment order request. Saves this advice to M_ShipmentSchedule.','D','AdviseDeliveryOrderWorkpackageProcessor','Y',TO_TIMESTAMP('2025-10-18 11:07:20.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-18T17:42:31.403Z
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540082,TO_TIMESTAMP('2025-10-18 17:42:31.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',0,'AdviseDeliveryOrderWorkpackageProcessor',10,TO_TIMESTAMP('2025-10-18 17:42:31.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-18T17:42:32.403Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540112,540126,540082,TO_TIMESTAMP('2025-10-18 17:42:32.099000','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2025-10-18 17:42:32.099000','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-10-18T17:50:17.750Z
UPDATE C_Queue_Processor SET KeepAliveTimeMillis=1000, PoolSize=1,Updated=TO_TIMESTAMP('2025-10-18 17:50:17.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Queue_Processor_ID=540082
;
