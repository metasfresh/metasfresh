-- Function: de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report(numeric, text, numeric, numeric)

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report( numeric, text, numeric, numeric );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_BPartner_Report;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Generics_BPartner_Report
(
  org_name        text,
  Org_AddressLine text,
  address1        text,
  postal          text,
  city            text,
  country         text,
  AddressBlock    text
);



CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report(
  org_id    numeric,
  doctype   text,
  bp_loc_id numeric,
  record_id numeric)
  RETURNS SETOF de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report AS
$BODY$
SELECT
  x.org_name,
  x.org_addressline,
  x.address1,
  x.postal,
  x.city,
  x.country,

  CASE
  WHEN $2 = 'o'
    THEN o.BPartnerAddress
  WHEN $2 = 'o_delivery'
    THEN o.DeliveryToAddress
  WHEN $2 = 'io'
    THEN io.BPartnerAddress
  WHEN $2 = 'freshio'
    THEN freshio.BPartnerAddress
  WHEN $2 = 'i'
    THEN i.BPartnerAddress
  WHEN $2 = 'l'
    THEN tl.BPartnerAddress
  WHEN $2 = 'lt'
    THEN COALESCE(bpg.name || ' ', '') || COALESCE(bp.name || ' ', '') || COALESCE(bp.name2 || E'\n', E'\n') ||
         COALESCE(letter.BPartnerAddress, '')
  WHEN $2 = 'd'
    THEN d.BPartnerAddress
  WHEN $2 = 'rfqr'
    THEN COALESCE(bprfqr.name || E'\n', '') || COALESCE(bplrfqr.address, '')
  WHEN $2 = 'ft'
    THEN COALESCE(bpft.name || E'\n', '') || COALESCE(bplft.address, '')
  WHEN $2 = 'mkt'
    THEN COALESCE(mktbp.name || E'\n', '') || COALESCE(mktbpl.address, '')
  WHEN $2 = 'ci'
    THEN ci.BPartnerAddress
  WHEN $3 IS NOT NULL
    THEN
      COALESCE(bp.name || E'\n', '') || COALESCE(bpl.address, '')
  ELSE 'Incompatible Parameter!'
  END || E'\n' AS addressblock
