-- Pins C_Order_ContractCompensationGroup's SeqNo to 550 (after HU's 500, before every loose
-- interceptor incl. freight). Reuses the existing @Interceptor @Component bean directly; no new
-- Java class needed.
INSERT INTO AD_ModelValidator (AD_Client_ID,AD_ModelValidator_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,ModelValidationClass,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,540127 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Registers C_Order_ContractCompensationGroup with a SeqNo after HU (500) and before every loose interceptor (incl. freight)','de.metas.contracts','Y','de.metas.contracts.compensationGroup.contract.interceptor.C_Order_ContractCompensationGroup','contractCompensationGroupOrderValidator',550,TO_TIMESTAMP('2026-09-29 09:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
