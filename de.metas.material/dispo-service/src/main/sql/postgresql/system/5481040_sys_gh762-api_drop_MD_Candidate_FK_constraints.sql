--
--drop md_candidate_prod_detail FKs
--

-- 2017-12-20T14:49:24.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:49:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556535
;
-- 2017-12-20T14:49:32.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556534
;
-- 2017-12-20T14:49:40.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:49:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556526
;
-- 2017-12-20T14:49:51.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556524
;
-- 2017-12-20T14:49:56.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:49:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556525
;
ALTER TABLE public.md_candidate_prod_detail DROP CONSTRAINT IF EXISTS pporder_mdcandidateproddetail;
ALTER TABLE public.md_candidate_prod_detail DROP CONSTRAINT IF EXISTS pporderbomline_mdcandidateprod;
ALTER TABLE public.md_candidate_prod_detail DROP CONSTRAINT IF EXISTS ppplant_mdcandidateproddetail;
ALTER TABLE public.md_candidate_prod_detail DROP CONSTRAINT IF EXISTS ppproductbomline_mdcandidatepr;
ALTER TABLE public.md_candidate_prod_detail DROP CONSTRAINT IF EXISTS ppproductplanning_mdcandidatep;

--
-- MD_Candidate_Dist_Detail
--
-- 2017-12-20T14:53:47.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:53:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556804
;
-- 2017-12-20T14:53:56.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:53:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556802
;
-- 2017-12-20T14:54:03.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:54:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556803
;
-- 2017-12-20T14:54:15.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556807
;
-- 2017-12-20T14:54:22.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:54:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556808
;
-- 2017-12-20T14:54:28.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:54:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556801
;
ALTER TABLE public.md_candidate_dist_detail DROP CONSTRAINT IF EXISTS ddnetworkdistributionline_mdca;
ALTER TABLE public.md_candidate_dist_detail DROP CONSTRAINT IF EXISTS ddorder_mdcandidatedistdetail;
ALTER TABLE public.md_candidate_dist_detail DROP CONSTRAINT IF EXISTS ddorderline_mdcandidatedistdet;
ALTER TABLE public.md_candidate_dist_detail DROP CONSTRAINT IF EXISTS mshipper_mdcandidatedistdetail;
ALTER TABLE public.md_candidate_dist_detail DROP CONSTRAINT IF EXISTS ppplant_mdcandidatedistdetail;
ALTER TABLE public.md_candidate_dist_detail DROP CONSTRAINT IF EXISTS ppproductplanning_mdcandidated;

--
-- MD_Candidate_Demand_Detail
--
-- 2017-12-20T14:57:21.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:57:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557516
;
-- 2017-12-20T14:57:31.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:57:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556582
;
-- 2017-12-20T14:57:39.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 14:57:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557365
;
ALTER TABLE public.md_candidate_demand_detail DROP CONSTRAINT IF EXISTS corderline_mdcandidatedemandde;
ALTER TABLE public.md_candidate_demand_detail DROP CONSTRAINT IF EXISTS mforecastline_mdcandidatedeman;
--note that the shipment sched's FK constraint was already cleaned up

--
-- MD_Candidate_Transaction_Detail
--
-- 2017-12-20T15:00:06.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-12-20 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557526
;
--note that the transaction's FK constraint was already cleaned up
