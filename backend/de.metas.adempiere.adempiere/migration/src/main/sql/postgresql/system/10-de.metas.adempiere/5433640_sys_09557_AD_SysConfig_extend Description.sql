

-- 18.11.2015 07:15
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=2000,Updated=TO_TIMESTAMP('2015-11-18 07:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=50197
;

COMMIT;
-- 18.11.2015 07:15
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_sysconfig','Description','VARCHAR(4000)',null,'NULL')
;
