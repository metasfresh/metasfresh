
-- 17.11.2016 11:23
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2016-11-17 11:23:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552525
;

-- 17.11.2016 11:24
-- URL zum Konzept
INSERT INTO t_alter_column values('c_async_batch_type','IsSendNotification','CHAR(1)',null,'N')
;

-- 17.11.2016 11:24
-- URL zum Konzept
UPDATE C_Async_Batch_Type SET IsSendNotification='N' WHERE IsSendNotification IS NULL
;

commit; 
-- 17.11.2016 11:24
-- URL zum Konzept
INSERT INTO t_alter_column values('c_async_batch_type','IsSendNotification',null,'NOT NULL',null)
;

commit;

-- 17.11.2016 11:38
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2016-11-17 11:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551399
;

-- 17.11.2016 11:38
-- URL zum Konzept
INSERT INTO t_alter_column values('c_async_batch_type','IsSendMail','CHAR(1)',null,'N')
;

-- 17.11.2016 11:38
-- URL zum Konzept
UPDATE C_Async_Batch_Type SET IsSendMail='N' WHERE IsSendMail IS NULL
;

commit;
-- 17.11.2016 11:38
-- URL zum Konzept
INSERT INTO t_alter_column values('c_async_batch_type','IsSendMail',null,'NOT NULL',null)
;

