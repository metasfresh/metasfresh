-- 23.12.2016 16:42
-- URL zum Konzept
INSERT INTO t_alter_column values('m_hu_item','M_HU_PI_Item_ID','NUMERIC(10)',null,'NULL')
;

-- 23.12.2016 16:43
-- URL zum Konzept
INSERT INTO t_alter_column values('m_hu_item_snapshot','M_HU_PI_Item_ID','NUMERIC(10)',null,'NULL')
;

COMMIT;

-- 23.12.2016 16:42
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, IsMandatory='N',Updated=TO_TIMESTAMP('2016-12-23 16:42:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549263
;

-- 23.12.2016 16:43
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-12-23 16:43:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552340
;

