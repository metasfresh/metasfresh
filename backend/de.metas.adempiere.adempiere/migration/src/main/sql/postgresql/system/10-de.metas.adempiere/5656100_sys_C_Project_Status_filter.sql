-- Name: R_Status_by_R_RequestType_ID
-- 2022-09-14T08:35:42.471Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540601,'R_Status.R_StatusCategory_ID=(select r_statuscategory_id from r_requesttype rt where rt.r_requesttype_id=@R_RequestType_ID@)',TO_TIMESTAMP('2022-09-14 11:35:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','R_Status_by_R_RequestType_ID','S',TO_TIMESTAMP('2022-09-14 11:35:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-14T08:36:10.271Z
INSERT INTO AD_Val_Rule_Dep (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Val_Rule_Dep_ID,AD_Val_Rule_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,529,540010,540601,TO_TIMESTAMP('2022-09-14 11:36:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-09-14 11:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Project.R_Project_Status_ID
-- 2022-09-14T08:36:43.567Z
UPDATE AD_Column SET Filter_Val_Rule_ID=540601,Updated=TO_TIMESTAMP('2022-09-14 11:36:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559693
;

-- Name: R_Status_by_C_ProjectType_ID
-- 2022-09-14T09:08:25.073Z
UPDATE AD_Val_Rule SET Code='R_Status.R_StatusCategory_ID=(select r_statuscategory_id from c_projecttype rt where rt.c_projecttype_id=@C_ProjectType_ID@)', Name='R_Status_by_C_ProjectType_ID',Updated=TO_TIMESTAMP('2022-09-14 12:08:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540601
;

-- Column: C_Project.C_ProjectType_ID
-- 2022-09-14T09:08:44.669Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-09-14 12:08:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8757
;

-- Column: C_Project.startdatetime
-- 2022-09-14T09:09:32.901Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2022-09-14 12:09:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568792
;

-- Column: C_Project.enddatetime
-- 2022-09-14T09:09:33.991Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2022-09-14 12:09:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568793
;

-- Column: C_Project.Name
-- 2022-09-14T09:09:34.958Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2022-09-14 12:09:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1356
;

-- Column: C_Project.Value
-- 2022-09-14T09:09:35.863Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2022-09-14 12:09:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2010
;

-- Column: C_Project.Created
-- 2022-09-14T09:09:36.740Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2022-09-14 12:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1353
;

-- Column: C_Project.C_BPartner_ID
-- 2022-09-14T09:09:37.595Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2022-09-14 12:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3902
;

-- Column: C_Project.PlannedAmt
-- 2022-09-14T09:09:38.462Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2022-09-14 12:09:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=5755
;

-- Column: C_Project.C_ProjectType_ID
-- 2022-09-14T09:09:39.295Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2022-09-14 12:09:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8757
;

-- Column: C_Project.R_Project_Status_ID
-- 2022-09-14T09:09:41.393Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2022-09-14 12:09:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559693
;

-- Column: C_Project.AD_Org_ID
-- 2022-09-14T09:09:42.407Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2022-09-14 12:09:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1350
;

