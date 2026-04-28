

DROP FUNCTION IF EXISTS de_metas_device.change_hostname(character varying, character varying);
CREATE OR REPLACE FUNCTION de_metas_device.change_hostname(current_hostname character varying, new_hostname character varying)
  RETURNS void AS
$BODY$

--select * FROM AD_SysConfig WHERE Name ilike 'de.metas.device.%.AvailableOn%' AND Value='PC-018';

UPDATE AD_SysConfig
SET Value=$2, Updated=now(), UpdatedBy=99
WHERE Name ilike 'de.metas.device.%.AvailableOn%' AND Value=$1
;

--select Name, replace(Name, 'PC-042', 'neu') FROM AD_SysConfig WHERE Name ilike 'de.metas.device.%'||'PC-042'||'%'

UPDATE AD_SysConfig
SET Name=replace(Name, $1, $2), Updated=now(), UpdatedBy=99
WHERE Name ilike 'de.metas.device.%'||$1||'%'
;

--select Name, replace(Name, 'PC-018', 'neu') FROM AD_SysConfig WHERE Name ilike 'CLogger.Level.'||'PC-018'||'%'
UPDATE AD_SysConfig
SET Name=replace(Name, $1, $2), Updated=now(), UpdatedBy=99
WHERE Name ilike 'CLogger.Level.'||$1||'%'


$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
COMMENT ON FUNCTION de_metas_device.change_hostname(character varying, character varying) IS
'Changes device and hostname related AD_SysConfig settings. Can be used if the same devices and loggin settings shall remain and just the hostname of the respective workstation shall be changed';
