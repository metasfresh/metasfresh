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

-- 2025-09-19T16:47:29.340Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,108,TO_TIMESTAMP('2025-09-19 16:47:29.339000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540016,'Aluminium',TO_TIMESTAMP('2025-09-19 16:47:29.339000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'90000')
;

-- 2025-09-19T16:47:57.925Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,108,TO_TIMESTAMP('2025-09-19 16:47:57.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540017,'Eisenmetall',TO_TIMESTAMP('2025-09-19 16:47:57.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'110000')
;

-- 2025-09-19T16:48:18.422Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,108,TO_TIMESTAMP('2025-09-19 16:48:18.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540018,'Getränke PET Verpackung',TO_TIMESTAMP('2025-09-19 16:48:18.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'120000')
;

-- 2025-09-19T16:48:33.738Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,108,TO_TIMESTAMP('2025-09-19 16:48:33.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540019,'Getränke Verbundverpackung',TO_TIMESTAMP('2025-09-19 16:48:33.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'130000')
;

-- 2025-09-19T16:48:56.723Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,108,TO_TIMESTAMP('2025-09-19 16:48:56.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540020,'Glas',TO_TIMESTAMP('2025-09-19 16:48:56.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'140000')
;

-- 2025-09-19T16:49:13.826Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,108,TO_TIMESTAMP('2025-09-19 16:49:13.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540021,'Holz',TO_TIMESTAMP('2025-09-19 16:49:13.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'15000')
;

-- 2025-09-19T16:49:23.597Z
UPDATE M_PackageLicensing_MaterialGroup SET Value='150000',Updated=TO_TIMESTAMP('2025-09-19 16:49:23.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE M_PackageLicensing_MaterialGroup_ID=540021
;

-- 2025-09-19T16:49:45.408Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,108,TO_TIMESTAMP('2025-09-19 16:49:45.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540022,'Kunststoff',TO_TIMESTAMP('2025-09-19 16:49:45.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'160000')
;

-- 2025-09-19T16:50:02.193Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,108,TO_TIMESTAMP('2025-09-19 16:50:02.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540023,'PPK',TO_TIMESTAMP('2025-09-19 16:50:02.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'170000')
;

-- 2025-09-19T16:50:28.521Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,108,TO_TIMESTAMP('2025-09-19 16:50:28.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540024,'Textile Faserstoffe',TO_TIMESTAMP('2025-09-19 16:50:28.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'180000')
;

-- 2025-09-19T16:50:43.553Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,108,TO_TIMESTAMP('2025-09-19 16:50:43.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540025,'Weißblech',TO_TIMESTAMP('2025-09-19 16:50:43.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'190000')
;

-- 2025-09-19T16:55:37.715Z
INSERT INTO M_PackageLicensing_MaterialGroup (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,IsActive,M_PackageLicensing_MaterialGroup_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,108,TO_TIMESTAMP('2025-09-19 16:55:37.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540026,'Sonstige Materialverbunde',TO_TIMESTAMP('2025-09-19 16:55:37.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'120000')
;
