-- Column: AD_Table.IsChangeLog
-- Column: AD_Table.IsChangeLog
-- 2025-03-28T14:12:00.424Z
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2025-03-28 14:12:00.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=8564
;

-- 2025-03-28T14:12:25.159Z
INSERT INTO t_alter_column values('ad_table','IsChangeLog','CHAR(1)',null,'Y')
;

-- 2025-03-28T14:12:25.983Z
UPDATE AD_Table SET IsChangeLog='Y' WHERE IsChangeLog IS NULL
;

-- Table: M_HU_Label_Config
-- Table: M_HU_Label_Config
-- 2025-03-28T14:16:00.756Z
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2025-03-28 14:16:00.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542272
;

