--
-- Script: /tmp/webui_migration_scripts_2026-08-06_1609493227235796762/10000040_migration_2026-08-06_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- 2026-08-06T14:45:03.447Z
INSERT INTO C_DocType_ReportElement (AD_Client_ID,AD_Org_ID,C_DocType_ID,C_DocType_ReportElement_ID,Created,CreatedBy,IsActive,IsHidden,ReportElement,Updated,UpdatedBy) VALUES (1000000,0,1000002,1000000,TO_TIMESTAMP('2026-08-06 14:45:03.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','DeliveryRule',TO_TIMESTAMP('2026-08-06 14:45:03.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-06T14:45:36.856Z
INSERT INTO C_DocType_ReportElement (AD_Client_ID,AD_Org_ID,C_DocType_ID,C_DocType_ReportElement_ID,Created,CreatedBy,IsActive,IsHidden,ReportElement,Updated,UpdatedBy) VALUES (1000000,0,1000002,1000001,TO_TIMESTAMP('2026-08-06 14:45:36.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','TotalAmount',TO_TIMESTAMP('2026-08-06 14:45:36.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-06T14:45:46.603Z
INSERT INTO C_DocType_ReportElement (AD_Client_ID,AD_Org_ID,C_DocType_ID,C_DocType_ReportElement_ID,Created,CreatedBy,IsActive,IsHidden,ReportElement,Updated,UpdatedBy) VALUES (1000000,0,1000002,1000002,TO_TIMESTAMP('2026-08-06 14:45:46.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Weight',TO_TIMESTAMP('2026-08-06 14:45:46.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
