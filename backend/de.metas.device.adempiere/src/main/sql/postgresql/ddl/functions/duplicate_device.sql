DROP FUNCTION IF EXISTS de_metas_device.duplicate_device(text, text);

CREATE OR REPLACE FUNCTION de_metas_device.duplicate_device(
    p_device_name text,
    p_new_device_name text DEFAULT NULL::text)
  RETURNS void AS
$BODY$
DECLARE
	v_new_device_name_to_use text;
BEGIN

	IF (p_new_device_name IS NULL) 
	THEN
		v_new_device_name_to_use := p_device_name || '_copy';
	ELSE
		v_new_device_name_to_use := p_new_device_name;
	END IF;

	INSERT INTO AD_SysConfig(
	  ad_sysconfig_id, -- numeric(10,0) NOT NULL,
	  ad_client_id, -- numeric(10,0) NOT NULL,
	  ad_org_id, -- numeric(10,0) NOT NULL,
	  created, -- timestamp with time zone NOT NULL,
	  updated, -- timestamp with time zone NOT NULL,
	  createdby, -- numeric(10,0) NOT NULL,
	  updatedby, -- numeric(10,0) NOT NULL,
	  isactive, -- character(1) NOT NULL DEFAULT 'Y'::bpchar,
	  name, -- character varying(255) NOT NULL,
	  value, -- character varying(255) NOT NULL,
	  description, -- character varying(4000),
	  entitytype, -- character varying(40) NOT NULL DEFAULT 'U'::character varying,
	  configurationlevel -- character(1) DEFAULT 'S'::bpchar,
	)
	SELECT 
	  nextval('ad_sysconfig_seq') as ad_sysconfig_id, -- numeric(10,0) NOT NULL,
	  ad_client_id, -- numeric(10,0) NOT NULL,
	  ad_org_id, -- numeric(10,0) NOT NULL,
	  now() as created, -- timestamp with time zone NOT NULL,
	  now() as updated, -- timestamp with time zone NOT NULL,
	  99 as createdby, -- numeric(10,0) NOT NULL,
	  99 as updatedby, -- numeric(10,0) NOT NULL,
	  'N' as isactive, -- character(1) NOT NULL DEFAULT 'Y'::bpchar,
	  replace(name, p_device_name, v_new_device_name_to_use) as name, -- character varying(255) NOT NULL,
	  replace(value, p_device_name, v_new_device_name_to_use) as value, -- character varying(255) NOT NULL,
	  description, -- character varying(4000),
	  entitytype, -- character varying(40) NOT NULL DEFAULT 'U'::character varying,
	  configurationlevel -- character(1) DEFAULT 'S'::bpchar,
	FROM AD_SysConfig 
	WHERE Name ilike 'de.metas.device.'||p_device_name||'.%';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION de_metas_device.duplicate_device(text, text)
  OWNER TO metasfresh;
COMMENT ON FUNCTION de_metas_device.duplicate_device(text, text) 
IS 'This function copies all AD_SysConfig records whose names start with "de.metas.device.<p_device_name>".
* The new records are created with IsActive=N.
* The second paramter "p_new_device_name" is optional. If ommitted, the copy-records are created with "de.metas.device.<p_device_name>_copy."
* The function does not create the sysconfig record "de.metas.device.NameN = <new-device-name>" that is needed to "aktivate the whole config."
* The function is intended to just do the more tedious work, and then a thinking human needs to check the result, tweak it and activate it.'
;
