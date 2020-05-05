--
-- i stumbled over this while syncing the ClientUpdateValidator with metasfresh-parent/pom.xml
--
DELETE FROM AD_SysConfig WHERE Name='de.metas.clientcheck.RestartClient';
