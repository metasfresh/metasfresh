-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- 2024-08-15T08:01:59.179Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-08-15 11:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588866
;

-- Column: MD_Candidate_Dist_Detail.PP_Order_Candidate_ID
-- Column: MD_Candidate_Dist_Detail.PP_Order_Candidate_ID
-- 2024-08-15T08:02:04.041Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-08-15 11:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588867
;

-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- 2024-08-15T08:02:09.432Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-08-15 11:02:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588865
;

-- Column: MD_Candidate_Dist_Detail.DD_Order_Candidate_ID
-- Column: MD_Candidate_Dist_Detail.DD_Order_Candidate_ID
-- 2024-08-15T08:03:13.252Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-08-15 11:03:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588901
;



alter table md_candidate_dist_detail drop CONSTRAINT pporder_mdcandidatedistdetail;
alter table md_candidate_dist_detail drop CONSTRAINT pporderbomline_mdcandidatedistdetail;
alter table md_candidate_dist_detail drop CONSTRAINT ppordercandidate_mdcandidatedistdetail;
alter table md_candidate_dist_detail drop CONSTRAINT ddordercandidate_mdcandidatedistdetail;

