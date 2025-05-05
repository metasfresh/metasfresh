-- Run mode: SWING_CLIENT

-- 2024-05-27T19:13:54.022Z
UPDATE C_DocType SET DocSubType='DCM',Updated=TO_TIMESTAMP('2024-05-27 22:13:54.022','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541123
;

-- 2024-05-28T10:35:43.192Z
UPDATE C_Queue_PackageProcessor SET Classname='de.metas.contracts.finalinvoice.workpackage.ModCntrInvoiceWorkPackageProcessor', InternalName='ModCntrInvoiceWorkPackageProcessor',Updated=TO_TIMESTAMP('2024-05-28 13:35:43.191','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540107
;

-- 2024-05-28T10:36:18.581Z
UPDATE C_Queue_Processor SET Name='ModCntrInvoiceWorkPackageProcessor',Updated=TO_TIMESTAMP('2024-05-28 13:36:18.58','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540077
;

