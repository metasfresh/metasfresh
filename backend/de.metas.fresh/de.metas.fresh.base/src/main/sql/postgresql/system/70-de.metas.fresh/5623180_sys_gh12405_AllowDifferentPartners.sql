-- 2022-01-26T14:28:08.234783700Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540569,'C_BPartner.IsSummary = ''N'' AND (EXISTS(SELECT * FROM C_BPartner_Location l WHERE C_BPartner.C_BPartner_ID = l.C_BPartner_ID AND l.IsShipTo = ''Y'')) OR EXISTS(SELECT * FROM C_BP_Relation r WHERE C_BPartner.C_BPartner_ID = r.C_BPartnerRelation_ID AND r.IsShipTo = ''Y'')',TO_TIMESTAMP('2022-01-26 16:28:08','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','C_BPartner_OnlyShipTo','S',TO_TIMESTAMP('2022-01-26 16:28:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-26T14:31:57.696463800Z
UPDATE AD_Column SET AD_Val_Rule_ID=540569,Updated=TO_TIMESTAMP('2022-01-26 16:31:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2764
;

