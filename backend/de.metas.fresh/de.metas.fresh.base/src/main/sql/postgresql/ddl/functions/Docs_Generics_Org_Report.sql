DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report( IN p_Email Character Varying(60), IN p_IsSOTrx Character Varying(1), IN p_AD_Org_ID Numeric );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report( IN p_Email Character Varying(60), IN p_IsSOTrx Character Varying(1), IN p_AD_Org_ID Numeric )
    RETURNS TABLE
            (
                Name        Character Varying(60),
                description varchar(255),
                Address     Character Varying(100),
                VATaxID     Character Varying(60),
                TaxID       Character Varying(60),
                Phone       Character Varying,
                Phone2      Character Varying,
                Fax         Character Varying,
                Postal      Character Varying(10),
                City        Character Varying(60),
                gln         varchar,
                Email       Character Varying(60),
                URL         Character Varying
            )


AS
$$
(
SELECT
	org_bp.name AS name,
    org_bp.description,
	loc.address1 AS address,
	org_bp.vataxid,
	org_bp.taxid,
	COALESCE(us.phone, org_bpl.phone, '-') AS phone,
	us.phone2,
	COALESCE(us.fax, org_bpl.fax, '-') AS fax,
	loc.postal,
	loc.city,
	org_bpl.gln,
	COALESCE(p_Email, us.email) as email,
	org_bp.URL
FROM
	ad_org ad_org
	INNER JOIN c_bpartner org_bp ON ad_org.ad_org_id = org_bp.ad_orgbp_id 
	INNER JOIN C_BPartner_Location org_bpl ON org_bpl.C_BPartner_Location_ID =
	(
		SELECT C_BPartner_Location_ID FROM C_BPartner_Location sub_bpl
		WHERE sub_bpl.c_bpartner_id = org_bp.c_bpartner_id 
		ORDER BY IsBillToDefault DESC, IsBillTo DESC
		LIMIT 1
	)
	INNER JOIN c_location loc ON org_bpl.c_location_id = loc.c_location_id 
	LEFT OUTER JOIN c_country cou ON loc.c_country_id = cou.c_country_id
	LEFT OUTER JOIN AD_User us ON us.AD_User_ID = 
	(
		SELECT AD_User_ID FROM AD_User sub_us
		WHERE org_bp.c_bpartner_id = sub_us.c_bpartner_id 
		AND ((p_IsSOTrx = 'Y' AND IsSalesContact = 'Y') OR (p_IsSOTrx = 'N' AND IsPurchaseContact = 'Y'))
		ORDER BY IsDefaultContact DESC
		LIMIT 1
	)
WHERE
	ad_org.ad_org_id = p_AD_Org_ID 
)
$$
LANGUAGE sql STABLE 
;
