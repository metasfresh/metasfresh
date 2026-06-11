--
-- Script: /tmp/webui_migration_scripts_2026-02-23_7269862644127664316/5789830_migration_2026-02-23_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- 2026-02-23T14:19:05.493Z
UPDATE C_DocType SET IsDefault='N',Updated=TO_TIMESTAMP('2026-02-23 14:19:05.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=540971
;

-- 2026-02-23T14:19:09.493Z
UPDATE C_DocType SET IsDefault='Y',Updated=TO_TIMESTAMP('2026-02-23 14:19:09.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=1000023
;

