-- 2021-09-28T14:18:37.659Z
-- URL zum Konzept
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2021-09-28 16:18:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=5951
;

-- 2021-09-28T14:27:50.662Z
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_pinstance','AD_User_ID','NUMERIC(10)',null,null)
;

ALTER TABLE ad_pinstance
    DROP CONSTRAINT aduser_pinstance;
