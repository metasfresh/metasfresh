--
-- Script: /tmp/webui_migration_scripts_2025-09-17_7172186814874100276/5769330_migration_2025-09-17_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- 2025-09-17T14:43:27.917Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556531,TO_TIMESTAMP('2025-09-17 14:43:27.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,1,'Y','N','Y','N','DocumentNo_ExternalSystem',1000000,TO_TIMESTAMP('2025-09-17 14:43:27.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-17T14:43:36.976Z
INSERT INTO ExternalSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-09-17 14:43:36.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540001,'Y','Alberta',TO_TIMESTAMP('2025-09-17 14:43:36.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ALBERTA')
;

-- 2025-09-17T14:44:02.463Z
INSERT INTO ExternalSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-09-17 14:44:02.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540002,'Y','RabbitMQRESTAPI',TO_TIMESTAMP('2025-09-17 14:44:02.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RabbitMQ')
;

-- 2025-09-17T14:44:18.356Z
UPDATE ExternalSystem SET Name='RabbitMQ', Value='RabbitMQRESTAPI',Updated=TO_TIMESTAMP('2025-09-17 14:44:18.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE ExternalSystem_ID=540002
;

-- 2025-09-17T14:44:39.758Z
INSERT INTO ExternalSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-09-17 14:44:39.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540003,'Y','WOO Commerce',TO_TIMESTAMP('2025-09-17 14:44:39.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'WooCommerce')
;

-- 2025-09-17T14:44:54.076Z
INSERT INTO ExternalSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-09-17 14:44:54.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540004,'Y','GRS Signum',TO_TIMESTAMP('2025-09-17 14:44:54.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GRSSignum')
;

-- 2025-09-17T14:45:06.363Z
INSERT INTO ExternalSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-09-17 14:45:06.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540005,'Y','Leich und Mehl',TO_TIMESTAMP('2025-09-17 14:45:06.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'LeichUndMehl')
;

-- 2025-09-17T14:45:19.037Z
INSERT INTO ExternalSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-09-17 14:45:19.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540006,'Y','Pro Care Management',TO_TIMESTAMP('2025-09-17 14:45:19.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ProCareManagement')
;

-- 2025-09-17T14:45:31.910Z
INSERT INTO ExternalSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-09-17 14:45:31.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540007,'Y','Shopware 6',TO_TIMESTAMP('2025-09-17 14:45:31.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Shopware6')
;

-- 2025-09-17T14:45:42.020Z
INSERT INTO ExternalSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-09-17 14:45:42.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540008,'Y','Other',TO_TIMESTAMP('2025-09-17 14:45:42.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Other')
;

-- 2025-09-17T14:45:54.054Z
INSERT INTO ExternalSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-09-17 14:45:54.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540009,'Y','Printing Client',TO_TIMESTAMP('2025-09-17 14:45:54.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PrintingClient')
;

-- 2025-09-17T14:46:02.219Z
INSERT INTO ExternalSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-09-17 14:46:02.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540010,'Y','Github',TO_TIMESTAMP('2025-09-17 14:46:02.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Github')
;

-- 2025-09-17T14:46:08.812Z
INSERT INTO ExternalSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-09-17 14:46:08.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540011,'Y','Everhour',TO_TIMESTAMP('2025-09-17 14:46:08.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Everhour')
;
