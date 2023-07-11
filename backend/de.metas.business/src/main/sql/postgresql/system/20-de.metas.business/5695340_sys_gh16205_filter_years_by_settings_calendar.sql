-- Name: C_Year (Calendar)
-- 2023-07-11T10:45:24.535118227Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540645,'asdfgasdg',TO_TIMESTAMP('2023-07-11 11:45:24.024','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','C_Year (Calendar)','S',TO_TIMESTAMP('2023-07-11 11:45:24.024','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: C_Year (Calendar)
-- 2023-07-11T12:54:31.712787842Z
UPDATE AD_Val_Rule SET Code='C_Year.C_Calendar_ID = (SELECT c_calendar_id FROM modcntr_settings WHERE modcntr_settings_id = (SELECT modcntr_settings_id FROM c_flatrate_conditions WHERE c_flatrate_conditions_id = @C_Flatrate_Conditions_ID@))',Updated=TO_TIMESTAMP('2023-07-11 13:54:31.71','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540645
;

-- Process: C_Flatrate_Conditions_Extend(de.metas.contracts.flatrate.process.C_Flatrate_Conditions_Extend)
-- ParameterName: C_Year_ID
-- 2023-07-11T10:58:51.537312334Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540645,Updated=TO_TIMESTAMP('2023-07-11 11:58:51.537','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542652
;

-- Process: C_Flatrate_Conditions_Extend(de.metas.contracts.flatrate.process.C_Flatrate_Conditions_Extend)
-- ParameterName: C_Year_ID
-- 2023-07-11T13:04:35.582039088Z
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=540133,Updated=TO_TIMESTAMP('2023-07-11 14:04:35.581','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542652
;

-- Process: C_Flatrate_Conditions_Extend(de.metas.contracts.flatrate.process.C_Flatrate_Conditions_Extend)
-- ParameterName: C_Year_ID
-- 2023-07-11T14:42:58.705397807Z
UPDATE AD_Process_Para SET AD_Reference_ID=30, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2023-07-11 15:42:58.704','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542652
;


