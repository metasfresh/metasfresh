--
-- Script: /tmp/webui_migration_scripts_2025-09-18_4150809319507254020/5770070_migration_2025-09-19_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- 2025-09-19T13:19:03.671Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,101,TO_TIMESTAMP('2025-09-19 13:19:03.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540001,'Glas',TO_TIMESTAMP('2025-09-19 13:19:03.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'10000')
;

-- 2025-09-19T13:19:31.357Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,101,TO_TIMESTAMP('2025-09-19 13:19:31.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540002,'PPK',TO_TIMESTAMP('2025-09-19 13:19:31.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'20000')
;

-- 2025-09-19T13:19:40.250Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,101,TO_TIMESTAMP('2025-09-19 13:19:40.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540003,'Eisenmetalle',TO_TIMESTAMP('2025-09-19 13:19:40.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'30000')
;

-- 2025-09-19T13:19:48.266Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,101,TO_TIMESTAMP('2025-09-19 13:19:48.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540004,'Aluminium',TO_TIMESTAMP('2025-09-19 13:19:48.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'40000')
;

-- 2025-09-19T13:19:58.018Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,101,TO_TIMESTAMP('2025-09-19 13:19:58.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540005,'Kunststoffe',TO_TIMESTAMP('2025-09-19 13:19:58.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'50000')
;

-- 2025-09-19T13:20:07.324Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,101,TO_TIMESTAMP('2025-09-19 13:20:07.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540006,'Getränkekartonverpackungen',TO_TIMESTAMP('2025-09-19 13:20:07.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'60000')
;

-- 2025-09-19T13:20:18.142Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,101,TO_TIMESTAMP('2025-09-19 13:20:18.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540007,'Sonstige Verbundverpackungen	',TO_TIMESTAMP('2025-09-19 13:20:18.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'70000')
;

-- 2025-09-19T13:20:25.136Z
UPDATE M_PackageLicensing_MaterialGroup SET Name='Sonstige Verbundverpackungen',Updated=TO_TIMESTAMP('2025-09-19 13:20:25.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE M_PackageLicensing_MaterialGroup_ID=540007
;

-- 2025-09-19T13:20:36.487Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,101,TO_TIMESTAMP('2025-09-19 13:20:36.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540008,'Sonstige Materialien',TO_TIMESTAMP('2025-09-19 13:20:36.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'80000')
;

