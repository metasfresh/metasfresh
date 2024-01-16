-- On systems that have just the standard-org, it's safe to change the api_audit_configs to Org=*.
-- Without any api_audit_config with Org=*, 
-- there is no API request auditing if the AD_User that invokes the API has AD_Org_ID=0
UPDATE api_audit_config
SET AD_org_ID=0, updated='2022-11-17 10:59', updatedby=99
WHERE ad_org_id = 1000000
  AND NOT EXISTS(select 1 from ad_org where isactive='Y' and ad_org_id not in (0, 1000000));
;