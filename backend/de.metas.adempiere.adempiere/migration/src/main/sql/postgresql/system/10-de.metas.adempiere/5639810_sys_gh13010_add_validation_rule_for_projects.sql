-- CREATE AND ASSIGN VALIDATION RULE
-- 2022-05-13T13:01:08.435Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540579,'C_Project.AD_Org_ID in (@AD_Org_ID/-1@, 0)',TO_TIMESTAMP('2022-05-13 15:01:08','YYYY-MM-DD HH24:MI:SS'),100,'Select projects that correspond with the selected organisation','D','Y','C_Project_from_organsiation','S',TO_TIMESTAMP('2022-05-13 15:01:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-13T13:01:36.771Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Reference_ID=30, AD_Val_Rule_ID=540579,Updated=TO_TIMESTAMP('2022-05-13 15:01:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=3459
;

-- 2022-05-13T13:02:40.388Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Reference_ID=30, AD_Val_Rule_ID=540579,Updated=TO_TIMESTAMP('2022-05-13 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=668767
;