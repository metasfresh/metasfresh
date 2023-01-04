-- Window: Work Order Project, InternalName=workOrderProject
-- 2022-05-31T10:00:01.504Z
UPDATE AD_Window SET ZoomIntoPriority=1,Updated=TO_TIMESTAMP('2022-05-31 13:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541512
;

-- Window: Budget Project, InternalName=budgetProject
-- 2022-05-31T10:00:11.366Z
UPDATE AD_Window SET ZoomIntoPriority=1,Updated=TO_TIMESTAMP('2022-05-31 13:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541506
;

-- Reference: C_Project_ID
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-05-31T10:13:21.369Z
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2022-05-31 13:13:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541136
;

