-- 11.03.2016 15:27
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-11 15:27:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554154
;

-- 11.03.2016 15:29
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-11 15:29:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554149
;

COMMIT;

-- 11.03.2016 15:29
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','M_PriceList_ID','NUMERIC(10)',null,'NULL')
;

-- 11.03.2016 15:29
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','M_PriceList_ID',null,'NULL',null)
;

-- 11.03.2016 15:28
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_purchasecandidate','M_PriceList_ID','NUMERIC(10)',null,'NULL')
;

-- 11.03.2016 15:28
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_purchasecandidate','M_PriceList_ID',null,'NULL',null)
;

