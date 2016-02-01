
UPDATE AD_SysConfig
SET 
	name = replace(name, 'org.adempiere.event', 'de.metas.event'),
	EntityType='de.metas.event',
	Updated=now(), UpdatedBy=99
WHERE Name like 'org.adempiere.event%'
;

