--
-- Script: /tmp/webui_migration_scripts_2025-03-21_6485551170027435096/5749850_migration_2025-03-21_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- 2025-03-21T14:17:30.357Z
UPDATE EXP_Format SET WhereClause='IsActive=''Y''',Updated=TO_TIMESTAMP('2025-03-21 14:17:30.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540419
;

-- 2025-03-21T14:18:32.089Z
UPDATE EXP_Format SET WhereClause='EDI_DesadvLine_ID not in (select pi.EDI_DesadvLine_ID from Edi_Desadv_Pack_Item pi join Edi_Desadv_Pack p on p.Edi_Desadv_Pack_ID=pi.Edi_Desadv_Pack_ID where p.IsActive=''Y'' and pi.IsActive=''Y'')',Updated=TO_TIMESTAMP('2025-03-21 14:18:32.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540422
;

