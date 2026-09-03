-- Name: M_HU_PI Only TUs
-- 2024-04-23T10:38:20.669Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540672,'EXISTS (SELECT 1 FROM m_hu_pi_version piv WHERE piv.M_HU_PI_ID = M_HU_PI.M_HU_PI_ID AND piv.isactive = ''Y'' AND piv.iscurrent = ''Y'' AND piv.hu_unittype IN (''TU'', ''V'')) AND M_HU_PI.M_HU_PI_ID != 100',TO_TIMESTAMP('2024-04-23 13:38:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_HU_PI Only TUs','S',TO_TIMESTAMP('2024-04-23 13:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Process: GenerateHUQRCodes(de.metas.handlingunits.process.GenerateHUQRCodes)
-- ParameterName: M_HU_PI_ID
-- 2024-04-23T10:38:38.664Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540672, IsMandatory='N',Updated=TO_TIMESTAMP('2024-04-23 13:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542803
;

