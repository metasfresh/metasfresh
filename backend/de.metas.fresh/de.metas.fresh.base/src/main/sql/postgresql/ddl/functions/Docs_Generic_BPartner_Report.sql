DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report(p_org_id    numeric,
                                                                                         p_doctype   text,
                                                                                         p_bp_loc_id numeric,
                                                                                         p_record_id numeric)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report(p_org_id    numeric,
                                                                                 p_doctype   text,
                                                                                 p_bp_loc_id numeric,
                                                                                 p_record_id numeric)

    RETURNS TABLE
            (
                org_name        text,
                org_addressline text,
                address1        text,
                postal          text,
                city            text,
                country         text,
                gln             text,
                addressblock    text
            )
    STABLE
    LANGUAGE sql

AS
$BODY$
SELECT x.org_name,
       x.org_addressline,
       x.address1,
       x.postal,
       x.city,
       x.country,
       bpl.gln,

       CASE
           WHEN p_bp_loc_id IS NOT NULL
               THEN
                   COALESCE(bp.name || E'\n', '') || COALESCE(bpl.address, '')
           WHEN p_doctype = 'shn'
               THEN
                   COALESCE(bpl.address, '')
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
		   WHEN p_doctype = 'di' -- Delivery Instructions 
		       THEN ''
               ELSE 'Incompatible Parameter!'
       END || E'\n' AS addressblock
FROM (
         SELECT COALESCE(org_bp.name, '')  AS org_name,
                TRIM(
                                    COALESCE(org_bp.name || ', ', '') ||
                                    COALESCE(loc.address1 || ' ', '') ||
                                    COALESCE(loc.postal || ' ', '') ||
                                    COALESCE(loc.city, '')
                    )                      AS org_addressline,
                COALESCE(loc.address1, '') AS address1,
                COALESCE(loc.postal, '')   AS postal,
                COALESCE(loc.city, '')     AS city,
                c.Name                     AS country
         FROM ad_orginfo oi
                  JOIN c_bpartner_location org_bpl
                       ON org_bpl.c_bpartner_location_ID = oi.orgbp_location_id
                  JOIN c_location loc ON org_bpl.c_location_id = loc.c_location_id
                  JOIN C_Country c ON loc.C_Country_ID = c.C_Country_ID
                  JOIN C_BPartner org_bp ON org_bpl.c_bpartner_id = org_bp.c_bpartner_id
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
         LEFT JOIN C_Letter letter ON letter.C_Letter_ID = p_record_id
         LEFT JOIN M_InOut io ON io.M_InOut_ID = p_record_id
         LEFT JOIN C_Customs_Invoice ci ON ci.C_Customs_Invoice_id = p_record_id

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
           MAX(iol.M_InOut_ID) AS M_InOut_ID
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
         LEFT JOIN mktg_contactperson mkt ON mkt.mktg_contactperson_id = p_record_id
         LEFT JOIN c_bpartner_location mktbpl
                   ON mkt.c_bpartner_location_id = mktbpl.c_bpartner_location_id
         LEFT JOIN C_BPartner mktbp ON mktbp.C_BPartner_ID = mktbpl.C_BPartner_ID


$BODY$
    LANGUAGE sql
    STABLE
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report(numeric, text, numeric, numeric) IS
'Note: if both p_bp_loc_id and p_record_id are not null, then **p_bp_loc_id** takes precendence.'
;
