--
-- Script: /tmp/webui_migration_scripts_2025-12-12_8954853235809741198/5781010_migration_2025-12-15_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- 2025-12-15T07:38:39.591Z
UPDATE C_Incoterms SET Value='EXW',Updated=TO_TIMESTAMP('2025-12-15 07:38:39.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Incoterms_ID=540001
;

-- 2025-12-15T07:39:00.548Z
UPDATE C_Incoterms SET Value='FCA',Updated=TO_TIMESTAMP('2025-12-15 07:39:00.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Incoterms_ID=540002
;

-- 2025-12-15T07:39:07.685Z
UPDATE C_Incoterms SET Value='FAS',Updated=TO_TIMESTAMP('2025-12-15 07:39:07.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Incoterms_ID=540003
;

-- 2025-12-15T07:39:15.971Z
UPDATE C_Incoterms SET Value='FOB',Updated=TO_TIMESTAMP('2025-12-15 07:39:15.971000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Incoterms_ID=540004
;

-- 2025-12-15T07:39:24.529Z
UPDATE C_Incoterms SET Value='CFR',Updated=TO_TIMESTAMP('2025-12-15 07:39:24.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Incoterms_ID=540005
;

-- 2025-12-15T07:39:31.572Z
UPDATE C_Incoterms SET Value='CIF',Updated=TO_TIMESTAMP('2025-12-15 07:39:31.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Incoterms_ID=540006
;

-- 2025-12-15T07:39:39.739Z
UPDATE C_Incoterms SET Value='CPT',Updated=TO_TIMESTAMP('2025-12-15 07:39:39.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Incoterms_ID=540008
;

-- 2025-12-15T07:39:46.341Z
UPDATE C_Incoterms SET Value='CIP',Updated=TO_TIMESTAMP('2025-12-15 07:39:46.341000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Incoterms_ID=540009
;

-- 2025-12-15T07:39:53.276Z
UPDATE C_Incoterms SET Value='DAP',Updated=TO_TIMESTAMP('2025-12-15 07:39:53.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Incoterms_ID=540010
;

-- 2025-12-15T07:40:00.245Z
UPDATE C_Incoterms SET Value='DDP',Updated=TO_TIMESTAMP('2025-12-15 07:40:00.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Incoterms_ID=540011
;

-- 2025-12-15T07:40:07.785Z
UPDATE C_Incoterms SET Value='DPU',Updated=TO_TIMESTAMP('2025-12-15 07:40:07.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Incoterms_ID=540012
;

