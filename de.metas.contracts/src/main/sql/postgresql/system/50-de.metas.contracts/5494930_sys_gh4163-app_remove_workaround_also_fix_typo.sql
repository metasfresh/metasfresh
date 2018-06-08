--INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541216,'S',TO_TIMESTAMP('2018-06-01 11:15:19','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','IntercetorEnabled_de.metas.contracts.interceptor.C_Flatrate_Term#preventOverlappingTerms_OnComplete',TO_TIMESTAMP('2018-06-01 11:15:19','YYYY-MM-DD HH24:MI:SS'),100,'N')
--;

-- remove the workaround, i.e. reenable the model interceptor
DELETE FROM AD_SysConfig where Name='IntercetorEnabled_de.metas.contracts.interceptor.C_Flatrate_Term#preventOverlappingTerms_OnComplete';

-- Fix the typo the AD_SysConfigs' prefixes (this is in sync with the constant in the javacode)
UPDATE AD_Sysconfig SET Name=replace(Name, 'IntercetorEnabled_', 'InterceptorEnabled_') WHERE Name like 'IntercetorEnabled_%';
