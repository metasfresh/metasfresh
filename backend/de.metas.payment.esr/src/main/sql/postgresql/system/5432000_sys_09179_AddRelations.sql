-- 30.10.2015 15:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540589,540590,540133,TO_TIMESTAMP('2015-10-30 15:12:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','c_bankstatement -> x_esr_import_in_c_bankstatement_v',TO_TIMESTAMP('2015-10-30 15:12:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 30.10.2015 15:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540592,TO_TIMESTAMP('2015-10-30 15:12:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','c_bankstatement -> x_esr_import_in_c_bankstatement_v',TO_TIMESTAMP('2015-10-30 15:12:51','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 30.10.2015 15:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540592 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 30.10.2015 15:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,541645,4909,0,540592,392,TO_TIMESTAMP('2015-10-30 15:14:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N',TO_TIMESTAMP('2015-10-30 15:14:02','YYYY-MM-DD HH24:MI:SS'),100,'C_BankStatement_ID = @C_BankStatement_ID@')
;

-- 30.10.2015 15:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540592,Updated=TO_TIMESTAMP('2015-10-30 15:14:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540133
;

-- 30.10.2015 15:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,Help,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540593,TO_TIMESTAMP('2015-10-30 15:15:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr',NULL,'Y','N','x_esr_import_in_c_bankstatement_v->c_bankstatement',TO_TIMESTAMP('2015-10-30 15:15:15','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 30.10.2015 15:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540593 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 30.10.2015 15:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,552785,552784,0,540593,540685,TO_TIMESTAMP('2015-10-30 15:16:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N',TO_TIMESTAMP('2015-10-30 15:16:27','YYYY-MM-DD HH24:MI:SS'),100,'C_BankStatement_ID = @C_BankStatement_ID@')
;

-- 30.10.2015 15:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540593, EntityType='de.metas.payment.esr', Role_Source='C_BankStatement', Role_Target='C_BankStatement',Updated=TO_TIMESTAMP('2015-10-30 15:17:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540133
;



-- 30.10.2015 16:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='c_bankstatement -> esr_import',Updated=TO_TIMESTAMP('2015-10-30 16:28:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540133
;

-- 30.10.2015 16:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='c_bankstatement -> esr_import',Updated=TO_TIMESTAMP('2015-10-30 16:29:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540592
;

-- 30.10.2015 16:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540592
;

-- 30.10.2015 16:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=547550, AD_Key=547550, AD_Table_ID=540409, WhereClause='exists (select 1 from x_esr_import_in_c_bankstatement_v v where v.C_BankStatement_ID = @C_BankStatement_ID@)
',Updated=TO_TIMESTAMP('2015-10-30 16:31:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540592
;

-- 30.10.2015 16:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,53331,541129,TO_TIMESTAMP('2015-10-30 16:34:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','ESR Import',TO_TIMESTAMP('2015-10-30 16:34:56','YYYY-MM-DD HH24:MI:SS'),100,'ESR_Import','ESR Import')
;

-- 30.10.2015 16:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541129 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 30.10.2015 16:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='ESR_Import',Updated=TO_TIMESTAMP('2015-10-30 16:35:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540133
;

-- 30.10.2015 16:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='esr_import->c_bankstatement',Updated=TO_TIMESTAMP('2015-10-30 16:35:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540593
;

-- 30.10.2015 16:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540593
;

-- 30.10.2015 16:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=541645, AD_Key=4909, AD_Table_ID=392, WhereClause='exists (select 1 from x_esr_import_in_c_bankstatement_v v where v.ESR_Import_ID = @ESR_Import_ID@)
',Updated=TO_TIMESTAMP('2015-10-30 16:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540593
;

-- 30.10.2015 16:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from x_esr_import_in_c_bankstatement_v v where v.C_BankStatement_ID = @C_BankStatement_ID@)',Updated=TO_TIMESTAMP('2015-10-30 16:40:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540593
;

-- 30.10.2015 16:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from x_esr_import_in_c_bankstatement_v v where v.ESR_Import_ID = @ESR_Import_ID@)
',Updated=TO_TIMESTAMP('2015-10-30 16:40:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540592
;

-- 30.10.2015 16:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from x_esr_import_in_c_bankstatement_v v where v.ESR_Import_ID = @v.ESR_Import_ID@ and v.C_BankStatement_ID = C_BankStatement.C_BankStatement_ID)',Updated=TO_TIMESTAMP('2015-10-30 16:46:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540593
;

-- 30.10.2015 16:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from x_esr_import_in_c_bankstatement_v v where v.C_BankStatement_ID = @C_BankStatement_ID@ and v.ESR_Import_ID = ESR_Import.ESR_Import_ID)',Updated=TO_TIMESTAMP('2015-10-30 16:48:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540592
;

-- 30.10.2015 16:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from x_esr_import_in_c_bankstatement_v v where v.ESR_Import_ID = @ESR_Import_ID@ and v.C_BankStatement_ID = C_BankStatement.C_BankStatement_ID)',Updated=TO_TIMESTAMP('2015-10-30 16:48:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540593
;

