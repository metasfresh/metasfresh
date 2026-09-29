-- Pins C_Order_ContractCompensationGroup's registration order: AD_ModelValidator module
-- activators (this row included) are always initialized before loose Spring @Interceptor beans
-- (ModelValidationEngine.init()), and among module activators by SeqNo. HU's packing-material
-- builder (de.metas.handlingunits.model.validator.Main) is SeqNo 500; freight cost and every
-- other loose interceptor have no AD_ModelValidator row at all, so they always come after ANY
-- module activator. SeqNo 550 (> 500, so after HU) guarantees this interceptor runs after HU's
-- packing-material lines exist and before freight's line is added, on every instance.
--
-- No new Java class is introduced: ModelValidationEngine.loadModuleActivatorClass() reuses the
-- already Spring-managed C_Order_ContractCompensationGroup bean directly when its own class name
-- is registered here (existingSpringInstance path, ModelValidationEngine.java ~234-251), so the
-- existing @Interceptor @Component class doubles as its own "module activator".
INSERT INTO AD_ModelValidator (AD_Client_ID,AD_ModelValidator_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,ModelValidationClass,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,540127 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Registers C_Order_ContractCompensationGroup with a SeqNo after HU (500) and before every loose interceptor (incl. freight)','de.metas.contracts','Y','de.metas.contracts.compensationGroup.contract.interceptor.C_Order_ContractCompensationGroup','contractCompensationGroupOrderValidator',550,TO_TIMESTAMP('2026-09-29 09:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
