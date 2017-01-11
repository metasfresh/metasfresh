-- 04.01.2017 15:57
-- URL zum Konzept
UPDATE AD_Column SET ValueMin='0',Updated=TO_TIMESTAMP('2017-01-04 15:57:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546002
;

commit;
-- 04.01.2017 15:57
-- URL zum Konzept
INSERT INTO t_alter_column values('c_flatrate_dataentry','FlatrateAmtPerUOM','NUMERIC',null,'NULL')
;

