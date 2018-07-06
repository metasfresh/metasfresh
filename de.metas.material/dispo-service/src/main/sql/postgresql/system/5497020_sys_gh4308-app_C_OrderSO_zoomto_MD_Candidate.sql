-- 2018-07-03T09:16:38.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Description='Activate to get a zoom-to target from order line, that only selects MD_Candidates with type = Production', IsActive='N',Updated=TO_TIMESTAMP('2018-07-03 09:16:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540179
;

-- 2018-07-03T09:17:28.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y', Name='C_OrderSO -> MD_Candidate_all',Updated=TO_TIMESTAMP('2018-07-03 09:17:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540178
;

-- 2018-07-03T09:17:38.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='MD_Candidate_Target_For_C_OrderSO',Updated=TO_TIMESTAMP('2018-07-03 09:17:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540720
;

-- 2018-07-03T09:19:25.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2018-07-03 09:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557892
;

-- 2018-07-03T09:19:49.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2018-07-03 09:19:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540100
;

-- 2018-07-03T09:19:58.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_OrderSO',Updated=TO_TIMESTAMP('2018-07-03 09:19:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540100
;

-- 2018-07-03T09:21:18.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2018-07-03 09:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540178
;

-- 2018-07-03T09:25:47.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Help='MD_Candidate.C_OrderSO_ID = @C_Order_ID/-1@',Updated=TO_TIMESTAMP('2018-07-03 09:25:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540720
;

-- 2018-07-03T09:27:24.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2018-07-03 09:27:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540178
;

-- 2018-07-03T09:49:26.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Help='',Updated=TO_TIMESTAMP('2018-07-03 09:49:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540720
;

-- 2018-07-03T09:49:39.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='MD_Candidate.C_OrderSO_ID = @C_Order_ID/-1@',Updated=TO_TIMESTAMP('2018-07-03 09:49:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540720
;

-- 2018-07-03T09:55:17.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='MD_Candidate_BusinessCase=''PRODUCTION'' AND C_OrderSO_ID = @C_Order_ID/-1@',Updated=TO_TIMESTAMP('2018-07-03 09:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540721
;

