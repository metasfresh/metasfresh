DROP FUNCTION IF EXISTS report.getPartnerExternalID(numeric)
;

CREATE FUNCTION report.getPartnerExternalID(p_record_id numeric)
    RETURNS character varying
    STABLE
    LANGUAGE sql
AS
$$
SELECT exr.ExternalReference AS External_ID
FROM S_ExternalReference exr
WHERE exr.Type = 'BPartner'
  AND exr.ExternalSystem = 'Other'
  AND exr.referenced_ad_table_id = 291
  AND exr.record_id = p_record_id
$$
;

