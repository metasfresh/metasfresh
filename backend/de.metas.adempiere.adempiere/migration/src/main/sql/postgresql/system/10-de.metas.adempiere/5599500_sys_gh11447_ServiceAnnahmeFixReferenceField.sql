-- Reference field not read only and not always updatable
-- 2021-07-22T13:28:37.975Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2021-07-22 16:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=627585
;

-- 2021-07-22T13:40:51.392Z
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-07-22 16:40:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3799
;

-- 2021-07-22T13:41:33.345Z
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2021-07-22 16:41:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3799
;

-- 2021-07-22T13:43:16.477Z
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2021-07-22 16:43:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3799
;

-- 2021-07-22T13:45:25.641Z
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='N',Updated=TO_TIMESTAMP('2021-07-22 16:45:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3799
;

