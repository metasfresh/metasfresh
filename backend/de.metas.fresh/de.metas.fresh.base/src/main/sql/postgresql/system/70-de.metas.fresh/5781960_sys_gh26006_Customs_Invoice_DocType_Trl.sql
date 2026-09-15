--
-- Script: /tmp/webui_migration_scripts_2025-12-17_7755729466004334304/5781960_migration_2025-12-18_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- 2025-12-18T08:40:11.077Z
UPDATE C_DocType_Trl SET Name='Zollrechnung',Updated=TO_TIMESTAMP('2025-12-18 08:40:11.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=540973
;

-- 2025-12-18T08:40:11.078Z
UPDATE C_DocType base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM C_DocType_Trl trl  WHERE trl.C_DocType_ID=base.C_DocType_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-18T08:40:12.149Z
UPDATE C_DocType_Trl SET PrintName='Zollrechnung',Updated=TO_TIMESTAMP('2025-12-18 08:40:12.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=540973
;

-- 2025-12-18T08:40:12.149Z
UPDATE C_DocType base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM C_DocType_Trl trl  WHERE trl.C_DocType_ID=base.C_DocType_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-18T08:40:13.634Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-18 08:40:13.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=540973
;

