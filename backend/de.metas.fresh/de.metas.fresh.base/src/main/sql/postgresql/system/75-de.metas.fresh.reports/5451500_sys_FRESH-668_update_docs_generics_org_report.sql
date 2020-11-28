DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report( IN Email Character Varying(60), IN IsSOTrx Character Varying(1), IN AD_Org_ID Numeric );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report 
(
	Name Character Varying(60),
	Address Character Varying(100),
	VATaxID Character Varying(60),
	Phone Character Varying,
	Fax Character Varying,
	Postal Character Varying(10),
	City Character Varying(60),
	Email Character Varying(60)
);

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report( IN Email Character Varying(60), IN IsSOTrx Character Varying(1), IN AD_Org_ID Numeric ) 
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report
AS
$$
(
SELECT
	org_bp.name AS name,
	loc.address1 AS address,
	org_bp.vataxid,
	COALESCE(us.phone, org_bpl.phone, '-') AS phone,
	COALESCE(us.fax, org_bpl.fax, '-') AS fax,
	loc.postal,
	loc.city,
	COALESCE($1, us.email) as email
FROM
	ad_org ad_org
	INNER JOIN c_bpartner org_bp ON ad_org.ad_org_id = org_bp.ad_orgbp_id AND org_bp.isActive = 'Y'
	INNER JOIN C_BPartner_Location org_bpl ON org_bpl.C_BPartner_Location_ID =
	(
		SELECT C_BPartner_Location_ID FROM C_BPartner_Location sub_bpl
		WHERE sub_bpl.c_bpartner_id = org_bp.c_bpartner_id AND sub_bpl.isActive = 'Y'
		ORDER BY IsBillToDefault DESC, IsBillTo DESC
		LIMIT 1
	)
	INNER JOIN c_location loc ON org_bpl.c_location_id = loc.c_location_id AND loc.isActive = 'Y'
	LEFT OUTER JOIN c_country cou ON loc.c_country_id = cou.c_country_id AND cou.isActive = 'Y'
	LEFT OUTER JOIN AD_User us ON us.AD_User_ID = 
	(
		SELECT AD_User_ID FROM AD_User sub_us
		WHERE org_bp.c_bpartner_id = sub_us.c_bpartner_id AND sub_us.isActive = 'Y'
		AND (($2 = 'Y' AND IsSalesContact = 'Y') OR ($2 = 'N' AND IsPurchaseContact = 'Y'))
		ORDER BY IsDefaultContact DESC
		LIMIT 1
	)
WHERE
	ad_org.ad_org_id = $3 AND ad_org.isActive = 'Y'
)
$$
LANGUAGE sql STABLE 
;
