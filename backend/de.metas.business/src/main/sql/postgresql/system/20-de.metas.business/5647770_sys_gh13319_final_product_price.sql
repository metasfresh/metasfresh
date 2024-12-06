-- 2022-07-21T19:27:37.342Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=541568,Updated=TO_TIMESTAMP('2022-07-21 19:27:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542190
;

-- 2022-07-21T19:37:28.139Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555982,TO_TIMESTAMP('2022-07-21 19:37:28','YYYY-MM-DD HH24:MI:SS'),100,10000000,10000000,'DocumentNo/Value for Table M_CostRevaluation',1,'Y','N','Y','N','DocumentNo_M_CostRevaluation','N',10000000,TO_TIMESTAMP('2022-07-21 19:37:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T19:44:36.266Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y', IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-07-21 19:44:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583792
;

-- 2022-07-21T19:44:50.078Z
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-07-21 19:44:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583792
;

-- 2022-07-21T19:45:31.122Z
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2022-07-21 19:45:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583793
;

-- 2022-07-21T19:51:42.798Z
-- URL zum Konzept
UPDATE AD_Sequence SET CurrentNext=1,Updated=TO_TIMESTAMP('2022-07-21 19:51:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555982
;

-- 2022-07-21T19:52:44.237Z
-- URL zum Konzept
UPDATE AD_Sequence SET DecimalPattern='00000',Updated=TO_TIMESTAMP('2022-07-21 19:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555982
;