FROM
  (
    SELECT
      COALESCE(org_bp.name, '')  as org_name,
      trim(
          COALESCE(org_bp.name || ', ', '') ||
          COALESCE(loc.address1 || ' ', '') ||
          COALESCE(loc.postal || ' ', '') ||
          COALESCE(loc.city, '')
      )                          as org_addressline,
      COALESCE(loc.address1, '') as address1,
      COALESCE(loc.postal, '')   as postal,
      COALESCE(loc.city, '')     as city,
	  c.Name 					 as country
    FROM
      ad_orginfo oi
      JOIN c_bpartner_location org_bpl
        ON org_bpl.c_bpartner_location_ID = oi.orgbp_location_id AND org_bpl.isActive = 'Y'
      JOIN c_location loc ON org_bpl.c_location_id = loc.c_location_id AND loc.isActive = 'Y'
	  JOIN C_Country c ON loc.C_Country_ID = c.C_Country_ID and c.isActive = 'Y'
      JOIN C_BPartner org_bp on org_bpl.c_bpartner_id = org_bp.c_bpartner_id
    WHERE
      oi.ad_org_id = $1) x
  LEFT JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = $3 AND bpl.isActive = 'Y'
  LEFT JOIN C_BPartner bp ON bp.C_BPartner_ID = bpl.C_BPartner_ID AND bp.isActive = 'Y'
  LEFT JOIN C_Greeting bpg ON bp.C_Greeting_id = bpg.C_Greeting_ID AND bpg.isActive = 'Y'
  LEFT JOIN C_Location l ON bpl.C_Location_id = l.C_Location_ID AND l.isActive = 'Y'
  LEFT JOIN C_Country lcou ON l.C_Country_id = lcou.C_Country_ID AND lcou.isActive = 'Y'
  LEFT JOIN C_Region r ON l.C_Region_id = r.C_Region_ID AND r.isActive = 'Y'

  LEFT JOIN C_Order o ON o.C_Order_id = $4 AND o.isActive = 'Y'
  LEFT JOIN C_Invoice i ON i.C_Invoice_id = $4 AND i.isActive = 'Y'
  LEFT JOIN T_Letter_Spool tl ON tl.AD_Pinstance_ID = $4 AND tl.isActive = 'Y'
  LEFT JOIN C_Letter letter ON letter.C_Letter_ID = $4 AND letter.isActive = 'Y'
  LEFT JOIN M_InOut io ON io.M_InOut_ID = $4 AND io.isActive = 'Y'
  LEFT JOIN C_Customs_Invoice ci on ci.C_Customs_Invoice_id = $4 AND ci.isActive = 'Y'

  LEFT JOIN C_Flatrate_Term ft ON ft.C_Flatrate_Term_ID = $4 AND ft.isActive = 'Y'
  LEFT JOIN C_BPartner_Location bplft ON bplft.C_BPartner_Location_ID = ft.Bill_location_ID AND bplft.isActive = 'Y'
  LEFT JOIN C_BPartner bpft ON bpft.C_BPartner_ID = ft.Bill_BPartner_ID AND bpft.isActive = 'Y'
  -- fresh specific: Retrieve an address for all receipt lines linked to an order line
  -- Note: This approach is used for purchase transactions only. Sales transactions are retrieved like always
  -- Note: Empties Receipts are also work the regular way
  LEFT JOIN C_Orderline ol ON ol.C_OrderLine_ID = $4 AND ol.isActive = 'Y'
  -- Retrieve 1 (random) in out linked to the given order line
  -- We assume that the the BPartner address is not changed in between. (backed with pomo)
  LEFT JOIN (
              SELECT
                rs.Record_ID,
                Max(iol.M_InOut_ID) AS M_InOut_ID
              FROM M_ReceiptSchedule rs
                JOIN M_ReceiptSchedule_Alloc rsa
                  ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID AND rsa.isActive = 'Y'
                JOIN M_InOutLine iol ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
              WHERE AD_Table_ID = (SELECT AD_Table_ID
                                   FROM AD_Table
                                   WHERE TableName = 'C_OrderLine' AND isActive = 'Y') AND rs.isActive = 'Y'
              GROUP BY rs.Record_ID
            ) io_id ON io_id.Record_ID = ol.C_OrderLine_ID
  LEFT JOIN M_InOut freshio ON io_id.M_InOut_ID = freshio.M_InOut_ID AND freshio.isActive = 'Y'
  LEFT JOIN C_DunningDoc d ON d.C_DunningDoc_ID = $4 AND d.isActive = 'Y'
  LEFT JOIN C_RfQResponse rfqr ON rfqr.C_RfQResponse_ID = $4 AND rfqr.isActive = 'Y'
  LEFT JOIN C_BPartner_Location bplrfqr
    ON bplrfqr.C_BPartner_Location_ID = rfqr.C_bpartner_location_ID AND bplrfqr.isActive = 'Y'
  LEFT JOIN C_BPartner bprfqr ON bprfqr.C_BPartner_ID = rfqr.C_BPartner_ID AND bprfqr.isActive = 'Y'

  -- extract marketing contact address
  LEFT JOIN mktg_contactperson mkt on mkt.mktg_contactperson_id = $4 AND mkt.isActive = 'Y'
  LEFT JOIN c_bpartner_location mktbpl
    on mkt.c_bpartner_location_id = mktbpl.c_bpartner_location_id AND mktbpl.isActive = 'Y'
  LEFT JOIN C_BPartner mktbp ON mktbp.C_BPartner_ID = mktbpl.C_BPartner_ID AND mktbp.isActive = 'Y'


$BODY$
LANGUAGE sql
STABLE;

