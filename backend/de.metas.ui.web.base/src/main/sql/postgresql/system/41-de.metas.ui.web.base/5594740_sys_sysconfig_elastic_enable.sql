-- 2021-06-25T05:25:22.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541378,'S',TO_TIMESTAMP('2021-06-25 08:25:22','YYYY-MM-DD HH24:MI:SS'),100,'Sysconfig to enable/disable the whole elasticsearch integration. This affects KPIs, full text search etc.','D','Y','elastic_enable',TO_TIMESTAMP('2021-06-25 08:25:22','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;


update ad_sysconfig
set value=(select value from ad_sysconfig where name='de.metas.elasticsearch.PostKpiEvents')
where name='elastic_enable';


