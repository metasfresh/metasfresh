

-- before now, these columns were of length 60

-- 2022-01-11T15:22:47.332Z
-- URL zum Konzept
UPDATE AD_Column
SET FieldLength=150, Updated=TO_TIMESTAMP('2022-01-11 17:22:47', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 2902
;

-- 2022-01-11T15:22:53.811Z
-- URL zum Konzept
INSERT INTO t_alter_column
VALUES ('c_bpartner', 'Name', 'VARCHAR(150)', NULL, NULL)
;

-- 2022-01-11T15:23:12.464Z
-- URL zum Konzept
UPDATE AD_Column
SET FieldLength=150, Updated=TO_TIMESTAMP('2022-01-11 17:23:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 542713
;

-- 2022-01-11T15:23:18.148Z
-- URL zum Konzept
INSERT INTO t_alter_column
VALUES ('c_bpartner', 'CompanyName', 'VARCHAR(150)', NULL, NULL)
;







-- 2022-01-12T14:51:45.182Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=70,Updated=TO_TIMESTAMP('2022-01-12 16:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6936
;

-- 2022-01-12T14:51:48.407Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_paymentterm','Value','VARCHAR(70)',null,null)
;

-- 2022-01-12T14:52:00.650Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=70,Updated=TO_TIMESTAMP('2022-01-12 16:52:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2035
;




-- 2022-01-12T14:54:22.775Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=70,Updated=TO_TIMESTAMP('2022-01-12 16:54:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3174
;

-- 2022-01-12T14:54:26.443Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_paymentterm_trl','Name','VARCHAR(70)',null,null)
;




-- 2022-01-12T15:04:52.846Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_paymentterm_trl','Name','VARCHAR(70)',null,null)
;

-- 2022-01-12T15:05:11.548Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_paymentterm','Name','VARCHAR(70)',null,null)
;

-- 2022-01-12T15:05:21.734Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_paymentterm','Value','VARCHAR(70)',null,null)
;


