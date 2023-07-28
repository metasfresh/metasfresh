-- 2023-07-26T18:58:25.381Z
INSERT INTO ExternalSystem_Config (AD_Client_ID,AD_Org_ID,AuditFileFolder,Created,CreatedBy,ExternalSystem_Config_ID,IsActive,Name,Type,Updated,UpdatedBy,WriteAudit) VALUES (1000000,1000000,'/app/data/audit',TO_TIMESTAMP('2023-07-26 20:58:25','YYYY-MM-DD HH24:MI:SS'),100,540016,'Y','print-to-remote-folder','PC',TO_TIMESTAMP('2023-07-26 20:58:25','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2023-07-26T18:59:34.271Z
INSERT INTO ExternalSystem_Config_PrintingClient (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Config_ID,ExternalSystem_Config_PrintingClient_ID,ExternalSystemValue,IsActive,Target_Directory,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2023-07-26 20:59:34','YYYY-MM-DD HH24:MI:SS'),100,540016,540000,'PrintingClient','Y','/app/data/printing_client',TO_TIMESTAMP('2023-07-26 20:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-26T18:59:54.109Z
UPDATE ExternalSystem_Config SET IsActive='N',Updated=TO_TIMESTAMP('2023-07-26 20:59:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_ID=1000000
;

INSERT INTO public.ad_printerhw (ad_client_id, ad_org_id, ad_printerhw_id, created, createdby, isactive, updated, updatedby, description, name, hostkey, outputtype, externalsystem_config_id) VALUES (1000000, 1000000, 540332, '2023-07-26 19:01:23.000000 +00:00', 100, 'Y', '2023-07-26 19:02:32.000000 +00:00', 100, null, 'print-to-externalsystems', null, 'Queue', 540016)
;

