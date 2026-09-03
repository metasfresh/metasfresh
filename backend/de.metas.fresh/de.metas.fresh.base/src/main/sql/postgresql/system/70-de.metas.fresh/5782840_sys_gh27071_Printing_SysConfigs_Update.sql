--
-- Script: /tmp/webui_migration_scripts_2025-12-19_6217244405075566059/5782840_migration_2026-01-05_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- SysConfig Name: de.metas.printing.StorePDFBaseDirectory
-- SysConfig Value: /tmp/
-- 2026-01-05T09:02:48.110Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', IsActive='Y',Updated=TO_TIMESTAMP('2026-01-05 09:02:48.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541325
;

-- SysConfig Name: de.metas.printing.StorePDFBaseDirectory
-- SysConfig Value: /home/metasfresh/print_share
-- SysConfig Value (old): /tmp/
-- 2026-01-05T09:02:55.873Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Value='/home/metasfresh/print_share',Updated=TO_TIMESTAMP('2026-01-05 09:02:55.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541325
;

-- SysConfig Name: de.metas.printing.process.ConcatenatePdfs.OutputDir
-- SysConfig Value: /home/metasfresh/concat_pdf_share
-- SysConfig Value (old): /home/adempiere/
-- 2026-01-05T09:08:19.977Z
UPDATE AD_SysConfig SET ConfigurationLevel='C', Value='/home/metasfresh/concat_pdf_share',Updated=TO_TIMESTAMP('2026-01-05 09:08:19.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=540267
;

