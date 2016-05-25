--
-- expecting no FK for AD_Table, AD_Session and AD_PInstance, because we want to be able to "clean up" theses hables without getting trouble because of some ancient AD_ChangeLogs
--

-- 25.05.2016 13:18
-- URL zum Konzept
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2016-05-25 13:18:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554396
;

-- 25.05.2016 13:18
-- URL zum Konzept
UPDATE AD_Column SET DDL_NoForeignKey='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2016-05-25 13:18:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8813
;

-- 25.05.2016 13:18
-- URL zum Konzept
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2016-05-25 13:18:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8804
;

-- 25.05.2016 13:18
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2016-05-25 13:18:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8804
;

