-- Column: AD_Sequence_No.CalendarMonth
-- Column: AD_Sequence_No.CalendarMonth
-- 2023-01-03T08:38:34.181Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-03 10:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585423
;

-- 2023-01-03T08:38:34.968Z
INSERT INTO t_alter_column values('ad_sequence_no','CalendarMonth','VARCHAR(2)',null,null)
;

-- Column: AD_Sequence_No.CalendarMonth
-- Column: AD_Sequence_No.CalendarMonth
-- 2023-01-03T08:43:20.569Z
UPDATE AD_Column SET IsKey='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-03 10:43:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585423
;

-- 2023-01-03T08:43:20.982Z
INSERT INTO t_alter_column values('ad_sequence_no','CalendarMonth','VARCHAR(2)',null,null)
;

-- Column: AD_Sequence_No.CalendarMonth
-- Column: AD_Sequence_No.CalendarMonth
-- 2023-01-03T08:44:39.276Z
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-03 10:44:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585423
;

-- Column: AD_Sequence_No.CalendarMonth
-- Column: AD_Sequence_No.CalendarMonth
-- 2023-01-03T08:44:52.347Z
UPDATE AD_Column SET DefaultValue='1', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-03 10:44:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585423
;

-- 2023-01-03T08:44:52.785Z
INSERT INTO t_alter_column values('ad_sequence_no','CalendarMonth','VARCHAR(2)',null,'1')
;

-- 2023-01-03T08:44:52.790Z
UPDATE AD_Sequence_No SET CalendarMonth='1' WHERE CalendarMonth IS NULL
;

-- 2023-01-03T08:44:52.791Z
INSERT INTO t_alter_column values('ad_sequence_no','CalendarMonth',null,'NOT NULL',null)
;

-- include CalendarMonth in primary key
ALTER TABLE AD_Sequence_no
    DROP CONSTRAINT ad_sequence_no_pkey,
    ADD CONSTRAINT ad_sequence_no_pkey PRIMARY KEY (AD_Sequence_ID, CalendarYear, CalendarMonth)
;