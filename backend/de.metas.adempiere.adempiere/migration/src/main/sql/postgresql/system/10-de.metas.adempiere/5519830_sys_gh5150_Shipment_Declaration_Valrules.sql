-- 2019-04-19T16:41:52.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540433,TO_TIMESTAMP('2019-04-19 16:41:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_BPartner_Of_M_InOut','S',TO_TIMESTAMP('2019-04-19 16:41:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-19T16:42:04.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Name='C_BPartner_ID_Of_M_InOut',Updated=TO_TIMESTAMP('2019-04-19 16:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540433
;

-- 2019-04-19T16:42:23.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_BPartner_ID = ',Updated=TO_TIMESTAMP('2019-04-19 16:42:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540433
;

-- 2019-04-19T16:43:54.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_BPartner.C_BPartner_ID = (select C_BPartner_ID from M_InOut where M_InOut_ID = @M_InOut_ID/0@)',Updated=TO_TIMESTAMP('2019-04-19 16:43:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540433
;

-- 2019-04-19T16:44:42.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540433,Updated=TO_TIMESTAMP('2019-04-19 16:44:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567825
;

-- 2019-04-19T17:02:45.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540434,'C_BPartner_Location.C_BPartner_Location_ID = (select C_BPartner_Location_ID from M_InOut where M_InOut_ID = @M_InOut_ID/0@)',TO_TIMESTAMP('2019-04-19 17:02:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_BPartner_Location_ID_Of_M_InOut','S',TO_TIMESTAMP('2019-04-19 17:02:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-19T17:02:58.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540434,Updated=TO_TIMESTAMP('2019-04-19 17:02:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567826
;

-- 2019-04-19T17:11:07.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540435,'C_DocType.DocBaseType IN (''SDD'') AND IsSOTrx=''Y''',TO_TIMESTAMP('2019-04-19 17:11:06','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','C_DocType Shipment Declaration','S',TO_TIMESTAMP('2019-04-19 17:11:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-19T17:11:24.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540435,Updated=TO_TIMESTAMP('2019-04-19 17:11:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567824
;

