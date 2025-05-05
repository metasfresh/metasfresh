-- Run mode: SWING_CLIENT

-- Name: Modular_Interim_Contract_OnFlatrateTermExtend
-- 2023-10-10T10:03:16Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540663,'(''@Type_Conditions@'' IN (''ModularContract'', ''InterimInvoice'') AND AD_Ref_List.Value = ''Ex'') OR ''@Type_Conditions@'' NOT IN (''ModularContract'', ''InterimInvoice'')',TO_TIMESTAMP('2023-10-10 13:03:15.657','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Modular_Interim_Contract_OnFlatrateTermExtend','S',TO_TIMESTAMP('2023-10-10 13:03:15.657','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: C_Flatrate_Conditions.OnFlatrateTermExtend
-- 2023-10-10T10:03:37.179Z
UPDATE AD_Column SET AD_Val_Rule_ID=540663,Updated=TO_TIMESTAMP('2023-10-10 13:03:37.179','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=559719
;

