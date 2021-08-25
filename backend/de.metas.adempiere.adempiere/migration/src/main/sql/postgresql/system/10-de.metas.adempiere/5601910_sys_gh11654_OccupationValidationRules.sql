-- METASFRESH
-- hide parent when type is B
-- 2021-08-25T08:39:31.242Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@JobType@=''0''',Updated=TO_TIMESTAMP('2021-08-25 11:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=652708
;

-- 2021-08-25T08:39:55.441Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@JobType@!=''0''',Updated=TO_TIMESTAMP('2021-08-25 11:39:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=652708
;

-- 2021-08-25T08:41:27.805Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@JobType@!=''B''',Updated=TO_TIMESTAMP('2021-08-25 11:41:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=652708
;

-- 2021-08-25T09:02:06.693Z
-- URL zum Konzept
UPDATE AD_Column SET MandatoryLogic='@JobType/''B''@!=''B''',Updated=TO_TIMESTAMP('2021-08-25 12:02:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575500
;

-- Validation rule for crm_occupation_parent_id
-- 2021-08-25T09:33:15.222Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540550,'exists (select 1 from CRM_Occupation where (CRM_Occupation.JobType = ''F'' and ''@JobType@'' = ''Z'') or (CRM_Occupation.JobType = ''B'' and ''@JobType@'' = ''F''))',TO_TIMESTAMP('2021-08-25 12:33:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CRM_Occupation.JobType','S',TO_TIMESTAMP('2021-08-25 12:33:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-25T09:34:27.396Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540550,Updated=TO_TIMESTAMP('2021-08-25 12:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575500
;

-- 2021-08-25T09:45:02.403Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='CRM_Occupation.JobType=CASE WHEN  @JobType@ IN (''F'', ''B'') THEN ''B'' ELSE ''F'' END',Updated=TO_TIMESTAMP('2021-08-25 12:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540550
;

-- 2021-08-25T09:53:34.537Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='CASE
WHEN ''@JobType@'' = ''F'' (select crm_occupation_id from crm_occupation where crm_occupation.jobtype = ''B'')
WHEN ''@JobType@'' = ''Z'' (select crm_occupation_id from crm_occupation where crm_occupation.jobtype = ''F'')
ELSE null
END',Updated=TO_TIMESTAMP('2021-08-25 12:53:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540550
;

-- 2021-08-25T09:54:24.925Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=176,Updated=TO_TIMESTAMP('2021-08-25 12:54:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575500
;

-- 2021-08-25T09:54:52.224Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540550,Updated=TO_TIMESTAMP('2021-08-25 12:54:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575500
;

-- 2021-08-25T10:04:37.733Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='crm_occupation.jobtype = (CASE WHEN ''@JobType@'' = ''F'' then ''B'' WHEN ''@JobType@'' = ''Z'' then ''F'' )END',Updated=TO_TIMESTAMP('2021-08-25 13:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540550
;

-- 2021-08-25T10:07:07.099Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='crm_occupation.jobtype = (CASE WHEN ''@JobType@'' = ''F'' then ''B'' WHEN ''@JobType@'' = ''Z'' then ''F'' END)',Updated=TO_TIMESTAMP('2021-08-25 13:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540550
;

-- 2021-08-25T10:16:08.289Z
-- URL zum Konzept
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2021-08-25 13:16:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575500
;

-- 2021-08-25T10:17:32.123Z
-- URL zum Konzept
UPDATE AD_Column SET MandatoryLogic='@JobType/''B''@!=''B''',Updated=TO_TIMESTAMP('2021-08-25 13:17:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575500
;







-- Validation Rule for Job
-- 2021-08-25T12:45:38.119Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540551,'CRM_Occupation.JobType = ''B''',TO_TIMESTAMP('2021-08-25 15:45:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CRM_Occupation_Job','S',TO_TIMESTAMP('2021-08-25 15:45:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-25T12:46:11.741Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540551,Updated=TO_TIMESTAMP('2021-08-25 15:46:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575465
;

-- Validation Rule for Specialization
-- 2021-08-25T13:16:54.940Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540552,'CRM_Occupation.JobType = ''F'' and CRM_Occupation.CRM_Occupation_Parent_ID = (SELECT distinct CRM_Occupation_ID from ad_user_occupation_job where AD_User_ID =@AD_USER_ID@)',TO_TIMESTAMP('2021-08-25 16:16:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CRM_Occupation_Specialization','S',TO_TIMESTAMP('2021-08-25 16:16:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-25T13:17:25.175Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540552,Updated=TO_TIMESTAMP('2021-08-25 16:17:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575483
;

-- 2021-08-25T13:18:34.222Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='CRM_Occupation.JobType = ''F'' and CRM_Occupation.CRM_Occupation_Parent_ID = (SELECT distinct CRM_Occupation_ID from ad_user_occupation_job where ad_user_occupation_job.AD_User_ID =@AD_USER_ID@)',Updated=TO_TIMESTAMP('2021-08-25 16:18:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540552
;

-- 2021-08-25T13:23:10.177Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540550,Updated=TO_TIMESTAMP('2021-08-25 16:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575483
;

-- 2021-08-25T13:23:50.188Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540552,Updated=TO_TIMESTAMP('2021-08-25 16:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575483
;

-- 2021-08-25T13:28:50.904Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='CRM_Occupation.JobType = ''F'' and exists(select 1 from ad_user_occupation_job job where job.ad_user_id = @AD_User_ID@ and crm_occupation.crm_occupation_parent_id = job.crm_occupation_id)',Updated=TO_TIMESTAMP('2021-08-25 16:28:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540552
;

-- Validation rule for additionalSpecialization
-- 2021-08-25T13:44:29.129Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540553,'crm_occupation.jobType = ''Z''
                      and exists(select 1 from ad_user_occupation_specialization s where s.ad_user_id = @AD_USER_ID@ and crm_occupation.crm_occupation_parent_id = s.crm_occupation_id)',TO_TIMESTAMP('2021-08-25 16:44:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CRM_Occupation_AdditionalSpecialization','S',TO_TIMESTAMP('2021-08-25 16:44:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-25T13:45:30.150Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540553,Updated=TO_TIMESTAMP('2021-08-25 16:45:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575497
;

-- 2021-08-25T13:46:58.049Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='crm_occupation.jobType = ''Z'' and exists(select 1 from ad_user_occupation_specialization s where s.ad_user_id = @AD_User_ID@ and crm_occupation.crm_occupation_parent_id = s.crm_occupation_id)',Updated=TO_TIMESTAMP('2021-08-25 16:46:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540553
;
