
-- 2022-08-25T02:12:45.306Z
UPDATE AD_Scheduler SET IsActive='Y',Updated=TO_TIMESTAMP('2022-08-25 05:12:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550062
;

UPDATE AD_Scheduler_Para schedulerPara
SET ParameterDefault = 1,
    Updated=now(),
    UpdatedBy=99
WHERE schedulerPara.AD_Scheduler_ID = 550062
  and schedulerPara.AD_Scheduler_Para_ID = 540011;
