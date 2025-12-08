-- 2022-07-22T08:14:33.671Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='@#Date@',Updated=TO_TIMESTAMP('2022-07-22 08:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583754
;

-- 2022-07-22T08:14:45.819Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-07-22 08:14:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583754
;

-- 2022-07-22T08:31:37.678Z
-- URL zum Konzept
UPDATE AD_Column SET IsUseDocSequence='N',Updated=TO_TIMESTAMP('2022-07-22 08:31:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583760
;

-- 2022-07-22T08:32:01.998Z
-- URL zum Konzept
UPDATE AD_Column SET IsUseDocSequence='Y',Updated=TO_TIMESTAMP('2022-07-22 08:32:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583760
;


ALTER TABLE m_costrevaluation DROP COLUMN name;
ALTER TABLE m_costrevaluation DROP COLUMN description;

ALTER TABLE m_costrevaluationline DROP COLUMN name;
ALTER TABLE m_costrevaluationline DROP COLUMN description;
ALTER TABLE m_costrevaluationline DROP COLUMN cm_template_id;
ALTER TABLE m_costrevaluationline DROP COLUMN otherclause;
ALTER TABLE m_costrevaluationline DROP COLUMN whereclause;
ALTER TABLE m_costrevaluationline DROP COLUMN ad_table_id;

