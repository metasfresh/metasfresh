-- 21.09.2016 11:46
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL=NULL,Updated=TO_TIMESTAMP('2016-09-21 11:46:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550587
;

-- 21.09.2016 11:48
-- URL zum Konzept
ALTER TABLE M_ReceiptSchedule ADD IsPackagingMaterial CHAR(1) DEFAULT NULL  CHECK (IsPackagingMaterial IN ('Y','N'))
;

-- 21.09.2016 11:48
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2016-09-21 11:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550587
;

-- 21.09.2016 11:49
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2016-09-21 11:49:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550587
;

commit; 


-- 21.09.2016 11:49
-- URL zum Konzept
INSERT INTO t_alter_column values('m_receiptschedule','IsPackagingMaterial','CHAR(1)',null,'N')
;

commit;


-- 21.09.2016 11:49
-- URL zum Konzept
UPDATE M_ReceiptSchedule SET IsPackagingMaterial='N' WHERE IsPackagingMaterial IS NULL
;


commit;

-- 21.09.2016 11:49
-- URL zum Konzept
INSERT INTO t_alter_column values('m_receiptschedule','IsPackagingMaterial',null,'NOT NULL',null)
;

commit;


	
	