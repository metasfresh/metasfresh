-- 16.06.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540336,'C_BPartner.C_BPartner_ID in (select C_BPartnerRelation_ID from C_BP_Relation r where r.C_BPartner_ID= COALESCE (@C_BPartner_Override_ID@,@C_BPartner_ID@) and r.IsHandOverLocation =''Y'')',TO_TIMESTAMP('2016-06-16 11:37:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','C_BP_Relation_isHandover_Override','S',TO_TIMESTAMP('2016-06-16 11:37:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.06.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540336,Updated=TO_TIMESTAMP('2016-06-16 11:37:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554411
;

