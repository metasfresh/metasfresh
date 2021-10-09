DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report(org_id numeric, doctype text, bp_loc_id numeric, record_id numeric);
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report(p_org_id numeric, p_doctype text, p_bp_loc_id numeric, p_record_id numeric);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report( IN Email Character Varying(60), IN IsSOTrx Character Varying(1), IN AD_Org_ID Numeric );
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report( IN p_Email Character Varying(60), IN p_IsSOTrx Character Varying(1), IN p_AD_Org_ID Numeric );



DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report 
(
	Name Character Varying(60),
	Address Character Varying(100),
	VATaxID Character Varying(60),
	TaxID Character Varying(60),
	Phone Character Varying,
	Phone2 Character Varying,
	Fax Character Varying,
	Postal Character Varying(10),
	City Character Varying(60),
	gln varchar,
	Email Character Varying(60),
	URL Character Varying
);

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report( IN p_Email Character Varying(60), IN p_IsSOTrx Character Varying(1), IN p_AD_Org_ID Numeric ) 
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report
AS
$$
(
SELECT
	org_bp.name AS name,
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


DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_BPartner_Report;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Generics_BPartner_Report
(
  org_name        text,
  Org_AddressLine text,
  address1        text,
  postal          text,
  city            text,
  country         text,
  gln             text,
  AddressBlock    text
);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report(p_org_id numeric,
                                                                                            p_doctype text,
                                                                                            p_bp_loc_id numeric,
                                                                                            p_record_id numeric)
    RETURNS SETOF de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report AS
$BODY$
SELECT x.org_name,
       x.org_addressline,
       x.address1,
       x.postal,
       x.city,
       x.country,
       bpl.gln,

       CASE
           WHEN p_doctype = 'o'
               THEN o.BPartnerAddress
           WHEN p_doctype = 'o_delivery'
               THEN o.DeliveryToAddress
           WHEN p_doctype = 'io'
               THEN io.BPartnerAddress
           WHEN p_doctype = 'freshio'
               THEN freshio.BPartnerAddress
           WHEN p_doctype = 'i'
               THEN i.BPartnerAddress
           WHEN p_doctype = 'l'
               THEN tl.BPartnerAddress
           WHEN p_doctype = 'lt'
               THEN COALESCE(bpg.name || ' ', '') || COALESCE(bp.name || ' ', '') ||
                    COALESCE(bp.name2 || E'\n', E'\n') ||
                    COALESCE(letter.BPartnerAddress, '')
           WHEN p_doctype = 'd'
               THEN d.BPartnerAddress
           WHEN p_doctype = 'rfqr'
               THEN COALESCE(bprfqr.name || E'\n', '') || COALESCE(bplrfqr.address, '')
           WHEN p_doctype = 'ft'
               THEN COALESCE(bpft.name || E'\n', '') || COALESCE(bplft.address, '')
           WHEN p_doctype = 'mkt'
               THEN COALESCE(mktbp.name || E'\n', '') || COALESCE(mktbpl.address, '')
           WHEN p_doctype = 'ci'
               THEN ci.BPartnerAddress
           WHEN p_bp_loc_id IS NOT NULL
               THEN
               COALESCE(bp.name || E'\n', '') || COALESCE(bpl.address, '')
           ELSE 'Incompatible Parameter!'
           END || E'\n' AS addressblock
FROM (
         SELECT COALESCE(org_bp.name, '')  as org_name,
                trim(
                                    COALESCE(org_bp.name || ', ', '') ||
                                    COALESCE(loc.address1 || ' ', '') ||
                                    COALESCE(loc.postal || ' ', '') ||
                                    COALESCE(loc.city, '')
                    )                      as org_addressline,
                COALESCE(loc.address1, '') as address1,
                COALESCE(loc.postal, '')   as postal,
                COALESCE(loc.city, '')     as city,
                c.Name                     as country
         FROM ad_orginfo oi
                  JOIN c_bpartner_location org_bpl
                       ON org_bpl.c_bpartner_location_ID = oi.orgbp_location_id 
                  JOIN c_location loc ON org_bpl.c_location_id = loc.c_location_id
                  JOIN C_Country c ON loc.C_Country_ID = c.C_Country_ID
                  JOIN C_BPartner org_bp on org_bpl.c_bpartner_id = org_bp.c_bpartner_id
         WHERE oi.ad_org_id = p_org_id) x
         LEFT JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = p_bp_loc_id 
         LEFT JOIN C_BPartner bp ON bp.C_BPartner_ID = bpl.C_BPartner_ID 
         LEFT JOIN C_Greeting bpg ON bp.C_Greeting_id = bpg.C_Greeting_ID
         LEFT JOIN C_Location l ON bpl.C_Location_id = l.C_Location_ID 
         LEFT JOIN C_Country lcou ON l.C_Country_id = lcou.C_Country_ID 
         LEFT JOIN C_Region r ON l.C_Region_id = r.C_Region_ID 

         LEFT JOIN C_Order o ON o.C_Order_id = p_record_id 
         LEFT JOIN C_Invoice i ON i.C_Invoice_id = p_record_id
         LEFT JOIN T_Letter_Spool tl ON tl.AD_Pinstance_ID = p_record_id
         LEFT JOIN C_Letter letter ON letter.C_Letter_ID =  p_record_id
         LEFT JOIN M_InOut io ON io.M_InOut_ID = p_record_id
         LEFT JOIN C_Customs_Invoice ci on ci.C_Customs_Invoice_id = p_record_id

         LEFT JOIN C_Flatrate_Term ft ON ft.C_Flatrate_Term_ID = p_record_id
         LEFT JOIN C_BPartner_Location bplft
                   ON bplft.C_BPartner_Location_ID = ft.Bill_location_ID 
         LEFT JOIN C_BPartner bpft ON bpft.C_BPartner_ID = ft.Bill_BPartner_ID
    -- fresh specific: Retrieve an address for all receipt lines linked to an order line
    -- Note: This approach is used for purchase transactions only. Sales transactions are retrieved like always
    -- Note: Empties Receipts are also work the regular way
         LEFT JOIN C_Orderline ol ON ol.C_OrderLine_ID = p_record_id
    -- Retrieve 1 (random) in out linked to the given order line
    -- We assume that the the BPartner address is not changed in between. (backed with pomo)
         LEFT JOIN (
    SELECT rs.Record_ID,
           Max(iol.M_InOut_ID) AS M_InOut_ID
    FROM M_ReceiptSchedule rs
             JOIN M_ReceiptSchedule_Alloc rsa
                  ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID 
             JOIN M_InOutLine iol ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID 
    WHERE AD_Table_ID = (SELECT AD_Table_ID
                         FROM AD_Table
                         WHERE TableName = 'C_OrderLine'
                           AND isActive = 'Y')
      AND rs.isActive = 'Y'
    GROUP BY rs.Record_ID
) io_id ON io_id.Record_ID = ol.C_OrderLine_ID
         LEFT JOIN M_InOut freshio ON io_id.M_InOut_ID = freshio.M_InOut_ID 
         LEFT JOIN C_DunningDoc d ON d.C_DunningDoc_ID = p_record_id 
         LEFT JOIN C_RfQResponse rfqr ON rfqr.C_RfQResponse_ID = p_record_id 
         LEFT JOIN C_BPartner_Location bplrfqr
                   ON bplrfqr.C_BPartner_Location_ID = rfqr.C_bpartner_location_ID 
         LEFT JOIN C_BPartner bprfqr ON bprfqr.C_BPartner_ID = rfqr.C_BPartner_ID 

    -- extract marketing contact address
         LEFT JOIN mktg_contactperson mkt on mkt.mktg_contactperson_id = p_record_id 
         LEFT JOIN c_bpartner_location mktbpl
                   on mkt.c_bpartner_location_id = mktbpl.c_bpartner_location_id 
         LEFT JOIN C_BPartner mktbp ON mktbp.C_BPartner_ID = mktbpl.C_BPartner_ID


$BODY$
    LANGUAGE sql
    STABLE;