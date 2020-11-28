

--
-- column size of ad_issue.Stacktrace and errortrace
-- 
-- 08.10.2015 15:00
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=999999, Updated=TO_TIMESTAMP('2015-10-08 15:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=14663
;

-- 08.10.2015 15:00
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_issue','ErrorTrace','TEXT',null,'NULL')
;

-- 08.10.2015 15:01
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=999999, Updated=TO_TIMESTAMP('2015-10-08 15:01:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=14664
;

-- 08.10.2015 15:01
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_issue','StackTrace','TEXT',null,'NULL')
;
