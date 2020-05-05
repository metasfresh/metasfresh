

-- 2017-05-15T12:38:19.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.MD_Candidate_Dist_Detail (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DD_Order_DocStatus VARCHAR(2), DD_Order_ID NUMERIC(10), DD_OrderLine_ID NUMERIC(10), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MD_Candidate_Dist_Detail_ID NUMERIC(10) NOT NULL, MD_Candidate_ID NUMERIC(10) NOT NULL, PP_Product_Planning_ID NUMERIC(10), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT DDOrder_MDCandidateDistDetail FOREIGN KEY (DD_Order_ID) REFERENCES public.DD_Order DEFERRABLE INITIALLY DEFERRED, CONSTRAINT DDOrderLine_MDCandidateDistDet FOREIGN KEY (DD_OrderLine_ID) REFERENCES public.DD_OrderLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MD_Candidate_Dist_Detail_Key PRIMARY KEY (MD_Candidate_Dist_Detail_ID), CONSTRAINT MDCandidate_MDCandidateDistDet FOREIGN KEY (MD_Candidate_ID) REFERENCES public.MD_Candidate DEFERRABLE INITIALLY DEFERRED, CONSTRAINT PPProductPlanning_MDCandidateD FOREIGN KEY (PP_Product_Planning_ID) REFERENCES public.PP_Product_Planning DEFERRABLE INITIALLY DEFERRED)
;


-- 2017-05-15T12:49:57.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('md_candidate_dist_detail','ALTER TABLE public.MD_Candidate_Dist_Detail ADD COLUMN DD_NetworkDistributionLine_ID NUMERIC(10)')
;

-- 2017-05-15T12:49:57.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE MD_Candidate_Dist_Detail ADD CONSTRAINT DDNetworkDistributionLine_MDCa FOREIGN KEY (DD_NetworkDistributionLine_ID) REFERENCES public.DD_NetworkDistributionLine DEFERRABLE INITIALLY DEFERRED
;


-- 2017-05-15T14:43:30.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('dd_order','ALTER TABLE public.DD_Order ADD COLUMN PP_Product_Planning_ID NUMERIC(10)')
;


-- 2017-05-15T16:39:03.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('md_candidate_dist_detail','ALTER TABLE public.MD_Candidate_Dist_Detail ADD COLUMN M_Shipper_ID NUMERIC(10)')
;

-- 2017-05-15T16:39:03.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE MD_Candidate_Dist_Detail ADD CONSTRAINT MShipper_MDCandidateDistDetail FOREIGN KEY (M_Shipper_ID) REFERENCES public.M_Shipper DEFERRABLE INITIALLY DEFERRED
;



-- 2017-05-15T18:26:47.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('md_candidate_dist_detail','ALTER TABLE public.MD_Candidate_Dist_Detail ADD COLUMN PP_Plant_ID NUMERIC(10)')
;

-- 2017-05-15T18:26:47.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE MD_Candidate_Dist_Detail ADD CONSTRAINT PPPlant_MDCandidateDistDetail FOREIGN KEY (PP_Plant_ID) REFERENCES public.S_Resource DEFERRABLE INITIALLY DEFERRED
;

