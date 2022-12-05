-- 2022-11-16T13:15:15.950Z
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Service_ID,IsActive,Name,Type,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2022-11-16 15:15:15','YYYY-MM-DD HH24:MI:SS'),100,540010,'Y','REST API','metasfresh',TO_TIMESTAMP('2022-11-16 15:15:15','YYYY-MM-DD HH24:MI:SS'),100,'defaultRestAPIMetasfresh')
;

-- 2022-11-16T13:15:23.780Z
UPDATE ExternalSystem_Service SET Description='/metasfresh',Updated=TO_TIMESTAMP('2022-11-16 15:15:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540010
;

-- 2022-11-16T13:15:31.482Z
UPDATE ExternalSystem_Service SET EnableCommand='enableRestAPI',Updated=TO_TIMESTAMP('2022-11-16 15:15:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540010
;

-- 2022-11-16T13:15:40.474Z
UPDATE ExternalSystem_Service SET DisableCommand='disableRestAPI',Updated=TO_TIMESTAMP('2022-11-16 15:15:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540010
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- 2022-11-16T17:13:24.669Z
UPDATE AD_Process_Trl SET Description='Prozess zum Aktivieren/Deaktivieren des HTTP-Rest-Endpunkts von metasfresh (''/camel/metasfresh''), der für die Arbeit mit großen json-Anfragen verwendet wird.',Updated=TO_TIMESTAMP('2022-11-16 19:13:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585141
;

-- Value: Call_External_System_Metasfresh
-- Classname: de.metas.externalsystem.process.InvokeMetasfreshAction
-- 2022-11-16T17:13:27.688Z
UPDATE AD_Process SET Description='Prozess zum Aktivieren/Deaktivieren des HTTP-Rest-Endpunkts von metasfresh (''/camel/metasfresh''), der für die Arbeit mit großen json-Anfragen verwendet wird.', Help=NULL, Name='metasfresh aufrufen',Updated=TO_TIMESTAMP('2022-11-16 19:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585141
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- 2022-11-16T17:13:27.628Z
UPDATE AD_Process_Trl SET Description='Prozess zum Aktivieren/Deaktivieren des HTTP-Rest-Endpunkts von metasfresh (''/camel/metasfresh''), der für die Arbeit mit großen json-Anfragen verwendet wird.',Updated=TO_TIMESTAMP('2022-11-16 19:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585141
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- 2022-11-16T17:13:29.940Z
UPDATE AD_Process_Trl SET Description='Prozess zum Aktivieren/Deaktivieren des HTTP-Rest-Endpunkts von metasfresh (''/camel/metasfresh''), der für die Arbeit mit großen json-Anfragen verwendet wird.',Updated=TO_TIMESTAMP('2022-11-16 19:13:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585141
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- 2022-11-16T17:13:31.299Z
UPDATE AD_Process_Trl SET Description='Prozess zum Aktivieren/Deaktivieren des HTTP-Rest-Endpunkts von metasfresh (''/camel/metasfresh''), der für die Arbeit mit großen json-Anfragen verwendet wird.',Updated=TO_TIMESTAMP('2022-11-16 19:13:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585141
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- 2022-11-16T17:13:40.335Z
UPDATE AD_Process_Trl SET Description='Process used to enable/disable metasfresh''s HTTP rest endpoint (''/camel/metasfresh'') which is used for working with huge json requests.',Updated=TO_TIMESTAMP('2022-11-16 19:13:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585141
;
