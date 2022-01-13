-- 2021-08-27T13:48:00.523Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0',Updated=TO_TIMESTAMP('2021-08-27 15:48:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575344
;

-- 2021-08-27T13:48:05.054Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_membershipmonth','month','NUMERIC(10)',null,'0')
;

-- 2021-08-27T13:48:05.143Z
-- URL zum Konzept
UPDATE C_MembershipMonth SET month=0 WHERE month IS NULL
;

