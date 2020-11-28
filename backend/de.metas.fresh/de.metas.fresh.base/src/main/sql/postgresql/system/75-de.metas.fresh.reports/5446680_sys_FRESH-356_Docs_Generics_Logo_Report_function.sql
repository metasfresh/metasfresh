DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Logo_Report(IN ad_org_id numeric);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Generics_Logo_Report(IN ad_org_id numeric)
RETURNS TABLE
(imageurl character varying)
AS
$$
	SELECT COALESCE(imageurl,0::character varying) AS imageurl
	FROM ad_orginfo o 
	LEFT OUTER JOIN c_BPartner_location bpl ON o.orgbp_location_ID = bpl.c_BPartner_location_ID
	LEFT OUTER JOIN c_bpartner bp ON bpl.c_BPartner_ID = bp.C_BPartner_id 
	LEFT OUTER JOIN ad_image img ON (o.logo_id = img.ad_image_id OR bp.logo_id = img.ad_image_id)

	WHERE o.ad_org_id = $1 AND
	(CASE WHEN imageurl IS NULL THEN 1=1 ELSE (o.logo_id = img.ad_image_id OR bp.logo_id = img.ad_image_id) END)
$$
LANGUAGE SQL STABLE;
