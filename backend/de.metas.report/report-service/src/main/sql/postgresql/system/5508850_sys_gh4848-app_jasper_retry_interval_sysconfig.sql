-- 2018-12-21T09:22:10.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,Created,CreatedBy,IsActive,ConfigurationLevel,Updated,UpdatedBy,AD_SysConfig_ID,Value,AD_Org_ID,Name,EntityType,Description) VALUES (0,TO_TIMESTAMP('2018-12-21 09:22:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','S',TO_TIMESTAMP('2018-12-21 09:22:10','YYYY-MM-DD HH24:MI:SS'),100,541253,'60000',0,'de.metas.report.jasper.client.ServiceConnectionExceptionRetryAdvisedInMillis','de.metas.adempiere.adempiereJasper','If the jasper client gets a java.net.ConnectException, it throws a metasfresh ServiceConnectionException. This parameter specifies the ServiceConnectionException''s retryAdvisedInMillis property.
Values <= 0 mean "don''t retry".
Note that we assume that a java.net.ConnectException means that the jasper server is temporarily out of order.')
;

-- 2018-12-21T09:22:19.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2018-12-21 09:22:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541253
;

-- 2018-12-21T09:57:12.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S',Updated=TO_TIMESTAMP('2018-12-21 09:57:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541253
;

-- 2018-12-21T10:32:17.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2018-12-21 10:32:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540655
;

