--
-- SEPA_MandateRefNo not mandatory, since we plainly don't export is as of now
--
-- 07.12.2015 07:30
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2015-12-07 07:30:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550927
;

-- 07.12.2015 07:30
-- URL zum Konzept
INSERT INTO t_alter_column values('sepa_export_line','SEPA_MandateRefNo','VARCHAR(50)',null,'NULL')
;

-- 07.12.2015 07:30
-- URL zum Konzept
INSERT INTO t_alter_column values('sepa_export_line','SEPA_MandateRefNo',null,'NULL',null)
;
