
--
-- with theses constraints, we run into trouble with the normal workflow (e.g. when an inout is reactivated)
-- also, to the material dispo service, there are just random numbers anyways.
-- note that at least for now, we keep "master data" FK cosntraints like C_UOM
--

-- MD_Candidate_Demand_Detail.M_ForecastLine_ID
--
-- 2017-11-03T15:13:21.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-11-03 15:13:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557365;
ALTER TABLE public.md_candidate_demand_detail DROP CONSTRAINT IF EXISTS mforecastline_mdcandidatedeman;

-- MD_Candidate_Demand_Detail.M_ShipmentSchedule_ID
--
-- 2017-11-03T15:13:33.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-11-03 15:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557516;
ALTER TABLE public.md_candidate_demand_detail DROP CONSTRAINT IF EXISTS mshipmentschedule_mdcandidated;

-- MD_Candidate_Demand_Detail.C_OrderLine_ID
--
-- 2017-11-03T15:28:59.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-11-03 15:28:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556582;
ALTER TABLE public.md_candidate_demand_detail DROP CONSTRAINT IF EXISTS corderline_mdcandidatedemandde;

-- MD_Candidate_Dist_Detail.DD_Order_ID
--
-- 2017-11-03T15:16:05.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-11-03 15:16:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556802;
ALTER TABLE public.md_candidate_dist_detail DROP CONSTRAINT IF EXISTS ddorder_mdcandidatedistdetail;

-- MD_Candidate_Dist_Detail.DD_OrderLine_ID
--
-- 2017-11-03T15:16:26.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-11-03 15:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556803;
ALTER TABLE public.md_candidate_dist_detail DROP CONSTRAINT IF EXISTS ddorderline_mdcandidatedistdet;

-- MD_Candidate_Prod_Detail.PP_Order_BOMLine_ID
--
-- 2017-11-03T15:17:15.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-11-03 15:17:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556535;
ALTER TABLE public.md_candidate_prod_detail DROP CONSTRAINT IF EXISTS pporderbomline_mdcandidateprod;

-- MD_Candidate_Prod_Detail.PP_Order_ID
--
-- 2017-11-03T15:17:56.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-11-03 15:17:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556534;
ALTER TABLE public.md_candidate_prod_detail DROP CONSTRAINT IF EXISTS pporder_mdcandidateproddetail;

-- MD_Candidate_Transaction_Detail.M_Transaction_ID
--
-- 2017-11-03T15:18:54.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-11-03 15:18:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557526;
ALTER TABLE public.md_candidate_transaction_detail DROP CONSTRAINT IF EXISTS mtransaction_mdcandidatetransa;
