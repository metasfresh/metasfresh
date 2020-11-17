--
-- fix display logic it MD_Candiate subtabs
--
-- 2017-12-13T16:15:19.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@MD_Candidate_BusinessCase@=PRODUCTION',Updated=TO_TIMESTAMP('2017-12-13 16:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540811
;

-- 2017-12-13T16:15:28.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@MD_Candidate_BusinessCase@=DISTRIBUTION',Updated=TO_TIMESTAMP('2017-12-13 16:15:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540821
;

--
-- also fix a relation type
--
-- 2017-12-13T16:45:07.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='MD_Candidate_BusinessCase=''PRODUCTION'' AND exists ( select 1 from MD_Candidate_Demand_Detail cdd join C_OrderLine ol on cdd.C_OrderLine_ID = ol.C_OrderLine_ID where ol.C_Order_ID = @C_Order_ID/-1@  AND MD_Candidate.MD_Candidate_ID = cdd.MD_Candidate_ID )',Updated=TO_TIMESTAMP('2017-12-13 16:45:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540721
;
