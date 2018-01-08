
UPDATE AD_SysConfig 
SET 
	Name='de.metas.elasticsearch.PostKpiEvents',
	Description='If set to Y, then the system will send KPI events to an elastic search server.
If you need to prevent the running system to try to connect to an ES server, then add elastic_enable=false to application.properties, or start the process with -Delastic_enable=false'
WHERE 
	Name='de.metas.elasticsearch.Enabled';
	
--update ad_sysconfig set value='N' where name='de.metas.elasticsearch.PostKpiEvents';
