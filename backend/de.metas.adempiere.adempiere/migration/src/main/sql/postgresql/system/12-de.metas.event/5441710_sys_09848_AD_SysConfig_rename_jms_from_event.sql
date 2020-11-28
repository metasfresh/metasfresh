
UPDATE AD_SysConfig SET Name = replace(Name, 'de.metas.event.jms', 'de.metas.jms') WHERE Name ilike 'de.metas.event.jms%';
