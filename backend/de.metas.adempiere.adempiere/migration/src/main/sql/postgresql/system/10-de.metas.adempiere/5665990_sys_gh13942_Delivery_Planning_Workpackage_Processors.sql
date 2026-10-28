-- 2022-11-24T08:53:05.783Z
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'de.metas.deliveryplanning.M_ShipmentSchedule_Create_M_Delivery_Planning',540099,TO_TIMESTAMP('2022-11-24 10:53:05','YYYY-MM-DD HH24:MI:SS'),100,'D','M_ShipmentSchedule_Create_M_Delivery_Planning','Y',TO_TIMESTAMP('2022-11-24 10:53:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T08:53:45.058Z
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'de.metas.deliveryplanning.M_ReceiptSchedule_Create_M_Delivery_Planning',540100,TO_TIMESTAMP('2022-11-24 10:53:44','YYYY-MM-DD HH24:MI:SS'),100,'D','M_ReceiptSchedule_Create_M_Delivery_Planning','Y',TO_TIMESTAMP('2022-11-24 10:53:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T09:00:42.637Z
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540068,TO_TIMESTAMP('2022-11-24 11:00:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'M_ShipmentSchedule_Create_M_Delivery_Planning',10,TO_TIMESTAMP('2022-11-24 11:00:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T09:00:53.993Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540099,540110,540068,TO_TIMESTAMP('2022-11-24 11:00:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-11-24 11:00:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T09:01:06.969Z
UPDATE C_Queue_Processor SET KeepAliveTimeMillis=1000, PoolSize=1,Updated=TO_TIMESTAMP('2022-11-24 11:01:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540068
;

-- 2022-11-24T09:01:31.426Z
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540069,TO_TIMESTAMP('2022-11-24 11:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'M_ReceiptSchedule_Create_M_Delivery_Planning',1,TO_TIMESTAMP('2022-11-24 11:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T09:01:38.626Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540100,540111,540069,TO_TIMESTAMP('2022-11-24 11:01:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-11-24 11:01:38','YYYY-MM-DD HH24:MI:SS'),100)
;

