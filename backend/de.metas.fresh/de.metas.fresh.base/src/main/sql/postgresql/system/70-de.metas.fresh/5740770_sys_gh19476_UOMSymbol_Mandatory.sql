--
-- Script: /tmp/webui_migration_scripts_2024-10-01_535955746742559566/5740770_migration_2024-12-02_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- Column: C_UOM.UOMSymbol
-- 2024-12-02T08:44:26.376Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-12-02 08:44:26.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=855
;

-- 2024-12-02T08:45:17.341Z
INSERT INTO t_alter_column values('c_uom','UOMSymbol','VARCHAR(10)',null,null)
;

-- 2024-12-02T08:45:17.344Z
INSERT INTO t_alter_column values('c_uom','UOMSymbol',null,'NOT NULL',null)
;

