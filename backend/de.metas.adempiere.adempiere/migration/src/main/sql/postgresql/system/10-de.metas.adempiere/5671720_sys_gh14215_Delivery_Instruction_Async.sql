-- 2023-01-13T14:47:08.683Z
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'de.metas.deliveryplanning.M_Delivery_Planning_Create_M_Delivery_Instruction_WorkpackageProcessor',540101,TO_TIMESTAMP('2023-01-13 16:47:07','YYYY-MM-DD HH24:MI:SS'),100,'D','M_Delivery_Planning_Create_M_Delivery_Instruction_WorkpackageProcessor','Y',TO_TIMESTAMP('2023-01-13 16:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T14:47:26.280Z
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540071,TO_TIMESTAMP('2023-01-13 16:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'M_Delivery_Planning_Create_M_Delivery_Instruction_WorkpackageProcessor',1,TO_TIMESTAMP('2023-01-13 16:47:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T14:47:45.481Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540101,540112,540071,TO_TIMESTAMP('2023-01-13 16:47:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2023-01-13 16:47:45','YYYY-MM-DD HH24:MI:SS'),100)
;

