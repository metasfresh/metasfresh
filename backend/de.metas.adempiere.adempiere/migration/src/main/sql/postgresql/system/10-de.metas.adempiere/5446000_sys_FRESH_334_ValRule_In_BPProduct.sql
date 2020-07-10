-- 23.05.2016 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540332,TO_TIMESTAMP('2016-05-23 17:57:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','C_BPartner_From _Org_Or_AllOrgs','S',TO_TIMESTAMP('2016-05-23 17:57:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.05.2016 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(C_BPartner.AD_Org_ID = @AD_Org_ID/-1@) or (@AD_Org_ID/-1@ = 0)',Updated=TO_TIMESTAMP('2016-05-23 17:58:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540332
;

-- 23.05.2016 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540332, IsUpdateable='N',Updated=TO_TIMESTAMP('2016-05-23 17:58:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=10163
;


-- 23.05.2016 18:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET EntityType='D',Updated=TO_TIMESTAMP('2016-05-23 18:43:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540332
;

-- 23.05.2016 19:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(C_BPartner.AD_Org_ID = @#AD_Org_ID/-1@) or (#@AD_Org_ID/-1@ = 0)',Updated=TO_TIMESTAMP('2016-05-23 19:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540332
;

-- 23.05.2016 19:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(C_BPartner.AD_Org_ID = @#AD_Org_ID/-1@) or (@#AD_Org_ID/-1@ = 0)',Updated=TO_TIMESTAMP('2016-05-23 19:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540332
;

-- 23.05.2016 19:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(C_BPartner.AD_Org_ID in (select c_org_id from M_Product where M_Product_ID = @M_Product_ID/-1@) ) or (eixts(select 1 from M_Product where M_Product_ID = @M_Product_ID/-1@ and ad_org_ID = 0))',Updated=TO_TIMESTAMP('2016-05-23 19:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540332
;

-- 23.05.2016 19:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(C_BPartner.AD_Org_ID in (select c_org_id from M_Product where M_Product_ID = @M_Product_ID/-1@) ) or (exists (select 1 from M_Product where M_Product_ID = @M_Product_ID/-1@ and ad_org_ID = 0))',Updated=TO_TIMESTAMP('2016-05-23 19:44:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540332
;


-- 23.05.2016 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(C_BPartner.AD_Org_ID = @AD_Org_ID/-1@) or (@AD_Org_ID/-1@ = 0)',Updated=TO_TIMESTAMP('2016-05-23 17:58:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540332
;
-- 23.05.2016 19:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(C_BPartner.AD_Org_ID = @AD_Org_ID/-1@) or (@AD_Org_ID/-1@ = 0)',Updated=TO_TIMESTAMP('2016-05-23 19:46:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540332
;

